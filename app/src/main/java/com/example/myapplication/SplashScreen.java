package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // Splash screen delay in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Delay the start of the MainActivity using a Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity after the splash delay
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Finish the splash activity so the user cannot navigate back to it
            }
        }, SPLASH_DELAY);
    }
}
