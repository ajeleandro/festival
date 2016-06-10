package com.artefacto1971.festival.logic;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.artefacto1971.festival.FestivalAplication;
import com.artefacto1971.festival.classes.Category;
import com.artefacto1971.festival.classes.Festival;
import com.artefacto1971.festival.classes.EventVideo;
import com.artefacto1971.festival.classes.InstagramImagesArray;
import com.artefacto1971.festival.classes.InstagramObjectsArray;
import com.artefacto1971.festival.classes.Lineup;
import com.artefacto1971.festival.classes.News;
import com.artefacto1971.festival.classes.Product;
import com.artefacto1971.festival.classes.Promo;
import com.artefacto1971.festival.classes.Summary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebServiceHandler {
	public static final String TAG = WebServiceHandler.class.getSimpleName();
	 //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://OTTpackage/";
    private static int TimeOut = 3500;
    //Webservice URL - WSDL File location    
    private static String URL = FestivalAplication.getServiceServer() +  "/FestivalWebService/OTTchannelsWebService?WSDL";
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://OTTpackage/OTTchannelsWebService/";
     
    public static ArrayList<Category> getCategories() {

    	String webMethName = "getVideoCategoriesList";
        ArrayList<Category> List = new ArrayList<Category>();
        
        try {
	        SoapObject request = new SoapObject(NAMESPACE, webMethName);

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if(response == null)
            	return null;
            
            String resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<Category>>(){}.getType();
            List = n.fromJson(resTxt, collectionType);
            return List;
            
        } catch (Exception e) {
        	//List.add(new Category(2, "2 WebServiceHandler Exception: " + e));
            return null;
        } 
    }
    
    public static Summary getSummaryList(int fk_festival, String tag){
    	String resTxt = "";
    	
    	try{
    		String webMethName = "getSummaryList";
    		SoapObject request = new SoapObject(NAMESPACE, webMethName);
    		
    		PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("tag");
 	        PropertyInfo.setValue(tag);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo); 
 	        
	        PropertyInfo PropertyInfo2 = new PropertyInfo();
	        PropertyInfo2.setName("fk_festival");
	        PropertyInfo2.setValue(fk_festival);
	        PropertyInfo2.setType(String.class);
	        request.addProperty(PropertyInfo2);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if(response == null){
            	Log.i(TAG, "response == null");
            	return null;
            }
            
            resTxt = response.toString();
            return new Gson().fromJson(response.toString(), new TypeToken<Summary>(){}.getType());
    	}
    	catch(Exception e){
    		Log.e(TAG, e + " response: " + resTxt);
    		return null;
    	}
    }
    
    public static InstagramImagesArray getInstagramImages(String tag, String next_max_id, int count){
    	
    	try{
    		String webMethName = "getInstagramImagesArray";
    		SoapObject request = new SoapObject(NAMESPACE, webMethName);
    		
    		PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("tag");
 	        PropertyInfo.setValue(tag);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo);
 	        
 	        PropertyInfo PropertyInfo2 = new PropertyInfo();
 	        PropertyInfo2.setName("next_max_id");
 	        PropertyInfo2.setValue(next_max_id);
 	        PropertyInfo2.setType(String.class);
 	        request.addProperty(PropertyInfo2);
 	        
 	        PropertyInfo PropertyInfo3 = new PropertyInfo();
 	        PropertyInfo3.setName("count");
 	        PropertyInfo3.setValue(count);
 	        PropertyInfo3.setType(String.class);
	        request.addProperty(PropertyInfo3);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
    		
            return new Gson().fromJson(response.toString(), new TypeToken<InstagramImagesArray>(){}.getType());
    	}
    	catch(Exception e){
    		return new InstagramImagesArray("WebServiceHandler Exception getInstagramImages: " + e,null);
    	}
    }
    
