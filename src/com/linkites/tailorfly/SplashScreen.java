package com.linkites.tailorfly;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class SplashScreen extends Activity {
	protected boolean _active = true;
	protected int _splashTime = 2000; // Splash screen time
	Context applicationContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        //this will remove the title bar from page
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(R.layout.splash_screen);
		setContentView(R.layout.splash_screen);
 
		applicationContext = this;
		
		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

				}
				runOnUiThread(endSplashThread);
			}
		};
		splashTread.start();
	}

	private Runnable endSplashThread = new Runnable() {
		public void run() {
			finish();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(),MainActivity.class);
			startActivity(intent);
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_active = false;
		}
		return true;
	}
}
