package com.dreamtrip.dreamtrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Trip_Items.Packlist.Packlist;
import Trip_Items.Packlist.Stuff;
import Trip_Items.TravelBooks.Post;
import Trip_Items.TravelBooks.TravelBook;
import Trip_Items.Trips_Plan.PlanPoint;
import Trip_Items.Trips_trip;

import static junit.framework.Assert.assertTrue;

public class DatabaseHelper extends SQLiteOpenHelper {

    // CONSTRUCTOR
    private DatabaseHelper(Context context) {
        super(context, "Dream_Trip_Database.db", null, 1);
    }

    //-----------------------------------------------------------------------------SINGLETON PATTERN
    // CREATING ONE STATIC DB
    private static volatile DatabaseHelper databaseHelper = null;

    static public void initDatabaseHelper(Context context){
        if (databaseHelper == null) databaseHelper = new DatabaseHelper(context);
        else Log.e("DatabaseHelper", "Already initiliased!!");
    }

    // FOR GETTING ACCESS TO DB
    public static DatabaseHelper getInstance(){
        assertTrue(databaseHelper != null);
        return databaseHelper;
    }
    //--------------------------------------------------------------------------------TABLES--------
    private String DATABASE_NAME = "DreamTrip_DB.db";

    private String TRIPS_TABLE = "Trips_table";
    private String PLAN_POINTS_TABLE = "Plan_points_table";
    private String PLACES_TABLE = "Places_table";
    private String ICONS_TABLE = "Icons_table";

    private String TRAVELBOOKS_TABLE = "Travelbooks_table";
    private String POSTS_TABLE = "Posts_table";
    private String PHOTOS_TABLE = "Photos_table";

    private String PACKLISTS_TABLE = "Packlists_table";
    private String ELEMENTS_TABLE = "Elements_table";

    //-------------------------------------------------------------------CREATING TABLES AND COLUMNS
    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATING TRAVELBOOKS_TABLE
        db.execSQL("CREATE TABLE " + TRAVELBOOKS_TABLE
                + " (TravelbookID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT, "
                + "Main_image BLOB, "
                + "Details TEXT);");

        // CREATING POSTS_TABLE
        db.execSQL("CREATE TABLE " + POSTS_TABLE
                + " (PostID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ColorBG INTEGER, "
                + "Color_textBG INTEGER, "
                + "Color_text INTEGER, "
                + "TravelbookID INTEGER, "
                + "ImageBG BLOB, "
                + "TextCaption TEXT, "
                + "Text TEXT, "
                + "PhotoUser BLOB, "
                + "FOREIGN KEY(TravelbookID) REFERENCES " + TRAVELBOOKS_TABLE + " (TravelbookID));");

        // CREATING PACKLISTS_TABLE
        db.execSQL("CREATE TABLE " + PACKLISTS_TABLE
                + " (PacklistID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT, "
                + "Main_image BLOB, "
                + "Details TEXT);");

        // CREATING ELEMENTS_TABLE
        db.execSQL("CREATE TABLE " + ELEMENTS_TABLE
                + " (ElementID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Title TEXT, "
                + "isChecked INTEGER, "
                + "PacklistID INTEGER, "
                + "FOREIGN KEY(PacklistID) REFERENCES " + PACKLISTS_TABLE + " (PacklistID));");

        // CREATING TRIPS_TABLE
        db.execSQL("CREATE TABLE " + TRIPS_TABLE
                + " (TripID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT, "
                + "PacklistID INTEGER, "
                + "TravelbookID INTEGER, "
                + "Start_date TEXT, "
                + "End_date TEXT, "
                + "Main_image BLOB, "
                + "Header_Image BLOB, "
                + "Text_color TEXT, "
                + "FOREIGN KEY(PacklistID) REFERENCES " + PACKLISTS_TABLE + " (PacklistID), "
                + "FOREIGN KEY(TravelbookID) REFERENCES " + TRAVELBOOKS_TABLE + " (TravelbookID));");

        // CREATING PLAN_POINTS_TABLE
        db.execSQL("CREATE TABLE " + PLAN_POINTS_TABLE
                + " (Point_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TripID INTEGER, "
                + "Title TEXT, "
                + "Opens TEXT, "
                + "Closes TEXT, "
                + "Address TEXT, "
                + "Color_index INTEGER, "
                + "Details TEXT, "
                + "IconID INTEGER, "
                + "FOREIGN KEY(TripID) REFERENCES " + TRIPS_TABLE + " (TripID));");
    }
    //-------------------------------------------------------------------------------INSERTING DATA

    public void insertTrip(Trips_trip trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", trip.getName());
        contentValues.put("Start_date", trip.getStartDate().toString());
        contentValues.put("End_date", trip.getEndDate().toString());
        contentValues.put("Text_color", trip.getTextColor());
        //contentValues.put("TravelbookID", 1);
        //contentValues.put("PacklistID", 1);
        //contentValues.put("Main_image", trip.getMainImage());
        //contentValues.put("Header_image", trip.getHeaderImage());

        db.insert(TRIPS_TABLE, null, contentValues);
    }

    public void insertTravelbook(TravelBook travelBook){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", travelBook.getName());
        contentValues.put("Main_image", travelBook.getPhotoIndex());
        contentValues.put("Details", travelBook.getDetails());

        db.insert(TRAVELBOOKS_TABLE, null, contentValues);
    }

    public void insertPacklist(Packlist packlist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", packlist.getName());
        contentValues.put("Main_image", packlist.getBagIndex());
        contentValues.put("Details", packlist.getDetails());

        db.insert(PACKLISTS_TABLE, null, contentValues);
    }

    public void insertPost(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ColorBG", post.getColorImg());
        contentValues.put("Color_textBG", post.getColorTextBg());
        contentValues.put("Color_text", post.getColorText());
        contentValues.put("Caption", post.getName());
        contentValues.put("Text", post.getTitlePhoto1());
        //contentValues.put("PhotoUser", post.getMainImg());
        //contentValues.put("TravelbookID", );
        //contentValues.put("ImageBG", post.getMainImg());

        db.insert(POSTS_TABLE, null, contentValues);

    }

    public void insertElement(Stuff element){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", element.getName());
        contentValues.put("isChecked", element.isChecked());

        //int packlistId = execSQL("SELECT PacklistID FROM Packlists_table ... ");
        //contentValues.put("PacklistID", packlistID);
        db.insert(ELEMENTS_TABLE, null, contentValues);
    }

    public void insertPlanPoint(PlanPoint planPoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put("TripID", planPoint.getTripID());
        contentValues.put("Details", planPoint.getOtherDetails());
        contentValues.put("Title", planPoint.getTitle());
        contentValues.put("Color_index", planPoint.getColorIndex());
        contentValues.put("Opens", planPoint.getPlace().get_opensAt().toString());
        contentValues.put("Closes", planPoint.getPlace().get_closesAt().toString());
        contentValues.put("Address", planPoint.getPlace().get_address());
        contentValues.put("IconID", planPoint.getIconIndex());

        db.insert(PLAN_POINTS_TABLE, null, contentValues);
    }

    //------------------------------------------------------------------------------UPGRADING TABLES
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIPS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + TRAVELBOOKS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PACKLISTS_TABLE + " ;");

        db.execSQL("DROP TABLE IF EXISTS " + ELEMENTS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + POSTS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PLAN_POINTS_TABLE + " ;");

        onCreate(db);
    }

}
