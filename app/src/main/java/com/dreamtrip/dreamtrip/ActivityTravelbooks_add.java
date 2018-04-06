package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

enum RequestCodeFrame {
    NONE,
    DARK,
    LIGHT,
}

public class ActivityTravelbooks_add extends AppCompatActivity {
    Button btn_save;
    ImageView imgPhoto, btnPhotoFrameLight, btnPhotoFrameDark;
    EditText editTravelbookTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_add);

        editTravelbookTitle = (EditText) findViewById(R.id.editTravelbookTitle);

        btn_save = (Button) findViewById(R.id.btn_travelbook_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(editTravelbookTitle.getText().toString().equals("")){
                    Toast.makeText(ActivityTravelbooks_add.this, "ERROR - Enter title!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("value", "Successfully added");
                    startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook").putExtras(bundle));
                }
            }
        });

        btnPhotoFrameDark = (ImageView) findViewById(R.id.btnPhotoFrameDark);
        btnPhotoFrameLight = (ImageView) findViewById(R.id.btnPhotoFrameLight);
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
                    break;
                }
                case LIGHT : {
                    chooseCover(findViewById(R.id.layoutFrameLight));
                    break;
                }
            }
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);
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

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));

    }
}
