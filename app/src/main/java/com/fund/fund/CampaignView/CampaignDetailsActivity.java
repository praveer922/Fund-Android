package com.fund.fund.CampaignView;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fund.fund.Models.Component;
import com.fund.fund.Models.Event;
import com.fund.fund.Models.Funder;
import com.fund.fund.Models.User;
import com.fund.fund.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        setData(event);
    }

    private void setData(Event event) {
        String image_url = event.event_image_url;
        String title = event.title;
        String venue = event.event_venue;
        Date event_date = event.event_date;
        TextView titleTv = findViewById(R.id.event_title);
        titleTv.setText(title);

        TextView venueTv = findViewById(R.id.location);
        venueTv.setText(venue);

        TextView dateTv = findViewById(R.id.time);
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        dateTv.setText(df.format(event_date));

        ImageView img = findViewById(R.id.event_img);
        Glide.with(this).load(image_url).apply(RequestOptions.centerCropTransform()).into(img);

        final RecyclerView rv = (RecyclerView)findViewById(R.id.avatar_list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);

        AttendeesRecyclerViewAdapter adapter = new AttendeesRecyclerViewAdapter(event.attendees);
        rv.setAdapter(adapter);

        if(event.attendees.size()>4) {
            TextView othersText = findViewById(R.id.others_text);
            othersText.setText("+" + (event.attendees.size()-4) + " OTHERS");
        }

        TextView desc = findViewById(R.id.event_description);
        desc.setText(event.description);

        RoundCornerProgressBar budgetBar = (RoundCornerProgressBar) findViewById(R.id.budget_bar);
        float totalBudget = calculateDesiredBudget(event.components);
        float funded = calculateFundedAmount(event.components);
        budgetBar.setMax(totalBudget);
        budgetBar.setProgress(funded);

        TextView amtFunded = (TextView) findViewById(R.id.amt_funded);
        amtFunded.setText("$" + String.format("%d",(long)funded)+ "/$" + String.format("%d",(long)totalBudget));

    }

    private float calculateFundedAmount(List<Component> components) {
        float sum = 0;
        for(Component component: components) {
            for(Funder funder: component.funders) {
                sum+= funder.amount_funded;
            }
        }
        return sum;
    }

    private float calculateDesiredBudget(List<Component> components) {
        float sum = 0;
        for(Component component : components) {
            sum += component.budget;
        }
        return sum;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
