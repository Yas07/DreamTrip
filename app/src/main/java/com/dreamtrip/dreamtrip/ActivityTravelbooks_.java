package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;

public class ActivityTravelbooks_ extends Fragment{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

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

        ArrayList<TravelBook> travelBooks = new ArrayList<TravelBook>(TravelBooksDB.getInstance().values());

        adapter = new AdapterRecycler_GridCards(colorBg, colorText, ActivityType.TRAVELBOOKS);
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
        //registerForContextMenu(AdapterRecycler_GridCards);
    }
}