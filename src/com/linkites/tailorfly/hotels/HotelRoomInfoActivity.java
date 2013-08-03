package com.linkites.tailorfly.hotels;

import com.linkites.tailorfly.R;
import com.linkites.tailorfly.R.color;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class HotelRoomInfoActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		LinearLayout layoutLinear=(LinearLayout) findViewById(R.id.tabsLayout);
		layoutLinear.setBackgroundColor(color.Black);
	}

}
