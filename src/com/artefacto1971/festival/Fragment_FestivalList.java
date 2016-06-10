package com.artefacto1971.festival;

import java.util.ArrayList;

import com.artefacto1971.festival.classes.Festival;
import com.artefacto1971.festival.logic.Database_DAO;
import com.artefacto1971.festival.logic.FestivalGridAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class Fragment_FestivalList extends Fragment{

    public static final String TAG = Fragment_FestivalList.class.getSimpleName();
	ArrayList<Festival> FestivalList;
	private SwipeRefreshLayout swipeLayout;
	ProgressBar progressBar;
	GridView gridView;
	View view;
	
	public Fragment_FestivalList(){
        return;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view           = inflater.inflate(R.layout.layout_grid, container, false);
		gridView	   = (GridView)    view.findViewById(R.id.GridView);
		
		progressBar    = (ProgressBar) view.findViewById(R.id.progressBar);
		
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
        swipeLayout.setOnRefreshListener(new OnRefreshListener(){

				@Override
				public void onRefresh() {
					new AsyncCallWS().execute();
				}
			});
        
        prepareList();
        
		new AsyncCallWS().execute();
		return view;
	}
	
	private void prepareList(){
		Database_DAO dao = new Database_DAO(view.getContext());
        dao.open();
        Cursor cursor = dao.getCursor(null, "festival", 0);
		if (cursor != null){
			cursor.moveToFirst();
			FestivalList = new ArrayList<>();
			do{
				FestivalList.add(new Festival(cursor));
			}while(cursor.moveToNext());
			createList(FestivalList);
		}
	}
	
	private void createList(ArrayList<Festival> FestivalList){
		gridView.setAdapter(new FestivalGridAdapter(view.getContext(), FestivalList));
		gridView.setOnItemClickListener(new OnItemClickListener() {	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				position++;
				String festival_name = ((TextView)view.findViewById(R.id.Title)).getText().toString();
				setGlobalEvent((int) id, festival_name);
				
				getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.content_frame,new Tabbed_Festival((int) id, festival_name), Tabbed_Festival.TAG).commit();
			}
		});
		gridView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	private class AsyncCallWS extends AsyncTask<String, ArrayList<Festival>, ArrayList<Festival>> {
		
		@Override
        protected ArrayList<Festival> doInBackground(String... params) {
			FestivalList = WebServiceHandler.getFestivals(view.getContext());
            return FestivalList;   
		}

        @Override
        protected void onPostExecute(ArrayList<Festival> FestivalList) { 
	        if (FestivalList != null){
	        	createList(FestivalList);	
	        	Database_DAO dao = new Database_DAO(view.getContext());
	            dao.open();
	            dao.insertFestival(FestivalList);
	            dao.close();
	        }
			swipeLayout.setRefreshing(false);
        }
    }
	
	public void setGlobalEvent(int event_id, String festival_name){
		((FestivalAplication) getActivity().getApplication()).setEventID(event_id);
		((FestivalAplication) getActivity().getApplication()).setFestival_name(festival_name);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}
}
