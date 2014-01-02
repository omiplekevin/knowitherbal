package com.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.config.Config;
import com.models.ImagesModel;
import com.models.PlantModel;
import com.models.PublishModel;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Queries {

	//======================================READ==================================
	public static ArrayList<PlantModel> getPlants(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		ArrayList<PlantModel> models = new ArrayList<PlantModel>();
		PlantModel pModel;
		List<String> url;
		
		sqliteDB = dbHelper.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.plantTable.toString(), null); 
		
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast())
		{
			do
			{
				pModel = new PlantModel();
				url = new ArrayList<String>();
				
				pModel.pID = mCursor.getInt(0);
				pModel.name = mCursor.getString(1);
				pModel.scientific_name = mCursor.getString(2);
				pModel.common_names = mCursor.getString(3);
				pModel.vernacular_names  = mCursor.getString(4);
				pModel.properties = mCursor.getString(5);
				pModel.usage = mCursor.getString(6);
				pModel.availability = mCursor.getString(7);
				pModel.imgUrls = getImageFilename(sqliteDB, dbHelper, pModel.pID);
				
				models.add(pModel);
				
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		Collections.sort(models, new Comparator<PlantModel>(){
			@Override
			public int compare(PlantModel lhs, PlantModel rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});
		
		return models;
	}
	
	public static ArrayList<String> getImageURLS(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, int idReference)
	{
		ArrayList<String> urls = new ArrayList<String>();
		Cursor mCursor = null;
		
		sqliteDB = dbHelper.getReadableDatabase();
		if(idReference != -1){
			mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.imagesTable.toString()+" WHERE pID = " + idReference, null);
		}
		else
		{
			mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.imagesTable.toString(), null);
		}
		
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) 
		{
			do
			{
				urls.add(mCursor.getString(1) + "/" + mCursor.getString(2));//URL
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		return urls;
	}
	
	public static ArrayList<String> getImageFilename(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, int idReference)
	{
		ArrayList<String> urls = new ArrayList<String>();
		Cursor mCursor = null;
		
		sqliteDB = dbHelper.getReadableDatabase();
		if(idReference != -1){
			mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.imagesTable.toString()+" WHERE pID = " + idReference, null);
		}
		else
		{
			mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.imagesTable.toString(), null);
		}
		
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) 
		{
			do
			{
				urls.add(mCursor.getString(2));//URL
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		return urls;
	}
	
	public static ArrayList<ImagesModel> getAllImages(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		ArrayList<ImagesModel> models = new ArrayList<ImagesModel>();
		ImagesModel iModel;
		
		sqliteDB = dbHelper.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM "+DatabaseHelper.imagesTable.toString(), null); 
		
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) 
		{
			do
			{
				iModel = new ImagesModel();
				
				iModel.imgID = mCursor.getInt(0);
				iModel.pID = mCursor.getInt(1);
				iModel.url = mCursor.getString(2);
				
				models.add(iModel);
				
			} while (mCursor.moveToNext());
		}
			
		mCursor.close();
		dbHelper.close();
		
		return models;
	}
	
	public static PublishModel getPublishInfo(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		Cursor mCursor = null;
		
		sqliteDB = dbHelper.getReadableDatabase();
		mCursor = sqliteDB.rawQuery("SELECT * FROM " + DatabaseHelper.publishTable.toString() + " ORDER BY publishID DESC LIMIT 1", null);
		mCursor.moveToFirst();
		
		PublishModel model = new PublishModel();
		model.publishID = mCursor.getInt(0);
		model.comment = mCursor.getString(1);
		model.created_at = mCursor.getString(2);
		model.updated_at = mCursor.getString(3);
			
		mCursor.close();
		dbHelper.close();
		
		return model;
	}
	
	public static int getPlantEntryCount(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		sqliteDB = dbHelper.getReadableDatabase();
		Cursor cursor = sqliteDB.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.plantTable, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		sqliteDB.close();
		return count;
	}
	
	public static int getImageEntryCount(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper)
	{
		sqliteDB = dbHelper.getReadableDatabase();
		Cursor cursor = sqliteDB.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.imagesTable, null);
		cursor.moveToFirst();
		sqliteDB.close();
		
		return cursor.getInt(0);
	}
	
	//====================================INSERT==================================
	public static void InsertPlant(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, PlantModel plant)
	{
		sqliteDB  = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("name", plant.getName());
		values.put("scientific_name", plant.getScientific());
		values.put("common_names", plant.getCommon());
		values.put("vernacular_names", plant.getVernacular());
		values.put("properties", plant.getProperties());
		values.put("usage", plant.getUsage());
		values.put("availability", plant.getAvailability());
		
		sqliteDB.insert(DatabaseHelper.plantTable, null, values);
		sqliteDB.close();
	}
	
	public static void InsertImage(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, ImagesModel images)
	{
		sqliteDB  = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("pID", images.getpID());
		values.put("url", images.getUrl());
		
		sqliteDB.insert(DatabaseHelper.imagesTable, null, values);
		sqliteDB.close();
	}
	
	public static void InsertPublish(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, PublishModel publish)
	{
		Log.e("INSERTING", "####################COMMENT####################");
		sqliteDB  = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("comment", publish.getComment());
		values.put("created_at", publish.getCreatedAt());
		values.put("updated_at", publish.getUpdatedAt());
		
		Log.e("INSERTING",publish.getComment());
		
		sqliteDB.insert(DatabaseHelper.publishTable, null, values);
		sqliteDB.close();
	}
	
	//====================================UPDATE==================================
	public static void UpdatePlant(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, PlantModel plant)
	{
		sqliteDB = dbHelper.getWritableDatabase();
		
		ContentValues newVal = new ContentValues();
		newVal.put("name", "PLANT");
		newVal.put("scientific_name", "SCIENTIFIC");
		newVal.put("common_names", "COMMON");
		newVal.put("vernacular_names", "VERNACULAR");
		newVal.put("properties", "PROPERTIES");
		newVal.put("usage", "USAGE");
		newVal.put("availability", "AVAILABLE");
		
		sqliteDB.update(DatabaseHelper.plantTable, newVal, "pID = " + plant.pID, null);
		sqliteDB.close();
	}
	
	public static void UpdateImage(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, ImagesModel model)
	{
		sqliteDB = dbHelper.getWritableDatabase();
		ContentValues newVal = new ContentValues();
		newVal.put("pID", "2");
		newVal.put("url", "temporary");
		
		sqliteDB.update(DatabaseHelper.imagesTable, newVal, "imgID = " + model.imgID, null);
		sqliteDB.close();
	}
	
	//====================================DELETE==================================
	public static boolean DeletePlant(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, int id)
	{
		int rt = 0;
		sqliteDB = dbHelper.getWritableDatabase();
		rt = sqliteDB.delete(DatabaseHelper.plantTable, "pID = " + id, null);
		sqliteDB.close();
		if(rt==1)
		{
			return true;
		}
		else
		{
			return false;			
		}
	}
	
	public static boolean DeleteImage(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, int id)
	{
		int rt = 0;
		sqliteDB = dbHelper.getWritableDatabase();
		rt = sqliteDB.delete(DatabaseHelper.imagesTable, "imgID = " + id, null);
		sqliteDB.close();
		if(rt==1)
		{
			return true;
		}
		else
		{
			return false;			
		}
	}
	
	//==================================TRUNCATE==================================
	public static void truncateDatabase(SQLiteDatabase sqliteDB, DatabaseHelper dbHelper, Context context)
	{
		sqliteDB = dbHelper.getWritableDatabase();
		sqliteDB.execSQL("DELETE FROM " + Config.plantTable);
		sqliteDB.execSQL("DELETE FROM " + Config.imageTable);
		sqliteDB.execSQL("DELETE FROM sqlite_sequence WHERE name='" + Config.plantTable + "'");
		sqliteDB.execSQL("DELETE FROM sqlite_sequence WHERE name='" + Config.imageTable + "'");
	}
}
