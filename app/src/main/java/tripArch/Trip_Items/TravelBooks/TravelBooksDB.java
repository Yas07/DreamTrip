package Trip_Items.TravelBooks;

import java.util.Comparator;
import java.util.TreeMap;

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
        get(packName).removeFromDb();
        return super.remove(packName);
    }
}
