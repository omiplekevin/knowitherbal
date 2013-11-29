package com.helper;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;

import com.adapter.CustomGridAdapter;
import com.adapter.PlantListAdapter;
import com.models.PlantModel;

public class ListSearch {
																												  /* T  |  F */
	public static Object searchPlantList(Context context, ArrayList<PlantModel> plantList, CharSequence s, boolean listOrGrid)
	{
		ArrayList<PlantModel> newList = new ArrayList<PlantModel>();
		boolean justAdded = false;
		for(int i=0;i<plantList.size();i++)
		{
			justAdded = false;
			if((plantList.get(i).getName().toLowerCase(Locale.getDefault())).contains(s))
			{
				newList.add(plantList.get(i));
				justAdded = true;
			}
			if((searchInCommonName(plantList.get(i), s)) && !justAdded)
			{
				newList.add(plantList.get(i));
			}
		}
		if(listOrGrid)
			return new PlantListAdapter(context, newList);
		else
			return new CustomGridAdapter(context, newList);
	}
	
	private static boolean searchInCommonName(PlantModel item, CharSequence s)
	{
		String[] commonNames = item.getCommon().split("\\|\\|");
		for(int c=0;c<commonNames.length;c++)
		{
			if((commonNames[c].toLowerCase(Locale.getDefault())).contains(s)){
				return true;
			}
		}
		return false;
	}

}