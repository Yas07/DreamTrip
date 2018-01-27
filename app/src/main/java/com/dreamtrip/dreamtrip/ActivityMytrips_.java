package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

public class ActivityMytrips_ extends Fragment {
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.mytrips_,container,false);

        return myview;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //((Activity) getContext()).getWindow().setBackgroundDrawableResource(R.drawable.gradient_blue1);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab_trip = (FloatingActionButton) view.findViewById(R.id.fab_trip);
        fab_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_add");
                startActivity(intent);
            }
        });

        //view.findViewById(R.id.fab_trip).setOnClickListener(this);

        // or
        //getActivity().findViewById(R.id.fab_trip).setOnClickListener(this);
    }
}
