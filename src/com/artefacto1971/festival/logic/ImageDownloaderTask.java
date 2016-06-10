package com.artefacto1971.festival.logic;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.artefacto1971.festival.FestivalAplication;
import com.artefacto1971.festival.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

	public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		
		private static final String TAG = ImageDownloaderTask.class.getSimpleName();
		private final WeakReference<ImageView> imageViewReference;
		private final Context context;
		private final String ID;
		private final String Table;
		private final String Column;
		private boolean isFromServer = false;
		private boolean inDB = false;
		
		public ImageDownloaderTask() {
			this.imageViewReference = null;
			this.context = null;
			this.ID = null;
			this.Table = null;
			this.Column = null;
		}
		
		public ImageDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
			this.context = null;
			this.ID = null;
			this.Table = null;
			this.Column = null;
		}
		
		public ImageDownloaderTask(ImageView imageView, Context context, String ID, String Table, String Column){
			imageViewReference = new WeakReference<ImageView>(imageView);
			this.context = context;
			this.ID = ID;
			this.Table = Table;
			this.Column = Column;
		}
		
		public ImageDownloaderTask(ImageView imageView, Context context, String ID, String Table, String Column, boolean isFromServer){
			imageViewReference = new WeakReference<ImageView>(imageView);
			this.context = context;
			this.ID = ID;
			this.Table = Table;
			this.Column = Column;
			this.isFromServer = isFromServer;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			if(Table != "Instagram")
				if(ID != null){
					Database_DAO dao = new Database_DAO(context);
					dao.open();
					Cursor cursor = dao.getCursor(ID,Table,0);
					if (cursor != null){
						cursor.moveToFirst();
						if(cursor.getBlob(cursor.getColumnIndex(Column)) != null){
							inDB = true;
							byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(Column));
							cursor.close();
							dao.close();
							Log.i(TAG, "ID: " + ID + " encontrada");
							return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
						}
					}else{
						Log.i(TAG, "ID: " + ID + " no encontrada");
						dao.insert_ID(ID, Table);
					}
					dao.close();
				}

			if(isFromServer)
				return downloadBitmap(FestivalAplication.getWebServer() + FestivalAplication.getProductImagesURL() + params[0],context);
			
			return downloadBitmap(params[0],context);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap){

			if (isCancelled())
				bitmap = null;

			if(bitmap != null){
				//agregar la imagen a la base de datos
				if ((ID != null)&&(inDB == false)){
					Database_DAO dao = new Database_DAO(context);
					dao.open();
					dao.updatePicture(ID, bitmap, Table, Column);
					dao.close();
				}

				if (imageViewReference != null) {
					ImageView imageView = imageViewReference.get();
					if (imageView != null)
						if(bitmap != null){
							imageView.setBackgroundResource(android.R.color.transparent);
							imageView.setImageBitmap(bitmap);
						}
						else //si no puede descarga pone lo siguiente:
							imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.icono_glamcrowd));
				}
			}
			else
				Log.i(TAG, "bitmap == null");
		}

		public Bitmap downloadBitmap(String url, Context context) {
			final HttpGet getRequest = new HttpGet(url);
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
			HttpConnectionParams.setSoTimeout(httpParameters, 6000);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			try {
				HttpResponse response = httpClient.execute(getRequest);
				final int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
					return null;
				}
				Log.i(TAG, "ID: " + ID + " descargada");
				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						inputStream = entity.getContent();
						
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 1; //4=1/4 de la foto; 1 no hace nada
						
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
						return bitmap;
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			} catch (Exception e) {
				getRequest.abort();
				/*Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher); remplaza la imagen no encontrada por esta default pero luego no la remplaza más nunca
				Bitmap bitmap = ((BitmapDrawable)d).getBitmap();*/
				Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
			} finally {
				/*if (client != null) {
					client.close();
				}*/
			}
			return null;
		}
	}