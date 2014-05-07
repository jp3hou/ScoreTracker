package com.example.scoretrackerunc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PebbleCheckService extends Service {

	private static Timer timer = new Timer();
	private Context ctx;
	private final static UUID PEBBLE_APP_UUID2 = UUID
			.fromString("dd616e5a-8ae3-4db5-8816-0b6fadda8e66");
	private static final String TAG = "PebbleCheckServivce";
	private static Runnable r;
	private static Thread t;
	private String weatherUpdate;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Toast msg = Toast.makeText(this, "PebbleCheckService created",
				Toast.LENGTH_LONG);
		msg.show();
		ctx = this;
		Log.d(TAG, "PebbleCheckService starts");
		startService();

	}

	private void startService() {
		r = new Runnable() {
			@Override
			public void run() {

				ArrayList<Integer> getA;
				PebbleDictionary dict = new PebbleDictionary();
				getA = getWeather("Chapel%20Hill");
				Log.d(TAG, "getA is done");
				dict.addString(1, "Chapel Hill");
				dict.addString(2, "TEMP: " + getA.get(0));
				dict.addString(3, "HUMIDITY: " + getA.get(1));
				dict.addString(4, weatherUpdate);
				Log.d(TAG,
						"Pebble should read:  " + getA.get(0) + " "
								+ getA.get(1));
				PebbleKit.sendDataToPebble(getApplicationContext(),
						PEBBLE_APP_UUID2, dict);
				// t.interrupt();
				timer.scheduleAtFixedRate(new mainTask(), 0, 90000000);
				stopSelf();
			}
		};
		t = new Thread(r);
		t.start();
	}

	private class mainTask extends TimerTask {
		@Override
		public void run() {

			Log.d(TAG, "Timer Task is called");
			startService();
			// toastHandler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
	}

	private ArrayList<Integer> getWeather(String city) {

		assertNotNull(city);
		ArrayList<Integer> intA = new ArrayList<Integer>();
		PebbleDictionary dict = new PebbleDictionary();
		String orig = "http://api.openweathermap.org/data/2.5/weather?q=";
		orig = orig.concat(city);
		InputStream is = null;
		t = null;
		BufferedReader br;
		URL url;
		String line;
		try {
			// t = new Thread();
			// t.start();
			Log.d(TAG, "inside try statement of getWEather");
			url = new URL(orig);
			is = url.openStream();
			Log.d(TAG, "stream opened");
			String output = "";
			Log.d(TAG,
					"inside try statement of getWEather, before bufferedReader");
			br = new BufferedReader(new InputStreamReader(is));
			Log.d(TAG,
					"inside try statement of getWEather, buffered reader read this"
							+ br);
			System.out.println(orig);
			line = br.readLine();
			System.out.println(line);
			assertThat(line, containsString("&mode=xml");
			assertThat(line, !containsString("Error: Not found city"));
			if (line.contains("Error: Not found city") == false) {
				// t = new Thread();
				// t.start();
				orig = orig.concat("&mode=xml");
				url = new URL(orig);
				br.close();
				is.close();
				is = url.openStream();
				br = new BufferedReader(new InputStreamReader(is));
				line = "";
				while ((line = br.readLine()) != null) {
					output = output.concat(line);
				}
				System.out.println("Printing");
				System.out.println(output);
				System.out.println("Done printing");
				assertThat(output, containsString("temperature value="));
				int tempIndex = output.indexOf("temperature value=");
				tempIndex = tempIndex + 19;
				String tempString = output.substring(tempIndex, tempIndex + 6);
				tempString = tempString.replace("\"", "");
				Double temp = Double.valueOf(tempString);
				temp = (temp - 273) * 1.8 + 32;
				temp = temp * 100;
				int intTemp = temp.intValue();
				intTemp = intTemp / 100;
				System.out.println(intTemp);
				assertThat(intTemp, instanceOf(Integer.class));
				intA.add(intTemp);
				assertThat(output, containsString("weather number="));
				int weatherIndex = output.indexOf("weather number=");
				String weather = output.substring(weatherIndex + 28,
						weatherIndex + 40);
				assertThat(output, containsString("humidity number="));
				tempIndex = output.indexOf("humidity value=");
				tempIndex = tempIndex + 16;
				tempString = output.substring(tempIndex, tempIndex + 2);
				tempString = tempString.replace("\"", "");
				System.out.println(tempString);
				Double temp2 = Double.valueOf(tempString);
				// int intg2 = intg.intValue();
				int temp3 = temp2.intValue();
				System.out.println(temp3);
				intA.add(temp3);
				setWeather(weather);
				WeatherFragment.setWeatherList("Chapel Hill", intTemp, temp3,
						weather);
				Log.d(TAG, "weather list passed");
				// t.interrupt();
			} else {
				System.out.println(line);
				System.out.println("Caught");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "caught error thrown" + e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "caught error thrown" + e);
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d(TAG, "caught error thrown" + e);
					e.printStackTrace();
				}
			}
		}

		return intA;
	}

	private void setWeather(String weather) {
		assertNotNull(weather);
		weatherUpdate = weather;

	}

	private final Handler toastHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ArrayList<Integer> getA;
			if (!PebbleKit.isWatchConnected(getApplicationContext())) {
				Log.d(TAG, "ToastHandler is called");

			} else {

				PebbleDictionary dict = new PebbleDictionary();
				getA = getWeather("Chapel%20Hill");
				dict.addInt32(0, getA.get(0));
				dict.addInt32(2, getA.get(1));
				Log.d(TAG,
						"Pebble should read:  " + getA.get(0) + " "
								+ getA.get(1));
				PebbleKit.sendDataToPebble(getApplicationContext(),
						PEBBLE_APP_UUID2, dict);
			}

		}
	};

}
