package com.artefacto1971.festival;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.News;
import com.artefacto1971.festival.logic.Database_DAO;
import com.artefacto1971.festival.logic.NewsAdapter;
import com.artefacto1971.festival.logic.RefreshAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

@SuppressLint("ValidFragment")
public class Fragment_News extends Fragment {
	
	public static final String TAG = Fragment_News.class.getSimpleName();
	ProgressBar progressBar;
	ArrayList<News> newsList = new ArrayList<News>();	
	ListView newsListView;
	public int event_id = 0;
	private SwipeRefreshLayout swipeLayout;
	View view;	
	boolean inDB = false;
	AsyncCallWS ACWS;
	
    public  Fragment_News(int event_id) {
    	this.event_id = event_id;
        return;
    }
    
    public  Fragment_News() {
        return;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		view = inflater.inflate(R.layout.layout_list, container, false);    	
		
		newsListView = (ListView) view.findViewById(R.id.ListView);	  
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

        	
			@Override
			public void onRefresh() {
		        prepareList();
				ACWS = new AsyncCallWS();
				ACWS.execute();
			}
		});
        
        prepareList();
        ACWS = new AsyncCallWS();
		ACWS.execute();
		return view;
	  }
	
	private void prepareList(){
		try{
			Database_DAO dao = new Database_DAO(view.getContext());
	        dao.open();
	        Cursor cursor = dao.getCursor(null, "news", event_id);
			if (cursor != null){
				cursor.moveToFirst();
				newsList = new ArrayList<>();
				do{
					newsList.add(new News(cursor));
				}while(cursor.moveToNext());
				inDB = true;
				createList(newsList);
			}
			dao.close();
		}
		catch(Exception e){
			Log.i(TAG, "prepareList() en Fragment_News(): " + e);
		}
	}
	
	private void createList(ArrayList<News> newsList){
		newsListView.setAdapter(new NewsAdapter(view.getContext(), newsList));
    	progressBar.setVisibility(View.INVISIBLE);
    	newsListView.setVisibility(View.VISIBLE);
	}
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         if (savedInstanceState != null)
         	event_id = savedInstanceState.getInt("event_id");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("event_id", event_id);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		if(ACWS.getStatus().equals(AsyncTask.Status.RUNNING))
			ACWS.cancel(true);
		super.onDestroy();
	}

	private class AsyncCallWS extends AsyncTask<String, ArrayList<News>, ArrayList<News>>{

			@Override
	        protected ArrayList<News> doInBackground(String... params) {

				newsList = WebServiceHandler.getLastestNews(view.getContext(),event_id);
	            return newsList;
	        }
	        
	        @Override
	        protected void onPostExecute(ArrayList<News> newsList) {
	
	        	 if (newsList != null){
	        		if(newsList.size()> 0){
		 	        	Database_DAO dao = new Database_DAO(view.getContext());
		 	            dao.open();
		 	            dao.insertNews(newsList);
		 	            dao.close();
		 	        	createList(newsList);
	        		}else{
	        			newsListView.setAdapter(new RefreshAdapter(view.getContext(),"",true));
	        		}
	 	        }
	        	else if(inDB == false){
	        		newsListView.setAdapter(new RefreshAdapter(view.getContext(),"We coundn't Connect to the Servers",true));
	        		progressBar.setVisibility(View.INVISIBLE);
	            	newsListView.setVisibility(View.VISIBLE);
	        	}
	 			swipeLayout.setRefreshing(false);
	        }
	    }
	}