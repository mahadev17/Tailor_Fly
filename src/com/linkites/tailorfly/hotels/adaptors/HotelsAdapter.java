package com.linkites.tailorfly.hotels.adaptors;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.linkites.Constants.Constant;
import com.linkites.tailorfly.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class HotelsAdapter extends ArrayAdapter<JSONObject> 
{
	private LayoutInflater lflater;
	Context appContext;
	private ArrayList<JSONObject> jsonList;
	OnClickListener clickListener;
	HashMap<String, Bitmap> hmHotelThmbs;
	private int[] TripRating = {R.drawable.trip_adviser_0,R.drawable.trip_adviser_1,R.drawable.trip_adviser_2,R.drawable.trip_adviser_3,R.drawable.trip_adviser_4,R.drawable.trip_adviser_5};
	
	int RequestFor = 0;
	public HotelsAdapter(Context context, int requested,
			ArrayList<JSONObject> list, HashMap<String, Bitmap> hmThumbs) {
		super(context,requested, list);
		// TODO Auto-generated constructor stub
		jsonList = list;
		appContext = context;
		hmHotelThmbs = hmThumbs;
		RequestFor = requested; 
		lflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public View getView(final int position, View convertView, ViewGroup parent) {
		View holder = convertView;
		if (holder == null)
		{
			if (RequestFor == Constant.HOTEL_CELL) {
				holder = lflater.inflate(com.linkites.tailorfly.R.layout.hotel_list_cell, null);
			}
			if (RequestFor == Constant.ROOM_CELL) {
				holder = lflater.inflate(com.linkites.tailorfly.R.layout.hotel_list_cell, null);
			}
		} // fif ends
		
		try {
			JSONObject obj;
			obj = jsonList.get(position);
			if (obj != null)
			{
				if (RequestFor == Constant.HOTEL_CELL)
				{
					ImageView imgThumb = (ImageView) holder.findViewById(com.linkites.tailorfly.R.id.imgThumbHotel);
					TextView txtHotelPrice = (TextView) holder.findViewById(R.id.txtHotelPrice);
					TextView txtHotelName = (TextView) holder.findViewById(com.linkites.tailorfly.R.id.txtHotelName);
					TextView txtHotelRating = (TextView) holder.findViewById(com.linkites.tailorfly.R.id.txtHotelRating);
					ImageView imgTripRating = (ImageView) holder.findViewById(com.linkites.tailorfly.R.id.imgTripRating);
					
					txtHotelPrice.setText("$"+obj.getString("highRate"));
					txtHotelName.setText(obj.getString("name"));
					txtHotelName.setText(obj.getString("name").replace("&amp;", "&"));
					String cAddress = obj.getString("address1")+", "+obj.getString("city")
							+", "+obj.getString("countryCode")+", "+obj.getString("postalCode");
					txtHotelRating.setText(cAddress.replace("&amp;", "&"));
					//set trip advisor rating
					int TripAdRating = (int)Float.parseFloat(obj.getString("tripAdvisorRating"));
					imgTripRating.setImageResource(TripRating[TripAdRating]);
					//show hotel thumb
					Bitmap bitmapThumb = hmHotelThmbs.get(obj.getString("hotelId"));
					if (bitmapThumb!=null) {
						imgThumb.setImageBitmap(bitmapThumb);
					}else{
						imgThumb.setImageResource(R.drawable.ic_launcher);
					}
				}
				if (RequestFor == Constant.ROOM_CELL) {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return holder;
	}
}
