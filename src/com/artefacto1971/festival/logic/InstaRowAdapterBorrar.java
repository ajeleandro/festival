package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.InstagramImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstaRowAdapterBorrar extends BaseAdapter {


	private static final String TAG = InstaRowAdapterBorrar.class.getSimpleName();
	private ArrayList<InstagramImage> instaArray;
	int lastPosition = -1;
	private LayoutInflater inflater;

	public InstaRowAdapterBorrar(Context context, ArrayList<InstagramImage> instaArray) {
		this.instaArray = instaArray;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}/*

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
		convertView = inflater.inflate(R.layout.layout_insta_row_image, null);
		holder = new InstagramImageRow();
		holder.caption = (TextView) convertView.findViewById(R.id.insta_image_title);
		holder.username = (TextView) convertView.findViewById(R.id.insta_username);
		holder.likes = (TextView) convertView.findViewById(R.id.insta_like_count);
		holder.instaImage = (ImageView) convertView.findViewById(R.id.insta_image);
		holder.profilePicture = (ImageView) convertView.findViewById(R.id.insta_profile_picture);
		
		if (instaArray.get(position).getImage_url() != null) 
			new ImageDownloaderTask(holder.instaImage, convertView.getContext(), instaArray.get(position).getId(), "instagram", "picture").execute(instaArray.get(position).getImage_url());
		
		if (instaArray.get(position).getProfile_picture_url() != null) 
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
	}*/

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}