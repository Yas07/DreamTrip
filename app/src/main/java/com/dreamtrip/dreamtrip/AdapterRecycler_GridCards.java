package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import Trip_Items.Trips_trip;

enum enum_ACTIVITY_TYPE{
    TRIPS,
    PACKLISTS,
    TRAVELBOOKS
}

public class AdapterRecycler_GridCards extends RecyclerView.Adapter<AdapterRecycler_GridCards.ViewHolder> {
    private String[] cardTitles;
    private String[] cardDetails;
    private int[] cardImages;
    private int colorBg;
    private int colorText;
    private enum_ACTIVITY_TYPE activityType;
    private List<Trips_trip> tripsList;


    public AdapterRecycler_GridCards(String[] cardTitles, String[] cardDetails,
                        int[] cardImages, int colorBg, int colorText, enum_ACTIVITY_TYPE activityType){
        this.cardTitles = cardTitles;
        this.cardDetails = cardDetails;
        this.cardImages = cardImages;
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        this.tripsList = null;
    }

    public AdapterRecycler_GridCards(List<Trips_trip> tripsList, int colorBg, int colorText, enum_ACTIVITY_TYPE activityType){
        assert (activityType == enum_ACTIVITY_TYPE.TRIPS);
        this.cardTitles = null;
        this.cardDetails = null;
        this.cardImages = null;
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        this.tripsList = tripsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public LinearLayout layoutCard;
        public TextView cardTitle;
        public TextView cardDetail;
        public ImageView cardImage;
        private final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            layoutCard = (LinearLayout) itemView.findViewById(R.id.cardGridLayout);
            cardTitle = (TextView) itemView.findViewById(R.id.cardGridTitle);
            cardDetail = (TextView) itemView.findViewById(R.id.cardGridDetails);
            cardImage = (ImageView) itemView.findViewById(R.id.cardGridImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    switch (activityType) {
                        case TRIPS:
                        {
                            int i = getAdapterPosition();
                            Trips_trip trip = tripsList.get(i);

                            final Intent intent =  new Intent(context, ActivityMytrips_trip.class).putExtras(trip.getBundle());
                            context.startActivity(intent);
                        }
                        break;

                        case PACKLISTS:
                        {
                            // TODO: remove
                            int position = getAdapterPosition();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(position));

                            final Intent intent =  new Intent(context, ActivityPacklists_packlist.class).putExtras(bundle);
                            context.startActivity(intent);
                        }
                        break;

                        case TRAVELBOOKS:
                        {
                            // TODO: remove
                            int position = getAdapterPosition();
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(position));

                            final Intent intent =  new Intent(context, ActivityTravelbooks_travelbook.class).putExtras(bundle);
                            context.startActivity(intent);
                        }
                        break;

                        default:
                            Log.e(this.getClass().getEnclosingMethod().getName(), "Wrong enum value!");
                        break;
                    }

                }
            });
        }
    }

    @Override
    public AdapterRecycler_GridCards.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards, viewGroup, false);
        return new AdapterRecycler_GridCards.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecycler_GridCards.ViewHolder holder, int i) {
        final AdapterRecycler_GridCards.ViewHolder viewHolder = holder;
        if (activityType == enum_ACTIVITY_TYPE.TRIPS) {
            Trips_trip trip = tripsList.get(i);
            if (trip == null) {
                Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                return;
            }

            viewHolder.cardTitle.setText(trip.getName());

            viewHolder.cardDetail.setText(trip.startEndDateToStr());

            // TODO: add images when they are ready
            // viewHolder.cardImage.setImageResource(cardImages[i]);

            viewHolder.cardTitle.setTextColor(colorText);
            viewHolder.cardDetail.setTextColor(colorText);
            viewHolder.layoutCard.setBackgroundColor(colorBg);

        } else {
            viewHolder.cardTitle.setText(cardTitles[i]);
            viewHolder.cardDetail.setText(cardDetails[i]);
            viewHolder.cardImage.setImageResource(cardImages[i]);

            viewHolder.cardTitle.setTextColor(colorText);
            viewHolder.cardDetail.setTextColor(colorText);
            viewHolder.layoutCard.setBackgroundColor(colorBg);
        }
    }

    @Override
    public int getItemCount() {
        switch (activityType) {
            case TRIPS:
            return tripsList.size();

            case PACKLISTS:
            case TRAVELBOOKS:
            return cardTitles.length;

            default:
                Log.e(this.getClass().getEnclosingMethod().getName(), "Wrong enum value!");
                return 0;
        }
    }

}
