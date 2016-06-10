package com.artefacto1971.festival;

import com.artefacto1971.festival.logic.HTML5WebViewVimeo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

public class Vimeo_Activity extends Activity {

	private static final String TAG = Vimeo_Activity.class.getSimpleName();
	HTML5WebViewVimeo mWebView;
	String currentVideoID = "0";
	int vimeoOrientation = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		vimeoOrientation = getResources().getConfiguration().orientation;
	    mWebView = new HTML5WebViewVimeo(this);	
	    
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
		if (savedInstanceState != null)
			mWebView.restoreState(savedInstanceState);
 	    else{		
 	    	mWebView.getSettings().setAllowFileAccess(true);
 			mWebView.getSettings().setAppCacheEnabled(true);
 			mWebView.getSettings().setSaveFormData(true);
 			mWebView.getSettings().setJavaScriptEnabled(true);
 			mWebView.getSettings().setDomStorageEnabled(true);
 			mWebView.getSettings().setLoadWithOverviewMode(true);
 			mWebView.getSettings().setAllowFileAccess(true);
 			mWebView.getSettings().setLoadsImagesAutomatically(true);
	 		currentVideoID = getIntent().getExtras().getString("currentVideoID");
	 		mWebView.loadUrl("http://player.vimeo.com/video/" + currentVideoID + "?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800");
		}		
		setContentView(mWebView.getLayout());	
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    public void onStop() {
		if(mWebView != null)
			mWebView.stopLoading();
        super.onStop();
    }

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.inCustomView()) {
                mWebView.hideCustomView();
                this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
    }
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("currentVideoID", currentVideoID);
        mWebView.saveState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case android.R.id.home:
        	if (mWebView.inCustomView()) {
                mWebView.hideCustomView();
                this.finish();
                return true;
            }
        	else
        		this.finish();
            return true;
        }
		return false; 
	}
}
