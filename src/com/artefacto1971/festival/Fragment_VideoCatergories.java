package com.artefacto1971.festival;

import java.util.ArrayList;

import com.artefacto1971.festival.classes.Category;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Fragment_VideoCatergories extends Fragment{

	ArrayList<Category> List = new ArrayList<Category>();
	ProgressBar progressBar;
	ListView vimeolistview;
	View view;
	
	public static Fragment_VideoCatergories newInstance() {
        return new Fragment_VideoCatergories();
    }
}
