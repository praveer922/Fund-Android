package com.fund.fund.Models;

import java.util.List;

/**
 * Created by Praveer on 3/21/2018.
 */

public class Component {
    public String name;
    public Double budget;
    public Double cur_funded;
    public List<Funder> funders;

    public Component(String name, Double budget, Double cur_funded, List<Funder> funders) {
        this.name = name;
        this.budget = budget;
        this.cur_funded = cur_funded;
        this.funders = funders;
    }
}
