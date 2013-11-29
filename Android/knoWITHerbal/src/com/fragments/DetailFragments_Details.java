package com.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.ExpandableListAdapter;
import com.models.PlantModel;

public class DetailFragments_Details extends SherlockFragment{

	private View view;
	private static PlantModel item;
	ExpandableListAdapter expandableListAdapter;
	ExpandableListView expandableListView;
	List<String> listHeader;
	HashMap<String, List<String>> listItems;
//	Button findOutMore;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.plantlist_item_detail, null);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(view != null)
		{
			ViewGroup viewGroup = (ViewGroup)view.getParent();
			if(viewGroup != null)
			{
				viewGroup.removeAllViews();
			}
		}
		Log.i("DetailFragment", "DetailFragment destroy");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		expandableListView = (ExpandableListView)view.findViewById(R.id.exListView);
		prepareListData();
		expandableListAdapter = new ExpandableListAdapter(getActivity(), listHeader, listItems);
		expandableListView.setAdapter(expandableListAdapter);
		expandableListView.setSelector(R.drawable.listitem_selector);
		
		/*findOutMore = (Button)view.findViewById(R.id.plantBtn);
		findOutMore.append(" " + item.getName());
		findOutMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent search = new Intent();
				search.setAction(Intent.ACTION_WEB_SEARCH);
				search.putExtra(SearchManager.QUERY, item.getName());
				startActivity(search);
			}
		});*/
		
	}
	
	public void prepareListData()
	{
		listHeader = new ArrayList<String>();
		listItems = new HashMap<String, List<String>>();
		
		listHeader.add("Name");
		listHeader.add("Scientific Name");
		listHeader.add("Availability");
		listHeader.add("Common Name");
		listHeader.add("Vernacular Names");
		listHeader.add("Properties");
		listHeader.add("Usage");
		
		//Name
		List<String> name = new ArrayList<String>();
		name.add(item.getName());
		
		//Sci. Name
		List<String> scientificNames = new ArrayList<String>();
		String[] sci = item.getScientific().split("\\|\\|");
		if(sci.length == 1 && sci[0].equals(""))
			scientificNames.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<sci.length;i++)
			{
				scientificNames.add(sci[i]);
			}
		}
		
		List<String> availability = new ArrayList<String>();
		String[] avail = item.getAvailability().split("\\|\\|");
		if(avail.length == 1 && avail[0].equals(""))
			availability.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<avail.length;i++)
			{
				availability.add(avail[i]);
			}
		}
		
		List<String> commonName = new ArrayList<String>();
		String[] common = item.getCommon().split("\\|\\|");
		if(common.length == 1 && common[0].equals(""))
			commonName.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<common.length;i++)
			{
				commonName.add(common[i]);
			}
		}
		
		List<String> vernacular = new ArrayList<String>();
		String[] ver = item.getVernacular().split("\\|\\|");
		if(ver.length == 1 && ver[0].equals(""))
			vernacular.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<ver.length;i++)
			{
				vernacular.add(ver[i]);
			}
		}
		
		List<String> properties = new ArrayList<String>();
		String[] prop = item.getProperties().split("\\|\\|");
		if(prop.length == 1 && prop[0].equals(""))
			properties.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<prop.length;i++)
			{
				properties.add(prop[i]);
			}
		}
		
		List<String> usage = new ArrayList<String>();
		String[] use = item.getUsage().split("\\|\\|");
		if(use.length == 1 && use[0].equals(""))
			availability.add("No entry/entries... :(");
		else
		{
			for(int i=0;i<use.length;i++)
			{
				usage.add(use[i]);
			}
		}
		
		listItems.put(listHeader.get(0), name);
		listItems.put(listHeader.get(1), scientificNames);
		listItems.put(listHeader.get(2), availability);
		listItems.put(listHeader.get(3), commonName);
		listItems.put(listHeader.get(4), vernacular);
		listItems.put(listHeader.get(5), properties);
		listItems.put(listHeader.get(6), usage);
		
	}
	
	public void setItems(PlantModel item)
	{
		DetailFragments_Details.item = item;
	}

}
