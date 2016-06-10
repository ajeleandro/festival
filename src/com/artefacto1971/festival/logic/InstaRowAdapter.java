package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.InstagramObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class InstaRowAdapter extends BaseAdapter {

	private static final String TAG = InstaRowAdapter.class.getSimpleName();
	private ArrayList<InstagramObject> instaArray;
	int lastPosition = -1;
	private LayoutInflater inflater;
	Context context;

	public InstaRowAdapter(Context context, ArrayList<InstagramObject> instaArray) {
		this.context = context;
		this.instaArray = instaArray;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return instaArray.size();
	}

	@Override
	public Object getItem(int position) {
		return instaArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		InstagramImageRow holder;

		if(instaArray.get(position).isIs_video() == true)
			convertView = inflater.inflate(R.layout.layout_insta_row_video, null);
		else
			convertView = inflater.inflate(R.layout.layout_insta_row_image, null);
			
		holder = new InstagramImageRow();
		holder.caption = (TextView) convertView.findViewById(R.id.insta_image_title);
		holder.username = (TextView) convertView.findViewById(R.id.insta_username);
		holder.likes = (TextView) convertView.findViewById(R.id.insta_like_count);
		holder.instaImage = (ImageView) convertView.findViewById(R.id.insta_image);
		holder.profilePicture = (ImageView) convertView.findViewById(R.id.insta_profile_picture);
		
		Database_DAO dao = new Database_DAO(context);
		
		if(instaArray.get(position).isIs_video() == true){
			VideoView videoView;
			videoView = (VideoView) convertView.findViewById(R.id.insta_video);
			MediaController mediaController = new MediaController(inflater.getContext());
			mediaController.setAnchorView(videoView);
			mediaController.setMediaPlayer(videoView);
			Uri video = Uri.parse(instaArray.get(position).getMedia_url());
			videoView.setMediaController(mediaController);
			videoView.setVideoURI(video);	
		}
		else{
			Bitmap bit = dao.CheckImage(instaArray.get(position).getId(), "instagram", "picture");
			if(bit != null)
					holder.instaImage.setImageBitmap(bit);
			else
				new ImageDownloaderTask(holder.instaImage, convertView.getContext(), instaArray.get(position).getId(), "instagram", "picture").execute(instaArray.get(position).getMedia_url());
		}
		
		Bitmap bit = dao.CheckImage(instaArray.get(position).getId(), "instagram", "profile_picture");
		if(bit != null)
				holder.profilePicture.setImageBitmap(bit);
		else
			new ImageDownloaderTask(holder.profilePicture, convertView.getContext(), instaArray.get(position).getId(), "instagram", "profile_picture").execute(instaArray.get(position).getProfile_picture_url());
				
		if (instaArray.get(position).getCaption_text().length() > 100)
			holder.caption.setText(instaArray.get(position).getCaption_text().substring(0,100) + "...");
		else
			holder.caption.setText(instaArray.get(position).getCaption_text());
		
		holder.username.setText(instaArray.get(position).getUsername());
		holder.likes.setText(Integer.toString(instaArray.get(position).getLikes()));
		
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
}