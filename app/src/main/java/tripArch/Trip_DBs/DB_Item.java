package Trip_DBs;

abstract public class DB_Item implements IDB {
    protected String name;
    protected String getItemName(){
        return name;
    }
}

