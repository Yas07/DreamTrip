package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startFragmentByActivityType(ActivityType.bundleToActivityType(getIntent().getExtras()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent layouts.activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFragmentByActivityType(ActivityType activityType) {
        switch (activityType) {
            case TRIPS:
                startActivityById(R.id.nav_mytrips);
                break;
            case TRAVELBOOKS:
                startActivityById(R.id.nav_travelbook);
                break;
            case PACKLISTS:
                startActivityById(R.id.nav_packlist);
                break;
            default:
                Log.e("MainActivity","wrong fragment type!");
        }
    }

    private void startActivityById(int id) {

        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_mytrips) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ActivityMytrips_()).commit();
        } else if (id == R.id.nav_travelbook) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ActivityTravelbooks_()).commit();
        } else if (id == R.id.nav_packlist) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ActivityPacklists_()).commit();
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivitySettings");
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityHelp");
            startActivity(intent);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item == null){
            Log.e("MainActivity", "null item");
            return true;
        }

        // Handle navigation view item clicks here.
        startActivityById(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
