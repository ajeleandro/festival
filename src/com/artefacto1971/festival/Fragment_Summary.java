package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.Summary;
import com.artefacto1971.festival.logic.Database_DAO;
import com.artefacto1971.festival.logic.SummaryAdapter;
import com.artefacto1971.festival.logic.VideoChange;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class Fragment_Summary extends Fragment{

	public static final String TAG = Fragment_Summary.class.getSimpleName();
	private SwipeRefreshLayout swipeLayout;
	Summary summary;	
	View view;
	int festifal_fk;
	ListView summaryListView;
	ProgressBar progressBar;
	AsyncCallWS ACWS;
	boolean inBD = false;
	
	public Fragment_Summary(int festifal_fk){
		this.festifal_fk = festifal_fk;
		return;
	}
	
	public Fragment_Summary(){
		return;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         if (savedInstanceState != null)
        	 festifal_fk = savedInstanceState.getInt("festifal_fk");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.layout_list, container, false);	
		summaryListView = (ListView) view.findViewById(R.id.ListView);	 
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				 //prepareList();
		         ACWS = new AsyncCallWS();
				 ACWS.execute();
			}
		});
		
        //prepareList();
        ACWS = new AsyncCallWS();
		ACWS.execute();
		return view;
	}
	
	private void prepareList(){
		try{
			Database_DAO dao = new Database_DAO(view.getContext());
	        dao.open();
	        summary = dao.getSummary(festifal_fk);
			if (summary != null){
				inBD = true;
				createList();
			}
			dao.close();
		}
		catch(Exception e){
			Log.i(TAG, "prepareList(): " + e);
		}
	}
	
	private void createList(){
		summaryListView.setAdapter(new SummaryAdapter(view.getContext(), summary));
    	
    	summaryListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				if(position == 0){
					String currentVideoID;
					String plataform = summary.getEventVideo().getPlataform();
					currentVideoID = summary.getEventVideo().getCode();
					new VideoChange(getActivity(), currentVideoID, plataform,summary.getEventVideo().getTitle());
				}
			}
		});
    	progressBar.setVisibility(View.INVISIBLE);
    	summaryListView.setVisibility(View.VISIBLE);
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {

		@Override
        protected Void doInBackground(String... params) {
			
			if(inBD == false)
				summary = WebServiceHandler.getSummaryList(festifal_fk, "tomorrowland");
			
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {

        	if(inBD == false)
	        	if(summary != null){
	        		Database_DAO dao = new Database_DAO(view.getContext());
		            dao.open();
		            dao.insertSummary(summary);
		            dao.close();
	        		createList();
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
