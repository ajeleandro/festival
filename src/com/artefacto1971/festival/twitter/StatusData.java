package com.artefacto1971.festival.twitter;

public class StatusData {
	
	/**tweet ID*/
	private long tweetID; 
	  
	/**user screen name of tweeter*/
	private String tweetUser;

	/** 
	 * Constructor receives ID and user name 
	 * @param ID 
	 * @param screenName 
	 */
	public StatusData(long tweetID, String tweetUser) { 
	        //instantiate variables 
	    this.tweetID = tweetID; 
	    this.tweetUser = tweetUser; 
	}
	public long getID() {return tweetID;}
	public String getUser() {return tweetUser;}
}
