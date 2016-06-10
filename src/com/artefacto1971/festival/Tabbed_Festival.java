package com.artefacto1971.festival;

import com.artefacto1971.festival.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
@SuppressLint("ValidFragment")
public class Tabbed_Festival extends Fragment {
 
    public static final String TAG = Tabbed_Festival.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String festival_name;
    public int currentEventID;
 
    public Tabbed_Festival(int currentEventID){
    	this.currentEventID = currentEventID;
        return ;
    }
    
    public Tabbed_Festival(){
        return;
    }
    
    public Tabbed_Festival(int currentEventID, String festival_name){
    	this.festival_name = festival_name;
    	this.currentEventID = currentEventID;
        return ;
    }
     
    @Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("currentEventID", currentEventID);
        savedInstanceState.putString("festival_name", festival_name);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
        	currentEventID = savedInstanceState.getInt("currentEventID");
        	festival_name = savedInstanceState.getString("festival_name");
        }
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
         
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }
     
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
 
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public Fragment getItem(int position) {
        	position++;		

			switch (position){
			case 1:
				return new Fragment_Summary_GridView(currentEventID);
			case 2:
				return new Fragment_CategoryGrid(currentEventID);
			case 3:
				return new Fragment_ScheduleList(currentEventID);	
			case 4:
				return new Fragment_ComingSoon();
			default:
				return null;
			}        	
        }
 
        @Override
        public int getCount() {
            return 4;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
        	position++;
            switch (position) {
            case 1:
				return festival_name;
			case 2:
				return "-> SHOWS";
			case 3:
				return "-> LINE UP";
			case 4:
				return "-> GOING TO";
			default:
				return "tittle_section error";
            }
        }
    }
 
    public static class TabbedContentFragment extends Fragment {
 
        public static final String ARG_SECTION_NUMBER = "section_number";
 
        public TabbedContentFragment() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_content,container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}