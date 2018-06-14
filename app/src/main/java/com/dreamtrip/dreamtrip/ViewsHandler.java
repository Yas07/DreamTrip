package com.dreamtrip.dreamtrip;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

public class ViewsHandler {

    private static volatile ViewsHandler viewsHandler = new ViewsHandler();
    private ViewsHandler(){};
    public static ViewsHandler getInstance(){return viewsHandler;}

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public Bitmap setImageFromGallery(Intent data, Context context){
        Uri uri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null,null,null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(filePath);
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
}
