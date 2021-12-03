package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.sneakernet.util.FireBaseUtil;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Stash extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private HashMap<String, Shoe> userShoe;
    private Button newShoe;
    private Button viewShoes;
    private Dialog dialog;
    private Dialog dialog_view;
    private Query mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stash);
        userShoe = new HashMap<>();
        Intent intent = getIntent();
        String num_shoes = intent.getStringExtra(MainMenu.EXTRA_MESSAGE);
        newShoe = findViewById(R.id.addNew);
        viewShoes = findViewById(R.id.viewStash);
        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        mQuery = mFirestore.collection("Shoe");


        dialog_view = new Dialog(this);
        viewShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.activity_shoes);
                TextView exit = dialog.findViewById(R.id.exitSign);

                mFirestore.collection("Shoe")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        dialog = new Dialog(this);
        newShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.shoe_information);
                Button submit_form = (Button) dialog.findViewById(R.id.submitForm);

                submit_form.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText userAnswer1 = dialog.findViewById(R.id.useAns1);
                        EditText userAnswer2= dialog.findViewById(R.id.useAns2);
                        EditText userAnswer3= dialog.findViewById(R.id.useAns3);
                        EditText userAnswer4= dialog.findViewById(R.id.useAns4);
                        EditText userAnswer5= dialog.findViewById(R.id.useAns5);
                        Shoe shoe = new Shoe(userAnswer1.getText().toString(),Integer.parseInt(userAnswer2.getText().toString()),Integer.parseInt(userAnswer3.getText().toString()),userAnswer4.getText().toString(),userAnswer5.getText().toString());
                        addShoe(shoe);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    //get user name and put into database

    public void addShoe(Shoe shoe){
        String id= FirebaseAuth.getInstance().getUid();
        CollectionReference shoes = mFirestore.collection("Shoe");
        userShoe.put(id,shoe);
        shoes.add(userShoe);
    }
}