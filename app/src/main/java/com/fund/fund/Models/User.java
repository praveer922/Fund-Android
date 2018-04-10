package com.fund.fund.Models;

/**
 * Created by Praveer on 3/21/2018.
 */

public class User {
    String email;
    String display_name;
    String imgUrl;

    User(String email, String display_name, String imgUrl) {
        this.email = email;
        this.display_name = display_name;
        this.imgUrl = imgUrl;
    }
}
