package com.example.sneakernet.util;

import com.google.firebase.auth.FirebaseAuth;

public class ShoeUtil {
    private String shoe_name;
    private int shoe_size;
    private int shoe_year;
    private String shoe_condition;
    private String shoe_color;
    private String UID;
    private Boolean done;


    public ShoeUtil(String shoe_name, int shoe_size, int shoe_year, String shoe_condition, String shoe_color, String UID, Boolean done){
        this.shoe_color = shoe_color;
        this.shoe_size = shoe_size;
        this.shoe_name = shoe_name;
        this.shoe_year = shoe_year;
        this.shoe_condition = shoe_condition;
        this.UID = UID;
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public String getUID() {
        return UID;
    }

    public int getShoe_size() {
        return shoe_size;
    }

    public String isShoe_condition() {
        return shoe_condition;
    }

    public int getShoe_year() {
        return shoe_year;
    }

    public String getShoe_color() {
        return shoe_color;
    }

    public String getShoe_name() {
        return shoe_name;
    }
}
