package com.example.bulletinator.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bulletinator.MainActivity;
import com.example.bulletinator.R;
import com.example.bulletinator.data.AppData;
import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<Building> buildings;
    private Set<String> curBldgs;
    private MainActivity mainActivity;
    private int tab;

    public ExpandableListAdapter(Context c, List<Building> bldgs, int t) {
        buildings = bldgs;
        curBldgs = new HashSet<String>();
        mainActivity = (MainActivity) c;
        tab = t;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return AppData.getBulletinsIn(((int) getGroupId(groupPosition))).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return AppData.getBulletinsIn(((int) getGroupId(groupPosition))).get(childPosition).getBulletinId();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // Create image and text combo for a bulletin
        Bulletin bulletin = AppData.getBulletinsIn(((int) getGroupId(groupPosition))).get(childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bulletin_preview, null);
        }

        // Create views for bulletin preview with image, title, and description
        TextView textView = (TextView) convertView.findViewById(R.id.bulletinPreview);
        TextView title = (TextView) convertView.findViewById(R.id.bulletinTitle);
        title.setText(bulletin.getTitle());
        textView.setText(bulletin.getDescription());
        title.setTypeface(null, Typeface.BOLD);

        Drawable image = mainActivity.getResources().getDrawable(bulletin.getIconId());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.bulletinIcon);
        imageView.setImageDrawable(image);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return AppData.getBulletinsIn(((int) getGroupId(groupPosition))).size();
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
        int id = buildings.get(groupPosition).getId();
        return (long) id;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Building bldg = buildings.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.building_header, null);
        }

        // Create building row views
        TextView textView = (TextView) convertView.findViewById(R.id.buildingName);
        textView.setText(bldg.getName());
        if (tab > 0)
            ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMargins(60, 0, 0, 0);
        else
            ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMargins(10, 0, 0, 0);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        curBldgs.remove(buildings.get(groupPosition).getName());
        mainActivity.setCurExpandedBldgs(tab, curBldgs);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        curBldgs.add(buildings.get(groupPosition).getName());
        mainActivity.setCurExpandedBldgs(tab, curBldgs);
    }
}
