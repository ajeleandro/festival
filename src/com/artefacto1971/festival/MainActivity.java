package com.artefacto1971.festival;

import java.util.List;

import com.artefacto1971.festival.classes.Category;
import com.artefacto1971.festival.classes.News;
import com.artefacto1971.festival.logic.DrawerMenuAdapter;
import com.artefacto1971.festival.twitter.TwitNiceActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.net.Uri;
import android.os.Bundle;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
								   		
    private static final String TAG = MainActivity.class.getSimpleName();
     
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
	public static DownloadManager dm;
	public static long enqueue;
    
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
         
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,  GravityCompat.START);
         
        // Add items to the ListView
        mDrawerList.setAdapter(new DrawerMenuAdapter(this));
        // Set the OnItemClickListener so something happens when a 
        // user clicks on an item.
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
         
        // Enable ActionBar app icon to behave as action to toggle the NavigationDrawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
         
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            
        	public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }
             
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }
        };
         
        mDrawerLayout.setDrawerListener(mDrawerToggle);
         
        // Set the default content area to item 0
        // when the app opens for the first time
        if(savedInstanceState == null) {
            navigateTo(0);
        }
    }
     
    /*
     * If you do not have any menus, you still need this function
     * in order to open or close the NavigationDrawer when the user 
     * clicking the ActionBar app icon.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
       
        return super.onOptionsItemSelected(item);
    }
     
    /*
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
     * and onConfigurationChanged()
     */
     
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
     
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
     
    private class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            navigateTo(position);
        }
    }
     
    private void navigateTo(int position) {
         
    	Intent intent = null;
    	
        switch(position) {
        
        case 0://Choose Your Festival
        	getSupportFragmentManager().beginTransaction()
        	.replace(R.id.content_frame, new Fragment_FestivalList(),Fragment_FestivalList.TAG).commit();
        	break;
        case 1://Shows And Line Up
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.content_frame, new Tabbed_Shows_LineUp(), Tabbed_Shows_LineUp.TAG).commit();
            break;
        case 2://News And Promos
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.content_frame, new Tabbed_News_Promos(), Tabbed_News_Promos.TAG).commit();
            break;
        case 3://Your Pics And Fashion
        	getSupportFragmentManager().beginTransaction().addToBackStack(null)
        	.replace(R.id.content_frame, new Tabbed_Pics_Fashion(),Tabbed_Pics_Fashion.TAG).commit();
        	break;
        case 4://Your Voice
    		intent = new Intent(this,TwitNiceActivity.class);
    		startActivityForResult(intent, 0);
        	break;
        case 5://Shop
        	intent = new Intent(this,MarketplaceActivity.class);
    		startActivityForResult(intent, 0);
        	break;
        case 6://Log In
        	intent = new Intent(this,Facebook_Activity.class);
    		startActivityForResult(intent, 0);
        	break;
        case 7://test
        	intent = new Intent(this,TestActivity.class);
    		startActivityForResult(intent, 0);
        	break;
        }
        mDrawerList.clearChoices();
        mDrawerLayout.closeDrawer(mDrawerList);
    }
     
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 }
