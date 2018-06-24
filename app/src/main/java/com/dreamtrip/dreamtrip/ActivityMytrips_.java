package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import Trip_DBs.Trips_BD;

public class ActivityMytrips_ extends Fragment {

    // RECYCLE VIEW, MANAGER, ADAPTER FOR CARDS
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterRecycler_GridCards adapter;

    AutoCompleteTextView packSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.mytrips_,container,false);

        // INIT DATABASE
        //DatabaseHelper.initDatabaseHelper(getActivity());

        // INSERT DATA IN DB
        //Trip_ mytrip = new Trip_("Lviv", new Date(2018,1,13), new Date(2018,1,14), Color.RED);
        //DatabaseHelper.getInstance().insertTrip(mytrip);
        //DatabaseHelper.getInstance().insertTravelbook(mytrip.getTitle(), "image1", "mydetails1");
        //DatabaseHelper.getInstance().insertPacklist("winter stuff", "image2", "mydetails2");

        int colorBg =  Color.WHITE;
        int colorText = Color.WHITE;
        packSearch = (AutoCompleteTextView) myLayout.findViewById(R.id.autoCompleteTextView);
        packSearch.addTextChangedListener(createTextWatcher());

        // SETTING COLORS
        colorBg = getResources().getColor(R.color.mytrips_bg);
        colorText = getResources().getColor(R.color.mytrips_labels);

        // SHOWING CARDS
        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_mytrips);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRecycler_GridCards(colorBg, colorText, ActivityType.TRIPS);
        recyclerView.setAdapter(adapter);


        return myLayout;
    }


    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getCurrentActivityType().
                        setItems(Trips_BD.
                                getInstance().findAllSimilar(s.toString()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
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
//        Bundle bundle = getActivity().getIntent().getExtras();
//        if (bundle != null) {
//            if(bundle.getString("value")!= null) {
//                Toast.makeText(getActivity(), bundle.getString("value"), Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
