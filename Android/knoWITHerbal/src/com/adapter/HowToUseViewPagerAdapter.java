package com.adapter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HowToUseViewPagerAdapter extends PagerAdapter{

	private Context context;
	
	public HowToUseViewPagerAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container,final int position) {
		// TODO Auto-generated method stub
		final ImageView helpView = new ImageView(context);
		helpView.setScaleType(ScaleType.FIT_CENTER);
		AsyncTask<Void, Void, Bitmap> loadHelp = new AsyncTask<Void, Void, Bitmap>()
		{

			@Override
			protected Bitmap doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				
				try {
					InputStream inputStream = context.getAssets().open("images/help-("+(position + 1)+").png");
					return BitmapFactory.decodeStream(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        return null;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				helpView.setImageBitmap(result);
			}
		};
		
		loadHelp.execute();
		
		((ViewPager)container).addView(helpView,0);
		
		return helpView;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View)arg1);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		((ViewPager)container).removeView((View)view);
	}
}
