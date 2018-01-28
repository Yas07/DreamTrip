package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
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

    public void chooseCover(View view){
        View[] buttons = {
                findViewById(R.id.travelbooks_add_btnChristmasBlack),
                findViewById(R.id.travelbooks_add_btnChristmasGreen),
                findViewById(R.id.travelbooks_add_btnChristmasWhite),
                findViewById(R.id.travelbooks_add_btnChristmasWhiteBlack),
                findViewById(R.id.travelbooks_add_btnChristmasWhiteRed),
                findViewById(R.id.travelbooks_add_btnCup),
                findViewById(R.id.travelbooks_add_btnDarkRed),
                findViewById(R.id.travelbooks_add_btnGreen),
                findViewById(R.id.travelbooks_add_btnLightBlue),
                findViewById(R.id.travelbooks_add_btnLightBlue2),
                findViewById(R.id.travelbooks_add_btnModern),
                findViewById(R.id.travelbooks_add_btnSummerFlamingo),
                findViewById(R.id.travelbooks_add_btnTravel),
                findViewById(R.id.travelbooks_add_btnTravelBrightBlue),
                findViewById(R.id.travelbooks_add_btnTravelOrange),
                findViewById(R.id.travelbooks_add_btnOrangeAbstract)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        view.setBackgroundColor(getResources().getColor(R.color.transparentDarkGreen));

    }
}
