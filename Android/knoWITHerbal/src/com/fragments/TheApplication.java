package com.fragments;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.config.Config;
import com.helper.AsyncTaskUpdateCheck;
import com.helper.DatabaseHelper;
import com.helper.XMLParser;
import com.utilities.Utilities;

public class TheApplication extends SherlockFragment{

	View view;
	ImageView imageView;
	TextView apptitle;
	TextView subtitle;
	Context context;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	Utilities util;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.theapplication, null);
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
		util = new Utilities(getSherlockActivity());
		ImageButton update = (ImageButton)view.findViewById(R.id.update);
		ImageButton help = (ImageButton)view.findViewById(R.id.howto);
		ImageButton developers = (ImageButton)view.findViewById(R.id.developer);
		ImageButton about = (ImageButton)view.findViewById(R.id.about);
		
		help.setOnClickListener(new OnClickListener() {
			
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
				switchContent(1);
			}
		});
		
		developers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchContent(2);
			}
		});
		
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchContent(3);
			}
		});
		
		imageView = (ImageView)view.findViewById(R.id.logo);
		apptitle = (TextView)view.findViewById(R.id.title);
		subtitle = (TextView)view.findViewById(R.id.subtitle);
	}
	
	private void switchContent(int contentID)
	{
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch(contentID)
		{
		case 0://HOW TO USE THE APPLICATION
			HowToUseFragment howto = new HowToUseFragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			ft.replace(R.id.baseFrame, howto);
			ft.addToBackStack("help");
			ft.commit();
			break;
		case 1://UPDATE
			if(util.isNetworkAvailable())
			{
				XMLParser preParser = new XMLParser(getSherlockActivity());
				preParser.grabXML(Config.xmlhostURL, Config.publishXML, false);
				try {
					if(preParser.checkPublish(Config.publishXML)){
						AsyncTaskUpdateCheck update = new AsyncTaskUpdateCheck(getSherlockActivity());
						update.execute();
					}
					else{
						AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
						AlertDialog dialog = builder.create();
						dialog.setTitle("Oops!");
						dialog.setMessage("We are currently digging up herbal plant data. Hold on! Check for updates anytime.");
						dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						dialog.show();
					}
				}
				catch (XmlPullParserException e) {}
				catch (IOException e) {}
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				AlertDialog dialog = builder.create();
				dialog.setTitle("Error");
				dialog.setMessage("No network connectivity. Connect to available networks and try again");
				dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new Dialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
			break;
		case 2://DEVELOPERS
			TheDevelopersFragment devs = new TheDevelopersFragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			ft.replace(R.id.baseFrame, devs);
			ft.addToBackStack("help");
			ft.commit();
			break;
		case 3://ABOUT
			About about = new About();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			ft.replace(R.id.baseFrame, about);
			ft.addToBackStack("about");
			ft.commit();
			break;
		}
	}
}
