package com.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	List<ItemModel> result;
	
	public void setResult(String pathCaptured, List<ItemModel> result)
	{
		this.result = result;
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
		Bitmap captured = BitmapFactory.decodeFile(pathCaptured);
		int newW = captured.getWidth() / 4;
		int newH = captured.getHeight() / 4;
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(pathCaptured), newW, newH, false);
		imageView.setImageBitmap(Bitmap.createBitmap(scaledBitmap));
		
		ArrayList<PlantModel> plants = new ArrayList<PlantModel>();
		plants = Queries.getPlants(sqliteDB, dbHelper);
		
		ArrayList<PlantModel> plantResults = new ArrayList<PlantModel>();
		
		for(int i=0;i<plants.size();i++)
		{
			PlantModel model = plants.get(result.get(i).plantID);
			model.rating = result.get(i).match;
			plantResults.add(model);
		}
		
		ArrayList<PlantModel> newList = removeDuplicates(plantResults);
		Collections.sort(newList, new MatchComparator());
		Collections.reverse(newList);
		
		adapter = new PlantListAdapter(getSherlockActivity(), newList, true);
		resultList.setAdapter(adapter);
		
	}
	
	private ArrayList<PlantModel> removeDuplicates(ArrayList<PlantModel> collection)
	{
		ArrayList<PlantModel> newCollection = new ArrayList<PlantModel>();
		for(int i=0;i<collection.size();i++)
		{
			if(i==0)
			{
				newCollection.add(collection.get(i));
			}
			else
			{
				boolean hasFoundDuplicate = false;
				for(int x=0;x<newCollection.size();x++)
				{
					if(collection.get(i).getpID() == newCollection.get(x).getpID())
					{
						hasFoundDuplicate = true;
						break;
					}
				}
				if(!hasFoundDuplicate)
				{
					newCollection.add(collection.get(i));
					if(newCollection.size() == 6)
						break;
				}
			}
		}
		return newCollection;
	}
	
	public class MatchIndexComparator implements Comparator<Integer>
	{
		final ArrayList<PlantModel> matches;
		public MatchIndexComparator(ArrayList<PlantModel> matches)
		{
			this.matches = matches;
		}
		
		@Override
		public int compare(Integer lhs, Integer rhs) {
			// TODO Auto-generated method stub
			int int1 = matches.get(lhs).rating;
			int int2 = matches.get(rhs).rating;
			return int1-int2;
		}		
	}
	
	public static class MatchComparator implements Comparator<PlantModel>
	{
		@Override
		public int compare(PlantModel lhs, PlantModel rhs) {
			// TODO Auto-generated method stub
			return lhs.rating > rhs.rating ? -1 : lhs.rating < rhs.rating ? 1 : 0;
		}
		
	}

}
