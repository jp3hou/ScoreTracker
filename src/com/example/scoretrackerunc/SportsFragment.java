package com.example.scoretrackerunc;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SportsFragment extends Fragment {


	Button football, basketball, soccer, baseball, hockey, golf, lacrosse,
			curling, tennis, bowling, rugby;


	View view;
	static ArrayList<String> sportsList;
	ArrayList<Integer> feedList2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sports_view, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);
//		
//		football = (Button) getView().findViewById(R.id.Football);
//		basketball = (Button) getView().findViewById(R.id.Basketball);
//		soccer = (Button) getView().findViewById(R.id.Soccer);
		baseball = (Button) getView().findViewById(R.id.Baseball);
//		hockey = (Button) getView().findViewById(R.id.Hockey);
//		golf = (Button) getView().findViewById(R.id.Golf);
//		lacrosse = (Button) getView().findViewById(R.id.Lacrosse);
//		curling = (Button) getView().findViewById(R.id.Curling);
//		tennis = (Button) getView().findViewById(R.id.Tennis);
//		bowling = (Button) getView().findViewById(R.id.Bowling);
//		rugby = (Button) getView().findViewById(R.id.Rugby);
		
//		football.setOnClickListener(btnOnClickListener);
//		basketball.setOnClickListener(btnOnClickListener);
//		soccer.setOnClickListener(btnOnClickListener);
		baseball.setOnClickListener(btnOnClickListener);
//		hockey.setOnClickListener(btnOnClickListener);
//		golf.setOnClickListener(btnOnClickListener);
//		lacrosse.setOnClickListener(btnOnClickListener);
//		curling.setOnClickListener(btnOnClickListener);
//		tennis.setOnClickListener(btnOnClickListener);
//		bowling.setOnClickListener(btnOnClickListener);
//		rugby.setOnClickListener(btnOnClickListener);
		
	}
	
	Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			
			
			 if (v == baseball) {
				 Log.d(getTag(), "baseball button is clicked");
				showSportsList();
			 }

		}

		private void showSportsList() {
			
			
			ListView sportList = (ListView) getView().findViewById(R.id.listSports);
			
			//Create the adapter with passing ArrayList as 3rd parameter
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,sportsList);
			//Set The Adapter
			sportList.setAdapter(arrayAdapter);
			
			//register onClickListener to Handle click events on each item
			sportList.setOnItemClickListener(new OnItemClickListener(){
				
				//implement actions upon feed clicks here
				public void onItemClick(AdapterView<?>arg0, View v, int position, long arg3) {
					
				}
			});
			
			
		}

		public  ArrayList<String> getScoresList() {
			return sportsList;
			
			
		}
		
		
	};

	public static void setScoresList(ArrayList<String> scores) {
		
		sportsList =  scores;
		
	}

	
}
