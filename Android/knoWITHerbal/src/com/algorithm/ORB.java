package com.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
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
import android.view.WindowManager;

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
	/*private int BEST = 0;
	private int plantID = 0;*/
	private List<PlantModel> plants;
	public Context context;
	ProgressDialog progressDialog;
	List<ItemModel> finalMatches;
	
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
			
			finalMatches = new ArrayList<ItemModel>();
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
					
					SortMatches();
					
					ResultFragment resultFragment = new ResultFragment();
					resultFragment.setResult(pathCaptured, finalMatches);
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.camera_base, resultFragment);
					ft.addToBackStack("result fragment");
					ft.commit();
				}

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					int plantEntry = Queries.getPlantEntryCount(sqliteDB, dbHelper);
					progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
					progressDialog.setIndeterminate(false);
					progressDialog.setCancelable(false);
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
							Bitmap bitmapSD = getBitmap(Config.externalDirectory + plants.get(plant).imgUrls.get(image));
							Mat bmpCap = new Mat();
							Mat bmpSD = new Mat();
							Mat img_object = new Mat();
							Mat img_scene = new Mat();
							
							Utils.bitmapToMat(bitmapCaptured, bmpCap);
							Utils.bitmapToMat(bitmapSD, bmpSD);
							
							Imgproc.cvtColor(bmpCap, img_object, Imgproc.COLOR_RGB2GRAY);
							Imgproc.cvtColor(bmpSD, img_scene, Imgproc.COLOR_RGB2GRAY);
							

							FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);

							MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
							MatOfKeyPoint keypoints_scene  = new MatOfKeyPoint();

							detector.detect(img_object, keypoints_object);
							detector.detect(img_scene, keypoints_scene);

							DescriptorExtractor extractor = DescriptorExtractor.create(FeatureDetector.ORB);

							Mat descriptor_object = new Mat();
							Mat descriptor_scene = new Mat();

							extractor.compute(img_object, keypoints_object, descriptor_object);
							extractor.compute(img_scene, keypoints_scene, descriptor_scene);

							DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
							MatOfDMatch matches = new MatOfDMatch();
							
							if(descriptor_object.cols() == descriptor_object.cols()
									&& descriptor_object.type() == descriptor_scene.type()
									&& descriptor_object.cols() > 0
									&& descriptor_scene.cols() > 0)
								{
									matcher.match(descriptor_object, descriptor_scene, matches);
									List<DMatch> matchesList = matches.toList();
		
									Double max_dist = 0.0;
									Double min_dist = 200.0;
		
									for(int i = 0; i < descriptor_object.rows(); i++){
									    Double dist = (double) matchesList.get(i).distance;
									    if(dist < min_dist) min_dist = dist;
									    if(dist > max_dist) max_dist = dist;
									}
		
									LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
									MatOfDMatch gm = new MatOfDMatch();
		
									for(int i = 0; i < descriptor_object.rows(); i++){
									    if(matchesList.get(i).distance < 2*min_dist){
									        good_matches.addLast(matchesList.get(i));
									    }
									}
		
									gm.fromList(good_matches);
		
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
		
									Mat mask = new Mat();
									Calib3d.findHomography(obj, scene, 8, 10, mask);
									
									int maskVal = 0;
									for(int i=0;i<mask.rows();i++)
									{
										for(int j=0;j<mask.cols();j++)
										{
											if((mask.get(i, j)[0] == 1))
											{
												++maskVal;
											}
										}
									}
	
									Log.e("RESULT LOG", "["+ plant + ", " + image + "] FILENAME: " + filename + " MATCH = " + maskVal);
									ItemModel item = new ItemModel(plant, maskVal);
									
									finalMatches.add(item);
		//							LinkedList<Point> cornerList = new LinkedList<Point>();
		//							cornerList.add(new Point(0,0));
		//							cornerList.add(new Point(img_object.cols(),0));
		//							cornerList.add(new Point(img_object.cols(),img_object.rows()));
		//							cornerList.add(new Point(0,img_object.rows()));
		
		//							MatOfPoint obj_corners = new MatOfPoint();
		//							obj_corners.fromList(cornerList);
//									System.gc();
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
	
	private void SortMatches()
	{
		Collections.sort(finalMatches, new MatchComparator());
		Collections.reverse(finalMatches);
	}
	
	public class MatchIndexComparator implements Comparator<Integer>
	{
		final List<ItemModel> matches;
		public MatchIndexComparator(List<ItemModel> matches)
		{
			this.matches = matches;
		}
		
		@Override
		public int compare(Integer lhs, Integer rhs) {
			// TODO Auto-generated method stub
			int int1 = matches.get(lhs).match;
			int int2 = matches.get(rhs).match;
			return int1-int2;
		}		
	}
	
	public static class ItemModel implements Comparable<Integer>
	{
		public int plantID;
		public int match;
		
		public ItemModel(int plantID, int match)
		{
			this.plantID = plantID;
			this.match = match;
		}
		
		public int getMatch()
		{
			return match;
		}
		
		public int getX()
		{
			return plantID;
		}

		@Override
		public int compareTo(Integer arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	public static class MatchComparator implements Comparator<ItemModel>
	{
		@Override
		public int compare(ItemModel lhs, ItemModel rhs) {
			// TODO Auto-generated method stub
			return lhs.match > rhs.match ? -1 : lhs.match < rhs.match ? 1 : 0;
		}
		
	}

}
