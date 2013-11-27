package com.config;

import com.helper.DatabaseHelper;

import android.content.Context;
import android.os.Environment;

public class Config {
	
	public static String externalDirectory = Environment.getExternalStorageDirectory() + "/.knoWITHerbal/";
	
	public static String hostURL = "http://omiplekevin:9980/knoWITHerbal/";
	
	public static String plantXML = "plants.xml";
	
	public static String imageXML = "images.xml";
	
	public static String viewsetting = "ViewSetting";
	
	public static String dbPath(Context context)
	{
		String path;
		path = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/" + DatabaseHelper.dBName;
		return path;
	}
	
	public static float thumbnailscaleFactor = 0.70f;
	
	public static float viewPagerscaleFactor = 0.4f;
	
	public static float viewPagerscaleFactor_large = 0.85f;
	
	/*************************************************************
	 * XML TAGS
	 * ************************************************************/
	//plants
	public static String KEY_PLANTITEM = "plant"; 
	
	public static String KEY_ID = "id";

	public static String KEY_NAME = "name";
	
	public static String KEY_SCIENTIFIC = "scientific_names";
	
	public static String KEY_COMMON = "common_names";
	
	public static String KEY_VERNACULAR = "vernacular_names";
	
	public static String KEY_PROPERTIES = "properties";
	
	public static String KEY_USAGE = "usage";
	
	public static String KEY_FILENAME = "filename";
	
	public static String KEY_AVAILABILITY = "availability";
	
	
	//images
	public static String KEY_IMAGEITEM = "image";
	
	public static String KEY_PLANTID = "plant_id";
	
	public static String KEY_IMAGEURL = "url";
	
	
	//common
	public static String KEY_CREATEDAT = "created_at";
	
	public static String KEY_UPDATEDAT = "updated_at";
	
}
