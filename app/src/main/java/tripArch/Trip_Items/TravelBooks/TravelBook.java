package Trip_Items.TravelBooks;

import android.graphics.Bitmap;

import java.util.LinkedList;

import Trip_DBs.IDB;
import Trip_Items.Trips_trip;

public class TravelBook extends LinkedList<Post> implements Comparable, IDB {

    private String         _name;
    private String         _details;
    private int            _photoIndex;
    private Bitmap         _photoImage;
    static private TravelBook _currentTravelBook;

    public TravelBook(String _name, String _details, int _photoIndex) {
        super();
        this._name = _name;
        this._photoIndex = _photoIndex;
        this._details = _details;
        setCurrentTravelBook(this);
    }

    public TravelBook(String _name, String _details) {
        super();
        this._name = _name;
        this._details = _details;
        setCurrentTravelBook(this);
    }

    public TravelBook(TravelBook travelBook) {
        this._name = travelBook._name;
        this._photoIndex = travelBook._photoIndex;
        this._details = travelBook._details;
        for (Post post : travelBook) {
            add(new Post(post));
        }
    }

    public String getName() {
        return _name;
    }

    public Bitmap getPhotoImage() {
        return _photoImage;
    }

    public void setPhotoImage(Bitmap _photoImage) {
        this._photoImage = Trips_trip.compressImage(_photoImage);
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

    public int getPhotoIndex() {
        return _photoIndex;
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
