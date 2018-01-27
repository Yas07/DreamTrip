package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityTravelbooks_add extends AppCompatActivity {
Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_add);
        btn_save = (Button) findViewById(R.id.btn_travelbook_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook");
                startActivity(intent);
            }
        });
    }
}
