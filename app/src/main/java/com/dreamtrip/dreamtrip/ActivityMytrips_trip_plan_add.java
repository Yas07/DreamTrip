package com.dreamtrip.dreamtrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import Trip_DBs.Trips_BD;
import Trip_Items.Places.Place;
import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Trips_Plan.PlanPoint;
import Trip_Items.Trips_trip;
import layout.FragmentPlan_eating;
import layout.FragmentPlan_extreme;
import layout.FragmentPlan_hotel;
import layout.FragmentPlan_museum;
import layout.FragmentPlan_photo;
import layout.FragmentPlan_summer;
import layout.FragmentPlan_transport;
import layout.FragmentPlan_walk;
import layout.FragmentPlan_winter;

public class ActivityMytrips_trip_plan_add extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    int DATE_NOT_SET = -1;

    EditText editTime;
    int hours, minutes;
    Calendar calendar;
    Trips_trip tripCtx;
    SimpleDateFormat timeFormat;
    ImageButton saveTimePointBtn;

    //DateTime in Plan
    int day, month, year, hour, minute;
    int dayFinal = DATE_NOT_SET,
            monthFinal = DATE_NOT_SET,
            yearFinal = DATE_NOT_SET,
            hourFinal = DATE_NOT_SET,
            minuteFinal = DATE_NOT_SET;

    // NOTE: pp -- plan point
    EditText ppTitle;
    EditText ppOpenAt;
    EditText ppCloseAt;
    EditText ppDateTime;
    EditText ppAddress;
    EditText ppOtherDetails;

    int currentFragment;
    int currentIcon;

    //EditText editTimeOpen, editTimeClose, editDateTimeVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFragment = R.id.btnTransport;
        setContentView(R.layout.mytrips_trip_plan_add);
        timeFormat = new SimpleDateFormat("HH:mm");
        calendar = new GregorianCalendar();
        saveTimePointBtn = (ImageButton) findViewById(R.id.imageButton6);
        ppTitle = (EditText) findViewById(R.id.editText8);
        ppOpenAt = (EditText) findViewById(R.id.editTimeOpen);
        ppCloseAt = (EditText) findViewById(R.id.editTimeClose);
        ppDateTime = (EditText) findViewById(R.id.editDateTimeVisit);
        ppAddress = (EditText) findViewById(R.id.editText16);
        ppOtherDetails = (EditText) findViewById(R.id.editText15);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        saveTimePointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTimePoint(view);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripCtx = Trips_trip.getCurrentTrip();

        if (tripCtx == null) {
            Log.e("Activity_trip", "Cannot resolve trip");
            return;
        }

        Fragment fragment;
        fragment = new FragmentPlan_hotel();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentPlace, fragment);
        ft.commit();

        //DateTime in Plan
        ppDateTime = (EditText) findViewById(R.id.editDateTimeVisit);
        ppDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityMytrips_trip_plan_add.this,
                        ActivityMytrips_trip_plan_add.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    // DateTime in Plan
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityMytrips_trip_plan_add.this,
                ActivityMytrips_trip_plan_add.this, hour, minute, true);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearFinal);
        c.set(Calendar.MONTH, monthFinal);
        c.set(Calendar.DAY_OF_MONTH, dayFinal);
        c.set(Calendar.HOUR_OF_DAY, hourFinal);
        c.set(Calendar.MINUTE, minuteFinal);

        String strDateTime = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime())
                + " " + timeFormat.format(c.getTime());
        //String strDateTime = dayFinal + ". " + monthFinal + ". " + yearFinal + " " + hourFinal + ":" + minuteFinal;
        ppDateTime.setText(strDateTime);
    }

//--------------------------------------------------------Pick Date / Time Dialog
//-----------------------------------------------------------------------------------------------

    public void openTimePicker(View view){
        switch (view.getId()){
            case (R.id.editTimeOpen): {
                editTime = (EditText) findViewById(R.id.editTimeOpen);
                break;
            }
            case (R.id.editTimeClose): {
                editTime = (EditText) findViewById(R.id.editTimeClose);
                break;
            }
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hoursOfDay, int minutesOfDay) {
                calendar.set(Calendar.HOUR_OF_DAY, hoursOfDay);
                calendar.set(Calendar.MINUTE, minutesOfDay);
                editTime.setText(timeFormat.format(calendar.getTime()));
                //timeFormat.parse(formatted); //parse to calendar format
            }
        }, hours, minutes, true);
        timePickerDialog.show();
    }

