package com.artefacto1971.festival.classes;

import java.util.ArrayList;

public class InstagramImagesArray {

	private String next_max_id;
	private ArrayList<InstagramImage> imagesArray;
	
	public InstagramImagesArray() {
		super();
	}

	public InstagramImagesArray(String next_max_id, ArrayList<InstagramImage> imagesArray) {
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

	public ArrayList<InstagramImage> getImagesArray() {
		return imagesArray;
	}

	public void setImagesArray(ArrayList<InstagramImage> imagesArray) {
		this.imagesArray = imagesArray;
	}
}
