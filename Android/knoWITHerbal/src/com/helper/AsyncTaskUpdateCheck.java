package com.helper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.Toast;

import com.config.Config;
import com.models.PublishModel;

public class AsyncTaskUpdateCheck extends AsyncTask<Void, Void, Boolean>{

	private Context context;
	PublishModel pubInfo;
	PublishModel temp;
	SimpleDateFormat format;
	Date date, newDate;
	ProgressDialog pd;
	SQLiteDatabase sqliteDB;
	DatabaseHelper dbHelper;
	
	public AsyncTaskUpdateCheck(Context context)
	{
		this.context = context;
		pd = new ProgressDialog(context);
		pubInfo = new PublishModel();
		temp = new PublishModel();
		format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd.setIndeterminate(true);
		pd.setTitle("Updating");
		pd.setMessage("Please Wait...");
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub

		dbHelper = new DatabaseHelper((Activity)context);
		pubInfo = Queries.getPublishInfo(sqliteDB, dbHelper);
		
		try
		{
			//YYYY-MM-DD HH:MM:SS
			date = format.parse(pubInfo.getCreatedAt());
			
			XMLParser parser = new XMLParser(context);
			parser.grabXML(Config.xmlhostURL, Config.publishXML, true);
			temp = parser.readTempPublish("temp_"+Config.publishXML);
			newDate = format.parse(temp.getCreatedAt());
			if(date.compareTo(newDate) < 0)
			{
				/*Toast.makeText(getSherlockActivity(), "Now Updating...", Toast.LENGTH_LONG).show();
				Queries.truncateDatabase(sqliteDB, dbHelper, getSherlockActivity());
				AsyncTaskDatabaseLoader loader = new AsyncTaskDatabaseLoader(getSherlockActivity());
				loader.execute();*/
				return true;
			}
			else
			{
				/*AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				AlertDialog dialog = builder.create();
				
				dialog.setTitle("Update Version");
				dialog.setMessage(Html.fromHtml(
						"<b><i>You have the latest data...</b></i><br/><br/>" +
						"<b>Date created:</b> " + newDate.toString() + "<br/>" +
						"<b>Remarks:</b> " + pubInfo.getComment()));
				dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new Dialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				dialog.show();*/
				return false;
			}
		}
		catch(ParseException e)
		{
			Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result != null)
		{
			if(result)
			{
				Toast.makeText(context, "Now Updating...", Toast.LENGTH_LONG).show();
				Queries.truncateDatabase(sqliteDB, dbHelper, context);
				AsyncTaskDatabaseLoader loader = new AsyncTaskDatabaseLoader(context);
				loader.execute();
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				AlertDialog dialog = builder.create();
				
				dialog.setTitle("Update Version");
				dialog.setMessage(Html.fromHtml(
						"<b><i>You have the latest data...</b></i><br/><br/>" +
						"<b>Date created:</b> " + pubInfo.getCreatedAt() + "<br/><br/>" +
						"<b>Remarks:</b> " + pubInfo.getComment()));
				dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new Dialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		}
		else
		{
			Toast.makeText(context, "Connection timed out! Try again.", Toast.LENGTH_LONG).show();
		}
		pd.dismiss();
	}
	
}