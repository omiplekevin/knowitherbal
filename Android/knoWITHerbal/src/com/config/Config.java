package com.config;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;

import com.helper.DatabaseHelper;

public class Config {
	
	public static String plantTable = "plantTB";
	
	public static String imageTable = "imagesTB";
	
	public static String publishTable = "publishesTB";
	
	public static String externalDirectory = Environment.getExternalStorageDirectory() + "/.knoWITHerbal/";
	
	public static String hostURL = "http://192.168.180.1:9980/herbal/public/";
	
	public static String imagehostURL = hostURL + "herbals_photos/";
	
	public static String xmlhostURL = hostURL + "json/";
	
	public static String thumbsURL = "thumbs/";
	
	public static String publicURL = "http://192.168.180.1:9980/knoWITHerbal/";
	
	public static String plantXML = "plants.xml";
	
	public static String imageXML = "images.xml";
	
	public static String publishXML = "publishes.xml";
	
	public static String viewsetting = "ViewSetting";
	
	public static Typeface fontFace(Context context)
	{
		Typeface font = Typeface.createFromAsset(context.getAssets(), "MicroFLF.ttf");
		return font;
	}
	
	public static String dbPath(Context context)
	{
		String path;
		path = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/" + DatabaseHelper.dBName;
		return path;
	}
	
	public static float thumbnailscaleFactor = 0.5f;
	
	public static float viewPagerscaleFactor = 0.4f;
	
	public static float viewPagerscaleFactor_large = 0.85f;
	
	/*************************************************************
	 * XML TAGS START
	 * ***********************************************************/
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
	
	
	//publish
	public static String KEY_COMMENT = "comment";
	
	public static String KEY_PUBLISHITEM = "publish";
	
	//common
	public static String KEY_CREATEDAT = "created_at";
	
	public static String KEY_UPDATEDAT = "updated_at";
	
	/*************************************************************
	 * XML TAGS END
	 * ***********************************************************/
	
	public static String[] prerequisites = {"OpenCV_2.4.7.1_Manager_2.15_armeabi.apk",
											"OpenCV_2.4.7.1_binary_pack_armv7a.apk"};
	
}
