package com.linkites.tailorfly;


import java.util.Timer;
import java.util.TimerTask;

import bus.BusMainActivity;

import com.linkites.tailorfly.hotels.HotelsMainActivity;
import com.linkites.tailorfly.util.Utility;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ActionBar mActionBar;
    private LayoutInflater mInflater;
    
    //image gallery 
	Integer pics[] = { R.drawable.home_image, R.drawable.home_image2, R.drawable.home_image3, 
			R.drawable.home_image4,R.drawable.home_image5,R.drawable.home_image6 };

	private ImageSwitcher iSwitcher;
	int imageIndex = 0;
	Context applicationContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		applicationContext = this;
		//customize the Action bar 
		initActionBar();
		
		//image gallery 
		iSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
		//iSwitcher.setFactory(this);
		iSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		iSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		//use a timer to switch the images
	    long INTERVAL = 5000;
		Timer mTimer = new Timer(true);
        mTimer.scheduleAtFixedRate(new repeatTask(),INTERVAL,INTERVAL);
        
        //Get the All Home screen Buttons and set the Click event on them
        {//Hotels button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.ImageButtonHotels);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//start hotel activity 
					Intent intent = new Intent(applicationContext, HotelsMainActivity.class);
					startActivity(intent);
					
				}
			});
        }
        {//Holidays button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.ImageButtonHoliday);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utility.ShowStringAlertWithMessage(applicationContext, "Hold down", "Feature to be implemented");
				}
			});
        }
        {//Flights button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.ImageButtonFlights);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utility.ShowStringAlertWithMessage(applicationContext, "Hold down", "Feature to be implemented");
				}
			});
        }
        {//Bus button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.ImageButtonBus);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(applicationContext, BusMainActivity.class);
					startActivity(intent);
				}
			});
        }
        {//Offers button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.ImageButtonOffers);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utility.ShowStringAlertWithMessage(applicationContext, "Hold down", "Feature to be implemented");
				}
			});
        }
        {//Kashmir Tours button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.imageButtonKashmirTours);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utility.ShowStringAlertWithMessage(applicationContext, "Hold down", "Feature to be implemented");
				}
			});
        }

	}

	private final class repeatTask extends TimerTask{

        @Override
        public void run() 
        {
            //--do stuff--
        	runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (iSwitcher!=null) 
					{
						if (imageIndex>(pics.length-1)) {
							imageIndex = 0;
						}
				       	iSwitcher.setBackgroundResource(pics[imageIndex]);
				       	imageIndex = imageIndex + 1;
					}
				}
			});
        }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//create Action bar 
	private void initActionBar() {
        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
    }
}
