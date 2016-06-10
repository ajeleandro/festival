package com.artefacto1971.festival.logic;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;

import com.artefacto1971.festival.classes.EventVideo;
import com.artefacto1971.festival.classes.Festival;
import com.artefacto1971.festival.classes.InstagramImage;
import com.artefacto1971.festival.classes.InstagramObject;
import com.artefacto1971.festival.classes.News;
import com.artefacto1971.festival.classes.Product;
import com.artefacto1971.festival.classes.Promo;
import com.artefacto1971.festival.classes.Summary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Database_DAO{

	 private static final String TAG = Database_DAO.class.getSimpleName();
	 private SQLiteDatabase database;
	 private Database_SQLiteHelper dbHelper;
	 Context context;
	 
	 public Bitmap CheckImage(String ID, String Table, String Column){
		 if(ID != null){
				open();
				Cursor cursor = getCursor(ID,Table,0);
				if (cursor != null){
					cursor.moveToFirst();
					if(cursor.getBlob(cursor.getColumnIndex(Column)) != null){
						byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(Column));
						cursor.close();
						close();
						Log.i(TAG, "ID: " + ID + " encontrada");
						return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
					}
				}else{
					Log.i(TAG, "ID: " + ID + " no encontrada");
					insert_ID(ID, Table);
				}
				close();
		 }
		 return null;
	 }
	 
	 public Database_DAO(Context context) {
		 this.context = context;
	    dbHelper = new Database_SQLiteHelper(context);
	 }
	 
	 public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
		dbHelper.onOpen(database);
	 }
	 
	 public void createDB(){
		 dbHelper.onCreate(database);
	 }
	 
	 public void close() {
	    dbHelper.close();
	 }
	 
	 public boolean insertFestival(ArrayList<Festival> list){
		 try{
			 for (Festival festival : list){
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", festival.getID());
				 contentValues.put("name", festival.getName());
				 contentValues.put("year", festival.getYear());
				 contentValues.put("active", festival.isActive());
				 database.insertOrThrow("festival", null, contentValues);
			 }
			 return true;
		 }
		 catch (Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertNews(ArrayList<News> list){
		 try{
			 for (News news : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", news.getID());
				 contentValues.put("title", news.getTitle());
				 contentValues.put("body", news.getBody());
				 contentValues.put("date", news.getDate().toString());
				 contentValues.put("fk_festival", news.getFk_festival());
				 database.insertOrThrow("news", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertProduct(ArrayList<Product> list){
		 try{
			 for (Product product : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", product.getID());
				 contentValues.put("name", product.getName());
				 contentValues.put("description", product.getDescription());
				 contentValues.put("file", product.getFile());
				 contentValues.put("img", product.getImg());
				 contentValues.put("type", product.getType());
				 contentValues.put("price", product.getPrice());
				 contentValues.put("buy_link", product.getBuy_link());
				 contentValues.put("downloadable", product.isDownloadable());
				 database.insertOrThrow("product", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertInstagram(ArrayList<InstagramImage> list){
		 try{
			 for (InstagramImage instagram : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", instagram.getId());
				 contentValues.put("media_url", instagram.getImage_url());
				 contentValues.put("likes", instagram.getLikes());
				 contentValues.put("username", instagram.getUsername());
				 contentValues.put("profile_picture_url", instagram.getProfile_picture_url());
				 contentValues.put("caption_text", instagram.getCaption_text());
				 database.insertOrThrow("instagram", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertInstagramObject(ArrayList<InstagramObject> list){
		 try{
			 for (InstagramObject instagram : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", instagram.getId());
				 contentValues.put("media_url", instagram.getMedia_url());
				 contentValues.put("likes", instagram.getLikes());
				 contentValues.put("is_video", instagram.isIs_video());
				 contentValues.put("username", instagram.getUsername());
				 contentValues.put("profile_picture_url", instagram.getProfile_picture_url());
				 contentValues.put("caption_text", instagram.getCaption_text());
				 database.insertOrThrow("instagram", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertPromo(ArrayList<Promo> list){
		 try{
			 for (Promo promo : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", promo.getId());
				 contentValues.put("title", promo.getTitle());
				 contentValues.put("body", promo.getBody());
				 contentValues.put("date", promo.getDate().toString());
				 contentValues.put("picture_url", promo.getPicture());
				 contentValues.put("active", promo.isActive());
				 contentValues.put("fk_festival", promo.getFk_festival());
				 database.insertOrThrow("promo", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	 public boolean insertEventVideo(ArrayList<EventVideo> list){
		 try{
			 for (EventVideo eventVideo : list) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put("ID", eventVideo.getID());
				 contentValues.put("title", eventVideo.getTitle());
				 contentValues.put("code", eventVideo.getCode());
				 contentValues.put("plataform", eventVideo.getPlataform());
				 contentValues.put("fk_artist", eventVideo.getFk_artist());
				 contentValues.put("fk_festival", eventVideo.GetFk_festival());
				 database.insertOrThrow("eventVideo", null, contentValues);
			}
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
	 }
	 
	public boolean insertSummary(Summary summary) {

		try{
			ArrayList<News> news = new ArrayList<News>();
			news.add(summary.getNews());
			insertNews(news);
			
			ArrayList<InstagramImage> instagram = new ArrayList<>();
			instagram.add(summary.getInstagramImage());
			insertInstagram(instagram);
			
			ArrayList<EventVideo> eventVideos = new ArrayList<>();
			eventVideos.add(summary.getEventVideo());
			insertEventVideo(eventVideos);
			
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public Summary getSummary(int festival_fk) throws ParseException {
		
		Cursor cursor = SelectAllLastID("eventVideo");
		if (cursor != null){	
			cursor.moveToFirst();
			EventVideo eventVideo = new EventVideo(cursor);
			cursor = SelectAllLastID("news");
			if (cursor != null){
				cursor.moveToFirst();
				News news = new News(cursor);
				cursor = SelectAllLastID("instagram"); 
				if (cursor != null){
					cursor.moveToFirst();
					InstagramImage instagram = new InstagramImage(cursor);
					cursor = SelectAllLastID("promo"); 
					if (cursor != null){
						cursor.moveToFirst();
						Promo promo = new Promo(cursor);
						return new Summary(eventVideo,news,instagram,promo,festival_fk);
					}
				}
			}
		}
		return null;
	}
	
	 public boolean updateSummary(){
		 
		 Cursor cursor =  database.rawQuery( "SELECT ID FROM news order by id desc limit 1", null );
		 if(cursor.getCount() > 1){
			 cursor.moveToFirst();
			 cursor =  database.rawQuery( "SELECT ID FROM instagram order by id desc limit 1", null );
			 if(cursor.getCount() > 1){
				 cursor.moveToFirst();
				 cursor =  database.rawQuery( "SELECT ID FROM eventVideo order by id desc limit 1", null );
				 if(cursor.getCount() > 1)
					 return true;
			 }
		 }
		 return false;
	 }
	
	 public Cursor SelectAllLastID(String Table){
		 
		 Cursor cursor =  database.rawQuery("SELECT * FROM " + Table + " order by id desc limit 1", null);
		 if(cursor.getCount() > 0)
			 return cursor;
		 return null;
	 }
	 
	 public String getLastID(String Table){
		 
		 Cursor cursor =  database.rawQuery( "SELECT ID FROM " + Table + " order by id desc limit 1", null );
		 if(cursor.getCount() > 0){
			 cursor.moveToFirst();
			 return cursor.getString(0);
		 }
		 else return "0";
	 }
	 
	 public Cursor getCursor(String ID, String Table, int event_id){
		 
		 Cursor cursor;
		 String event_id_query = "";
		 if(event_id != 0)
			 event_id_query = " AND fk_festival = " + event_id;
		 if(ID == null)
			 cursor =  database.rawQuery( "SELECT * FROM " + Table + " WHERE 1 = 1 " + event_id_query +" order by id desc", null);
		 else
			 try{
				 int intID = Integer.parseInt(ID);
				 cursor =  database.rawQuery("SELECT * FROM " + Table + " WHERE ID= " + intID, null);
			 }catch(Exception e){
				 cursor =  database.rawQuery("SELECT * FROM " + Table + " WHERE ID= '" + ID +"'", null);
			 }
	     
	     if (cursor.getCount() > 0)
	    	 return cursor;
	     
	     return null;
	   }
	 
	 public boolean insert_ID(String ID, String Table){

		 ContentValues contentValues = new ContentValues();
	     contentValues.put("ID", ID);
		 database.insertOrThrow(Table, null, contentValues);
	     return true;
	 }
	 
	 public boolean updatePicture(String ID, Bitmap picture, String Table, String Column){
		 
	     ByteArrayOutputStream stream = new ByteArrayOutputStream();
	     picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
	     byte[] byteArray = stream.toByteArray();
		 
	     ContentValues contentValues = new ContentValues();
		 contentValues.put(Column,byteArray);
		 String[] a = {ID};
		 database.update(Table,contentValues,"ID = ?", a);
		 return true;
	 }
}