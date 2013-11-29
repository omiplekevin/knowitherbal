package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.config.Config;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.models.PlantModel;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class PlantListAdapter extends ArrayAdapter<PlantModel> implements StickyListHeadersAdapter{

	Context context;
	ArrayList<PlantModel> plantList;
	LayoutInflater inflater;
	
	public PlantListAdapter(Context context, ArrayList<PlantModel> items)
	{
		super(context,0, items);
		this.context = context;
		this.plantList = items;
		inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null)
		{
			holder = new Holder();
			convertView = inflater.inflate(R.layout.plantlist_item, null);
			holder.plantName = (TextView)convertView.findViewById(R.id.plantName);
			holder.plantPrimeDetail = (TextView)convertView.findViewById(R.id.version);
			holder.imageView = (ImageView)convertView.findViewById(R.id.plantImageView);
			holder.pb1 = (ProgressBar)convertView.findViewById(R.id.progressBar1);
			holder.pb2 = (ProgressBar)convertView.findViewById(R.id.progressBar2);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder)convertView.getTag();
		}
		
		final ArrayList<String> imgURLs = plantList.get(position).imgUrls;
		final int pos = position;
		AsyncTask<Holder, Void, Bitmap> loadDetail = new AsyncTask<Holder, Void, Bitmap>() {
		    private Holder v;

		    @Override
		    protected Bitmap doInBackground(Holder... params) {
		        v = params[0];
		        return BitmapFactory.decodeFile(Config.externalDirectory + ".thumbnail/thumbnail_" + imgURLs.get(0));
		    }

		    @Override
		    protected void onPostExecute(Bitmap result) {
		        super.onPostExecute(result);
		        v.pb1.setVisibility(View.GONE);
		        v.pb2.setVisibility(View.GONE);
		        v.plantName.setText(plantList.get(pos).getName());
		        String clean = plantList.get(pos).getScientific().replace("||", "\n");
				v.plantPrimeDetail.setText(clean);
				v.plantPrimeDetail.setSelected(true);
		        v.imageView.setImageBitmap(result);
		        
		        AnimatorSet anim = new AnimatorSet();
				anim.playTogether(
						ObjectAnimator.ofFloat(v.imageView, "alpha", 0, 0.5f, 1),
						ObjectAnimator.ofFloat(v.imageView, "translationY", 30, 0));
				anim.setDuration(300);
				anim.start();
		    }
		};
		
		loadDetail.execute(holder);
		
		/*holder.plantName.setText(plantList.get(position).getName());
		String clean = plantList.get(position).getScientific().replace("||", ", ");
		holder.plantPrimeDetail.setText(Html.fromHtml(clean));
		holder.plantPrimeDetail.setSelected(true);
		ArrayList<String> imgURLs = plantList.get(position).imgUrls;
		Log.i("IMAGE", imgURLs.get(0));
		Bitmap thumbnail = BitmapFactory.decodeFile(Config.externalDirectory + ".thumbnail/thumbnail_" + imgURLs.get(0));
		holder.imageView.setImageBitmap(thumbnail);
		*/
//		Log.i("plantList", plantList.get(position).getName());
		return convertView;
	}
	
	
	
	static class Holder
	{
		TextView plantName;
		TextView plantPrimeDetail;
		ImageView imageView;
		ProgressBar pb1;
		ProgressBar pb2;
	}
	
	
	
	//=============================STICKY=HEADERS===========================================
	
	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if(convertView == null)
		{
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.plantlist_stickyheader, null);
			holder.stickyText = (TextView)convertView.findViewById(R.id.stickheaderText);
			convertView.setTag(holder);
		}
		else
		{
			holder = (HeaderViewHolder)convertView.getTag();
		}
		
		String headerChar = ""+plantList.get(position).getName().subSequence(0, 1).charAt(0);
		holder.stickyText.setText(headerChar);
		
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return plantList.get(position).getName().subSequence(0, 1).charAt(0);
	}
	
	class HeaderViewHolder
	{
		TextView stickyText;
	}

}