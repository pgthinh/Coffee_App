package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.myapplication.Database.OrderContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RedeemActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    TextView n1, n2, n3, n4;
    ImageView btnBack;
    public Uri mCurrentCartUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        btn1=findViewById(R.id.pointsRedeem1);
        btn2=findViewById(R.id.pointsRedeem2);
        btn3=findViewById(R.id.pointsRedeem3);
        btn4=findViewById(R.id.pointsRedeem4);
        n1=findViewById(R.id.coffeeNameredeem1);
        n2=findViewById(R.id.coffeeNameredeem2);
        n3=findViewById(R.id.coffeeNameredeem3);
        n4=findViewById(R.id.coffeeNameredeem4);
        btnBack=findViewById(R.id.backredeem);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChangePoints(n1.getText().toString())){
                    Intent intent = new Intent(RedeemActivity.this, SummaryActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChangePoints(n2.getText().toString())){
                    Intent intent = new Intent(RedeemActivity.this, SummaryActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChangePoints(n3.getText().toString())){
                    Intent intent = new Intent(RedeemActivity.this, SummaryActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChangePoints(n4.getText().toString())){
                    Intent intent = new Intent(RedeemActivity.this, SummaryActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn1.setText("500 pts");
        n1.setText("Americano");
        btn2.setText("500 pts");
        n2.setText("Cappuccino");
        btn3.setText("500 pts");
        n3.setText("Mocha");
        btn4.setText("500 pts");
        n4.setText("Latte");
    }

    private boolean ChangePoints(String name){
        boolean check=false;

        float price = 0;
        String quantity = "1";

        List<Drink> historyPoint = loadOrder("HistoryPoint");
        int point = 0;
        for (int i =0; i < historyPoint.size();i++) {
            Drink temp = historyPoint.get(i);
            int temp2=Integer.parseInt(temp.quantiy);
            if(temp.price==0)
                point = point - 500;
            else
                point = point + 100*temp2;
        }

        if (point >= 500) {
            //Toast.makeText(this, "Success and already in your Cart", Toast.LENGTH_SHORT).show();

            ContentValues values = new ContentValues();
            values.put(OrderContract.OrderEntry.COLUMN_NAME, name.toString());
            values.put(OrderContract.OrderEntry.COLUMN_PRICE, 0);
            values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, "1");
            values.put(OrderContract.OrderEntry.COLUMN_DOUBLESHOT, "Double shot: YES");
            values.put(OrderContract.OrderEntry.COLUMN_UPSIZE, "Upsize: YES");

            Drink temp = new Drink();
            temp.name = name;
            temp.quantiy = "0";
            temp.hasDoubleshot = "";
            temp.hasUpsize = "";
            temp.price = 0;
            historyPoint.add(temp);

            SharedPreferences sPreferenceshis = getSharedPreferences("HistoryPoint", MODE_PRIVATE);
            SharedPreferences.Editor edit = sPreferenceshis.edit();
            Gson gson = new Gson();
            String json = gson.toJson(historyPoint);
            edit.putString("Item", json);
            edit.apply();
            check = true;
            if (mCurrentCartUri == null) {
                Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, values);
                if (newUri == null) {
                    Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show();

                }
            }
        }
        else{
            Toast.makeText(RedeemActivity.this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        }
        return check;
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
}
