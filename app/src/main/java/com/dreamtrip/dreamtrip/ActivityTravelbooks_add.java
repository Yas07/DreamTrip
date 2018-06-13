package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;

enum RequestCodeFrame {
    NONE,
    DARK,
    LIGHT,
}

public class ActivityTravelbooks_add extends AppCompatActivity {
    Button btn_save;
    ImageView imgPhoto, btnPhotoFrameLight, btnPhotoFrameDark;
    boolean isPhotoSet = false;
    EditText editTravelbookTitle;
    EditText editTravelBookDetails;

    int currentPhotoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_add);

        editTravelbookTitle = (EditText) findViewById(R.id.editTravelbookTitle);
        editTravelBookDetails = (EditText) findViewById(R.id.editText5);

        btn_save = (Button) findViewById(R.id.btn_travelbook_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addTravelBook(v);
            }
        });

        btnPhotoFrameDark = (ImageView) findViewById(R.id.btnPhotoFrameDark);
        btnPhotoFrameLight = (ImageView) findViewById(R.id.btnPhotoFrameLight);
    }


    private void addTravelBook(View v) {
        String title = editTravelbookTitle.getText().toString();
        if(title.equals("")){
            Toast.makeText(ActivityTravelbooks_add.this, "ERROR - Enter title!", Toast.LENGTH_SHORT).show();
        }
//        TODO: check if photo was picked
//        else if (!isPhotoSet) {
//            Toast.makeText(ActivityTravelbooks_add.this, "ERROR - Choose cover picture!", Toast.LENGTH_SHORT).show();
//        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("value", "Successfully added");

            String details = editTravelBookDetails.getText().toString();
            TravelBook travelBook = new TravelBook(title, details, currentPhotoId);

            if (isPhotoSet) {
                travelBook.setPhotoImage(Trips_trip.getBitMapFromView(imgPhoto));
            }

            TravelBooksDB.getInstance().put(travelBook);

            startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook").putExtras(bundle));
        }
    }

    //--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public void openGallery(View view){
        RequestCodeFrame requestCode = RequestCodeFrame.NONE;
        switch (view.getId()){
            case R.id.btnPhotoFrameDark:
                imgPhoto = btnPhotoFrameDark;
                requestCode = RequestCodeFrame.DARK;
                break;
            case R.id.btnPhotoFrameLight:
                imgPhoto = btnPhotoFrameLight;
                requestCode = RequestCodeFrame.LIGHT;
                break;
        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, requestCode.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //any frame
            Bitmap selectedImage = ViewsHandler.getInstance().setImageFromGallery(data, ActivityTravelbooks_add.this);
            imgPhoto.getLayoutParams().height =
                    (int) ViewsHandler.getInstance().convertDpToPx(84, ActivityTravelbooks_add.this);
            imgPhoto.getLayoutParams().width =
                    (int) ViewsHandler.getInstance().convertDpToPx(84, ActivityTravelbooks_add.this);
            imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgPhoto.requestLayout();
            selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 300, 200);

            switch (RequestCodeFrame.values()[requestCode]) {
                case DARK:{
                    chooseCover(findViewById(R.id.layoutFrameDark));
                    currentPhotoId = R.drawable.travelbook_frame_black;
                    break;
                }
                case LIGHT : {
                    chooseCover(findViewById(R.id.layoutFrameLight));
                    currentPhotoId = R.drawable.travelbook_frame_light;
                    break;
                }
            }
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);
            isPhotoSet = true;
        }
    }


    public void chooseCover(View view){
        View[] buttons = {
                findViewById(R.id.layoutFrameDark),
                findViewById(R.id.layoutFrameLight),
                findViewById(R.id.travelbooks_add_btnChristmasBlack),
                findViewById(R.id.travelbooks_add_btnChristmasGreen),
                findViewById(R.id.travelbooks_add_btnChristmasWhite),
                findViewById(R.id.travelbooks_add_btnChristmasWhiteBlack),
                findViewById(R.id.travelbooks_add_btnChristmasWhiteRed),
                findViewById(R.id.travelbooks_add_btnCup),
                findViewById(R.id.travelbooks_add_btnDarkRed),
                findViewById(R.id.travelbooks_add_btnGreen),
                findViewById(R.id.travelbooks_add_btnLightBlue),
                findViewById(R.id.travelbooks_add_btnLightBlue2),
                findViewById(R.id.travelbooks_add_btnModern),
                findViewById(R.id.travelbooks_add_btnSummerFlamingo),
                findViewById(R.id.travelbooks_add_btnTravel),
                findViewById(R.id.travelbooks_add_btnTravelPink),
                findViewById(R.id.travelbooks_add_btnTravelBrightBlue),
                findViewById(R.id.travelbooks_add_btnTravelOrange),
                findViewById(R.id.travelbooks_add_btnOrangeAbstract)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);


        String photo = (String)  view.getTag();
        currentPhotoId = getPhotoIDbyName(photo);
        isPhotoSet = false;

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));

    }

    public int getPhotoIDbyName(String name) {
        int res = 0;
        try {
            res =  getResources().getIdentifier(name, "drawable",
                    getPackageName() );

            if (res == 0 )  {
                res = getResources().getIdentifier(name, "mipmap",
                        getPackageName() );
            }

        } catch(Exception e) {
            Log.e("getIconIdByName", "Failed to find icon=" + name);
        }
        return res;
    }


}
