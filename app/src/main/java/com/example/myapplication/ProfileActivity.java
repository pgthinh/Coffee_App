package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        EditText editname,editphone,editmail,editadrress;
        editname=findViewById(R.id.editname);
        editadrress=findViewById(R.id.editaddress);
        editmail=findViewById(R.id.editmail);
        editphone=findViewById(R.id.editnumber);

        TextView name,phone,mail,address;
        name=findViewById(R.id.name);
        phone=findViewById(R.id.number);
        mail=findViewById(R.id.mail);
        address=findViewById(R.id.address);

        ImageView changename,changeaddress,changemail,changephone;
        changeaddress=findViewById(R.id.changeaddress);
        changename=findViewById(R.id.changename);
        changemail=findViewById(R.id.changemail);
        changephone=findViewById(R.id.changephone);

        ImageView back=findViewById(R.id.backProfile);

        SharedPreferences sPreferencesPro = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sPreferencesPro.edit();

        name.setText(sPreferencesPro.getString("name","Pham Gia Thinh"));
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getVisibility()==View.VISIBLE){
                    editname.setVisibility(View.VISIBLE);
                    name.setVisibility(View.INVISIBLE);
                }
                else{
                    editname.setVisibility((View.INVISIBLE));
                    name.setText(editname.getText().toString());
                    name.setVisibility(View.VISIBLE);
                }

            }
        });

        phone.setText(sPreferencesPro.getString("phone","0091345112"));
        changephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getVisibility()==View.VISIBLE){
                    editphone.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.INVISIBLE);
                }
                else{
                    editphone.setVisibility((View.INVISIBLE));
                    phone.setText(editphone.getText().toString());
                    phone.setVisibility(View.VISIBLE);
                }
            }
        });

        mail.setText(sPreferencesPro.getString("mail", "abc@gmail.com"));
        changemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mail.getVisibility()==View.VISIBLE){
                    editmail.setVisibility(View.VISIBLE);
                    mail.setVisibility(View.INVISIBLE);
                }
                else{
                    editmail.setVisibility((View.INVISIBLE));
                    mail.setText(editmail.getText().toString());
                    mail.setVisibility(View.VISIBLE);
                }
            }
        });

        address.setText(sPreferencesPro.getString("address","227, Nguyen Van Cu, District 5, Ho Chi Minh City"));
        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getVisibility()==View.VISIBLE){
                    editadrress.setVisibility(View.VISIBLE);
                    address.setVisibility(View.INVISIBLE);
                }
                else{
                    editadrress.setVisibility((View.INVISIBLE));
                    address.setText(editadrress.getText().toString());
                    address.setVisibility(View.VISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("name", name.getText().toString());
                edit.putString("phone", phone.getText().toString());
                edit.putString("mail",mail.getText().toString());
                edit.putString("address",address.getText().toString());
                edit.apply();
                Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

}