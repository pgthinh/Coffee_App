package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RewardsActivity extends AppCompatActivity {

    List<Drink> historyReward;
    RewardAdapter AdapterReward;
    RecyclerView recyclerViewReward;
    Button redeem;
    TextView point,numberpernumber,loyal;

    RecyclerView recyclerViewCups;
    List<Integer> cupList;
    LoyaltyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        historyReward = loadOrder("HistoryPoint");

        int pt = 0;
        for (int i =0; i < historyReward.size();i++) {
            Drink temp = historyReward.get(i);
            int temp2=Integer.parseInt(temp.quantiy);
            if(temp.price==0)
                pt = pt - 500;
            else
                pt = pt + 100*temp2;
        }
        point=findViewById(R.id.points);
        point.setText(String.valueOf(pt));

        numberpernumber=findViewById(R.id.numberofcups);
        recyclerViewCups = findViewById(R.id.recyclerViewCups);

        //get number of cup
        SharedPreferences number = getSharedPreferences("NumberCup", MODE_PRIVATE);
        SharedPreferences.Editor editnumber = number.edit();
        int numberCup = number.getInt("num", 0);
        //editnumber.clear().commit();
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

        recyclerViewReward=findViewById(R.id.recyclerViewReward);
        recyclerViewReward.setLayoutManager(new LinearLayoutManager(null));
        AdapterReward=new RewardAdapter(this, historyReward);
        recyclerViewReward.setAdapter(AdapterReward);

        redeem=findViewById(R.id.redeembutton);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RewardsActivity.this, RedeemActivity.class);
                startActivity(intent);
            }
        });

        //reset point
        loyal=findViewById(R.id.loyaltycard);
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

                    adapter = new LoyaltyAdapter(RewardsActivity.this, cupList1);
                    recyclerViewCups.setLayoutManager(new GridLayoutManager(RewardsActivity.this, 8));
                    recyclerViewCups.setAdapter(adapter);
                }
                else editn.apply();
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.navigationreward);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                onBackPressed();
                                break;
                            case R.id.ordericon:
                                Intent intent2 = new Intent(RewardsActivity.this, OrderScreenActivity.class);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                });
    }

    private List<Drink> loadOrder(String table){
        List<Drink> ls;
        SharedPreferences sharedPreferences = getSharedPreferences(table.toString(), MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Item", null);
        Type newtype = new TypeToken<List<Drink>>() {}.getType();
        ls = gson.fromJson(json, newtype);
        if(ls == null) ls = new ArrayList<Drink>();
        return ls;
    }

    private int getSum(List<Drink> modellistx){
        int cnt=0;
        for (int i=0; i < modellistx.size();i++){
            Drink tmp = modellistx.get(i);
            cnt += Integer.parseInt(tmp.getDrinkQuantity());
        }
        return cnt;
    }

}
