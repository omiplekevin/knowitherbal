package com.models;
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
import java.util.ArrayList;
import java.util.List;


public class PlantModel implements Comparable<Integer>{
	
	public int 					pID;
	public String 				name;
	public String				scientific_name;
	public String				common_names;
	public String				vernacular_names;
	public String				properties;
	public String				usage;
	public String				availability;
	
	public ArrayList<String> 	imgUrls;
	public int					rating;
	
	public int getpID() { return pID; }
	public String getName() {	return name;	}
	public String getScientific(){	return scientific_name;   }
	public String getCommon()	{	return common_names;		}
	public String getVernacular(){  return vernacular_names;  }
	public String getProperties(){  return properties;  }
	public String getUsage(){  return usage;  }
	public String getAvailability(){  return availability;  }
	public ArrayList<String> getImgURLS(){  return imgUrls;  }
	public int getRating() { return rating; }
	
	@Override
	public int compareTo(Integer another) {
		// TODO Auto-generated method stub
		return 0;
	}
}