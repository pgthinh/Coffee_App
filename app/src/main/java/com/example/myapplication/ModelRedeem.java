package com.example.myapplication;

public class ModelRedeem {
    String DrinkName;
    String DrinkDate;
    int DrinkPhoto;
    int DrinkPoints;

    public ModelRedeem(String DrinkName, String date, int DrinkPhoto, int point){
        this.DrinkName = DrinkName;
        this.DrinkDate = date;
        this.DrinkPhoto = DrinkPhoto;
        this.DrinkPoints= point;
    }
    public String getDrinkName(){
        return  DrinkName;
    }

    public String getDrinkDate(){
        return DrinkDate;
    }

    public int getDrinkPhoto(){
        return DrinkPhoto;
    }

    public int getDrinkPoints(){return DrinkPoints;}
}
