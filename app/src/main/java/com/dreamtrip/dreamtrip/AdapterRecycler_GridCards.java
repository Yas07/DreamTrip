package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import java.util.ArrayList;
import java.util.List;

import Trip_DBs.Trips_BD;
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;
import dalvik.system.PathClassLoader;

enum enum_ACTIVITY_TYPE{
    TRIPS,
    PACKLISTS,
    TRAVELBOOKS
}

public class AdapterRecycler_GridCards extends RecyclerView.Adapter<AdapterRecycler_GridCards.ViewHolder> {
    private int colorBg;
    private int colorText;
    private enum_ACTIVITY_TYPE activityType;
    private List<Trips_trip> tripsList;
    private ArrayList<Packlist> packlists;
    private ArrayList<TravelBook> travelBooks;

    public AdapterRecycler_GridCards(List<Trips_trip> tripsList, int colorBg, int colorText, enum_ACTIVITY_TYPE activityType){
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        this.tripsList = tripsList;
    }

    public AdapterRecycler_GridCards(ArrayList<Packlist> packlists, int colorBg, int colorText, enum_ACTIVITY_TYPE activityType){
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        this.packlists = packlists;
    }

    public AdapterRecycler_GridCards(int colorBg, int colorText, ArrayList<TravelBook> travelBooks, enum_ACTIVITY_TYPE activityType){
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        this.travelBooks = travelBooks;
    }

    private void initLists() {
        if (tripsList == null){

        }

        if (packlists == null) {
            packlists = new ArrayList<Packlist>();
        }


        if (travelBooks == null) {
            travelBooks = new ArrayList<TravelBook>();
        }

    }

    private void updateItemsList() {
        initLists();
//        if (tripsList.size() != Trips_BD.getInstance().size()){

//        }

        if (packlists != null && PacklistsDB.getInstance().size() != packlists.size()) {
            packlists = new ArrayList<Packlist>(PacklistsDB.getInstance().values());
        }


        if (travelBooks != null && TravelBooksDB.getInstance().size() != travelBooks.size()) {
            travelBooks = new ArrayList<TravelBook>(TravelBooksDB.getInstance().values());
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{
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
                            Trips_trip.setCurrentTrip(trip);

                            final Intent intent =  new Intent(context, ActivityMytrips_trip.class).putExtras(trip.getBundle());
                            context.startActivity(intent);
                        }
                        break;

                        case PACKLISTS:
                        {
                            int i = getAdapterPosition();
                            Packlist packlist = packlists.get(i);
                            Packlist.setCurrentPacklist(packlist);

                            // TODO: remove
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(i));

                            final Intent intent =  new Intent(context, ActivityPacklists_packlist.class).putExtras(bundle);
                            context.startActivity(intent);
                        }
                        break;

                        case TRAVELBOOKS:
                        {
                            int i = getAdapterPosition();
                            TravelBook travelBook = travelBooks.get(i);
                            TravelBook.setCurrentTravelBook(travelBook);

                            // TODO: remove
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(i));

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
        switch (activityType) {
            case TRIPS:{
                Trips_trip trip = tripsList.get(i);
                if (trip == null) {
                    Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                    return;
                }

                viewHolder.cardTitle.setText(trip.getName());

                viewHolder.cardDetail.setText(trip.startEndDateToStr());

                Bitmap bit = trip.getMainImage();
                if (bit != null) {
                    viewHolder.cardImage.setImageBitmap(bit);
                }

                viewHolder.cardTitle.setTextColor(colorText);
                viewHolder.cardDetail.setTextColor(colorText);
                viewHolder.layoutCard.setBackgroundColor(colorBg);

            }
            break;

            case PACKLISTS: {
                Packlist packlist = packlists.get(i);
                if (packlist == null) {
                    Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                    return;
                }

                viewHolder.cardTitle.setText(packlist.getName());

                viewHolder.cardDetail.setText(packlist.getDetails());


                Bitmap bit = packlist.getBagPhoto();
                if (bit != null && packlist.getBagIndex() != 0) {
                    viewHolder.cardImage.setImageResource(packlist.getBagIndex());
                    Drawable draw =  viewHolder.cardImage.getDrawable();
                    viewHolder.cardImage.setImageBitmap(bit);
                    viewHolder.cardImage.setBackground(draw);
                } else if (packlist.getBagIndex() != 0) {
                    viewHolder.cardImage.setImageResource(packlist.getBagIndex());
                }

                viewHolder.cardTitle.setTextColor(colorText);
                viewHolder.cardDetail.setTextColor(colorText);
                viewHolder.layoutCard.setBackgroundColor(colorBg);

            }
            break;

            case TRAVELBOOKS: {
                TravelBook travelBook = travelBooks.get(i);
                if (travelBook == null) {
                    Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                    return;
                }

                viewHolder.cardTitle.setText(travelBook.getName());

                viewHolder.cardDetail.setText(travelBook.getDetails());

                Bitmap bit = travelBook.getPhotoImage();
                if ( bit != null &&  (travelBook.getPhotoIndex() != 0)) {
                    viewHolder.cardImage.setImageResource(travelBook.getPhotoIndex());
                    Drawable draw =  viewHolder.cardImage.getDrawable();
                    viewHolder.cardImage.setImageBitmap(bit);
                    viewHolder.cardImage.setBackground(draw);
                } else if (travelBook.getPhotoIndex() != 0) {
                    viewHolder.cardImage.setImageResource(travelBook.getPhotoIndex());
                }

                viewHolder.cardTitle.setTextColor(colorText);
                viewHolder.cardDetail.setTextColor(colorText);
                viewHolder.layoutCard.setBackgroundColor(colorBg);
            }
            break;

            default:
                Log.e(this.getClass().getEnclosingMethod().getName(), "Wrong enum value!");
        }

    }

    @Override
    public int getItemCount() {
        updateItemsList();
        switch (activityType) {
            case TRIPS:
            return tripsList.size();

            case PACKLISTS:
            return packlists.size();

            case TRAVELBOOKS:
            return travelBooks.size();

            default:
                Log.e(this.getClass().getEnclosingMethod().getName(), "Wrong enum value!");
                return 0;
        }
    }

}
