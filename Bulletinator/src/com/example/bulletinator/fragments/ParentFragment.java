package com.example.bulletinator.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.example.bulletinator.*;
import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;
import com.example.bulletinator.helpers.ExpandableListAdapter;

public abstract class ParentFragment extends Fragment {
    private List<Building> buildings;
    private ExpandableListView expandableListView;
    protected MainActivity mainActivity;
    private ExpandableListAdapter adapter;

    public abstract int getTab();

    public abstract int[] getPos();

    public abstract void setPos(int index, int top);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        // Inflate current buildings layout
        View view = inflater.inflate(R.layout.list_view, container, false);

        // Populate bulletins that MainActivity retrieved
        if (getTab() != 3)
            buildings = mainActivity.getBuildings();
        else {
            List<Bulletin> bulletins = mainActivity.getArchivedBulletins();
            Building bldg = new Building("Archived", bulletins);
            buildings = new ArrayList<Building>();
            buildings.add(bldg);
        }

        // Populate list with bulletins
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        adapter = new ExpandableListAdapter(
                mainActivity, buildings, getTab());

        // Set on click listener for viewing bulletins
        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // Start new bulletin activity
                Bulletin b = buildings.get(groupPosition).getBulletins()
                        .get(childPosition);
                mainActivity.selectBulletin(b);
                // TODO: delete on click of an image that appears when you swipe?
                /*adapter.removeChild(groupPosition, childPosition);
                adapter.notifyDataSetChanged();
                mainActivity.delete(b.getBulletinId());*/

                mainActivity.archiveBulletin(b);
                return true;
            }
        });
        expandableListView.setAdapter(adapter);

        if (getTab() == MainActivity.CURRENT|| getTab() == 3) {
            // Do nothing on current fragment when you click the only group
            expandableListView
                    .setOnGroupClickListener(new OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent,
                                                    View v, int groupPosition, long id) {
                            return true;
                        }
                    });
            expandableListView.setAdapter(adapter);
            expandableListView.expandGroup(0);
            expandableListView.setGroupIndicator(null);
        }
        // Re-expand previously expanded groups
        else {
            Set<String> bldgs = mainActivity.getCurBldgs(getTab());
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                String name = ((Building) adapter.getGroup(i)).getName();
                if (bldgs.contains(name))
                    expandableListView.expandGroup(i);
            }
            expandableListView.setIndicatorBounds(0, 50);
        }
        // Re-instantiate scroll position
        int position[] = getPos();
        expandableListView.setSelectionFromTop(position[0], position[1]);

        return view;
    }

    // Save scroll position
    @Override
    public void onPause() {
        super.onPause();
        View v = expandableListView.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();
        setPos(expandableListView.getFirstVisiblePosition(), top);
    }
}
