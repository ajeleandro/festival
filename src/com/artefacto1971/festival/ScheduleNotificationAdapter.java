package com.artefacto1971.festival;

import com.artefacto1971.festival.classes.Lineup;
import com.artefacto1971.festival.logic.ImageHandler;
import com.artefacto1971.festival.logic.GetDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleNotificationAdapter extends BaseAdapter {

	Lineup schedule;
	Context context;
    private static LayoutInflater inflater = null;
	
	public ScheduleNotificationAdapter(Lineup schedule,Context context){
		this.schedule = schedule;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount(){
		return 1;
	}

	@Override
	public Object getItem(int position) {
		return schedule;
	}

	@Override
	public long getItemId(int position) {
		return schedule.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = inflater.inflate(R.layout.layout_schedule_notification, null);  
		
		ImageView imageview = (ImageView) view.findViewById(R.id.imageView1);
        imageview.setBackgroundResource(new ImageHandler().getFestivalDrawable(schedule.getFk_festival()));

        ((TextView) view.findViewById(R.id.list_content1)).setText(schedule.getArtist());
        ((TextView) view.findViewById(R.id.list_content2)).setText(schedule.getPlace());        
        ((TextView) view.findViewById(R.id.day)).setText(new GetDate().getLineUpDay(schedule));
        ((TextView) view.findViewById(R.id.time)).setText(schedule.getTime());
        
		return view;
	}
}