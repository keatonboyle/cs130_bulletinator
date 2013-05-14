package com.example.bulletinator;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private List<Building> buildings;
	private Context context;
	private List<String> curBldgs;
	// adapter must know which tab it is connected to...
	private MainActivity activity;
	private int tab;
	
	public ExpandableListAdapter(Context c, List<Building> bldgs, int t) {
		context = c;
		buildings = bldgs;
		curBldgs = new ArrayList<String>();
		activity = (MainActivity) context;
		tab = t;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return buildings.get(groupPosition).getBulletins().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// Create image and text combo for a bulletin
		Bulletin bulletin = buildings.get(groupPosition).getBulletins()
				.get(childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.bulletin_preview, null);
		}

		// Create views for bulletin preview with image and description
		TextView textView = (TextView) convertView
				.findViewById(R.id.bulletinPreview);
		textView.setText(bulletin.getDescription());

		Drawable image = activity.getResources().getDrawable(
				bulletin.getIconId());
		image.setBounds(0, 0, 120, 120);
		textView.setCompoundDrawables(image, null, null, null);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return buildings.get(groupPosition).getBulletins().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return buildings.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return buildings.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		Building bldg = buildings.get(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.building_header, null);
		}

		// Create building row views
		TextView textView = (TextView) convertView
				.findViewById(R.id.buildingName);
		textView.setText(bldg.getName());
		textView.setTextSize(30);
		if (tab > 0)
			((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMargins(60, 0, 0, 0);
		else
			((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMargins(10, 0, 0, 0);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	@Override
	public void onGroupCollapsed(int groupPosition) {
		curBldgs.remove(buildings.get(groupPosition).getName());
		activity.setCurBldgs(tab, curBldgs);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		curBldgs.add(buildings.get(groupPosition).getName());
		activity.setCurBldgs(tab, curBldgs);
	}
}
