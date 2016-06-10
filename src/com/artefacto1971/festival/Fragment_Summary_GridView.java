package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.Summary;
import com.artefacto1971.festival.logic.SummaryGridAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class Fragment_Summary_GridView extends Fragment{

    public static final String TAG = Fragment_Summary_GridView.class.getSimpleName();
	private SwipeRefreshLayout swipeLayout;
	ProgressBar progressBar;
	ListView listView;
	View view;
	Summary summary;	
	int festifal_fk;
	AsyncCallWS ACWS;
	
	public Fragment_Summary_GridView(){
        return;
    }
	
	public Fragment_Summary_GridView(int festifal_fk){
		this.festifal_fk = festifal_fk;
		return;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
       	 festifal_fk = savedInstanceState.getInt("festifal_fk");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view           = inflater.inflate(R.layout.layout_list, container, false);
		listView       = (ListView)    view.findViewById(R.id.ListView);
		
		progressBar    = (ProgressBar) view.findViewById(R.id.progressBar);
		
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener(){

				@Override
				public void onRefresh() {
					Toast.makeText(view.getContext(), "Loading...", Toast.LENGTH_LONG).show();
				    ACWS = new AsyncCallWS();
					ACWS.execute();
				}
			});
        
        ACWS = new AsyncCallWS();
		ACWS.execute();
		return view;
	}
	
	private void createList(){
		listView.setClickable(false);
		listView.setAdapter(new SummaryGridAdapter(view.getContext(),summary,getActivity()));
		
    	progressBar.setVisibility(View.INVISIBLE);
    	listView.setVisibility(View.VISIBLE);
	}
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {

		@Override
        protected Void doInBackground(String... params) {
			
			summary = WebServiceHandler.getSummaryList(festifal_fk, "tomorrowlandbrasil");
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
        	
        	if(summary != null)
				createList();
        	else{
        		Toast.makeText(view.getContext(), "The was a problem in the connection, please try again later.", Toast.LENGTH_LONG).show();
        		progressBar.setVisibility(View.INVISIBLE);
            	listView.setVisibility(View.VISIBLE);
        	}
        	swipeLayout.setRefreshing(false);
         }
    }
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("festifal_fk", festifal_fk);
		super.onSaveInstanceState(savedInstanceState);
	}
}
