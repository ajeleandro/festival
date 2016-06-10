package com.artefacto1971.festival.classes;

import android.database.Cursor;

/**
*
* @author Alejandro
*/
public class EventVideo {
   
   private int ID;
   private String title;
   private String code;
   private String Thumbnail;
   private int fk_festival;
   private int fk_artist;
   private String plataform;
   
   public EventVideo (int ID, String title, String plataform) {
      this.ID = ID;
      this.title = title;
      this.plataform = plataform;
   }
    
   public EventVideo(String code, String title, String plataform){
      this.code = code;
      this.title = title;
      this.plataform = plataform;
   }

   public EventVideo (int ID, String title, String code, String Thumbnail,int fk_festival,int fk_artist, String plataform) {
      this.ID = ID;
      this.title = title;
      this.code = code;
      this.Thumbnail = Thumbnail;
      this.fk_festival = fk_festival;
      this.fk_artist = fk_artist;
      this.plataform = plataform;
   }
   
   public EventVideo(Cursor cursor){
	  this.ID = cursor.getInt(0);
	  this.title = cursor.getString(1);
	  this.plataform = cursor.getString(2);
	  this.code = cursor.getString(3);
	  this.fk_artist = cursor.getInt(4);
	  this.fk_festival = cursor.getInt(5);
   }
   
   public int getFk_artist() {
      return fk_artist;
   }

   public void setfk_artist(int fk_artist) {
      this.fk_artist = fk_artist;
   }
   
   public int GetFk_festival() {
      return fk_festival;
   }

   public void setFk_festival(int fk_festival) {
      this.fk_festival = fk_festival;
   }
   
   public String getThumbnail() {
      return Thumbnail;
   }

   public void setThumbnail(String Thumbnail) {
      this.Thumbnail = Thumbnail;
   }
   
   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }
   
   public int getID() {
      return ID;
   }

   public void setID(int iD) {
      this.ID = iD;
   }

   public String getTitle() {
       return title;
   }

   public void setTitle(String title) {
       this.title = title;
   }    
   
     public String getPlataform() {
       return plataform;
   }

   public void setPlataform(String plataform) {
       this.plataform = plataform;
   }  
}
