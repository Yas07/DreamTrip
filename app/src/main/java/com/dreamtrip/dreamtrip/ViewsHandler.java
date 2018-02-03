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

/**
 * Created by MENEDGERP36 on 01.02.2018.
 */

public final class ViewsHandler {

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public static Bitmap setImageFromGallery(Intent data, Context context){
        Uri uri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null,null,null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
        return selectedImage;
    }

    public static Bitmap resizeImage(Bitmap image, int minWidth, int minHeight){
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        int percWidth = (minWidth * 100) / oldWidth;
        int percHeight = (minHeight * 100) / oldHeight;
        int percOfResize = (percHeight < percWidth)? percHeight : percWidth;
        int newWidth = (oldWidth * percOfResize) / 100;
        int newHeight = (oldHeight * percOfResize) / 100;
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
        return bitmapScaled;
    }


//-------------------------------------------------------------Convert Measurements
//-----------------------------------------------------------------------------------------------

    public static float convertDpToPx(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp *((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPxToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
