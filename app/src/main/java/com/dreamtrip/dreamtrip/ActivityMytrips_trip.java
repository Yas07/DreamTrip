package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import layout.Fragment_packlist;
import layout.Fragment_plan;
import layout.Fragment_travelbook;
import Trip_DBs.DB_Item;

public class ActivityMytrips_trip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if(bundle.getString("value")!= null) {
                Toast.makeText(this, bundle.getString("value"), Toast.LENGTH_SHORT).show();
            }
        }
    }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_mytrips_plan);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip_plan_add");
//                startActivity(intent);
//            }
//        });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarEdit: {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_add");
                startActivity(intent);
                break;
            }
            case R.id.toolbarDelete: {
                new AlertDialog.Builder(this)
                        .setTitle("Delete trip")
                        .setMessage("Do you want to delete your trip?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(ActivityMytrips_trip.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_");
                                //startActivity(intent);
                                //startActivity(new Intent(getBaseContext(),ActivityMytrips_.class).putExtra("value", "Successfully deleted"));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            }
        }
        return false;
    }

    public void addPlanPoint(View view){
        if (view.getId() == R.id.fab_mytrips_plan){
            Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip_plan_add");
            startActivity(intent);
        }
    }


    public void changeTab(View view){
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
