package com.example.sneakernet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakernet.R;
import com.example.sneakernet.util.ShoeUtil;
import java.util.ArrayList;

public class shoeAdapter extends RecyclerView.Adapter<shoeAdapter.MyViewHolder> {
    private ArrayList<ShoeUtil> userShoes;

    public shoeAdapter(ArrayList<ShoeUtil> userShoes){
        this.userShoes = userShoes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView listOfShoes;

        public MyViewHolder(final View view) {
            super(view);
            listOfShoes = view.findViewById(R.id.user_shoe_list);
        }
    }

    @NonNull
    @Override
    public shoeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.listshoe,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull shoeAdapter.MyViewHolder holder, int position) {
        String name = userShoes.get(position).getShoe_name();
        holder.listOfShoes.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
       return userShoes.size();
    }
}
