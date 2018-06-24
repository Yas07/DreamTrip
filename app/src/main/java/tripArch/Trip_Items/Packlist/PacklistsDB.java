package Trip_Items.Packlist;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
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
    
    public ArrayList<Packlist> findAllSimilar(String seachVal) {
        ArrayList<Packlist> packlists = new ArrayList<Packlist>();
        Set<String> keys = tailMap(seachVal).keySet();
        for (String key : keys) {
           if (key.startsWith(seachVal)) {
               packlists.add(get(key));
           }
        }
        return packlists;
    }

//    public String[] getStrKeys() {
//        Object[] objArray = keySet().toArray();
//        return keySet().toArray(new String[objArray.length]);
//    }

//    public List<String> getStrKeys() {
//        Set<String> set = keySet();
//        return Arrays.asList(set.toArray(new String[size()]));
//    }

    public Packlist put(Packlist p) {
        p.addToDb();
        return super.put(p.getName(), p);
    }

    public Packlist remove(Packlist packlist) {
        return remove(packlist.getName());
    }

    public Packlist remove(String packName) {
        get(packName).removeFromDb();
        return super.remove(packName);
    }
}
