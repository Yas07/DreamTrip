package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import Trip_DBs.Trips_BD;
import Trip_Items.IDelEdit;
import Trip_Items.Trips_trip;
import layout.Fragment_packlist;
import layout.Fragment_plan;
import layout.Fragment_travelbook;
import Trip_DBs.DB_Item;

public class ActivityMytrips_trip extends AppCompatActivity{

    private Trips_trip tripCtx;

    private int currentFragId = 0;

    private String savePackListStr = "Save packlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mytrips_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (currentFragId == R.id.btnPacklist1) {
            toolbar.getMenu().add(savePackListStr);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        tripCtx = Trips_BD.getInstance().findByBundle(bundle);

        if (tripCtx == null) {
            Log.e("Activity_trip", "Cannot resolve trip");
            tripCtx = Trips_trip.getCurrentTrip();
            if (tripCtx == null) {
                Log.e("Activity_trip", "No current trip");
                return;
            }
        }

        TextView tripTitle = (TextView) findViewById(R.id.trip_name);
        TextView tripDate = (TextView) findViewById(R.id.trip_date);
        LinearLayout head = (LinearLayout) findViewById(R.id.header_trip);

        Bitmap bit = tripCtx.getHeaderImage();
        if (bit != null) {
            head.setBackground(new BitmapDrawable(getResources(), bit));
        }


        tripTitle.setText(tripCtx.getName());
        tripDate.setText(tripCtx.startEndDateToStr());

        tripTitle.setTextColor(tripCtx.getTextColor());
        tripDate.setTextColor(tripCtx.getTextColor());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarEdit: {
                Intent intent = new Intent(this, ActivityMytrips_add.class).
                        putExtras(Trips_trip.getEditBundle());
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
                                Trips_trip trip = Trips_trip.getCurrentTrip();
                                if (trip != null) {
                                    Trips_BD.getInstance().remove(trip);
                                    Toast.makeText(ActivityMytrips_trip.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(getBaseContext(),MainActivity.class).putExtra("value", "Successfully deleted");
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            }
            default:
                if(item.getTitle() != null && item.getTitle().equals(savePackListStr)) {
                    Log.e("save packlist", "cool");
                }
        }
        return false;
    }


    private void savePacklistDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Save trip")
                .setMessage("Do you want to delete your trip?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Trips_trip trip = Trips_trip.getCurrentTrip();
                        if (trip != null) {
                            Trips_BD.getInstance().remove(trip);
                            Toast.makeText(ActivityMytrips_trip.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(getBaseContext(),MainActivity.class).putExtra("value", "Successfully deleted");
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

//    private void addPlanPoint(View view){
//        if (view.getId() == R.id.fab_mytrips_plan){
//            Intent intent = new Intent(getBaseContext(), ActivityMytrips_trip_plan_add.class).putExtras(tripCtx.getBundle());
//            startActivity(intent);
//        }
//    }

//    public void changeTab(FragmentType fragmentType) {
//        View view;
//        switch (fragmentType) {
//            case TRIP:
//                view = findViewById(R.id.btnPlan1);
//                changeTab();
//                break;
//            case TRAVEL_BOOK:
//                break;
//            case PACKLIST:
//                break;
//            default:
//                Log.i("changeTab", "No tab to change by frag type");
//        }
//    }


    public void changeTab(View view){
        Fragment fragment;
        currentFragId = view.getId();
        switch (view.getId()){
            case R.id.btnPlan1:
                fragment = new Fragment_plan(); break;
            case R.id.btnPacklist1:
                if (tripCtx.getPacklist() == null) {
                    Log.e("Changetab","There is no packlist available");

                    return;
                }
                fragment = new Fragment_packlist(); break;
            case R.id.btnTravelbook1:
                if (tripCtx.getTravelbook() == null) {
                    Log.e("Changetab","There is no travelBook available");
                    return;
                }
                fragment = new Fragment_travelbook();
                break;
            default:
                Log.e("changeTab", "No such fragment");
                return;
        }

        View[] buttons = {
                findViewById(R.id.btnPlan1),
                findViewById(R.id.btnPacklist1),
                findViewById(R.id.btnTravelbook1)};
        for (View temp: buttons)
            temp.setBackgroundColor(getResources().getColor(R.color.black_overlay));
        view.setBackgroundColor(getResources().getColor(R.color.transparentGrey));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentPlace, fragment);
        ft.commit();
    }

}
