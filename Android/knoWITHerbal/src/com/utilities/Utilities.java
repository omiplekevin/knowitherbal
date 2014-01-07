package com.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.helper.AsyncTaskDatabaseLoader;
import com.helper.AsyncTaskImageDownload;
import com.helper.DatabaseHelper;
import com.helper.OpenCVManagerDownloader;
import com.helper.Queries;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Utilities {
	
	Context context;
	
	public Utilities(Context context)
	{
		this.context = context;
	}

	public void PrepareFileForDatabase()
	{
		/*DatabaseHelper dbHelper;
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase sqlite;
		sqlite = dbHelper.getReadableDatabase();
		*/
		AsyncTaskDatabaseLoader dbLoader = new AsyncTaskDatabaseLoader(context);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			dbLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			dbLoader.execute();
		}
		/*sqlite.close();*/
	}
	
	public void PrepareFilesForImage()
	{
		//image download
		DatabaseHelper dbHelper;
		dbHelper = new DatabaseHelper((Activity)context);
		SQLiteDatabase sqlite;
		sqlite = dbHelper.getReadableDatabase();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		AlertDialog dialog = builder.create();
		
		if(isNetworkAvailable())
		{
			ArrayList<String> urls = Queries.getImageURLS(sqlite, dbHelper, -1);
			ArrayList<String> forDL = new ArrayList<String>();
			for(int i=0;i<urls.size();i++)
			{
				Log.e("imageURL",urls.get(i));
				forDL.add(urls.get(i));
			}
			AsyncTaskImageDownload imageDownload = new AsyncTaskImageDownload(context, forDL, Queries.getImageEntryCount(sqlite, dbHelper));
			
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
				imageDownload.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
			else{
				imageDownload.execute();
			}
		}
		else
		{
			dialog.setTitle("Error");
			dialog.setMessage("No network connectivity. Connect to available networks and try again");
			dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(1);
				}
			});
			dialog.show();
		}
	}
	
	public boolean isNetworkAvailable()
	{
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			Log.i("isNetworkAvailable", "Available!");
			return true;
		}
		Log.i("isNetworkAvailable", "Not Available!");
		return false;
	}
	
	public void deleteTempFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles())
            	deleteTempFiles(child);
        }

        fileOrDirectory.delete();
    }
	
	public boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	public void OpenCVInstallCheck()
	{
		AsyncTask<Void, Void, Boolean> checkOCV = new AsyncTask<Void, Void, Boolean>()
		{
			ProgressDialog pDialog;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pDialog = new ProgressDialog(context);
				pDialog.setMessage("A moment please while we prepare your OpenCV Manager...");
				pDialog.setIndeterminate(true);
				pDialog.setCancelable(false);
				pDialog.setCanceledOnTouchOutside(false);
				pDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				final PackageManager packageManager = context.getPackageManager();
		        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
		        for(ApplicationInfo info: installedApps)
		        {
		        	if((info.loadLabel(packageManager).equals("OpenCV Manager")) || (info.packageName.equals("org.opencv.engine")))
		        	{
		        		Log.e("APPLICATIONS", "PKG: " + info.packageName);
			        	Log.e("APPLICATIONS", "NAME: " + info.loadLabel(packageManager));
		        		return true;
		        	}
		        }
		        return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				pDialog.dismiss();				
				
				if(!result)
				{
					final AlertDialog.Builder builder = new AlertDialog.Builder(context);
					AlertDialog dialog = builder.create();
					dialog.setTitle("Oops!");
					dialog.setMessage(Html.fromHtml("It seems that you don't have OpenCV Manager installed...<br>" +
							"Do you want to download and install <b>OpenCV Manager?</b><br><br>"));
					dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							OpenCVManagerDownloader OCVDownload = new OpenCVManagerDownloader(context);
							OCVDownload.execute();
							dialog.dismiss();
						}
					});
					
					dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Maybe later", new DialogInterface.OnClickListener() {
						
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
					dialog.show();
				}
				else
				{
					Toast.makeText(context, "You're good to go!", Toast.LENGTH_LONG).show();
				}
			}
			
		};
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			checkOCV.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			checkOCV.execute();
		}
	}
}
