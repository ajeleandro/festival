package com.artefacto1971.festival.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artefacto1971.festival.MarketplaceActivity;
import com.artefacto1971.festival.R;
import com.artefacto1971.festival.Tabbed_News_Promos;
import com.artefacto1971.festival.Tabbed_Pics_Fashion;
import com.artefacto1971.festival.classes.Summary;

public class SummaryGridAdapter extends BaseAdapter {

	public static final String TAG = SummaryGridAdapter.class.getSimpleName();
    Context context;
    FragmentActivity act;
    private static LayoutInflater inflater = null;
    private static Summary summary;		
    int lastPosition = -1;

    public SummaryGridAdapter(Context context, Summary _summary, FragmentActivity act) {
    	this.act = act;
        this.context = context;
        summary = _summary;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
    public int getCount() {
    	return 3;
    }

    @Override
    public Object getItem(int position) {
    	return summary;
    }

    @Override
    public long getItemId(int position) {
    	return position;
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		position++;
		convertView = inflater.inflate(R.layout.layout_summary_grid_item, null);
		TextView textViewTitle;
		ImageView squareImage;
		RelativeLayout layout;
		
		switch(position){
			case 1://video
				layout = (RelativeLayout) convertView.findViewById(R.id.relativeLayoutCompleto);
				layout.setVisibility(View.VISIBLE);
		        textViewTitle = (TextView) convertView.findViewById(R.id.TitleCompleto);
		        squareImage = (ImageView) convertView.findViewById(R.id.ThumbnailCompleto);
		        squareImage.setTag("eventVideo");
		        squareImage.setOnClickListener(new summaryListener());
				textViewTitle.setText(summary.getEventVideo().getTitle());
				new ImageDownloaderTask(squareImage,convertView.getContext(),summary.getEventVideo().getID() + "","eventVideo","picture").execute(summary.getEventVideo().getThumbnail());
				break;
				
			case 2://news
				layout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout1);
				layout.setVisibility(View.VISIBLE);
				textViewTitle = (TextView) convertView.findViewById(R.id.Title);
		        squareImage = (ImageView) convertView.findViewById(R.id.Thumbnail);
		        squareImage.setTag("News");
		        squareImage.setOnClickListener(new summaryListener());
				textViewTitle.setText("News");
				squareImage.setBackgroundResource(new ImageHandler().getFestivalDrawable(summary.getNews().getFk_festival()));
				//instagram
				layout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout2);
				layout.setVisibility(View.VISIBLE);
				textViewTitle = (TextView) convertView.findViewById(R.id.Title2);
		        squareImage = (ImageView) convertView.findViewById(R.id.Thumbnail2);
		        squareImage.setTag("Your Pics");
		        squareImage.setOnClickListener(new summaryListener());
				textViewTitle.setText("Your Pics");
				new ImageDownloaderTask(squareImage,convertView.getContext(),summary.getInstagramImage().getId() + "","instagram","picture").execute(summary.getInstagramImage().getImage_url());
				break;
				
			case 3://promo
				layout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout1);
				layout.setVisibility(View.VISIBLE);
				textViewTitle = (TextView) convertView.findViewById(R.id.Title);
		        squareImage = (ImageView) convertView.findViewById(R.id.Thumbnail);
		        squareImage.setTag("Promo");
		        squareImage.setOnClickListener(new summaryListener());
				textViewTitle.setText("Promo");
				new ImageDownloaderTask(squareImage,convertView.getContext(),summary.getPromo().getId() + "","promo","picture").execute(summary.getPromo().getPicture());
				//shop
				layout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout2);
				layout.setVisibility(View.VISIBLE);
				textViewTitle = (TextView) convertView.findViewById(R.id.Title2);
		        squareImage = (ImageView) convertView.findViewById(R.id.Thumbnail2);
		        squareImage.setTag("Shop");
		        squareImage.setOnClickListener(new summaryListener());
				textViewTitle.setText("Shop");
				squareImage.setBackgroundResource(new ImageHandler().getCategoryDrawable(9));
				break;
		}
        return convertView;
    }
	
	private class summaryListener implements  OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getTag().toString()){
			case "eventVideo":
				String currentVideoID;
				String plataform = summary.getEventVideo().getPlataform();
				currentVideoID = summary.getEventVideo().getCode();
				new VideoChange(act, currentVideoID, plataform,summary.getEventVideo().getTitle());				
				break;
			case "Shop":
				Intent intent = new Intent(context,MarketplaceActivity.class);
	    		((Activity) context).startActivityForResult(intent, 0);
	        	break;
			case "News":
				act.getSupportFragmentManager().beginTransaction().addToBackStack(null)
            	.replace(R.id.content_frame, new Tabbed_News_Promos(), Tabbed_News_Promos.TAG).commit();
				break;
			case "Promo":
				act.getSupportFragmentManager().beginTransaction().addToBackStack(null)
            	.replace(R.id.content_frame, new Tabbed_News_Promos(), Tabbed_News_Promos.TAG).commit();
				break;
			case "Your Pics":
				act.getSupportFragmentManager().beginTransaction().addToBackStack(null)
	        	.replace(R.id.content_frame, new Tabbed_Pics_Fashion(),Tabbed_Pics_Fashion.TAG).commit();
				break;
			}
		}
	}
}