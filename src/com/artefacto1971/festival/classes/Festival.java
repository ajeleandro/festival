package com.artefacto1971.festival.classes;

import android.database.Cursor;

public class Festival{

	private int ID;
    private String name;
    private int year;
    private boolean active;
    
    public Festival(int iD, String name, int year, boolean active) {
		super();
		ID = iD;
		this.name = name;
		this.year = year;
		this.active = active;
	}
    
    public Festival(Cursor cursor){
    	super();
    	this.ID = cursor.getInt(0);
    	this.name = cursor.getString(1);
    	this.year = cursor.getInt(2);
    	this.active = cursor.getInt(3) > 0;
    }
    
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}