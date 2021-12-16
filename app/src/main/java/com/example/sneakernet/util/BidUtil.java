package com.example.sneakernet.util;

/**
 * Class that allows user to bid price. Every bid has a price and shoe name
 */
public class BidUtil {
    /**
     * price
     */
    private String price;
    /**
     * shoe name
     */
    private String shoe_name;


    /**
     * sets constructor
     * @param price
     * @param shoe_name
     */
    public BidUtil(String price, String shoe_name){
        this.price = price;
        this.shoe_name = shoe_name;
    }

    /**
     * returns price
     * @return
     */
    public String getPrice() {
        return price;
    }

    /**
     * returns shoe name
     * @return
     */
    public String getShoe_name() {
        return shoe_name;
    }


}
