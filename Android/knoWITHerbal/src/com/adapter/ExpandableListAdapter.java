package com.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.LMO.capstone.R;
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
public class ExpandableListAdapter extends BaseExpandableListAdapter{

	private Context context;
	private List<String> exlistHeaders;
	private HashMap<String, List<String>> exlistItems;
	
	public ExpandableListAdapter(Context context, List<String> listHeader,
            HashMap<String, List<String>> listItems) {
        this.context = context;
        this.exlistHeaders = listHeader;
        this.exlistItems = listItems;
    }
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this.exlistItems.get(this.exlistHeaders.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		final String text = (String)getChild(groupPosition, childPosition);
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.expandable_list_item, null);
		}
		
		TextView textView = (TextView)convertView.findViewById(R.id.exListItem);
		textView.setText(text);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int position) {
		// TODO Auto-generated method stub
		return this.exlistItems.get(this.exlistHeaders.get(position)).size();
	}

	@Override
	public Object getGroup(int position) {
		// TODO Auto-generated method stub
		return this.exlistHeaders.get(position);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.exlistHeaders.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String headerTitle = (String)getGroup(groupPosition);
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.expandable_list_group, null);
		}
		
		TextView textView = (TextView)convertView.findViewById(R.id.listHeader);
		textView.setText(headerTitle);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
