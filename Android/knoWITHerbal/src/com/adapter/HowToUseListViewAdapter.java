package com.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.LMO.capstone.R;
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
		content.setText(Html.fromHtml(items[position]));
		
		return view;
	}

}
