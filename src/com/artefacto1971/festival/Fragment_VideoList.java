package com.artefacto1971.festival;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.Category;
import com.artefacto1971.festival.classes.EventVideo;
import com.artefacto1971.festival.logic.RefreshAdapter;
import com.artefacto1971.festival.logic.VideoChange;
import com.artefacto1971.festival.logic.VideoListAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

@SuppressLint("ValidFragment")
public class Fragment_VideoList extends Fragment{

	private ArrayList<EventVideo> globalEventVideoList;
	public static final String TAG = Fragment_VideoList.class.getSimpleName();
	private SwipeRefreshLayout swipeLayout;
	ProgressBar progressBar;
	ListView vimeolistview;
	String currentVideoID = "0";
	static final String VIDEO_ID = "0";
	static final String VIDEOCATEGORY = "0";
	static final String EVENTID = "0";
	public String currentVideoCategory = "0";
	public int event_id = 0;
	View view;
	
	public Fragment_VideoList(String video_category, int event_id){
		this.currentVideoCategory = video_category;		
		this.event_id = event_id;		
        return;
    }
	
	public Fragment_VideoList(){
        return;
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	super.onCreateView(inflater, container, savedInstanceState);

	view = inflater.inflate(R.layout.layout_list, container, false);

	if (savedInstanceState != null){
		  currentVideoID = savedInstanceState.getString("currentVideoID");
		  currentVideoCategory = savedInstanceState.getString("currentVideoCategory");
		  event_id = savedInstanceState.getInt("event_id");
	}
	
	if (currentVideoID == "0")
		  currentVideoID = "12027904";

	progressBar    = (ProgressBar) view.findViewById(R.id.progressBar);
	vimeolistview  =  (ListView)   view.findViewById(R.id.ListView);
	
	swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
	swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
    swipeLayout.setOnRefreshListener(new OnRefreshListener() {

		@Override
		public void onRefresh() {
			new AsyncCallWS().execute();
		}
	});

	new AsyncCallWS().execute();

	return view;
	}
	
	private class AsyncCallWS extends AsyncTask<String, ArrayList<EventVideo>, ArrayList<EventVideo>> {
			
			@Override
	        protected ArrayList<EventVideo> doInBackground(String... params) {
	            return WebServiceHandler.getLastestVideos(view.getContext(),currentVideoCategory,event_id);
	        }

	        @Override
	        protected void onPostExecute(ArrayList<EventVideo> eventVideoList) { 
	        	
	        	if(eventVideoList != null){
		        	globalEventVideoList = eventVideoList;
		        	if(eventVideoList.size() > 0){
			        	vimeolistview.setAdapter(new VideoListAdapter(view.getContext(), eventVideoList));
			        	vimeolistview.setOnItemClickListener(new OnItemClickListener() {	
							@Override
							public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
								
								EventVideo festivalvideo = (EventVideo) vimeolistview.getItemAtPosition(position);
								currentVideoID = festivalvideo.getCode();
								new VideoChange(getActivity(), currentVideoID, festivalvideo.getPlataform(), globalEventVideoList.get(position).getTitle());
							}
						});
		        	}
		        	else{
		        		vimeolistview.setAdapter(new RefreshAdapter(view.getContext(),"",true));
		        	}
	        	}
	        	else{
	        		vimeolistview.setAdapter(new RefreshAdapter(view.getContext(),"We coundn't Connect to the Servers",true));
	        	}
	        	progressBar.setVisibility(View.INVISIBLE);
	        	vimeolistview.setVisibility(View.VISIBLE);
	        	swipeLayout.setRefreshing(false);
	         }
	    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  savedInstanceState.putString("currentVideoID", currentVideoID);
	  savedInstanceState.putString("currentVideoCategory", currentVideoCategory);
	  savedInstanceState.putInt("event_id", event_id);
	  super.onSaveInstanceState(savedInstanceState);
	}
}
