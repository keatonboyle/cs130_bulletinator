package com.example.bulletinator;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class CurrentFragment extends Fragment {
	// private List<Bulletin> bulletins;
	private List<Building> buildings;
	private ExpandableListView expandableListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate current buildings layout
		View view = inflater.inflate(R.layout.current, container, false);

		// Populate bulletins that MainActivity retrieved?
		buildings = ((MainActivity) getActivity()).getBuildings();

		// Populate list with bulletins
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		ExpandableListAdapter adapter = new ExpandableListAdapter(
				(MainActivity) getActivity(), buildings, true);

		// Set on click listener for viewing bulletins
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// Start new bulletin activity
				Bulletin b = buildings.get(groupPosition).getBulletins()
						.get(childPosition);
				((MainActivity) getActivity()).selectBulletin(b);
				return true;
			}
		});
		// Do nothing on current fragment when you click the only group
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		expandableListView.setAdapter(adapter);
		expandableListView.expandGroup(0);
		expandableListView.setGroupIndicator(null);
		return view;
	}
}