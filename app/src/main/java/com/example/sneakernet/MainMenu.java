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

public class MainMenu extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private LoginViewModel mViewModel;
    private TextView welcome;
    private Button userSettings;
    private Button userMarket;
    private Button userMessages;
    private Button userCollection;
    private int num_shoes;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static final int RC_SIGN_IN = 9001;
    public static String EXTRA_MESSAGE = "000";



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




        userSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserProfile();
            }
        });
        userCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStash();
            }
        });
        userMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserMessages();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
            return;
        }

    }
    private boolean shouldStartSignIn() {
        return (!mViewModel.getIsSigningIn() && FireBaseUtil.getAuth().getCurrentUser() == null);
    }

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


    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }
    }

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
    private void toUserMessages(){
        Intent intent = new Intent(this, Messaging.class);
        startActivity(intent);
    }
    private void toUserProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


}