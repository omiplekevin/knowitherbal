package com.adapter;

import java.util.ArrayList;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.config.Config;
import com.models.PlantModel;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
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
public class CustomGridAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<PlantModel> items = new ArrayList<PlantModel>();
	int width;
	int height;
	
	public CustomGridAdapter(Context context, ArrayList<PlantModel> items)
	{
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new Holder();
			convertView = inflater.inflate(R.layout.plantlist_fragment_grid_item, null);
			
			final ImageView imageView = (ImageView)convertView.findViewById(R.id.logo);
			final TextView textView = (TextView)convertView.findViewById(R.id.title);
			
			holder.imageView = imageView;
			holder.textView = textView;
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder)convertView.getTag();
		}
			
			AsyncTask<Holder, Void, Bitmap> loadThumbnail = new AsyncTask<Holder, Void, Bitmap>()
			{
				
				ArrayList<String> imgURLs;
				Holder view;
				@Override
				protected Bitmap doInBackground(Holder... params) {
					// TODO Auto-generated method stub
					view = params[0];
					if(items.get(position).imgUrls.size() > 0){
						imgURLs = items.get(position).imgUrls;
						return BitmapFactory.decodeFile(Config.externalDirectory + ".thumbnail/" + imgURLs.get(0));
					}
					else
					{
						return BitmapFactory.decodeResource(context.getResources(), R.drawable.no_photos_yet);
					}
				}

				@Override
				protected void onPostExecute(Bitmap result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					view.imageView.setImageBitmap(result);
					AnimatorSet anim = new AnimatorSet();
					anim.playTogether(
							ObjectAnimator.ofFloat(view.imageView, "alpha", 0, 0.5f, 1));
					/*if(position%2 == 0){
						anim.playTogether(
								ObjectAnimator.ofFloat(view.imageView, "alpha", 0, 0.5f, 1),
								ObjectAnimator.ofFloat(view.imageView, "translationX", 50, 0));
					}
					else
					{
						anim.playTogether(
								ObjectAnimator.ofFloat(view.imageView, "alpha", 0, 0.5f, 1),
								ObjectAnimator.ofFloat(view.imageView, "translationX", -50, 0));
					}*/
					anim.setDuration(400);
					anim.start();
				}
				
			};
			
			loadThumbnail.execute(holder);
			
			holder.textView.setText(items.get(position).getName());
			if(Config.FUNMODE){
				AnimatorSet viewAnim = new AnimatorSet();
				viewAnim.playSequentially(
						/*ObjectAnimator.ofFloat(convertView, "translationY", 30,0),*/
						ObjectAnimator.ofFloat(convertView, "rotationY", 90,-30),
						ObjectAnimator.ofFloat(convertView, "rotationY", -30,10),
						ObjectAnimator.ofFloat(convertView, "rotationY", 10,0)
						);
				viewAnim.setDuration(300);
				viewAnim.start();
			}

		return convertView;
	}
	
	public class Holder
	{
		ImageView imageView;
		TextView textView;
	}

}
