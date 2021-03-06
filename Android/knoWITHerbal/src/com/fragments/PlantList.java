package com.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.utilities.Utilities;
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
public class PlantList extends SherlockFragment{

	View view;
	Utilities util;
	private boolean plantview = false;
	/* 
	 * plantview
	 * TRUE = listview;
	 * FALSE = gridview;
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.plantlist_frame, null);
			
		}
		/*FragmentManager fm = getFragmentManager();
		fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				// TODO Auto-generated method stub
				if(getFragmentManager().getBackStackEntryCount() == 0)
				{
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastPress) > 3000)
					{
						Toast.makeText(getSherlockActivity(), "Press again to exit", Toast.LENGTH_LONG).show();
						lastPress = currentTime;
					}
					else
					{
						System.exit(0);
					}
				}
			}
		});*/
		setHasOptionsMenu(true);
		return view;
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		util = new Utilities(getSherlockActivity());
		PlantList_FragmentGridList gridList = new PlantList_FragmentGridList();
		PlantList_FragmentList listView = new PlantList_FragmentList();
		
		FragmentManager fm = getFragmentManager();
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
		if(util.isTablet(this.getActivity())){
			ft.replace(R.id.innerFrame, gridList);
		}
		else{
			plantview = true;
			ft.replace(R.id.innerFrame, listView);
		}
		ft.commit();
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
		/*FragmentManager fm = getFragmentManager();
		for(int i=0;i<fm.getBackStackEntryCount();i++){
			fm.popBackStack("details",FragmentManager.POP_BACK_STACK_INCLUSIVE);
			Log.i("BACKSTACK", "popping " + i);
		}*/
		setHasOptionsMenu(false);
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem settings = menu.add("View Toggle");
		
		if(plantview){
			settings.setIcon(R.drawable.to_grid);
		}
		else{
			settings.setIcon(R.drawable.to_list);
		}
		
		settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		settings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				if(plantview)
				{
					item.setIcon(R.drawable.to_list);
					ft.replace(R.id.innerFrame, new PlantList_FragmentGridList());
					plantview = false;
				}
				else
				{
					item.setIcon(R.drawable.to_grid);
					ft.replace(R.id.innerFrame, new PlantList_FragmentList());
					plantview = true;
				}
				ft.commit();
				return true;
			}
		});
	}
}
