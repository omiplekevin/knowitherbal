package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.LMO.capstone.R;

public class HowToUseListViewAdapter extends ArrayAdapter<String>{

	Context context;
	String[] items;
	
	public HowToUseListViewAdapter(Context context, String[] objects) {
		super(context, 0, objects);
		this.context = context;
		items = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.help_listitem, null);
		
		TextView content = (TextView)view.findViewById(R.id.textView2);
		content.setText(items[position]);
		
		return view;
	}

}