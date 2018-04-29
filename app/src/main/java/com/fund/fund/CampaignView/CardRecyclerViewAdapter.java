package com.fund.fund.CampaignView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fund.fund.Models.Event;
import com.fund.fund.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Praveer on 3/11/2018.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.CampaignViewHolder>{
    List<Event> events;
    Context context;

    CardRecyclerViewAdapter(List<Event> events, Context applicationContext) {
        this.events = events;
        this.context = applicationContext;
    }

    @Override
    public CampaignViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_card_view, parent, false);
        CampaignViewHolder pvh = new CampaignViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CampaignViewHolder holder, int i) {
        final Event event = events.get(i);
        holder.name.setText(event.title);
        holder.location.setText(event.event_venue);
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        holder.date.setText(df.format(event.event_date));
        Glide.with(context).load(event.event_image_url).into(holder.photo);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CampaignDetailsActivity.class);
                intent.putExtra("image_url", event.event_image_url);
                intent.putExtra("title",event.title);
                intent.putExtra("venue",event.event_venue);
                intent.putExtra("event_date", event.event_date.getTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class CampaignViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView location;
        ImageView photo;
        TextView date;

        CampaignViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            location = (TextView) itemView.findViewById(R.id.location);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            date = (TextView) itemView.findViewById(R.id.time);

        }
    }
}

