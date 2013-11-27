package com.fragments;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.adapter.DetailViewPagerAdapter;
import com.models.PlantModel;

public class DetailFragment extends SherlockFragment{
	
	/*variables*/
	private int currentPage;
	
	/*views*/
	private ViewPager pager;
	private TextView plantName;
	private View view;
	private ImageButton websearch;
//	private ImageButton photoPreview;
	
	/*instance*/
	private PlantModel plantItem;
	private Timer swipeTimer;
	private Handler handler;
	private Runnable Update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
			view = inflater.inflate(R.layout.plantlist_itemdetails, container, false);
		
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSherlockActivity().getSupportActionBar().setTitle(Html.fromHtml("<i>Information</i>"));
		menu.getItem(0).setVisible(false);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroy();
		swipeTimer.cancel();
		getSherlockActivity().getSupportActionBar().setTitle("Plant List");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentPage = 0;
		
		super.onViewCreated(view, savedInstanceState);
		pager = (ViewPager)view.findViewById(R.id.imageViewPager);
		plantName = (TextView)view.findViewById(R.id.title);
		websearch = (ImageButton)view.findViewById(R.id.clear);
		websearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent search = new Intent();
				search.setAction(Intent.ACTION_WEB_SEARCH);
				search.putExtra(SearchManager.QUERY, plantItem.getName() + " Plant");
				startActivity(search);
			}
		});
		
		plantName.setText(plantItem.getName());
		
		pager.setAdapter(new DetailViewPagerAdapter(getSherlockActivity(), plantItem.imgUrls));
		pager.setPageTransformer(true, new DepthPageTransformer());
		
		handler = new Handler();
        Update = new Runnable() {
            public void run() {
                if (currentPage == plantItem.imgUrls.size()) {
                    currentPage = 0;
                }
//                Log.i("current viewpager item", ""+currentPage);
                pager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DetailFragments_Details details = new DetailFragments_Details();
        details.setItems(plantItem);
        ft.replace(R.id.detailFrame, details);
        ft.commit();
	}
	
	public void setItem(PlantModel item)
	{
		plantItem = item;
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
