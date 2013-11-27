package com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.KnoWITHerbalMain;
import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class AboutThisApplication extends SherlockFragment{

	View view;
	ImageView imageView;
	TextView apptitle;
	TextView subtitle;
	
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
		
		Button howToUse = (Button)view.findViewById(R.id.howto);
		howToUse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KnoWITHerbalMain main = new KnoWITHerbalMain();
				main.createWelcome(getSherlockActivity());
			}
		});
		
		imageView = (ImageView)view.findViewById(R.id.imageView1);
		apptitle = (TextView)view.findViewById(R.id.title);
		subtitle = (TextView)view.findViewById(R.id.subtitle);
		
		AnimatorSet image = new AnimatorSet();
		image.playTogether(
				ObjectAnimator.ofFloat(imageView, "translationY", -30, 0),
				ObjectAnimator.ofFloat(imageView, "alpha", 0,0.5f,1));
		
		AnimatorSet title = new AnimatorSet();
		title.playTogether(
				ObjectAnimator.ofFloat(apptitle, "translationY", -100, 0),
				ObjectAnimator.ofFloat(apptitle, "alpha", 0, 0.5f, 1));
		
		AnimatorSet sub = new AnimatorSet();
		sub.playTogether(
				ObjectAnimator.ofFloat(subtitle, "translationY", 100, 0),
				ObjectAnimator.ofFloat(subtitle, "alpha", 0, 0.6f, 1));
		
		AnimatorSet allAnim = new AnimatorSet();
		allAnim.playTogether(image, title, sub);
		allAnim.setDuration(2000);
		allAnim.start();
	}

}
