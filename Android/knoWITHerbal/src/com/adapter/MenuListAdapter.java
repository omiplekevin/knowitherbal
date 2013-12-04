package com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.config.Config;

public class MenuListAdapter extends ArrayAdapter<String>{

	LayoutInflater inflater;
	String[] menus;
	Context context;
	
	public MenuListAdapter(Context context, String[] menus) {
		super(context, 0, menus);
		this.menus = menus;
		this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.navigation_item, null);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);
		TextView label = (TextView)convertView.findViewById(R.id.text1);
		
		label.setTypeface(Config.fontFace(context));
		
		switch(position)
		{
		case 0:
			imageView.setImageResource(R.drawable.menu_camera);
			label.setText(menus[0]);
			break;
		case 1:
			imageView.setImageResource(R.drawable.menu_list);
			label.setText(menus[1]);
			break;
		case 2:
			imageView.setImageResource(R.drawable.menu_info);
			label.setText(menus[2]);
			break;
		case 3:
			imageView.setImageResource(R.drawable.menu_osl);
			label.setText(menus[3]);
			break;
		}
		return convertView;
	}
}
