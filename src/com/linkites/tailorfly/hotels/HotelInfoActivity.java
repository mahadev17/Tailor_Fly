/**
 * 
 */
package com.linkites.tailorfly.hotels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;

import com.linkites.Constants.Constant;
import com.linkites.tailorfly.R;
import com.linkites.tailorfly.hotels.adaptors.HotelsAdapter;
import com.linkites.tailorfly.util.Utility;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author apple
 *
 */
public class HotelInfoActivity extends Activity {

	private ActionBar mActionBar;
    private LayoutInflater mInflater;

	private Context appContext;
	private String jsonInput;
	
	//Hotel info object 
	JSONObject objHotelInfo;
	//Hotel listing parms 
	private ListView listViewHotels;
	private ArrayList<JSONObject> arrayListRooms;
	private HotelsAdapter adaptorRooms;

	//loading view 
	private LinearLayout layoutLoading;

	//HashMap for loading Images 
	HashMap<String, Bitmap> hmHotelThumbs;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_detail);
		appContext = this;
		
		//Initialize action bar 
		initActionBar();
		
		//Initialize array and lists 
		arrayListRooms = new ArrayList<JSONObject>();
		hmHotelThumbs = new HashMap<String, Bitmap>();
		
		
		/*listViewHotels = (ListView) findViewById(R.id.listViewHotels);
		adaptorRooms = new HotelsAdapter(appContext, Constant.ROOM_CELL, arrayListRooms,hmHotelThumbs);
		listViewHotels.setAdapter(adaptorRooms);*/
		
		layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
		((TextView)layoutLoading.findViewById(R.id.txtLoadingMsg)).setText("Loading Hotel Info....");
		
		//imageview for hotel thum
		ImageView imageThumbHotel=(ImageView) findViewById(R.id.imageThumbHotelDetail);
		
		Bundle bundle = getIntent().getExtras();
		System.out.println("url "+bundle.getString("xmlInPut"));
		
		
		/*//get hotel thumbnail's hashmap 
		hmHotelThumbs=(HashMap<String, Bitmap>) getIntent().getSerializableExtra("hmHotelThumbs");
		
		//get bitmap through hotel
		Bitmap bitmapThumb = hmHotelThumbs.get("");
		
		if (bitmapThumb!=null) {
			imageThumbHotel.setImageBitmap(bitmapThumb);
		}else{
			imageThumbHotel.setImageResource(R.drawable.ic_launcher);
		}*/
		
		jsonInput = bundle.getString("xmlInPut");
		try 
		{
			jsonInput = URLEncoder.encode(jsonInput, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//start loading hotel info in bg thread
		Thread thred = new Thread(runnLoadhotelInfo);
		thred.start();

	}
	
	private Runnable runnLoadhotelInfo = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String url = Constant.HOTEL_INFO+"&xml="+jsonInput;
			String response = Utility.findJSONFromUrl(url);
			try 
			{
				JSONObject objResult = new JSONObject(response);
				if (objResult!=null) 
				{
					objResult = objResult.getJSONObject("HotelInformationResponse");
					if (objResult.has("HotelDetails")) {
						objHotelInfo = objResult.getJSONObject("HotelDetails");
						System.out.println(objHotelInfo);
					}
				}
				runOnUiThread(mainLoadHotelInfoInUI);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	Runnable mainLoadHotelInfoInUI = new Runnable() {
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			if (objHotelInfo!=null) {
				//Hotel info has been found , show the info in page
				layoutLoading.setVisibility(View.GONE);
				//adaptorRooms.notifyDataSetChanged();
			}else
			{
				Utility.ShowStringAlertWithMessage(appContext, "Alert", "Error in getting Hotel Info. Please try after some time. ");
			}
		}
	};
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
