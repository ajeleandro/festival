package com.artefacto1971.festival.classes;

import android.view.LayoutInflater;
import android.view.View;

public interface ScheduleItemInterface {
	public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
