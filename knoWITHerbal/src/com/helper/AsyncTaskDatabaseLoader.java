package com.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.config.Config;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AsyncTaskDatabaseLoader extends AsyncTask<Void, Void, Void>{

	ProgressDialog pDialog;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	Context context;
	
	public AsyncTaskDatabaseLoader(Context context) {
		// TODO Auto-generated constructor stub
		pDialog = new ProgressDialog(context);
		this.dbHelper = new DatabaseHelper((Activity)context);
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		/*Dataloader.LoadPlants(sqliteDB,dbHelper);
		Dataloader.LoadImages(sqliteDB,dbHelper);*/
		downloadDB();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		downloadImages();
		pDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog.setMessage("Please wait while we load your database...");
		pDialog.show();
	}
	
	private void downloadDB()
	{
		/*try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy); 
             Log.d("downloadDB", "downloading database");
             URL url = new URL(Config.hostURL + "Herbals");
              Open a connection to that URL. 
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.setDoOutput(true);
             connection.connect();
             InputStream is = connection.getInputStream();
             BufferedInputStream bis = new BufferedInputStream(is);
             
              * Read bytes to the Buffer until there is nothing more to read(-1).
              
             ByteArrayBuffer baf = new ByteArrayBuffer(50);
             int current = 0;
             while ((current = bis.read()) != -1) {
                     baf.append((byte) current);
             }

              Convert the Bytes read to a String. 
             FileOutputStream fos = null;
             // Select storage location
             fos = this.getApplicationContext().openFileOutput("Herbals", Context.MODE_PRIVATE);
             String dir = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
             File dbDir = new File(dir);
             if(!dbDir.exists())
             {
            	 if(dbDir.mkdir())
            	 {
            		 Log.i("DB DIR CREATED!", "DB DIR CREATED!");
            	 }
             }
             FileOutputStream fos = new FileOutputStream(dir + "Herbals");

             fos.write(baf.toByteArray());
             fos.close();
             Log.d("downloadDB", "downloaded");
		     } 
		catch (IOException e) {
		             Log.e("downloadDB", "downloadDatabase Error: " , e);
		     }  
		catch (NullPointerException e) {
		             Log.e("downloadDB", "downloadDatabase Error: " , e);
		     } 
		catch (Exception e){
		             Log.e("downloadDB", "downloadDatabase Error: " , e);
		     }*/
		
		XMLParser parser = new XMLParser(context);
		
		parser.grabXML(Config.hostURL + Config.plantXML);
		//parser.grabXML(Config.hostURL + Config.imageXML);
		
		try {
			parser.readXML(Config.plantXML);
//			parser.readXML(Config.imageXML);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void downloadImages()
	{
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
				forDL.add(Config.hostURL + urls.get(i));
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
	
	private boolean isNetworkAvailable()
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
	
	

}
