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

public class ActivityTravelbooks_ extends Fragment{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private String[] cardTitles = {"Lviv album", "Chernivtsi album", "Paris album", "Egypt album",
                                    "Disneyland album", "Favourite trip album"};
    private String[] cardDetails = {"06-08.01.18", "13-14.01.18", "02.05.18", "07.09.16", "22.06.19", "15.12.12"};
    private int[] cardImages = {R.drawable.travelbook_cover_christmas_black_dpi, R.drawable.travelbook_cover_cup_dpi,
            R.drawable.travelbook_cover_travel_blue_dpi, R.drawable.travelbook_cover_light_summer_dpi,
            R.drawable.travelbook_cover_modern_black_dpi, R.drawable.travelbook_cover_world_travel_dpi};
    private int colorBg = Color.parseColor("#B0F3DCE7");
    private int colorText = Color.BLACK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.travelbooks_,container,false);
        colorBg = getResources().getColor(R.color.travelbooks_bg);
        colorText = getResources().getColor(R.color.travelbooks_labels);

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_travelbooks);
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
        FloatingActionButton fab_travelbooks_add = (FloatingActionButton) view.findViewById(R.id.fab_travelbooks_add);
        fab_travelbooks_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_add");
                startActivity(intent);
            }
        });
    }
}