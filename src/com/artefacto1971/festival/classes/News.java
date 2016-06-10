package com.artefacto1971.festival.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;

public class News {

	private int ID;
    private String title;
	private String body;
	private Date date;
	private int fk_festival;

	public News(int ID, String title, String body, Date date,int fk_festival) {
        this.ID = ID;
        this.title = title;
        this.body = body;
        this.date = date;
        this.fk_festival = fk_festival;
    }
	
	public News(Cursor cursor) throws ParseException{
		super();
		this.ID = cursor.getInt(0);
		this.title = cursor.getString(1);
		this.body = cursor.getString(2);
		this.date = new SimpleDateFormat("EEE MMM dd").parse(cursor.getString(3));
		this.fk_festival = cursor.getInt(4);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }     
        
    public int getFk_festival() {
	return fk_festival;
	}

	public void setFk_festival(int fk_festival) {
		this.fk_festival = fk_festival;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}

