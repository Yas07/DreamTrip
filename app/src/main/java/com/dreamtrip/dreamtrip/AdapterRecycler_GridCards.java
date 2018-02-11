package com.dreamtrip.dreamtrip;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MENEDGERP36 on 11.02.2018.
 */

public class AdapterRecycler_GridCards extends RecyclerView.Adapter<AdapterRecycler_GridCards.ViewHolder> {
    private String[] cardTitles;
    private String[] cardDetails;
    private int[] cardImages;
    private int colorBg;
    private int colorText;

    public AdapterRecycler_GridCards(String[] cardTitles, String[] cardDetails,
                        int[] cardImages, int colorBg, int colorText){
        this.cardTitles = cardTitles;
        this.cardDetails = cardDetails;
        this.cardImages = cardImages;
        this.colorBg = colorBg;
        this.colorText = colorText;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public LinearLayout layoutCard;
        public TextView cardTitle;
        public TextView cardDetail;
        public ImageView cardImage;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutCard = (LinearLayout) itemView.findViewById(R.id.cardGridLayout);
            cardTitle = (TextView) itemView.findViewById(R.id.cardGridTitle);
            cardDetail = (TextView) itemView.findViewById(R.id.cardGridDetails);
            cardImage = (ImageView) itemView.findViewById(R.id.cardGridImage);
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
    public AdapterRecycler_GridCards.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards, viewGroup, false);
        AdapterRecycler_GridCards.ViewHolder viewHolder = new AdapterRecycler_GridCards.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler_GridCards.ViewHolder viewHolder, int i) {
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