//--------------------------------------------------------Change Fragment and Icons
//-----------------------------------------------------------------------------------------------

    public void changeFragment(View view){
        View[] buttons = {
                findViewById(R.id.btnTransport),
                findViewById(R.id.btnHotel),
                findViewById(R.id.btnEating),
                findViewById(R.id.btnMuseum),
                findViewById(R.id.btnPhoto),
                findViewById(R.id.btnWalk),
                findViewById(R.id.btnExtreme),
                findViewById(R.id.btnWinter),
                findViewById(R.id.btnSummer)};
        for (View temp: buttons)
            temp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view.setBackgroundColor(getResources().getColor(R.color.colorActiveButton));

        Fragment fragment;
        fragment = null;
        currentFragment =  view.getId();
        switch (view.getId()){
            case R.id.btnTransport: {
                fragment = new FragmentPlan_transport(); break;}
            case R.id.btnHotel: {
                fragment = new FragmentPlan_hotel(); break;}
            case R.id.btnEating:{
                fragment = new FragmentPlan_eating(); break;}
            case R.id.btnMuseum: {
                fragment = new FragmentPlan_museum(); break;}
            case R.id.btnPhoto: {
                fragment = new FragmentPlan_photo(); break;}
            case R.id.btnWalk: {
                fragment = new FragmentPlan_walk(); break;}
            case R.id.btnExtreme: {
                fragment = new FragmentPlan_extreme(); break;}
            case R.id.btnWinter: {
                fragment = new FragmentPlan_winter(); break;}
            case R.id.btnSummer: {
                fragment = new FragmentPlan_summer(); break;}
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentPlace, fragment);
        ft.commit();
    }

    // TODO: remove all changeIcon functions and make one small

    // TODO: this functions need farther development if used more than one icon
    public void changeIconHotel(View view) {
        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);
        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconTransport(View view) {
        View[] buttons = {
                findViewById(R.id.btnTransAirplane),
                findViewById(R.id.btnTransBike),
                findViewById(R.id.btnTransBus),
                findViewById(R.id.btnTransCar),
                findViewById(R.id.btnTransFeet),
                findViewById(R.id.btnTransShip),
                findViewById(R.id.btnTransTaxi),
                findViewById(R.id.btnTransTrain),
                findViewById(R.id.btnTransTram)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconEating(View view) {
        View[] buttons = {
                findViewById(R.id.btnEatRestaurant),
                findViewById(R.id.btnEatSushi),
                findViewById(R.id.btnEatFastfood),
                findViewById(R.id.btnEatBakery),
                findViewById(R.id.btnEatBar),
                findViewById(R.id.btnEatCafe),
                findViewById(R.id.btnEatHomefood),
                findViewById(R.id.btnEatOther),
                findViewById(R.id.btnEatRoom),
                findViewById(R.id.btnEatPizza),
                findViewById(R.id.btnEatCanteen)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);



        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconOutside(View view) {
        View[] buttons = {
                findViewById(R.id.btnOutSun),
                findViewById(R.id.btnOutPets),
                findViewById(R.id.btnOutOther),
                findViewById(R.id.btnOutAmusepark),
                findViewById(R.id.btnOutCloud),
                findViewById(R.id.btnOutFitness),
                findViewById(R.id.btnOutFlower)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconInside(View view) {
        View[] buttons = {
                findViewById(R.id.btnInOther),
                findViewById(R.id.btnInVideogame),
                findViewById(R.id.btnInTv),
                findViewById(R.id.btnInSchool),
                findViewById(R.id.btnInCinema),
                findViewById(R.id.btnInFitness),
                findViewById(R.id.btnInGame),
                findViewById(R.id.btnInMuseum),
                findViewById(R.id.btnInNote),
                findViewById(R.id.btnInBowling),
                findViewById(R.id.btnInZoo),
                findViewById(R.id.btnInMall)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconExtreme(View view) {
        View[] buttons = {
                findViewById(R.id.btnExtremeWalk),
                findViewById(R.id.btnExtremeOther),
                findViewById(R.id.btnExtremeGlid),
                findViewById(R.id.btnExtremeClimb),
                findViewById(R.id.btnExtremeBoard),
                findViewById(R.id.btnExtremeArchery),
                findViewById(R.id.btnExtremeBonfire),
                findViewById(R.id.btnExtremeCamp),
                findViewById(R.id.btnExtremeBalloon),
                findViewById(R.id.btnExtremeBike)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconSummer(View view) {
        View[] buttons = {
                findViewById(R.id.btnSummerBeach),
                findViewById(R.id.btnSummerSun),
                findViewById(R.id.btnSummerBoat),
                findViewById(R.id.btnSummerCocktail),
                findViewById(R.id.btnSummerFisch),
                findViewById(R.id.btnSummerMarina),
                findViewById(R.id.btnSummerOther),
                findViewById(R.id.btnSummerSail),
                findViewById(R.id.btnSummerShip),
                findViewById(R.id.btnSummerSnorkel),
                findViewById(R.id.btnSummerSurf),
                findViewById(R.id.btnSummerSwim),
                findViewById(R.id.btnSummerWaterski)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconWinter(View view) {
        View[] buttons = {
                findViewById(R.id.btnWinterBoard),
                findViewById(R.id.btnWinterOther),
                findViewById(R.id.btnWinterSanta),
                findViewById(R.id.btnWinterSkate),
                findViewById(R.id.btnWinterSki),
                findViewById(R.id.btnWinterSki2),
                findViewById(R.id.btnWinterSki3),
                findViewById(R.id.btnWinterSled),
                findViewById(R.id.btnWinterSnow),
                findViewById(R.id.btnWinterSnowman),
                findViewById(R.id.btnWinterTree)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public void changeIconWalk(View view) {
        View[] buttons = {
                findViewById(R.id.btnWalkCapitol),
                findViewById(R.id.btnWalkCastle),
                findViewById(R.id.btnWalkCastle2),
                findViewById(R.id.btnWalkChurch),
                findViewById(R.id.btnWalkFountain),
                findViewById(R.id.btnWalkFrance),
                findViewById(R.id.btnWalkKiev),
                findViewById(R.id.btnWalkLiberty),
                findViewById(R.id.btnWalkMosque),
                findViewById(R.id.btnWalkNefertiti),
                findViewById(R.id.btnWalkOther),
                findViewById(R.id.btnWalkPyramid),
                findViewById(R.id.btnWalkTemple)};
        for (View temp: buttons)
            temp.setBackgroundColor(Color.TRANSPARENT);

        String iconName = (String)  view.getTag();
        int resId = getIconIDbyName(iconName);
        setCurrentIcon(resId);

        view.setBackgroundColor(getResources().getColor(R.color.transparentWhite));
    }

    public int getIconIDbyName(String name) {
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


    public void setCurrentIcon(int currentIcon) {
        this.currentIcon = currentIcon;
    }

    private int fragmentToColor(int fragment) {

        switch (fragment){
            case R.id.btnPhoto:
            case R.id.btnExtreme:
                return R.drawable.note_green_mdpi;

            case R.id.btnTransport:
            case R.id.btnHotel:
                return R.drawable.note_violet_mdpi;

            case R.id.btnSummer:
            case R.id.btnEating:
                return R.drawable.note_yellow_mdpi;

            case R.id.btnMuseum:
                return R.drawable.note_pink_mdpi;

            case R.id.btnWalk:
            case R.id.btnWinter:
                return R.drawable.note_blue_mdpi;
        }
        return -1;
    }

    private int getCurrentIcon () {
        return currentIcon;
    }


    private Date combineDates(Date date, Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal.getTime();
    }

    private Calendar getTimeDateCalFromEdit(EditText editText) {
        if (yearFinal == DATE_NOT_SET ||
                monthFinal == DATE_NOT_SET ||
                dayFinal == DATE_NOT_SET ||
                hourFinal == DATE_NOT_SET ||
                minuteFinal == DATE_NOT_SET) {
            Log.e("getTimeDateClaFromEdit", "date time not set!");
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
        return cal;
    }

    private Calendar getCalendarFromEdit(EditText editText) {
        Calendar cal = new GregorianCalendar();

        Date tmpDate = null;
        try {
            tmpDate = timeFormat.parse(editText.getText().toString());
            cal.setTime(tmpDate);
            return cal;
        } catch (ParseException e) {
            Log.e("getCalendarFromEdit","Failed to parse editText");
            return null;
        }
    }

    private Place getPlace() {

        return new Place(getCalendarFromEdit(ppOpenAt),
                                getCalendarFromEdit(ppCloseAt),
                                ppAddress.getText().toString()
        );
    }

    public void saveTimePoint(View view) {
        try {

            if (ppTitle.getText().toString().equals("")) throw new Exception("Enter a title!");
            if (getCurrentIcon() == 0) throw new Exception("Choose icon on second line!");
            if (getTimeDateCalFromEdit(ppDateTime) == null)
                throw new Exception("Choose date and time of visiting!");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        PlanPoint planPoint = new PlanPoint(ppTitle.getText().toString(),
                fragmentToColor(currentFragment),
                getCurrentIcon(),
                getPlace(),
                getTimeDateCalFromEdit(ppDateTime),
                ppOtherDetails.getText().toString()
        );
        tripCtx.getPlan().add(planPoint);
        Toast.makeText(this, "Plan point was added successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent("com.dreamtrip.dreamtrip.ActivityMytrips_trip").putExtras(tripCtx.getBundle()));
    }
}
