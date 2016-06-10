package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artefacto1971.festival.R;

public class DrawerMenuAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null; 
    private String[] mDrawerItmeTitle;
    
    public static final String TAG = DrawerMenuAdapter.class.getSimpleName();
    
    public DrawerMenuAdapter(Context context) {
        this.context = context;
        mDrawerItmeTitle =   context.getResources().getStringArray(R.array.drawer_titles);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return mDrawerItmeTitle.length;
    }

    @Override
    public Object getItem(int position){
    	return mDrawerItmeTitle[position];
    }

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        	view = inflater.inflate(R.layout.drawer_menu_row_item, null);
        
        TextView textview = (TextView) view.findViewById(R.id.menu_item_title);
        textview.setText(mDrawerItmeTitle[position]);
        
        ImageView imageView = (ImageView) view.findViewById(R.id.menu_item_icon);
        imageView.setBackgroundResource(new ImageHandler().getMenuItemDrawable(mDrawerItmeTitle[position]));
        
        return view;
    }
}