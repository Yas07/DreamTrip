package com.dreamtrip.dreamtrip;

// android includes
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

// java includes
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import yuku.ambilwarna.AmbilWarnaDialog;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;

// internal includes
import Trip_DBs.Trips_BD;
import Trip_Items.*;


enum enum_RequestCodePhoto {
    NONE,
    HEADER,
    MAIN,
}

enum enum_EditDateID {
    NONE,
    START_DATE,
    END_DATE,
}
public class ActivityMytrips_add extends AppCompatActivity  implements IDelEdit{
    Button btnSave;
    ImageView imgPhoto, btnTripHeader, btnTripPhoto;
    boolean isTripHeaderSet = false, isTripMainSet = false;
    ImageView btnColorPicker;
    EditText editDate, editStartDate, editEndDate, editTripTitle;

    Calendar calendar;
    DatePickerDialog datePickerDialog = null;
    DatePickerDialog dateStartPickerDialog;
    DatePickerDialog dateEndPickerDialog;

    int year_start, month_start, day_start;
    int year_end, month_end, day_end;
    int defaultColor;
    boolean isEditMode = false;

    Spinner spinner;
    String packlist = "";
    List<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_add);

        Bundle bundle = getIntent().getExtras();
        isEditMode = bundle != null && bundle.getBoolean(Trips_BD.editBundleValue);

        editTripTitle = (EditText) findViewById(R.id.editTripTitle);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (!isValidInput()) {
                    return;
                }

                Trips_trip trip  = isEditMode ? Trips_trip.getCurrentTrip() : new Trips_trip();

                Trips_trip.setCurrentTrip(trip);

                setFieldItem();

                if (!isEditMode) {
                    Trips_BD.getInstance().add(trip);
                }

                Toast.makeText(ActivityMytrips_add.this, "New trip was added successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip").putExtras(trip.getBundle()));

            }
        });


        btnColorPicker = (ImageView) findViewById(R.id.btnPickColor);

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
        // TODO: probably, should be written more flexible
        Set<String> keysSet = PacklistsDB.getInstance().keySet();
        keysSet.remove(Packlist.defaultPackName);
        List<String> strKeys = new ArrayList<>(Arrays.asList(Packlist.defaultPackName));
        strKeys.addAll(keysSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, R.layout.spin_item, strKeys);

        adapter.setDropDownViewResource(R.layout.spin_item);
        spinner = (Spinner) findViewById(R.id.tripSpinnerPacklists);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packlist = spinner.getSelectedItem().toString();
                //Toast.makeText(ActivityMytrips_add.this, packlist, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (isEditMode) {
            // set selection for spinner
            Trips_trip trip = Trips_trip.getCurrentTrip();
            if (trip != null) {
                int index = -1;
                for (int i = 0 ; i < strKeys.size() ; i++ ) {
                    if (strKeys.get(i).equals(trip.getName())) {
                        index = i;
                    }
                }

                if (index >= 0) {
                    spinner.setSelection(index);
                }
            }
            getFieldItem();
        }
    }


    private boolean isValidInput() {
        try {
            if(editTripTitle.getText().toString().equals("")) throw new Exception("Enter title!");
            if(editStartDate.getText().toString().isEmpty() ||
                    editEndDate.getText().toString().isEmpty()) throw new Exception("You should input the dates!!");
            if(new Date(editStartDate.getText().toString()).after(
                    new Date(editEndDate.getText().toString()))) throw new Exception("End date should be bigger!");
            if (!isTripMainSet) throw new Exception("No main image was set!");
            if (!isTripHeaderSet) throw new Exception("No header image was set!");
            return true;
        } catch (Exception e) {
            Toast.makeText(ActivityMytrips_add.this, "ERROR - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

//--------------------------------------------------------Pick Date Dialog
//-----------------------------------------------------------------------------------------------

    public void openDatePicker(View view){
        enum_EditDateID editDateID = enum_EditDateID.NONE;
        switch (view.getId()){
            case (R.id.editStartDate):
                editDateID = enum_EditDateID.START_DATE;
                editDate = (EditText) findViewById(R.id.editStartDate);
                break;
            case (R.id.editEndDate):
                editDateID = enum_EditDateID.END_DATE;
                editDate = (EditText) findViewById(R.id.editEndDate);
                break;
        }
        showDialog(editDateID.ordinal());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (enum_EditDateID.values()[id]){
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
        enum_RequestCodePhoto requestCode = enum_RequestCodePhoto.NONE;
        switch (view.getId()){
            case R.id.imgTripHeader:
                imgPhoto = btnTripHeader;
                requestCode = enum_RequestCodePhoto.HEADER;
                break;
            case R.id.imgTripPhoto:
                imgPhoto = btnTripPhoto;
                requestCode = enum_RequestCodePhoto.MAIN;
                break;
        }
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, requestCode.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap selectedImage = ViewsHandler.getInstance().getImageFromGallery(data, ActivityMytrips_add.this);
            switch (enum_RequestCodePhoto.values()[requestCode]) {
                case HEADER:{
                    btnTripHeader.getLayoutParams().height =
                            (int) ViewsHandler.getInstance().convertDpToPx(112, ActivityMytrips_add.this);
                    btnTripHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnTripHeader.requestLayout();
                    selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 800, 600);
                    isTripHeaderSet = true;
                    break;
                }
                case MAIN: {
                    btnTripPhoto.getLayoutParams().width =
                            (int) ViewsHandler.getInstance().convertDpToPx(150, ActivityMytrips_add.this);
                    btnTripPhoto.getLayoutParams().height =
                            (int) ViewsHandler.getInstance().convertDpToPx(90, ActivityMytrips_add.this);
                    btnTripPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btnTripPhoto.requestLayout();
                    selectedImage = ViewsHandler.getInstance().resizeImage(selectedImage, 300, 200);
                    isTripMainSet = true;
                    break;
                }
            }
            if (isTripMainSet || isTripHeaderSet) {
                Drawable d = new BitmapDrawable(getResources(), selectedImage);
                imgPhoto.setImageDrawable(d);
            }
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

    @Override
    public void editItem() {

    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void setFieldItem() {

        Trips_trip trip = Trips_trip.getCurrentTrip();

        if (trip == null) {
            Log.e("setFieldItem", "null trip");
            return;
        }

        String sDate = editStartDate.getText().toString();
        String eDate = editEndDate.getText().toString();

        trip.setStartDateStr(sDate);
        trip.setEndDateStr(eDate);

        trip.setName(editTripTitle.getText().toString());

        // getColor
        int color = Color.WHITE;
        Drawable background = btnColorPicker.getBackground();
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }

        trip.setTextColor(color);

        Packlist pack =  PacklistsDB.getInstance().get(packlist);
        if (pack != null) {
            trip.setPacklist(new Packlist(pack));
        } else if (packlist.equals(Packlist.defaultPackName) ) {
            trip.setPacklist(Packlist.makeDefaultPack());
        } else {
            Log.e("Create trip", "Null pack, what a pity");
        }

        if (isTripHeaderSet) {
            trip.setHeaderImage(Trips_trip.getBitMapFromView(btnTripHeader));
        }

        if (isTripMainSet) {
            trip.setMainImage(Trips_trip.getBitMapFromView(btnTripPhoto));
        }

        trip.setOrUpdateTravelBook(trip.getName(), trip.getStartDateStr(), trip.getMainImage());
    }

    @Override
    public void getFieldItem() {
        Trips_trip trip = Trips_trip.getCurrentTrip();

        if (trip == null) {
            Log.e("getFieldItem", "null trip");
            return;
        }

        editStartDate.setText(trip.getStartDateStr());
        editEndDate.setText(trip.getEndDateStr());


        editTripTitle.setText(trip.getName());

        btnColorPicker.setBackgroundColor(trip.getTextColor());

        if (trip.getHeaderImage() != null) {
            isTripHeaderSet = true;
            btnTripHeader.setImageBitmap(trip.getHeaderImage());
        }

        if (trip.getMainImage() != null) {
            isTripMainSet = true;
            btnTripPhoto.setImageBitmap(trip.getMainImage());
        }

    }

    @Override
    public void startItem() {

    }

    @Override
    public void saveItem() {

    }

}
