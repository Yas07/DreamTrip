package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import layout.Fragment_packlist;
import layout.Fragment_plan;
import layout.Fragment_travelbook;

public class ActivityMytrips_trip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_mytrips_plan);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip_plan_add");
//                startActivity(intent);
//            }
//        });

    public void addPlanPoint(View view){
        if (view.getId() == R.id.fab_mytrips_plan){
        Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip_plan_add");
        startActivity(intent);}
    }


    public void changePlan(View view){
        View[] buttons = {
                findViewById(R.id.btnPlan1),
                findViewById(R.id.btnPacklist1),
                findViewById(R.id.btnTravelbook1)};
        for (View temp: buttons)
            temp.setBackgroundColor(getResources().getColor(R.color.black_overlay));
        view.setBackgroundColor(getResources().getColor(R.color.transparentGrey));

        Fragment fragment;
        fragment = new Fragment_plan();
        switch (view.getId()){
            case R.id.btnPlan1: {
                fragment = new Fragment_plan(); break;}
            case R.id.btnPacklist1: {
                fragment = new Fragment_packlist(); break;}
            case R.id.btnTravelbook1: {
                fragment = new Fragment_travelbook(); break;}
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentPlace, fragment);
        ft.commit();
    }
}
