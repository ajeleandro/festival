package com.artefacto1971.festival.classes;

import android.database.Cursor;
import android.util.Log;

public class InstagramImage {

	private String id; 
	private String image_url;
	private int likes;
	private String username;
	private String profile_picture_url;
        private String caption_text;
	
	public InstagramImage(String id, String image_url, int likes,String username, String profile_picture_url, String caption_text) {
		super();
		this.id = id;
		this.image_url = image_url;
		this.likes = likes;
		this.username = username;
		this.profile_picture_url = profile_picture_url;
        this.caption_text = caption_text;
	}
	
	public InstagramImage(Cursor cursor){
		Log.i("INSTAGRAM:","ID:" + cursor.getString(0) + " url:" + cursor.getString(1));
		if(cursor.getInt(3) == 0){
			this.id = cursor.getString(0);
			this.image_url = cursor.getString(1);
			this.likes = cursor.getInt(2);
			//this.is_video = cursor.getInt(3) > 0;
			this.username = cursor.getString(4);
			this.profile_picture_url = cursor.getString(5);
	        this.caption_text = cursor.getString(6);
	    }
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile_picture_url() {
		return profile_picture_url;
	}

	public void setProfile_picture_url(String profile_picture_url) {
		this.profile_picture_url = profile_picture_url;
	}
        
        public String getCaption_text() {
		return caption_text;
	}

	public void setCaption_text(String caption_text) {
		this.caption_text = caption_text;
	}
}