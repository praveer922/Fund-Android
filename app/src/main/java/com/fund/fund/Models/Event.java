package com.fund.fund.Models;

import com.fund.fund.Models.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Praveer on 3/11/2018.
 */

public class Event implements Serializable{
    public String id;
    public String title;
    public String description;
    public String event_venue;
    public String event_owner_name;
    public String event_owner_email;
    public String event_owner_profile_image;
    public Date event_date_created;
    public Date event_date;
    public String event_image_url;
    public List<Component> components;
    public List<User> attendees;

    public Event(String id, String title, String description, String event_venue, String event_owner_name, String event_owner_email,
                 String event_owner_profile_image, Date event_date_created, Date event_date, String event_image_url,
                 List<Component> components, List<User> attendees) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.event_venue = event_venue;
        this.event_owner_name = event_owner_name;
        this.event_owner_email = event_owner_email;
        this.event_owner_profile_image = event_owner_profile_image;
        this.event_date_created = event_date_created;
        this.event_date = event_date;
        this.event_image_url = event_image_url;
        this.components = components;
        this.attendees = attendees;
    }
}
