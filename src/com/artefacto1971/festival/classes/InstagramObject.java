package com.artefacto1971.festival.classes;

import android.database.Cursor;

public class InstagramObject {

	private String id; 
	private String media_url;
	private int likes;
	private boolean is_video;
	private String username;
	private String profile_picture_url;
    private String caption_text;
	
	public InstagramObject(String id, String media_url, int likes, boolean is_video, String username, String profile_picture_url, String caption_text) {
		super();
		this.id = id;
		this.media_url = media_url;
		this.likes = likes;
		this.is_video = is_video;
		this.username = username;
		this.profile_picture_url = profile_picture_url;
        this.caption_text = caption_text;
	}
	
	public InstagramObject(Cursor cursor){
		super();
		this.id = cursor.getString(0);
		this.media_url = cursor.getString(1);
		this.likes = cursor.getInt(2);
		this.is_video = cursor.getInt(3) > 0;
		this.username = cursor.getString(4);
		this.profile_picture_url = cursor.getString(5);
        this.caption_text = cursor.getString(6);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	

	public String getMedia_url() {
		return media_url;
	}

	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}

	public boolean isIs_video() {
		return is_video;
	}

	public void setIs_video(boolean is_video) {
		this.is_video = is_video;
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
