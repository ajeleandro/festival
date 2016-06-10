package com.artefacto1971.festival.twitter;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.logic.ImageDownloaderTask;

import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter; 
import android.content.Context; 
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View; 
import android.view.ViewGroup;
import android.widget.ImageView;
import android.text.format.DateUtils; 
import android.widget.TextView;
import android.provider.BaseColumns;
import android.view.View.OnClickListener; 
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent; 
import android.content.SharedPreferences; 
import android.widget.Toast; 
import android.net.Uri; 
import twitter4j.Twitter; 
import twitter4j.TwitterException; 
import twitter4j.TwitterFactory; 
import twitter4j.conf.Configuration; 
import twitter4j.conf.ConfigurationBuilder;

public class UpdateAdapter extends SimpleCursorAdapter {
    /**strings representing database column names to map to views*/
	static final String[] from = { "update_text", "user_screen", "update_time", "profile_picture" }; 
    /**view item IDs for mapping database record values to*/
	static final int[] to = { R.id.updateText, R.id.userScreen, R.id.updateTime, R.id.userImg }; 
	      
	private String TAG = "UpdateAdapter";
	LayoutInflater inflater;
	int lastPosition = -1;
	Cursor cursor;
	public UpdateAdapter(Context context, Cursor cursor) {
		
	    super(context, R.layout.layout_twitter_update, cursor, from, to);
	    this.cursor = cursor;
	    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent){

		try{
			cursor.moveToPosition(position);
			row = inflater.inflate(R.layout.layout_twitter_update, null);
			
			TextView textCreatedAt = (TextView)row.findViewById(R.id.updateTime);
			long createdAt = cursor.getLong(cursor.getColumnIndex("update_time"));
			textCreatedAt.setText(DateUtils.getRelativeTimeSpanString(createdAt)+" ");
			
			TextView tweet = (TextView)row.findViewById(R.id.updateText);
			String tweettext = cursor.getString(cursor.getColumnIndex("update_text"));
			tweet.setText(tweettext);
			
			TextView twitterUserTextView = (TextView)row.findViewById(R.id.userScreen);
			String twitterUser = cursor.getString(cursor.getColumnIndex("user_screen"));
			twitterUserTextView.setText(twitterUser);
			
			long statusID = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)); 
			String statusName = cursor.getString(cursor.getColumnIndex("user_screen")); 
			StatusData tweetData = new StatusData(statusID, statusName);

			row.findViewById(R.id.retweet).setTag(tweetData); 
			row.findViewById(R.id.reply).setTag(tweetData);
			
			row.findViewById(R.id.retweet).setOnClickListener(tweetListener); 
			row.findViewById(R.id.reply).setOnClickListener(tweetListener);
			row.findViewById(R.id.userScreen).setOnClickListener(tweetListener);
			
			ImageView imageView = (ImageView)row.findViewById(R.id.userImg);
			byte[] imageByte = cursor.getBlob(cursor.getColumnIndex("profile_picture"));
			imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
			
			//new ImageDownloaderTask((ImageView)row.findViewById(R.id.userImg)).execute(cursor.getString(cursor.getColumnIndex("user_img")));
		
			// ANIMATION
	        if((position != 0) && (position > lastPosition)){
				Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.animation_up_from_bottom);
			    animation.setDuration(600);
			    row.startAnimation(animation);
	        }
			    lastPosition = position;
		}
		catch(Exception e){

		};
		return row;
	}
	
	/** 
	 * tweetListener handles clicks of reply and retweet buttons 
	 * - also handles clicking the user name within a tweet 
	 */
	private OnClickListener tweetListener = new OnClickListener() { 
	    //onClick method 
	    public void onClick(View v) { 
	  
	    	//which view was clicked 
	    	switch(v.getId()) { 
	    	        //reply button pressed 
	    	    case R.id.reply: 
	    	        //implement reply 
    	    	    //create an intent for sending a new tweet 
	    	    	Intent replyIntent = new Intent(v.getContext(), NiceTweet.class); 
    	    	    //get the data from the tag within the button view 
	    	    	StatusData theData = (StatusData)v.getTag(); 
    	    	    //pass the status ID 
	    	    	replyIntent.putExtra("tweetID", theData.getID()); 
    	    	    //pass the user name 
	    	    	replyIntent.putExtra("tweetUser", theData.getUser()); 
    	    	    //go to the tweet screen 
	    	    	v.getContext().startActivity(replyIntent);
	    	        break; 
	    	        //retweet button pressed 
	    	    case R.id.retweet: 
	    	    	
	    	    	Intent replyIntent2 = new Intent(v.getContext(), NiceTweet.class); 
	    	    	StatusData theData2 = (StatusData)v.getTag(); 
	    	    	replyIntent2.putExtra("retweet", 1);
	    	    	replyIntent2.putExtra("retweetText",((TextView)((LinearLayout)v.getParent().getParent()).findViewWithTag("updateText")).getText().toString());
	    	    	replyIntent2.putExtra("tweetID", theData2.getID()); 
	    	    	replyIntent2.putExtra("tweetUser", theData2.getUser()); 
	    	    	v.getContext().startActivity(replyIntent2);
	    	    	break;
	    	    	
	    	    case R.id.userScreen: 
	    	        //implement visiting user profile 
	    	        //get the user screen name 
	    	    	TextView tv = (TextView)v.findViewById(R.id.userScreen); 
	    	    	String userScreenName = tv.getText().toString();
    	    	    //open the user's profile page in the browser
	    	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW,
    	    	    Uri.parse("http://twitter.com/"+userScreenName));
	    	    	v.getContext().startActivity(browserIntent);
	    	        break; 
	    	    default: 
	    	        break; 
	    	}
	    } 
	};
	
	
}