package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;

/**
 * Created by MENEDGERP36 on 28.01.2018.
 */

public class AdapterSwipe extends PagerAdapter {

    private int[] image_res = {
            R.drawable.bag_adventure_dpi,
            R.drawable.bag_birds_dpi,
            R.drawable.bag_britain_dpi};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private TravelBook travelBookCtx;

    public AdapterSwipe(Context ctx){
        this.ctx = ctx;
        travelBookCtx = TravelBook.getCurrentTravelBook();
        if (travelBookCtx == null) {
            Log.e("AdapterSwipe" , "Caution! travelBook = null!");
        }
    }

    @Override
    public int getCount() {
        return travelBookCtx.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.travelbooks_travelbook_swipe, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.photo1);
        final ImageView imageBG = (ImageView) item_view.findViewById(R.id.bg);
        TextView textView = (TextView) item_view.findViewById(R.id.text1);
        TextView caption = (TextView) item_view.findViewById(R.id.caption);


        final Post post = travelBookCtx.get(position);
        if (post == null) {
            Log.e("instantiateItem", "Post = null!");
            return item_view;
        }


        textView.setText(post.getTitlePhoto1());
        caption.setText(post.getName());

        if (post.getColorImg() != 0) {
            imageBG.setBackgroundColor(post.getColorImg());
        } else {
            ViewsHandler.getInstance().loadImageIntoViewBg(post.getBackGroundImgUri(), imageBG);
        }

        ViewsHandler.getInstance().loadImageIntoView(post.getMainImgUri(), imageView);


        if (post.getColorText() != 0) {
            textView.setTextColor(post.getColorText());
            caption.setTextColor(post.getColorText());
        }

        if (post.getColorTextBg() != 0) {
            textView.setBackgroundColor(post.getColorTextBg());
            caption.setBackgroundColor(post.getColorTextBg());
        }

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
