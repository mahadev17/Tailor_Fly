package com.linkites.tailorfly.hotels;
import com.linkites.tailorfly.R;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class HotelDetailTabActivity extends TabActivity{
	private Context appContext;
	private TabHost tabHost;
	private ActionBar mActionBar;
	 private LayoutInflater mInflater;
	
	 @SuppressWarnings("deprecation")
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_hotel_tab);
	        appContext=this;
	        
	      //Initialize action bar 
			initActionBar();
	        
	        Resources ressources = getResources(); 
			tabHost = getTabHost();
			
			Bundle bundle=getIntent().getExtras();
			String xmlInPut =bundle.getString("xmlInPut");
			String hotelInfo=bundle.getString("HotelInfo");
			Intent intent_home = new Intent().setClass(appContext,HotelInfoActivity.class);
			intent_home.putExtra("xmlInPut", xmlInPut);
			intent_home.putExtra("HotelInfo",hotelInfo);
			addTab(1,intent_home,R.drawable.tab);
			
			Intent intent_community = new Intent().setClass(appContext,HotelRoomInfoActivity.class);
			addTab(2,intent_community, R.drawable.tab);
			
			//set Windows tab as default (zero based)
			tabHost.setCurrentTab(0);
	 }
	 
	 
	 
	 private void addTab(int labelId, Intent activityIntent, int resId) {
			
	    	TabHost.TabSpec spec = tabHost.newTabSpec("tabid" + labelId);
			View indicator11 = LayoutInflater.from(this).inflate(R.layout.tab, null);
			ImageView img = (ImageView) indicator11.findViewById(R.id.tab_icon);
			img.setImageResource(resId);
			spec.setContent(activityIntent);
			spec.setIndicator(indicator11);
			tabHost.addTab(spec);
		}
	 
	//create Action bar 
		private void initActionBar()
		{
	        mActionBar = getActionBar();
	        mActionBar.setDisplayShowHomeEnabled(false);
	        mActionBar.setDisplayShowTitleEnabled(false);
	        mInflater = LayoutInflater.from(this);
	        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
	        ImageView imgHeading = (ImageView) mCustomView.findViewById(R.id.imageHeading);
	        imgHeading.setImageResource(R.drawable.holidays);
	        mActionBar.setCustomView(mCustomView);
	        mActionBar.setDisplayShowCustomEnabled(true);
	        // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
	    } 
	 
}
