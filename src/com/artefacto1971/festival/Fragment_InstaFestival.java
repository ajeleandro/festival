package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.InstagramObjectsArray;
import com.artefacto1971.festival.logic.InstaRowAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class Fragment_InstaFestival extends Fragment{
	

	public static final String TAG = Fragment_InstaFestival.class.getSimpleName();
	private SwipeRefreshLayout swipeLayout;
	View view;	
	ProgressBar progressBar;
	ListView instaListView;
	InstagramObjectsArray instaArray;
	AsyncCallWS ACWS;
    int currentEventID;
    String festival_name;

	public Fragment_InstaFestival(){
		return;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 
		view = inflater.inflate(R.layout.layout_list, container, false);
		progressBar   = (ProgressBar) view.findViewById(R.id.progressBar);
		instaListView = (ListView) view.findViewById(R.id.ListView);
		
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				ACWS = new AsyncCallWS();
				ACWS.execute();
			}
		});
		
        ACWS = new AsyncCallWS();
		ACWS.execute();	
		
		return view;
	}
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			int counter = 0;
			do{
				instaArray = WebServiceHandler.getInstagramObjects("tomorrowlandbrasil","0",20);
				Log.i(TAG, instaArray.getNext_max_id());
				if(instaArray.getObjectsArray() == null)
					try {
						Log.i(TAG,instaArray.getNext_max_id());
						Log.i(TAG,"sleeping .5 sec");
						Thread.sleep(500);
						}
					catch (InterruptedException e) {
						Log.e(TAG,e.toString());
					}
				
			}while(instaArray.getObjectsArray() == null && counter++ != 10);
			
			return null;
		}
		
		@Override
        protected void onPostExecute(Void result) {
			if (instaArray.getObjectsArray() == null){
					progressBar.setVisibility(View.INVISIBLE);
					Toast.makeText(view.getContext(), "Please Reload or Try Again Later", Toast.LENGTH_LONG).show();
			}
			else{
				instaListView.setAdapter(new InstaRowAdapter(view.getContext(), instaArray.getObjectsArray()));
				progressBar.setVisibility(View.INVISIBLE);
				instaListView.setVisibility(View.VISIBLE);
			}
			swipeLayout.setRefreshing(false);
		}
	}
	
	@Override
	public void onDestroyView() {
		if(ACWS.getStatus().equals(AsyncTask.Status.RUNNING))
			ACWS.cancel(true);
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
