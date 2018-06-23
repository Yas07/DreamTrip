package Trip_Items.Packlist;

import android.graphics.Bitmap;

import java.util.LinkedList;

import Trip_DBs.IDB;
import Trip_Items.Trips_trip;

public class Packlist extends LinkedList<Stuff> implements Comparable, IDB {

    static public String defaultPackName = "Default";
    static public String defaultPackNameTitle = "Check list";

    private String         _name;
    private String         _details;
    private int            _bagIndex;
    static private Packlist _currentPacklist;
    private Bitmap          _bagPhoto;

    public Packlist() {
        super();
    }

    public Packlist(String _name, String _details, int _bagIndex) {
        super();
        this._name = _name;
        this._bagIndex = _bagIndex;
        this._details = _details;
    }

    public Packlist(Packlist pack) {
        super();
        this._name = pack._name;
        this._bagIndex = pack._bagIndex;
        this._details = pack._details;
        for (Stuff stuff : pack) {
            add(new Stuff(stuff));
        }
    }

    public String getName() {
        return _name;
    }

    public static Packlist makeDefaultPack() {
        return new Packlist(defaultPackName, "", 0);
    }

    public Stuff find(String stuffName) {
        for (Stuff s: this) {
            if (s.getName().equals(stuffName)) {
               return s;
            }
        }
        return null;
    }

    public static Packlist getCurrentPacklist() {
        return _currentPacklist;
    }

    public static void setCurrentPacklist(Packlist _currentPacklist) {
        Packlist._currentPacklist = _currentPacklist;
    }

    public String getDetails() {
        return _details;
    }

    public int getBagIndex() {
        return _bagIndex;
    }

    public Bitmap getBagPhoto() {
        return _bagPhoto;
    }


    public void setBagPhoto(Bitmap _bagPhoto) {
        this._bagPhoto = Trips_trip.compressImage(_bagPhoto);
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setDetails(String _details) {
        this._details = _details;
    }

    public void setBagIndex(int _bagIndex) {
        this._bagIndex = _bagIndex;
    }

    @Override
    public boolean add(Stuff stuff) {
        stuff.addToDb();
        return super.add(stuff);
    }

//    @Override
    public void add(int index, Stuff stuff) {
        stuff.addToDb();
        super.add(index, stuff);
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
