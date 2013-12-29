package com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class About extends SherlockFragment{

	View view;
	ImageView logo;
	ImageView title;
	TextView subtitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.theapplication_about, null);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		logo = (ImageView)view.findViewById(R.id.imageView1);
		title = (ImageView)view.findViewById(R.id.imageView3);
		subtitle = (TextView)view.findViewById(R.id.textView1);
		
		AnimatorSet imageViewAnim = new AnimatorSet();
		imageViewAnim.playTogether(
				ObjectAnimator.ofFloat(logo, "translationY", -30, 0),
				ObjectAnimator.ofFloat(logo, "alpha", 0,0.5f,1));
		
		AnimatorSet appNameAnim = new AnimatorSet();
		appNameAnim.playTogether(
				ObjectAnimator.ofFloat(title, "translationY", -100, 0),
				ObjectAnimator.ofFloat(title, "alpha", 0, 0.5f, 1));
		
		AnimatorSet subtitle = new AnimatorSet();
		subtitle.playTogether(
				ObjectAnimator.ofFloat(this.subtitle, "translationY", -150, 0),
				ObjectAnimator.ofFloat(this.subtitle, "alpha", 0, 0, 1));
		
		AnimatorSet allAnim = new AnimatorSet();
		allAnim.playTogether(imageViewAnim, appNameAnim, subtitle);
		allAnim.setDuration(2000);
		allAnim.start();
	}
}
