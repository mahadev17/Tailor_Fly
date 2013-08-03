/**
 * 
 */
package com.linkites.tailorfly.hotels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.linkites.Constants.Constant;
import com.linkites.tailorfly.R;
import com.linkites.tailorfly.util.Utility;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author apple
 *
 */
public class HotelsMainActivity extends Activity {

	private ActionBar mActionBar;
    private LayoutInflater mInflater;
    private Context appContext;
    //Autocomplete textfield
    private AutoCompleteTextView atxtCity;
    private ArrayAdapter<String> adapterCitys;
    private ArrayList<String> arrayListCitys;
    //Dates
    private int CheckInYear,CheckInMonth,CheckInDay,CheckInDate;
    private int CheckOutYear,CheckOutMonth,CheckOutDay,CheckOutDate;
    private TextView txtCheckInDate,txtCheckInMonth,txtCheckInDay;
    private TextView txtCheckOutDate,txtCheckOutMonth,txtCheckOutDay;
    
    static final int DATE_DIALOG_CHECK_IN = 1;
    static final int DATE_DIALOG_CHECK_OUT = 2;
    
    private TypedArray days,months;
    //Spinners
    Spinner spinnerAdults,spinnerChildren;
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);
		setContentView(R.layout.hotel_main_layout);
		
		appContext = this;
		//initialise the Action bar 
		initActionBar();
		
		//Initialise the AutoCOmplete textfield
		arrayListCitys = new ArrayList<String>();
		atxtCity = (AutoCompleteTextView) findViewById(R.id.atxtCityList);
        adapterCitys = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, arrayListCitys);
        atxtCity.setAdapter(adapterCitys);
        
        //text change event
        atxtCity.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (atxtCity.isPerformingCompletion()) {return;}
				//call the city api
				Runnable runnableCity = new Runnable() {
					@Override
					public void run() {
						String query = atxtCity.getText().toString().trim();
						try 
						{
							query = URLEncoder.encode(query, "UTF-8");
							String URL = Constant.SEARCH_CITYS+query;
							// TODO Auto-generated method stub
							String response = Utility.findJSONFromUrl(URL);
							if (response==null) {
								return;
							}
							response = response.replace("[\"", "");
							response = response.replace("\"]", "");
							String arr[] = response.split("\",\"");
							arrayListCitys.clear();
							for (int i=0; i < arr.length;i++) {
								arrayListCitys.add(arr[i]);
							}
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									adapterCitys.clear();
									adapterCitys.addAll(arrayListCitys);
								}
						});
							
					}
				};
				Thread thread = new Thread(runnableCity);
				thread.start();
			}
		});
		
		//initialise Dates in labels
		Resources res = getResources();
		days = res.obtainTypedArray(R.array.Days);
		months = res.obtainTypedArray(R.array.Month);
		
		final Calendar c = Calendar.getInstance();
		
        CheckInYear = c.get(Calendar.YEAR);
        CheckInMonth = c.get(Calendar.MONTH);
        CheckInDay = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("day "+CheckInDay);
        CheckInDate = c.get(Calendar.DAY_OF_MONTH);
        
        CheckOutYear = c.get(Calendar.YEAR);
        CheckOutMonth = c.get(Calendar.MONTH);
        CheckOutDay = c.get(Calendar.DAY_OF_WEEK);
        CheckOutDate = c.get(Calendar.DAY_OF_MONTH);
        
        //comment out later when no need
        CheckOutDate = CheckOutDate + 2;
        
		txtCheckInDate = (TextView) findViewById(R.id.txtHotelCheckInDate);
		txtCheckInDay = (TextView) findViewById(R.id.txtHotelCheckInDay);
		txtCheckInDay.setText("("+days.getString(CheckInDay-1)+")");
		txtCheckInMonth = (TextView) findViewById(R.id.txtHotelCheckInMonth);
		
		txtCheckOutDate = (TextView) findViewById(R.id.txtHotelCheckOutDate);
		txtCheckOutDay = (TextView) findViewById(R.id.txtHotelCheckOutDay);
		txtCheckOutDay.setText("("+days.getString(CheckOutDay-1)+")");
		txtCheckOutMonth = (TextView) findViewById(R.id.txtHotelCheckOutMonth);
		
		initCheckInCheckOutDateLabels();
		
		//set the click events on dates 
		LinearLayout layoutCheckIn = (LinearLayout) findViewById(R.id.layoutCheckIn);
		layoutCheckIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_CHECK_IN);
			}
		});
		LinearLayout layoutCheckOut = (LinearLayout) findViewById(R.id.layoutCheckOut);
		layoutCheckOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_CHECK_OUT);
			}
		});
		
		//initialise the Passenger counts in Spinners
		spinnerAdults = (Spinner) findViewById(R.id.spinnerAdults);
		spinnerChildren = (Spinner) findViewById(R.id.spinnerChildren);
        /* spinner selection methods 
        spinnerAdults.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {}
                    public void onNothingSelected(AdapterView<?> parent) { }
                });*/
		
		//Go Button Click listnerer
		{//Hotels button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.imgBtnGo);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					String cityOrState = atxtCity.getText().toString().trim();
					//cityOrState = "London,UK";
					if (cityOrState.length()==0)
					{
						Utility.ShowStringAlertWithMessage(appContext, "Alert", "Please type your city or location.");
						return;
					}
					try 
					{
						cityOrState = URLEncoder.encode(cityOrState, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String arrivalDate = ""+(CheckInMonth+1)+"/"+CheckInDate+"/"+CheckInYear;
					String departureDate = ""+(CheckOutMonth+1)+"/"+CheckOutDate+"/"+CheckOutYear;
					
					String xmlInPut = "<HotelListRequest>";
					xmlInPut = xmlInPut+"<city>"+cityOrState+"</city>";
					xmlInPut = xmlInPut+"<arrivalDate>"+arrivalDate+"</arrivalDate>";
					xmlInPut = xmlInPut+"<departureDate>"+departureDate+"</departureDate>";
					xmlInPut = xmlInPut+"<RoomGroup><Room>";
					xmlInPut = xmlInPut+"<numberOfAdults>"+spinnerAdults.getSelectedItem().toString()+"</numberOfAdults>";
					xmlInPut = xmlInPut+"<numberOfChildren>"+spinnerChildren.getSelectedItem().toString()+"</numberOfChildren>";
					xmlInPut = xmlInPut+"</Room></RoomGroup>";
					xmlInPut = xmlInPut+"<numberOfResults>50</numberOfResults>";
					xmlInPut = xmlInPut+"</HotelListRequest>";
 					// TODO Auto-generated method stub
					//start hotel activity 
					Intent intent = new Intent(getApplicationContext(), HotelsListingActivity.class);
					intent.putExtra("xmlInPut", xmlInPut);
					startActivity(intent);
					//obj = null;
				}
			});
        }
	}
	private void initCheckInCheckOutDateLabels()
	{
		//check-in date
		txtCheckInDate.setText(String.valueOf(CheckInDate));
		txtCheckInMonth.setText(months.getString(CheckInMonth)+" "+CheckInYear);
		//check out date
		txtCheckOutDate.setText(String.valueOf(CheckOutDate));
		txtCheckOutMonth.setText(months.getString(CheckOutMonth)+" "+CheckOutYear);
	}
	
	
	//Date Dialog Delegates
    @Override
    protected Dialog onCreateDialog(int id) 
    {
    	switch (id) {
		case DATE_DIALOG_CHECK_IN:
			return new DatePickerDialog(this,mDateCheckInSetListener,CheckInYear,CheckInMonth, CheckInDate);
		case DATE_DIALOG_CHECK_OUT:
			return new DatePickerDialog(this,mDateCheckOutSetListener,CheckInYear,CheckInMonth, CheckInDate);
		default:
		}
         return null;
    }
    private DatePickerDialog.OnDateSetListener mDateCheckInSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SimpleDateFormat")
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                        int dayOfMonth) {
                   CheckInDate = dayOfMonth;
                   CheckInMonth = monthOfYear;
                   CheckInYear = year;
                   SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                   @SuppressWarnings("deprecation")
                   Date date = new Date(CheckInYear, CheckInMonth, CheckInDate-1);
                   String dayOfWeek = simpledateformat.format(date);
                   txtCheckInDay.setText("("+dayOfWeek+")");
                   initCheckInCheckOutDateLabels();
                }
     };
     private DatePickerDialog.OnDateSetListener mDateCheckOutSetListener =
             new DatePickerDialog.OnDateSetListener() {
                 @SuppressLint("SimpleDateFormat")
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                         int dayOfMonth) {
                     CheckOutDate = dayOfMonth;
                     CheckOutMonth = monthOfYear;
                     CheckOutYear = year;
                     SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                     @SuppressWarnings("deprecation")
                     Date date = new Date(CheckOutYear, CheckOutMonth, CheckOutDate-1);
                     String dayOfWeek = simpledateformat.format(date);
                     txtCheckOutDay.setText("("+dayOfWeek+")");
                     initCheckInCheckOutDateLabels();
                 }
      };

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.hold, R.anim.push_out_to_left);
		finish();
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
