package Trip_Items.TravelBooks;

import android.util.Log;

import java.util.Comparator;
import java.util.TreeMap;

import Trip_DBs.Trips_BD;

public class TravelBooksDB extends TreeMap<String, TravelBook>{

    private static TravelBooksDB _instance;

    public TravelBooksDB() {
        super();
    }

    // static methods
    private static void init()
    {
        assert (_instance == null);
        _instance = new TravelBooksDB();
    }


    public static TravelBooksDB getInstance()
    {
        if (_instance == null) {
            init();
        }
        return _instance;
    }

    public String[] getStrKeys() {
        Object[] objArray = keySet().toArray();
        return keySet().toArray(new String[objArray.length]);
    }


    public TravelBook put(TravelBook p) {
        p.addToDb();
        return super.put(p.getName(), p);
    }

    public TravelBook remove(String packName) {
        TravelBook travelBook =  get(packName);
        if (travelBook == null) {
            Log.e("TravelBooksBD", "can't remove null travelbook");
            return null;
        }
        travelBook.clear();
        travelBook.removeFromDb();
        return super.remove(packName);
    }
}
