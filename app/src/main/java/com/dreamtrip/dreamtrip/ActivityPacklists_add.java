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

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;

enum RequestCodeBag {
    NONE,
    RECTANGLE,
    SQUARE,
}

public class ActivityPacklists_add extends AppCompatActivity {
    Button btn_save;
    ImageView btnBagSquare, btnBagRectangle, imgPhoto;
    EditText packlistDetail, packlistTitle;

    int currentBagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlists_add);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addPacklist(v);
            }
        });
        packlistTitle = (EditText) findViewById(R.id.editPacklistTitle);
        packlistDetail = (EditText) findViewById(R.id.editPacklistDetail);
        btnBagSquare = (ImageView) findViewById(R.id.btnBagSquare);
        btnBagRectangle = (ImageView) findViewById(R.id.btnBagRectangle);
    }

    public void addPacklist(View view){
        String packName = packlistTitle.getText().toString();
        String packDetails = packlistDetail.getText().toString();

        if(packName.equals("")){
            Toast.makeText(this, "ERROR - Enter title!", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("value", "Successfully added");


            Packlist packlist = new Packlist(packName, packDetails, currentBagId);

            PacklistsDB.getInstance().put(packlist);

            Packlist.setCurrentPacklist(packlist);

            startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_packlist").putExtras(bundle));
        }
    }

    public void openGallery(View view){
        RequestCodeBag requestCode = RequestCodeBag.NONE;
        switch (view.getId()){
            case R.id.btnBagRectangle:
                imgPhoto = btnBagRectangle;
                requestCode = RequestCodeBag.RECTANGLE;
                break;
            case R.id.btnBagSquare:
                imgPhoto = btnBagSquare;
                requestCode = RequestCodeBag.SQUARE;
                break;
        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, requestCode.ordinal());
    }

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap selectedImage = ViewsHandler.getInstance().setImageFromGallery(data, ActivityPacklists_add.this);
            switch (RequestCodeBag.values()[requestCode]) {
                case RECTANGLE:{                                    //bag rectangle
                    imgPhoto.getLayoutParams().height =
                            (int) ViewsHandler.getInstance().convertDpToPx(75, ActivityPacklists_add.this);
                    imgPhoto.getLayoutParams().width =
                            (int) ViewsHandler.getInstance().convertDpToPx(110, ActivityPacklists_add.this);
                    imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgPhoto.requestLayout();
                    selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 300, 200);
                    chooseBag(findViewById(R.id.layoutBagRectangle));
                    break;
                }
                case SQUARE:{                                    //bag square
                    imgPhoto.getLayoutParams().height =
                            (int) ViewsHandler.getInstance().convertDpToPx(75, ActivityPacklists_add.this);
                    imgPhoto.getLayoutParams().width =
                            (int) ViewsHandler.getInstance().convertDpToPx(90, ActivityPacklists_add.this);
                    imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgPhoto.requestLayout();
                    selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 300, 200);
                    chooseBag(findViewById(R.id.layoutBagSquare));
                    break;
                }
            }
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);
        }
    }

    public void chooseBag(View view){
        View[] buttons = {
                findViewById(R.id.layoutBagRectangle),
                findViewById(R.id.layoutBagSquare),
                findViewById(R.id.packlists_add_btnAdventure),
                findViewById(R.id.packlists_add_btnBirds),
                findViewById(R.id.packlists_add_btnChildren),
                findViewById(R.id.packlists_add_btnFestival),
                findViewById(R.id.packlists_add_btnFrance1),
                findViewById(R.id.packlists_add_btnFrance2),
                findViewById(R.id.packlists_add_btnGreece),
                findViewById(R.id.packlists_add_btnLondon1),
                findViewById(R.id.packlists_add_btnLondon2),
                findViewById(R.id.packlists_add_btnLondon3),
                findViewById(R.id.packlists_add_btnMelons),
                findViewById(R.id.packlists_add_btnSummer),
                findViewById(R.id.packlists_add_btnTravel),
                findViewById(R.id.packlists_add_btnTravel2),
                findViewById(R.id.packlists_add_btnTwopeople),
                findViewById(R.id.packlists_add_btnWinter1),
                findViewById(R.id.packlists_add_btnWinter2)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        currentBagId = getBagIDbyName(iconName);


        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));

    }


    public int getBagIDbyName(String name) {
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
