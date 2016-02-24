package zoom.city.android.main.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import zoom.city.android.main.data.DataItem;

/**
 * @author Andrija
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "favouriteManager";
 
    // Table name
    private static final String TABLE_FAVOURITES = "favourites";
 
    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
//    private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_DATE = "date";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_COMPANY = "company";
//	private static final String KEY_TITLE_INDICATOR = "titleIndicator";
//	private static final String KEY_MICROLOCATION = "microlocation";
//	private static final String KEY_GENRE = "genre";
//	private static final String KEY_STREET = "street";
//	private static final String KEY_WEB = "web";
//	private static final String KEY_CITY = "city";
//	private static final String KEY_AUTHOR = "author";
//	private static final String KEY_TIME = "time";
//	private static final String KEY_IMAGE2 = "image2";
//	private static final String KEY_ZONA = "zona";
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_LOGO ="logo";
//	private static final String KEY_FAX = "fax";
	private static final String KEY_PREVIEWTYPE = "previewtype";
//	private static final String KEY_PHONE1 = "phone1";
//	private static final String KEY_PHONE2 = "phone2";
//	private static final String KEY_PHONE3 = "phone3";
//	private static final String KEY_EMAIL = "email";
//	private static final String KEY_IMAGE3 = "image3";
	private static final String KEY_COMPANYID = "companyId";
//	private static final String KEY_SHARE = "share";
	private static final String KEY_SLIDER = "slider";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVOURITES_TABLE = "CREATE TABLE " + TABLE_FAVOURITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
                + KEY_COMPANYID + " TEXT,"
                + KEY_COMPANY + " TEXT,"
        		+ KEY_TITLE + " TEXT UNIQUE,"
                + KEY_DATE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_X + " TEXT,"
                + KEY_Y + " TEXT,"
                + KEY_LOGO + " TEXT,"
                + KEY_PREVIEWTYPE + " TEXT,"
                + KEY_IMAGE + " TEXT "
        		+ ")";
        db.execSQL(CREATE_FAVOURITES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    public boolean addFavourite(DataItem dataItem) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        long result;
        
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dataItem.getId());
        values.put(KEY_COMPANYID, dataItem.getCompanyId());
        values.put(KEY_COMPANY, dataItem.getCompany());
        values.put(KEY_TITLE, dataItem.getTitle());
        values.put(KEY_DATE, dataItem.getDate());
        values.put(KEY_CATEGORY, dataItem.getCategory());
        values.put(KEY_X, dataItem.getX());
        values.put(KEY_Y, dataItem.getY());
        values.put(KEY_LOGO, dataItem.getLogo());
        values.put(KEY_PREVIEWTYPE, dataItem.getPreviewtype());
        values.put(KEY_IMAGE, dataItem.getImage());
 
        // Inserting Row
        result = db.insertWithOnConflict(TABLE_FAVOURITES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close(); // Closing database connection
        
        if(result >= 0){
        	return true;
        }else{
        	return false;
        }
    }
 
    // Getting single contact
    public DataItem getFavourite(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_FAVOURITES, null, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        DataItem dataItem = new DataItem();
        dataItem.setId(cursor.getString(0));
        dataItem.setCompanyId(cursor.getString(1));
        dataItem.setCompany(cursor.getString(2));
        dataItem.setTitle(cursor.getString(3));
        dataItem.setDate(cursor.getString(4));
        dataItem.setCategory(cursor.getString(5));
        dataItem.setX(cursor.getString(6));
        dataItem.setY(cursor.getString(7));
        dataItem.setLogo(cursor.getString(8));
        dataItem.setPreviewtype(cursor.getString(9));
        dataItem.setImage(cursor.getString(10));
        
        return dataItem;
    }
     
    // Getting All
    public List<DataItem> getAllFavourites() {
        List<DataItem> favouriteList = new ArrayList<DataItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataItem dataItem = new DataItem();
                dataItem.setId(cursor.getString(0));
                dataItem.setCompanyId(cursor.getString(1));
                dataItem.setCompany(cursor.getString(2));
                dataItem.setTitle(cursor.getString(3));
                dataItem.setDate(cursor.getString(4));
                dataItem.setCategory(cursor.getString(5));
                dataItem.setX(cursor.getString(6));
                dataItem.setY(cursor.getString(7));
                dataItem.setLogo(cursor.getString(8));
                dataItem.setPreviewtype(cursor.getString(9));
                dataItem.setImage(cursor.getString(10));
                dataItem.setTitleIndicator("1");
                
                // Adding contact to list
                favouriteList.add(dataItem);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return favouriteList;
    }
 
    // Updating single
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
// 
//        // updating row
//        return db.update(TABLE_FAVOURITES, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
 
    // Deleting single
    public void deleteFavourite(DataItem dataItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVOURITES, KEY_TITLE + " = ?",
                new String[] { dataItem.getTitle() });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getFavouriteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
    /**
     * 
     * @param id Title of item
     * @return true if the item exists in the database, false otherwise 
     */
    public boolean checkFavouriteStatus(String id) {
    	Log.d("MYTAG", "DATABASE: " + id);
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITES + " WHERE " + KEY_TITLE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[] { id });
        //cursor.close();
        Log.d("MYTAG", "DATABASE: " + countQuery);
        Log.d("MYTAG", "COUNT: " + cursor.getCount());
        // return count
        if(cursor.getCount() == 0){
        	return false;
        }else{
        	return true;
        }
    }

}
