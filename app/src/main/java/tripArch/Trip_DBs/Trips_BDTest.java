package Trip_DBs;
/*
import android.graphics.Color;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.NavigableSet;

import Trip_Items.Trips_trip;

import org.junit.Test;

import static org.junit.Assert.*;


class Trips_BDTest {
    @Test
    void test() {
        // create bd
        Trips_BD trips_bd = new Trips_BD(new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                return ((Trips_trip)o).compareTo(t1);
            }
        });
//        Trips_BD trips_bd = new Trips_BD(Comparator.naturalOrder());

        // create trips
        Trip_Items.Trips_trip trip2 = new Trip_Items.Trips_trip("title2",
                new Date(2018,2, 25),
                new Date(2018,4, 30), new Color());
        Trip_Items.Trips_trip trip1 = new Trip_Items.Trips_trip("title",
                new Date(2018,1, 20),
                new Date(2018,2, 21), new Color());
        Trip_Items.Trips_trip trip3 = new Trip_Items.Trips_trip("title3",
                new Date(2017,1, 20),
                new Date(2017,2, 21), new Color());
        Trip_Items.Trips_trip trip4 = new Trip_Items.Trips_trip("title",
                new Date(2018,1, 20),
                new Date(2018,2, 21), new Color());
        // add trips
        trips_bd.add(trip1);
        trips_bd.add(trip2);
        trips_bd.add(trip3);
        trips_bd.add(trip4);

        // check public NavigableSet<VALUE> findAll(KEY key)
        // trip1 and trip4 are equal
        {
            NavigableSet<Trips_trip> set = trips_bd.findAll(trip1.getStartDate());
            assertTrue(set.contains(trip1) && set.contains(trip4));
        }

        // check public NavigableSet<VALUE> findAll(String name)
        {
            NavigableSet<Trips_trip> set = trips_bd.findAll(trip1.getName());
            assertTrue(set.contains(trip1) && set.contains(trip4));
        }

        // check public VALUE find(KEY key, String name){
        {
            assertTrue (trip1.equals(trips_bd.find(trip1.getStartDate(), trip1.getName())));
        }


        // check public Collection<Trips_trip> getValuesSortByDate()
        {
            Collection<Trips_trip> c = trips_bd.getValuesSortByDate();
            assertTrue(c != null);
            Date date = new Date(0,0,0);
            for (Trips_trip i : c) {
                assertTrue(date.compareTo(i.getStartDate()) < 0);
                date = i.getStartDate();
                System.out.println(date.toString());
            }
        }

        // Check public Collection<Trips_trip> getValuesSortByTripName()
        {
            Collection<Trips_trip> c = trips_bd.getValuesSortByTripName();
            assertTrue(c != null);
            String tripName = "";
            for (Trips_trip i : c) {
                assertTrue(tripName.compareTo(i.getName()) < 0);
                tripName  = i.getName();
                System.out.println(tripName);
            }
        }

        // check public void remove(KEY key, String name)
        {
            trips_bd.remove(trip2.getStartDate(), trip2.getName());
            assertFalse(trip2.equals(trips_bd.find(trip2.getStartDate(), trip2.getName())));
        }

        // check public void remove(Trips_trip trip)
        {
            trips_bd.remove(trip1);
            assertFalse (trip1.equals(trips_bd.find(trip1.getStartDate(), trip1.getName())));
        }
    }
}
*/