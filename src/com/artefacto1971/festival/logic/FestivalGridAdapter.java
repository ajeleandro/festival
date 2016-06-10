package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
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
import com.artefacto1971.festival.classes.Festival;

public class FestivalGridAdapter extends BaseAdapter {

	public static final String TAG = FestivalGridAdapter.class.getSimpleName();
    Context context;
    private static LayoutInflater inflater = null;
    ArrayList<Festival> festivalList;		
    int lastPosition = -1;

    public FestivalGridAdapter(Context context, ArrayList<Festival> festivalList) {
        this.context = context;
        this.festivalList = festivalList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return festivalList.size();
    }

    @Override
    public Object getItem(int position) {
    	return festivalList.get(position);
    }

    @Override
    public long getItemId(int position) {
    	return festivalList.get(position).getID();
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
        convertView = inflater.inflate(R.layout.layout_festival_grid_item, null);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.Title);
        
        if (festivalList.get(position).getName().length() > 12)
        	textViewTitle.setTextSize(14);

        textViewTitle.setText(festivalList.get(position).getName());
        
        textViewTitle.setTextColor(Color.WHITE);
        ImageView preview = (ImageView) convertView.findViewById(R.id.Thumbnail);
        preview.setBackgroundResource(new ImageHandler().getFestivalDrawable(festivalList.get(position).getID()));

        return convertView;
    }
}
