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
	    		+ "name VARCHAR(255), "
	    		+ "scientific_name VARCHAR(255), "
	    		+ "common_names VARCHAR(255), "
	    		+ "vernacular_names VARCHAR(255), "
	    		+ "properties VARCHAR(255), "
	    		+ "usage VARCHAR(255), "
	    		+ "availability VARCHAR(255), "
	    		+ "imgID INTEGER "
	    		+ ");");
		
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + imagesTable + " ("
	    		+ "imgID INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ "pID INTEGER, "
	    		+ "url VARCHAR(255) "
	    		+ ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + plantTable);
		db.execSQL("DROP TABLE IF EXISTS " + imagesTable);
	}
}
