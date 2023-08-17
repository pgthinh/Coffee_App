package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.OrderContract;
import com.example.myapplication.Database.OrderHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity{
    public CartAdapter mAdapter;
    public static final int LOADER = 0;
    ImageView backMain;
    public OrderHelper db;
    List<DrinkwithID> modelist;
    RecyclerView recyclerView;
    CartAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        modelist = new ArrayList<DrinkwithID>();
        recyclerView=findViewById(R.id.list);
        TextView price = findViewById(R.id.wholeprice);
        Button check=findViewById(R.id.checkout);
        backMain=findViewById(R.id.backSummary);
        db = new OrderHelper(this);
        modelist = db.getListhasID();

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHelper db=new OrderHelper(SummaryActivity.this);
                List<Drink> ls = db.getList();
                SharedPreferences sPreferences = getSharedPreferences("OnGoing", MODE_PRIVATE);
                SharedPreferences.Editor edit = sPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(ls);
                edit.putString("Item", json);
                edit.apply();
                int deletethedata = getContentResolver().delete(OrderContract.OrderEntry.CONTENT_URI, null, null);
                Intent intent = new Intent(SummaryActivity.this, OrderSuccess.class);
                startActivity(intent);
            }
        });



        price.setText(String.valueOf(db.getPrice()+ " VND"));
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        Adapter=new CartAdapter(SummaryActivity.this, modelist);
        recyclerView.setAdapter(Adapter);

        SwipedToDelete();
    }

    public ItemTouchHelper.SimpleCallback setSwipedToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(SummaryActivity.this, "Moving", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(SummaryActivity.this, "Item deleted Successfully", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                db.delete(modelist.get(position).getID());
                recreate();
            }
        };
        return simpleItemTouchCallback;
    }

    private void SwipedToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = setSwipedToDelete();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}