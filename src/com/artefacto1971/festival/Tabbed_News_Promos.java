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
public class Tabbed_News_Promos extends Fragment {
 
    public static final String TAG = Tabbed_News_Promos.class.getSimpleName();
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    int event_id;
    String festival_name;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        event_id = ((FestivalAplication) getActivity().getApplication()).getEventID();
        if (event_id != 0)
    		festival_name = ((FestivalAplication) getActivity().getApplication()).getFestival_name();
    	 else
         	festival_name = "All";
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
				return new Fragment_News(event_id);
			case 2:
				return new Fragment_Promo(event_id);
			default:
				return null;
			}        	
        }
 
        @Override
        public int getCount() {
            return 2;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
        	position++;
            switch (position) {
            case 1:
				return festival_name + " NEWS";
			case 2:
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