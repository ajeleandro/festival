package com.artefacto1971.festival.classes;

import java.util.ArrayList;

public class InstagramObjectsArray {

	private String next_max_id;
	private ArrayList<InstagramObject> imagesArray;
	
	public InstagramObjectsArray() {
		super();
	}

	public InstagramObjectsArray(String next_max_id, ArrayList<InstagramObject> imagesArray) {
		super();
		this.next_max_id = next_max_id;
		this.imagesArray = imagesArray;
	}

	public String getNext_max_id() {
		return next_max_id;
	}

	public void setNext_max_id(String next_max_id) {
		this.next_max_id = next_max_id;
	}

	public ArrayList<InstagramObject> getObjectsArray() {
		return imagesArray;
	}

	public void setImagesArray(ArrayList<InstagramObject> imagesArray) {
		this.imagesArray = imagesArray;
	}
}