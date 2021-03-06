package com.example.sneakernet.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakernet.R;
import com.example.sneakernet.ShoeDetailMarket;
import com.example.sneakernet.util.ShoeUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * RecyclerView adapter for displaying the results of a Shoe {@link Query}
 */
public class shoeAdapter extends RecyclerView.Adapter<shoeAdapter.MyViewHolder> {
    private ArrayList<ShoeUtil> userShoes;
    private onShoeClicked my_onShoeClicked;

    /**
     * interface for shoes clicekd holds position
     */
    public interface onShoeClicked{
    void shoeClicked(int position);
}

    /**
     * constructor that holds list of shoes, and on click
     * @param userShoes
     * @param onShoeClicked
     */
    public shoeAdapter(ArrayList<ShoeUtil> userShoes, onShoeClicked onShoeClicked){
        this.userShoes = userShoes;
        this.my_onShoeClicked = onShoeClicked;
    }

    /**
     * Class that implements an on click listener
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView listOfShoes;
        private TextView user_shoe_size;
        private TextView user_shoe_condition;
        onShoeClicked onShoeClicked;
        public MyViewHolder(final View view, onShoeClicked onShoeClicked) {
            super(view);
            listOfShoes = view.findViewById(R.id.shoe_name);
            user_shoe_size = view.findViewById(R.id.user_shoe_size);
            user_shoe_condition = view.findViewById(R.id.user_condition);
            this.onShoeClicked = onShoeClicked;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onShoeClicked.shoeClicked(getAdapterPosition());
        }
    }

    /**
     * On Create Viewholder will return new viewholder when shoeclicked
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public shoeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.listshoe,parent,false);
        return new MyViewHolder(itemview, my_onShoeClicked);
    }

    /**
     * Lists shoes name size and condition
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull shoeAdapter.MyViewHolder holder, int position) {

        String name = userShoes.get(position).getShoe_name();
        String shoe_size = Integer.toString(userShoes.get(position).getShoe_size());
        String condition = userShoes.get(position).isShoe_condition();
        holder.listOfShoes.setText(name +" Size: " +shoe_size +" Condition: " +condition);
        holder.user_shoe_size.setText("" );
        holder.user_shoe_condition.setText("");


    }

    /**
     * return size of shoe list
     * @return
     */

    @Override
    public int getItemCount() {
        return userShoes.size();
    }



}


