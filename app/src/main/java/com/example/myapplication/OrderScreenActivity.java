package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.OrderHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

public class OrderScreenActivity extends AppCompatActivity {
    OrderInfoAdapter Adapter;
    RecyclerView recyclerView;
    List<Drink> modellist1,history,histtorypoint;
    Button done;
    TextView ongoing, hist;
    ImageView backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        modellist1 = loadOrder("OnGoing");
        recyclerView=findViewById(R.id.recyclerViewmyOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        Adapter=new OrderInfoAdapter(this, modellist1);
        recyclerView.setAdapter(Adapter);


        done=findViewById(R.id.done);
        hist=findViewById(R.id.history);
        backHome=findViewById(R.id.backmyOrder);
        ongoing=findViewById(R.id.ongoingbold);


        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modellist1.size() > 0) {
                    history = loadOrder("History");
                    for (int i = 0; i < modellist1.size(); i++) {
                        Drink temp = modellist1.get(i);
                        if (temp.quantiy != "0")
                            history.add(modellist1.get(i));
                    }

                    hist.setTypeface(null, Typeface.BOLD);
                    ongoing.setTypeface(null, Typeface.NORMAL);

                    //Update history
                    SharedPreferences sPreferenceshis = getSharedPreferences("History", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sPreferenceshis.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(history);
                    edit.putString("Item", json);
                    edit.apply();

                    //sPreferenceshis.edit().clear().commit();
                    histtorypoint = loadOrder("HistoryPoint");
                    if (histtorypoint.size() == 0)
                        histtorypoint = new ArrayList<>();
                    for (int i = 0; i < modellist1.size(); i++) {
                        Drink temp = modellist1.get(i);
                        if ((temp.price == 0)) {
                        } else histtorypoint.add(modellist1.get(i));
                    }


                    //Update history for changing point
                    SharedPreferences sharedPreferenceshistorypoint = getSharedPreferences("HistoryPoint", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferenceshistorypoint.edit();
                    Gson gson1 = new Gson();
                    String json1 = gson1.toJson(histtorypoint);
                    editor.putString("Item", json1);
                    editor.apply();
                    //sharedPreferenceshistorypoint.edit().clear().commit();


                    //Update number of cup
                    SharedPreferences number = getSharedPreferences("NumberCup", MODE_PRIVATE);
                    SharedPreferences.Editor editnumber = number.edit();
                    int numcart = number.getInt("num", 0);
                    int extracart = Math.min(8 - numcart, getSum(modellist1));
                    editnumber.putInt("num", numcart + extracart);
                    editnumber.apply();


                    //Delete On Going
                    SharedPreferences sPreferenceson = getSharedPreferences("OnGoing", MODE_PRIVATE);
                    sPreferenceson.edit().clear().commit();
                    modellist1.clear();


                    recyclerView.setLayoutManager(new LinearLayoutManager(null));
                    Adapter = new OrderInfoAdapter(OrderScreenActivity.this, history);
                    recyclerView.setAdapter(Adapter);

                }
                else
                    Toast.makeText(OrderScreenActivity.this,"Everything is done", Toast.LENGTH_SHORT).show();
            };
        });

        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = loadOrder("History");
                SharedPreferences sPreferences = getSharedPreferences("History", MODE_PRIVATE);
                SharedPreferences.Editor edit = sPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(history);
                edit.putString("Item", json);
                edit.apply();

                hist.setTypeface(null, Typeface.BOLD);
                ongoing.setTypeface(null,Typeface.NORMAL);

                recyclerView.setLayoutManager(new LinearLayoutManager(null));
                Adapter=new OrderInfoAdapter(OrderScreenActivity.this, history);
                recyclerView.setAdapter(Adapter);

            }
        });

        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hist.setTypeface(null, Typeface.NORMAL);
                ongoing.setTypeface(null,Typeface.BOLD);

                recyclerView.setLayoutManager(new LinearLayoutManager(null));
                Adapter=new OrderInfoAdapter(OrderScreenActivity.this, modellist1);
                recyclerView.setAdapter(Adapter);
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