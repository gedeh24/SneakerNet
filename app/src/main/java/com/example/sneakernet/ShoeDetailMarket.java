package com.example.sneakernet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShoeDetailMarket extends AppCompatActivity {
    private static final String TAG = "ShoeDetail";
    public static final String KEY_SHOE_ID = "key_shoe_id";
    public static final String KEY_SHOE_SIZE = "key_shoe_id1";
    public static final String KEY_SHOE_NAME = "key_shoe_id2";
    public static final String KEY_SHOE_YEAR = "key_shoe_id3";
    public static final String KEY_SHOE_CONDITION = "key_shoe_id4";
    public static final String KEY_SHOE_COLOR = "key_shoe_id5";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_detail_market);
    }
}