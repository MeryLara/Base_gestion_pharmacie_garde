package com.example.gestionpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScrene extends AppCompatActivity {

  //  private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screne);

        new Handler().postDelayed(new Runnable()
        {
            @Override public void run()
            {
                Intent i = new Intent(SplashScrene.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 2500);
    }

}
