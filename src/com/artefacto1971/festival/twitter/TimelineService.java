package com.artefacto1971.festival.twitter;

import com.artefacto1971.festival.classes.DeveloperKey;
import com.artefacto1971.festival.logic.Database_SQLiteHelper;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class TimelineService extends Service {

    /**twitter object*/
	private Twitter timelineTwitter;
	/**updater thread object*/
	private TimelineUpdater niceUpdater;
	
	/**database helper object*/
	private Database_SQLiteHelper niceHelper; 
	/**timeline database*/
	private SQLiteDatabase niceDB;
	
	Configuration twitConf;
	
    /**shared preferences for user details*/
	private SharedPreferences nicePrefs; 
    /**handler for updater*/
	private Handler niceHandler; 
    /**delay between fetching new tweets*/
	private static int mins = 5;//alter to suit 
	private static final long FETCH_DELAY = mins * (60*1000); 
    //debugging tag 
	private String LOG_TAG = "TimelineService";

	
	@Override
	public void onCreate() { 
		try{
		    super.onCreate(); 
		    //setup the class    
		    //get prefs 
		    nicePrefs = getSharedPreferences("TwitNicePrefs", 0); 
	        //get database helper 
		    niceHelper = new Database_SQLiteHelper(this); 
	        //get the database 
		    niceDB = niceHelper.getWritableDatabase();
		    
		    //get user preferences 
		    String userToken = nicePrefs.getString("user_token", null); 
		    String userSecret = nicePrefs.getString("user_secret", null); 
		      
	        //create new configuration 
		    twitConf = new ConfigurationBuilder() 
		        .setOAuthConsumerKey(DeveloperKey.TWIT_KEY) 
		        .setOAuthConsumerSecret(DeveloperKey.TWIT_SECRET) 
		        .setOAuthAccessToken(userToken) 
		        .setOAuthAccessTokenSecret(userSecret) 
		        .build(); 
		        //instantiate new twitter 
		    timelineTwitter = new TwitterFactory(twitConf).getInstance();
		}
		catch(Exception te) {Log.e(LOG_TAG, "TimelineService exception: " + te.getMessage());}
	    
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) { 
	    super.onStart(intent, startId); 
        //get handler 
	    niceHandler = new Handler(); 
        //create an instance of the updater class
		niceUpdater = new TimelineUpdater(); 
        //add to run queue 
	    
	    niceHandler.post(niceUpdater); 
        //return sticky 
	    return START_STICKY; 
	}
	
	@Override
	public IBinder onBind(Intent intent) { 
	    return null; 
	}	
	
	@Override
	public void onDestroy() { 
	    super.onDestroy(); 
        //stop the updating 
	    niceHandler.removeCallbacks(niceUpdater); 
	    niceDB.close(); 
	}
	
	/** 
	 * TimelineUpdater class implements the runnable interface 
	 */
	class TimelineUpdater implements Runnable {
		
	    //check for updates - assume none 
		boolean statusChanges = false;

		@Override
		public void run() {
			//fetch updates
		    //fetch timeline
		    //retrieve the new home timeline tweets as a list
			
			new Thread(){
				@Override
				public void run() {
					//List<Status> homeTimeline = null; descomentar para tener home timeline
					try {
					    Query query = new Query("#ultra");
					    query.count(20);
					    QueryResult result = timelineTwitter.search(query);
						
						//buscar timeline:
						//homeTimeline = timelineTwitter.getHomeTimeline(); descomentar para tener home timeline
						//for (Status statusUpdate : homeTimeline) descomentar para tener home timeline
					    
					    //iterate through new status updates
						for (Status statusUpdate : result.getTweets())
						{
							//call the getValues method of the data helper class, passing the new updates
						    ContentValues timelineValues = Database_SQLiteHelper.getValues(statusUpdate);
					        //if the database already contains the updates they will not be inserted
						    
						    //test: para borrar la bd
						    //niceHelper.dropIt(niceDB);
						    
					    	try {
								niceDB.insertOrThrow("twitter", null, timelineValues);
						        //confirm we have new updates
							    statusChanges = true;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), e.getMessage() + " .Try Again Later",Toast.LENGTH_LONG ).show();
								e.printStackTrace();
							}
						}
					}
					catch (Exception te) {
						Log.e(LOG_TAG, "TimelineService exception 2:  " + te);
					}
					
					try {
						//if we have new updates, send a Broadcast
						if (statusChanges)
						{
						//this should be received in the main timeline class
						    sendBroadcast(new Intent("TWITTER_UPDATES"));
						}
						//delay fetching new updates
						niceHandler.postDelayed(this, FETCH_DELAY);
					} catch (Exception e) {
						Log.e(LOG_TAG, "TimelineService exception 3:  " + e);
					}
				}
			}.start();
		}
	}
}