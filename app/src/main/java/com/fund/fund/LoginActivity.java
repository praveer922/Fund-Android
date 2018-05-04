package com.fund.fund;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fund.fund.CampaignView.CampaignViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import spencerstudios.com.bungeelib.Bungee;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String EMAIL = "email";
    private static final String USER_FRIENDS = "user_friends";

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //remove focus
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //bold sign up text
        SpannableStringBuilder str = new SpannableStringBuilder("Don't have an account? Sign Up");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        _signupLink.setText(str);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                login(email,password);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                Bungee.slideLeft(LoginActivity.this);
            }
        });

        //facebook login
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL,USER_FRIENDS));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                onFacebookLoginSucess(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Facebook login cancelled", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Facebook login failed", Toast.LENGTH_SHORT);
                exception.printStackTrace();
            }
        });

        //try quick login
        String token = OkHttpHandler.getToken(this);
        if(token!=null) {
            tokenLogin(token);
        }

    }

    private void onFacebookLoginSucess(AccessToken accessToken) {
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("fb_token", accessToken.getToken());

        RequestBody formBody = formBuilder.build();

        try {
            OkHttpHandler.getInstance().doPost("/api/user/login-facebook", formBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoginFailed("Login Failed");
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonRes = null;
                                try {
                                    jsonRes = new JSONObject(response.body().string());
                                    onLoginSuccess(jsonRes.getJSONObject("data").getString("token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    if(response.code() == 403){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoginFailed("Facebook user login failed");
                            }
                        });
                    } else if(response.code() == 422) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoginFailed("Facebook user login missing fields");
                            }
                        });
                    }
                }
            },this);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }

    public void login(String email, String password) {
        if (!validate()) {
            onLoginFailed("Login Failed");
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("email", email)
                .add("password", password);

        RequestBody formBody = formBuilder.build();

        try {
            OkHttpHandler.getInstance().doPost("/api/user/login", formBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            onLoginFailed("Login Failed");
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                JSONObject jsonRes = null;
                                try {
                                    jsonRes = new JSONObject(response.body().string());
                                    onLoginSuccess(jsonRes.getJSONObject("data").getString("token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    if(response.code() == 403){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                onLoginFailed("Wrong password");
                            }
                        });
                    }
                }
            }, this);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void tokenLogin(final String token) {
        FormBody.Builder formBuilder = new FormBody.Builder();

        RequestBody formBody = formBuilder.build();

        try {
            OkHttpHandler.getInstance().doPost("/api/auth/user/token-login", formBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoginFailed("Login Failed");
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoginSuccess(token);
                            }
                        });
                    }

                    if(response.code() == 403){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoginFailed("User login failed.");
                            }
                        });
                    }
                }
            }, this);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                login(data.getStringExtra("email"),data.getStringExtra("password"));
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String token) {
        _loginButton.setEnabled(true);
        OkHttpHandler.setToken(token, getApplicationContext());
        Intent intent = new Intent(this, CampaignViewActivity.class);
        startActivity(intent);
        Bungee.slideDown(this);
    }

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError("at least 6 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}