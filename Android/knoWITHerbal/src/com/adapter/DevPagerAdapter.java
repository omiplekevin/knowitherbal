package com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class DevPagerAdapter extends PagerAdapter{

	private Context context;
	
	public DevPagerAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
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
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.developers_page, null);
		ImageView banner = (ImageView)view.findViewById(R.id.webBtn);
		TextView name = (TextView)view.findViewById(R.id.content);
		switch(position)
		{
		case 0:
			name.setText("Fatima D. Ledesma\nData");
			banner.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icecream_sandwhich)));
			break;
		case 1:
			name.setText("Princess Lei R. Madriaga\nWeb Service");
			banner.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.jelly_bean)));
			break;
		case 2:
			name.setText("Kevin J. Omiple\nAndroid Application");
			banner.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.kitkat)));
			break;
		}
		
		((ViewPager)container).addView(view,0);
		
		return view;
	}
	
	
}
