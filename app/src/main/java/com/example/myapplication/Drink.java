package com.example.myapplication;

public class Drink {
    public String name;
    public  String quantiy;
    public  String hasUpsize;
    public String hasDoubleshot;
    public int price;
    public String getDrinkName(){
        return  name;
    }

    public String getDrinkQuantity(){
        return quantiy;
    }

    public String getHasUpsize(){return hasUpsize;}

    public  String getDoubleshot(){return hasDoubleshot;}

    public int getDrinkPrice(){return price;}
}

