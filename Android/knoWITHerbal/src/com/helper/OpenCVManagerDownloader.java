package com.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.sax.StartElementListener;

import com.config.Config;

public class OpenCVManagerDownloader extends AsyncTask<Void, Void, Void>{

	ProgressDialog pDialog;
	Context context;
	
	public OpenCVManagerDownloader(Context context)
	{
		this.pDialog = new ProgressDialog(context);
		this.context = context;
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog.setMessage("Downloading OpenCV Application...");
		pDialog.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		downloadOpenCV();
		return null;
	}

	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
//		installOpenCV();
		pDialog.dismiss();
	}
	
	private void downloadOpenCV()
	{
//		String preReq = Config.OCVpkg;
		try{
			String OCVUrl = "market://details?id=org.opencv.engine";
			Intent OCVDownload = new Intent(Intent.ACTION_VIEW);
			OCVDownload.setData(Uri.parse(OCVUrl));
			context.startActivity(OCVDownload);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*try {
			File dir = new File (Config.externalDirectory);
	           if(dir.exists()==false) {
	                dir.mkdirs();
	           }

	           URL url = new URL(Config.publicURL + preReq); //you can write here any link
	           File file;
        	   file = new File(dir, preReq);
        	   
        	   long startTime = System.currentTimeMillis();
	           Log.d("DownloadManager", "download begining");
	           Log.d("DownloadManager", "download url:" + url);
	           Log.d("DownloadManager", "downloaded file name:" + preReq[i]);

	            Open a connection to that URL. 
	           URLConnection ucon = url.openConnection();

	           
	            * Define InputStreams to read from the URLConnection.
	            
	           InputStream is = ucon.getInputStream();
	           BufferedInputStream bis = new BufferedInputStream(is);

	           
	            * Read bytes to the Buffer until there is nothing more to read(-1).
	            
	           ByteArrayBuffer baf = new ByteArrayBuffer(5000);
	           int current = 0;
	           while ((current = bis.read()) != -1) {
	              baf.append((byte) current);
	           }


	            Convert the Bytes read to a String. 
	           FileOutputStream fos = new FileOutputStream(file);
	           fos.write(baf.toByteArray());
	           fos.flush();
	           fos.close();
//		           Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
		     }
		
		catch (IOException e) {  }  
		catch (NullPointerException e) {  } 
		catch (Exception e){  }*/
	}
	
	private void installOpenCV()
	{
		String preReq = Config.OCVpkg;
		Intent promptInstall = new Intent(Intent.ACTION_VIEW)
	    .setDataAndType(Uri.parse("file://" + Config.externalDirectory + preReq), 
	                    "application/vnd.android.package-archive");
		promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.getApplicationContext().startActivity(promptInstall); 
	}

}
