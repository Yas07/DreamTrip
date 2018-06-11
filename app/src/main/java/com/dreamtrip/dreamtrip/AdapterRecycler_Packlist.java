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
        if (i == 0) {
            setTitle(viewHolder);
            return;
        }

        addItem(viewHolder, i);
    }


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


    private void setTitle(AdapterRecycler_Packlist.ViewHolder viewHolder)  {
        viewHolder.itemCheckbox.setVisibility(View.GONE);
        viewHolder.itemCheckbox.setClickable(false);
        viewHolder.checkboxDel.setVisibility(View.GONE);
        viewHolder.checkboxEdit.setVisibility(View.GONE);
        viewHolder.textPacklist.setText(packlistTitle);      // set packlist title
        viewHolder.textPacklist.setVisibility(View.VISIBLE); // display packlist title
    }

    @Override
    public int getItemCount() {
        return currentPackList.size() + 1; // +1 for title
    }

}
