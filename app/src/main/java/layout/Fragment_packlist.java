package layout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dreamtrip.dreamtrip.AdapterRecycler_Packlist;
import com.dreamtrip.dreamtrip.R;


public class Fragment_packlist extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    FloatingActionButton fab_packlist_add_stuff;
    boolean isEditOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myLayout = inflater.inflate(R.layout.packlists_packlist_fragment, container, false);

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_packlist);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRecycler_Packlist(isEditOpen); //edit is not opened yet
        recyclerView.setAdapter(adapter);

        fab_packlist_add_stuff = (FloatingActionButton) myLayout.findViewById(R.id.fab_packlist_add_stuff);
        fab_packlist_add_stuff.setOnClickListener(this);
        return myLayout;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_packlist_add_stuff:{
                // change icon
                if (isEditOpen) {
                    fab_packlist_add_stuff.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_white_24px));
                } else {
                    fab_packlist_add_stuff.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white_24px));
                }

                isEditOpen = !isEditOpen; // change state

                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new AdapterRecycler_Packlist(isEditOpen);
                recyclerView.setAdapter(adapter);
                break;
            }


        }
    }
}
