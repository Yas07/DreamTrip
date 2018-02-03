package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamtrip.dreamtrip.R;
import com.dreamtrip.dreamtrip.Travelbook_swipe;


public class Fragment_travelbook extends Fragment implements View.OnClickListener{
    ViewPager viewPager;
    Travelbook_swipe adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myLayout = inflater.inflate(R.layout.travelbooks_travelbook_fragment, container, false);
        FloatingActionButton fab_travelbook_add_post = (FloatingActionButton) myLayout.findViewById(R.id.fab_travelbook_add_post);
        fab_travelbook_add_post.setOnClickListener(this);

        viewPager = (ViewPager) myLayout.findViewById(R.id.view_pager);
        adapter = new Travelbook_swipe(getActivity());
        viewPager.setAdapter(adapter);

        return myLayout;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fab_travelbook_add_post:
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook_add");
                startActivity(intent);
                break;
        }
    }
}