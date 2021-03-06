package Trip_Items;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import Trip_DBs.DB_Item;
import Trip_DBs.Trips_BD;
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Packlist.Packlist;

public class Trips_trip extends DB_Item implements Comparable {

    // TODO: probably shouldn't be used, remove somehow
    private static Trips_trip currentTrip;

    private Date            startDate;
    private Date            endDate;
    private String            startDateStr;
    private String            endDateStr;
    // TODO: use something another instead of ImageView
    private Bitmap          headerImage;
    private Bitmap          mainImage;
    //                /\/\/\
    private int             textColor;
    private Plan            plan;
//    private Places_DB       places;
    private TravelBook travelbook;
    private Packlist        packList;

//    public Trips_trip(String name, Date startDate, Date endDate, int textColor) {
//    }

    public Trips_trip() {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.textColor = textColor;
        this.travelbook =  null;
    }

    public static Trips_trip getCurrentTrip() {
        return currentTrip;
    }

    public static void setCurrentTrip(Trips_trip currentTrip) {
        Trips_trip.currentTrip = currentTrip;
        Packlist.setCurrentPacklist(currentTrip.packList);
        TravelBook.setCurrentTravelBook(currentTrip.travelbook);
    }

    public Packlist getPacklist() {
        return packList;
    }

    public TravelBook getTravelbook() {
        return travelbook;
    }

    public void setOrUpdateTravelBook(String name, String startDateStr, Bitmap image) {
        if (travelbook == null) {
            travelbook = new TravelBook(name, startDateStr);
            TravelBooksDB.getInstance().put(travelbook);
        } else {
            travelbook.setName(name + "\n" + startDateStr);
//            travelbook.setDetails(startDateStr);
        }
        travelbook.setPhotoImage(image);
    }

    public void setPacklist(Packlist pack) {
        Packlist.setCurrentPacklist(pack);
        packList = pack;
    }

    public void setHeaderImage(Bitmap headerImage) {
        this.headerImage = compressImage(headerImage);
    }

    public void setMainImage(Bitmap mainImage) {
        this.mainImage = compressImage(mainImage);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static Bitmap compressImage(Bitmap bitmapImage){
        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
        return Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//        return bitmapImage;
    }

    public static Bitmap getBitMapFromView(View v) {
        if (v == null) {
            Log.e("getBitMapFromView", "null view");
            return null;
        }
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public Packlist getPackList() {
        return packList;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setTravelbook(TravelBook travelbook) {
        this.travelbook = travelbook;
    }

    public Bitmap getHeaderImage() {
        return headerImage;
    }

    public Bitmap getMainImage() {
        return mainImage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String startEndDateToStr() {
        DateFormat dateInstance = new SimpleDateFormat("dd.MM.yyyy");
                DateFormat.getDateInstance();
        return (String.format("%s - %s",
                dateInstance.format(getStartDate()),
                dateInstance.format(getEndDate())));
    }

    public String getName() {
        return getItemName();
    }

    public void setName(String name) {
        setItemName(name);
    }

    public int getTextColor() {
        return textColor;
    }

    public Plan getPlan() {
        if (plan == null) {
            plan = new Plan();
        }
        return plan;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putStringArray(Trips_BD.bundleValue, new String[] {getName(), getStartDate().toString()});
        return bundle;
    }

    static public Bundle getEditBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Trips_BD.editBundleValue, true);
        return bundle;
    }

    static public Bundle getSaveBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Trips_BD.saveBundleValue, true);
        return bundle;
    }

    @Override
    public String toString() {
        return "Trip_Items.Trips_trip{" +
                "\nname='" + name + '\'' +
                ", \nstartDate=" + startDate +
                ", \nendDate=" + endDate +
                ", \ntextColor=" + textColor +
                "}\n";
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Trips_trip)o).getName());
    }


    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    @Override
    public void addToDb() {

    }

    @Override
    public void removeFromDb() {
        // save fields into SQL db..
    }
}

