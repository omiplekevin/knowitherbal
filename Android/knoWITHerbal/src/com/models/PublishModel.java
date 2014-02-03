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
public class PublishModel {
	
	public int publishID;
	public String comment;
	public String created_at;
	public String updated_at;
	
	public int getpublishID()
	{
		return publishID;
	}
	
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
