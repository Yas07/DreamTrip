package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamtrip.dreamtrip.R;


public class Fragment_packlist extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myLayout = inflater.inflate(R.layout.packlists_packlist_fragment, container, false);
        FloatingActionButton fab_packlist_add_stuff = (FloatingActionButton) myLayout.findViewById(R.id.fab_packlist_add_stuff);
        fab_packlist_add_stuff.setOnClickListener(this);
        return myLayout;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fab_packlist_add_stuff:
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_packlist_add");
                startActivity(intent);
                break;
        }
    }
//    public void addPacklistStuff(View view){
//        if (view.getId() == R.id.fab_packlist_add_stuff){
//            Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_packlist_add");
//            startActivity(intent);}
//    }
}
