package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

public class ActivityPacklists_ extends Fragment {
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.packlists_,container,false);
        return myview;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab_packlists_add = (FloatingActionButton) view.findViewById(R.id.fab_packlists_add);
        fab_packlists_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_add");
                startActivity(intent);
            }
        });
    }
}