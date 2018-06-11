package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ActivityTravelbooks_travelbook_add extends AppCompatActivity {
    int defaultColor;
    int defaultColorText = Color.WHITE;
    int defaultColorTextBg = Color.TRANSPARENT;
    ImageView imageButton = null;
    ImageView btnColorBg, btnColorText, btnColorTextBg;
    ImageView imgPhoto = null;
    ImageView imgBgCustom, imgPhoto1;
    TextView labelTextColor, labelTextBgColor;
    ImageView saveBtn;
    TravelBook travelBookCtx;
    EditText mainTitle;
    EditText textPhoto1;

    int choosenBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_travelbook_add);
        travelBookCtx = TravelBook.getCurrentTravelBook();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnColorBg = (ImageButton) findViewById(R.id.btnColorBg);
        btnColorText = (ImageView) findViewById(R.id.btnColorText);
        btnColorTextBg = (ImageView) findViewById(R.id.btnColorTextBg);
        labelTextBgColor = (TextView) findViewById(R.id.labelTextBgColor);
        labelTextColor = (TextView) findViewById(R.id.labelTextColor);
        saveBtn = (ImageView) findViewById(R.id.saveBtn);
        mainTitle = (EditText) findViewById(R.id.mainTitle);
        textPhoto1 = (EditText) findViewById(R.id.textPhoto1);

        saveBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                travelBookCtx.add(assamblePost());
                Bundle bundle = new Bundle();
                bundle.putString("value", "Successfully added");
                startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook").putExtras(bundle));
            }
        });


        imgBgCustom = (ImageView) findViewById(R.id.imgPostBgCustom);
        imgPhoto1 = (ImageView) findViewById(R.id.imgPhoto1);
//        imgPhoto2 = (ImageView) findViewById(R.id.imgPhoto2);
//        imgPhoto3 = (ImageView) findViewById(R.id.imgPhoto3);
//        imgPhoto4 = (ImageView) findViewById(R.id.imgPhoto4);

    }

    private Post assamblePost() {
        Post post = new Post(
                        mainTitle.getText().toString(),
                        textPhoto1.getText().toString(),
                        defaultColorText,
                        defaultColorTextBg);
        post.setMainImg(Trips_trip.getBitMapFromView(imgPhoto1));
        setBgForPost(post);
        return post;
    }

    public void chooseBg(View view){
        View[] buttons = {
                findViewById(R.id.layoutBgUpload),
                findViewById(R.id.layoutBgColor)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        view.setBackgroundColor(getResources().getColor(R.color.black_overlay));
        choosenBackGround = view.getId();
    }

   private void setBgForPost(Post post) {
        switch(choosenBackGround) {
            case R.id.layoutBgColor:
                post.setColorImg(defaultColor);
                break;

            case R.id.layoutBgUpload:
                post.setBackGroundImg(Trips_trip.getBitMapFromView(imgBgCustom));
                break;
        }

   }

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public void openGallery(View view){
        int number = 5;
        switch (view.getId()){
            case R.id.imgPostBgCustom:
                imgPhoto = imgBgCustom;
                number = 0;
                break;
            case R.id.imgPhoto1:
                imgPhoto = imgPhoto1;
                number = 1;
                break;
//            case R.id.imgPhoto2:
//                imgPhoto = imgPhoto2;
//                number = 2;
//                break;
//            case R.id.imgPhoto3:
//                imgPhoto = imgPhoto3;
//                number = 3;
//                break;
//            case R.id.imgPhoto4:
//                imgPhoto = imgPhoto4;
//                number = 4;
//                break;
        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap selectedImage = ViewsHandler.getInstance().setImageFromGallery(data, ActivityTravelbooks_travelbook_add.this);
            selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 800, 600);
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);
            switch(requestCode){
                case 0:
                    chooseBg(findViewById(R.id.layoutBgUpload));
                    break;
            }
        }
    }


//-------------------------------------------------------------Pick Color
//-----------------------------------------------------------------------------------------------

    public void openColorPicker(View view) {
        switch (view.getId()){
            case (R.id.btnColorBg):
                imageButton = btnColorBg;
                break;
            case (R.id.btnColorText):
                imageButton = btnColorText;
                break;
            case (R.id.btnColorTextBg):
                imageButton = btnColorTextBg;
                break;
        }
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                if (imageButton == btnColorBg) {
                    defaultColor = Color.argb(150, Color.red(color), Color.green(color), Color.blue(color));
                    imageButton.setBackgroundColor(defaultColor);
                    chooseBg(findViewById(R.id.layoutBgColor));
                }
                if (imageButton == btnColorText) {
                    defaultColorText = Color.argb(150, Color.red(color), Color.green(color), Color.blue(color));
                    labelTextColor.setTextColor(defaultColorText);
                    labelTextBgColor.setTextColor(defaultColorText);
                }
                if (imageButton == btnColorTextBg) {
                    defaultColorTextBg = Color.argb(150, Color.red(color), Color.green(color), Color.blue(color));
                    labelTextColor.setBackgroundColor(defaultColorTextBg);
                    labelTextBgColor.setBackgroundColor(defaultColorTextBg);
                }
            }
        });
        colorPicker.show();
    }
}
