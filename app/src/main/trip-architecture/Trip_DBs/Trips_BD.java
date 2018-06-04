package Trip_DBs;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import Trip_Items.Trips_trip;

public class Trips_BD extends DB<Date, Trips_trip> {

    public Trips_BD(Comparator comparator ){
        super(comparator);
    }

    public void add(Trips_trip trip)
    {
        add(trip.getStartDate(), trip);
    }

    public void remove(Trips_trip trip)
    {
        remove(trip.getStartDate(), trip);
    }

    public Collection<Trips_trip> getValuesSortByDate()
    {
        return getValuesSortByKey();
    }

    public Collection<Trips_trip> getValuesSortByTripName()
    {
        return getValuesSortByName();
    }
}

