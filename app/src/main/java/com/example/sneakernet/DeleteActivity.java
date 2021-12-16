package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sneakernet.adapter.shoeAdapter;
import com.example.sneakernet.util.FireBaseUtil;
import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private Button accept_delete;
    private Button decline_delete;
    private Query mQuery;


    private FirebaseFirestore mFirestore;

    public static final String KEY_SHOE_NAME = "key_shoe_id2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Intent intent = getIntent();
        String delete_shoe_name = intent.getStringExtra(KEY_SHOE_NAME);
        accept_delete = findViewById(R.id.button5);
        decline_delete = findViewById(R.id.button6);


        accept_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        decline_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });







        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        mQuery = mFirestore.collection("Shoe");

        /**
        mQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               if(delete_shoe_name ==  document.getData().get("shoe_name")){

                               }
                            }



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
**/






    }


}