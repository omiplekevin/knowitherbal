package com.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.config.Config;
import com.models.ImagesModel;
import com.models.PlantModel;

public class XMLParser {

	Context context;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	
	// constructor
	public XMLParser(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper((Activity) context);
	}
	
	public void grabXML(String host, String filename)
	{
		try {
			if(isNetworkAvailable()){
//	            URL url = new URL("http://labs.philippineglobaloutsourcing.com/xmlrepo/restaurants/app_version.xml");
				URL url = new URL(host+filename);
	            HttpURLConnection c = (HttpURLConnection) url.openConnection();
	            c.setRequestMethod("GET");
	            c.setDoOutput(true);
	            c.connect();
	
	            /*String PATH = Environment.getExternalStorageDirectory()
	                    + "/PGOLabs/";*/
	            String PATH = Config.externalDirectory;
	            Log.e("LOG", "PATH: " + PATH);
	            File file = new File(PATH);
	            if(file.isDirectory())
	            {
	            	Log.e("Existing directory!", file.toString());
	            	//Toast.makeText(context.getApplicationContext(), "Directory \""+PATH+"\" already exist!", Toast.LENGTH_LONG).show();
	            }
	            else
	            {
	            	//Toast.makeText(context.getApplicationContext(), "Directory \""+PATH+"\" created!  ", Toast.LENGTH_LONG).show();
	            	file.mkdirs();
	            }
	            
//	            Toast.makeText(context.getApplicationContext(), URLString, Toast.LENGTH_LONG).show();
	            Log.e("URL", host+filename);
	
	            String fileName = filename;
	
	            File outputFile = new File(file, fileName);
	            FileOutputStream fos = new FileOutputStream(outputFile);
	
	            InputStream is = c.getInputStream();
	
	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            while ((len1 = is.read(buffer)) != -1) {
	
	            	//Log.e("buffer", buffer.toString());
	            	//Log.e("len1", ""+len1);
	                fos.write(buffer, 0, len1);
	
	            }
	            //Log.e("file size", ""+outputFile.getTotalSpace());
	            fos.close();
	            is.close();
	            
	
	            // }
			}
        }
	    catch (IOException e) {
            Log.e("LOG ERROR", "Error: " + e);
            Toast.makeText(context.getApplicationContext(), "error " + e.toString(), Toast.LENGTH_LONG)
                    .show();

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
		
		String tagName = "";
//****************************************************************************************
		if(Config.plantXML.equals(source)){
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
	}
}
