package com.artefacto1971.festival.classes;
import java.util.Date;

import com.artefacto1971.festival.Notificator;
import com.artefacto1971.festival.R;
import com.artefacto1971.festival.logic.ImageHandler;
import com.artefacto1971.festival.logic.ScheduleAdapter.RowType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Lineup implements ScheduleItemInterface{

	private int ID;
	private Date date;
	private String time;
	private String place;
	private String artist;
	private int fk_festival;
	
	public Lineup(int iD, Date date, String time, String place, String artist, int fk_festival) {
		super();
		ID = iD;
		this.date = date;
		this.time = time;
		this.place = place;
		this.artist = artist;
		this.fk_festival = fk_festival;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getFk_festival() {
		return fk_festival;
	}
	public void setFk_festival(int fk_festival) {
		this.fk_festival = fk_festival;
	}
	@Override
	public int getViewType() {
		 return RowType.LIST_ITEM.ordinal();
	}
	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
		
        if (convertView == null)
            view = (View) inflater.inflate(R.layout.layout_schedule_list_item, null);
        else 
            view = convertView;

        ImageView imageview = (ImageView) view.findViewById(R.id.imageView1);
        imageview.setBackgroundResource(new ImageHandler().getFestivalDrawable(fk_festival));
        
        TextView text1 = (TextView) view.findViewById(R.id.list_content1);
        TextView text2 = (TextView) view.findViewById(R.id.list_content2);
        text1.setText(artist);
        text2.setText(place + "");
        
        Button button = (Button) view.findViewById(R.id.notify);
        button.setOnClickListener(new lineupNotification(this, inflater.getContext()));

        return view;
	}
	
	public class lineupNotification implements OnClickListener
	   {

	     Lineup lineup;
	     Context context;
	     public lineupNotification(Lineup lineup, Context context) {
	          this.lineup = lineup;
	          this.context = context;
	     }

	     @Override
	     public void onClick(View v)
	     {
	    	new Notificator().Schedule(context,lineup);	
	     }

	  };
}
