package com.example.nasadailyimage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	Handler handler = null;
	IotdHandler iotHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		handler = new Handler();
		refreshFromFeed();
	}
	
	private void resetDisplay(String title, String date,
			String description, Bitmap image) {
		TextView titleView = (TextView) findViewById(R.id.imageTitle);
		titleView.setText(title);
		TextView dateView = (TextView) findViewById(R.id.imageDate);
		dateView.setText(date);
		ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
		imageView.setImageBitmap(image);
		TextView descriptionView = (TextView) findViewById(R.id.imageDescription);
		descriptionView.setText(description);
	}
	
	public void refreshFromFeed() {
		
		Thread th = new Thread() {
			public void run() {
				if (iotHandler == null) {
					iotHandler = new IotdHandler();
				}
				iotHandler.processFeed();
				handler.post(new Runnable() {
					public void run () {
						resetDisplay(iotHandler.getTitle().toString(), iotHandler.getDate(), 
								iotHandler.getDescription().toString(), iotHandler.getImage());						
					}
				});
			}
		};
		th.start();
	}
	
	public void onRefresh(View view) {
		refreshFromFeed();
	}
	
	/*

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
