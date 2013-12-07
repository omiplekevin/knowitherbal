package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;

public class AboutThisApplication extends SherlockFragment{

	View view;
	ImageView imageView;
	TextView apptitle;
	TextView subtitle;
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.about_this_application, null);
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
		
		ImageButton update = (ImageButton)view.findViewById(R.id.update);
		ImageButton howToUse = (ImageButton)view.findViewById(R.id.howto);
		ImageButton developers = (ImageButton)view.findViewById(R.id.developer);
		ImageButton about = (ImageButton)view.findViewById(R.id.about);
		
		howToUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchContent(0);
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getSherlockActivity(), "UPDATE MODULE", Toast.LENGTH_SHORT).show();
				switchContent(1);
			}
		});
		
		developers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getSherlockActivity(), "DEVELOPERS MODULE", Toast.LENGTH_SHORT).show();
				switchContent(2);
			}
		});
		
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getSherlockActivity(), "ABOUT MODULE", Toast.LENGTH_SHORT).show();
				switchContent(3);
			}
		});
		
		imageView = (ImageView)view.findViewById(R.id.imageView1);
		apptitle = (TextView)view.findViewById(R.id.title);
		subtitle = (TextView)view.findViewById(R.id.subtitle);
	}
	
	private void switchContent(int contentID)
	{
		switch(contentID)
		{
		case 0:
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			HowToUseFragment howto = new HowToUseFragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			ft.replace(R.id.baseFrame, howto);
			ft.addToBackStack("help");
			ft.commit();
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

}
