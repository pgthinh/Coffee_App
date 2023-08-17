package com.example.myapplication;

public class Model {
    String DrinkName;
    String DrinkDetail;
    int DrinkPhoto;

    public Model(String DrinkName, String DrinkDetail, int DrinkPhoto){
        this.DrinkName = DrinkName;
        this.DrinkDetail = DrinkDetail;
        this.DrinkPhoto = DrinkPhoto;
    }
    public String getDrinkName(){
        return  DrinkName;
    }

    public String getDrinkDetail(){
        return DrinkDetail;
    }

    public int getDrinkPhoto(){
        return DrinkPhoto;
    }
}
