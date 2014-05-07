package com.example.scoretrackerunc;

import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers;

import java.util.ArrayList;
import java.util.Hashtable;

import com.getpebble.android.kit.util.PebbleDictionary;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class WeatherFragment extends Fragment {


	Button local,regional,national,world;
	static String weatherList;
	
	View view;
	static ArrayList<String> feedList;
	static ArrayList<Integer> feedList2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
	return inflater.inflate(R.layout.weather_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

	super.onViewCreated(view, savedInstanceState);
		
	showWeatherList();
	

	}

	
private void showWeatherList() {
		
		
		ListView sportList = (ListView) getView().findViewById(R.id.listSports);
		feedList = new ArrayList<String>();
		getWeatherList();
		
		//Create the adapter with passing ArrayList as 3rd parameter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,feedList);
		//Set The Adapter
		sportList.setAdapter(arrayAdapter);
		
		//register onClickListener to Handle click events on each item
		sportList.setOnItemClickListener(new OnItemClickListener(){
			
			//implement actions upon feed clicks here
			public void onItemClick(AdapterView<?>arg0, View v, int position, long arg3) {
				
			}
		});
		
		
	}

	public static void getWeatherList() {
		
		int x=0;
		
		if(weatherList!=null){
			feedList.add(weatherList);
		}else{
			
			while( x < 10){
			feedList2.add(x);
			x++;
			}
		}
	}
//	public static void setWeatherList(ArrayList<Integer>  weather) {
//		weatherList = weather;
//	}

	public static void setWeatherList(String string, int intTemp,
			Integer temp3, String weather) {
		
		assertNotNull(string);
		assertNotNull(intTemp);
		assertNotNull(temp3);
		assertNotNull(weather);
		weatherList = string + "       Temperature: " + intTemp + "    Humidity: " + temp3 +"     "+ weather ;
		
	}
	
}
