package com.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.LMO.capstone.R;
import com.config.Config;
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
public class DetailViewPagerAdapter extends PagerAdapter{

	Context context;
	ArrayList<String> imgURL;
	
	public DetailViewPagerAdapter(Context context, ArrayList<String> imgURLS)
	{
		this.context = context;
		this.imgURL = imgURLS;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(imgURL.size() == 0)
			return 1;
		else
			return imgURL.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		final ImageView imageView = new ImageView(context);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		
		AsyncTask<Void, Void, Bitmap> loadImage = new AsyncTask<Void, Void, Bitmap>()
		{

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(imgURL.size() == 0)
					imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.no_photos_yet_big));
				else
					imageView.setImageBitmap(result);
			}

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try
				{
					return BitmapFactory.decodeFile(Config.externalDirectory + imgURL.get(position));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
		};
		
		loadImage.execute();
		
		if(imgURL.size() != 0){
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openImage(position);
				}
			});
		}
		
		((ViewPager)container).addView(imageView,0);
		return imageView;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void openImage(int position)
	{
		String bmp = Config.externalDirectory + imgURL.get(position);
		/*Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + bmp), "image/*");
		context.startActivity(intent);*/
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		AlertDialog dialog = builder.create();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.web_view, null);
		dialog.setView(view);
		WebView webView = (WebView)view.findViewById(R.id.webView1);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadUrl("file://" + bmp);
		
		dialog.show();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		((ViewPager)container).removeView((View)view);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View)arg1);
	}
	
	

}
