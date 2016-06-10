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
import com.artefacto1971.festival.classes.Promo;
import com.artefacto1971.festival.logic.Database_DAO;
import com.artefacto1971.festival.logic.PromoAdapter;
import com.artefacto1971.festival.logic.RefreshAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

@SuppressLint("ValidFragment")
public class Fragment_Promo extends Fragment {
	
	public static final String TAG = Fragment_Promo.class.getSimpleName();
	ProgressBar progressBar;
	ArrayList<Promo> promoList = new ArrayList<>();
	ListView promoListView;
	public int event_id = 0;
	private SwipeRefreshLayout swipeLayout;
	AsyncCallWS ACWS;
	boolean inDB = false;
	View view;
	
    public  Fragment_Promo(int event_id) {
    	this.event_id = event_id;
        return;
    }
    
    public  Fragment_Promo() {
        return;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view = inflater.inflate(R.layout.layout_list, container, false);
		
		promoListView = (ListView) view.findViewById(R.id.ListView);	  
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
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         if (savedInstanceState != null)
         	event_id = savedInstanceState.getInt("event_id");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("event_id", event_id);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	private void prepareList(){
		try{
			Database_DAO dao = new Database_DAO(view.getContext());
	        dao.open();	      
	        Cursor cursor = dao.getCursor(null, "promo", event_id);
			if (cursor != null){
				cursor.moveToFirst();
				promoList = new ArrayList<>();
				do{
					promoList.add(new Promo(cursor));
				}while(cursor.moveToNext());
				inDB = true;
				createList(promoList);
			}
			dao.close();
		}
		catch(Exception e){
			Log.i(TAG, "prepareList(): " + e);
		}
	}
	
	private void createList(ArrayList<Promo> promoList){
		promoListView.setAdapter(new PromoAdapter(view.getContext(), promoList));	
    	progressBar.setVisibility(View.INVISIBLE);	        	
    	promoListView.setVisibility(View.VISIBLE);
	}
	
	private class AsyncCallWS extends AsyncTask<String,  ArrayList<Promo>,  ArrayList<Promo>> {
	
				@Override
		        protected ArrayList<Promo> doInBackground(String... params) {

					promoList = WebServiceHandler.getLastestPromo(view.getContext(),event_id);
		            return promoList;
		        }
		        
		        @Override
		        protected void onPostExecute(ArrayList<Promo> promoList) {
		        	if (promoList != null){
		        		if(promoList.size() > 0){
			 	        	Database_DAO dao = new Database_DAO(view.getContext());
			 	            dao.open();
			 	            dao.insertPromo(promoList);
			 	            dao.close();
			 	        	createList(promoList);
		        		}else{
		        			promoListView.setAdapter(new RefreshAdapter(view.getContext(),"",true));
		        		}
		 	        }else if(inDB == false){
		 	        	promoListView.setAdapter(new RefreshAdapter(view.getContext(),"We coundn't Connect to the Servers",true));
		        		progressBar.setVisibility(View.INVISIBLE);
		        		promoListView.setVisibility(View.VISIBLE);
		        	}
		        	swipeLayout.setRefreshing(false);
		         }
    }
}