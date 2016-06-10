package com.artefacto1971.festival.classes;

public class Summary {

	private EventVideo eventVideo;
	private News news;
	private InstagramImage instagramImage;
	private int festival_fk;
	private Promo promo;
	
	public Summary(EventVideo eventVideo, News news, InstagramImage instagramImage, Promo promo, int festival_fk) {
		this.eventVideo = eventVideo;
		this.news = news;
		this.instagramImage = instagramImage;
		this.promo = promo;
		this.festival_fk = festival_fk;
	}
	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
	public EventVideo getEventVideo() {
		return eventVideo;
	}
	public void setEventVideo(EventVideo eventVideo) {
		this.eventVideo = eventVideo;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public InstagramImage getInstagramImage() {
		return instagramImage;
	}
	public void setInstagramImage(InstagramImage instagramImage) {
		this.instagramImage = instagramImage;
	}	
	public int getFestival_fk() {
		return festival_fk;
	}
	public void setFestival_fk(int festival_fk) {
		this.festival_fk = festival_fk;
	}
}