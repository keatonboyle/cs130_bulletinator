package com.example.bulletinator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {
	public static final String BULLETIN = "BULLETIN";
	public static final int CURRENT = 0, NEARBY = 1, ALL = 2;
	private List<Building> buildings;
	private List<String> nearbyExpandedBldgs, allExpandedBldgs;
	private int curTab;

	// For testing
	private String[] bNames = { "Boelter Hall", "Engineering V", "Humanities" };
	private String[] bullDescriptions = {
			"Free food!",
			"Tutoring positions available.",
			"Volunteers needed.",
			};
	private int[] ids = { R.drawable.food_icon, R.drawable.tutoring_icon, 
			R.drawable.volunteering_icon
	};
	private int[] fIds = { R.drawable.flyer, R.drawable.tutor_flyer,
			R.drawable.volunteers_needed
	};//////////

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Create fragments by giving it an adapter? Use a parent class or
		// just
		// the same fragment class w/ different adapter?? and its own variable
		// to know which fragment it is? use some kind of id to id the fragment
		// as current
		// etc. use the set fragment params thing? USE tab listener to see if i
		// can
		// only use one fragment class, in tab listener maybe "set" the adapter
		// so have the fragment class have a set adapter function
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		String tab1 = getResources().getString(R.string.label1);
		String tab2 = getResources().getString(R.string.label2);
		String tab3 = getResources().getString(R.string.label3);

		Tab tab = actionBar
				.newTab()
				.setText(tab1)
				.setTabListener(
						new TabListener<CurrentFragment>(this, tab1,
								CurrentFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(tab2)
				.setTabListener(
						new TabListener<NearbyFragment>(this, tab2,
								NearbyFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(tab3)
				.setTabListener(
						new TabListener<AllFragment>(this, tab3,
								AllFragment.class));
		actionBar.addTab(tab);

		restorePreferences();
		createDummyBulletins();
	}

	public void selectBulletin(Bulletin b) {
		Intent intent = new Intent(this, BulletinActivity.class);
		intent.putExtra(BULLETIN, b);
		startActivity(intent);
	}

	// Decides which buildings a fragment will receive
	public List<Building> getBuildings() {
		List<Building> bldgs;
				
		switch (curTab) {
		case CURRENT: {
			bldgs = new ArrayList<Building>();
			bldgs.add(buildings.get(0));
			return bldgs;
		}
		case NEARBY: {
			bldgs = new ArrayList<Building>();
			bldgs.add(buildings.get(0));
			bldgs.add(buildings.get(1));
			return bldgs;
		}
		default:
			return buildings;
		}
	}

	/*
	 * TODO: Saving last stuff you saw 3. If in nearby fragment, only if those buildings are still
	 * nearby 4. If in all fragment, keep every building that was expanded
	 * expanded? 5. Use getLastVisiblePosition on expandableListAdapter to
	 * return to same place in list?
	 */
	@Override
	public void onPause() {
		super.onPause();

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		// Save last tab
		editor.putInt("lastTab", curTab);

		// Save current expanded buildings to be retrieved later
		Set<String> bldgSet = new HashSet<String>(nearbyExpandedBldgs);
		editor.putStringSet("lastNearbyExpandedBuildings", bldgSet);
		bldgSet = new HashSet<String>(allExpandedBldgs);
		editor.putStringSet("lastAllExpandedBuildings", bldgSet);

		// Save list position in each tab

		// Note: Only those buildings still nearby should become expanded
		// Since the fragments all call getBuildings before re-populating.
		// So only those still in the getBuildings list will be able to be
		// re-expanded!

		editor.commit();
	}

	@Override
	public void onResume() {
		// TODO: Re-instantiate selected tab elsewhere?
		super.onResume();
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		getActionBar().selectTab(
				getActionBar().getTabAt(settings.getInt("lastTab", 0)));
	}

	private void createDummyBulletins() {
		// Will be replaced with something that returns actual bulletins
		buildings = new ArrayList<Building>();
		for (int i = 0; i < bNames.length; i++) {
			String name = bNames[i];
			ArrayList<Bulletin> barr = new ArrayList<Bulletin>();
			for (int j = 0; j < 3; j++) {
				String title = "Bulletin #" + j;
				String description = bullDescriptions[j];
				String bodyText = j==2? "Volunteers needed for decision making study. " +
						"Please contact if interested.": null;
				Bulletin b = new Bulletin(title, description, bodyText,
						"555-555-5555", fIds[j], ids[j], 0);
				barr.add(b);
				if (bNames[i].equals("Engineering V"))
					break;
			}
			Building bldg = new Building(name, barr);
			buildings.add(bldg);
		}
	}

	public void setCurBldgs(int tab, List<String> bldgs) {
		switch(tab) {
		case NEARBY: {
			nearbyExpandedBldgs = bldgs;
			return;
		}
		case ALL: {
			allExpandedBldgs = bldgs;
			return;
		}
		default: return;
		}
	}

	public List<String> getCurBldgs(int tab) {
		switch(tab) {
		case NEARBY: 
			return nearbyExpandedBldgs;
		case ALL:
			return allExpandedBldgs;
		default: return null;
		}
	}
	
	public void setCurTab(int tab) {
		curTab = tab;
	}
	
	public void restorePreferences() {
		// Restore last tab
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		curTab = settings.getInt("lastTab", 0);

		// Restore last expanded buildings (by tab)
		Set<String> bldgSet = settings.getStringSet("lastNearbyExpandedBuildings", new HashSet<String>());
		List<String> bldgs = new ArrayList<String>(bldgSet);
		nearbyExpandedBldgs = bldgs;
		bldgSet = settings.getStringSet("lastAllExpandedBuildings", new HashSet<String>());
		bldgs = new ArrayList<String>(bldgSet);
		allExpandedBldgs = bldgs;
	}
}