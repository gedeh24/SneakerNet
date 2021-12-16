package com.example.sneakernet.util;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Class that defines what a shoe is
 * every shoe has a size name year condition color id and check mark for if it wants to be on the market or not
 */
public class ShoeUtil {
    /**
     * shoe name
     */
    private String shoe_name;
    /**
     *Shoe size
     */
    private int shoe_size;
    /**
     * shoe year
     */
    private int shoe_year;
    /**
     * shoe condition
     */
    private String shoe_condition;
    /**
     * shoe color
     */
    private String shoe_color;
    /**
     * shoe id
     */
    private String UID;
    /**
     * boolean to see if wanted on market or not
     */
    private Boolean done;


    /**
     * shoe price
     */
    private int shoe_price;

    /**
     * Constructor that sets parameters
     * @param shoe_name
     * @param shoe_size
     * @param shoe_year
     * @param shoe_condition
     * @param shoe_color
     * @param UID
     * @param done
     * @param shoe_price
     */
    public ShoeUtil(String shoe_name, int shoe_size, int shoe_year, String shoe_condition, String shoe_color, String UID, Boolean done, int shoe_price){
        this.shoe_color = shoe_color;
        this.shoe_size = shoe_size;
        this.shoe_name = shoe_name;
        this.shoe_year = shoe_year;
        this.shoe_condition = shoe_condition;
        this.UID = UID;
        this.done = done;
        this.shoe_price = shoe_price;
}

    /**
     * returnd shoe price
     * @return
     */
    public int getShoe_price() {
        return shoe_price;
    }

    /**
     * returns shoe size
     * @return
     */
    public int getShoe_size() {
        return shoe_size;
    }

    /**
     * returns shoe condition
     * @return
     */
    public String isShoe_condition() {
        return shoe_condition;
    }

    /**
     * returns shoe year
     * @return
     */
    public int getShoe_year() {
        return shoe_year;
    }

    /**
     * returns shoe color
     * @return
     */

    public String getShoe_color() {
        return shoe_color;
    }

    /**
     * returns shoe name
     * @return
     */
    public String getShoe_name() {
        return shoe_name;
    }
}
