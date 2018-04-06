package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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


    public AdapterRecycler_GridCards(String[] cardTitles, String[] cardDetails,
                        int[] cardImages, int colorBg, int colorText, enum_ACTIVITY_TYPE activityType){
        this.cardTitles = cardTitles;
        this.cardDetails = cardDetails;
        this.cardImages = cardImages;
        this.colorBg = colorBg;
        this.colorText = colorText;
        this.activityType = activityType;
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
                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    bundle.putString("value", Integer.toString(position));
                    if (activityType == enum_ACTIVITY_TYPE.TRIPS){
                        final Intent intent =  new Intent(context, ActivityMytrips_trip.class).putExtras(bundle);
                        context.startActivity(intent);
                    } else
                        if (activityType == enum_ACTIVITY_TYPE.PACKLISTS){
                                final Intent intent =  new Intent(context, ActivityPacklists_packlist.class).putExtras(bundle);
                                context.startActivity(intent);
                        }
                        else
                        if (activityType == enum_ACTIVITY_TYPE.TRAVELBOOKS){
                            final Intent intent =  new Intent(context, ActivityTravelbooks_travelbook.class).putExtras(bundle);
                            context.startActivity(intent);
                        }

                }
            });
        }
    }

    @Override
    public AdapterRecycler_GridCards.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards, viewGroup, false);
        AdapterRecycler_GridCards.ViewHolder viewHolder = new AdapterRecycler_GridCards.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler_GridCards.ViewHolder holder, int i) {
        final AdapterRecycler_GridCards.ViewHolder viewHolder = holder;
        viewHolder.cardTitle.setText(cardTitles[i]);
        viewHolder.cardDetail.setText(cardDetails[i]);
        viewHolder.cardImage.setImageResource(cardImages[i]);

        viewHolder.cardTitle.setTextColor(colorText);
        viewHolder.cardDetail.setTextColor(colorText);
        viewHolder.layoutCard.setBackgroundColor(colorBg);
    }

    @Override
    public int getItemCount() {
        return cardTitles.length;
    }

}
