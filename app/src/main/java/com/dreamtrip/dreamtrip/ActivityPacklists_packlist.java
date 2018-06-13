package com.dreamtrip.dreamtrip;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



public class ActivityPacklists_packlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlists_packlist);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarEdit: {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_add");
                startActivity(intent);
                break;
            }
            case R.id.toolbarDelete: {
                new AlertDialog.Builder(this)
                        .setTitle("Delete packing list")
                        .setMessage("Do you want to delete your packing list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(ActivityPacklists_packlist.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                //TODO: delete packlist and show message
                                //Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_");
                                //startActivity(intent);
                                //startActivity(new Intent(getBaseContext(),ActivityPacklists_.class).putExtra("value", "Successfully deleted"));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            }
        }
        return false;
    }
}

