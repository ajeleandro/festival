package com.artefacto1971.festival;

import java.util.ArrayList;
import java.util.List;

import com.artefacto1971.festival.classes.Lineup;
import com.artefacto1971.festival.classes.ScheduleItemInterface;
import com.artefacto1971.festival.logic.RefreshAdapter;
import com.artefacto1971.festival.logic.ScheduleAdapter;
import com.artefacto1971.festival.logic.ScheduleListHandler;
import com.artefacto1971.festival.logic.WebServiceHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

@SuppressLint("ValidFragment")
public class Fragment_ScheduleList extends Fragment{

	public static final String TAG = Fragment_ScheduleList.class.getSimpleName();
	int event_id;
	ProgressBar progressBar;
	private SwipeRefreshLayout swipeLayout;
	ListView lineupListView;
	View view;
	
	public Fragment_ScheduleList(int event_id) {
		this.event_id = event_id;
	}
	
	public Fragment_ScheduleList() {
	}
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         if (savedInstanceState != null)
        	 event_id = savedInstanceState.getInt("event_id");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.layout_list, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AsyncCallWS().execute();
			}
		});
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        lineupListView = (ListView) view.findViewById(R.id.ListView);
        new AsyncCallWS().execute();
		return view;
	}
	
	private class AsyncCallWS extends AsyncTask<String, List<ScheduleItemInterface>, List<ScheduleItemInterface>> {

		@Override
        protected List<ScheduleItemInterface> doInBackground(String... params) {
			
			ArrayList<Lineup> schedule = WebServiceHandler.getLineup(0, event_id);
			if (schedule!=null)
				return new ScheduleListHandler().GetArrangedLineup(schedule);
            return null;
        }
        
        @Override
        protected void onPostExecute(List<ScheduleItemInterface> schedule) {
        	if(schedule != null){
        		if(schedule.size() > 0){
		        	lineupListView.setAdapter(new ScheduleAdapter(view.getContext(), schedule));
        		}
        		else{
        			lineupListView.setAdapter(new RefreshAdapter(view.getContext(),"",true));
        		}
        	}
        	else{
        		lineupListView.setAdapter(new RefreshAdapter(view.getContext(),"We coundn't Connect to the Servers",true));
        	}
        	progressBar.setVisibility(View.INVISIBLE);	        	
        	lineupListView.setVisibility(View.VISIBLE);
        	swipeLayout.setRefreshing(false);
         }
    }
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("event_id", event_id);
		super.onSaveInstanceState(savedInstanceState);
	}
}
