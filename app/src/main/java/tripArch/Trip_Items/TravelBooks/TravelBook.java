package Trip_Items.TravelBooks;

import java.util.LinkedList;

import Trip_DBs.IDB;

public class TravelBook extends LinkedList<Post> implements Comparable, IDB {

    private String         _name;
    private String         _details;
    private int            _bagIndex;
    static private TravelBook _currentTravelBook;

    public TravelBook(String _name, String _details, int _bagIndex) {
        super();
        this._name = _name;
        this._bagIndex = _bagIndex;
        this._details = _details;
    }

    public TravelBook(TravelBook pack) {
        this._name = pack._name;
        this._bagIndex = pack._bagIndex;
        this._details = pack._details;
        for (Post stuff : pack) {
            add(new Post(stuff));
        }
    }

    public String getName() {
        return _name;
    }

    public Post find(String stuffName) {
        for (Post s: this) {
            if (s.getName().equals(stuffName)) {
               return s;
            }
        }
        return null;
    }

    public static TravelBook getCurrentTravelBook() {
        return _currentTravelBook;
    }

    public static void setCurrentTravelBook(TravelBook _currentTravelBook) {
        TravelBook._currentTravelBook = _currentTravelBook;
    }

    public String getDetails() {
        return _details;
    }

    public int getBagIndex() {
        return _bagIndex;
    }

    @Override
    public boolean add(Post stuff) {
        stuff.addToDb();
        return super.add(stuff);
    }

//    @Override
    public void add(int index, Post stuff) {
        stuff.addToDb();
        super.add(index, stuff);
    }


    @Override
    public boolean remove(Object o) {
        ((Post)o).removeFromDb();
        return super.remove(o);
    }

    public boolean remove(String stuffName) {
        Post stuff = find(stuffName);
        if (stuff == null) {
            return false;
        }
        return remove(stuff);
    }

    @Override
    public int compareTo(Object o) {
        TravelBook p = (TravelBook)o;
        return _name.compareTo(p._name);
    }

    @Override
    public void addToDb() {
        // sql magic, should add only TravelBook data
    }

    @Override
    public void removeFromDb() {
        // sql magic, should remove only TravelBook data
    }

}
