package com.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.DetailViewPagerAdapter;
import com.models.PlantModel;

public class DetailFragments_Photos extends SherlockFragment{

	private View view;
	private static PlantModel item;
	private ViewPager viewPager;
	private DetailViewPagerAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.plantlist_item_photo, container, false);
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
		viewPager = (ViewPager)view.findViewById(R.id.viewPager);
		adapter = new DetailViewPagerAdapter(getActivity(), item.imgUrls);
		viewPager.setAdapter(adapter);
		viewPager.setPageTransformer(true, new DepthPageTransformer());
	}
	
	public void setItems(PlantModel item)
	{
		DetailFragments_Photos.item = item;
	}
	
	public class DepthPageTransformer implements ViewPager.PageTransformer {
	    private float MIN_SCALE = 0.75f;

	    @SuppressLint("NewApi")
		public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();

	        if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
	            view.setAlpha(0);

	        } else if (position <= 0) { // [-1,0]
	            // Use the default slide transition when moving to the left page
	            view.setAlpha(1);
	            view.setTranslationX(0);
	            view.setScaleX(1);
	            view.setScaleY(1);

	        } else if (position <= 1) { // (0,1]
	            // Fade the page out.
	            view.setAlpha(1 - position);

	            // Counteract the default slide transition
	            view.setTranslationX(pageWidth * -position);

	            // Scale the page down (between MIN_SCALE and 1)
	            float scaleFactor = MIN_SCALE
	                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);

	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
	}

}
