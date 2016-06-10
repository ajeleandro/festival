package com.artefacto1971.festival.logic;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class YouTubeListener implements OnInitializedListener {
	
	private String video = "";
	
	public YouTubeListener(String id) {
			video = id;
	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player, boolean arg2) {
		player.cueVideo(video);
	}
	
	@Override
	public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub		
	}
}
