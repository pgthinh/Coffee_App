package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder>  {

    List<Drink> modellistdrink;
    Context context;
    public OrderInfoAdapter(Context context, List<Drink> modellist){
        this.context=context;
        this.modellistdrink=modellist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cartlist,parent,false);
        return new OrderInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String NameDrink1=modellistdrink.get(position).getDrinkName();
        String Quantity1=modellistdrink.get(position).getDrinkQuantity();
        String Upsize1=modellistdrink.get(position).getHasUpsize();
        String Doubleshot1=modellistdrink.get(position).getDoubleshot();
        int  Price1=modellistdrink.get(position).getDrinkPrice();
        holder.DrinkName.setText(NameDrink1);
        holder.DrinkQuantity.setText(Quantity1);
        holder.Upsize.setText(Upsize1);
        holder.Doubleshot.setText(Doubleshot1);
        holder.Price.setText(String.valueOf(Price1) + " VND");
    }

    @Override
    public int getItemCount(){return modellistdrink.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView DrinkName, DrinkQuantity, Upsize, Doubleshot, Price;
        ImageView imageView;

        public ViewHolder( View itemView) {
            super(itemView);
            DrinkName=itemView.findViewById(R.id.drinkNameinOrderSummary);
            DrinkQuantity=itemView.findViewById(R.id.quantityinOrderSummary);
            Upsize=itemView.findViewById(R.id.hasUpsize);
            Doubleshot=itemView.findViewById(R.id.hasDoubleshot);
            Price=itemView.findViewById(R.id.priceinOrderSummary);
        }

        @Override
        public void onClick(View v) {
        }
    }

}