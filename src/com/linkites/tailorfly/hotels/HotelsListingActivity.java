/**
 * 
 */
package com.linkites.tailorfly.hotels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.linkites.Constants.Constant;
import com.linkites.tailorfly.R;
import com.linkites.tailorfly.hotels.adaptors.HotelsAdapter;
import com.linkites.tailorfly.util.Utility;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @author apple
 *
 */
public class HotelsListingActivity extends Activity {

	private ActionBar mActionBar;
    private LayoutInflater mInflater;

	private Context appContext;
	private String jsonInput;
	private String errorMsg;
	int selectedLocationIndex = 0;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	//Hotel listing parms 
	private ListView listViewHotels;
	private ArrayList<JSONObject> arrayListHotels;
	private HotelsAdapter adaptorHotels;
	private String locations[] = {};
	//loading view 
	private LinearLayout layoutLoading;
	//HashMap for loading Images 
	HashMap<String, Bitmap> hmHotelThumbs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_list_main);
		appContext = this;
		
		//Initialize action bar 
		initActionBar();
		
		//Initialize array and lists 
		arrayListHotels = new ArrayList<JSONObject>();
		hmHotelThumbs = new HashMap<String, Bitmap>();
		listViewHotels = (ListView) findViewById(R.id.listViewHotels);
		adaptorHotels = new HotelsAdapter(appContext, Constant.HOTEL_CELL, arrayListHotels,hmHotelThumbs);
		listViewHotels.setAdapter(adaptorHotels);
		
		layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
		
		Bundle bundle = getIntent().getExtras();
		System.out.println("url "+bundle.getString("xmlInPut"));
		jsonInput = bundle.getString("xmlInPut");
		//start loading hotels in bg thread
		Thread thred = new Thread(runnLoadhotels);
		thred.start();
		
		//set the Listview item click listenerer
		listViewHotels.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
//    			<HotelInformationRequest>
//		    	<hotelId>122212</hotelId>
//		    	<options>0</options>
//		    	</HotelInformationRequest>
            	JSONObject objHotel = arrayListHotels.get(position);
            	if (objHotel!=null) 
            	{
    				try 
    				{
    					String xmlInPut = "<HotelInformationRequest>";
						xmlInPut = xmlInPut+"<hotelId>"+objHotel.getInt("hotelId")+"</hotelId>";
	    				xmlInPut = xmlInPut+"<options>HOTEL_DETAILS,PROPERTY_AMENITIES,HOTEL_IMAGES</options>";
	    				xmlInPut = xmlInPut+"</HotelInformationRequest>";
						Intent intent = new Intent(getApplicationContext(), HotelInfoActivity.class);
						intent.putExtra("xmlInPut", xmlInPut);
						intent.putExtra("hmHotelThumbs",hmHotelThumbs);
						intent.putExtra("HotelInfo", objHotel.toString());
						startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }
        });
	}
	
	
	private Runnable runnLoadhotels = new Runnable() {
		@Override
		public void run() 
		{
			String xmlInput = "";
			try 
			{
				xmlInput = URLEncoder.encode(jsonInput, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			String url = Constant.HOTEL_LISTING+"&xml="+xmlInput;
			String response = Utility.findJSONFromUrl(url);
			if (response==null) {
				Utility.ShowStringAlertWithMessage(appContext, "Alert", "No network.");
				return;
			}
			try 
			{
				JSONObject objResult = new JSONObject(response);
				if (objResult!=null) 
				{
					arrayListHotels.clear();
					System.out.println(objResult);
					objResult = objResult.getJSONObject("HotelListResponse");
					if (objResult.has("HotelList")) 
					{
						JSONObject objHotels = (JSONObject) objResult.get("HotelList");
						JSONArray arrHotels = objHotels.getJSONArray("HotelSummary");
						for (int i = 0; i < arrHotels.length(); i++) 
						{
							arrayListHotels.add((JSONObject) arrHotels.get(i));
						}
					}else
					{
						JSONObject objError = (JSONObject) objResult.get("EanWsError");
						errorMsg = objError.getString("presentationMessage");
						if (errorMsg.contains("Multiple locations")) 
						{
							JSONObject objLocations = objResult.getJSONObject("LocationInfos");
							JSONArray arraylocatins = objLocations.getJSONArray("LocationInfo");
							if (arraylocatins.length()>0) {
								locations = new String[arraylocatins.length()];
								for (int i = 0; i < arraylocatins.length(); i++) {
									JSONObject objLoc = (JSONObject) arraylocatins.get(i);
									locations[i] = objLoc.getString("code");
									System.out.println(objLoc.getString("code"));
								}
							}
						}
					}
				}
				runOnUiThread(mainLoadHotelsInUI);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	Runnable mainLoadHotelsInUI = new Runnable() {
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			if (arrayListHotels.size()>0)
			{
				layoutLoading.setVisibility(View.GONE);
				adaptorHotels.notifyDataSetChanged();
				
				Thread thread = new Thread(runnableImageLoading);
				thread.start();
			}else
			{
				if (errorMsg.contains("Multiple locations")) 
				{
					
			 		AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
			 		builder.setCancelable(false);
			 		builder.setIcon(R.drawable.ic_launcher);
			 		builder.setTitle(errorMsg);
			 		builder.setSingleChoiceItems(locations, 0, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	                        /* User clicked on a radio button do some stuff */
	                    	selectedLocationIndex = whichButton;
	                    }
	                });
			 		// Set behavior of positive button
			 		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			 			public void onClick(DialogInterface dialog, int which) {
			 				// OK the dialog
			 				try 
			 				{
				 				String selectedLocation = locations[selectedLocationIndex];
				 				int start = jsonInput.indexOf("<city>");
				 				int end = (jsonInput.indexOf("</city>"));
				 				String oldLocation = jsonInput.substring(start, end);
				 				jsonInput = jsonInput.replace(oldLocation, ("<city>")+selectedLocation);
				 				//start loading hotels in bg thread
				 				Thread thred = new Thread(runnLoadhotels);
				 				thred.start();
							} catch (Exception e) {
								// TODO: handle exception
								Utility.ShowStringAlertWithMessage(appContext, "Alert", "Can not process your request.Please try after some time.");
							}
			 			}
			 		});
			 		// Set behavior of negative button
			 		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 			public void onClick(DialogInterface dialog, int which) {
			 				// Cancel the dialog
			 				dialog.cancel();
			 				finish();
			 			}
			 		});
			 		AlertDialog alert = builder.create();
			 		alert.show();
				}else
				{
					// Assign the alert builder , this can not be assign in Click events
			   		AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
			   		builder.setCancelable(false);
			   		builder.setMessage(errorMsg);
			   		builder.setTitle("Alert");
			   		// Set behavior of negative button
			   		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

			   			public void onClick(DialogInterface dialog, int which) {
			   				// Cancel the dialog
			   				finish();
			   			}
			   		});
			   		AlertDialog alert = builder.create();
			   		alert.show();
				}
			}
		}
	};
	
	Runnable runnableImageLoading = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < arrayListHotels.size(); i++) 
			{
				try 
				{
					JSONObject objHotel = arrayListHotels.get(i);
					String thumburl = Constant.EAN_IMAGES_BASE_URL+ objHotel.getString("thumbNailUrl");
					Bitmap bitmapThumb = Utility.getSimpleBitmap(thumburl);
					if (bitmapThumb!=null) {
						hmHotelThumbs.put(objHotel.getString("hotelId"), bitmapThumb);
						runOnUiThread(runnableLoadImageInUI);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	Runnable runnableLoadImageInUI = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			adaptorHotels.notifyDataSetChanged();
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
