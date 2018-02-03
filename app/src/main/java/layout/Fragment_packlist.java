package layout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamtrip.dreamtrip.R;


public class Fragment_packlist extends Fragment implements View.OnClickListener{
   boolean isEditOpen = false;
    FloatingActionButton fab_packlist_add_stuff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myLayout = inflater.inflate(R.layout.packlists_packlist_fragment, container, false);
        fab_packlist_add_stuff = (FloatingActionButton) myLayout.findViewById(R.id.fab_packlist_add_stuff);
        fab_packlist_add_stuff.setOnClickListener(this);
        return myLayout;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_packlist_add_stuff:
                if (isEditOpen) {
                    fab_packlist_add_stuff.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_white_24px));
                    isEditOpen = false;
                } else {
                    fab_packlist_add_stuff.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white_24px));
                    isEditOpen = true;
                }
                break;
        }
    }
}
