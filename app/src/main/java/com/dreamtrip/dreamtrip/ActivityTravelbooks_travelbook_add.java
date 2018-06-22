package com.dreamtrip.dreamtrip;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import Trip_DBs.Trips_BD;
import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.Trips_trip;
import yuku.ambilwarna.AmbilWarnaDialog;

enum requestCodePhoto {
    NONE,
    MAIN,
    BACKGROUND,
}

public class ActivityTravelbooks_travelbook_add extends AppCompatActivity{
    int defaultColor;
    int defaultColorText = Color.WHITE;
    int defaultColorTextBg = Color.TRANSPARENT;
    ImageView imageButton = null;
    ImageView btnColorBg, btnColorText, btnColorTextBg;
    ImageView imgBgCustom, imgPhoto1;
    Uri imgBgCustomUri, imgPhoto1Uri;

    TextView labelTextColor, labelTextBgColor;
    ImageView saveBtn;
    TravelBook travelBookCtx;
    EditText mainTitle;
    EditText textPhoto1;

    int choosenBackGround;
    boolean isEditMode = false;
    int postEditIndex = -1;


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
        imgBgCustom = (ImageView) findViewById(R.id.imgPostBgCustom);
        imgPhoto1 = (ImageView) findViewById(R.id.imgPhoto1);
        Bundle bundle = getIntent().getExtras();
        isEditMode = bundle != null && bundle.getBoolean(Trips_BD.editBundleValue);
        postEditIndex = AdapterSwipe.exitFromEditIndex;

        saveBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                if (isEditMode) {
                    travelBookCtx.set(postEditIndex, assemblePost());
                    bundle.putString("value", "Successfully edited");
                } else {
                    travelBookCtx.add(assemblePost());
                    bundle.putString("value", "Successfully added");
                }
                startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityTravelbooks_travelbook").
                        putExtras(bundle).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
            }
        });

        if (isEditMode) {
            disassemblePost(travelBookCtx.get(postEditIndex));
        }

    }

    private Post assemblePost() {
        Post post = new Post(
                        mainTitle.getText().toString(),
                        textPhoto1.getText().toString(),
                        defaultColorText,
                        defaultColorTextBg);
        post.setMainImgUri(imgPhoto1Uri);
        setBgForPost(post);
        return post;
    }

    private void disassemblePost(Post post) {
        if (post == null) {
            return;
        }
        mainTitle.setText(post.getName());
        textPhoto1.setText(post.getTitlePhoto1());
        btnColorTextBg.setBackgroundColor(post.getColorTextBg());

        getBgFromPost(post);

        ViewsHandler.getInstance().loadImageIntoView(post.getMainImgUri(), imgPhoto1);
        imgPhoto1Uri = post.getMainImgUri();

        btnColorText.setColorFilter(post.getColorText());
        btnColorTextBg.setColorFilter(post.getColorTextBg());
    }

    public void chooseBgAndHighlight(View view){
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
                post.setBackGroundImgUri(imgBgCustomUri);
                break;
        }

   }

    private void getBgFromPost(Post post) {
        if (post.getBackGroundImgUri() != null) {
            chooseBgAndHighlight(imgBgCustom);
        } else if(post.getColorImg() != 0) {
            chooseBgAndHighlight(btnColorBg);
        }

        switch(choosenBackGround) {
            case R.id.btnColorBg:
                btnColorBg.setBackgroundColor(post.getColorImg());
                break;

            case R.id.imgPostBgCustom:
                imgBgCustomUri = post.getBackGroundImgUri();
                ViewsHandler.getInstance().
                        loadImageIntoView(post.getBackGroundImgUri(), imgBgCustom);
                break;
        }

    }

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public void openGallery(View view){
        requestCodePhoto codePhoto =  requestCodePhoto.NONE;
        switch (view.getId()){
            case R.id.imgPostBgCustom:
                codePhoto = requestCodePhoto.BACKGROUND;
                break;
            case R.id.imgPhoto1:
                codePhoto = requestCodePhoto.MAIN;
                imgPhoto1.setBackground(null);
                break;

        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(GalleryIntent, codePhoto.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCodePhoto.values()[requestCode]){
                case MAIN:
                    imgPhoto1Uri = data.getData();
                    imgPhoto1Uri = ViewsHandler.getInstance().saveTempImg(imgPhoto1Uri, this);
                    ViewsHandler.getInstance().loadImageIntoView(imgPhoto1Uri, imgPhoto1);
                    break;
                case BACKGROUND:
                    imgBgCustomUri = data.getData();
                    imgBgCustomUri = ViewsHandler.getInstance().saveTempImg(imgBgCustomUri, this);

                    ViewsHandler.getInstance().loadImageIntoView(imgBgCustomUri, imgBgCustom);
                    chooseBgAndHighlight(findViewById(R.id.layoutBgUpload));
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
                    chooseBgAndHighlight(findViewById(R.id.layoutBgColor));
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

//    protected void setFieldItem(Post post) {
//    }

    protected void getFieldItem(Post post) {

    }
}
