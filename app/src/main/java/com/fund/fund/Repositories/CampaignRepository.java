package com.fund.fund.Repositories;

import com.fund.fund.Models.Event;
import com.fund.fund.Models.Component;
import com.fund.fund.Models.Funder;
import com.fund.fund.Models.User;
import com.fund.fund.OkHttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.Callback;

/**
 * Created by Praveer on 3/22/2018.
 */

public class CampaignRepository {

    public static List<Event> getCampaigns(Callback callback) {
        final List<Event> events = new ArrayList<>();
        OkHttpHandler.getInstance().doGet("/api/auth/event/retrieve-all", callback);
        return events;
    }

    public static List<Event> parseCampaignsResponse(String responseBody) throws JSONException {
        JSONObject json = new JSONObject(responseBody);
        JSONArray eventsJson = json.getJSONArray("data");
        List<Event> events = new ArrayList<Event>();
        for(int i=0;i<eventsJson.length();i++) {
            JSONObject eventJsonObject = eventsJson.getJSONObject(i);
            String event_id = eventJsonObject.getString("event_id");
            String description = eventJsonObject.getString("description");
            String event_title = eventJsonObject.getString("event_title");
            String event_venue = eventJsonObject.getString("event_venue");
            String event_owner_name = eventJsonObject.getString("event_owner_name");
            String event_owner_email     = eventJsonObject.getString("event_owner_email");
            String event_owner_profile_image     = eventJsonObject.getString("event_owner_profile_image");
            Date event_date_created = new Date(eventJsonObject.getLong("event_date_created"));
            Date event_date = new Date(eventJsonObject.getLong("event_date"));
            String event_image_url = eventJsonObject.getString("event_image_url");

            //attendees
            List<User> attendees = new ArrayList<>();
            JSONArray attendeesJson = eventJsonObject.getJSONArray("attendees");
            for(int m=0;m<attendeesJson.length();m++) {
                JSONObject attendeesJsonObject = attendeesJson.getJSONObject(m);
                int attendee_id = attendeesJsonObject.getInt("id");
                String name = attendeesJsonObject.getString("name");
                String email = attendeesJsonObject.getString("email");
                String profile_image_url = attendeesJsonObject.getString("profile_image_url");
                attendees.add(new User(attendee_id,name,email,profile_image_url));
            }

            //components
            List<Component> components = new ArrayList<>();
            JSONArray componentsJson = eventJsonObject.getJSONArray("components");
            for(int j=0;j<componentsJson.length();j++) {
                JSONObject componentsJsonObject = componentsJson.getJSONObject(j);
                int component_id = componentsJsonObject.getInt("id");
                String title = componentsJsonObject.getString("title");
                Double budget = componentsJsonObject.getDouble("budget");
                String image_url = componentsJsonObject.getString("image_url");

                List<Funder> funders = new ArrayList<>();
                JSONArray fundersJson = componentsJsonObject.getJSONArray("funders");
                for(int k=0;k<fundersJson.length();k++) {
                    JSONObject fundersJsonObject = fundersJson.getJSONObject(k);
                    int funder_id = fundersJsonObject.getInt("id");
                    String name = fundersJsonObject.getString("name");
                    String email = fundersJsonObject.getString("email");
                    String profile_image_url = fundersJsonObject.getString("profile_image_url");
                    double amount_funded = fundersJsonObject.getDouble("amount");
                    funders.add(new Funder(funder_id,name,email,profile_image_url,amount_funded));
                }
                components.add(new Component(component_id,title,budget,image_url,funders));
            }


            Event event = new Event(event_id,event_title,description,event_venue,event_owner_name, event_owner_email, event_owner_profile_image, event_date_created,event_date,event_image_url,components,attendees);
            events.add(event);
        }

        return events;

    }
}
