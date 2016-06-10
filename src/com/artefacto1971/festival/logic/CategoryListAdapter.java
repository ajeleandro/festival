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
import com.artefacto1971.festival.classes.Category;

public class CategoryListAdapter extends BaseAdapter {

	public static final String TAG = CategoryListAdapter.class.getSimpleName();
    Context context;
    private static LayoutInflater inflater = null;
    ArrayList<Category> categoryList;		
    int lastPosition = -1;

    public CategoryListAdapter(Context context, ArrayList<Category> festivalList) {
        this.context = context;
        this.categoryList = festivalList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
    	return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
    	return categoryList.get(position).getID();
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
        convertView = inflater.inflate(R.layout.layout_simplelist_row, null);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.Title);
        textViewTitle.setText(categoryList.get(position).getName());

        textViewTitle.setTextColor(Color.WHITE);
        ImageView preview = (ImageView) convertView.findViewById(R.id.Thumbnail); 
        preview.setBackgroundResource(new ImageHandler().getCategoryDrawable(categoryList.get(position).getID()));

        return convertView;
    }
}
