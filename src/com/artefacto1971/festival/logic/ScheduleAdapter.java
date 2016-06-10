package com.artefacto1971.festival.logic;

import java.util.List;

import com.artefacto1971.festival.classes.ScheduleItemInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ScheduleAdapter extends ArrayAdapter<ScheduleItemInterface> {
    private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public ScheduleAdapter(Context context, List<ScheduleItemInterface> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}