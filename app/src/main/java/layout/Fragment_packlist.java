package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dreamtrip.dreamtrip.AdapterRecycler_Packlist;
import com.dreamtrip.dreamtrip.R;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.Stuff;


public class Fragment_packlist extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    FloatingActionButton fab_packlist_add_stuff;
    boolean isEditOpen = false;
    String newStuffTitle = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myLayout = inflater.inflate(R.layout.packlists_packlist_fragment, container, false);

        recyclerView = (RecyclerView) myLayout.findViewById(R.id.recycler_view_packlist);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRecycler_Packlist(getActivity()); //edit is not opened yet
        recyclerView.setAdapter(adapter);

        fab_packlist_add_stuff = (FloatingActionButton) myLayout.findViewById(R.id.fab_packlist_add_stuff);
        fab_packlist_add_stuff.setOnClickListener(this);
        return myLayout;
    }

    //create dialog to add new stuff with user's input
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_packlist_add_stuff:{

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add new element");

                // Set up the input
                final EditText input = new EditText(getActivity());

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newStuffTitle = input.getText().toString();
                        Packlist pack =  Packlist.getCurrentPacklist();
                        pack.add(new Stuff(newStuffTitle));
                        adapter.notifyItemInserted(pack.size());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }


        }
    }
}
