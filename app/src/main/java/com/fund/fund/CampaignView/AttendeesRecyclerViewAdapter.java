package com.fund.fund.CampaignView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fund.fund.Models.User;
import com.fund.fund.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Praveer on 5/11/2018.
 */

public class AttendeesRecyclerViewAdapter extends RecyclerView.Adapter<AttendeesRecyclerViewAdapter.AttendeesViewHolder>{
    List<User> attendees;

    AttendeesRecyclerViewAdapter(List<User> attendees) {
        this.attendees = attendees;
    }

    @Override
    public AttendeesRecyclerViewAdapter.AttendeesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendee_profile_image, parent, false);
        AttendeesViewHolder vh = new AttendeesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AttendeesRecyclerViewAdapter.AttendeesViewHolder holder, int i) {
        final User attendee = attendees.get(i);
        if(!attendee.profile_image_url.equals("null")) {
            Glide.with(holder.profile_image.getContext()).load(attendee.profile_image_url).into(holder.profile_image);
        } else {
            Glide.with(holder.profile_image.getContext()).load(R.drawable.female_placeholder).into(holder.profile_image);
        }

        if(i==0) {
            holder.hostText.setText("HOST");
        }

//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,CampaignDetailsActivity.class);
//                intent.putExtra("event", event);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(attendees.size()<=4) {
            return attendees.size();
        } else {
            return 4;
        }
    }

    public static class AttendeesViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView hostText;

        AttendeesViewHolder(View itemView) {
            super(itemView);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            hostText = (TextView) itemView.findViewById(R.id.host_text);
        }
    }
}
