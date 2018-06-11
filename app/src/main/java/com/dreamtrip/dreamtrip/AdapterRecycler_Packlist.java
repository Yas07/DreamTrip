package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.Stuff;

public class AdapterRecycler_Packlist extends RecyclerView.Adapter<AdapterRecycler_Packlist.ViewHolder>{

    Packlist currentPackList;

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

    private String packlistTitle = "Title";
    private boolean isEditOpen = false;
    private Packlist currentPacklist;
    private Context context;

    public AdapterRecycler_Packlist(Context context){
        //this.isEditOpen = isEditOpen;
        this.context = context;
        currentPackList = Packlist.getCurrentPacklist();
        if (currentPackList == null) {
           Log.e("adapter packlist", "no current packlist!!");
           return;
        }
        packlistTitle = currentPackList.getName();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView textPacklist;
        public CheckBox itemCheckbox;
        public ImageButton checkboxDel;
        public ImageButton checkboxEdit;
        public LinearLayout layoutItemAdd;




        public ViewHolder(View itemView) {
            super(itemView);
            textPacklist = (TextView)itemView.findViewById(R.id.packlistTitleText);
            itemCheckbox = (CheckBox)itemView.findViewById(R.id.packlistCheckbox);
            checkboxDel = (ImageButton) itemView.findViewById(R.id.packlistCheckboxDel);
            checkboxEdit = (ImageButton) itemView.findViewById(R.id.packlistCheckboxEdit);
            checkboxEdit.setTag(this);


            checkboxEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            onCheckBoxEdit(v, getAdapterPosition());

                        }
            });

            checkboxDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            onCheckBoxDel(v, getAdapterPosition());
                }
            });

            itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int i = getAdapterPosition();
                    if (i == 0 || i > currentPackList.size()) {
                        Log.e("Invalid", "index");
                        return;
                    }
                    currentPackList.get(i - 1).setCheck(isChecked);
                }
            });
        }
    }


//            checkboxDel.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        switch (v.getId()) {
//        case R.id.packlistCheckboxEdit: {
//        onCheckBoxEdit(v);
//        }
//        break;
//        case R.id.packlistCheckboxDel: {
//        onCheckBoxDel(v);
//        }
//        break;
//        }
//        }
//        });

    private void onItemCheckBox(final View v, final int index) {

    }

    public void onCheckBoxEdit (final View v, final int index) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit element");

        // Set up the input
        final EditText input = new EditText(context);

        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editStuffTitle = input.getText().toString();
                ViewHolder holder = (ViewHolder) (v.getTag());
                holder.itemCheckbox.setText(editStuffTitle);
                Stuff stuff = currentPackList.get(index - 1);
                if (stuff == null) {
                    Log.e("onCheckBoxEdit", "Invalid index");
                    return;
                }
                stuff.setName(editStuffTitle);
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

    public void onCheckBoxDel (final View v, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete item");
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Stuff stuff = currentPackList.get(index - 1);
                if (stuff == null) {
                    Log.e("onCheckBoxEdit", "Invalid index");
                    return;
                }
                currentPackList.remove(stuff);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, currentPackList.size() + 2);
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




    @Override
    public AdapterRecycler_Packlist.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.packlists_packlist_card, viewGroup, false);
        AdapterRecycler_Packlist.ViewHolder viewHolder = new AdapterRecycler_Packlist.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler_Packlist.ViewHolder viewHolder, int i) {

//        resetAllItems(viewHolder);

        if (i == 0) {
            setTitle(viewHolder);
            return;
        }

        addItem(viewHolder, i);



//        if (i == 0){           // if this is first card
//            viewHolder.textPacklist.setText(packlistTitle);      // set packlist title
//            viewHolder.textPacklist.setVisibility(View.VISIBLE); // display packlist title
//        } else {              //if this is not first card
//            if (!groupsTitles[i].equals(groupsTitles[i-1])) {     // if we have unique (new) group
////                viewHolder.textGroup.setText(groupsTitles[i]);   // set group title
//            } else {                                                 // if we have the same group
//                viewHolder.layoutGroup.setVisibility(View.GONE); // hide group layout
//            }
//
//            if (isEditOpen) {
//                if (i == currentPackList.size() - 1) {                          // if this is the last element
//                    viewHolder.layoutItemAdd.setVisibility(View.VISIBLE);  // show layout to add item
//                    viewHolder.layoutGroupAdd.setVisibility(View.VISIBLE); // show layout to add group
//                } else {
//                    if (!groupsTitles[i].equals(groupsTitles[i + 1])) {         // if this is the last element in this group
//                        viewHolder.layoutItemAdd.setVisibility(View.VISIBLE); // show layout to add item
//                    }
//                }
//            }
//        }

//        viewHolder.itemCheckbox.setText(itemTitles[i]); // anyway set checkbox (stuff) item
    }

