package com.artefacto1971.festival.twitter;

import com.artefacto1971.festival.MainActivity;
import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.DeveloperKey;
import com.artefacto1971.festival.logic.Database_SQLiteHelper;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.widget.ListView; 
import android.provider.BaseColumns; 
import android.database.DatabaseUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class TwitNiceActivity extends Activity implements OnClickListener{

	/**app url*/
	public final static String TWIT_URL = "tnice-android:///";
	/**Twitter instance*/
	private Twitter niceTwitter; 
	/**Broadcast receiver for when new updates are available*/
	private BroadcastReceiver niceStatusReceiver;
	
	/**request token for accessing user account*/
	private RequestToken niceRequestToken; 
	/**shared preferences to store user details*/
	private SharedPreferences nicePrefs; 
	
	private SwipeRefreshLayout swipeLayout;

    /**main view for the home timeline*/
	private ListView homeTimeline; 
    /**database helper for update data*/
	private Database_SQLiteHelper timelineHelper; 
    /**update database*/
	private SQLiteDatabase timelineDB; 
    /**cursor for handling data*/
	private Cursor timelineCursor; 
    /**adapter for mapping data*/
	private UpdateAdapter timelineAdapter;
	
	private ProgressBar Pbar;

	//for error logging 
	private String TAG = "TwitNiceActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//get the preferences for the app
		nicePrefs = getSharedPreferences("TwitNicePrefs", 0);
		//find out if the user preferences are set 
		if(nicePrefs.getString("user_token", null) == null) {
			
    		//no user preferences so prompt to sign in
		    setContentView(R.layout.layout_twitter_login);
		    //get a twitter instance for authentication
		    niceTwitter = new TwitterFactory().getInstance();
		    
		    //pass developer key and secret
		    niceTwitter.setOAuthConsumer(DeveloperKey.TWIT_KEY, DeveloperKey.TWIT_SECRET);
		    
	        //get authentication request token
		    new getOAuthRequestToken().execute();
		}
		else
		    setupTimeline(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_twitter, menu);
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.layout_twitter_intro, container,false);
			return rootView;
		}
	}
	
	/* 
	 * onNewIntent fires when user returns from Twitter authentication Web page 
	 */
	@Override
	protected void onNewIntent(Intent intent){
	    super.onNewIntent(intent);
	    //get the retrieved data
	    Uri twitURI = intent.getData();
	    //make sure the url is correct
	    if(twitURI!=null && twitURI.toString().startsWith(TWIT_URL))
	        new getOAuthAccessToken(twitURI).execute();
	}

	private void setupTimeline(boolean refreshing) {
		if (!refreshing){
			setContentView(R.layout.layout_twitter_timeline);
			
			Pbar = (ProgressBar) findViewById(R.id.progressBar);
			Pbar.setVisibility(View.VISIBLE);
			swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
	        swipeLayout.setOnRefreshListener(new OnRefreshListener() {
				
				@Override
				public void onRefresh() {
	        		Log.i(TAG, "refreshing");				
	        		setupTimeline(true);
				}
			});
			
	        swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
		}
		
		try {
			//get reference to the list view
			homeTimeline = (ListView)findViewById(R.id.homeList);
		    //instantiate database helper
			timelineHelper = new Database_SQLiteHelper(this);
		    //get the database
			timelineDB = timelineHelper.getReadableDatabase();
	    	//query the database, most recent tweets first
			timelineCursor = timelineDB.query("twitter", null, null, null, null, null, "update_time DESC");			
		    //manage the updates using a cursor
			startManagingCursor(timelineCursor);
		    //instantiate adapter
			timelineAdapter = new UpdateAdapter(this, timelineCursor);
		    //this will make the app populate the new update data in the timeline view
			homeTimeline.setAdapter(timelineAdapter);
		    //instantiate receiver class for finding out when new updates are available
			niceStatusReceiver = new TwitterUpdateReceiver();
		    //register for updates
			registerReceiver(niceStatusReceiver, new IntentFilter("TWITTER_UPDATES"));
			//setup onclick listener for tweet button 
			LinearLayout tweetClicker = (LinearLayout)findViewById(R.id.tweetbtn); 
			tweetClicker.setOnClickListener(this);
			//start the Service for updates now
			new AsyncTimelineService().execute();
			}
		catch(Exception te) {Log.e(TAG, "Failed to fetch timeline: " + te);}
		}
	
	@Override
	public void onClick(View v){
	    switch(v.getId()){
	        //sign in button pressed
	        case R.id.signin:
	        	Log.i(TAG,"care id" + niceRequestToken.toString());
	            //take user to twitter authentication web page to allow app access to their twitter account
	            String authURL = niceRequestToken.getAuthenticationURL();
	            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authURL)));
	            Pbar = (ProgressBar)findViewById(R.id.progressBarBeforeLogInTwitter);
			    Pbar.setVisibility(View.VISIBLE);
			    Button signIn = (Button)findViewById(R.id.signin);
			    signIn.setVisibility(View.INVISIBLE);
	            break;
	        
            //user has pressed tweet button
	        case R.id.tweetbtn:
                //launch tweet activity
	            startActivity(new Intent(this, NiceTweet.class));
	            break;
		  
		    default:
		    	Log.i(TAG,"nope");
		        break;
	    }
	}
	
	 private class AsyncTimelineService extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params){
			Intent serviceIntent = new Intent(getApplicationContext(), TimelineService.class);
			serviceIntent.setAction("com.artefacto1971.festival.twitter.MY_SERVICE");
			startService(serviceIntent);
			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			swipeLayout.setRefreshing(false);
			if (Pbar != null)
				Pbar.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
	 }
	
	 private class getOAuthRequestToken extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			 try {
					niceRequestToken = niceTwitter.getOAuthRequestToken(TWIT_URL);
					Log.i(TAG, niceRequestToken.toString());
				} catch (TwitterException te){
					Log.e(TAG, "Exception getOAuthRequestToken:" + te.getMessage());
					Toast.makeText(getApplicationContext(), te.getMessage() + " .Try Again Later",Toast.LENGTH_LONG ).show();
					finish();
				}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		    Button signIn = (Button)findViewById(R.id.signin);
		    signIn.setVisibility(View.VISIBLE);
			Pbar = (ProgressBar)findViewById(R.id.progressBarBeforeLogInTwitter);
		    Pbar.setVisibility(View.INVISIBLE);
		} 
	 }
	 
	 private class getOAuthAccessToken extends AsyncTask<Void, Void, Void> {

		private Uri twitURI;
		
		public getOAuthAccessToken(Uri twitURI){
			this.twitURI = twitURI;
		}
		 
		@Override
		protected Void doInBackground(Void... params) {
			 try {
				 	String oaVerifier = twitURI.getQueryParameter("oauth_verifier");
				 	AccessToken accToken = niceTwitter.getOAuthAccessToken(niceRequestToken, oaVerifier);
		            //add the token and secret to shared prefs for future reference
		            nicePrefs.edit()
		                .putString("user_token", accToken.getToken())
		                .putString("user_secret", accToken.getTokenSecret())
		                .commit();
				} catch (TwitterException te){
					Log.e(TAG, "Exception getOAuthAccessToken:" + te.getMessage());
					cancel(true);
					finish();
				}
			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			setupTimeline(false);
			super.onPostExecute(result);
		} 
	 }
	 
	 /** 
	  * Class to implement Broadcast receipt for new updates 
	  */
	 class TwitterUpdateReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int rowLimit = 50; 
			if(DatabaseUtils.queryNumEntries(timelineDB, "twitter")>rowLimit) { 
			    String deleteQuery = "DELETE FROM twitter WHERE "+BaseColumns._ID+" NOT IN " + 
			        "(SELECT "+BaseColumns._ID+" FROM twitter ORDER BY "+"update_time DESC " + 
			        "limit "+rowLimit+")";   
			    timelineDB.execSQL(deleteQuery);
			}
			timelineCursor = timelineDB.query("twitter", null, null, null, null, null, "update_time DESC"); 
			startManagingCursor(timelineCursor); 
			timelineAdapter = new UpdateAdapter(context, timelineCursor); 
			homeTimeline.setAdapter(timelineAdapter);
		}
	 }
	 
	 @Override
	 public void onDestroy(){
	     super.onDestroy();
	     try {
	    	 //stop the updater Service
	         stopService(new Intent(this, TimelineService.class));
             //remove receiver register
	         unregisterReceiver(niceStatusReceiver);
             //close the database
	         timelineDB.close();
	     } 
	     catch(Exception se) { 
	    	 Log.e(TAG, "unable to stop Service or receiver");
	    	 Toast.makeText(getApplicationContext(), se.getMessage() + " .Try Again Later",Toast.LENGTH_LONG ).show();
	     } 
	 }

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Intent intent = new Intent(this,MainActivity.class);
        	startActivityForResult(intent, 0);
        	this.finish();
            return true;
            }
        return super.onKeyDown(keyCode, event);
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
        switch (item.getItemId()){
	        case android.R.id.home:
	        	Intent intent = new Intent(this,MainActivity.class);
	        	startActivityForResult(intent, 0);
	        	this.finish();
	            return true;
	        case R.id.twitter_logout:
	        	nicePrefs.edit().clear().commit();
	        	this.finish();
	        	return true;
        }
		return super.onOptionsItemSelected(item); 
	}
}