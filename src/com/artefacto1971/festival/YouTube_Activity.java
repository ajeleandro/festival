/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.DeveloperKey;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.os.Bundle;
import android.view.MenuItem;

public class YouTube_Activity extends YouTubeFailureRecoveryActivity {


	public static final String TAG = YouTube_Activity.class.getSimpleName();
	String currentVideoID = "0";
	String videoTitle = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.layout_youtube);

      getActionBar().setDisplayHomeAsUpEnabled(true);
      currentVideoID = getIntent().getExtras().getString("currentVideoID");
      videoTitle = getIntent().getExtras().getString("videoTitle");
      
      this.setTitle(videoTitle);
      
      YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
      youTubeView.initialize(DeveloperKey.YOUTUBE_DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
        boolean wasRestored) {
      if (!wasRestored) {      
        player.cueVideo(currentVideoID);
      }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
      return (YouTubePlayerView) findViewById(R.id.youtube_view);
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
  
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("currentVideoID", currentVideoID);
		savedInstanceState.putString("videoTitle", videoTitle);
        super.onSaveInstanceState(savedInstanceState);
    }
}