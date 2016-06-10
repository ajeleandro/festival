package com.artefacto1971.festival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends Activity{

    private static int SPLASH_TIME_OUT = 1200;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);

	    //getBaseContext().deleteDatabase("festival.db"); // BORRA LA BASE DE DATOS
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {            	
            	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}