//            viewHolder.checkboxDel.setVisibility(View.GONE);
//            viewHolder.groupBtnDel.setVisibility(View.GONE);
//            viewHolder.groupBtnEdit.setVisibility(View.GONE);
//            viewHolder.textGroup.setFocusable(false);
//            viewHolder.textGroup.setFocusableInTouchMode(false);
//            viewHolder.textGroup.setClickable(false);
//            viewHolder.textGroup.setBackgroundColor(Color.TRANSPARENT);

    private void addItem(AdapterRecycler_Packlist.ViewHolder viewHolder, int i) {

        i -= 1; // true index is -1
        if (i < 0 ) {
            Log.e("addItem", "invalid index");
            return;
        }

        Stuff stuff =  currentPackList.get(i);

        if (stuff == null) {
            Log.e("addItem", "invalid stuff");
            return;
        }

        viewHolder.itemCheckbox.setText(stuff.getName());
        viewHolder.itemCheckbox.setChecked(stuff.isChecked());
    }

//    private packItemType chooseItem(int index) {
//        Stuff stuff = currentPackList.get(index);
//
//    }

//    private void setEditMode(AdapterRecycler_Packlist.ViewHolder viewHolder, int i) {
//        viewHolder.checkboxDel.setVisibility(View.VISIBLE);
//        viewHolder.groupBtnDel.setVisibility(View.VISIBLE);
//        viewHolder.groupBtnEdit.setVisibility(View.VISIBLE);
//        viewHolder.layoutItemAdd.setVisibility(View.VISIBLE);
//        viewHolder.textGroup.setFocusable(true);
//        viewHolder.textGroup.setFocusableInTouchMode(true);
//        viewHolder.textGroup.setClickable(true);
//        int itemIndex = i - 1;
//        if (itemIndex < 0) {
//            return;
//        }
//    }

    private void resetAllItems(AdapterRecycler_Packlist.ViewHolder viewHolder) {
        viewHolder.textPacklist.setVisibility(View.GONE);
//
        viewHolder.itemCheckbox.setVisibility(View.GONE);
        viewHolder.itemCheckbox.setClickable(false);
        viewHolder.layoutItemAdd.setVisibility(View.GONE);
        viewHolder.itemCheckbox.setVisibility(View.GONE);
        viewHolder.itemCheckbox.setClickable(false);
        viewHolder.checkboxDel.setVisibility(View.GONE);
        viewHolder.checkboxEdit.setVisibility(View.GONE);

    }

    private void setTitle(AdapterRecycler_Packlist.ViewHolder viewHolder)  {
        viewHolder.itemCheckbox.setVisibility(View.GONE);
        viewHolder.itemCheckbox.setClickable(false);
        viewHolder.checkboxDel.setVisibility(View.GONE);
        viewHolder.checkboxEdit.setVisibility(View.GONE);
        viewHolder.textPacklist.setText(packlistTitle);      // set packlist title
        viewHolder.textPacklist.setVisibility(View.VISIBLE); // display packlist title

//        viewHolder.groupBtnDel.setVisibility(View.GONE);
//        viewHolder.groupBtnEdit.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return currentPackList.size() + 1; // +1 for title
    }

}
