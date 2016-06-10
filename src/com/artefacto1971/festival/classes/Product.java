package com.artefacto1971.festival.classes;

public class Product {

	private int ID;
	private String name;
	private String description;
	private String file;
	private String img;
	private String type;
	private float price;
	private String buy_link;
	private boolean downloadable;
	
	public Product() {
		super();
	}
	public Product(int iD, String name, String description, String file,String img, String type, float price, String buy_link, boolean downloadable) {
		super();
		ID = iD;
		this.name = name;
		this.description = description;
		this.file = file;
		this.img = img;
		this.type = type;
		this.price = price;
		this.buy_link = buy_link;
		this.downloadable = downloadable;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getBuy_link() {
		return buy_link;
	}
	public void setBuy_link(String buy_link) {
		this.buy_link = buy_link;
	}
	public boolean isDownloadable() {
		return downloadable;
	}
	public void setDownloadable(boolean downloadable) {
		this.downloadable = downloadable;
	}
	
	
	
}
