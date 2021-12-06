package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneakernet.util.ShoeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.sneakernet.util.FireBaseUtil;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.util.HashMap;

public class Stash extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private HashMap<String, ShoeUtil> userShoe;
    private Button newShoe;
    private Button viewShoes;
    private Dialog dialog;
    private Dialog dialog_view;
    private Dialog shoe_viewer;
    private String shoe_names;
    private Query mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stash);
        shoe_names = "";
        userShoe = new HashMap<>();
        Intent intent = getIntent();
       //String num_shoes = intent.getStringExtra(MainMenu.EXTRA_MESSAGE);
        newShoe = findViewById(R.id.addNew);
        viewShoes = findViewById(R.id.viewStash);
        mFirestore = FireBaseUtil.getFirestore();
        FirebaseFirestore.setLoggingEnabled(true);
        mQuery = mFirestore.collection("Shoe");


        dialog_view = new Dialog(this);



        viewShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_view.setContentView(R.layout.activity_shoes);
                TextView exit = dialog_view.findViewById(R.id.exitSign);
                TextView myShoes = dialog_view.findViewById(R.id.listOfShoes);
                shoe_names="";
                mFirestore.collection("Shoe")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                       shoe_names= shoe_names+ document.getData().get("shoe_name")+"\n";
                                        myShoes.setText(shoe_names);
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });


                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_view.dismiss();
                    }
                });
                myShoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shoe_viewer.setContentView(R.layout.view_shoe_activity);
                        new DownloadImageTask((ImageView) findViewById(R.id.imageView5))
                                .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
                        shoe_viewer.show();

                    }
                });

                dialog_view.show();
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
                        ShoeUtil shoeUtil = new ShoeUtil(userAnswer1.getText().toString(),Integer.parseInt(userAnswer2.getText().toString()),Integer.parseInt(userAnswer3.getText().toString()),userAnswer4.getText().toString(),userAnswer5.getText().toString(),FirebaseAuth.getInstance().getUid().toString());
                        addShoe(shoeUtil);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    //get user name and put into database

    public void addShoe(ShoeUtil shoeUtil){
        CollectionReference shoes = mFirestore.collection("Shoe");
        shoes.add(shoeUtil);
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

