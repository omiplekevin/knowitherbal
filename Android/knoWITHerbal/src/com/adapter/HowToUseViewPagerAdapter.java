package com.adapter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;

public class HowToUseViewPagerAdapter extends PagerAdapter{

	private Context context;
	
	public HowToUseViewPagerAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container,final int position) {
		// TODO Auto-generated method stub
//		final ImageView helpView = new ImageView(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View helpView = inflater.inflate(R.layout.theapplication_help_page, null);
		final ImageView imageView = (ImageView)helpView.findViewById(R.id.webBtn);
		final TextView textView = (TextView)helpView.findViewById(R.id.content);
		AsyncTask<Void, Void, Bitmap> loadHelp = new AsyncTask<Void, Void, Bitmap>()
		{

			@Override
			protected Bitmap doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				
				try {
					InputStream inputStream = context.getAssets().open("images/help-("+(position + 1)+").jpg");
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
				imageView.setImageBitmap(result);
				String res = "";
				switch(position)
				{
				case 0:
					res = context.getResources().getString(R.string.help_content1);
					break;
				case 1:
					res = context.getResources().getString(R.string.help_content2);
					break;
				case 2:
					res = context.getResources().getString(R.string.help_content3);
					break;
				case 3:
					res = context.getResources().getString(R.string.help_content4);
					break;
				case 4:
					res = context.getResources().getString(R.string.help_content5);
					break;
				case 5:
					res = context.getResources().getString(R.string.help_content6);
					break;
				case 6:
					res = context.getResources().getString(R.string.help_content7);
					break;
				case 7:
					res = context.getResources().getString(R.string.help_content8);
					break;
				case 8:
					res = context.getResources().getString(R.string.help_content9);
					break;
				}
				textView.setText(Html.fromHtml(res));
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
