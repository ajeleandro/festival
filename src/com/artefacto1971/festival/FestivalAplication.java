package com.artefacto1971.festival;

import android.app.Application;

public class FestivalAplication extends Application {
	
	private int eventID;
	private String festival_name;
	//private static String webserver = "192.168.1.101:51309"; //local
	private static String serviceserver = "http://54.94.144.25:8080"; //amazon
	private static String webserver = "http://artefactoestudiocreativo.com/festivalimage"; //artefacto
	
	private static String productImagesURL = "/productsimages/"; //artefacto
	private static String DownloadsURL = "/downloads/"; //artefacto

	public static String getServiceServer() {
			return serviceserver;
	}
	
	public static String getWebServer() {
		return webserver;
	}
	
	public static String getProductImagesURL() {
		return productImagesURL;
	}

	public static void setProductImagesURL(String productImagesURL) {
		FestivalAplication.productImagesURL = productImagesURL;
	}

	public static String getDownloadsURL() {
		return DownloadsURL;
	}

	public static void setDownloadsURL(String downloadsURL) {
		DownloadsURL = downloadsURL;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getFestival_name() {
		return festival_name;
	}

	public void setFestival_name(String festival_name) {
		this.festival_name = festival_name;
	}
}
