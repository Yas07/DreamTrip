package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.SortedMap;

import Trip_DBs.Trips_BD;
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;

enum ActivityType {
    TRIPS,
    PACKLISTS,
    TRAVELBOOKS;

    private ArrayList items;

    private void initItems() {
        switch (this) {
            case TRIPS:
                items = new ArrayList<>(Trips_BD.getInstance().getValuesSortByDate());
                break;
            case PACKLISTS:
                items = new ArrayList<>(PacklistsDB.getInstance().values());
                break;
            case TRAVELBOOKS:
                items = new ArrayList<>(TravelBooksDB.getInstance().values());
                break;
            default:
                Log.e("init items", "invalid items");
        }
    }

    public boolean isUpdateNeeded() {
        if (items == null) {
            return true;
        }
        switch (this) {
            case TRIPS:
                return items.size() != Trips_BD.getInstance().size();
            case PACKLISTS:
                return items.size() != PacklistsDB.getInstance().size();
            case TRAVELBOOKS:
                return items.size() != TravelBooksDB.getInstance().size();
            default:
                Log.e("init items", "invalid items");
                return  false;
        }
    }

    public void resetItems() {
        items = null;
    }

    public void setItems(ArrayList items) {
        if (items == null) {
            Log.e("setItems", "null items provided");
            return;
        }
        this.items = items;
    }

    public ArrayList getItems() {
        if (items == null) {
            initItems();
        }
        return items;
    }

    static private String bundleValue = "activityTypeNav";

    static public Bundle activityTypeToBundle(ActivityType activityType) {
        Bundle bundle = new Bundle();
        bundle.putInt(bundleValue, activityType.ordinal());
        return bundle;
    }

    static public ActivityType bundleToActivityType(Bundle bundle) {
        if (bundle == null) {
            Log.e("bundleToFragType", "wrong bundle=null");
            return TRIPS;
        }

        return values()[bundle.getInt(bundleValue)];
    }

}

public class AdapterRecycler_GridCards extends RecyclerView.Adapter<AdapterRecycler_GridCards.ViewHolder> {
    private int colorBg;
    private int colorText;
    private ActivityType activityType;
//    private ArrayList items;


    public AdapterRecycler_GridCards(int colorBg, int colorText, ActivityType activityType){
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
        if (this.activityType.isUpdateNeeded()) {
            this.activityType.resetItems();
        }
    }

//    private void updateItemsList() {
//        items = activityType.getItems();
//    }

    public ActivityType getCurrentActivityType() {
        return activityType;
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
                            Trips_trip trip = (Trips_trip)activityType.getItems().get(i);
                            Trips_trip.setCurrentTrip(trip);

                            final Intent intent =  new Intent(context, ActivityMytrips_trip.class).putExtras(trip.getBundle());
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(intent);
                        }
                        break;

                        case PACKLISTS:
                        {
                            int i = getAdapterPosition();
                            Packlist packlist = (Packlist)activityType.getItems().get(i);
                            Packlist.setCurrentPacklist(packlist);

                            // TODO: remove
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(i));

                            final Intent intent =  new Intent(context, ActivityPacklists_packlist.class).putExtras(bundle);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(intent);
                        }
                        break;

                        case TRAVELBOOKS:
                        {
                            int i = getAdapterPosition();
                            TravelBook travelBook = (TravelBook) activityType.getItems().get(i);
                            TravelBook.setCurrentTravelBook(travelBook);

                            // TODO: remove
                            Bundle bundle = new Bundle();
                            bundle.putString("value", Integer.toString(i));

                            final Intent intent =  new Intent(context, ActivityTravelbooks_travelbook.class).putExtras(bundle);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                Trips_trip trip = (Trips_trip) activityType.getItems().get(i);
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
                Packlist packlist = (Packlist) activityType.getItems().get(i);
                if (packlist == null) {
                    Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                    return;
                }

                viewHolder.cardTitle.setText(packlist.getName());

                viewHolder.cardDetail.setText(packlist.getDetails());

//                Bitmap bit = packlist.getBagPhoto();
//                if (bit != null) {
//                    viewHolder.cardImage.setImageBitmap(bit);
//                }

                Bitmap bit = packlist.getBagPhoto();
                if (bit != null) {
                    viewHolder.cardImage.setImageBitmap(bit);
                } else if (packlist.getBagIndex() != 0) {
                    viewHolder.cardImage.setImageResource(packlist.getBagIndex());
                }

                viewHolder.cardTitle.setTextColor(colorText);
                viewHolder.cardDetail.setTextColor(colorText);
                viewHolder.layoutCard.setBackgroundColor(colorBg);

            }
            break;

            case TRAVELBOOKS: {
                TravelBook travelBook = (TravelBook) activityType.getItems().get(i);
                if (travelBook == null) {
                    Log.e(this.getClass().getEnclosingMethod().getName(), "null trip, what a pity");
                    return;
                }

                viewHolder.cardTitle.setText(travelBook.getName());

                viewHolder.cardDetail.setText(travelBook.getDetails());

                Bitmap bit = travelBook.getPhotoImage();
                Uri photoUri = travelBook.getPhotoImageUri();
                if (photoUri != null) {
                    ViewsHandler.getInstance().loadImageIntoView(photoUri, viewHolder.cardImage);
                } else if (travelBook.getPhotoIndex() != 0) {
                    viewHolder.cardImage.setImageResource(travelBook.getPhotoIndex());
                } else if ( bit != null ) {
                    viewHolder.cardImage.setImageBitmap(bit);
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
//        updateItemsList();
        return activityType.getItems().size();
    }

}
