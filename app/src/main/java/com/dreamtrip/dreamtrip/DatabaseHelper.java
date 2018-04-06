package com.dreamtrip.dreamtrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private String DATABASE_NAME = "Dream_Trip_Database.db";

    private String TRIPS_TABLE = "Trips_table";
    private String PLAN_POINTS_TABLE = "Plan_points_table";
    private String PLACES_TABLE = "Places_table";
    private String ICONS_TABLE = "Icons_table";

    private String TRAVELBOOKS_TABLE = "Travelbooks_table";
    private String POSTS_TABLE = "Posts_table";
    private String PHOTOS_TABLE = "Photos_table";

    private String PACKLISTS_TABLE = "Packlists_table";
    private String ELEMENTS_TABLE = "Elements_table";

    private String BACKGROUNDS_TABLE = "Backgrounds_table";
    private String ACTIVITIES_STYLES_TABLE = "Activities_styles_table";

    //-------------------------------------------------------------------CREATING TABLES AND COLUMNS
    @Override
    public void onCreate(SQLiteDatabase db) {
//        // CREATING BACKGROUNDS_TABLE
//        db.execSQL("CREATE TABLE " + BACKGROUNDS_TABLE
//                + "(BG_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Main_image BLOB NOT NULL, "
//                + "Type INTEGER);");
//
//        // CREATING ACTIVITIES_STYLES_TABLE
//        db.execSQL("CREATE TABLE " + ACTIVITIES_STYLES_TABLE
//                + "(Activity_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "ImageBG BLOB, "
//                + "ColorBG INTEGER, "
//                + "Color_textBG INTEGER, "
//                + "Color_text INTEGER), "
//                + "FOREIGN KEY(ImageBG) REFERENCES " + BACKGROUNDS_TABLE + " (BG_ID));");

        // CREATING TRAVELBOOKS_TABLE
        db.execSQL("CREATE TABLE " + TRAVELBOOKS_TABLE
                + "(TravelbookID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT NOT NULL, "
                + "Main_image BLOB NOT NULL, "
                + "Details TEXT);");

//        // CREATING POSTS_TABLE
//        db.execSQL("CREATE TABLE " + POSTS_TABLE
//                + "(PostID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Title TEXT, "
//                + "ColorBG INTEGER, "
//                + "Color_textBG INTEGER, "
//                + "Color_text INTEGER), "
//                + "TravelbookID INTEGER, "
//                + "ImageBG BLOB, "
//                + "FOREIGN KEY(TravelbookID) REFERENCES " + TRAVELBOOKS_TABLE + " (TravelbookID), "
//                + "FOREIGN KEY(ImageBG) REFERENCES " + BACKGROUNDS_TABLE + " (BG_ID));");
//
//        // CREATING PHOTOS_TABLE
//        db.execSQL("CREATE TABLE " + PHOTOS_TABLE
//                + "(PhotoID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Image BLOB NOT NULL, "
//                + "Title TEXT, "
//                + "PostID INTEGER, "
//                + "FOREIGN KEY(PostID) REFERENCES " + POSTS_TABLE + " (PostID));");

        // CREATING PACKLISTS_TABLE
        db.execSQL("CREATE TABLE " + PACKLISTS_TABLE
                + "(PacklistID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT NOT NULL, "
                + "Main_image BLOB NOT NULL, "
                + "Details TEXT);");

//        // CREATING ELEMENTS_TABLE
//        db.execSQL("CREATE TABLE " + ELEMENTS_TABLE
//                + "(ElementID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Title TEXT NOT NULL, "
//                + "isChecked INTEGER NOT NULL, "
//                + "GROUP TEXT, "
//                + "PacklistID INTEGER NOT NULL, "
//                + "FOREIGN KEY(PacklistID) REFERENCES " + PACKLISTS_TABLE + " (PacklistID));");

        // CREATING TRIPS_TABLE
        db.execSQL("CREATE TABLE " + TRIPS_TABLE
                + " (TripID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT NOT NULL, "
                + "PacklistID INTEGER, "
                + "TravelbookID INTEGER, "
                + "Start_date TEXT NOT NULL, "
                + "End_date TEXT NOT NULL, "
                + "Main_image BLOB, "
                + "Header_Image BLOB, "
                + "Text_color TEXT, "
                + "FOREIGN KEY(PacklistID) REFERENCES " + PACKLISTS_TABLE + " (PacklistID), "
                + "FOREIGN KEY(TravelbookID) REFERENCES " + TRAVELBOOKS_TABLE + " (TravelbookID));");

//        // CREATING PLACES_TABLE
//        db.execSQL("CREATE TABLE " + PLACES_TABLE
//                + "(PlaceID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Name TEXT NOT NULL, "
//                + "TripID INTEGER, "
//                + "Address TEXT), "
//                + "FOREIGN KEY(TripID) REFERENCES " + TRIPS_TABLE + " (TripID));");
//
//        // CREATING ICONS_TABLE
//        db.execSQL("CREATE TABLE " + ICONS_TABLE
//                + "(IconID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "Name TEXT NOT NULL, "
//                + "Icon_image BLOB NOT NULL, "
//                + "Activity_type INTEGER);");
//
//        // CREATING PLAN_POINTS_TABLE
//        db.execSQL("CREATE TABLE " + PLAN_POINTS_TABLE
//                + "(Point_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "TripID INTEGER, "
//                + "PlaceID INTEGER, "
//                + "Details TEXT, "
//                + "Activity_type INTEGER NOT NULL, "
//                + "IconID INTEGER NOT NULL, "
//                + "FOREIGN KEY(TripID) REFERENCES " + TRIPS_TABLE + " (TripID), "
//                + "FOREIGN KEY(PlaceID) REFERENCES "+ PLACES_TABLE+ " (PlaceID), "
//                + "FOREIGN KEY(IconID) REFERENCES " + ICONS_TABLE + " (IconID));");

    }
    //-------------------------------------------------------------------------------INSERTING DATA

    public void insertTrip(Trip_ trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", trip.getTitle());
        contentValues.put("Start_date", trip.getStartDate().toString());
        contentValues.put("End_date", trip.getEndDate().toString());
        contentValues.put("Text_color", trip.getTextColor());
        contentValues.put("TravelbookID", 1); //new travelbook
        contentValues.put("PacklistID", 1);   //old packlist
        //contentValues.put("Main_image", trip.getMainImage());
        //contentValues.put("Header_image", trip.getHeaderImage());
        db.insert(TRIPS_TABLE, null, contentValues);
    }

    public void insertTravelbook(String name, String image, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Main_image", image);
        contentValues.put("Details", details);
        db.insert(TRAVELBOOKS_TABLE, null, contentValues);
    }

    public void insertPacklist(String name, String image, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Main_image", image);
        contentValues.put("Details", details);
        db.insert(PACKLISTS_TABLE, null, contentValues);
    }

