package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class LoyaltyAdapter extends RecyclerView.Adapter<LoyaltyAdapter.ViewHolder> {

    private Context context;
    private List<Integer> cupList;

    public LoyaltyAdapter(Context context, List<Integer> cupList) {
        this.context = context;
        this.cupList = cupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.listcups, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        int cupImageResource = cupList.get(position);
        holder.imageViewCup.setImageResource(cupImageResource);
    }

    @Override
    public int getItemCount() {
        return cupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCup;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewCup = itemView.findViewById(R.id.cupImageView);
        }
    }
}