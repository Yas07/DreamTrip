package com.dreamtrip.dreamtrip;


import org.junit.Test;

import java.util.TreeSet;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.PacklistsDB;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void searchTest() throws Exception {
        PacklistsDB.getInstance().put(new Packlist("Odessa", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Kyiv", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Mykolayiv", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Odeniya", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Odiseya", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Mona", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Bdesia", null, 0));
        PacklistsDB.getInstance().put(new Packlist("Odesisa", null, 0));

        String searchValue;

        searchValue = "Od";
        Log.e(searchValue, PacklistsDB.getInstance().tailMap(searchValue).toString());

        searchValue = "Odi";
        Log.e(searchValue, PacklistsDB.getInstance().tailMap(searchValue).toString());

        searchValue = "Ode";
        Log.e(searchValue, PacklistsDB.getInstance().tailMap(searchValue).toString());

        searchValue = "Odes";
        Log.e(searchValue, PacklistsDB.getInstance().tailMap(searchValue).toString());
    }
}

class Log {
    public static int d(String tag, String msg) {
        System.out.println("DEBUG: " + tag + ": " + msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        System.out.println("INFO: " + tag + ": " + msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        System.out.println("WARN: " + tag + ": " + msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        System.out.println("ERROR: " + tag + ": " + msg);
        return 0;
    }

    // add other methods if required...
}
