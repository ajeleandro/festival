package com.artefacto1971.festival;

import java.util.ArrayList;

import com.artefacto1971.festival.classes.Product;
import com.artefacto1971.festival.logic.MarketplaceAdapter;
import com.artefacto1971.festival.logic.RefreshAdapter;
import com.artefacto1971.festival.logic.WebServiceHandler;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Build;

public class MarketplaceActivity extends Activity {
	 private long enqueue;
	    public DownloadManager dm;	
	    public SwipeRefreshLayout swipeLayout;
		ProgressBar progressBar;
		ListView listView;
		Button button;
	 
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_list);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        listView = (ListView) findViewById(R.id.ListView);
	        
			progressBar    = (ProgressBar) findViewById(R.id.progressBar);
			listView       = (ListView)    findViewById(R.id.ListView);
			
			swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
			swipeLayout.setColorScheme(R.color.control_one_opaque,R.color.control_two,R.color.control_two_opaque,R.color.control_one);
	        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					new AsyncCallWS().execute();
				}
			});
	       
	        
	        new AsyncCallWS().execute();
	    }
	    
	    private class AsyncCallWS extends AsyncTask<String, ArrayList<Product>, ArrayList<Product>> {
			
			@Override
	        protected ArrayList<Product> doInBackground(String... params) {
				BroadcastReceiver receiver = new receiver();
		        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
	            return WebServiceHandler.getProducts(getApplicationContext());
			}

	        @Override
	        protected void onPostExecute(ArrayList<Product> ProductList) { 

	        	if(ProductList != null){
		    		listView.setAdapter(new MarketplaceAdapter(getApplicationContext(), ProductList,dm,enqueue));
	        	}else{
	        		listView.setAdapter(new RefreshAdapter(getApplicationContext(),"We coundn't Connect to the Servers",true));
	        	}

	        	progressBar.setVisibility(View.INVISIBLE);
	        	listView.setVisibility(View.VISIBLE);
	        	swipeLayout.setRefreshing(false);
	         }
	    }
	    
    private class receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Query query = new Query();
                query.setFilterById(enqueue);
                Cursor c = dm.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {

                    	Toast.makeText(getApplicationContext(), "Download Successfull",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
