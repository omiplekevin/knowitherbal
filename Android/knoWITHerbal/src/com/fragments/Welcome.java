package com.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Welcome extends SherlockFragment{

	private ImageView imageView;
	private ImageView appName;
	private TextView version;
	private TextView subtitle;
	private ImageView webBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.opening_fragment, null);
		getSherlockActivity().getSupportActionBar().setTitle("");
		imageView = (ImageView)view.findViewById(R.id.logo);
		appName = (ImageView)view.findViewById(R.id.app_name);
		version = (TextView)view.findViewById(R.id.visit);
		subtitle = (TextView)view.findViewById(R.id.title);
		webBtn = (ImageView)view.findViewById(R.id.webBtn);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		AnimatorSet imageViewAnim = new AnimatorSet();
		imageViewAnim.playTogether(
				ObjectAnimator.ofFloat(imageView, "translationY", -30, 0),
				ObjectAnimator.ofFloat(imageView, "alpha", 0,0.5f,1));
		
		AnimatorSet appNameAnim = new AnimatorSet();
		appNameAnim.playTogether(
				ObjectAnimator.ofFloat(appName, "translationY", -100, 0),
				ObjectAnimator.ofFloat(appName, "alpha", 0, 0.5f, 1));
		
		AnimatorSet versionAnim = new AnimatorSet();
		versionAnim.playTogether(
				ObjectAnimator.ofFloat(version, "translationY", 100, 0),
				ObjectAnimator.ofFloat(version, "alpha", 0, 0.6f, 1));
		
		AnimatorSet webButton = new AnimatorSet();
		webButton.playTogether(
				ObjectAnimator.ofFloat(webBtn, "translationX", 100, 0),
				ObjectAnimator.ofFloat(webBtn, "alpha", 0, 0.6f, 1));
		
		AnimatorSet subtitle = new AnimatorSet();
		subtitle.playTogether(
				ObjectAnimator.ofFloat(this.subtitle, "translationY", -150, 0),
				ObjectAnimator.ofFloat(this.subtitle, "alpha", 0, 0, 1));
		
		AnimatorSet allAnim = new AnimatorSet();
		allAnim.playTogether(imageViewAnim, appNameAnim, versionAnim, webButton, subtitle);
		allAnim.setDuration(2000);
		allAnim.start();
		
		webBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent webService = new Intent(Intent.ACTION_VIEW, Uri.parse("http://appspot.knowitherbal.com"));
				startActivity(webService);
			}
		});
	}

}
