package com.artefacto1971.festival;

import java.util.ArrayList;

import com.artefacto1971.festival.classes.Category;
import com.artefacto1971.festival.logic.CategoryListAdapter;
import com.artefacto1971.festival.logic.RefreshAdapter;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class Fragment_CategoryList extends Fragment{

	public static final String TAG = Fragment_CategoryList.class.getSimpleName();
	private SwipeRefreshLayout swipeLayout;
	ProgressBar progressBar;
	ListView listView;
	String currentVideoCategory;
	public int event_id = 0;
	View view;
	
	public Fragment_CategoryList() {
        return;
    }
	
	public Fragment_CategoryList(int event_id) {
		this.event_id = event_id;
        return;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		if (savedInstanceState != null)
			event_id = savedInstanceState.getInt("event_id");

		view           = inflater.inflate(R.layout.layout_list, container, false);
		progressBar    = (ProgressBar) view.findViewById(R.id.progressBar);
		listView       = (ListView)    view.findViewById(R.id.ListView);
		
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
	
	private class AsyncCallWS extends AsyncTask<String, ArrayList<Category>, ArrayList<Category>> {
		
		@Override
        protected ArrayList<Category> doInBackground(String... params) {
            return WebServiceHandler.getCategories();   
		}

        @Override
        protected void onPostExecute(ArrayList<Category> CategoryList) { 

        	if(CategoryList != null){

	    		listView.setAdapter(new CategoryListAdapter(view.getContext(), CategoryList));
	    		listView.setOnItemClickListener(new OnItemClickListener(){	
					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						position++;

						TextView category = (TextView) view.findViewById(R.id.Title);

						getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
		                .replace(R.id.content_frame, new Fragment_VideoList(category.getText().toString(),event_id), TAG).commit();    					
					}
				});
        	}else{
        		listView.setAdapter(new RefreshAdapter(view.getContext(),"We coundn't Connect to the Servers",true));
        	}

        	progressBar.setVisibility(View.INVISIBLE);
        	listView.setVisibility(View.VISIBLE);
        	swipeLayout.setRefreshing(false);
         }
    }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  savedInstanceState.putInt("event_id", event_id);
	  super.onSaveInstanceState(savedInstanceState);
	}
}
