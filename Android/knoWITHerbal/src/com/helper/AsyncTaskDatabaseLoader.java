package com.helper;

import java.io.IOException;
import java.util.ArrayList;

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
import android.util.Log;

import com.config.Config;
import com.utilities.Utilities;
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
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AsyncTaskDatabaseLoader extends AsyncTask<Void, Void, Void>{

	ProgressDialog pDialog;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	Utilities util;
	Context context;
	
	public AsyncTaskDatabaseLoader(Context context) {
		// TODO Auto-generated constructor stub
		pDialog = new ProgressDialog(context);
		this.dbHelper = new DatabaseHelper((Activity)context);
		this.context = context;
		util = new Utilities(context);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		downloadDB();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		util.PrepareFilesForImage();
		pDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog.setTitle("Updating");
		pDialog.setMessage("Please wait while we update your database...");
		
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
             
             // Read bytes to the Buffer until there is nothing more to read(-1).
              
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
		
		parser.grabXML(Config.xmlhostURL, Config.plantXML, false); //http://192.168.180.1:9980/herbal/public/json/plants.xml
		parser.grabXML(Config.xmlhostURL, Config.imageXML, false); //http://192.168.180.1:9980/herbal/public/json/images.xml
		parser.grabXML(Config.xmlhostURL, Config.publishXML, false); //http://192.168.180.1:9980/herbal/public/json/publishes.xml
		
		try {
			parser.readXML(Config.plantXML);
			parser.readXML(Config.imageXML);
			parser.readXML(Config.publishXML);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
