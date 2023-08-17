package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Model> modellist;
    RecyclerView recyclerView,recyclerViewCups;
    OrderAdapter Adapter;
    LoyaltyAdapter adapter;
    TextView loyal,numberpernumber;
    ImageView cart;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.customername);
        SharedPreferences customer = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor editname = customer.edit();
        String newname= customer.getString("name", "Thinh");
        editname.apply();
        name.setText(newname);

        recyclerViewCups=findViewById(R.id.recyclerViewCupsmain);
        loyal=findViewById(R.id.loyaltycardmain);
        numberpernumber=findViewById(R.id.numberofcupsmain);


        //Get information of loyalty card
        SharedPreferences number = getSharedPreferences("NumberCup", MODE_PRIVATE);
        SharedPreferences.Editor editnumber = number.edit();
        int numberCup = number.getInt("num", 0);
        editnumber.apply();
        List<Integer> cupList = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            if (i<numberCup)
                cupList.add(R.drawable.cupbold);
            else{
                cupList.add(R.drawable.cupnormal);
            };}
        numberpernumber.setText(String.valueOf(numberCup) +"/8");
        adapter = new LoyaltyAdapter(this, cupList);
        recyclerViewCups.setLayoutManager(new GridLayoutManager(this, 8));
        recyclerViewCups.setAdapter(adapter);


        //Show menu
        modellist= new ArrayList<>();
        modellist.add(new Model("Americano", getString(R.string.americano), R.drawable.americano));
        modellist.add(new Model("Cappuccino", getString(R.string.cappuccino), R.drawable.cappuccino));
        modellist.add(new Model("Mocha", getString(R.string.mocha), R.drawable.mocha));
        modellist.add(new Model("Latte", getString(R.string.latte), R.drawable.latte));

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        Adapter=new OrderAdapter(this, modellist);
        recyclerView.setAdapter(Adapter);


        ImageView prof=findViewById(R.id.profile);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        loyal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences numberofCup = getSharedPreferences("NumberCup", MODE_PRIVATE);
                SharedPreferences.Editor editn = numberofCup.edit();
                int num = numberofCup.getInt("num", 0);
                if (num == 8){
                    editn.putInt("num",0);
                    editn.apply();
                    List<Integer> cupList1 = new ArrayList<>();
                    for (int i = 0; i < 8; i++)
                        cupList1.add(R.drawable.cupnormal);
                    numberpernumber.setText("0/0");

                    adapter = new LoyaltyAdapter(MainActivity.this, cupList1);
                    recyclerViewCups.setLayoutManager(new GridLayoutManager(MainActivity.this, 8));
                    recyclerViewCups.setAdapter(adapter);
                }
                else editn.apply();
            }
        });


        cart=findViewById(R.id.cartMain);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, SummaryActivity.class);
                startActivity(intent1);
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.giftsicon:
                                Intent intent1 = new Intent(MainActivity.this, RewardsActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.ordericon:
                                Intent intent2 = new Intent(MainActivity.this, OrderScreenActivity.class);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                });

    }

}