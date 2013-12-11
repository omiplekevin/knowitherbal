package com.models;

public class PublishModel {
	
	public int publishID;
	public String comment;
	public String created_at;
	public String updated_at;
	
	public String getComment()
	{
		return comment;
	}
	
	public String getCreatedAt()
	{
		return created_at;
	}
	
	public String getUpdatedAt()
	{
		return updated_at;
	}

}
