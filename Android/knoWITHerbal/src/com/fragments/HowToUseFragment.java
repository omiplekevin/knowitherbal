package com.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.HowToUseListViewAdapter;
import com.adapter.HowToUseViewPagerAdapter;

public class HowToUseFragment extends SherlockFragment{

	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
			view = inflater.inflate(R.layout.help, null);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		HowToUseViewPagerAdapter adapter = new HowToUseViewPagerAdapter(getActivity());
		ViewPager pager = (ViewPager)view.findViewById(R.id.helpPager);
		String[] content = {""};
		
		final ListView listView = (ListView)view.findViewById(R.id.listView1);
		content = getActivity().getResources().getStringArray(R.array.help_content1);
		
		listView.setAdapter(new HowToUseListViewAdapter(getActivity(), content));
		
		final ProgressBar pb = (ProgressBar)view.findViewById(R.id.progressBar1);
		pb.setMax(adapter.getCount());
		pb.setProgress(pager.getCurrentItem()+1);
		
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(final int arg0) {
				// TODO Auto-generated method stub
				pb.setProgress(arg0+1);
				AsyncTask <Void, Void, Void> changeContent = new AsyncTask<Void, Void, Void>(){
					String[] switchContent = {""};
					HowToUseListViewAdapter adapter;
					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						
						switch(arg0)
						{
						case 0:
							switchContent = getResources().getStringArray(R.array.help_content1);
							break;
						case 1:
							switchContent = getResources().getStringArray(R.array.help_content2);
							break;
						case 2:
							switchContent = getResources().getStringArray(R.array.help_content3);
							break;
						case 3:
							switchContent = getResources().getStringArray(R.array.help_content4);
							break;
						case 4:
							switchContent = getResources().getStringArray(R.array.help_content5);
							break;
						case 5:
							switchContent = getResources().getStringArray(R.array.help_content6);
							break;
						case 6:
							switchContent = getResources().getStringArray(R.array.help_content7);
							break;
						case 7:
							switchContent = getResources().getStringArray(R.array.help_content8);
							break;
						case 8:
							switchContent = getResources().getStringArray(R.array.help_content9);
							break;
						}
						adapter = new HowToUseListViewAdapter(getActivity(), switchContent);
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
//						super.onPostExecute(result);
						listView.setAdapter(adapter);
						listView.invalidate();
					}
					
				};
				changeContent.execute();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
