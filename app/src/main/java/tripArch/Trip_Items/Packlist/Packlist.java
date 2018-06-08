package Trip_Items.Packlist;

import java.util.LinkedList;

import Trip_DBs.IDB;

public class Packlist extends LinkedList<Stuff> implements Comparable, IDB {

    private String         _name;

    public Packlist(String _name) {
        super();
        this._name = _name;
    }

    public String getName() {
        return _name;
    }

    public Stuff find(String stuffName) {
        for (Stuff s: this) {
            if (s.getName().equals(stuffName)) {
               return s;
            }
        }
        return null;
    }

    @Override
    public boolean add(Stuff stuff) {
        stuff.addToDb();
        return super.add(stuff);
    }

    @Override
    public boolean remove(Object o) {
        ((Stuff)o).removeFromDb();
        return super.remove(o);
    }

    public boolean remove(String stuffName) {
        Stuff stuff = find(stuffName);
        if (stuff == null) {
            return false;
        }
        return remove(stuff);
    }

    @Override
    public int compareTo(Object o) {
        Packlist p = (Packlist)o;
        return _name.compareTo(p._name);
    }

    @Override
    public void addToDb() {
        // sql magic, should add only Packlist data
    }

    @Override
    public void removeFromDb() {
        // sql magic, should remove only Packlist data
    }

}
