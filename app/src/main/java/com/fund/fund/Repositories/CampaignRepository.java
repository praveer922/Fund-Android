package com.fund.fund.Repositories;

import android.widget.Toast;

import com.fund.fund.JSONParser;
import com.fund.fund.Models.Campaign;
import com.fund.fund.Models.Component;
import com.fund.fund.Models.Funder;
import com.fund.fund.Models.User;
import com.fund.fund.OkHttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Praveer on 3/22/2018.
 */

public class CampaignRepository {

    private static List<Campaign> getCampaigns() {
        final List<Campaign> campaigns = new ArrayList<>();
        OkHttpHandler.getInstance().doGet("/api/auth/campaign/retrieve-all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    campaigns.addAll(parseCampaignsResponse(new JSONObject(response.body().string())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return campaigns;
    }

    public static List<Campaign> parseCampaignsResponse(JSONObject json) throws JSONException {
        JSONArray campaignsJson = json.getJSONArray("data");
        List<Campaign> campaigns = new ArrayList<Campaign>();
        for(int i=0;i<campaignsJson.length();i++) {
            JSONObject campaignJsonObject = campaignsJson.getJSONObject(i);
            String campaign_id = campaignJsonObject.getString("campaign_id");
            String description = campaignJsonObject.getString("description");
            String title = campaignJsonObject.getString("title");
            String location = campaignJsonObject.getString("location");
            String creator = campaignJsonObject.getString("creator");
            Date date_created = new Date(campaignJsonObject.getLong("date_created"));
            Date date_event = new Date(campaignJsonObject.getLong("date_event"));
            String cover_image_url = campaignJsonObject.getString("cover_image_url");

            //attendees
            JSONArray attendeeIdsJson = campaignJsonObject.getJSONArray("attendees");
            List<String> attendeeIds = new ArrayList<>();
            for(int j=0;j<attendeeIdsJson.length();j++) {
                attendeeIds.add(attendeeIdsJson.getString(j));
            }
            List<User> attendees = UserRepository.getUsers(attendeeIds);

            //components
            List<Component> components = new ArrayList<>();
            JSONArray componentsJson = campaignJsonObject.getJSONArray("components");
            for(int j=0;j<componentsJson.length();j++) {
                JSONObject componentsJsonObject = componentsJson.getJSONObject(i);
                Double budget = componentsJsonObject.getDouble("budget");
                String name = componentsJsonObject.getString("name");
                Double cur_funded= componentsJsonObject.getDouble("cur_funded");
                components.add(new Component(name,budget,cur_funded,new ArrayList<Funder>()));
            }


            Campaign campaign = new Campaign(campaign_id,title,description,location,creator,date_created,date_event,cover_image_url,components,attendees);
            campaigns.add(campaign);
        }

        return campaigns;

    }
}
