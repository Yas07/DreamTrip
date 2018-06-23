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

import com.squareup.picasso.Picasso;

import java.security.spec.ECField;

import Trip_DBs.Trips_BD;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;

enum RequestCodeFrame {
    NONE,
    LIGHT
}

public class ActivityTravelbooks_add extends AppCompatActivity {
    Button btn_save;
    ImageView btnPhotoFrameLight;
    boolean isPhotoSet = false;
    EditText editTravelbookTitle;
    EditText editTravelBookDetails;

    int currentPhotoId;
    boolean isEditMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelbooks_add);

        Bundle bundle = getIntent().getExtras();
        isEditMode = bundle != null && bundle.getBoolean(Trips_BD.editBundleValue);

        editTravelbookTitle = (EditText) findViewById(R.id.editTravelbookTitle);
        editTravelBookDetails = (EditText) findViewById(R.id.editText5);
        btnPhotoFrameLight = (ImageView) findViewById(R.id.btnPhotoFrameLight);

        btn_save = (Button) findViewById(R.id.btn_travelbook_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addTravelBook(v);
            }
        });


        if (isEditMode) {
            disassembleTravelBook(TravelBook.getCurrentTravelBook());
        }
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

            TravelBook travelBook = TravelBook.getCurrentTravelBook();
            if ((isEditMode == false) || (travelBook == null)) {
                travelBook = new TravelBook();
            }

            assembleTravelBook(travelBook);

            TravelBooksDB.getInstance().put(travelBook);
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook").putExtras(bundle));
        }
    }

    private void assembleTravelBook(TravelBook travelBook) {

        if (travelBook == null) {
            Log.e("assembleTravelBook", "null travelbook");
            return;
        }

        String details = editTravelBookDetails.getText().toString();
        String title = editTravelbookTitle.getText().toString();
        travelBook.setDetails(details);
        travelBook.setName(title);
        travelBook.setPhotoIndex(currentPhotoId);

        if (isPhotoSet) {
            btnPhotoFrameLight.buildDrawingCache();
            travelBook.setPhotoImage(btnPhotoFrameLight.getDrawingCache());
        }

    }

    private void disassembleTravelBook(TravelBook travelBook) {
        if (travelBook == null) {
            Log.e("assembleTravelBook", "null travelbook");
            return;
        }

        editTravelBookDetails.setText(travelBook.getDetails());
        editTravelbookTitle.setText(travelBook.getName());
        currentPhotoId = travelBook.getPhotoIndex();

        if (travelBook.getPhotoImage() != null) {
            btnPhotoFrameLight.setImageBitmap(travelBook.getPhotoImage());
            isPhotoSet = true;
            chooseCover(btnPhotoFrameLight);
        } else if (currentPhotoId != 0) {
            chooseCover(findViewById(currentPhotoId));
        }
    }
//----------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public void openGallery(View view){
        RequestCodeFrame requestCode = RequestCodeFrame.NONE;
        switch (view.getId()){
            case R.id.btnPhotoFrameLight:
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
            chooseCover(btnPhotoFrameLight);
            try {
                Picasso.get().load(data.getData()).resize(300, 200).centerInside().into(btnPhotoFrameLight);
            } catch (Exception e) {
                Log.e("galery", "error = " + e.getMessage());
            }
            isPhotoSet = true;
            currentPhotoId = 0;
        }
    }


    public void chooseCover(View view){
        View[] buttons = {
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
        currentPhotoId = view.getId() == R.id.layoutFrameLight ? 0 : getPhotoIDbyName(photo);

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
            return res;
        } catch(Exception e) {
            Log.e("getIconIdByName", "Failed to find icon=" + name);
            return res;
        }

    }


}
