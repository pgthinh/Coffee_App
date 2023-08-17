package com.example.myapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.annotation.SuppressLint;

import com.example.myapplication.Drink;
import com.example.myapplication.DrinkwithID;

import java.util.ArrayList;
import java.util.List;

public class OrderHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABSE_NAME = "ord.db";
    public static final  String CLAUSE ="OrderContract.OrderEntry._ID ";

    public OrderHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TABLE = "CREATE TABLE " + OrderContract.OrderEntry.TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderContract.OrderEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_QUANTITY + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_PRICE + " FLOAT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_UPSIZE + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_DOUBLESHOT + " TEXT NOT NULL);";

        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getPrice() {
        int count=0;
        String countQuery ="select sum(price) from orderig";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        while (cursor.moveToNext()){
            count += cursor.getFloat(0);
        }
        return count;
    }

    public List<Drink> getList(){
        ArrayList<Drink> list = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "select * from orderig";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            Drink ndrink = new Drink();
            ndrink.name = cursor.getString(1);
            ndrink.quantiy = cursor.getString(2);
            ndrink.price = cursor.getInt(3);
            ndrink.hasUpsize =cursor.getString(4);
            ndrink.hasDoubleshot = cursor.getString(5);
            list.add(ndrink);
        }
        return list;
    }

    public List<DrinkwithID> getListhasID(){
        ArrayList<DrinkwithID> list = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "select * from orderig";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            DrinkwithID ndrink = new DrinkwithID();
            ndrink.ID = cursor.getString(0);
            ndrink.name = cursor.getString(1);
            ndrink.quantiy = cursor.getString(2);
            ndrink.price = cursor.getInt(3);
            ndrink.hasUpsize =cursor.getString(4);
            ndrink.hasDoubleshot = cursor.getString(5);
            list.add(ndrink);
        }
        return list;
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OrderContract.OrderEntry.TABLE_NAME,
                OrderContract.OrderEntry._ID + " = ?",
                new String[]{id});
    }
}
