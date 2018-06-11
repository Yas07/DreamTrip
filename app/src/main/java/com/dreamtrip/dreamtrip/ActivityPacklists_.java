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
import android.widget.Toast;

import java.util.ArrayList;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;

public class ActivityPacklists_ extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private int colorBg = Color.WHITE;
    private int colorText = Color.BLACK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.packlists_,container,false);
        colorBg = getResources().getColor(R.color.packlists_bg);
        colorText = getResources().getColor(R.color.packlists_labels);

        ArrayList<Packlist> packlists = new ArrayList<Packlist> (PacklistsDB.getInstance().values());

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_packlists);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRecycler_GridCards(packlists, colorBg, colorText, enum_ACTIVITY_TYPE.PACKLISTS);
        recyclerView.setAdapter(adapter);
        return myLayout;
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