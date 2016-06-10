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
public class Tabbed_Main extends Fragment {
 
    public static final String TAG = Tabbed_Main.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public int event_id;
    
    public Tabbed_Main(){
    	this.event_id = 0;
        return ;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        	event_id = savedInstanceState.getInt("event_id");
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
				return new Fragment_News();
			case 2:
				return new Fragment_CategoryList();
			case 3:
				return new Fragment_Promo();
			default:
				return null;
			}        	
        }
 
        @Override
        public int getCount() {
            return 3;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
        	position++;
            switch (position) {
            case 1:
				return "NEWS";
			case 2:
				return "-> SHOWS";
			case 3:
				return "-> PROMOS";
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