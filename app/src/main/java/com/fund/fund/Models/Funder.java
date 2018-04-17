package com.fund.fund.Models;

/**
 * Created by Praveer on 3/21/2018.
 */

public class Funder {
    int funder_id;
    String name;
    String email;
    String profile_image_url;
    double amount_funded;

    public Funder(int funder_id, String name, String email, String profile_image_url, double amount_funded) {
        this.funder_id = funder_id;
        this.name = name;
        this.email = email;
        this.profile_image_url = profile_image_url;
        this.amount_funded = amount_funded;
    }
}
