package com.LMO.capstone;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.adapter.MenuListAdapter;
import com.config.Config;
import com.fragments.AboutThisApplication;
import com.fragments.Camera;
import com.fragments.HowToUseFragment;
import com.fragments.OpenSourceLicense;
import com.fragments.PlantList;
import com.fragments.Welcome;
import com.helper.AsyncTaskDatabaseLoader;
import com.helper.AsyncTaskImageDownload;
import com.helper.DatabaseHelper;
import com.helper.OpenCVManagerDownloader;
import com.helper.Queries;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KnoWITHerbalMain extends SherlockFragmentActivity{
	
	public DrawerLayout drawerLayout;
	private ListView drawerList;
	public LinearLayout drawer;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence title;
	private String[] navTitles;
	private long lastPress;
	/*private DatabaseHelper dbHelper;
	private SQLiteDatabase sqliteDB;*/

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		title = "";
		navTitles = getResources().getStringArray(R.array.navigationMenus);
		drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		drawerList = (ListView)findViewById(R.id.nav_list);
		drawer = (LinearLayout)findViewById(R.id.drawer_view);
		
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		drawerList.setAdapter(new MenuListAdapter(this, navTitles));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		drawerList.setSelector(R.drawable.listitem_selector);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00CC00")));
		getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_launcher_2));

		drawerToggle = new ActionBarDrawerToggle(
				this,
				drawerLayout, 
				R.drawable.ic_drawer, 
				R.string.drawer_open, 
				R.string.drawer_close){
			public void onDrawerClosed(View view){
				getSupportActionBar().setTitle(title);
				supportInvalidateOptionsMenu();
			}
			public void onDrawerOpened(View view){
				getSupportActionBar().setDisplayShowTitleEnabled(true);
				getSupportActionBar().setTitle("Menu");
				supportInvalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		
		//FIRST RUN OF THE APPLICATION============================================
		if (savedInstanceState == null) {
			FragmentTransaction fTransac = getSupportFragmentManager().beginTransaction();
            fTransac.replace(R.id.frame_content, new Welcome()).commit();
            
            if(!new File(Config.dbPath(getApplicationContext())).exists()){
            	HowToUseFragment howto = new HowToUseFragment();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.frame_content, howto);
				ft.addToBackStack("help");
				ft.commit();
            	PrepareFileForDatabase();//will run on thread
            }
            else
            {
            	File appDir = new File(Config.externalDirectory);
            	
            	if(!appDir.exists())
            		appDir.mkdir();
            	
            	File[] files = appDir.listFiles();
            	DatabaseHelper dbHelper = new DatabaseHelper(this);
            	SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase();
            	if(files.length == 0 || files.length < Queries.getImageEntryCount(sqliteDB, dbHelper))
            	{
            		PrepareFilesForImage();// will run on thread
            	}
            }
            
            OpenCVManagerDownloader openCVDownloader = new OpenCVManagerDownloader(this);
            openCVDownloader.execute();
            
          //FIRST RUN OF THE APPLICATION============================================
        }
		
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
			.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
	}
	
	//end of onCreate()
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		long currentTime = System.currentTimeMillis();
		FragmentManager fm = getSupportFragmentManager();
		if(fm.getBackStackEntryCount() == 0){
			if((currentTime - lastPress) > 3000)
			{
				Toast.makeText(this, "Press \'back\' again to exit", Toast.LENGTH_LONG).show();
				lastPress = currentTime;
			}else
			{
				super.onBackPressed();
			}
		}
		else
		{
			super.onBackPressed();
		}
		
	}
	
	public void PrepareFileForDatabase()
	{
		/*DatabaseHelper dbHelper;
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase sqlite;
		sqlite = dbHelper.getReadableDatabase();
		*/
		AsyncTaskDatabaseLoader dbLoader = new AsyncTaskDatabaseLoader(this);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			dbLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			dbLoader.execute();
		}
		/*sqlite.close();*/
	}
	
	public void PrepareFilesForImage()
	{
		//image download
		DatabaseHelper dbHelper;
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase sqlite;
		sqlite = dbHelper.getReadableDatabase();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(KnoWITHerbalMain.this);
		AlertDialog dialog = builder.create();
		
		if(isNetworkAvailable())
		{
			ArrayList<String> urls = Queries.getImageURLS(sqlite, dbHelper, -1);
			ArrayList<String> forDL = new ArrayList<String>();
			for(int i=0;i<urls.size();i++)
			{
				Log.e("imageURL",urls.get(i));
				forDL.add(urls.get(i));
			}
			AsyncTaskImageDownload imageDownload = new AsyncTaskImageDownload(this, forDL, Queries.getImageEntryCount(sqlite, dbHelper));
			
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
				imageDownload.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
			else{
				imageDownload.execute();
			}
		}
		else
		{
			dialog.setTitle("Error");
			dialog.setMessage("No network connectivity. Connect to available networks and try again");
			dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(1);
				}
			});
			dialog.show();
		}
	}
	
	private boolean isNetworkAvailable()
	{
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
		{
			Log.i("isNetworkAvailable", "Available!");
			return true;
		}
		Log.i("isNetworkAvailable", "Not Available!");
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		deleteTempFiles(new File(Environment.getExternalStorageDirectory() + "/.temp"));
		System.exit(0);
	}
	
	private void deleteTempFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles())
            	deleteTempFiles(child);
        }

        fileOrDirectory.delete();
    }

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	      @Override
	      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	         selectItem(position);
	      }
	   }
	
	public void selectItem(int position)
	{
		
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		switch(position)
		{
			case 0:
				this.title = navTitles[0];
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.frame_content, new Camera()).commit();
				break;
			case 1:
				this.title = navTitles[1];
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.frame_content, new PlantList()).commit();
				break;
			case 2:
				this.title = navTitles[2];
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.frame_content, new AboutThisApplication()).commit();
				break;
			case 3:
				this.title = navTitles[3];
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.frame_content, new OpenSourceLicense()).commit();
				break;
		}
		
		drawerLayout.closeDrawer(drawer);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = !drawerLayout.isDrawerOpen(drawer);
		return drawerOpen;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getSupportMenuInflater().inflate(R.menu.kno_witherbal_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if(drawerToggle.onOptionsItemSelected(getMenuItem(item)))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------OVERRIDE FOR getMenuItem(MenuItem)-------------------------------
	private android.view.MenuItem getMenuItem(final MenuItem item) {
	      return new android.view.MenuItem() {
	         @Override
	         public int getItemId() {
	            return item.getItemId();
	         }

	         public boolean isEnabled() {
	            return true;
	         }

	         @Override
	         public boolean collapseActionView() {
	            return false;
	         }

	         @Override
	         public boolean expandActionView() {
	            return false;
	         }

	         @Override
	         public ActionProvider getActionProvider() {
	            return null;
	         }

	         @Override
	         public View getActionView() {
	            return null;
	         }

	         @Override
	         public char getAlphabeticShortcut() {
	            return 0;
	         }

	         @Override
	         public int getGroupId() {
	            return 0;
	         }

	         @Override
	         public Drawable getIcon() {
	            return null;
	         }

	         @Override
	         public Intent getIntent() {
	            return null;
	         }

	         @Override
	         public ContextMenuInfo getMenuInfo() {
	            return null;
	         }

	         @Override
	         public char getNumericShortcut() {
	            return 0;
	         }

	         @Override
	         public int getOrder() {
	            return 0;
	         }

	         @Override
	         public SubMenu getSubMenu() {
	            return null;
	         }

	         @Override
	         public CharSequence getTitle() {
	            return null;
	         }

	         @Override
	         public CharSequence getTitleCondensed() {
	            return null;
	         }

	         @Override
	         public boolean hasSubMenu() {
	            return false;
	         }

	         @Override
	         public boolean isActionViewExpanded() {
	            return false;
	         }

	         @Override
	         public boolean isCheckable() {
	            return false;
	         }

	         @Override
	         public boolean isChecked() {
	            return false;
	         }

	         @Override
	         public boolean isVisible() {
	            return false;
	         }

	         @Override
	         public android.view.MenuItem setActionProvider(ActionProvider actionProvider) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setActionView(View view) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setActionView(int resId) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setCheckable(boolean checkable) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setChecked(boolean checked) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setEnabled(boolean enabled) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIcon(Drawable icon) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIcon(int iconRes) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIntent(Intent intent) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setNumericShortcut(char numericChar) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setShortcut(char numericChar, char alphaChar) {
	            return null;
	         }

	         @Override
	         public void setShowAsAction(int actionEnum) {

	         }

	         @Override
	         public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitle(CharSequence title) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitle(int title) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitleCondensed(CharSequence title) {
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setVisible(boolean visible) {
	            return null;
	         }
	      };
	   }
}