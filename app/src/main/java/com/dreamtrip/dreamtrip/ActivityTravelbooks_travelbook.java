package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import Trip_DBs.Trips_BD;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if(bundle.getString("value")!= null){
                //Toast.makeText(this, bundle.getString("value"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class).
                putExtras(ActivityType.
                        activityTypeToBundle(ActivityType.TRAVELBOOKS)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarEdit: {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_add").
                        putExtras(Trips_trip.getEditBundle());
                startActivity(intent);
                break;
            }
            case R.id.toolbarDelete: {
                new AlertDialog.Builder(this)
                        .setTitle("Delete travelbook")
                        .setMessage("Do you want to delete your travelbook?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                Trips_BD.getInstance().remove(trip);
                                if (travelBookCtx == null) {
                                    Log.e("delete travelbook" , "travelBookCtx==null");
                                    return;
                                }
                                TravelBooksDB.getInstance().remove(travelBookCtx.getName());
                                Toast.makeText(ActivityTravelbooks_travelbook.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            }
        }
        return false;
    }

}
