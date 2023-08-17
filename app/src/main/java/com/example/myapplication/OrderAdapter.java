package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Model> modellist;
    Context context;
    public OrderAdapter(Context context, List<Model> modellist){
        this.context=context;
        this.modellist=modellist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.listitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String NameDrink=modellist.get(position).getDrinkName();
        String description=modellist.get(position).getDrinkDetail();
        int images=modellist.get(position).getDrinkPhoto();

        holder.DrinkName.setText(NameDrink);
        holder.DrinkDescription.setText(description);
        holder.imageView.setImageResource((images));
    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView DrinkName, DrinkDescription;
        ImageView imageView;

        public ViewHolder( View itemView) {
            super(itemView);
            DrinkName=itemView.findViewById(R.id.coffeeName);
            DrinkDescription=itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.coffeeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == 0) {
                Intent intent = new Intent(context, InfoActivity.class);
                context.startActivity(intent);
            }

            if (position == 1){
                Intent intent = new Intent(context, CappuccinoActivity.class);
                context.startActivity(intent);
            }

            if (position == 2){
                Intent intent = new Intent(context, MochaActivity.class);
                context.startActivity(intent);
            }

            if (position == 3){
                Intent intent = new Intent(context, LatteActivity.class);
                context.startActivity(intent);
            }
        }
    }
}
