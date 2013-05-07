package com.example.bulletinator;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {
	public static final String BULLETIN = "BULLETIN";
	private List<Building> buildings;
	public static final int CURRENT = 0, NEARBY = 1, ALL = 2;
	// private List<String> curBldgs;
	private String curBldg = "";

	// For testing
	private String[] bNames = { "Boelter Hall", "Engineering V", "Humanities" };
	private String[] bullDescriptions = {
			"The axiomatic weather examines the meat.",
			"The standing disgust exchanges the land.",
			"The payment combines the unique quality.",
			"The reading informs the yummy twist.",
			"The guide assembles the typical edge." };

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
						new TabListener<NonCurrentFragment>(this, tab2,
								NonCurrentFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(tab3)
				.setTabListener(
						new TabListener<NonCurrentFragment>(this, tab3,
								NonCurrentFragment.class));
		actionBar.addTab(tab);

		// curBldgs = new ArrayList<String>();

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
		int curTab = getActionBar().getSelectedTab().getPosition();
		switch (curTab) {
		case CURRENT: {
			bldgs = new ArrayList<Building>();
			bldgs.add(buildings.get(1));
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
	 * TODO: Saving last stuff you saw 1. Be able to have last buildings
	 * expanded expanded (Have a list of them (ids?)) 2. If in current fragment,
	 * do nothing 3. If in nearby fragment, only if those buildings are still
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
		int curTab = getActionBar().getSelectedTab().getPosition();
		editor.putInt("lastTab", curTab);

		// Save last building(s) expanded
		editor.putString("lastBuilding", curBldg);

		editor.commit();
	}

	@Override
	public void onResume() {
		// Re instantiate stuff
		super.onResume();
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		getActionBar().selectTab(
				getActionBar().getTabAt(settings.getInt("lastTab", 0)));
		curBldg = settings.getString("lastBuilding", "None");
	}

	private void createDummyBulletins() {
		// Will be replaced with something that returns actual bulletins
		buildings = new ArrayList<Building>();
		for (int i = 0; i < bNames.length; i++) {
			String name = bNames[i];
			ArrayList<Bulletin> barr = new ArrayList<Bulletin>();
			for (int j = 0; j < 5; j++) {
				String title = "Bulletin #" + j;
				String description = bullDescriptions[j];
				String bodyText = bullDescriptions[j] + " "
						+ bullDescriptions[j] + " " + bullDescriptions[j];
				Bulletin b = new Bulletin(title, description, bodyText,
						"555-555-5555", R.drawable.flyer);
				barr.add(b);
			}
			Building bldg = new Building(name, barr);
			buildings.add(bldg);
		}
	}

	public void setCurBldg(String b) {
		curBldg = b;
	}

	public String getCurBldg() {
		return curBldg;
	}
}