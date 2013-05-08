package com.example.bulletinator;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class NonCurrentFragment extends Fragment {

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
				(MainActivity) getActivity(), buildings, false);

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
		expandableListView.setAdapter(adapter);

		// expand the last building?
		// String bldg = ((MainActivity) getActivity()).getCurBldg();
		// for (int i = 0; i < buildings.size(); i++) {
		// if (buildings.get(i).getName().equals(bldg)) {
		// expandableListView.expandGroup(i);
		// }
		// }
		return view;
	}
}