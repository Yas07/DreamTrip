package Trip_Items.Packlist;

import java.util.TreeMap;

public class PacklistsDB extends TreeMap<String, Packlist>{

    public PacklistsDB() {
        super();
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
