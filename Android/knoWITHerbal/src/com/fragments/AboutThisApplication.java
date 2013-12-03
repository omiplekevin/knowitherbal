package com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.HowToUseListViewAdapter;
import com.adapter.HowToUseViewPagerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class AboutThisApplication extends SherlockFragment{

	View view;
	ImageView imageView;
	TextView apptitle;
	TextView subtitle;
	Context context;
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
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
				context = getSherlockActivity();
				createWelcome();
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
	
	public void createWelcome()
	{
		Log.e("Context", context.toString());
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final AlertDialog dialog = builder.create();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.help, null);
		
		HowToUseViewPagerAdapter adapter = new HowToUseViewPagerAdapter(context);
		ViewPager pager = (ViewPager)view.findViewById(R.id.helpPager);
		String[] content = {""};
		
		final ListView listView = (ListView)view.findViewById(R.id.listView1);
		content = context.getResources().getStringArray(R.array.help_content1);
		
		for(String item : content)
				Log.e("content", item);
		
		listView.setAdapter(new HowToUseListViewAdapter(context, content));
		
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
							switchContent = context.getResources().getStringArray(R.array.help_content1);
							break;
						case 1:
							switchContent = context.getResources().getStringArray(R.array.help_content2);
							break;
						case 2:
							switchContent = context.getResources().getStringArray(R.array.help_content3);
							break;
						case 3:
							switchContent = context.getResources().getStringArray(R.array.help_content4);
							break;
						case 4:
							switchContent = context.getResources().getStringArray(R.array.help_content5);
							break;
						case 5:
							switchContent = context.getResources().getStringArray(R.array.help_content6);
							break;
						case 6:
							switchContent = context.getResources().getStringArray(R.array.help_content7);
							break;
						case 7:
							switchContent = context.getResources().getStringArray(R.array.help_content8);
							break;
						case 8:
							switchContent = context.getResources().getStringArray(R.array.help_content9);
							break;
						}
						adapter = new HowToUseListViewAdapter(context, switchContent);
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
		
		dialog.setView(view);
		dialog.setTitle("How to use the Application");
		/*dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});*/
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		Toast.makeText(context, "SWIPE TO VIEW MORE", Toast.LENGTH_LONG).show();
	}

}
