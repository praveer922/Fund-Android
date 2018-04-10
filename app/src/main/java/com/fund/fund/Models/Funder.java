package com.fund.fund.Models;

/**
 * Created by Praveer on 3/21/2018.
 */

public class Funder {
    public User user;
    public Float amount_funded;

    Funder(User user, Float amount_funded) {
        this.user = user;
        this.amount_funded = amount_funded;
    }
}
