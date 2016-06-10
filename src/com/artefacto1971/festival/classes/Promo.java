package com.artefacto1971.festival.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;

public class Promo {

	private int id;
	private String title;
	private String body;
	private Date date;
	private String picture;
	private boolean active;
	private int fk_festival;
	
	public Promo(int id, String title, String body, Date date, String picture, boolean active, int fk_festival) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.date = date;
		this.picture = picture;
		this.active = active;
		this.fk_festival = fk_festival;
	}
	
	public Promo(Cursor cursor) throws ParseException{
		super();
		this.id = cursor.getInt(0);
		this.title = cursor.getString(1);
		this.body = cursor.getString(2);
		this.date = new SimpleDateFormat("EEE MMM dd").parse(cursor.getString(3));
		this.picture = cursor.getString(4);
		this.active = cursor.getInt(5) > 0;
		this.fk_festival = cursor.getInt(6);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getFk_festival() {
		return fk_festival;
	}
	public void setFk_festival(int fk_festival) {
		this.fk_festival = fk_festival;
	}
}
