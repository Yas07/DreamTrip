package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.Calendar;

import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Trips_Plan.PlanPoint;
import Trip_Items.Trips_trip;

/**
 * Created by MENEDGERP36 on 04.02.2018.
 */

public class AdapterRecycler_Plan extends RecyclerView.Adapter<AdapterRecycler_Plan.ViewHolder> {

    private ArrayList<PlanPoint> planPoints;
    private Context context;

    public AdapterRecycler_Plan(Context context) {
       super();
        this.context = context;
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
                            onPlanPoint(v, getAdapterPosition());
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
        viewHolder.itemTitle.setText(planPoint.getTitle());
        viewHolder.itemTime.setText(planPoint.getTime());

        String itemDetails = planPoint.getPlace().getPlaceData() +  planPoint.getOtherDetails();
        viewHolder.itemDetail.setText(itemDetails);

        if (planPoint.getIconIndex() != 0 && planPoint.getColorIndex() != 0) {
            viewHolder.itemImage.setImageResource(planPoint.getIconIndex());
            viewHolder.layoutNote.setBackgroundResource(planPoint.getColorIndex());
        } else {
            Log.e("onBindView", "invalid indexes");
        }

        if (i == 0){
            viewHolder.textPlan.setVisibility(View.VISIBLE);
            viewHolder.itemDayWeek.setText(planPoint.getDayOfWeek());
            viewHolder.itemDate.setText(planPoint.getDate());
        } else {
            if (!planPoint.getDate().equals(
                    planPoints.get(i-1).getDate())){ // check previous weekday
                viewHolder.itemDayWeek.setText(planPoint.getDayOfWeek());
                viewHolder.itemDate.setText(planPoint.getDate());
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

    public void onPlanPoint (final View v, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete plan point");
        builder.setMessage("Do you want to delete this plan point?");
        builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //TODO: delete plan point

//                Stuff stuff = currentPackList.get(index - 1);
//                if (stuff == null) {
//                    Log.e("onCheckBoxEdit", "Invalid index");
//                    return;
//                }
//                currentPackList.remove(stuff);
//                notifyItemRemoved(index);
//                notifyItemRangeChanged(index, currentPackList.size() + 2);
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
