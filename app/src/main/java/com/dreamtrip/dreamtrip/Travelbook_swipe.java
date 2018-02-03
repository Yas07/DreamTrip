package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by MENEDGERP36 on 28.01.2018.
 */

public class Travelbook_swipe extends PagerAdapter {

    private int[] image_res = {
            R.drawable.bag_adventure_dpi,
            R.drawable.bag_birds_dpi,
            R.drawable.bag_britain_dpi};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public Travelbook_swipe(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return image_res.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.travelbooks_travelbook_swipe, container, false);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);

        TextView textView = (TextView) item_view.findViewById(R.id.mytext);
        imageView.setImageResource(image_res[position]);
        textView.setText("Image: " + position);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
