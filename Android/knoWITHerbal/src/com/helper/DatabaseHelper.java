package com.helper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	public final static String dBName = "Herbals";
	final static String plantTable = "plantTB";
	final static String imagesTable = "imagesTB";
	final static int DB_VERSION = 1;
	
	static Activity activity;
	
	public DatabaseHelper(Activity activity) {
		super(activity.getApplicationContext(), dBName, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + plantTable + " ("
	    		+ "pID INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ "name VARCHAR(1000), "
	    		+ "scientific_name VARCHAR(1000), "
	    		+ "common_names VARCHAR(1000), "
	    		+ "vernacular_names VARCHAR(1000), "
	    		+ "properties VARCHAR(1000), "
	    		+ "usage VARCHAR(1000), "
	    		+ "availability VARCHAR(1000), "
	    		+ "imgID INTEGER "
	    		+ ");");
		
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + imagesTable + " ("
	    		+ "imgID INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ "pID INTEGER, "
	    		+ "url VARCHAR(1000) "
	    		+ ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + plantTable);
		db.execSQL("DROP TABLE IF EXISTS " + imagesTable);
	}
}
