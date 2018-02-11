package com.dreamtrip.dreamtrip;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MENEDGERP36 on 11.02.2018.
 */

public class AdapterRecycler_Packlist extends RecyclerView.Adapter<AdapterRecycler_Packlist.ViewHolder>{

    private String[] groupsTitles = {"Main",
            "Main",
            "Main",
            "Clothes",
            "Clothes",
            "Clothes",
            "Other",
            "Other"};

    private String[] itemTitles = {"Phone",
            "Passport",
            "Tickets",
            "Dress",
            "Skirts",
            "Shirts",
            "Phone charger",
            "Selfi stick"};

    private String packlistTitle = "Summer stuff";

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView textPacklist;
        public TextView textGroup;
        public CheckBox itemCheckbox;
        public LinearLayout layoutGroup;
        public LinearLayout layoutGroupAdd;
        public LinearLayout layoutItemAdd;


        public ViewHolder(View itemView) {
            super(itemView);
            textPacklist = (TextView)itemView.findViewById(R.id.packlistTitleText);
            textGroup = (TextView)itemView.findViewById(R.id.packlistEditGroupTitle);
            itemCheckbox = (CheckBox)itemView.findViewById(R.id.packlistCheckbox);
            layoutGroup = (LinearLayout)itemView.findViewById(R.id.packlistLayoutGroup);
            layoutGroupAdd = (LinearLayout) itemView.findViewById(R.id.packlistLayoutGroupAdd);
            layoutItemAdd = (LinearLayout) itemView.findViewById(R.id.packlistLayoutItemAdd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public AdapterRecycler_Packlist.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.packlists_packlist_card, viewGroup, false);
        AdapterRecycler_Packlist.ViewHolder viewHolder = new AdapterRecycler_Packlist.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler_Packlist.ViewHolder viewHolder, int i) {

        if (i == 0){           // if this is first card
            viewHolder.textPacklist.setText(packlistTitle);       //set packlist title
            viewHolder.textPacklist.setVisibility(View.VISIBLE); // display packlist title
            viewHolder.textGroup.setText(groupsTitles[i]);      //set group title
        }
        else {              //if this is not first card
            if (!groupsTitles[i].equals(groupsTitles[i-1]))     //if we have unique (new) group
                viewHolder.textGroup.setText(groupsTitles[i]); //set group title
            else                                                //if we have the same group
                viewHolder.layoutGroup.setVisibility(View.GONE); //hide group layout

            if (i == itemTitles.length-1) {                             //if this is the last element
                viewHolder.layoutItemAdd.setVisibility(View.VISIBLE); //show layout to add item
                viewHolder.layoutGroupAdd.setVisibility(View.VISIBLE); //show layout to add group
            }
            else
                if (!groupsTitles[i].equals(groupsTitles[i+1]))      //if this is the last element in this group
                    viewHolder.layoutItemAdd.setVisibility(View.VISIBLE); //show layout to add item

        }
        viewHolder.itemCheckbox.setText(itemTitles[i]); // anyway set checkbox (stuff) item
    }

    @Override
    public int getItemCount() {
        return itemTitles.length;
    }

}
