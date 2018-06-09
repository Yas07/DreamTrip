package com.dreamtrip.dreamtrip;

import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Trips_Plan.PlanPoint;
import Trip_Items.Trips_trip;

/**
 * Created by MENEDGERP36 on 04.02.2018.
 */

public class AdapterRecycler_Plan extends RecyclerView.Adapter<AdapterRecycler_Plan.ViewHolder> {

    private ArrayList<PlanPoint> planPoints;

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

    public AdapterRecycler_Plan() {
       super();

        Trips_trip tripCtx = Trips_trip.getCurrentTrip();
        planPoints = null;
        if (tripCtx == null) {
            Log.e("Adapter plan, on bind", "There is no current trip");
        } else {
            Plan plan = tripCtx.getPlan();
            if (plan != null) {
                planPoints = new ArrayList<PlanPoint>(plan);
            } else {
                Log.e("Adapter plan, on bind", "There is no plan available");
            }
        }

    }

//    public AdapterRecycler_Plan() {
//        super();
//    }


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
                .inflate(R.layout.mytrips_trip_plan_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PlanPoint planPoint = planPoints.get(i);

        if (planPoint == null)  {
            Log.e("onBindView", "Invalid index");
            return;
        }

        viewHolder.itemTime.setText(planPoint.getTime());
        viewHolder.itemDetail.setText(planPoint.get_otherDetails());
        viewHolder.itemImage.setImageResource(images[0]); // TODO: after making images
        viewHolder.layoutNote.setBackgroundResource(backgrounds[0]); // TODO: after images

        if (i == 0){
            viewHolder.textPlan.setVisibility(View.VISIBLE);
            viewHolder.itemDayWeek.setText(weekdays[i]); // TODO: after making date & time
            viewHolder.itemDate.setText(dates[i]); // TODO: after making date & time
        } else {
            if (!weekdays[i].equals(weekdays[i-1])){ // check previous weekday
                viewHolder.itemDayWeek.setText(weekdays[i]); // TODO: after making date & time
                viewHolder.itemDate.setText(dates[i]); // TODO: after making date & time
            } else  {
                viewHolder.layoutDate.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (planPoints == null) {
            Log.e("onBindView", "getItemCount, planPoinst == null? wtf??");
            return 0;
        } else {
            return planPoints.size();
        }

    }
}
