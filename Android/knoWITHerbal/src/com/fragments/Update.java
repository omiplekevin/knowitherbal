package com.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PublishModel;

public class Update extends SherlockFragment{

	View view;
	private PublishModel pubInfo;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.theapplication_update, null);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		dbHelper = new DatabaseHelper(getSherlockActivity());
		pubInfo = Queries.getPublishInfo(sqliteDB, dbHelper);
		
		textView = (TextView)view.findViewById(R.id.textView1);
		textView.setText(Html.fromHtml("This application contains data created on <b>"+pubInfo.getCreatedAt()+"</b>.\n" +
				"Web Service's comment about this published data: \"<i>"+pubInfo.getComment()+"</i>\"\n\n" +
						"Do you want to check for an update?"));
		
	}

	
}
