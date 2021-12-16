package com.example.sneakernet;


import static com.example.sneakernet.util.FireBaseUtil.getAuth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sneakernet.viewmodel.Rating;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Dialog Fragment containing rating form.
 */
public class RatingDialogFragment extends DialogFragment implements View.OnClickListener {


    /**
     * Instance of rating bar
     */
    private MaterialRatingBar mRatingBar;
    /**
     * Edittext instance
     */
    private EditText mRatingText;

    /**
     * interface used for listener
     */

    interface RatingListener {

        void onRating(Rating rating);

    }

    /**
     * instance of listenert
     */
    private RatingListener mRatingListener;

    /**
     * Creates instance of the layout initialzes variables
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_rating, container, false);
        mRatingBar = v.findViewById(R.id.user_form_rating);
        mRatingText = v.findViewById(R.id.user_form_text);

        v.findViewById(R.id.user_form_button).setOnClickListener(this);
        v.findViewById(R.id.user_form_cancel).setOnClickListener(this);

        return v;
    }

    /**
     * overriding method from Dialog Fragment
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RatingListener) {
            mRatingListener = (RatingListener) context;
        }
    }

    /**
     * On Resume
     */
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    /**
     * When clicked user can cancel or submit depending on case
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_form_button:
                onSubmitClicked(v);
                break;
            case R.id.user_form_cancel:
                onCancelClicked(v);
                break;
        }
    }

    /**
     * Will submit click and dismiss once the rating is instantiated
     * @param view
     */
    public void onSubmitClicked(View view) {
        Rating rating = new Rating(
                getAuth().getCurrentUser(),
                mRatingBar.getRating(),
                mRatingText.getText().toString());

        if (mRatingListener != null) {
            mRatingListener.onRating(rating);
        }

        dismiss();
    }

    /**
     * Cancels click on click
     * @param view
     */
    public void onCancelClicked(View view) {
        dismiss();
    }
}

