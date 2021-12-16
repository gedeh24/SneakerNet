package com.example.sneakernet.viewmodel;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Model POJO for a rating. Gets rating from each user and has getters and setters
 */
public class Rating {
    /**
     * user ID
     */
    private String userId;
    /**
     * User name
     */
    private String userName;
    /**
     * Rating
     */
    private double rating;
    /**
     * text
     */
    private String text;
    /**
     * Date
     */
    private @ServerTimestamp Date timestamp;

    /**
     * constructor initialize
     * @param user
     * @param rating
     * @param text
     */
    public Rating(FirebaseUser user, double rating, String text) {
        this.userId = user.getUid();
        this.userName = user.getDisplayName();
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }

        this.rating = rating;
        this.text = text;
    }

    /**
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @return userName
     */

    public String getUserName() {
        return userName;
    }



    /**
     *
     * @return rating
     */

    public double getRating() {
        return rating;
    }


    /**
     * return tect
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * sets text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * return timestsmp
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * sets timestamp
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

