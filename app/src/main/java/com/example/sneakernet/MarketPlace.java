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

/**
 * Makret place containing all the shoes on market
 */
public class MarketPlace extends AppCompatActivity implements shoeAdapter.onShoeClicked {
    /**
     * Query instance
     */
    private Query mQuery;
    /**
     * adapter instance
     */
    private shoeAdapter mAdapter;
    /**
     * instance of firestore
     */
    private FirebaseFirestore mFirestore;
    /**
     * arraylist of user shoes
     */
    private ArrayList<ShoeUtil> userShoe;
    /**
     * arraylist of shoe ids
     */
    private ArrayList<String> userShoeIDs;
    /**
     * recycle view instance
     */
    private RecyclerView recyclerView;

    /**
     * Query get all data  and retrieves them and saves them into an arraylist
     * duplicate shoe is added to the arrayList as well as the id of the shoe
     * All of this data is then pushed into shoeclicked where it has the specific information for the shoe
     * user can buy from there. Recycle view grows the more data inputted
     * @param savedInstanceState
     */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);

        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        userShoe = new ArrayList<>();
        userShoeIDs = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerView);
        mQuery = mFirestore.collection("Shoe");

/**
 * Loads all shoes that were selected to be placed on market
 */
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
                                Long shoe_price = (Long)document.getData().get("shoe_price");
                                int new_shoe_price = shoe_price.intValue();
                                if(done == true) {
                                    ShoeUtil duplicate_shoe = new ShoeUtil(temp, new_ret, new_shoe_year, condition, color, UID, done, new_shoe_price);
                                    userShoe.add(duplicate_shoe);
                                    userShoeIDs.add(document.getId());

                                }
                            }
                            initRecyclerView();


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    /**
     * initializes recycler view
     */
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

    /**
     * When shoe is clicked it will load shoes clicked at a position in the arraylist
     * @param position
     */
    @Override
    public void shoeClicked(int position) {
       userShoe.get(position);
        userShoeIDs.get(position);
        Intent intent = new Intent(this, ShoeDetailMarket.class);
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_NAME, userShoe.get(position).getShoe_name());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_ID, userShoeIDs.get(position));
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_COLOR, userShoe.get(position).getShoe_color());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_SIZE, Integer.toString(userShoe.get(position).getShoe_size()));
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_CONDITION, userShoe.get(position).isShoe_condition());
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_YEAR, Integer.toString(userShoe.get(position).getShoe_year()));
        intent.putExtra(ShoeDetailMarket.KEY_SHOE_PRICE, Integer.toString(userShoe.get(position).getShoe_price()));
        startActivity(intent);
    }
}