package com.example.scoretrackerunc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;


public class MainActivity extends Activity {
	
	Fragment fragment;
	Button sports,weather,news,twitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(MainActivity.this, PebbleCheckService.class));
		
		sports = (Button) findViewById(R.id.Sports);
		weather = (Button) findViewById(R.id.Weather);
		news = (Button) findViewById(R.id.News);
		twitter = (Button) findViewById(R.id.Twitter);
		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		StartFragment myFragment = new StartFragment();
		ft.add(R.id.myFragment,  myFragment);
		ft.commit();
		
		sports.setOnClickListener(btnOnClickListener);
		news.setOnClickListener(btnOnClickListener);
		weather.setOnClickListener(btnOnClickListener);
		twitter.setOnClickListener(btnOnClickListener);
	}



	Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			Fragment newFragment;
			
			//initializes given fragment depending on view passed to method
			if(v == sports) {
				newFragment = new SportsFragment();
				startService(new Intent(MainActivity.this, PebbleSportsService.class));
			} else if (v == news) {
				newFragment = new NewsFragment();
			} else if (v == weather) {
				newFragment = new WeatherFragment();
				//startService(new Intent(MainActivity.this, PebbleCheckService.class));
				
			} else if (v == twitter) {
				newFragment = new TwitterFragment();
			} else {
				newFragment = new StartFragment();
			}
		
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.myFragment, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		
	}
		
	};

}
