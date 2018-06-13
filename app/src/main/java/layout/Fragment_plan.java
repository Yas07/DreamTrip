package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamtrip.dreamtrip.ActivityMytrips_trip_plan_add;
import com.dreamtrip.dreamtrip.R;
import com.dreamtrip.dreamtrip.AdapterRecycler_Plan;

import Trip_Items.Trips_trip;

public class Fragment_plan extends Fragment  implements View.OnClickListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

//    public Fragment_plan(){
//       super();

//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.mytrips_trip_plan, container, false);

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterRecycler_Plan(getActivity());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) myLayout.findViewById(R.id.fab_mytrips_plan);
        fab.setOnClickListener(this);

        return myLayout;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_mytrips_plan:{

                Trips_trip trip = Trips_trip.getCurrentTrip();
                Intent intent;
                if(trip == null) {
                    intent = new Intent(getActivity().getBaseContext(), ActivityMytrips_trip_plan_add.class);
                    Log.e("Fragment_plan", "there is no current trip");
                } else {
                    intent = new Intent(getActivity().getBaseContext(), ActivityMytrips_trip_plan_add.class).putExtras(trip.getBundle());
                }
                startActivity(intent);

                break;
            }


        }
    }

}
