package com.example.sneakernet;

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

import com.example.sneakernet.util.BidUtil;
import com.example.sneakernet.util.FireBaseUtil;
import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShoeDetailMarket extends AppCompatActivity {
    private static final String TAG = "ShoeDetail";
    public static final String KEY_SHOE_ID = "key_shoe_id";
    public static final String KEY_SHOE_SIZE = "key_shoe_id1";
    public static final String KEY_SHOE_NAME = "key_shoe_id2";
    public static final String KEY_SHOE_YEAR = "key_shoe_id3";
    public static final String KEY_SHOE_CONDITION = "key_shoe_id4";
    public static final String KEY_SHOE_COLOR = "key_shoe_id5";
    public static final String KEY_SHOE_PRICE = "key_shoe_id6";
    private Dialog dialog;
    private Dialog submitPurchase;
    private BidUtil bidUtil;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    //for tomorrow add a hashmap to keep track of the shoe name and bid price.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_detail_market);
        mFirestore = FireBaseUtil.getFirestore();
        mQuery = mFirestore.collection("bidPrice");
        Intent intent = getIntent();
        String shoe_id = intent.getStringExtra(KEY_SHOE_ID);
        String shoe_size = intent.getStringExtra(KEY_SHOE_SIZE);
        String shoe_name = intent.getStringExtra(KEY_SHOE_NAME);
        String shoe_year = intent.getStringExtra(KEY_SHOE_YEAR);
        String shoe_condition = intent.getStringExtra(KEY_SHOE_CONDITION);
        String shoe_color = intent.getStringExtra(KEY_SHOE_COLOR);
        String shoe_price = intent.getStringExtra(KEY_SHOE_PRICE);
        String bid = "0";
        dialog = new Dialog(this);
        submitPurchase = new Dialog(this);
        TextView textView = findViewById(R.id.ShoeContext);
        Button userbid = findViewById(R.id.userBid);
        Button userPurchase = findViewById(R.id.userPurchase);
        textView.setText(shoe_name + " size " + shoe_size+ " "+ shoe_year + ". Condition: " + shoe_condition + "\n" +
                "shoe color is "+shoe_color);


        userPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPurchase.setContentView(R.layout.activity_confirm_purchase);
                Button confirmation = submitPurchase.findViewById(R.id.button3);
                Button decline = submitPurchase.findViewById(R.id.button4);
                TextView set_message = submitPurchase.findViewById(R.id.textView5);
                set_message.setText("Are you sure you want to make this purchase? It costs " + shoe_price + " dollars?" );

                confirmation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitPurchase.dismiss();
                    }
                });

                decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitPurchase.dismiss();
                    }
                });
                submitPurchase.show();
            }
        });

        userbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.activity_bid_info);
                Button submit = dialog.findViewById(R.id.submitBid);
                TextView highestBid;
                highestBid = dialog.findViewById(R.id.textView4);
                EditText setPrice = dialog.findViewById(R.id.editText);




               submit.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String temp = (setPrice.getText().toString()) ;
                       addBid(new BidUtil(temp,shoe_name));
                       highestBid.setText("The highest bid is currently " + bidUtil.getPrice() + " dollars");
                   }
               });



                    dialog.show();
                }



        });






    }

    public void addBid(BidUtil bidUtil){
        CollectionReference bid = mFirestore.collection("bidPrice");
        DocumentReference docRef = mFirestore.collection("bidPrice").document(bidUtil.getShoe_name());
        this.bidUtil = bidUtil;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bid.document(bidUtil.getShoe_name()).set(bidUtil.getPrice());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

}