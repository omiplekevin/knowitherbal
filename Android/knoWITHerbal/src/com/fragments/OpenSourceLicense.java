package com.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.OpenSourceLicenseAdapter;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
/**
 * @author Kevin Jimenez Omiple
 * 
 * omiple.kevin@gmail.com
 *
 * Any replication codes without citation of the author aforementioned
 * is a direct violation of ownership rights of the author.
 *
 *
 */
public class OpenSourceLicense extends SherlockFragment{
	
	public View view;
	private StickyListHeadersListView listView;
	private String[] licenses;
	private String[] library;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.open_source_license, null);
		}
		
		
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		licenses = getResources().getStringArray(R.array.licenses);
		library = getResources().getStringArray(R.array.library);
		
		listView = (StickyListHeadersListView)view.findViewById(R.id.licenseList);
		listView.setAdapter(new OpenSourceLicenseAdapter(getActivity(), licenses, library));
	}
	
	

}
