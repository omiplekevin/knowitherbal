package com.models;

import java.util.ArrayList;
import java.util.List;


public class PlantModel {
	
	public int pID;
	public String 	name;
	public String	scientific_name;
	public String	common_names;
	public String	vernacular_names;
	public String	properties;
	public String	usage;
	public String	availability;
	
	public ArrayList<String> imgUrls;
	
	public int getpID() { return pID; }
	public String getName() {	return name;	}
	public String getScientific(){	return scientific_name;   }
	public String getCommon()	{	return common_names;		}
	public String getVernacular(){  return vernacular_names;  }
	public String getProperties(){  return properties;  }
	public String getUsage(){  return usage;  }
	public String getAvailability(){  return availability;  }
	public List<String> getImgURLS(){  return imgUrls;  }
}