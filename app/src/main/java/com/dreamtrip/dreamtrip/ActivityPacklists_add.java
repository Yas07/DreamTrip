package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPacklists_add extends AppCompatActivity {
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlists_add);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_packlist");
                startActivity(intent);
            }
        });
    }

    public void chooseBag(View view){
        View[] buttons = {
                findViewById(R.id.packlists_add_btnAdventure),
                findViewById(R.id.packlists_add_btnBirds),
                findViewById(R.id.packlists_add_btnChildren),
                findViewById(R.id.packlists_add_btnFestival),
                findViewById(R.id.packlists_add_btnFrance1),
                findViewById(R.id.packlists_add_btnFrance2),
                findViewById(R.id.packlists_add_btnGreece),
                findViewById(R.id.packlists_add_btnLondon1),
                findViewById(R.id.packlists_add_btnLondon2),
                findViewById(R.id.packlists_add_btnLondon3),
                findViewById(R.id.packlists_add_btnMelons),
                findViewById(R.id.packlists_add_btnSummer),
                findViewById(R.id.packlists_add_btnTravel),
                findViewById(R.id.packlists_add_btnTravel2),
                findViewById(R.id.packlists_add_btnTwopeople),
                findViewById(R.id.packlists_add_btnWinter1),
                findViewById(R.id.packlists_add_btnWinter2)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        view.setBackgroundColor(getResources().getColor(R.color.transparentDarkBrown));

    }
}
