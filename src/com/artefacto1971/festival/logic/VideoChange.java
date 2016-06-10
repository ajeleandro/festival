package com.artefacto1971.festival.logic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.artefacto1971.festival.Vimeo_Activity;
import com.artefacto1971.festival.YouTube_Activity;

public class VideoChange {

	
	public VideoChange(FragmentActivity activity, String currentVideoID, String plataform, String videoTitle){
		
		Intent intent = null;
		if(plataform.equals("vimeo"))
			intent = new Intent(activity,Vimeo_Activity.class);
		
		else //youtube
			intent = new Intent(activity,YouTube_Activity.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("currentVideoID", currentVideoID);
		bundle.putString("videoTitle", videoTitle);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, 0);
	}
}
