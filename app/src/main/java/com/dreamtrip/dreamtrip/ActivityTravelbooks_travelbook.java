package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import Trip_Items.TravelBooks.TravelBook;

public class ActivityTravelbooks_travelbook extends AppCompatActivity {

    TravelBook travelBookCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_travelbook);

        travelBookCtx = TravelBook.getCurrentTravelBook();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if(bundle.getString("value")!= null){
                Toast.makeText(this, bundle.getString("value"), Toast.LENGTH_SHORT).show();
            }
        }



    }

}
