package com.fragments;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
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

public class Camera extends SherlockFragment{

	View view;
	Uri uri;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		callIntent();
	}

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
	    try
	    {
	        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);
	        imageView.setImageBitmap(bitmap);
	    }
	    catch (Exception e)
	    {
	        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
	        Log.d("TAG", "Failed to load", e);
	    }
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1337 && resultCode == Activity.RESULT_OK)
		{
			ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
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
