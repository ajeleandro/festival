package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.Lineup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ScheduleNotificationActivity extends Activity {

	ListView list;
	ProgressBar progressBar;
	Lineup schedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		list = (ListView) findViewById(R.id.ListView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
        Intent myIntent = getIntent(); // gets the previously created intent
        String json = myIntent.getStringExtra("extraJson"); 
        if(json != null){
        	Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<Lineup>(){}.getType();
            schedule = n.fromJson(json, collectionType);
            list.setAdapter(new ScheduleNotificationAdapter(schedule, getApplicationContext()));
        }
		progressBar.setVisibility(View.INVISIBLE);
		list.setVisibility(View.VISIBLE);
	}
}
