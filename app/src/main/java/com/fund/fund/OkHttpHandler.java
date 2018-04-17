package com.fund.fund;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.io.IOException;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Praveer on 3/9/2018.
 */

public class OkHttpHandler {
    public static String BASE_URL = "http://54.214.106.170:3333";

    private static OkHttpHandler okHttpHandlerInstance;

    OkHttpHandler() {

    }

    public void doPost(String path, RequestBody formBody, Callback callback) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(formBody)
                .build();

        client.newCall(request)
                .enqueue(callback);
    }

    public void doGet(String path, Callback callback, Context applicationContext) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Authorization", "Bearer " + getToken(applicationContext))
                .build();
        client.newCall(request)
                .enqueue(callback);
    }

    public static OkHttpHandler getInstance(){
        if (okHttpHandlerInstance == null){ //if there is no instance available... create new one
            okHttpHandlerInstance = new OkHttpHandler();
        }

        return okHttpHandlerInstance;
    }

    public static void setToken(String token, Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getToken(Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE);
        return pref.getString("token", null);
    }
}
