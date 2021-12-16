package com.example.sneakernet;

import static android.content.ContentValues.TAG;

;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneakernet.viewmodel.LoginViewModel;
import com.firebase.ui.auth.data.model.User;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.lifecycle.ViewModelProvider;
import com.firebase.ui.auth.AuthUI;
import com.example.sneakernet.util.FireBaseUtil;

import java.io.InputStream;
import java.util.Collections;

/**
 * Main menu where user can login and choose from different options on where to go from there
 */

public class MainMenu extends AppCompatActivity {
    /**
     * Firestore instance
     */
    private FirebaseFirestore mFirestore;
    /**
     * Query instance
     */
    private Query mQuery;
    /**
     * Viewmodel instance
     */
    private LoginViewModel mViewModel;
    /**
     * Welcome textview
     */
    private TextView welcome;
    /**
     * Settings button
     */
    private Button userSettings;
    /**
     * market button
     */
    private Button userMarket;
    /**
     * user Message settings
     */
    private Button userMessages;
    /**
     * stash button
     */
    private Button userCollection;
    /**
     * shoes iin collection
     */
    private int num_shoes;
    /**
     * Analytics instance
     */
    private FirebaseAnalytics mFirebaseAnalytics;
    /**
     * sign in int
     */
    private static final int RC_SIGN_IN = 9001;
    /**
     * String for when going to intent
     */
    public static String EXTRA_MESSAGE = "000";


    /**
     * All buttons are set visible and user can sign in.
     * Any button that is clicked will take you to the home page. There is a welcome text that is put in there as well
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        FireBaseUtil g = new FireBaseUtil();
        getInstance().signOut();
        mFirestore = FireBaseUtil.getFirestore();
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initRecyclerView();
        welcome = findViewById(R.id.UserSettings);
        welcome.setText("Welcome to SneakerNet where your \t\t\tshoes are the stars.\n\tWhat would you like to do???");
        userCollection = findViewById(R.id.userCollection);
        userSettings = findViewById(R.id.userSettings);
        userMarket = findViewById(R.id.userMarket);
        userMessages = findViewById(R.id.userMessaging);
        num_shoes = sharedPref.getInt("Shoes in Collection", 0);
       // mFirebaseAnalytics.setUserProperty("Number of Shoes", Integer.toString(num_shoes));
        userMessages.setVisibility(View.VISIBLE);
        userMarket.setVisibility(View.VISIBLE);
        userSettings.setVisibility(View.VISIBLE);
        userCollection.setVisibility(View.VISIBLE);

        userMessages.invalidate();
        userMarket.invalidate();
        userSettings.invalidate();
        userCollection.invalidate();

/**
 * on click listener to send to another page
 */
        userMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMarketPlace();
            }
        });
/**
 * on click listener to send to another page
 */
        userSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserProfile();
            }
        });
        /**
         * on click listener to send to another page
         */
        userCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStash();
            }
        });
        /**
         * on click listener to send to another page
         */
        userMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserMessages();
            }
        });


    }

    /**
     * on start
     */
    @Override
    public void onStart() {
        super.onStart();

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
            return;
        }

    }

    /**
     * returns sign in boolean
     * @return
     */
    private boolean shouldStartSignIn() {
        return (!mViewModel.getIsSigningIn() && FireBaseUtil.getAuth().getCurrentUser() == null);
    }

    /**
     * signs in to firebase
     */

    private void startSignIn() {
        // Sign in with FirebaseUI

        Intent intent = FireBaseUtil.getAuthUI()
                .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
        mViewModel.setIsSigningIn(true);
    }

    /**
     * Initializes recycler view
     */

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }
    }

    /**
     * takes me to stash page. saves how many shoes user has
     */
    private void toStash(){
        SharedPreferences sharedPref;

        {
            sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(EXTRA_MESSAGE, num_shoes);
            editor.apply();
        }

        Intent intent = new Intent(this, Stash.class);
        intent.putExtra(EXTRA_MESSAGE,num_shoes);
        startActivity(intent);
    }

    /**
     * takes me to usermessages
     */
    private void toUserMessages(){
        Intent intent = new Intent(this, Messaging.class);
        startActivity(intent);
    }

    /**
     * takes me to user profile
     */
    private void toUserProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    /**
     * takes me to marketplace
     */
    private void toMarketPlace(){
        Intent intent = new Intent(this, MarketPlace.class);
        startActivity(intent);
    }


}