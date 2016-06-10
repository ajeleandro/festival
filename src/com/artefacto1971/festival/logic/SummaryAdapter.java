package com.artefacto1971.festival.logic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.Summary;

public class SummaryAdapter extends BaseAdapter {

	public static final String TAG = SummaryAdapter.class.getSimpleName();
    Context context;
    private static LayoutInflater inflater = null;
    Summary summary;		
    int lastPosition = -1;

    public SummaryAdapter(Context context, Summary summary) {
        this.context = context;
        this.summary = summary;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

		switch(position){
			case 0: //video
				
		        convertView = inflater.inflate(R.layout.layout_video_preview_row, null);
		        
		        TextView header = (TextView) convertView.findViewById(R.id.header);
		        header.setText("Shows");
		        header.setVisibility(View.VISIBLE);
		        header.setTextSize(16);
		        
		        ImageView separator = (ImageView) convertView.findViewById(R.id.separator);
		        separator.setVisibility(View.GONE);
	        	
		        TextView textViewTitle = (TextView) convertView.findViewById(R.id.video_preview_title);
		        textViewTitle.setText(summary.getEventVideo().getTitle());
		        
		        ImageView Thumbnail = (ImageView) convertView.findViewById(R.id.video_preview_thumbnail); 
		        new ImageDownloaderTask(Thumbnail,convertView.getContext(),summary.getEventVideo().getID() + "","eventVideo","picture").execute(summary.getEventVideo().getThumbnail());
		        
		        ImageView festivalIcon = (ImageView) convertView.findViewById(R.id.video_preview_festival_icon);
		        festivalIcon.setImageResource(new ImageHandler().getFestivalDrawable(summary.getEventVideo().GetFk_festival()));
				
				break;
			case 1: //news
				
				convertView = inflater.inflate(R.layout.layout_news_row, null);
				
				TextView header2 = (TextView) convertView.findViewById(R.id.header);
		        header2.setText("News");
		        header2.setVisibility(View.VISIBLE);
		        header2.setTextSize(16);
		        
		        ImageView separator2 = (ImageView) convertView.findViewById(R.id.separator);
		        separator2.setVisibility(View.GONE);
				
				TextView textViewTitle2 = (TextView) convertView.findViewById(R.id.Title);
				textViewTitle2.setText(summary.getNews().getTitle());
		        
		        TextView textViewBody = (TextView) convertView.findViewById(R.id.Body);        
		        textViewBody.setText(summary.getNews().getBody());
		        
		        TextView textViewDate = (TextView) convertView.findViewById(R.id.Date);
		        textViewDate.setText(summary.getNews().getDate().toString().toString().split(" 00")[0]);
		        
		        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewFestival);
		        imageView.setBackgroundResource(new ImageHandler().getFestivalDrawable(summary.getNews().getFk_festival()));

				break;
			case 2: //instagram
				
				InstagramImageRow holder;
				convertView = inflater.inflate(R.layout.layout_insta_row_image, null);
				
				TextView header3 = (TextView) convertView.findViewById(R.id.header);
		        header3.setText("Your Pics");
		        header3.setVisibility(View.VISIBLE);
		        header3.setTextSize(16);
		        
		        ImageView separator3 = (ImageView) convertView.findViewById(R.id.separator);
		        separator3.setVisibility(View.GONE);
				
				holder = new InstagramImageRow();
				holder.caption        = (TextView)  convertView.findViewById(R.id.insta_image_title);
				holder.username       = (TextView)  convertView.findViewById(R.id.insta_username);
				holder.likes          = (TextView)  convertView.findViewById(R.id.insta_like_count);
				holder.instaImage     = (ImageView) convertView.findViewById(R.id.insta_image);
				holder.profilePicture = (ImageView) convertView.findViewById(R.id.insta_profile_picture);
				
				new ImageDownloaderTask(holder.instaImage,convertView.getContext(),summary.getInstagramImage().getId(),"instagram","picture").execute(summary.getInstagramImage().getImage_url());				
				new ImageDownloaderTask(holder.profilePicture,convertView.getContext(),summary.getInstagramImage().getId(),"instagram","profile_picture").execute(summary.getInstagramImage().getProfile_picture_url());
			
				if (summary.getInstagramImage().getCaption_text().length() > 100)
					holder.caption.setText(summary.getInstagramImage().getCaption_text().substring(0,100) + "...");
				else
					holder.caption.setText(summary.getInstagramImage().getCaption_text());
				
				holder.username.setText(summary.getInstagramImage().getUsername());
				holder.likes.setText(Integer.toString(summary.getInstagramImage().getLikes()));
				break;
		}
	
		// ANIMATION
        if((position != 0) && (position > lastPosition)){
			Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.animation_up_from_bottom);
		    animation.setDuration(600);
		    convertView.startAnimation(animation);
        }
		    lastPosition = position;

        return convertView;
    }
	
	static class InstagramImageRow {
		TextView caption;
		TextView username;
		TextView likes;
		ImageView instaImage;
		ImageView profilePicture;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
