package com.example.sneakernet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Messaging on SneakerNEt
 */
public class Messaging extends AppCompatActivity {

    public static String fcm_message = "";

    /**
     * Losads instsance of messaging screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

    }
}