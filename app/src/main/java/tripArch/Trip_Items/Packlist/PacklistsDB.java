package Trip_Items.Packlist;

import java.util.Comparator;
import java.util.TreeMap;

public class PacklistsDB extends TreeMap<String, Packlist>{

    private static PacklistsDB _instance;

    public PacklistsDB() {
        super();
    }

    // static methods
    private static void init()
    {
        assert (_instance == null);
        _instance = new PacklistsDB();
    }

    public static PacklistsDB getInstance()
    {
        if (_instance == null) {
            init();
        }
        return _instance;
    }

    public Packlist put(Packlist p) {
        p.addToDb();
        return super.put(p.getName(), p);
    }

    public Packlist remove(String packName) {
        get(packName).removeFromDb();
        return super.remove(packName);
    }
}
