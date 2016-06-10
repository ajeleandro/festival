package com.artefacto1971.festival;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.InstagramImagesArray;
import com.artefacto1971.festival.logic.WebServiceHandler;

public class Fragment_ws_test extends Fragment {
	
		Button b;
		TextView tv;
		EditText et;
		ProgressBar pg;
		String editText;
		String displayText;
		InstagramImagesArray InstaArray;
		public static final String TAG = Fragment_ws_test.class.getSimpleName();
	
	  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	                           Bundle savedInstanceState){		  
		  
		  View view = inflater.inflate(R.layout.layout_ws_test, container, false);
		  
	       et = (EditText) view.findViewById(R.id.editText1);
	       tv = (TextView) view.findViewById(R.id.tv_result);
	       b = (Button) view.findViewById(R.id.button1);
	       pg = (ProgressBar) view.findViewById(R.id.progressBar1);
	       
	       b.setOnClickListener(new OnClickListener() {
	           @Override
			public void onClick(View v) {
	               if (et.getText().length() != 0 && et.getText().toString() != "") {
	                   editText = et.getText().toString();
	                   AsyncCallWS task = new AsyncCallWS();
	                   task.execute();
	               } else {
	                   tv.setText("Please enter name");
	               }
	           }
	       });		
		
		return view;
	  }
	  
	  
	  private class AsyncCallWS extends AsyncTask<String, Void, Void> {

			@Override
	        protected Void doInBackground(String... params) {
	            
	            InstaArray =  WebServiceHandler.getInstagramImages(editText, "0", 0);
	            return null;
	        }
	        
	        @Override
	        protected void onPostExecute(Void result) {
	            tv.setText(displayText);
	            pg.setVisibility(View.INVISIBLE);
            	
	            Log.i(TAG, InstaArray.getNext_max_id());
	            Log.i(TAG, InstaArray.getImagesArray().get(1).getImage_url());
	            displayText = "prueba";
	        }
	 
	        @Override
	        protected void onPreExecute() {
	            pg.setVisibility(View.VISIBLE);
	        }
	 
	        @Override
	        protected void onProgressUpdate(Void... values) {
	        }
	    }
	}