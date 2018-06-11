package Trip_Items;

import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.widget.ImageView;

import Trip_DBs.DB_Item;
import Trip_DBs.Trips_BD;
import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;
import Trip_Items.Trips_Plan.Plan;
import Trip_Items.Packlist.Packlist;

public class Trips_trip extends DB_Item implements Comparable {

    // TODO: probably shouldn't be used, remove somehow
    private static Trips_trip currentTrip;

    private Date            startDate;
    private Date            endDate;
    // TODO: use something another instead of ImageView
    private ImageView       headerImage;
    private ImageView       mainImage;
    //                /\/\/\
    private int             textColor;
    private Plan            plan;
//    private Places_DB       places;
//    private TravelBook      travelbook;
    private Packlist        packList;

    // TODO: this constuctor is needed only for testing, remove in production.
    // Do not use in non-testing use cases!!
    public Trips_trip(String name, Date startDate, Date endDate, int textColor) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.textColor = textColor;
    }

    public Trips_trip(String name, Date startDate, Date endDate,
                      ImageView headerImage, ImageView mainImage,
                      int textColor) {
        this.name           = name;
        this.startDate      = startDate;
        this.endDate        = endDate;
         this.headerImage    = headerImage;
        this.mainImage      = mainImage;
        this.textColor      = textColor;
//        this.travelbook     = new Travelbook(name);
        this.plan = new Plan();
    }

    public static Trips_trip getCurrentTrip() {
        return currentTrip;
    }

    public static void setCurrentTrip(Trips_trip currentTrip) {
        Trips_trip.currentTrip = currentTrip;
    }

    public Packlist getPacklist() {
        return packList;
    }

    public void setPacklist(Packlist pack) {
        Packlist.setCurrentPacklist(pack);
        packList = pack;
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
        return name;
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

    @Override
    public void addToDb() {
        // save fields into SQL db..
    }

    @Override
    public void removeFromDb() {
        // save fields into SQL db..
    }
}

