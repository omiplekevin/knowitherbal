package com.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.config.Config;

public class AsyncTaskImageDownload extends AsyncTask<Void, Void, Void>{
	
	ProgressDialog progressDialog;
	ArrayList<String> urls;
	int imageCount;
	Context context;
	
	
	public AsyncTaskImageDownload(Context context, ArrayList<String> urls, int imageCount)
	{
		progressDialog = new ProgressDialog(context);
		this.urls = urls;
		this.imageCount = imageCount;
		this.context = context;
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressDialog.incrementProgressBy(1);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		/*progressDialog.setIndeterminate(true);
		progressDialog.setMessage("Preparing images...");
		progressDialog.show();*/
		progressDialog.setIndeterminate(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMax(imageCount);
		progressDialog.setTitle(Html.fromHtml("<b>This may take a while...</b>"));
		progressDialog.setMessage(Html.fromHtml("Downloading additional files for <b><i>knoWITHerbal</i></b>"));
		progressDialog.show();
		boolean dir = (new File(Config.externalDirectory)).mkdir();
		boolean thumbDir = (new File(Config.externalDirectory + ".thumbnail/")).mkdir();
		if(!dir)
		{
			Log.w("ASYNCTASK","directory not created!\nMaybe created already or Insufficient Storage!");
		}
		if(!thumbDir)
		{
			Log.w("ASYNCTASK", "thumbnail directory not created! expect \"laggy\" performance... =(");
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		for(int i=0;i<urls.size();i++)
		{
			Log.i("URL", urls.get(i));
			try {
				URL url = new URL(urls.get(i));
				Log.e("URL image", urls.get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 2;
                Bitmap myBitmap = BitmapFactory.decodeStream(input,null,option);
                int newH = (int)(myBitmap.getHeight() - myBitmap.getHeight()*Config.thumbnailscaleFactor);
                int newW = (int)(myBitmap.getWidth() - myBitmap.getWidth()*Config.thumbnailscaleFactor);
                Bitmap thumbnail = Bitmap.createScaledBitmap(myBitmap, newW, newH, true);
                
                String data1 = url.getFile();
                String[] filename = data1.split("/");
                
                String file = filename[filename.length-1];
                String thumbnail_file = "thumbnail_"+file;
                
                FileOutputStream stream = new FileOutputStream(Config.externalDirectory + file);
                FileOutputStream thumbnail_stream = new FileOutputStream(Config.externalDirectory + ".thumbnail/" + thumbnail_file);
                Log.i("THUMBNAIL", thumbnail_file);
                
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                ByteArrayOutputStream thumbnail_outstream = new ByteArrayOutputStream();
                
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, thumbnail_outstream);
                
                byte[] byteArray = outstream.toByteArray();
                byte[] thumbnail_byteArray = thumbnail_outstream.toByteArray();
                
                stream.write(byteArray);
                thumbnail_stream.write(thumbnail_byteArray);
                
                stream.close();
                thumbnail_stream.close();
                
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			publishProgress();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
	}

}