/*    public void insertPost(Travelbook_Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", post.getTitle());
        contentValues.put("ColorBG", post.getColorBG());
        contentValues.put("Color_textBG", post.getColor_textBG());
        contentValues.put("Color_text", post.getColor_text());
        contentValues.put("TravelbookID", post.getTravelbookID());
        contentValues.put("ImageBG", post.getImageBG());
        db.insert(POSTS_TABLE, null, contentValues);

        int postID = db.execSQL("SELECT PostID FROM Posts_table ... ");
        ContentValues valuesPhotos = null;
        String array[4][2] = post.getPhotosArray();
        for (int i = 0; i < array.length(); i++){
            valuesPhotos = new ContentValues();
            contentValues.put("PostID", postID);
            contentValues.put("Image", array[i][0]);
            contentValues.put("Title", array[i][1]);
            db.insert(PHOTOS_TABLE, null, valuesPhotos);
        }
    }

    public void insertElement(Packlist_element element){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", element.getTitle());
        contentValues.put("isChecked", element.getIsChecked());
        contentValues.put("Group", element.getGroup());

        int packlistId = execSQL("SELECT PacklistID FROM Packlists_table ... ");
        contentValues.put("PacklistID", packlistID);
        db.insert(ELEMENTS_TABLE, null, contentValues);
    }

    public void insertPlace(Trip_place place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", place.getName());
        contentValues.put("Address", place.getAddress());
        contentValues.put("TripID", place.getTripID());
        db.insert(PLACES_TABLE, null, contentValues);
    }

    public void insertPlanPoint(Trip_PlanPoint planPoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TripID", planPoint.getTripID());
        contentValues.put("PlaceID", planPoint.getPlaceID());
        contentValues.put("Details", place.getDetails());
        contentValues.put("Activity_type", place.getActivityType());
        contentValues.put("IconID", place.getIconID());
        db.insert(PLAN_POINTS_TABLE, null, contentValues);
    }
*/
    //------------------------------------------------------------------------------UPGRADING TABLES
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIPS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + TRAVELBOOKS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PACKLISTS_TABLE + " ;");
 /*
        db.execSQL("DROP TABLE IF EXISTS " + ELEMENTS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + POSTS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PLACES_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PLAN_POINTS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + ICONS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + BACKGROUNDS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_STYLES_TABLE + " ;");
*/
        onCreate(db);
    }

}
