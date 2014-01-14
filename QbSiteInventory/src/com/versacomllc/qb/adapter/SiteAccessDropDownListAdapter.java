package com.versacomllc.qb.adapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.versacomllc.qb.gateway.domain.CustomerSiteAccess;

public class SiteAccessDropDownListAdapter extends ArrayAdapter<CustomerSiteAccess> {

	private Context mContext;
	private int resourceId;
	private List<CustomerSiteAccess> items;

	public SiteAccessDropDownListAdapter(Context context, int resource, List<CustomerSiteAccess> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.resourceId = resource;
		this.items = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	      ViewHolder holder;
	      if (convertView == null) {
	    	  
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(resourceId, parent, false);

	        holder = new ViewHolder();

	        holder.name = (TextView) convertView.findViewById(android.R.id.text1);

	        convertView.setTag(holder);
	      }
	      else {
	        holder = (ViewHolder) convertView.getTag();
	      }
	      
	      CustomerSiteAccess item = items.get(position);
	      
	      final String description = item.getCustomer() + " -> "+ item.getSiteId();
	      holder.name.setText(description);


	      
		return convertView;
	}

	

	  static class ViewHolder {
	    TextView name;
	  }
	  
}
