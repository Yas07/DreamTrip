package com.dreamtrip.dreamtrip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;

import java.util.TreeSet;

import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.Trips_trip;

/**
 * Created by MENEDGERP36 on 28.01.2018.
 */

public class AdapterSwipe extends PagerAdapter {


    private Context ctx;
    private TravelBook travelBookCtx;
//    private TreeSet<Integer> idsForDelete;

    static private int DELETE_ITEM = -1;
    static public int exitFromEditIndex= -1;

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
                        exitFromEditIndex = index;
                        Bundle bundle = Trips_trip.getEditBundle();
                        bundle.putInt(Post.postIndexValue, index);
                        Intent intent = new Intent(ctx, ActivityTravelbooks_travelbook_add.class).
                                putExtras(bundle);
                        ctx.startActivity(intent);
                        Log.e("Edit", "post=" + index);
                        return true;
                    }
                });

                contextMenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setTitle("Delete post");
                        builder.setMessage("Do you want to delete this post?");
                        builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Post post = travelBookCtx.get(index);
                                    travelBookCtx.remove(post);
//                            idsForDelete.add(index);
                                    view.setTag(DELETE_ITEM);
                                    notifyDataSetChanged();
                                    Toast.makeText(ctx, "Post was deleted", Toast.LENGTH_SHORT).show();
                                } catch (Exception e ) {
                                    String msg = e.getMessage();
                                    Log.e("Post adapter", msg == null ? "even msg null" : msg);
                                    //return true;
                                }
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
