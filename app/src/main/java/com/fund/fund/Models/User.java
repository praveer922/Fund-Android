package com.fund.fund.Models;

import java.io.Serializable;

/**
 * Created by Praveer on 3/21/2018.
 */

public class User implements Serializable{
    public int attendee_id;
    public String name;
    public String email;
    public String profile_image_url;

    public User(int attendee_id, String name, String email, String profile_image_url) {
        this.attendee_id = attendee_id;
        this.name = name;
        this.email = email;
        this.profile_image_url = profile_image_url;
    }
}
