package com.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
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
public class OpenSourceLicenseAdapter extends ArrayAdapter<String> implements StickyListHeadersAdapter{

	LayoutInflater inflater;
	Context context;
	String[] licenses;
	String[] library;
	
	public OpenSourceLicenseAdapter(Context context, String[] licenses, String[] library) {
		super(context, 0, licenses);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.licenses = licenses;
		this.library = library;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null)
		{
			holder = new Holder();
			convertView = inflater.inflate(R.layout.license_item, null);
			holder.license = (TextView)convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder)convertView.getTag();
		}
		
		holder.license.setText(licenses[position]);
		
		return convertView;
	}

	class Holder
	{
		TextView license;
	}


	/*************************************************
	 * STICKY LIST VIEW
	 * ***********************************************/
	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HeaderViewHolder holder;
		if(convertView == null)
		{
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.osl_stickyheader, null);
			holder.image = (ImageView)convertView.findViewById(R.id.logo);
			holder.library = (TextView)convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		}
		else
		{
			holder = (HeaderViewHolder)convertView.getTag();
		}
		
		holder.library.setText(library[position]);
		
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	class HeaderViewHolder
	{
		ImageView image;
		TextView library;
	}

}
