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

public abstract class ParentFragment extends Fragment {
	private List<Building> buildings;
	private ExpandableListView expandableListView;

	public abstract int getTab();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate current buildings layout
		View view = inflater.inflate(R.layout.list_view, container, false);

		// Populate bulletins that MainActivity retrieved?
		buildings = ((MainActivity) getActivity()).getBuildings();

		// Populate list with bulletins
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		ExpandableListAdapter adapter = new ExpandableListAdapter(
				(MainActivity) getActivity(), buildings, getTab());

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

		if (getTab() == MainActivity.CURRENT) {
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
		// TODO: When/How often should this happen?
		else {
			List<String> bldgs = ((MainActivity) getActivity())
					.getCurBldgs(getTab());
			for (int i = 0; i < adapter.getGroupCount(); i++) {
				String name = ((Building) adapter.getGroup(i)).getName();
				if (bldgs.contains(name))
					expandableListView.expandGroup(i);
			}
		}
		return view;
	}
}