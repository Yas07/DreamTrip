package Trip_Items;

import android.graphics.Color;

import java.util.Date;

import android.widget.ImageView;

import Trip_DBs.DB_Item;
import Trip_Items.Trips_Plan.Plan;

public class Trips_trip extends DB_Item implements Comparable {
    private Date            startDate;
    private Date            endDate;
    private ImageView       headerImage;
    private ImageView       mainImage;
    private Color textColor;
    private Plan            plan;
//    private Places_DB       places;
//    private TravelBook      travelbook;
//    private PackList        packList;

    // TODO: this constuctor is needed only for testing, remove in production.
    // Do not use in non-testing use cases!!
    public Trips_trip(String name, Date startDate, Date endDate, Color textColor) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.textColor = textColor;
    }

    public Trips_trip(String name, Date startDate, Date endDate,
                      ImageView headerImage, ImageView mainImage,
                      Color textColor) {
        this.name           = name;
        this.startDate      = startDate;
        this.endDate        = endDate;
        this.headerImage    = headerImage;
        this.mainImage      = mainImage;
        this.textColor      = textColor;
//        this.packlist       = packlist;
//        this.travelbook     = new Travelbook(name);
        this.plan = new Plan();
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
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

