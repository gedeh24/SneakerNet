package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MarketPlace extends AppCompatActivity implements shoeAdapter.onShoeClicked {
    private Query mQuery;
    private shoeAdapter mAdapter;

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
        mQuery = mFirestore.collection("Shoe");


        mQuery.get()
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
                            initRecyclerView();


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }



    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new shoeAdapter(userShoe, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void shoeClicked(int position) {
       userShoe.get(position);
        Intent intent = new Intent(this, ShoeDetailMarket.class);
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_NAME, userShoe.get(position).getShoe_name());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_ID, userShoe.get(position).getUID());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_COLOR, userShoe.get(position).getShoe_color());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_SIZE, Integer.toString(userShoe.get(position).getShoe_size()));
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_CONDITION, userShoe.get(position).isShoe_condition());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_NAME, Integer.toString(userShoe.get(position).getShoe_year()));
        startActivity(intent);
    }
}