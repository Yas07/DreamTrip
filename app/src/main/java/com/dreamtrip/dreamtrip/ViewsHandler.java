package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.internal.io.FileSystem;

//enum FragmentType {
//    NONE,
//    TRIP,
//    TRAVEL_BOOK,
//    PACKLIST;
//
//    static private String bundleValue = "fragType";
//
//    static public Bundle fragTypeToBundle(FragmentType fragmentType) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(bundleValue, fragmentType.ordinal());
//        return bundle;
//    }
//
//    static public FragmentType bundleToFragType(Bundle bundle) {
//        if (bundle == null) {
//            Log.e("bundleToFragType", "wrong bundle=null");
//            return NONE;
//        }
//        return values()[bundle.getInt(bundleValue)];
//    }
//
//}

public class ViewsHandler {

    private static volatile ViewsHandler viewsHandler = new ViewsHandler();
    private Queue<Target> targetBGset;
    private ViewsHandler(){
        targetBGset = new ArrayDeque<>();
    }

    public static ViewsHandler getInstance(){return viewsHandler;}
//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------


    public Uri saveTempImg(Uri imgToSave, Context context) {
        try {
            File outputDir = context.getExternalCacheDir(); // context being the Activity pointer
            StringBuilder name = new StringBuilder(imgToSave.getLastPathSegment());
            name = new StringBuilder(name.substring(name.lastIndexOf("/") + 1));
            if (name.length() == 0) {
                name = new StringBuilder(imgToSave.getLastPathSegment());
            }
            while (name.length() < 3) name.append("name");
            File outputFile = File.createTempFile(name.toString(), null, outputDir);
            outputFile.deleteOnExit();
            OutputStream fOut = new FileOutputStream(outputFile);
            Bitmap image  = getImageFromGalleryByUri(imgToSave, context);
            if (image == null) {
                throw new Exception("null image prt");
            }
            image.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            return Uri.fromFile(outputFile);
        } catch (Exception e) {
            Log.e("saveTempImg", "failed to save img, error" + e.getMessage());
            return null;
        }
    }

    private Bitmap getImageFromGalleryByUri(Uri uri, Context context)  {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null,null,null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(filePath);
    }

    public Bitmap getImageFromGallery(Intent data, Context context){
        Uri uri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null,null,null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(filePath);
    }

    public void loadImageIntoView(Uri uri, ImageView imageView) {
        if (uri != null && imageView != null) {
            Picasso.get().load(uri).fit().centerInside().into(imageView);
        } else {
            Log.e("loadImageInroView", "uri or imageView are empty!");
        }
    }

    public void loadImageIntoViewBg(final Uri uri, final ImageView imageView) {
        if (uri == null || imageView == null) {
            Log.e("loadImageIntoViewBg", "Null pointer");
            return;
        }
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Target backGroundTarget = new Target() {

                    ImageView view = imageView;

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        view.setBackground(new BitmapDrawable(view.getResources(), bitmap));
                        targetBGset.remove(this);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Log.e("Failed bitmap", e.getMessage());
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                targetBGset.add(backGroundTarget);

                Picasso.get().load(uri).resize(imageView.getWidth(), imageView.getHeight()).centerInside().into(backGroundTarget);
            }
        });
    }

    public Bitmap resizeImage(Bitmap image, int minWidth, int minHeight){
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        int percWidth = (minWidth * 100) / oldWidth;
        int percHeight = (minHeight * 100) / oldHeight;
        int percOfResize = (percHeight < percWidth)? percHeight : percWidth;
        int newWidth = (oldWidth * percOfResize) / 100;
        int newHeight = (oldHeight * percOfResize) / 100;
        return Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
    }


//-------------------------------------------------------------Convert Measurements
//-----------------------------------------------------------------------------------------------

    public float convertDpToPx(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp *((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public float convertPxToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    static public void deleteByUri(Uri uri) {
        try {
            File file = new File(uri.getPath());
            if (!file.delete()) throw new Exception("Can't delete by uri");
        } catch (Exception e) {
            Log.e("deleteByUri", e.getMessage());
        }
    }

}
