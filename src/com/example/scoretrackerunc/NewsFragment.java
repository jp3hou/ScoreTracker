package com.example.scoretrackerunc;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NewsFragment extends Fragment {

	Button local,regional,national,world;

View view;
ArrayList<String> feedList;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
	Bundle savedInstanceState) {
return inflater.inflate(R.layout.news_fragment, container, false);
}

@Override
public void onViewCreated(View view, Bundle savedInstanceState) {

super.onViewCreated(view, savedInstanceState);

local = (Button) getView().findViewById(R.id.Local);
regional = (Button) getView().findViewById(R.id.Regional);
national = (Button) getView().findViewById(R.id.National);
world = (Button) getView().findViewById(R.id.World);


local.setOnClickListener(btnOnClickListener);
regional.setOnClickListener(btnOnClickListener);
national.setOnClickListener(btnOnClickListener);
world.setOnClickListener(btnOnClickListener);


}

Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
@Override
public void onClick(View v) {
	
	ArrayList<String> newsList = null;
	

	if (v == local) {
		showNewsList(newsList);
	} else if (v == regional) {
		showNewsList(newsList);
	} else if (v == national) {
		showNewsList(newsList);
	} else if (v == world) {
		showNewsList(newsList);
	} else {
		showNewsList(newsList);
	}



}

private void showNewsList(ArrayList<String> sportsList) {
	
	
	ListView newsList = (ListView) getView().findViewById(R.id.listSports);
	feedList = new ArrayList<String>();
	getNewsList();
	
	//Create the adapter with passing ArrayList as 3rd parameter
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,feedList);
	//Set The Adapter
	newsList.setAdapter(arrayAdapter);
	
	//register onClickListener to Handle click events on each item
	newsList.setOnItemClickListener(new OnItemClickListener(){
		
		//implement actions upon feed clicks here
		public void onItemClick(AdapterView<?>arg0, View v, int position, long arg3) {
			
		}
	});
	
	
}

void getNewsList() {
	feedList.add("This is the first NEWS feed");
	feedList.add("This is the second NEWS feed");
	feedList.add("This is the third NEWS feed");
	feedList.add("This is the fourth NEWS feed");
	feedList.add("This is the fifth NEWS feed");
	feedList.add("This is the sixth NEWS feed");
	feedList.add("This is the seventh NEWS feed");
	feedList.add("This is the eigth NEWS feed");
	feedList.add("This is the ninth  NEWS feed");
	feedList.add("This is the tenth NEWS feed");
	feedList.add("This is the eleventh NEWS feed");
	feedList.add("This is the twelfth NEWS feed");
	
}

};
}
