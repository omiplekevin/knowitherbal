package com.fragments;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.PlantListAdapter;
import com.algorithm.ORB.ItemModel;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PlantModel;

public class ResultFragment extends SherlockFragment{
	
	int resultID;
	PlantListAdapter adapter;
	ListView resultList;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	String pathCaptured;
	ImageView imageView;
	ArrayList<ItemModel> items;
	
	public void setResult(String pathCaptured, ArrayList<ItemModel> matchRating)
	{
		this.items = matchRating;
		this.pathCaptured = pathCaptured;
	}
	
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
			view = inflater.inflate(R.layout.result_fragment, null);
		
		imageView = (ImageView)view.findViewById(R.id.logo);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		dbHelper = new DatabaseHelper(getSherlockActivity());
		sqliteDB = dbHelper.getReadableDatabase();
		
		resultList = (ListView)view.findViewById(R.id.about_listview);
		imageView.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(pathCaptured)));
		
		ArrayList<PlantModel> plants = new ArrayList<PlantModel>();
		plants = Queries.getPlants(sqliteDB, dbHelper);
		
		ArrayList<PlantModel> plantResults = new ArrayList<PlantModel>();
		for(int i=0;i<items.size();i++)
		{
			plantResults.add(plants.get(items.get(i).x));
		}
		
		adapter = new PlantListAdapter(getSherlockActivity(), plantResults);
		resultList.setAdapter(adapter);
		
	}

}
