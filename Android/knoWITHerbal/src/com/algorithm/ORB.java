package com.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragment;
import com.config.Config;
import com.fragments.Camera;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PlantModel;

public class ORB extends SherlockFragment{
	
	String pathCaptured;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Log.i("TAG", "Trying to load OpenCV library");
	    if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, getActivity(), mOpenCVCallBack))
	    {
	      Log.e("TAG", "Cannot connect to OpenCV Manager");
	    }
	    
	}

	private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(getActivity()) {
		
		@Override
		public void onManagerConnected(int status) {
		   switch (status) {
		       case LoaderCallbackInterface.SUCCESS:
		       {
		      Log.i("TAG", "OpenCV loaded successfully");
		       } break;
		       default:
		       {
		      super.onManagerConnected(status);
		       } break;
		   }
		    }
		};
	
	public int analyze(String imagePath, Context context)
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
			SQLiteDatabase sqliteDB;
			DatabaseHelper dbHelper = new DatabaseHelper((Activity)context);
			sqliteDB = dbHelper.getReadableDatabase();
			
			List<PlantModel> plants = Queries.getPlants(sqliteDB, dbHelper);
			Bitmap bitmapCaptured = getBitmap(pathCaptured);
			int BEST;
			
			for(int plant=0;plant<plants.size();plant++)
			{
				for(int image=0;image<plants.get(plant).imgUrls.size();image++)
				{
					String filename = plants.get(plant).imgUrls.get(image);
					Log.e("IMAGE PLANT", ""+plant + ", " +image + "FILENAME: " + filename);
					Bitmap bitmapSD = getBitmap(Config.externalDirectory + plants.get(plant).imgUrls.get(image));
					
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
						Log.e("BEST MATCHES [", matchFilterred.size() + "] COLS: [" + matchFilterred.cols() + "] ROWS: [" + matchFilterred.rows() + "]");
					}
				}
				
			}
		}
		
		return 0;
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
	
	

}
