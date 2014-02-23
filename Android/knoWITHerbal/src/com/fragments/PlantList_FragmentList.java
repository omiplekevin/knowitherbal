package com.fragments;

import java.util.ArrayList;
import java.util.Locale;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.PlantListAdapter;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.helper.DatabaseHelper;
import com.helper.ListSearch;
import com.helper.Queries;
import com.models.PlantModel;
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
public class PlantList_FragmentList extends SherlockFragment{

	private SQLiteDatabase sqliteDB;
	private DatabaseHelper dbHelper;
	private ArrayList<PlantModel> plantList = new ArrayList<PlantModel>();
	private StickyListHeadersListView listView;
	private EditText searchText;
	private PlantListAdapter adapter;
	private ImageButton clearBtn;
	Utilities util;
	
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		 TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.plantlist_fragment, null);
			
			listView = (StickyListHeadersListView)view.findViewById(R.id.plant_listView);
			searchText = (EditText)view.findViewById(R.id.editText1);
			clearBtn = (ImageButton)view.findViewById(R.id.clear);
		}
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getSherlockActivity().getSupportActionBar().setTitle("Plant List");
		instantiateView();
		util = new Utilities(getActivity());
		
		clearBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchText.setText("");
				util.hideKeyboard(searchText);
//				InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//				im.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
			}
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(view != null)
		{
			ViewGroup viewGroup = (ViewGroup)view.getParent();
			if(viewGroup != null)
			{
				viewGroup.removeAllViews();
			}
		}
	}
	
	private void instantiateView()
	{
		dbHelper = new DatabaseHelper(this.getActivity());
		plantList = Queries.getPlants(sqliteDB, dbHelper);
		
		adapter = new PlantListAdapter(getActivity(), plantList, false);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				util.hideKeyboard(searchText);
				DetailFragment details = new DetailFragment();
				details.setItem((PlantModel)listView.getItemAtPosition(position));
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
				ft.replace(R.id.list_frame, details);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		
		listView.setSelector(R.drawable.listitem_selector);
		listView.setAdapter(adapter);
		searchText.addTextChangedListener(textwatcher);
	}
	
	private TextWatcher textwatcher = new TextWatcher() {
		PlantListAdapter newAdapter;
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
			if(!searchText.getText().toString().equals(""))
			{
				newAdapter = (PlantListAdapter)ListSearch.searchPlantList(getActivity(), plantList, s.toString().toUpperCase(Locale.getDefault()), true);
				clearBtn.setVisibility(View.VISIBLE);
			}
			else
			{
				newAdapter = new PlantListAdapter(getActivity(), plantList, false);
				clearBtn.setVisibility(View.INVISIBLE);
			}
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			listView.setAdapter(newAdapter);
			listView.invalidateViews();
		}
	};
}
