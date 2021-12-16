package com.example.sneakernet.util;

public class BidUtil {
    private String price;
    private String shoe_name;

    public BidUtil(String price, String shoe_name){
        this.price = price;
        this.shoe_name = shoe_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShoe_name() {
        return shoe_name;
    }

    public void setShoe_name(String shoe_name) {
        this.shoe_name = shoe_name;
    }
}
