package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder>  {

    List<Drink> modellistdrink;
    Context context;
    public RewardAdapter(Context context, List<Drink> modellist){
        this.context=context;
        this.modellistdrink=modellist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rewardlist,parent,false);
        return new RewardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String NameDrink1=modellistdrink.get(position).getDrinkName();
        String Quantity1=modellistdrink.get(position).getDrinkQuantity();
        int Points=100*Integer.parseInt(Quantity1);
        holder.DrinkName.setText(NameDrink1);
        holder.DrinkQuantity.setText(Quantity1);
        if (Integer.parseInt(Quantity1) == 0) {
            holder.DrinkQuantity.setText("1");
            holder.Point.setText("- 500 pts");
        }
        else
            holder.Point.setText("+ " + String.valueOf(Points) + " pts");
    }

    @Override
    public int getItemCount(){return modellistdrink.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView DrinkName, DrinkQuantity, Point;

        public ViewHolder( View itemView) {
            super(itemView);
            DrinkName=itemView.findViewById(R.id.drinkNameReward);
            DrinkQuantity=itemView.findViewById(R.id.quantityReward);
            Point =itemView.findViewById(R.id.pointsReward);
        }

        @Override
        public void onClick(View v) {
        }
    }}

