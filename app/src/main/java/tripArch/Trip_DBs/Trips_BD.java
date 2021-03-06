package Trip_DBs;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import Trip_Items.TravelBooks.TravelBooksDB;
import Trip_Items.Trips_trip;

public class Trips_BD extends DB<Date, Trips_trip> {

    // fields
    private static Trips_BD _instance;
    public static final String bundleValue = "titleAndDates";
    public static final String editBundleValue = "isEditMode";
    public static final String saveBundleValue = "saveBundleValue";

    protected Trips_BD(Comparator comparator ){
        super(comparator);
    }

    // static methods
    private static void init()
    {
        assert (_instance == null);
        _instance = new Trips_BD(new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                return ((Comparable)o).compareTo(t1);
            }
        });
    }

    public static Trips_BD getInstance()
    {
        if (_instance == null) {
            init();
        }
        return _instance;
    }

    public Trips_trip findByBundle(Bundle bundle)
    {
        String args[] = null;

        if (bundle != null) {
            args =  bundle.getStringArray(bundleValue);
            if(args == null) {
                Log.e("Activity_trip", "Wrong arguments provided");
                return null;
            }
        } else {
            Log.e("Activity_trip", "Zero bundle, what a pity");
            return null;
        }

        String title = args[0];
        String startDate = args[1];

        Trips_trip trip = null;

        try {
            trip = Trips_BD.getInstance().find(new Date(startDate));
        } catch (IllegalArgumentException e) {
            Log.e("Activity_trip", "Cannot parse date");
        }

        return trip;
    }

    public void add(Trips_trip trip)
    {
//        Trips_trip tripInBd = find(trip.getStartDate());
//        if (tripInBd != null) {
//            // remove travelbook from bd in case of overriding
//            TravelBooksDB.getInstance().remove(tripInBd.getTravelbook());
//        }
        add(trip.getStartDate(), trip);
        Trips_trip.setCurrentTrip(trip);
    }

    public void remove(Trips_trip trip)
    {
        remove(trip.getStartDate(), trip);
    }

    public Collection<Trips_trip> getValuesSortByDate()
    {
        return getValuesSortByKey();
    }

    public ArrayList<Trips_trip> findAllSimilar(String seachVal) {
        ArrayList<Trips_trip> trips = new ArrayList<Trips_trip>();
        Collection<Trips_trip> rawTrips = getValuesSortByDate();

        for (Trips_trip key : rawTrips) {
            if (key.getName().startsWith(seachVal)) {
                trips.add(key);
            }
        }
        return trips;
    }

    public Collection<Trips_trip> getValuesSortByTripName()
    {
        return getValuesSortByName();
    }

}

