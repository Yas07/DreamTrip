package com.dreamtrip.dreamtrip;

import android.support.transition.Visibility;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by MENEDGERP36 on 04.02.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] titles = {"Chapter One",
            "Chapter Two",
            "Chapter Three",
            "Chapter Four",
            "Chapter Five",
            "Chapter Six",
            "Chapter Seven",
            "Chapter Eight"};

    private String[] weekdays = {"Monday",
            "Monday",
            "Monday",
            "Monday",
            "Tuesday",
            "Tuesday",
            "Tuesday",
            "Wednesday"};

    private String[] dates = {"01.01.2018",
            "01.01.2018",
            "01.01.2018",
            "01.01.2018",
            "02.01.2018",
            "02.01.2018",
            "02.01.2018",
            "03.01.2018"};

    private String[] time = {"12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00"};
    private String[] details = {"Item one details",
            "Item two details", "Item three details",
            "Item four details", "Item file details",
            "Item six details", "Item seven details",
            "Item eight details"};

    private int[] images = { R.drawable.icontransport_tram_black_24px,
            R.drawable.iconeat_bakery,
            R.drawable.iconextreme_archery,
            R.drawable.iconwinter_man_skiing,
            R.drawable.iconeat_pizza_black_24px,
            R.drawable.iconsummer_beach_black_24px,
            R.drawable.iconoutside_amusement_park,
            R.drawable.icontour_nefertiti };

    private int[] backgrounds = { R.drawable.note_violet_mdpi,
            R.drawable.note_yellow_mdpi,
            R.drawable.note_green_mdpi,
            R.drawable.note_blue_mdpi,
            R.drawable.note_yellow_mdpi,
            R.drawable.note_yellow_mdpi,
            R.drawable.note_green_mdpi,
            R.drawable.note_pink_mdpi };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView textPlan;
        public LinearLayout layoutDate;
        public TextView itemDayWeek;
        public TextView itemDate;
        public LinearLayout layoutNote;
        public ImageView itemImage;
        public TextView itemTime;
        public TextView itemTitle;
        public TextView itemDetail;


        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.imgIcon);
            itemTime = (TextView)itemView.findViewById(R.id.textTime);
            itemTitle = (TextView)itemView.findViewById(R.id.textTitle);
            itemDetail = (TextView)itemView.findViewById(R.id.textDetails);
            layoutNote = (LinearLayout) itemView.findViewById(R.id.layoutPlanPoint);
            layoutDate = (LinearLayout) itemView.findViewById(R.id.planLayoutDate);
            itemDayWeek = (TextView)itemView.findViewById(R.id.planDayWeek);
            itemDate = (TextView)itemView.findViewById(R.id.planDate);
            textPlan = (TextView)itemView.findViewById(R.id.planTextPlan);

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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemTime.setText(time[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        viewHolder.layoutNote.setBackgroundResource(backgrounds[i]);
        if (i == 0){
            viewHolder.textPlan.setVisibility(View.VISIBLE);
            viewHolder.itemDayWeek.setText(weekdays[i]);
            viewHolder.itemDate.setText(dates[i]);
        } else {
            if (!weekdays[i].equals(weekdays[i-1])){
                viewHolder.itemDayWeek.setText(weekdays[i]);
                viewHolder.itemDate.setText(dates[i]);
            }
            else viewHolder.layoutDate.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
