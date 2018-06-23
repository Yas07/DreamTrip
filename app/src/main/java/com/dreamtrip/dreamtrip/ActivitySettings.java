package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ActivitySettings extends AppCompatActivity {
    ImageButton btnDelAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDelAll = (ImageButton) findViewById(R.id.btnDelAll);
        btnDelAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDialog(v);
            }
        });
    }

    public void openDialog(View view){
        new AlertDialog.Builder(this)
                .setTitle("Delete all data")
                .setMessage("Do you want to delete all your data in app?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //TODO: delete all data in app
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

}
