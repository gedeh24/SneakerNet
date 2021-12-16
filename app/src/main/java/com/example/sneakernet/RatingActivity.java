package com.example.sneakernet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.sneakernet.adapter.RatingAdapter;
import com.example.sneakernet.util.ShoeUtil;
import com.example.sneakernet.viewmodel.Rating;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * THIS IS WHERE USERS CAN ADD RATING BUT HAS BEEEN REMOVED BECAUSE IT CONTINUALLY CRASHED
 * implements View on click listener
 */
public class RatingActivity extends AppCompatActivity implements View.OnClickListener,
        EventListener<DocumentSnapshot>,
        RatingDialogFragment.RatingListener{
    /**
     * instance of rating bar
     */
    private MaterialRatingBar mRatingIndicator;
    /**
     * instance of recycler view
     */
    private RecyclerView mRatingsRecycler;
    /**
     * Instance of Dialog
     */
    private RatingDialogFragment mRatingDialog;
    /**
     * Instance of firestore
     */
    private FirebaseFirestore mFirestore;
    /**
     * instance of shoe reference
     */
    private DocumentReference mShoeref;
    /**
     * Listener instance
     */
    private ListenerRegistration mShoeRegistration;
    /**
     * holds shoe id
     */
    public static final String KEY_SHOE_ID1 = "key_shoe_id";

    /**
     * instance of adapter
     */

    private RatingAdapter mRatingAdapter;

    /**
     * loads up review layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_layout);


    }

    /**
     * on click method
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * on rating method
     * @param rating
     */
    @Override
    public void onRating(Rating rating) {

    }

    /**
     * On event method
     * @param value
     * @param error
     */
    @Override
    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

    }
}