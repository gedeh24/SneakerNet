package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakernet.adapter.shoeAdapter;
import com.example.sneakernet.util.FireBaseUtil;
import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that that holds the user stash
 */
public class Stash extends AppCompatActivity implements shoeAdapter.onShoeClicked  {
    /**
     * Fire store instance
     */
    private FirebaseFirestore mFirestore;
    /**
     * ArrayList that holds every shoe
     */
    private ArrayList<ShoeUtil> userShoe;
    /**
     * button that generates new shoe
     */
    private Button newShoe;
    /**
     * view shoes
     */
    private Button viewShoes;
    /**
     * Dialog to go to another window
     */
    private Dialog dialog;
    /**
     * Dialog to go to another window
     */
    private Dialog dialog_view;
    /**
     * Dialog to go to another window
     */
    private Dialog shoe_viewer;
    /**
     * Shoe Name
     */
    private String shoe_names;
    /**
     *Whether user checked box or not
     */
    private CheckBox checkBox;
    /**
     * Collection reference of shoes
     */
    private Query mQuery;


    /**
     * Recycler view to view shoes
     */
    private RecyclerView recyclerView;
    /**
     * adapter that changes for every shoe instance
     */
    private shoeAdapter mAdapter;

    /**
     * creates layout that holds stash
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stash);

        shoe_names = "";

        userShoe = new ArrayList<>();
        newShoe = findViewById(R.id.addNew);
        viewShoes = findViewById(R.id.viewStash);
        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        mQuery = mFirestore.collection("Shoe");


        dialog_view = new Dialog(this);
        shoe_viewer = new Dialog(this);

        /**
         * When button is pressed goes through list and lists every shoe and puts into recycle view
         */
        viewShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userShoe.clear();
                dialog_view.setContentView(R.layout.activity_shoes);
                //TextView exit = dialog_view.findViewById(R.id.exitSign);
              //  TextView myShoes = dialog_view.findViewById(R.id.listOfShoes);
                recyclerView = dialog_view.findViewById(R.id.imageRecyler);
                shoe_names="";

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
                                        if(UID.equals(FirebaseAuth.getInstance().getUid()) ){
                                            ShoeUtil duplicate_shoe = new ShoeUtil(temp,new_ret,new_shoe_year,condition,color,UID,done, new_shoe_price);
                                            userShoe.add(duplicate_shoe);
                                        }
                                       // Log.d(TAG,"hey there + " +UID);
                                      //  Log.d(TAG,"Good morning " +FirebaseAuth.getInstance().getUid().toString());
                                       //shoe_names= shoe_names+ document.getData().get("shoe_name")+"\n";
                                       // myShoes.setText(shoe_names);
                                    }
                                    initRecyclerView();


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });


             /**   exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_view.dismiss();
                    }
                });**/

                dialog_view.show();
            }
        });




        /**
         * When new shew is added a forum is generated where user can enter shoe information
         * shoe is then put into the database
         */
        dialog = new Dialog(this);
        newShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.shoe_information);
                Button submit_form = (Button) dialog.findViewById(R.id.submitForm);
                TextView textView = (TextView) dialog.findViewById(R.id.exitView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                submit_form.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText userAnswer1 = dialog.findViewById(R.id.useAns1);
                        EditText userAnswer2= dialog.findViewById(R.id.useAns2);
                        EditText userAnswer3= dialog.findViewById(R.id.useAns3);
                        EditText userAnswer4= dialog.findViewById(R.id.useAns4);
                        EditText userAnswer5= dialog.findViewById(R.id.useAns5);
                        EditText userAnswer6 = dialog.findViewById(R.id.useAns);
                        checkBox = dialog.findViewById(R.id.marketAvailability);
                        Boolean available = checkBox.isChecked();
                        int temp = Integer.parseInt(userAnswer6.getText().toString());
                        ShoeUtil shoeUtil = new ShoeUtil(userAnswer1.getText().toString(),Integer.parseInt(userAnswer2.getText().toString()),Integer.parseInt(userAnswer3.getText().toString()),userAnswer4.getText().toString(),userAnswer5.getText().toString(),FirebaseAuth.getInstance().getUid().toString(),available, temp);
                        addShoe(shoeUtil);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    //get user name and put into database

    /**
     * adds shoe to data base
     * @param shoeUtil Shoe util holds a shoe
     */

    public void addShoe(ShoeUtil shoeUtil){
        CollectionReference shoes = mFirestore.collection("Shoe");
        shoes.add(shoeUtil);
    }


    /**
     * recycle view that adds all the shoes to the view. sets adapters and layouts to fit
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
     * when shoe is clicked it will open up delete shoe activity where shoe can be deleted
     * @param position
     */
    @Override
    public void shoeClicked(int position) {
        userShoe.get(position);
        Intent intent = new Intent(this, DeleteActivity.class);
        intent.putExtra(DeleteActivity.KEY_SHOE_NAME, userShoe.get(position).getShoe_name());
        startActivity(intent);

    }
}

