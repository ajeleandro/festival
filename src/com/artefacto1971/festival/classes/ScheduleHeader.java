package com.artefacto1971.festival.classes;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.logic.ScheduleAdapter.RowType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ScheduleHeader implements ScheduleItemInterface {
    private final String name;
    private boolean isTime;

    public ScheduleHeader(String name, boolean isTime) {
        this.name = name;
        this.isTime = isTime;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        
        if (convertView == null) 
            view = (View) inflater.inflate(R.layout.header, null);
        else 
            view = convertView;
        
        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(name);

        //android color bug
        if(isTime)
        	text.setBackgroundColor(-12303292);
		else 
			text.setBackgroundColor(-12303292);
        return view;
    }
}