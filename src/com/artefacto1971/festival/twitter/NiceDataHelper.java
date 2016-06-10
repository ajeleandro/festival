package com.artefacto1971.festival.twitter;

import java.io.ByteArrayOutputStream;

import com.artefacto1971.festival.logic.ImageDownloaderTask;

import twitter4j.Status;
import android.content.ContentValues;
import android.content.Context; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 
import android.graphics.Bitmap;
import android.provider.BaseColumns; 
import android.util.Log;

public class NiceDataHelper extends SQLiteOpenHelper {

	/**db version*/
	private static final int DATABASE_VERSION = 1;
    /**database name*/
	private static final String DATABASE_NAME = "festival.db";
	/**twitter table name**/
	private static final String TWITTER_TABLE = "twitter";
    /**ID column*/
	private static final String HOME_COL = BaseColumns._ID;
    /**tweet text*/
	private static final String UPDATE_COL = "update_text";
    /**twitter screen name*/
	private static final String USER_COL = "user_screen";
    /**time tweeted*/
	private static final String TIME_COL = "update_time";
    /**user profile image*/
	private static final String USER_IMG = "user_img";
	/** profile picture*/
	private static final String PROFILE_PICTURE = "profile_picture";
	
	/**database creation string*/
	private static final String DATABASE_CREATE =
	"CREATE TABLE " + TWITTER_TABLE + " (" + 
	HOME_COL + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
	UPDATE_COL + " TEXT, " +
    USER_COL + " TEXT, " +
	TIME_COL + " INTEGER, " +
    USER_IMG + " TEXT, " +
	PROFILE_PICTURE + " BLOB);";
	
	NiceDataHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
		public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TWITTER_TABLE);
		db.execSQL("VACUUM");
		onCreate(db);
	}
	
	public void dropIt(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TWITTER_TABLE);
		db.execSQL("VACUUM");
		onCreate(db);
	}
	/** 
	 * getValues retrieves the database records 
	 * - called from TimelineUpdater in TimelineService 
	 * - this is a static method that can be called without an instance of the class 
	 *  
	 * @param status 
	 * @return ContentValues result 
	 */
	public static ContentValues getValues(Status status) { 
	          
        //prepare ContentValues to return 
	    ContentValues homeValues = new ContentValues(); 
	  
        //get the values 
	          
	    try { 
	        //get each value from the table 
		    homeValues.put(HOME_COL,   status.getId()); 
		    homeValues.put(UPDATE_COL, status.getText()); 
		    homeValues.put(USER_COL,   status.getUser().getScreenName()); 
		    homeValues.put(TIME_COL,   status.getCreatedAt().getTime()); 
		    homeValues.put(USER_IMG,   status.getUser().getProfileImageURL().toString()); 

		    //Descarga la imagen para ser almacenada.
		    ImageDownloaderTask idt = new ImageDownloaderTask();
		    Bitmap bmp = idt.downloadBitmap(status.getUser().getProfileImageURL().toString(),null);
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		    byte[] byteArray = stream.toByteArray();
		    
		    homeValues.put(PROFILE_PICTURE,byteArray); 		    
		} 
		catch(Exception te) { Log.e("NiceDataHelper", te.getMessage()); }
	    
	    return homeValues; 
	}
}