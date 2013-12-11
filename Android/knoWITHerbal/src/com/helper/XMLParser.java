package com.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.config.Config;
import com.models.ImagesModel;
import com.models.PlantModel;
import com.models.PublishModel;

public class XMLParser {

	Context context;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	
	// constructor
	public XMLParser(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper((Activity) context);
	}
	
	public void grabXML(String host, String filename, boolean forTemporary)
	{
		try {
			if(isNetworkAvailable()){
				/*URL url = new URL(host+filename);
	            HttpURLConnection c = (HttpURLConnection) url.openConnection();
	            c.setRequestMethod("GET");
	            c.setDoOutput(true);
	            c.connect();
	
	            String PATH = Config.externalDirectory;
	            Log.e("LOG", "PATH: " + PATH);
	            File file = new File(PATH);
	            if(file.isDirectory())
	            {
	            	Log.e("Existing directory!", file.toString());
	            }
	            else
	            {
	            	file.mkdirs();
	            }
	            
	            Log.e("URL", host+filename);
	
	            String fileName = filename;
	
	            File outputFile = new File(file, fileName);
	            FileOutputStream fos = new FileOutputStream(outputFile);
	
	            InputStream is = c.getInputStream();
	
	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            while ((len1 = is.read(buffer)) != -1) {
	
	                fos.write(buffer, 0, len1);
	
	            }
	            fos.close();
	            is.close();*/
				File dir = new File (Config.externalDirectory);
		           if(dir.exists()==false) {
		                dir.mkdirs();
		           }

		           URL url = new URL(host + filename); //you can write here any link
		           File file;
		           if(!forTemporary){
		        	   file = new File(dir, filename);
		           }
		           else{
		        	   file = new File(dir, "temp_"+filename);
		           }

		           long startTime = System.currentTimeMillis();
		           Log.d("DownloadManager", "download begining");
		           Log.d("DownloadManager", "download url:" + url);
		           Log.d("DownloadManager", "downloaded file name:" + filename);

		           /* Open a connection to that URL. */
		           URLConnection ucon = url.openConnection();

		           /*
		            * Define InputStreams to read from the URLConnection.
		            */
		           InputStream is = ucon.getInputStream();
		           BufferedInputStream bis = new BufferedInputStream(is);

		           /*
		            * Read bytes to the Buffer until there is nothing more to read(-1).
		            */
		           ByteArrayBuffer baf = new ByteArrayBuffer(5000);
		           int current = 0;
		           while ((current = bis.read()) != -1) {
		              baf.append((byte) current);
		           }


		           /* Convert the Bytes read to a String. */
		           FileOutputStream fos = new FileOutputStream(file);
		           fos.write(baf.toByteArray());
		           fos.flush();
		           fos.close();
		           Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
			}
        }
	    catch (IOException e) {
            Log.e("LOG ERROR", "Error: " + e);
            Toast.makeText(context, "error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) 
	      context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public void readXML(String source) throws XmlPullParserException, IOException
	{
		dbHelper = new DatabaseHelper((Activity)context);
		
		File file = new File(Config.externalDirectory + source);
//		Log.e("FILE",file.getAbsolutePath());
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		
		parser.setInput(new FileReader(file));
		int eventType = parser.getEventType();
		
//****************************************************************************************
		if(Config.plantXML.equals(source)){
			String tagName = "";
			List<PlantModel> listModel = new ArrayList<PlantModel>();
			PlantModel model = null;
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_DOCUMENT){
					model = new PlantModel();
					model.name = "";
					model.availability = "";
					model.common_names = "";
					model.properties = "";
					model.scientific_name = "";
					model.usage = "";
					model.vernacular_names = "";
					
				}
				else if(eventType == XmlPullParser.END_DOCUMENT){
//					Log.e("End Document", "New Item");
				}
				else if(eventType == XmlPullParser.START_TAG){
//					Log.e("XmlPullParser", "Start Tag " + parser.getName());
					tagName = parser.getName();
					if(tagName.equals(Config.KEY_PLANTITEM))
					{
//						Log.e("NEW", "plant!");
						model = new PlantModel();
						model.name = "";
						model.availability = "";
						model.common_names = "";
						model.properties = "";
						model.scientific_name = "";
						model.usage = "";
						model.vernacular_names = "";
					}
				}
				else if(eventType == XmlPullParser.END_TAG){
					if(parser.getName().equals(Config.KEY_PLANTITEM))
						listModel.add(model);
					tagName = "";
				}
				else if(eventType == XmlPullParser.TEXT){
					if(tagName.equals(Config.KEY_NAME)){
						model.name = parser.getText();
//						Log.e("NAME", parser.getText());
					}
					else if(tagName.equals(Config.KEY_SCIENTIFIC)){
						model.scientific_name = parser.getText();
//						Log.e("SCI_NAME", model.getScientific());
					}
					else if(tagName.equals(Config.KEY_COMMON)){
						model.common_names = parser.getText();
//						Log.e("COMMON_NAMES", model.getCommon());
					}
					else if(tagName.equals(Config.KEY_VERNACULAR)){
						model.vernacular_names = parser.getText();
//						Log.e("VERNACULAR_NAMES", model.getVernacular());
					}
					else if(tagName.equals(Config.KEY_PROPERTIES)){
						model.properties = parser.getText();
//						Log.e("PROPERTIES", model.getProperties());
					}
					else if(tagName.equals(Config.KEY_USAGE)){
						model.usage = parser.getText();
//						Log.e("USAGE", model.getUsage());
					}
					else if(tagName.equals(Config.KEY_AVAILABILITY)){
						model.availability = parser.getText();
					}
				}
				eventType = parser.next();
				
			}
			for(PlantModel item : listModel)
			{
				Queries.InsertPlant(sqliteDB, dbHelper, item);
			}
			
		}
		
//****************************************************************************************
		else if(Config.imageXML.equals(source))
		{
			String tagName = "";
			List<ImagesModel> listModel = new ArrayList<ImagesModel>();
			ImagesModel model = null;
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_DOCUMENT){
					model = new ImagesModel();
					model.url = "";
				}
				else if(eventType == XmlPullParser.END_DOCUMENT){
					
				}
				else if(eventType == XmlPullParser.START_TAG){
//					Log.e("XmlPullParser", "Start Tag " + parser.getName());
					
					tagName = parser.getName();
					if(tagName.equals(Config.KEY_IMAGEITEM))
					{
						model = new ImagesModel();
						model.url = "";
					}
				}
				else if(eventType == XmlPullParser.END_TAG){
					if(parser.getName().equals(Config.KEY_IMAGEITEM))
						listModel.add(model);
					tagName = "";
				}
				else if(eventType == XmlPullParser.TEXT){
					if(tagName.equals(Config.KEY_PLANTID)){
						model.pID = Integer.parseInt(parser.getText());
					}
					else if(tagName.equals(Config.KEY_IMAGEURL)){
						model.url = parser.getText();
					}
				}
				eventType = parser.next();
				
			}
			for(ImagesModel item : listModel)
			{
				Queries.InsertImage(sqliteDB, dbHelper, item);
			}
		}
//****************************************************************************************
		else if(Config.publishXML.equals(source))
		{
			String tagName = "";
			PublishModel publish = new PublishModel();
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_DOCUMENT){
					publish = new PublishModel();
					publish.comment= "";
				}
				else if(eventType == XmlPullParser.END_DOCUMENT){
					
				}
				else if(eventType == XmlPullParser.START_TAG){
//					Log.e("XmlPullParser", "Start Tag " + parser.getName());
					
					tagName = parser.getName();
					if(tagName.equals(Config.KEY_PUBLISHITEM))
					{
						publish = new PublishModel();
						publish.comment = "";
					}
				}
				else if(eventType == XmlPullParser.END_TAG){
					tagName = "";
				}
				else if(eventType == XmlPullParser.TEXT){
					if(tagName.equals(Config.KEY_COMMENT)){
						Log.e("Comment", parser.getText());
						publish.comment = parser.getText();
					}
					else if(tagName.equals(Config.KEY_CREATEDAT)){
						Log.e("Created_At", parser.getText());
						publish.created_at = parser.getText();
					}
					else if(tagName.equals(Config.KEY_UPDATEDAT)){
						Log.e("Updated_At", parser.getText());
						publish.updated_at = parser.getText();
					}
				}
				eventType = parser.next();
			}
			Queries.InsertPublish(sqliteDB, dbHelper, publish);
		}
	}
	
	public PublishModel readTempPublish(String temp_source) throws XmlPullParserException, IOException
	{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		
		File file = new File(Config.externalDirectory + temp_source);
		
		parser.setInput(new FileReader(file));
		int eventType = parser.getEventType();
		String tagName = "";
		PublishModel publish = new PublishModel();
		while(eventType != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_DOCUMENT){
				publish = new PublishModel();
				publish.comment= "";
			}
			else if(eventType == XmlPullParser.END_DOCUMENT){
				
			}
			else if(eventType == XmlPullParser.START_TAG){
//				Log.e("XmlPullParser", "Start Tag " + parser.getName());
				
				tagName = parser.getName();
				if(tagName.equals(Config.KEY_PUBLISHITEM))
				{
					publish = new PublishModel();
					publish.comment = "";
				}
			}
			else if(eventType == XmlPullParser.END_TAG){
				tagName = "";
			}
			else if(eventType == XmlPullParser.TEXT){
				if(tagName.equals(Config.KEY_COMMENT)){
					Log.e("Comment", parser.getText());
					publish.comment = parser.getText();
				}
				else if(tagName.equals(Config.KEY_CREATEDAT)){
					Log.e("Created_At", parser.getText());
					publish.created_at = parser.getText();
				}
				else if(tagName.equals(Config.KEY_UPDATEDAT)){
					Log.e("Updated_At", parser.getText());
					publish.updated_at = parser.getText();
				}
			}
			eventType = parser.next();
		}
		
		return publish;
	}
}
