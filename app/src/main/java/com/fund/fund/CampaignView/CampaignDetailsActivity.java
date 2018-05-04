package com.fund.fund.CampaignView;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fund.fund.Models.Event;
import com.fund.fund.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CampaignDetailsActivity extends AppCompatActivity {

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_campaign_details);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        event = (Event) getIntent().getSerializableExtra("event");
        setData(event.event_image_url,event.title,event.event_venue,event.event_date);
    }

    private void setData(String image_url, String title, String venue, Date event_date) {
        TextView titleTv = findViewById(R.id.event_title);
        titleTv.setText(title);

        TextView venueTv = findViewById(R.id.location);
        venueTv.setText(venue);

        TextView dateTv = findViewById(R.id.time);
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        dateTv.setText(df.format(event_date));

        ImageView img = findViewById(R.id.event_img);
        Glide.with(this).load(image_url).apply(RequestOptions.centerCropTransform()).into(img);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
