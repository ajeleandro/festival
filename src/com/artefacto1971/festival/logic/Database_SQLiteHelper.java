package com.artefacto1971.festival.logic;

import java.io.ByteArrayOutputStream;

import twitter4j.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;
import android.util.Log;

public class Database_SQLiteHelper extends SQLiteOpenHelper {
	
	private static final String TAG = Database_SQLiteHelper.class.getName();
	private static final String DATABASE_NAME = "festival.db";
	private static final String HOME_COL = BaseColumns._ID;
	private static final int DATABASE_VERSION = 1;
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_PICTURE = "picture";
	
	private static final String Festival_Table = " CREATE TABLE " +
			"festival(" +
			COLUMN_ID + " int primary key, " +
			"name varchar(20), " + 
			"year int(4), " +
			"active tinyint(1)" +
			");";
	
	private static final String Product_Table = " CREATE TABLE " +
			"product(" +
			COLUMN_ID + " int primary key, " +
			"name varchar(100), " + 
			"description varchar(300), " + 
			"file varchar(100), " + 
			"img varchar(100), " + 
			"type varchar(100), " + 
			"price float, " + 
			"buy_link text, " +
			COLUMN_PICTURE + " BLOB, " +			
			"downloadable tinyint(1)" +
			");";
	
	private static final String Instagram_Table = " CREATE TABLE " +
			"instagram(" +
			COLUMN_ID + " varchar(100) primary key, " +
			"media_url varchar(200), " +
			"likes integer , " +
			"is_video tinyint(1), " +
			"username varchar(25), " +
			"profile_picture_url varchar(200), " +
			"caption_text text, " +
			COLUMN_PICTURE + " BLOB, " +
			"profile_picture BLOB" +
			");";
	
	private static final String Promo_Table = " CREATE TABLE " +
			"promo(" +
			COLUMN_ID + " integer primary key, " +
			"title varchar(100), " +
			"body text, " +
			"date varchar(30), " +
			"picture_url text, " +
			"active tinyint(1), " +
			"fk_festival integer, " +
			COLUMN_PICTURE + " BLOB" +
			");";
	
	private static final String News_Table = " CREATE TABLE " +
			"news(" +
			COLUMN_ID + " integer primary key, " +
			"title varchar(100), " +
			"body text, " + 
			"date varchar(30), " +
			"fk_festival int " +
			");";

	private static final String twitter_Table = "CREATE TABLE " +
			"twitter(" + 
			HOME_COL + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
			"update_text TEXT, " +
		    "user_screen TEXT, " +
			"update_time INTEGER, " +
		    "user_img TEXT, " +
			"profile_picture BLOB " +
		    ");";
	
	private static final String eventVideo_Table = " CREATE TABLE " + 
			"eventVideo(" +
			COLUMN_ID + " integer primary key, " +
			"title varchar(100), " +
			"plataform varchar(30), " +
			"code varchar(150), " +
			"fk_artist int, " +
			"fk_festival int," +
			COLUMN_PICTURE + " BLOB" +
			");";
	
	//-------------------------------------------------------------------------------------------//
	public Database_SQLiteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public Database_SQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(Instagram_Table);
		db.execSQL(Product_Table);
		db.execSQL(Festival_Table);
		db.execSQL(Promo_Table);
		db.execSQL(News_Table);
		db.execSQL(twitter_Table);
		db.execSQL(eventVideo_Table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.i(TAG,"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS twitter");
		db.execSQL("VACUUM");
		onCreate(db);
	}
	
	public void dropIt(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS twitter");
		db.execSQL("VACUUM");
		onCreate(db);
	}
	
	public static ContentValues getValues(Status status) { 
        
        //prepare ContentValues to return 
	    ContentValues homeValues = new ContentValues(); 
	  
        //get the values 
	          
	    try { 
	        //get each value from the table 
		    homeValues.put(HOME_COL,   status.getId()); 
		    homeValues.put("update_text", status.getText()); 
		    homeValues.put("user_screen",   status.getUser().getScreenName()); 
		    homeValues.put("update_time",   status.getCreatedAt().getTime()); 
		    homeValues.put("user_img",   status.getUser().getProfileImageURL().toString()); 

		    //Descarga la imagen para ser almacenada.
		    ImageDownloaderTask idt = new ImageDownloaderTask();
		    Bitmap bmp = idt.downloadBitmap(status.getUser().getProfileImageURL().toString(),null);
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		    byte[] byteArray = stream.toByteArray();
		    
		    homeValues.put("profile_picture",byteArray); 		    
		} 
		catch(Exception te){ 
			Log.e(TAG, te.getMessage()); 
		}
	    
	    return homeValues; 
	}
}
