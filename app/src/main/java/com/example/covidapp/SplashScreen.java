package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {
    private int time = 1000;
    String profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    SplashScreen.this.finish();
                    Intent main = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(main);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }, time);
    }
}
