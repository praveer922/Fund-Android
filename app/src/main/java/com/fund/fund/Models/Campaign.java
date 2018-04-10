package com.fund.fund.Models;

import com.fund.fund.Models.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Praveer on 3/11/2018.
 */

public class Campaign {
    public String id;
    public String title;
    public String description;
    public String location;
    public String creator;
    public Date date_created;
    public Date date_event;
    public String imgUrl;
    public List<Component> components;
    public List<User> attendees;

    public Campaign(String id, String title, String description, String location,
             String creator, Date date_created, Date date_event, String imgUrl,
             List<Component> components, List<User> attendees) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.creator = creator;
        this.date_created = date_created;
        this.date_event = date_event;
        this.imgUrl = imgUrl;
        this.components = components;
        this.attendees = attendees;
    }
}
