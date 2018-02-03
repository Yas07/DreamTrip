package com.dreamtrip.dreamtrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

enum RequestCodePhoto {
    NONE,
    HEADER,
    MAIN,
};

enum EditDateID {
    NONE,
    START_DATE,
    END_DATE,
};
public class ActivityMytrips_add extends AppCompatActivity {
    Button btnSave;
    ImageView imgPhoto, btnTripHeader, btnTripPhoto;
    EditText editDate, editStartDate, editEndDate;

    Calendar calendar;
    DatePickerDialog datePickerDialog = null;
    DatePickerDialog dateStartPickerDialog;
    DatePickerDialog dateEndPickerDialog;

    int year_start, month_start, day_start;
    int year_end, month_end, day_end;
    int defaultColor;

    Spinner spinner;
    String packlist;
    List<String> spinnerArray =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_add);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip");
                startActivity(intent);
            }
        });

        // Image Views - buttons to open gallery and save images
        btnTripPhoto = (ImageView) findViewById(R.id.imgTripPhoto);
        btnTripHeader = (ImageView) findViewById(R.id.imgTripHeader);
        defaultColor = R.color.myTransparent;

        // Calendar - edits to open calendar and save dates
        editStartDate = (EditText) findViewById(R.id.editStartDate);
        editEndDate = (EditText) findViewById(R.id.editEndDate);

        calendar = Calendar.getInstance();
        year_start = calendar.get(Calendar.YEAR);
        month_start = calendar.get(Calendar.MONTH);
        day_start = calendar.get(Calendar.DAY_OF_MONTH);

        year_end = calendar.get(Calendar.YEAR);
        month_end = calendar.get(Calendar.MONTH);
        day_end = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        editStartDate.setText(currentDate);
        editEndDate.setText(currentDate);

        // Spinner
        spinnerArray.add("Default packing list");
        spinnerArray.add("Create new packing list");
        spinnerArray.add("Children stuff");
        spinnerArray.add("Summer stuff");
        spinnerArray.add("Winter stuff");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, R.layout.spin_item, spinnerArray);

        adapter.setDropDownViewResource(R.layout.spin_item);
        spinner = (Spinner) findViewById(R.id.tripSpinnerPacklists);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packlist = spinner.getSelectedItem().toString();
                Toast.makeText(ActivityMytrips_add.this, packlist, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

//--------------------------------------------------------Pick Date Dialog
//-----------------------------------------------------------------------------------------------

    public void openDatePicker(View view){
        EditDateID editDateID = EditDateID.NONE;
        switch (view.getId()){
            case (R.id.editStartDate):
                editDateID = EditDateID.START_DATE;
                editDate = (EditText) findViewById(R.id.editStartDate);
                break;
            case (R.id.editEndDate):
                editDateID = EditDateID.END_DATE;
                editDate = (EditText) findViewById(R.id.editEndDate);
                break;
        }
        showDialog(editDateID.ordinal());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (EditDateID.values()[id]){
            case START_DATE:
                editDate = editStartDate;
                dateStartPickerDialog = new DatePickerDialog(this, dateStartPickerListener, year_start, month_start, day_start);
                return dateStartPickerDialog;
            case END_DATE:
                editDate = editEndDate;
                dateEndPickerDialog = new DatePickerDialog(this, dateEndPickerListener, year_end, month_end, day_end);
                return dateEndPickerDialog;
        }
        return datePickerDialog;
    }

    private DatePickerDialog.OnDateSetListener dateStartPickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int yearOfCalendar, int monthOfCalendar, int dayOfCalendar) {

            calendar.set(Calendar.YEAR, yearOfCalendar);
            calendar.set(Calendar.MONTH, monthOfCalendar);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfCalendar);

            year_start = yearOfCalendar;
            month_start = monthOfCalendar;
            day_start = dayOfCalendar;

            String currentDate;
            currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
            editStartDate.setText(currentDate);

//            dateEndPickerListener.onDateSet(dateStartPickerDialog.getDatePicker(), yearOfCalendar, monthOfCalendar, dayOfCalendar);
//            datePickerDialog.getDatePicker().setMinDate(0);
//            datePickerDialog.getDatePicker().setMinDate(calendar_start.getTimeInMillis());
        }
    };

    private DatePickerDialog.OnDateSetListener dateEndPickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int yearOfCalendar, int monthOfCalendar, int dayOfCalendar) {

            calendar.set(Calendar.YEAR, yearOfCalendar);
            calendar.set(Calendar.MONTH, monthOfCalendar);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfCalendar);

            year_end = yearOfCalendar;
            month_end = monthOfCalendar;
            day_end = dayOfCalendar;

            String currentDate;
            currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
            editEndDate.setText(currentDate);

//            datePickerDialog.getDatePicker().setMinDate(0);
//            datePickerDialog.getDatePicker().setMinDate(calendar_start.getTimeInMillis());
        }
    };

//--------------------------------------------------------Open from Gallery
//-----------------------------------------------------------------------------------------------

    public void openGallery(View view){
        RequestCodePhoto requestCode = RequestCodePhoto.NONE;
        switch (view.getId()){
            case R.id.imgTripHeader:
                imgPhoto = btnTripHeader;
                requestCode = RequestCodePhoto.HEADER;
                break;
            case R.id.imgTripPhoto:
                imgPhoto = btnTripPhoto;
                requestCode = RequestCodePhoto.MAIN;
                break;
        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, requestCode.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap selectedImage = ViewsHandler.setImageFromGallery(data, ActivityMytrips_add.this);
            switch (RequestCodePhoto.values()[requestCode]) {
                case HEADER:{
                    btnTripHeader.getLayoutParams().height =
                            (int) ViewsHandler.convertDpToPx(112, ActivityMytrips_add.this);
                    btnTripHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnTripHeader.requestLayout();
                    selectedImage = ViewsHandler.resizeImage(selectedImage, 800, 600);
                    break;
                }
                case MAIN: {
                    btnTripPhoto.getLayoutParams().width =
                            (int) ViewsHandler.convertDpToPx(150, ActivityMytrips_add.this);
                    btnTripPhoto.getLayoutParams().height =
                            (int) ViewsHandler.convertDpToPx(90, ActivityMytrips_add.this);
                    btnTripPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnTripPhoto.requestLayout();
                    selectedImage = ViewsHandler.resizeImage(selectedImage, 300, 200);
                    break;
                }
            }
            Drawable d = new BitmapDrawable(getResources(), selectedImage);
            imgPhoto.setImageDrawable(d);
        }
    }

//-------------------------------------------------------------Pick Color
//-----------------------------------------------------------------------------------------------

    public void openColorPicker(View view){
        final View myView = view;
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = Color.argb(150, Color.red(color), Color.green(color), Color.blue(color));
                myView.setBackgroundColor(defaultColor);
            }
        });
        colorPicker.show();
    }

}
