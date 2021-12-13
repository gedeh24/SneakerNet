package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sneakernet.Stash;

import com.example.sneakernet.adapter.shoeAdapter;
import com.example.sneakernet.util.FireBaseUtil;
import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MarketPlace extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private ArrayList<ShoeUtil> userShoe;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);

        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        userShoe = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerView);


        mFirestore.collection("Shoe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String temp = (String)document.getData().get("shoe_name");
                                Long ret = (Long)document.getData().get("shoe_size") ;
                                String color =(String)document.getData().get("shoe_color");
                                Long shoe_year = (Long)document.getData().get("shoe_year");
                                int new_ret = ret.intValue();
                                int new_shoe_year = shoe_year.intValue();
                                String condition = (String)document.getData().get("shoe_condition");
                                String UID = (String)document.getData().get("uid");
                                Boolean done = (Boolean)document.getData().get("done");
                                if(done == true) {
                                    ShoeUtil duplicate_shoe = new ShoeUtil(temp, new_ret, new_shoe_year, condition, color, UID, done);
                                    userShoe.add(duplicate_shoe);
                                }
                            }
                            setAdapter();


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void setAdapter(){
        shoeAdapter shoeAdapter = new shoeAdapter(userShoe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shoeAdapter);
    }
}