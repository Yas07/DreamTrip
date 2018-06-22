package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.TreeSet;

import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;

/**
 * Created by MENEDGERP36 on 28.01.2018.
 */

public class AdapterSwipe extends PagerAdapter {


    private Context ctx;
    private TravelBook travelBookCtx;
//    private TreeSet<Integer> idsForDelete;

    private int DELETE_ITEM = -1;

    public AdapterSwipe(Context ctx){
        this.ctx = ctx;
        travelBookCtx = TravelBook.getCurrentTravelBook();
        if (travelBookCtx == null) {
            Log.e("AdapterSwipe" , "Caution! travelBook = null!");
        }
//    idsForDelete = new TreeSet<>();
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
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.travelbooks_travelbook_swipe, container, false);

        initPost(item_view, position);

        container.addView(item_view);

        return item_view;
    }

    private void initPost(View item_view, int position) {

        final Post post = travelBookCtx.get(position);
        if (post == null) {
            Log.e("instantiateItem", "Post = null!");
            return;
        }

        item_view.setTag(position);


        ImageView imageView = (ImageView) item_view.findViewById(R.id.photo1);
        final ImageView imageBG = (ImageView) item_view.findViewById(R.id.bg);
        TextView textView = (TextView) item_view.findViewById(R.id.text1);
        TextView caption = (TextView) item_view.findViewById(R.id.caption);



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

        initPostContextMenu(item_view);
    }

    private void initPostContextMenu(View v) {
        v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, final View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuInflater menu = new MenuInflater(ctx);
                menu.inflate(R.menu.edit_delete, contextMenu);
                final int index = (int) view.getTag();
                contextMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Log.e("Edit", "post=" + index);
                        return true;
                    }
                });

                contextMenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        try {
                            Post post = travelBookCtx.get(index);
                            travelBookCtx.remove(post);
//                            idsForDelete.add(index);
                            view.setTag(DELETE_ITEM);
                            notifyDataSetChanged();
                        } catch (Exception e ) {
                            String msg = e.getMessage();
                            Log.e("Post adapter", msg == null ? "even msg null" : msg);
                            return true;
                        }
                        return true;
                    }
                });

            }

        });
    }

    @Override
    public int getItemPosition(Object object) {
        View v = (View) object;
        // TODO: do not overload all the images
//        int index = (int)v.getTag();
//        if (index == DELETE_ITEM) {
//            return PagerAdapter.POSITION_NONE;
//        } else {
            return PagerAdapter.POSITION_NONE;
//        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
