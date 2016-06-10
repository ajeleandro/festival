package com.artefacto1971.festival.logic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artefacto1971.festival.R;

public class RefreshAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    public static final String TAG = RefreshAdapter.class.getSimpleName();
    private String text;
    boolean pulltorefresh;
    
    public RefreshAdapter(Context context, String text, boolean pulltorefresh) {
        this.context = context;
        this.text = text;
        this.pulltorefresh = pulltorefresh;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return 1;
    }

    @Override
    public Object getItem(int position) {
    	return position;
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
    public View getView(int position, View view, ViewGroup parent) {
	    
		view = inflater.inflate(R.layout.layout_refresh_row, null);
		
		if(!text.equals("")){
			TextView textView = (TextView) view.findViewById(R.id.text);
			textView.setText(text);
			textView.setVisibility(View.VISIBLE);
		}
		if(pulltorefresh){
			TextView pull = (TextView) view.findViewById(R.id.pullToRefresh);
			pull.setVisibility(View.VISIBLE);
		}
		
        return view;
    }
}
