package com.dreamtrip.dreamtrip;

import android.graphics.Color;
import android.widget.ImageView;
import java.text.DateFormat;
import java.util.Date;

public class Trip_ {

    private String title;
    private Date startDate;
    private Date endDate;
    private ImageView headerImage;
    private ImageView mainImage;
    private int textColor;

//    private Trip_Plan plan;
//    private Trip_Places places;
//    private TravelBook_ travelbook;
//    private PackList_ packlist;

    public Trip_(){}

// temp
    // FAST CREATING
    public Trip_(String title, Date startDate, Date endDate, int textColor) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.textColor = textColor;
        this.headerImage = null;
        this.mainImage = null;
    }

    // FULL CONSTRUCTOR
    public Trip_(String title, Date startDate, Date endDate,
                 ImageView headerImage, ImageView mainImage,
                 int textColor) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.headerImage = headerImage;
        this.mainImage = mainImage;
        this.textColor = textColor;

//        this.packlist = new Packlist();
//        this.places = new Trip_places();
//        this.travelbook = new Travelbook(title);
//        this.plan = new Trip_Plan();
    }

    // GETTERS
    public String getTitle() {
        return title;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public ImageView getHeaderImage() {
        return headerImage;
    }
    public ImageView getMainImage() {
        return mainImage;
    }
    public int getTextColor() {
        return textColor;
    }
}
