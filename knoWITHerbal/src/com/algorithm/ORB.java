package com.algorithm;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.actionbarsherlock.app.SherlockFragment;
import com.fragments.Camera;
import com.helper.DatabaseHelper;
import com.helper.Queries;
import com.models.PlantModel;

public class ORB extends SherlockFragment{
	
	String pathCaptured;
	
	public int analyze(String imagePath)
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
			DatabaseHelper dbHelper = new DatabaseHelper(getSherlockActivity());
			sqliteDB = dbHelper.getReadableDatabase();
			List<PlantModel> plants = Queries.getPlants(sqliteDB, dbHelper);
			
			Bitmap bitmapCaptured = getBitmap(pathCaptured);
			Bitmap bitmapSD = getBitmap(plants.get(0).imgUrls.get(0));
			
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
			descExtract.compute(grayBmpCap, kp1, desc1);
			descExtract.compute(grayBmpSD, kp2, desc2);
			
			DescriptorMatcher descMatch = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			MatOfDMatch matches = new MatOfDMatch();
			descMatch.match(desc1, desc2, matches);
			double MAX = 0;
			double MIN = 0;
			
			for(int i=0;i<desc1.rows();i++)
			{
				
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
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options(); // Limit the filesize since 5MP pictures will kill you RAM
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
