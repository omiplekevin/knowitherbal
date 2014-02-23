package com.fragments;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.config.Config;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PublishModel;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
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
public class About extends SherlockFragment{

	View view;
	ImageView logo;
	ImageView title;
	TextView subtitle;
	TextView publishInfo;
	private PublishModel pubInfo;
	private SQLiteDatabase sqliteDB;
	private DatabaseHelper dbHelper;
	private long lastClick;
	private int comboClick;
	
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
		comboClick = 0;
//		getSherlockActivity().getSupportActionBar().setTitle(Html.fromHtml("<i>About</i>"));
		logo = (ImageView)view.findViewById(R.id.webBtn);
		title = (ImageView)view.findViewById(R.id.imageView3);
		subtitle = (TextView)view.findViewById(R.id.content);
		publishInfo = (TextView)view.findViewById(R.id.publish_info);
		
		dbHelper = new DatabaseHelper(getSherlockActivity());
		pubInfo = Queries.getPublishInfo(sqliteDB, dbHelper);
		
		AnimatorSet imageViewAnim = new AnimatorSet();
		imageViewAnim.playTogether(
				ObjectAnimator.ofFloat(logo, "translationX", -30, 0),
				ObjectAnimator.ofFloat(logo, "alpha", 0,0.5f,1));
		
		AnimatorSet appNameAnim = new AnimatorSet();
		appNameAnim.playTogether(
				ObjectAnimator.ofFloat(title, "translationX", 50, 0),
				ObjectAnimator.ofFloat(title, "alpha", 0, 0.5f, 1));
		
		AnimatorSet subtitle = new AnimatorSet();
		subtitle.playTogether(
				ObjectAnimator.ofFloat(this.subtitle, "translationX", -30, 0),
				ObjectAnimator.ofFloat(this.subtitle, "alpha", 0, 0, 1));
		
		AnimatorSet allAnim = new AnimatorSet();
		allAnim.playTogether(imageViewAnim, appNameAnim, subtitle);
		allAnim.setDuration(2000);
		allAnim.start();
		
		publishInfo.setText(Html.fromHtml("Content publish date:<br/><b>"+pubInfo.getCreatedAt()+"</b>.<br/><br/>" +
				"Remarks:<br/>\" <i>"+pubInfo.getComment()+"</i> \"<br/><br/>"));
		
		logo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long currentTime = System.currentTimeMillis();
				if((currentTime-lastClick) < 1000)
				{
					comboClick++;
					if(comboClick == 5)
					{
						if(!Config.FUNMODE)
						{
							Toast.makeText(getSherlockActivity(), "FUNMODE! Check your plant lists! ^_^", Toast.LENGTH_LONG).show();
							Config.FUNMODE = true;
						}
						else
						{
							Toast.makeText(getSherlockActivity(), "FUNMODE disabled", Toast.LENGTH_LONG).show();
							Config.FUNMODE = false;
						}
					}
				}
				else
				{
					lastClick = currentTime;
					comboClick = 0;
				}
			}
		});
		
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getSherlockActivity().getSupportActionBar().setTitle("The Application");
	}
}
