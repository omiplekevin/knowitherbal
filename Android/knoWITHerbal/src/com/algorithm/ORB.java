package com.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
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
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
					ft.replace(R.id.camera_base, resultFragment);
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
							Mat img_object = new Mat();
							Mat img_scene = new Mat();
							
							Utils.bitmapToMat(bitmapCaptured, bmpCap);
							Utils.bitmapToMat(bitmapSD, bmpSD);
							
							Imgproc.cvtColor(bmpCap, img_object, Imgproc.COLOR_RGB2GRAY);
							Imgproc.cvtColor(bmpSD, img_scene, Imgproc.COLOR_RGB2GRAY);

							FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB); //4 = SURF 

							MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
							MatOfKeyPoint keypoints_scene  = new MatOfKeyPoint();

							detector.detect(img_object, keypoints_object);
							detector.detect(img_scene, keypoints_scene);

							DescriptorExtractor extractor = DescriptorExtractor.create(FeatureDetector.ORB); //2 = SURF;

							Mat descriptor_object = new Mat();
							Mat descriptor_scene = new Mat() ;

							extractor.compute(img_object, keypoints_object, descriptor_object);
							extractor.compute(img_scene, keypoints_scene, descriptor_scene);

							DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING); // 1 = FLANNBASED
							MatOfDMatch matches = new MatOfDMatch();

							matcher.match(descriptor_object, descriptor_scene, matches);
							List<DMatch> matchesList = matches.toList();

							Double max_dist = 0.0;
							Double min_dist = 100.0;

							for(int i = 0; i < descriptor_object.rows(); i++){
							    Double dist = (double) matchesList.get(i).distance;
							    if(dist < min_dist) min_dist = dist;
							    if(dist > max_dist) max_dist = dist;
							}

							System.out.println("-- Max dist : " + max_dist);
							System.out.println("-- Min dist : " + min_dist);    

							LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
							MatOfDMatch gm = new MatOfDMatch();

							for(int i = 0; i < descriptor_object.rows(); i++){
							    if(matchesList.get(i).distance < 3*min_dist){
							        good_matches.addLast(matchesList.get(i));
							    }
							}

							gm.fromList(good_matches);

							Mat img_matches = new Mat();
							Features2d.drawMatches(
							        img_object,
							        keypoints_object, 
							        img_scene,
							        keypoints_scene, 
							        gm, 
							        img_matches, 
							        new Scalar(255,0,0), 
							        new Scalar(0,0,255), 
							        new MatOfByte(), 
							        2);

							LinkedList<Point> objList = new LinkedList<Point>();
							LinkedList<Point> sceneList = new LinkedList<Point>();

							List<KeyPoint> keypoints_objectList = keypoints_object.toList();
							List<KeyPoint> keypoints_sceneList = keypoints_scene.toList();

							for(int i = 0; i<good_matches.size(); i++){
							    objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
							    sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
							}

							MatOfPoint2f obj = new MatOfPoint2f();
							obj.fromList(objList);

							MatOfPoint2f scene = new MatOfPoint2f();
							scene.fromList(sceneList);

							Mat H = Calib3d.findHomography(obj, scene);

							LinkedList<Point> cornerList = new LinkedList<Point>();
							cornerList.add(new Point(0,0));
							cornerList.add(new Point(img_object.cols(),0));
							cornerList.add(new Point(img_object.cols(),img_object.rows()));
							cornerList.add(new Point(0,img_object.rows()));

							MatOfPoint obj_corners = new MatOfPoint();
							obj_corners.fromList(cornerList);

//							MatOfPoint scene_corners = new MatOfPoint();

							//ERROR HERE :
							//OpenCV Error: Assertion failed (scn + 1 == m.cols && (depth == CV_32F || depth == CV_64F)) in unknown function, file ..\..\..\src\opencv\modules\core\src\matmul.cpp, line 1926
//							Core.perspectiveTransform(obj_corners, scene_corners, H);

							//Draw the lines... later, when the homography will work
							/*
							Core.line(img_matches, new Point(), new Point(), new Scalar(0,255,0), 4);
							Core.line(img_matches, new Point(), new Point(), new Scalar(0,255,0), 4);
							Core.line(img_matches, new Point(), new Point(), new Scalar(0,255,0), 4);
							Core.line(img_matches, new Point(), new Point(), new Scalar(0,255,0), 4);
							*/

							//Sauvegarde du résultat
							System.out.println(String.format("Writing %s", pathCaptured));
//							Highgui.imwrite(pathCaptured, img_matches);

							
							
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
	
	private void SortMatches(ArrayList<ItemModel> items)
	{
		/*for(int i=0;i<items.size()-1;i++)
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
		}*/
		
		List<Integer> matches = new ArrayList<Integer>();
		for(int i=0;i<items.size();i++)
		{
			matches.add(items.get(i).getX());
		}
		
		Collections.sort(matches);
		Collections.reverse(matches);
		
		for(int n : matches)
			Log.e("ITEM[SORTED]", ""+n);
		
	}
	
	public class ItemModel
	{
		public int x;
		public int match;
		
		public int getMatch()
		{
			return match;
		}
		
		public int getX()
		{
			return x;
		}
	}

}
