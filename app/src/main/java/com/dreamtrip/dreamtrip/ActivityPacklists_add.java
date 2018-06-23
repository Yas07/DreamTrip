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

import Trip_DBs.Trips_BD;
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import Trip_Items.Trips_trip;

enum RequestCodeBag {
    NONE,
    RECTANGLE
}

public class ActivityPacklists_add extends AppCompatActivity {
    Button btn_save;
    ImageView btnBagRectangle, imgPhoto;
    EditText packlistDetail, packlistTitle;
    boolean isPhotoSet = false;
    boolean isEditMode = false;

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
        btnBagRectangle = (ImageView) findViewById(R.id.btnBagRectangle);

        Bundle bundle = getIntent().getExtras();
        isEditMode = bundle != null && bundle.getBoolean(Trips_BD.editBundleValue);

        if (isEditMode) {
            disassemblePacklist(Packlist.getCurrentPacklist());
        }
    }

    public void addPacklist(View view){
        String packName = packlistTitle.getText().toString();
        String packDetails = packlistDetail.getText().toString();

        if(packName.equals("")){
            Toast.makeText(this, "ERROR - Enter title!", Toast.LENGTH_SHORT).show();
//          TODO: check if photo was picked
//        } else if (!isPhotoSet) {
//            Toast.makeText(this, "ERROR - Choose cover image!", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("value", "Successfully added");

            Packlist packlist = Packlist.getCurrentPacklist();
            if (!isEditMode) {
                packlist = new Packlist();
            }

            assemblePacklist(packlist);

            if (!isEditMode) {
                PacklistsDB.getInstance().put(packlist);
            }

            Packlist.setCurrentPacklist(packlist);
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityPacklists_packlist").putExtras(bundle));
        }
    }


    private void assemblePacklist(Packlist packlist) {
        if (packlist == null) {
            Log.e("assemblePacklist", "paclist = null");
            return;
        }
        String packName = packlistTitle.getText().toString();
        String packDetails = packlistDetail.getText().toString();
        packlist.setName(packName);
        packlist.setDetails(packDetails);
        packlist.setBagIndex(currentBagId);

        if (isPhotoSet) {
            packlist.setBagPhoto(Trips_trip.getBitMapFromView(btnBagRectangle));
        }

    }

    private void disassemblePacklist(Packlist packlist) {
        if (packlist == null) {
            Log.e("assemblePacklist", "paclist = null");
            return;
        }

        packlistTitle.setText(packlist.getName());
        packlistDetail.setText(packlist.getDetails());
        currentBagId = packlist.getBagIndex();
        Bitmap packPhoto =  packlist.getBagPhoto();
        if (packPhoto != null) {
            isPhotoSet = true;
            btnBagRectangle.setImageBitmap(packPhoto);
            chooseBag(btnBagRectangle);
        } else if (currentBagId != 0) {
            chooseBag(findViewById(currentBagId));
        }
    }

    public void openGallery(View view){
        RequestCodeBag requestCode = RequestCodeBag.NONE;
        switch (view.getId()){
            case R.id.btnBagRectangle:
                imgPhoto = btnBagRectangle;
                requestCode = RequestCodeBag.RECTANGLE;
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
            Bitmap selectedImage = ViewsHandler.getInstance().getImageFromGallery(data, ActivityPacklists_add.this);
            switch (RequestCodeBag.values()[requestCode]) {
                case RECTANGLE:{                                    //bag rectangle
                    imgPhoto.getLayoutParams().height =
                            (int) ViewsHandler.getInstance().convertDpToPx(90, ActivityPacklists_add.this);
                    imgPhoto.getLayoutParams().width =
                            (int) ViewsHandler.getInstance().convertDpToPx(150, ActivityPacklists_add.this);
                    imgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgPhoto.requestLayout();
                    selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 300, 200);
                    chooseBag(findViewById(R.id.layoutBagRectangle));
                    currentBagId = R.drawable.bag_rectangle_dpi;
                    break;
                }
            }
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);

            isPhotoSet = true;
        }
    }

    public void chooseBag(View view){
        if (view == null) {
            Log.e("chooseBag", "null view");
            return;
        }
        View[] buttons = {
                findViewById(R.id.layoutBagRectangle),
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

        isPhotoSet = view.getId() == R.id.layoutBagRectangle;

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
