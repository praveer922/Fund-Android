package com.fund.fund.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Praveer on 3/21/2018.
 */

public class Component implements Serializable{
    int component_id;
    public String title;
    public Double budget;
    public String image_url;
    public List<Funder> funders;

    public Component(int component_id, String title, Double budget, String image_url, List<Funder> funders) {
        this.component_id = component_id;
        this.title = title;
        this.budget = budget;
        this.image_url = image_url;
        this.funders = funders;
    }
}
