package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class ActivityStart extends AppCompatActivity {
    private static int SPLASCH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent main = new Intent(ActivityStart.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        }, SPLASCH_TIME_OUT);
    }
}
