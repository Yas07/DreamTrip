package Trip_DBs;
import android.util.Log;

import com.google.common.collect.TreeMultimap;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

abstract class DB<KEY, VALUE extends DB_Item>{
    private TreeMap<KEY,VALUE> _keyValueDB;
    private TreeMultimap<String,VALUE> _stringValueDB;

    // ** public methods.. **
    public DB(Comparator<KEY> keyComparator, Comparator<VALUE> valueComparator, Comparator<String> stringComparator)
    {
        _keyValueDB = new TreeMap<KEY,VALUE>(keyComparator);
        _stringValueDB = TreeMultimap.create(stringComparator, valueComparator);
    }
    public DB(Comparator comparator)
    {
        _keyValueDB =  new TreeMap<KEY,VALUE>(comparator); //TreeMultimap.create(comparator, comparator);
        _stringValueDB = TreeMultimap.create(comparator, comparator);
    }


    public void add(KEY key, VALUE value)
    {
        _keyValueDB.put(key, value);
        _stringValueDB.put(value.getItemName(), value);
        value.addToDb();
    }


    public NavigableSet<VALUE> findAll(String name)
    {
        return _stringValueDB.get(name);
    }

    public VALUE find(KEY key)
    {
        return _keyValueDB.get(key);
    }

//    public VALUE find(KEY key, String name){
//        NavigableSet<VALUE> set = findAll(key);
//        for (VALUE item: set) {
//            if (item.getItemName().equals(name)){
//                return item;
//            }
//        }
//        return null;
//    }

    public void remove(KEY key, VALUE value)
    {
        _keyValueDB.remove(key);
        _stringValueDB.remove(value.getItemName(), value);
        value.removeFromDb();
    }

//    public void remove(KEY key, String name)
//    {
//        VALUE value = find(key, name);
//        if (value != null) {
//            _keyValueDB.remove(key);
//            _stringValueDB.remove(name, value);
//        } else {
//          // TODO: print something..
//        }
//    }

    protected Collection<VALUE> getValuesSortByName() {
        return _stringValueDB.values();
    }

    protected Collection<VALUE> getValuesSortByKey() {
        return _keyValueDB.values();
    }

    public int size() {
        if (_keyValueDB.size() != _stringValueDB.size()) {
            Log.e("DB", "different size!!!");
        }
        return _keyValueDB.size();
    }

    public SortedMap<String, Collection<VALUE>> tailMap(String str){
        return _stringValueDB.asMap().tailMap(str);
    }

    public void clear() {
        _stringValueDB.clear();
        _keyValueDB.clear();
    }

}

