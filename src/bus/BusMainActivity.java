/**
 * 
 */
package bus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import psl.api.ssapi.client.SSRestServiceClient;
import psl.api.ssapi.model.CityList;

import com.linkites.Constants.Constant;
import com.linkites.tailorfly.R;
import com.linkites.tailorfly.hotels.HotelsListingActivity;
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
public class BusMainActivity extends Activity {
	
	private ActionBar mActionBar;
    private LayoutInflater mInflater;
    private Context appContext;
    //Autocomplete textfield
    private AutoCompleteTextView atxtSCity,atxtDCity;
    private ArrayAdapter<String> adapterCitys;
    private ArrayList<String> arrayListCitys;
    //Dates
    private int CheckInYear,CheckInMonth,CheckInDay,CheckInDate;
    private TextView txtCheckInDate,txtCheckInMonth,txtCheckInDay;
    
    static final int DATE_DIALOG_CHECK_IN = 1;
    
    private TypedArray days,months;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_main_layout);
		
		appContext = this;
		//initialise the Action bar 
		initActionBar();
		
		//Initialise the AutoCOmplete textfield
		arrayListCitys = new ArrayList<String>();
		atxtSCity = (AutoCompleteTextView) findViewById(R.id.atxtBusSCityList);
		atxtDCity = (AutoCompleteTextView) findViewById(R.id.atxtBusDCityList);
        adapterCitys = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, arrayListCitys);
        atxtSCity.setAdapter(adapterCitys);
        atxtDCity.setAdapter(adapterCitys);
		
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
        
		txtCheckInDate = (TextView) findViewById(R.id.txtBusCheckInDate);
		txtCheckInDay = (TextView) findViewById(R.id.txtBusCheckInDay);
		txtCheckInDay.setText("("+days.getString(CheckInDay-1)+")");
		txtCheckInMonth = (TextView) findViewById(R.id.txtBusCheckInMonth);
		
		initCheckInCheckOutDateLabels();
		
		//set the click events on dates 
		LinearLayout layoutCheckIn = (LinearLayout) findViewById(R.id.layoutBusJourneyDate);
		layoutCheckIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_CHECK_IN);
			}
		});
		
		//Go Button Click listnerer
		{//Hotels button
        	ImageButton imgBtnHome = (ImageButton) findViewById(R.id.imgBusBtnGo);
        	imgBtnHome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					String cityOrStateS = atxtSCity.getText().toString().trim();
					String cityOrStateD = atxtSCity.getText().toString().trim();
					//cityOrState = "London,UK";
					if (cityOrStateS.length()==0)
					{
						Utility.ShowStringAlertWithMessage(appContext, "Alert", "Please type your city or location.");
						return;
					}
					if (cityOrStateD.length()==0)
					{
						Utility.ShowStringAlertWithMessage(appContext, "Alert", "Please type your city or location.");
						return;
					}
					
					try 
					{
						cityOrStateS = URLEncoder.encode(cityOrStateS, "UTF-8");
						cityOrStateD = URLEncoder.encode(cityOrStateD, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String arrivalDate = ""+(CheckInMonth+1)+"/"+CheckInDate+"/"+CheckInYear;

					//String url = String.format(Constant.RED_BUS_BOOKING_SERVICE, );
 					// TODO Auto-generated method stub
					//start hotel activity 
					Intent intent = new Intent(getApplicationContext(), HotelsListingActivity.class);
					//intent.putExtra("xmlInPut", xmlInPut);
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
		
		//Load the Sources and destination citys 
		LoadCitys();
	}
	//method will load citys in textfields
	private void LoadCitys()
	{
		final SSRestServiceClient client = SSRestServiceClient
	            .getInstance("http://api.seatseller.travel:9191", Constant.RED_BUS_CONSUMER_KEY,
	            		Constant.RED_BUS_CONSUMER_SECRET);
		
		CityList listOfCitys = client.getAllSources();
		System.out.println(listOfCitys);

		Runnable runnableLoadCitys = new Runnable() {
			@Override
			public void run() {
			}
		};
		Thread thred = new Thread(runnableLoadCitys);
		thred.start();
	}
	
	//Date Dialog Delegates
    @Override
    protected Dialog onCreateDialog(int id) 
    {
    	switch (id) {
		case DATE_DIALOG_CHECK_IN:
			return new DatePickerDialog(this,mDateCheckInSetListener,CheckInYear,CheckInMonth, CheckInDate);
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
        imgHeading.setImageResource(R.drawable.book_bus);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
    }

}
