package com.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.HowToUseListViewAdapter;
import com.adapter.HowToUseViewPagerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class HowToUseFragment extends SherlockFragment{

	private View view;
	ImageButton toRight;
	ImageButton toLeft;
	int currentPage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
			view = inflater.inflate(R.layout.theapplication_help, null);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		toRight = (ImageButton)view.findViewById(R.id.imageButton1);
		toLeft = (ImageButton)view.findViewById(R.id.imageButton2);
		
		HowToUseViewPagerAdapter adapter = new HowToUseViewPagerAdapter(getActivity());
		final ViewPager pager = (ViewPager)view.findViewById(R.id.helpPager);
		String[] content = {""};
		
		final ListView listView = (ListView)view.findViewById(R.id.listView1);
		content = getActivity().getResources().getStringArray(R.array.help_content1);
		
		listView.setAdapter(new HowToUseListViewAdapter(getActivity(), content));
		
		toRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playAnimation(1);
				pager.setCurrentItem(pager.getCurrentItem()+1, true);
			}
		});
		
		toLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playAnimation(-1);
				pager.setCurrentItem(pager.getCurrentItem()-1, true);
			}
		});
		currentPage = 0;
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(final int arg0) {
				// TODO Auto-generated method stub
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
				if(currentPage < arg0)
				{
					playAnimation(1);
					currentPage = arg0;
				}
				else
				{
					playAnimation(-1);
					currentPage = arg0;
				}
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
	
	private void playAnimation(int FLAG)
	{
		switch(FLAG)
		{
		case 1:
			AnimatorSet rightAnim = new AnimatorSet();
			rightAnim.playSequentially(ObjectAnimator.ofFloat(toRight, "translationX", 20, 0));
			rightAnim.setDuration(500);
			rightAnim.start();
			break;
		case -1:
			AnimatorSet leftAnim = new AnimatorSet();
			leftAnim.playSequentially(ObjectAnimator.ofFloat(toLeft, "translationX", -20, 0));
			leftAnim.setDuration(500);
			leftAnim.start();
			break;
		}
	}

}
