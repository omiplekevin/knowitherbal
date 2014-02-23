package com.adapter;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DevPagerAdapter extends PagerAdapter{

	private Context context;
	
	public DevPagerAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View)arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView((View)view);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.developers_page_2, null);
		final ListView lstView = (ListView)view.findViewById(R.id.listView_dev);
		ArrayList<Object> draws = new ArrayList<Object>();
		switch(position)
		{
		case 0:
			draws.add(context.getResources().getDrawable(R.drawable.pat));
			draws.add("Fatima Ledesma");
			draws.add("Data Entries");
			draws.add(context.getResources().getDrawable(R.drawable.badge_data));
			draws.add("#B100CB");
			break;
		case 1:
			draws.add(context.getResources().getDrawable(R.drawable.lei));
			draws.add("Princess Lei Madriaga");
			draws.add("Web Service");
			draws.add(context.getResources().getDrawable(R.drawable.badge_web));
			draws.add("#FED800");
			break;
			
		case 2:
			draws.add(context.getResources().getDrawable(R.drawable.kev));
			draws.add("Kevin Omiple");
			draws.add("Android Application");
			draws.add(context.getResources().getDrawable(R.drawable.badge_android));
			draws.add("#6AD600");
			break;
		case 3:
			draws.add(context.getResources().getDrawable(R.drawable.consultant_adviser));
			draws.add("Jonnel Ryan Buisan");
			draws.add("Consultant");
			draws.add(context.getResources().getDrawable(R.drawable.badge_consultant_adviser));
			draws.add("#CC0000");
			break;
		case 4:
			draws.add(context.getResources().getDrawable(R.drawable.consultant_adviser));
			draws.add("Exander Barrios, MIM, MIT");
			draws.add("Adviser");
			draws.add(context.getResources().getDrawable(R.drawable.badge_consultant_adviser));
			draws.add("#CC0000");
			break;
		}
		Adapter2 adapter = new Adapter2(context, 0, draws);
		lstView.setAdapter(adapter);
		
		((ViewPager)container).addView(view,0);
		
		lstView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(android.os.Build.VERSION.SDK_INT > 14){
					try{
						View v = lstView.getChildAt(0);
						View v2 = lstView.getChildAt(1);
						int scroll = -v.getTop() + lstView.getFirstVisiblePosition() * v.getHeight();
						v.setScrollY((int)((v2.getScrollY()-scroll)/2));
						v2.setScrollY(v2.getScrollY()*2);
					}
					catch(Exception e)
					{
						Log.e("EXCEPTION", "Child View "+ position +" not yet drawn");
					}
				}
			}
		});
		
		return view;
	}
	
	
}

class Adapter2 extends ArrayAdapter<Object>
{

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	ArrayList<Object> views;
	Context context;
	
	public Adapter2(Context context, int textViewResourceId, ArrayList<Object> objects) {
		super(context, 0, objects);
		this.views = objects;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null)
		{
			View view = null;
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(position == 0){
				view = inflater.inflate(R.layout.dev_list_item, null);
				ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
				imageView.setImageDrawable((Drawable)views.get(0));
			}
			else if(position == 1)
			{
				view = inflater.inflate(R.layout.dev_list_item_detail, null);
				TextView name = (TextView)view.findViewById(R.id.textView1);
				TextView detail = (TextView)view.findViewById(R.id.textView2);
				detail.setTextColor(Color.parseColor((String)views.get(4)));
				ImageView badge = (ImageView)view.findViewById(R.id.imageView2);
				badge.setImageDrawable((Drawable)views.get(3));
				name.setText((String)views.get(1));
				detail.setText((String)views.get(2));
			}
			return view;
		}
		else{
			return convertView;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
