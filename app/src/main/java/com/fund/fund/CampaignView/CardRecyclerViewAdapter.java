package com.fund.fund.CampaignView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fund.fund.Models.Campaign;
import com.fund.fund.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Praveer on 3/11/2018.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.CampaignViewHolder>{
    List<Campaign> campaigns;

    CardRecyclerViewAdapter(List<Campaign> campaigns) {
     this.campaigns = campaigns;
    }
    @Override
    public CampaignViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_card_view, parent, false);
        CampaignViewHolder pvh = new CampaignViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CampaignViewHolder holder, int i) {
        holder.name.setText(campaigns.get(i).title);
        holder.location.setText(campaigns.get(i).location);
        ImageView photo  = holder.photo;
        Picasso.with(photo.getContext()).load(campaigns.get(i).imgUrl).into(photo);
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public static class CampaignViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView location;
        ImageView photo;

        CampaignViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            location = (TextView) itemView.findViewById(R.id.location);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}

