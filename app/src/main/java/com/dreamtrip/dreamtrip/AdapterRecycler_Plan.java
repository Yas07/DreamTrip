package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Trips_Plan.PlanPoint;
import Trip_Items.Trips_trip;
import layout.Fragment_plan;

/**
 * Created by MENEDGERP36 on 04.02.2018.
 */

public class AdapterRecycler_Plan extends RecyclerView.Adapter<AdapterRecycler_Plan.ViewHolder> {

    private ArrayList<PlanPoint> planPoints;
    private int choosenPlanPoint;
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

    enum HolderType {
        DATE,
        PLAN_POINT,
        PLAN_TEXT
    }

    class ViewHolder extends RecyclerView.ViewHolder { // implements View.OnCreateContextMenuListener {

        private int index;
        private TextView textPlan;
        private LinearLayout layoutDate;
        private TextView itemDayWeek;
        private TextView itemDate;
        private LinearLayout layoutNote;
        private ImageView itemImage;
        private TextView itemTime;
        private TextView itemTitle;
        private TextView itemDetail;


        public ViewHolder(final View itemView) {
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

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mytrips_trip_plan_card, viewGroup, false);

        final AdapterRecycler_Plan.ViewHolder viewHolder = new ViewHolder(v);
        v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    MenuInflater menu = new MenuInflater(viewGroup.getContext());
                    menu.inflate(R.menu.edit_delete, contextMenu);
                    contextMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Log.e("Edit", "planpoint=" + viewHolder.index);
                            return true;
                        }
                    });
                    contextMenu.getItem(0).setVisible(false);

                    contextMenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete plan point");
                            builder.setMessage("Do you want to delete this point in plan?");
                            builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PlanPoint planPoint = planPoints.get(viewHolder.index);
                                    if (planPoint == null) {
                                        Log.e("Delete planpoint", "Null planpoint");
                                        //return true;
                                    }
                                    planPoints.remove(planPoint);
                                    Trips_trip.getCurrentTrip().getPlan().remove(planPoint);
                                    notifyItemRemoved(viewHolder.index);
                                    notifyItemRangeChanged(viewHolder.index, planPoints.size());
                                    Toast.makeText(context, "Point was deleted", Toast.LENGTH_SHORT).show();
                                    //return true;
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                            return true;
                        }
                    });

            }

        });


        return viewHolder;
    }

    private HolderType checkPlanPointType(int index) {
        PlanPoint planPoint = planPoints.get(index);

        if (index == 0){
            return HolderType.PLAN_TEXT;
        } else {
            if (!planPoint.getDate().equals(
                    planPoints.get(index-1).getDate())){ // check previous weekday
                return HolderType.DATE;
            } else  {
                return HolderType.PLAN_POINT;
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PlanPoint planPoint = planPoints.get(i);

        if (planPoint == null)  {
            Log.e("onBindView", "Invalid index");
            return;
        }

        viewHolder.index = i;
        viewHolder.itemTitle.setText(planPoint.getTitle());
        viewHolder.itemTime.setText(planPoint.getTime());

        String itemDetails = planPoint.getPlace().getPlaceData();
        itemDetails += itemDetails.isEmpty() ? "" : "\n";
        itemDetails += planPoint.getOtherDetails();

        viewHolder.itemDetail.setText(itemDetails);

        if (planPoint.getIconIndex() != 0 && planPoint.getColorIndex() != 0) {
            viewHolder.itemImage.setImageResource(planPoint.getIconIndex());
            viewHolder.layoutNote.setBackgroundResource(planPoint.getColorIndex());
        } else {
            Log.e("onBindView", "invalid indexes");
        }

        switch (checkPlanPointType(i)) {
            case PLAN_TEXT:
                viewHolder.textPlan.setVisibility(View.VISIBLE);
                viewHolder.itemDayWeek.setText(planPoint.getDayOfWeek());
                viewHolder.itemDate.setText(planPoint.getDate());
                break;

            case DATE:
                viewHolder.itemDayWeek.setText(planPoint.getDayOfWeek());
                viewHolder.itemDate.setText(planPoint.getDate());
                break;

            case PLAN_POINT:
                viewHolder.layoutDate.setVisibility(View.GONE);
                break;
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
