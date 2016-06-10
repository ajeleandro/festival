package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.News;

public class NewsAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    ArrayList<News> newsList;		
    public static final String TAG = NewsAdapter.class.getSimpleName();
    int lastPosition = -1;
    
    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return newsList.size();
    }

    @Override
    public Object getItem(int position) {
    	return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
    	return newsList.get(position).getID();
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
    	//return false;
	}

	@Override
    public View getView(int position, View view, ViewGroup parent) {

		view = inflater.inflate(R.layout.layout_news_row, null);        
        
        TextView textViewTitle = (TextView) view.findViewById(R.id.Title);
        textViewTitle.setText(newsList.get(position).getTitle());
        
        TextView textViewBody = (TextView) view.findViewById(R.id.Body);        
        textViewBody.setText(newsList.get(position).getBody());
        
        TextView textViewDate = (TextView) view.findViewById(R.id.Date);
        textViewDate.setText(newsList.get(position).getDate().toString().split(" 00")[0]);
        
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewFestival);
        imageView.setBackgroundResource(new ImageHandler().getFestivalDrawable(newsList.get(position).getFk_festival()));
        
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
