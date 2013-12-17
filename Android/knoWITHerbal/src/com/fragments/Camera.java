package com.fragments;

import java.io.File;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.algorithm.ORB;
import com.models.PlantModel;

public class Camera extends SherlockFragment{

	View view;
	Uri uri;
	private List<PlantModel> plants;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try{
			 if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_7, this.getSherlockActivity(), mOpenCVCallBack))
		        {
		            Log.e("Load OpenCV", "Cannot connect to OpenCV Manager");
		            AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
		            AlertDialog dialog = builder.create();
		            dialog.setTitle("Needed File");
		            dialog.setMessage("The Application needs to download the OpenCVManager and its binaries from our online resource.\n" +
		            		"Proceed with download?");
		            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new Dialog.OnClickListener() {
						
		            	@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
		        }
			 else
			 {
				 callIntent();
			 }
		}
		catch(Exception e)
		{
			
		}
	}
	
	private BaseLoaderCallback  mOpenCVCallBack = new BaseLoaderCallback(this.getSherlockActivity()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("Load OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.camera_fragment, null);
		ImageButton imageButton = (ImageButton)view.findViewById(R.id.clear);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callIntent();
			}
		});
		return view;
	}
	
	public void callIntent()
	{
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File photo = null;
		try
		{
			photo = this.createTemporaryFile("picture",".jpg");
			Log.i("INFO", "photo is created at " + photo.toString());
			uri = Uri.fromFile(photo);
		}
		catch(Exception e)
		{
			Toast.makeText(getActivity(), "Failed to capture image", Toast.LENGTH_SHORT).show();
		}
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		startActivityForResult(cameraIntent, 1337);
	}
	
	private File createTemporaryFile(String part, String ext) throws Exception
	{
		File tempDir= Environment.getExternalStorageDirectory();
	    tempDir=new File(tempDir + "/.temp/");
	    if(!tempDir.exists())
	    {
	        tempDir.mkdir();
	    }
	    return File.createTempFile(part, ext, tempDir);
	}
	
	public void grabImage(ImageView imageView)
	{
		this.getActivity().getApplicationContext().getContentResolver().notifyChange(uri, null);
	    ContentResolver cr = this.getActivity().getApplicationContext().getContentResolver();
	    Bitmap bitmap;
	    /*try
	    {*/
	        /*try {*/
				//bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);
	        	/*bitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(uri.getPath()));
	        	Bitmap resized = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, false);
				imageView.setImageBitmap(resized);*/
		        Log.e("URI", uri.getPath());
		        String path = uri.getPath();
		        ORB orb = new ORB();
		        
		        orb.setContext(this.getSherlockActivity());
		        //===================================================================THE MADNESS!!!
		        orb.analyze(path, this.getSherlockActivity(), getFragmentManager());
		        
		        /*TextView name = (TextView)view.findViewById(R.id.textView1);
		        
		        SQLiteDatabase sqliteDB;
				DatabaseHelper dbHelper = new DatabaseHelper(this.getSherlockActivity());
				sqliteDB = dbHelper.getReadableDatabase();
				
				plants = Queries.getPlants(sqliteDB, dbHelper);
		        name.setText(plants.get(id).getName());*/
			/*} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    /*}*/
	    /*catch (Exception e)
	    {
	        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
	        Log.d("TAG", "Failed to load", e);
	    }*/
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1337 && resultCode == Activity.RESULT_OK)
		{
			ImageView imageView = (ImageView)view.findViewById(R.id.captured);
			grabImage(imageView);
		}
		else if(resultCode == Activity.RESULT_CANCELED)
		{
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.frame_content, new Welcome());
			ft.commitAllowingStateLoss();
			this.getSherlockActivity().getSupportActionBar().setTitle("");
		}
	}
	
	
	
	/*
	 * RUN ALGORITHMS HERE!!
	 * */
}
