package com.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.config.Config;
import com.fragments.Camera;
import com.fragments.ResultFragment;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PlantModel;

public class ORB extends SherlockFragment{
	
	String pathCaptured;
	private int BEST = 0;
	private int plantID = 0;
	private List<PlantModel> plants;
	public Context context;
	ProgressDialog progressDialog;
	ArrayList<ItemModel> matchRating;
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void analyze(String imagePath, final Context context, final FragmentManager fragmentManager)
	{
		pathCaptured = imagePath;
		if(!new File(pathCaptured).isFile())
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			AlertDialog dialog = builder.create();
			
			dialog.setTitle("Error!");
			dialog.setMessage("An error occured while reading " + pathCaptured + ".");
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Camera camera = new Camera();
					camera.callIntent();
				}
			});
			
			dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dismiss", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			dialog.show();
		}
		else
		{
			matchRating = new ArrayList<ItemModel>();
			progressDialog = new ProgressDialog(context);
			final SQLiteDatabase sqliteDB;
			final DatabaseHelper dbHelper = new DatabaseHelper((Activity)context);
			sqliteDB = dbHelper.getReadableDatabase();
			
			plants = Queries.getPlants(sqliteDB, dbHelper);
			final Bitmap bitmapCaptured = getBitmap(pathCaptured);
			
			AsyncTask<Void, Void, Void> algorithm = new AsyncTask<Void, Void, Void>()
			{
				
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					progressDialog.dismiss();
					
					SortMatches(matchRating);
					
					ResultFragment resultFragment = new ResultFragment();
					resultFragment.setResult(pathCaptured, matchRating);
					
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.FrameLayout1, resultFragment);
					ft.addToBackStack("results");
					ft.commit();
					
				}

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					int plantEntry = Queries.getPlantEntryCount(sqliteDB, dbHelper);
					progressDialog.setIndeterminate(true);
					progressDialog.setIndeterminate(false);
					progressDialog.setCanceledOnTouchOutside(false);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setMax(plantEntry);
					progressDialog.setTitle(Html.fromHtml("<b>This may take a while...</b>"));
					progressDialog.setMessage(Html.fromHtml("<i>Analyzing</i>"));
					progressDialog.show();
				}

				@Override
				protected void onProgressUpdate(Void... values) {
					// TODO Auto-generated method stub
					super.onProgressUpdate(values);
					progressDialog.incrementProgressBy(1);
				}

				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					for(int plant=0;plant<plants.size();plant++)
					{
						for(int image=0;image<plants.get(plant).imgUrls.size();image++)
						{
							String filename = plants.get(plant).imgUrls.get(image);
							Log.e("IMAGE PLANT", ""+plant + ", " +image + " FILENAME: " + filename);
							Bitmap bitmapSD = getBitmap(Config.externalDirectory + plants.get(plant).imgUrls.get(image));
//							Bitmap bitmapSD = getBitmap(Config.externalDirectory + plants.get(plant).imgUrls.get(image));
							
							Mat bmpCap = new Mat();
							Mat bmpSD = new Mat();
							Mat grayBmpCap = new Mat();
							Mat grayBmpSD = new Mat();
							
							Utils.bitmapToMat(bitmapCaptured, bmpCap);
							Utils.bitmapToMat(bitmapSD, bmpSD);
							
							Imgproc.cvtColor(bmpCap, grayBmpCap, Imgproc.COLOR_RGB2GRAY);
							Imgproc.cvtColor(bmpSD, grayBmpSD, Imgproc.COLOR_RGB2GRAY);
							
							FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);
							
							MatOfKeyPoint kp1 = new MatOfKeyPoint();
							MatOfKeyPoint kp2 = new MatOfKeyPoint();
							
							fd.detect(grayBmpCap, kp1);
							fd.detect(grayBmpSD, kp2);
							
							DescriptorExtractor descExtract = DescriptorExtractor.create(DescriptorExtractor.BRIEF);
							Mat desc1 = new Mat();
							Mat desc2 = new Mat();
							
							MatOfDMatch matches = new MatOfDMatch();
							
							descExtract.compute(grayBmpCap, kp1, desc1);
							descExtract.compute(grayBmpSD, kp2, desc2);
							
							MatOfDMatch matchFilterred = new MatOfDMatch();
							
							DescriptorMatcher descMatch = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
				//			List<MatOfDMatch> listMatches = new ArrayList<MatOfDMatch>();
							
							if(desc1.cols() == desc1.cols() && desc1.type() == desc2.type() && desc1.cols() > 0 && desc2.cols() > 0)
							{
								descMatch.match(desc1, desc2, matches);
								List<DMatch> matchesList = matches.toList();
								List<DMatch> bestMatches = new ArrayList<DMatch>();
								
								double MAX = 0;
								double MIN = 100;
								
								for( int i = 0; i < matchesList.size(); i++ )
								{
									double dist = matchesList.get(i).distance;
									if(dist < MIN && dist != 0)
									{
										MIN = dist;
									}
									if(dist > MAX)
									{
										MAX = dist;
									}
								}
								
								double thresh = 2 * MIN;
								
								for(int i=0;i<matchesList.size();i++)
								{
									double dist = (double)matchesList.get(i).distance;
									if(dist < thresh)
									{
										bestMatches.add(matches.toList().get(i));
									}
								}
								
								matchFilterred.fromList(bestMatches);
								Log.e("BEST MATCHES ", matchFilterred.size() + " COLS: [" + matchFilterred.cols() + "] ROWS: [" + matchFilterred.rows() + "]");
								if(matchFilterred.rows() > BEST){
									plantID = plant;
									BEST = matchFilterred.rows();
								}
								ItemModel item = new ItemModel();
								item.x = plant;
								item.y = image;
								item.match = matchFilterred.rows();
								matchRating.add(item);
							}
						}//child for-loop
						publishProgress();
					}
					return null;
				}//parent for-loop
			};
			algorithm.execute();
		}
	}
	
	/*private Bitmap convertToARGB8888(String path)
	{
		Bitmap bmp;
		bmp = getBitmap(Config.externalDirectory + plants.get(0).imgUrls.get(0)); 
		int MAX_DIM = 300;
		int w, h;
		if (bmp.getWidth() >= bmp.getHeight())
		{
			w = MAX_DIM;
			h = bmp.getHeight()*MAX_DIM/bmp.getWidth();
		} 
		else
		{
			h = MAX_DIM;
			w = bmp.getWidth()*MAX_DIM/bmp.getHeight();
		} 
		bmp = Bitmap.createScaledBitmap(bmp, w, h, false);
		Bitmap img1 = bmp.copy(Bitmap.Config.ARGB_8888, false);
		
		return img1;
	}*/
	
	private Bitmap getBitmap(String path)
	{
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options(); // Limit the filesize since 5MP pictures will kill your RAM
		bitmapOptions.inSampleSize = 1;
		Bitmap bmp = BitmapFactory.decodeFile(path, bitmapOptions);
		
		//convert into ARGB_8888
		int MAX_DIM = 300;
		int w, h;
		if (bmp.getWidth() >= bmp.getHeight())
		{
			w = MAX_DIM;
			h = bmp.getHeight()*MAX_DIM/bmp.getWidth();
		} 
		else
		{
			h = MAX_DIM;
			w = bmp.getWidth()*MAX_DIM/bmp.getHeight();
		} 
		bmp = Bitmap.createScaledBitmap(bmp, w, h, false);
		Bitmap img1 = bmp.copy(Bitmap.Config.ARGB_8888, false);
		
		
		return img1;
	}
	
	private void displayBEST()
	{
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.camera_fragment, null);
		TextView plantName = (TextView)view.findViewById(R.id.textView1);
		plantName.setText(plants.get(plantID).getName());
		plantName.invalidate();
	}
	
	private void SortMatches(ArrayList<ItemModel> items)
	{
		for(int i=0;i<items.size()-1;i++)
		{
			for(int x=i; x<items.size()-1-i;x++)
			{
				ItemModel lhs = items.get(x);
				ItemModel rhs = items.get(x+1);
				if(rhs.getMatch() > lhs.getMatch())
				{
					items.set(x+1, lhs);
					items.set(x, rhs);
				}
			}
		}
	}
	
	public class ItemModel
	{
		public int x;
		public int y;
		public int match;
		
		public int getMatch()
		{
			return match;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
	}

}
