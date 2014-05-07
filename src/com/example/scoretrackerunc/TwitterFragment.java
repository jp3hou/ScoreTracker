package com.example.scoretrackerunc;

import java.util.ArrayList;

import static org.junit.Assert.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TwitterFragment extends Fragment {
	View view;
	ArrayList<String> feedList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		//Inflate the layout for this fragment
		
		return inflater.inflate(R.layout.twitter_fragment,  container, false);
		
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		
		ArrayList<String> twitterList = null;
		showTwitterList(twitterList);
		
		super.onViewCreated(view, savedInstanceState);
	
	
	
	}
	
	
	private void showTwitterList(ArrayList<String> sportsList) {
		
		
		ListView twitterList = (ListView) getView().findViewById(R.id.listSports);
		feedList = new ArrayList<String>();
		getTwitterList();
		
		//Create the adapter with passing ArrayList as 3rd parameter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,feedList);
		//Set The Adapter
		twitterList.setAdapter(arrayAdapter);
		
		//register onClickListener to Handle click events on each item
		twitterList.setOnItemClickListener(new OnItemClickListener(){
			
			//implement actions upon feed clicks here
			public void onItemClick(AdapterView<?>arg0, View v, int position, long arg3) {
				
			}
		});
		
		
	}

	void getTwitterList() {
		feedList.add("This is the first TWITTER feed");
		feedList.add("This is the second TWITTER feed");
		feedList.add("This is the third TWITTER feed");
		feedList.add("This is the fourth TWITTER feed");
		feedList.add("This is the fifth TWITTER feed");
		feedList.add("This is the sixth TWITTER feed");
		feedList.add("This is the seventh TWITTER feed");
		feedList.add("This is the eigth TWITTER feed");
		feedList.add("This is the ninth  TWITTER feed");
		feedList.add("This is the tenth TWITTER feed");
		feedList.add("This is the eleventh TWITTER feed");
		feedList.add("This is the twelfth TWITTER feed");
		String a = "a";
		assertEquals(a, "a");
	}

	};

	

