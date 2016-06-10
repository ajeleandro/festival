package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.artefacto1971.festival.FestivalAplication;
import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.Product;

public class MarketplaceAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> products;
    private static LayoutInflater inflater = null;
    public static final String TAG = MarketplaceAdapter.class.getSimpleName();
    int lastPosition = -1;
    DownloadManager dm;
    long enqueue;
    
    public MarketplaceAdapter(Context context, ArrayList<Product> products, DownloadManager dm, long enqueue) {
        this.context = context;
        this.products = products;
        this.dm = dm;
        this.enqueue = enqueue;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return products.size();
    }

    @Override
    public Object getItem(int position) {
    	return products.get(position);
    }

    @Override
    public long getItemId(int position) {
    	return products.get(position).getID();
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
    public View getView(final int position, View view, ViewGroup parent) {
        
		view = inflater.inflate(R.layout.layout_marketplace_row, null);        

        TextView productTitle = (TextView) view.findViewById(R.id.productTitle);
        productTitle.setText(products.get(position).getName());

        TextView productDescription = (TextView) view.findViewById(R.id.productDescription);        
        productDescription.setText(products.get(position).getDescription() + "\nType: " + products.get(position).getType());
        
        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        
        if(products.get(position).getPrice() == 0)
        	productPrice.setText("Free!");
        else
        	productPrice.setText("$" + Float.toString(products.get(position).getPrice()));       

        Button button = (Button) view.findViewById(R.id.productButton);
        if(!products.get(position).isDownloadable()){
        	button.setText("Buy");   
        	button.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(products.get(position).getBuy_link()));
    				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				context.startActivity(browserIntent);
    			}
    		});
        }
        else
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String product = products.get(position).getFile().replace(" ", "%20");
		        Request request = new Request(Uri.parse(FestivalAplication.getWebServer() + FestivalAplication.getDownloadsURL() + product));
		        request.setDestinationInExternalPublicDir("/Festival", product);
		        enqueue = dm.enqueue(request);
			}
		});
        
        ImageView imageView = (ImageView) view.findViewById(R.id.productImage);
        new ImageDownloaderTask(imageView,view.getContext(),products.get(position).getID() + "","product","picture",true).execute(products.get(position).getImg());

        // ANIMATION
        if((position != 0) && (position > lastPosition)){
			Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.animation_up_from_bottom);
		    animation.setDuration(600);
			view.startAnimation(animation);
        }
	    lastPosition = position;
	    
        return view;
    }
}
