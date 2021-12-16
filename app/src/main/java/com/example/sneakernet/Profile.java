package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sneakernet.util.FireBaseUtil;
import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private Button deleteProfile;
    private Button logOut;
    private TextView name;
    private EditText email;
    private TextView shoe_counter;
    private FirebaseFirestore mFirestore;
    private int shoe_count;
    private Button userChanges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userChanges = findViewById(R.id.userChanges);
        logOut = findViewById(R.id.button2);
        deleteProfile = findViewById(R.id.DeleteAccount);
        shoe_count = 0;
        shoe_counter = findViewById(R.id.userShoeNumbers);
        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore.collection("Shoe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String UID = (String) document.getData().get("uid");
                                if (UID.equals(FirebaseAuth.getInstance().getUid())) {
                                    shoe_count++;

                                }

                            }
                            String temp = Integer.toString(shoe_count);
                            shoe_counter.setText("You have " +temp + " sneakers in your collection!!");


                        }
                    }
                });


        name = findViewById(R.id.Name);
        email = findViewById(R.id.userEmail);


        userChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance().getCurrentUser().updateEmail(email.getText().toString());
            }
        });



        name.setText("Name is: " + getInstance().getCurrentUser().getDisplayName());
        email.setHint("User's email is " + getInstance().getCurrentUser().getEmail());
        //email.setText();



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseAuth.getInstance().getCurrentUser().delete();
                FirebaseAuth.getInstance().signOut();
            }
        });



    }

}