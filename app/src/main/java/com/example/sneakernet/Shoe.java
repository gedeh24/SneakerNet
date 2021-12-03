package com.example.sneakernet;

public class Shoe {
    private String shoe_name;
    private int shoe_size;
    private int shoe_year;
    private String shoe_condition;
    private String shoe_color;

    public Shoe(String shoe_name, int shoe_size, int shoe_year, String shoe_condition, String shoe_color){
        this.shoe_color = shoe_color;
        this.shoe_size = shoe_size;
        this.shoe_name = shoe_name;
        this.shoe_year = shoe_year;
        this.shoe_condition = shoe_condition;
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
