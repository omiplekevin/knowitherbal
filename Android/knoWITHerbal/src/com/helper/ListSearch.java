package com.helper;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.util.Log;

import com.adapter.CustomGridAdapter;
import com.adapter.PlantListAdapter;
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
public class ListSearch {
																												  /* T  |  F */
	public static Object searchPlantList(Context context, ArrayList<PlantModel> plantList, CharSequence s, boolean listOrGrid)
	{
		ArrayList<PlantModel> newList = new ArrayList<PlantModel>();
		boolean justAdded = false;
		for(int i=0;i<plantList.size();i++)
		{
			justAdded = false;
			if((plantList.get(i).getName().toUpperCase(Locale.getDefault())).contains(s.toString().toUpperCase(Locale.getDefault())))
			{
				newList.add(plantList.get(i));
				justAdded = true;
			}
			
			if((searchInCommonName(plantList.get(i), s)) && !justAdded)
			{
				newList.add(plantList.get(i));
				justAdded = true;
			}
			
			if((searchInUsageName(plantList.get(i), s)) && !justAdded)
			{
				newList.add(plantList.get(i));
			}
		}
		if(listOrGrid)
			return new PlantListAdapter(context, newList, false);
		else
			return new CustomGridAdapter(context, newList);
	}
	
	private static boolean searchInCommonName(PlantModel item, CharSequence s)
	{
		String[] commonNames = item.getCommon().split("\\|\\|");
		for(int c=0;c<commonNames.length;c++)
		{
			if((commonNames[c].toUpperCase(Locale.getDefault())).contains(s.toString().toUpperCase(Locale.getDefault()))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean searchInUsageName(PlantModel item, CharSequence s)
	{
		String[] usage = item.getUsage().split("\\|\\|");
		for(int c=0;c<usage.length;c++)
		{
			if((usage[c].toLowerCase(Locale.getDefault())).contains(s.toString().toUpperCase(Locale.getDefault()))){
				return true;
			}
		}
		return false;
	}

}
