package com.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
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
public class AsyncTaskImageDownload extends AsyncTask<Void, Void, Void>{
	
	ProgressDialog progressDialog;
	ArrayList<String> urls;
	int imageCount;
	Context context;
	Utilities util;
	
	
	public AsyncTaskImageDownload(Context context, ArrayList<String> urls, int imageCount)
	{
		progressDialog = new ProgressDialog(context);
		this.urls = urls;
		this.imageCount = imageCount;
		this.context = context;
		util = new Utilities(context);
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
		progressDialog.setMessage(Html.fromHtml("Downloading additional files..."));
		progressDialog.show();
		boolean dir = (new File(Config.externalDirectory)).mkdir();
		boolean thumbDir = (new File(Config.externalDirectory + ".thumbnail/")).mkdir();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		File dirFiles = new File(Config.externalDirectory);
		
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.toString().toLowerCase(Locale.getDefault()).contains(".jpg") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".png") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".bmp") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".gif") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".jpeg");
			}
		};
		
		File[] fileList = dirFiles.listFiles(filter);
		for(int i=0;i<urls.size();i++)
		{
//			Log.i("URL", urls.get(i));
			int count;
			boolean found = false;
			String[] splitURL = urls.get(i).split("/");

			for(File file : fileList)
			{
				if(file.getName().equals(splitURL[1]))
				{ found = true; break; }
			}

			if(!found){
				try {
					URL url = new URL(Config.imagehostURL + splitURL[0]+"/"+splitURL[1]);
					URLConnection conexion = url.openConnection();
					conexion.connect();
	
//					int lenghtOfFile = conexion.getContentLength();
	//				Log.e("ANDROID_ASYNC", "Length of file: " + lenghtOfFile);
	
					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(Config.externalDirectory + splitURL[1]);
	
					byte data[] = new byte[1024];
	
	
						while ((count = input.read(data)) != -1) {
							output.write(data, 0, count);
						}
	
						output.flush();
						output.close();
						input.close();
					} catch (Exception e) {}
				
				/*FOR THUMBNAIL DIMENSION IMAGE*/
				try {
					URL url = new URL(Config.imagehostURL + splitURL[0]+ "/" + Config.thumbsURL + splitURL[1]);
					URLConnection conexion = url.openConnection();
					conexion.connect();
	
//					int lenghtOfFile = conexion.getContentLength();
	//				Log.e("ANDROID_ASYNC", "Length of file: " + lenghtOfFile);
	
					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(Config.externalDirectory + ".thumbnail/" + splitURL[1]);
	
					byte data[] = new byte[1024];
	
					while ((count = input.read(data)) != -1) {
							output.write(data, 0, count);
						}
	
						output.flush();
						output.close();
						input.close();
					} catch (Exception e) {}
			}
			publishProgress();
		}
		
		cleanDirectory(urls);
		return null;
	}

	/**
	 * 
	 */
	private void cleanDirectory(ArrayList<String> filenames) {
		// TODO Auto-generated method stub
		File dirFiles = new File(Config.externalDirectory);
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.toString().toLowerCase(Locale.getDefault()).contains(".jpg") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".png") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".bmp") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".gif") ||
				pathname.toString().toLowerCase(Locale.getDefault()).contains(".jpeg");
			}
		};
		File[] files = dirFiles.listFiles(filter);
		for(File singleFile: files)
		{
			boolean found = false;
			for(int i=0;i<filenames.size();i++)
			{
				String[] fname = filenames.get(i).split("/");
				if(fname[1].equals(singleFile.getName()))
				{
					found = true;
					break;
				}
			}
			if(!found)
				singleFile.delete();
		}
		
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
		util.OpenCVInstallCheck();
	}
}
