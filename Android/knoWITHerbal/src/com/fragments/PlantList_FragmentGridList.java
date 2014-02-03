package com.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.LMO.capstone.R;
import com.actionbarsherlock.app.SherlockFragment;
import com.adapter.CustomGridAdapter;
import com.helper.DatabaseHelper;
import com.helper.ListSearch;
import com.helper.Queries;
import com.models.PlantModel;
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
public class PlantList_FragmentGridList extends SherlockFragment{

	View view;
	private SQLiteDatabase sqliteDB;
	private DatabaseHelper dbHelper;
	private ArrayList<PlantModel> plantList = new ArrayList<PlantModel>();
	private CustomGridAdapter adapter;
	private ImageButton clearBtn;
	EditText searchText;
	GridView gridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null)
		{
			view = inflater.inflate(R.layout.plantlist_fragment_grid,null);
			instantiateView();
		}
		return view;
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

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		clearBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchText.setText("");
				InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
			}
		});
	}
	
	private void instantiateView()
	{
		dbHelper = new DatabaseHelper(this.getActivity());
		plantList = Queries.getPlants(sqliteDB, dbHelper);
		
		gridView = (GridView)view.findViewById(R.id.grid_view);
		searchText = (EditText)view.findViewById(R.id.searchString);
		clearBtn = (ImageButton)view.findViewById(R.id.grid_clear);
		searchText.addTextChangedListener(textWatcher);
		adapter = new CustomGridAdapter(getActivity(), plantList);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				DetailFragment details = new DetailFragment();
				details.setItem((PlantModel)gridView.getItemAtPosition(position));
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
				ft.addToBackStack(null);
				ft.replace(R.id.grid_frame, details);
				ft.commit();
				/*Toast.makeText(getSherlockActivity(), "OnClick is temporarily disabled", Toast.LENGTH_SHORT).show();*/
			}
		});
	}
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			CustomGridAdapter newAdapter;
			if(!searchText.getText().toString().equals(""))
			{
				newAdapter = (CustomGridAdapter)ListSearch.searchPlantList(getActivity(), plantList, s, false);
				clearBtn.setVisibility(View.VISIBLE);
			}
			else
			{
				newAdapter = new CustomGridAdapter(getActivity(), plantList);
				clearBtn.setVisibility(View.INVISIBLE);
			}
			gridView.setAdapter(newAdapter);
			gridView.invalidateViews();
		}

		@Override
		public void afterTextChanged(Editable s) { }

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) { }
		};
}