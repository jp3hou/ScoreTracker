package com.example.scoretrackerunc;

import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

public class PebbleSportsService extends Service {

	private static Timer timer = new Timer();
	private Context ctx;
	private final static UUID PEBBLE_APP_UUID2 = UUID
			.fromString("dd616e5a-8ae3-4db5-8816-0b6fadda8e66");
	private static final String TAG = "PebbleSportsServivce";
	private static Runnable r;
	private static Thread t;
	private static String[] scoreUpdate;
	private static Boolean firstScore = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		ctx = this;
		startService();

	}

	private void startService() {

		r = new Runnable() {
			@Override
			public void run() {

				ArrayList<String> getA = null;
				PebbleDictionary dict = new PebbleDictionary();
				int count = 0;
				try {
					getA = getSports("Baseball");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Prints scores to Pebble
				Log.d(TAG, "getA is done");
				dict.addString(5, "----SCORES----");
				if (firstScore) {
					dict.addString(6, scoreUpdate[0]);
					dict.addString(7, scoreUpdate[1]);

					PebbleKit.sendDataToPebble(getApplicationContext(),
							PEBBLE_APP_UUID2, dict);
					if (count == 2) {
						firstScore = false;
						count = 0;
					}
					count++;
				}
				timer.scheduleAtFixedRate(new mainTask(), 0, 900000);
				stopSelf();

			}

		};
		t = new Thread(r);
		t.start();
	}

	private class mainTask extends TimerTask {
		@Override
		public void run() {

			startService();
		}
	}

	@Override
	public void onDestroy() {
		t.interrupt();
		super.onDestroy();
		Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
	}

	private ArrayList<String> getSports(String sport) throws URISyntaxException {

		assertNotNull(sport);
		ArrayList<String> intA = new ArrayList<String>();
		String orig = "http://sports.espn.go.com/mlb/bottomline/scores";
		InputStream is = null;
		BufferedReader br;
		URL url;
		String line;

		try {
			t.interrupt();
			Log.d(TAG, "inside try statement of getSports");
			url = new URL(orig);
			is = url.openStream();
			Log.d(TAG, "stream opened");
			String output = "";
			br = new BufferedReader(new InputStreamReader(is));
			System.out.println(orig);
			line = br.readLine();
			System.out.println(line);
			
			assertThat(line, !containsString("Error: Not found city"));
			
			if (line.contains("Error: Not found city") == false) {

				url = new URL(orig);
				br.close();
				is.close();
				is = url.openStream();
				br = new BufferedReader(new InputStreamReader(is));
				line = "";
				while ((line = br.readLine()) != null) {
					output = output.concat(line);

				}

				output = "http://Here%20are%20the%20scores:%20?".concat(output);

				ArrayList<String> scores = getUrlParameters(output);

				SportsFragment.setScoresList(scores);

				intA = scores;
				t.interrupt();
			} else {
				t.interrupt();
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
					t.interrupt();
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d(TAG, "caught error thrown" + e);
					e.printStackTrace();
				}
			}
		}
		t.interrupt();
		return intA;
	}

	private ArrayList<String> getUrlParameters(String url)
			throws UnsupportedEncodingException {
		Log.d(TAG, "getURLParameters is Reached");
		
		assertNotNull(url);
		
		ArrayList<String> scores = new ArrayList<String>();
		String[] urlParts = url.split("\\?");
		String score;

		// Checks for multiple lines
		if (urlParts.length > 1) {
			int i = 1;

			int count = urlParts.length - 1;
			// iterates through url to collect all scores
			while (count > 0) {

				String query = urlParts[i];

				i++;
				count--;
				for (String param : query.split("&")) {

					Log.d(TAG, "param " + param + "  ");
					String pair[] = param.split("=");

					if (pair.length > 1) {
						// matches pattern of scores, collects data
						if (pair[0].matches("mlb_s_left[1-9]*")) {
							score = pair[1].replaceAll("%20", " ");
							score = score.replaceAll("\\^", "");
							firstScore = true;
							setScoreUpdate(score);
							scores.add(score);

						}

					}
				}
			}
			return scores;
		}
		return scores;

	}

	private void setScoreUpdate(String score) {
		assertNotNull(score);
		Log.d(TAG, "setScoreUpdate Reached");
		if (firstScore) {
			Log.d(TAG, "Score is - " + score);
			if (score.matches(".*at.*")) {

				scoreUpdate = score.split("at");

			} else {

				scoreUpdate = score.split(" " + " " + " ");

			}

			for (int i = 0; i < 2; i++) {
				scoreUpdate[i] = scoreUpdate[i].trim();
				if (scoreUpdate[i].length() >= 14) {

					int x = scoreUpdate[i].length() - 1;
					scoreUpdate[i] = scoreUpdate[i].substring(0, 9) + " "
							+ scoreUpdate[i].substring(x - 11, x - 7);
				}
			}

		}

	}

}