public static InstagramObjectsArray getInstagramObjects(String tag, String next_max_id, int count){
    	
    	try{
    		String webMethName = "getInstagramObjectsArray";
    		SoapObject request = new SoapObject(NAMESPACE, webMethName);
    		
    		PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("tag");
 	        PropertyInfo.setValue(tag);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo);
 	        
 	        PropertyInfo PropertyInfo2 = new PropertyInfo();
 	        PropertyInfo2.setName("next_max_id");
 	        PropertyInfo2.setValue(next_max_id);
 	        PropertyInfo2.setType(String.class);
 	        request.addProperty(PropertyInfo2);
 	        
 	        PropertyInfo PropertyInfo3 = new PropertyInfo();
 	        PropertyInfo3.setName("count");
 	        PropertyInfo3.setValue(count);
 	        PropertyInfo3.setType(String.class);
	        request.addProperty(PropertyInfo3);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
    		
            return new Gson().fromJson(response.toString(), new TypeToken<InstagramObjectsArray>(){}.getType());
    	}
    	catch(Exception e){
    		return new InstagramObjectsArray("WebServiceHandler Exception getInstagramObjects: " + e,null);
    	}
    }
    
    public static ArrayList<Festival> getFestivals(Context context) {

        ArrayList<Festival> List = new ArrayList<Festival>();
        String webMethName = "getFestivals";
        try {
	        SoapObject request = new SoapObject(NAMESPACE, webMethName);
	        
	        Database_DAO dao = new Database_DAO(context);
			dao.open();
			String lastId = dao.getLastID("festival");
			dao.close();
			
	        PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("lastId");
 	        PropertyInfo.setValue(lastId);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null)
            	return null;
            
            String resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<Festival>>(){}.getType();
            List = n.fromJson( resTxt, collectionType);
            return List;
            
        } catch (Exception e) {
        	//List.add(new Festival(1, "getFestivals WebServiceHandler Exception: " + e,0000,true));
            return null;
        } 
    }
    
    public static ArrayList<Product> getProducts(Context context) {

        ArrayList<Product> List = new ArrayList<Product>();
        String webMethName = "getProducts";
        String resTxt = "";
        try {
	        SoapObject request = new SoapObject(NAMESPACE, webMethName);
	        
	        Database_DAO dao = new Database_DAO(context);
			dao.open();
			String lastId = dao.getLastID("Product");
			dao.close();
			
	        PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("lastId");
 	        PropertyInfo.setValue(lastId);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
	        
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null)
            	return null;
            
            resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<Product>>(){}.getType();
            List = n.fromJson( resTxt, collectionType);
            
            for (int i = 0; i < List.size(); i++) {
                Log.i("webservicehandler getproduct","is downloadable " + String.valueOf((List.get(i).isDownloadable())));
			}
            
            return List;
            
        } catch (Exception e) {
        	Log.i("getProducts","response: " + resTxt + " Exeption: " + e);
        	//List.add(new Festival(1, "getFestivals WebServiceHandler Exception: " + e,0000,true));
            return null;
        } 
    }
    
	public static ArrayList<EventVideo> getLastestVideos(Context context, String category,int fk_festival) {
		
        ArrayList<EventVideo> videoList = new ArrayList<EventVideo>();
        String webMethName = "getLatestVideos"; 
        
        try {
	        SoapObject request = new SoapObject(NAMESPACE, webMethName);
	        
	        Database_DAO dao = new Database_DAO(context);
			dao.open();
			String lastId = dao.getLastID("festival");
			dao.close();
			
			PropertyInfo PropertyInfo = new PropertyInfo();
 	        PropertyInfo.setName("lastId");
 	        PropertyInfo.setValue(lastId);
 	        PropertyInfo.setType(String.class);
 	        request.addProperty(PropertyInfo);
	        
	        PropertyInfo PropertyInfo2 = new PropertyInfo();
	        PropertyInfo2.setName("category");
	        PropertyInfo2.setValue(category);
	        PropertyInfo2.setType(String.class);
	        request.addProperty(PropertyInfo2);
	        
	        PropertyInfo PropertyInfo3 = new PropertyInfo();
	        PropertyInfo3.setName("fk_festival");
	        PropertyInfo3.setValue(fk_festival);
	        PropertyInfo3.setType(String.class);
	        request.addProperty(PropertyInfo3);
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);

            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null)
            	return null;
            
            String resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<EventVideo>>(){}.getType();
            videoList = n.fromJson( resTxt, collectionType);
            return videoList;
            
        } catch (Exception e) {
        	/*videoList.add(new EventVideo(24577973, "6 WebServiceHandler Exception: " + e,"vimeo"));*/
            return null; 
        } 
    }    
    
	public static ArrayList<News> getLastestNews(Context context,int fk_festival) {

		String webMethName = "getLastestNews";
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        
        Database_DAO dao = new Database_DAO(context);
  		dao.open();
  		String lastId = dao.getLastID("news");
  		dao.close();
  		
	    PropertyInfo PropertyInfo = new PropertyInfo();
	    PropertyInfo.setName("lastId");
	    PropertyInfo.setValue(lastId);
	    PropertyInfo.setType(String.class);
	    request.addProperty(PropertyInfo);
        
        // Property which holds input parameters
        PropertyInfo PropertyInfo2 = new PropertyInfo();
        // Set Name
        PropertyInfo2.setName("fk_festival");
        // Set Value
        PropertyInfo2.setValue(fk_festival);
        // Set dataType
        PropertyInfo2.setType(String.class);
        // Add the property to request object
        request.addProperty(PropertyInfo2);
        
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
        ArrayList<News> newsList = new ArrayList<News>();
        
        try {
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null)
            	return null;
            
            resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<News>>(){}.getType();
            newsList = n.fromJson( resTxt, collectionType);
            return newsList;
            
        } catch (Exception e){
        	/*News exceptionNews = new News(0, "7 WebServiceHandler Exception", e.toString(), new Date(), 0);
        	newsList.add(exceptionNews);*/
            return null; 
        }
    }    
	
	public static ArrayList<Lineup> getLineup(int fk_artist,int fk_festival) {
        String resTxt = null;
        String webMethName = "getLineup";
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        
        PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("fk_artist");
        PropertyInfo.setValue(fk_artist);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);
        
        PropertyInfo PropertyInfo2 = new PropertyInfo();
        PropertyInfo2.setName("fk_festival");
        PropertyInfo2.setValue(fk_festival);
        PropertyInfo2.setType(String.class);
        request.addProperty(PropertyInfo2);
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
        ArrayList<Lineup> lineupList = new ArrayList<Lineup>();
        
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null)
            	return null;
            	
            resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<Lineup>>(){}.getType();
            lineupList = n.fromJson( resTxt, collectionType);
            return lineupList;  
            
        } catch (Exception e) {
        	//Lineup exceptionLineup = new Lineup(0,java.sql.Date.valueOf(new Date().toString()),"0","getLineup Android WS Handler Exception: " + fk_artist + " " + fk_festival,"",0);
        	//lineupList.add(exceptionLineup);
            return null; 
        } 
    }    
	
	public static ArrayList<Promo> getLastestPromo(Context context, int fk_festival){
		
		String webMethName = "getLastestPromos";
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        
        Database_DAO dao = new Database_DAO(context);
  		dao.open();
  		String lastId = dao.getLastID("promo");
  		dao.close();
  		
  		Log.i(TAG, "lastID Promo: " + lastId);
  		
  		PropertyInfo PropertyInfo = new PropertyInfo();
        PropertyInfo.setName("lastId");
        PropertyInfo.setValue(lastId);
        PropertyInfo.setType(String.class);
        request.addProperty(PropertyInfo);
        
        PropertyInfo PropertyInfo2 = new PropertyInfo();
        PropertyInfo2.setName("fk_festival");
        PropertyInfo2.setValue(fk_festival);
        PropertyInfo2.setType(String.class);
        request.addProperty(PropertyInfo2);
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TimeOut);
        ArrayList<Promo> promoList = new ArrayList<>();
        
        try {
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            
            if (response == null){
            	Log.i(TAG, "response Promo: null ");
            	return null;
            }
            resTxt = response.toString();
            
            Gson n = new Gson();
            java.lang.reflect.Type collectionType = new TypeToken<List<Promo>>(){}.getType();
            promoList = n.fromJson( resTxt, collectionType);
            return promoList;  
            
        } catch (Exception e) {
        	/*Promo promo = new Promo(0,"getLastestPromo WebServiceHandler Exception" + e,e + "",new Date(),"",true,0);
        	promoList.add(promo);*/
        	Log.i(TAG, "exception " + e);
            return null; 
        } 
    }    
}