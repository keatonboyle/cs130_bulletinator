package com.example.bulletinator.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.bulletinator.MainActivity;
import com.example.bulletinator.R;
import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<Building> buildings;
    private Set<String> curBldgs;
    private MainActivity mainActivity;
    private int tab, childHeight;

    public ExpandableListAdapter(Context c, List<Building> bldgs, int t) {
        buildings = bldgs;
        curBldgs = new HashSet<String>();
        mainActivity = (MainActivity) c;
        tab = t;
        resolveListHeight();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return buildings.get(groupPosition).getBulletins().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return buildings.get(groupPosition).getBulletins().get(childPosition)
                .getBulletinId();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // Create image and text combo for a bulletin
        Bulletin bulletin = buildings.get(groupPosition).getBulletins()
                .get(childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bulletin_preview, null);
        }
        // TODO: Truncate body text if no description

        // Create views for bulletin preview with image and description
        TextView textView = (TextView) convertView.findViewById(R.id.bulletinPreview);
        textView.setText(bulletin.getDescription());

        Drawable image = mainActivity.getResources().getDrawable(bulletin.getIconId());
        image.setBounds(0, 0, childHeight, childHeight);
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
        mainActivity.setCurExpandedBldgs(tab, curBldgs);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        curBldgs.add(buildings.get(groupPosition).getName());
        mainActivity.setCurExpandedBldgs(tab, curBldgs);
    }

    private void resolveListHeight() {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new android.util.DisplayMetrics();
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mainActivity.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, value, true);
        childHeight = TypedValue.complexToDimensionPixelSize(value.data, metrics);
    }
}
