package com.fragments;

import java.io.File;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
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
import com.helper.OpenCVManagerDownloader;
import com.models.PlantModel;
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
public class Camera extends SherlockFragment{

	View view;
	Uri uri;
	private List<PlantModel> plants;
	ProgressDialog dialog;
	ImageButton imageButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new ProgressDialog(getSherlockActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.camera_fragment, null);
		imageButton = (ImageButton)view.findViewById(R.id.cameraBtn);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AsyncTask<Void, Void, Boolean> checkOCV = new AsyncTask<Void, Void, Boolean>()
				{
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						dialog.setMessage("Loading OpenCV...");
						dialog.setIndeterminate(true);
						dialog.setCancelable(false);
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
					}

					@Override
					protected Boolean doInBackground(Void... params) {
						// TODO Auto-generated method stub
						final PackageManager packageManager = getSherlockActivity().getPackageManager();
				        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
				        for(ApplicationInfo info: installedApps)
				        {
				        	if((info.loadLabel(packageManager).equals("OpenCV Manager")) || (info.packageName.equals("org.opencv.engine")))
				        		return true;
				        }
				        return false;
					}

					@Override
					protected void onPostExecute(Boolean result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						dialog.dismiss();
						final AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
					    final AlertDialog postDialog = builder.create();
						if(result)
						{
							if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_7, getSherlockActivity(), mOpenCVCallBack))
							{
							    Log.e("OCV INTERNAL ERROR!", "Cannot connect to OpenCV Manager");
							    postDialog.setTitle("Oops!");
							    postDialog.setMessage("Something went wrong internally...");
							    postDialog.show();
							    postDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										postDialog.dismiss();
									}
								});
							}
							else
							{
								callIntent();
							}
						}
						else
						{
							imageButton.setEnabled(false);
							postDialog.setTitle("Oops!");
							postDialog.setMessage(Html.fromHtml("It seems that you don't have OpenCV Manager installed...<br>" +
									"Do you want to download and install <b>OpenCV Manager?</b><br><br>"));
						    postDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									OpenCVManagerDownloader OCVDownload = new OpenCVManagerDownloader(getSherlockActivity());
									OCVDownload.execute();
									postDialog.dismiss();
									imageButton.setEnabled(true);
								}
							});
						    postDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Maybe later", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									final AlertDialog warning = builder.create();
									warning.setMessage("Camera feature will not be available.\nOpenCV Manager is required by this feature.");
									warning.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											warning.dismiss();
										}
									});
									
									warning.show();
								}
							});
						    postDialog.show();
						}
					}
				};
				checkOCV.execute();
			}
		});
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
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
        Log.e("URI", uri.getPath());
        String path = uri.getPath();
        ORB orb = new ORB();
        
        orb.setContext(this.getSherlockActivity());
        //===================================================================THE MADNESS!!!
        orb.analyze(path, this.getSherlockActivity(), getFragmentManager());
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1337 && resultCode == Activity.RESULT_OK)
		{
			ImageView imageView = (ImageView)view.findViewById(R.id.logo);
			grabImage(imageView);
		}
		else if(resultCode == Activity.RESULT_CANCELED)
		{
			/*FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.frame_content, new Welcome());
			ft.commitAllowingStateLoss();
			this.getSherlockActivity().getSupportActionBar().setTitle("");*/
		}
	}
	
}
