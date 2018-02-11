package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivityMytrips_ extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private String[] cardTitles = {"Lviv", "Chernivtsi", "Paris", "Egypt", "Disneyland", "Favourite trip"};
    private String[] cardDetails = {"06-08.01.18", "13-14.01.18", "02.05.18", "07.09.16", "22.06.19", "15.12.12"};
    private int[] cardImages = {R.drawable.city_lviv_dpi, R.drawable.city_chernivtsi_dpi, R.drawable.city_paris_dpi,
                                R.drawable.city_egypt_dpi, R.drawable.city_lviv_dpi, R.drawable.city_chernivtsi_dpi};

    private int colorBg =  Color.WHITE;
    private int colorText = Color.WHITE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.mytrips_,container,false);
        colorBg = getResources().getColor(R.color.mytrips_bg);
        colorText = getResources().getColor(R.color.mytrips_labels);

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_mytrips);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRecycler_GridCards(cardTitles,
                cardDetails, cardImages, colorBg, colorText);
        recyclerView.setAdapter(adapter);

        return myLayout;
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
    }
}
