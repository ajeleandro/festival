package com.artefacto1971.festival.twitter;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.DeveloperKey;

import android.app.Activity; 
import android.content.SharedPreferences; 
import android.os.AsyncTask;
import android.os.Bundle; 
import android.util.Log; 
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.LinearLayout;
import android.widget.Toast;
import twitter4j.StatusUpdate; 
import twitter4j.Twitter; 
import twitter4j.TwitterException; 
import twitter4j.TwitterFactory; 
import twitter4j.conf.Configuration; 
import twitter4j.conf.ConfigurationBuilder;

public class NiceTweet extends Activity implements OnClickListener {

	/**shared preferences for user twitter details*/
	private SharedPreferences tweetPrefs; 
	/**twitter object**/
	private Twitter tweetTwitter; 
	  
	/**the update ID for this tweet if it is a reply*/
	private long tweetID = 0;
	/**the username for the tweet if it is a reply*/
	private String tweetName = "";
	private String retweetText = "";
	private boolean isRetweet = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    setContentView(R.layout.layout_twitter_tweet);   
	}
	
	@Override
	public void onResume() { 
	    super.onResume(); 
	        //call helper method 
	    setupTweet(); 
	}
	
	/** 
	 * Method called whenever this Activity starts 
	 * - get ready to tweet 
	 * Sets up twitter and onClick listeners 
	 * - also sets up for replies 
	 */
	private void setupTweet() { 
	    //prepare to tweet 
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null) 
		{ 
		    if (extras.getInt("retweet") == 1){

		    	isRetweet = true;
	    		tweetID = extras.getLong("tweetID");
	    		retweetText = extras.getString("retweetText");
	    		EditText theTweet = (EditText)findViewById(R.id.tweettext);
	    		theTweet.setText(retweetText);
	    		theTweet.setKeyListener(null);
	    		theTweet.setFocusable(false);
	    		theTweet.setCursorVisible(false);
	    		theTweet.setClickable(false);
	    		theTweet.setFocusableInTouchMode(false);
		    }
		    else{
		        //get the ID of the tweet we are replying to
			    tweetID = extras.getLong("tweetID");
		        //get the user screen name for the tweet we are replying to
			    tweetName = extras.getString("tweetUser");
			  
		        //use the passed information 
			    //get a reference to the text field for tweeting 
			    EditText theReply = (EditText)findViewById(R.id.tweettext); 
		        //start the tweet text for the reply @username 
			    theReply.setText("@"+tweetName+" "); 
		        //set the cursor to the end of the text for entry 
			    theReply.setSelection(theReply.getText().length());
		    }
		}
		else 
		{ 
	        EditText theReply = (EditText)findViewById(R.id.tweettext); 
	        theReply.setText(""); 
		}
		
		
		//get preferences for user twitter details 
		tweetPrefs = getSharedPreferences("TwitNicePrefs", 0); 
		          
	        //get user token and secret for authentication 
		String userToken = tweetPrefs.getString("user_token", null); 
		String userSecret = tweetPrefs.getString("user_secret", null); 
		  
	    //create a new twitter configuration usign user details 
		Configuration twitConf = new ConfigurationBuilder() 
		    .setOAuthConsumerKey(DeveloperKey.TWIT_KEY) 
		    .setOAuthConsumerSecret(DeveloperKey.TWIT_SECRET) 
		    .setOAuthAccessToken(userToken) 
		    .setOAuthAccessTokenSecret(userSecret) 
		    .build(); 
		  
	    //create a twitter instance 
		tweetTwitter = new TwitterFactory(twitConf).getInstance();
	    //set up listener for choosing home button to go to timeline 
		LinearLayout tweetClicker = (LinearLayout)findViewById(R.id.homebtn); 
		tweetClicker.setOnClickListener(this); 
		          
	    //set up listener for send tweet button 
		Button tweetButton = (Button)findViewById(R.id.dotweet); 
		tweetButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		EditText tweetTxt = (EditText)findViewById(R.id.tweettext);
		
	    //find out which view has been clicked
		switch(v.getId()) {
		case R.id.dotweet:
		    //send tweet
			String toTweet = tweetTxt.getText().toString();
			new doTweet(toTweet).execute();
	        //reset the edit text
		    tweetTxt.setText("");
		      
		    break;
		case R.id.homebtn:
		    //go to the home timeline
			tweetTxt.setText("");
		  
		    break;
		default: 
		    break;
		}
		finish();
	}
	
	private class doTweet extends AsyncTask<Void, Void, Void> {

		private String toTweet;
		
		public doTweet(String toTweet){
			this.toTweet = toTweet;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			try {
				if(isRetweet){
					tweetTwitter.retweetStatus(tweetID);
				}
				else{
					//handle replies
				    if(tweetName.length()>0)
				        tweetTwitter.updateStatus(new StatusUpdate(toTweet).inReplyToStatusId(tweetID));
				     
			        //handle normal tweets
				    else
				        tweetTwitter.updateStatus(toTweet);
				}
			} catch(TwitterException te) { Log.e("NiceTweet", te.getMessage()); }
			
			return null;
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.finish();
                return true;
            }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case android.R.id.home:
        		this.finish();
            return true;
        }
		return false; 
	}
}