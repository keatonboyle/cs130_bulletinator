package com.example.bulletinator;

import java.io.*;
import java.util.*;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;
import com.example.bulletinator.fragments.AllFragment;
import com.example.bulletinator.fragments.ArchivedFragment;
import com.example.bulletinator.fragments.CurrentFragment;
import com.example.bulletinator.fragments.NearbyFragment;
import com.example.bulletinator.helpers.ExpandableListAdapter;
import com.example.bulletinator.helpers.ScrollManager;
import com.example.bulletinator.helpers.TabListener;

public class MainActivity extends Activity {
    public static final String BULLETIN = "BULLETIN";
    public static final int CURRENT = 0, NEARBY = 1, ALL = 2;
    private List<Building> buildings;
    private Set<String> nearbyExpandedBldgs, allExpandedBldgs;
    private int curTab;
    private ScrollManager sm;
    public List<Integer> deleted;

    // For testing
    private String[] bNames = {"Boelter Hall", "Engineering V", "Humanities"};
    private String[] bullDescriptions = {"Free food!",
            "Tutoring positions available.", "Volunteers needed.",};
    private int[] ids = {R.drawable.food_icon, R.drawable.tutoring_icon,
            R.drawable.volunteering_icon};
    private int[] fIds = {R.drawable.flyer, R.drawable.tutor_flyer,
            R.drawable.volunteers_needed};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sm = new ScrollManager();
        restorePreferences();

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
        actionBar.addTab(tab, 0, curTab == 0);

        tab = actionBar
                .newTab()
                .setText(tab2)
                .setTabListener(
                        new TabListener<NearbyFragment>(this, tab2,
                                NearbyFragment.class));
        actionBar.addTab(tab, 1, curTab == 1);

        tab = actionBar
                .newTab()
                .setText(tab3)
                .setTabListener(
                        new TabListener<AllFragment>(this, tab3,
                                AllFragment.class));
        actionBar.addTab(tab, 2, curTab == 2);

        tab = actionBar
                .newTab()
                .setText("Archived")
                .setTabListener(
                        new TabListener<ArchivedFragment>(this, "Archived",
                                ArchivedFragment.class));
        actionBar.addTab(tab, 3, curTab == 3);

        createDummyBulletins();
    }

    public void selectBulletin(Bulletin b) {
        Intent intent = new Intent(this, BulletinActivity.class);
        intent.putExtra(BULLETIN, b);
        startActivity(intent);
    }

    // Decides which buildings a fragment will receive
    public List<Building> getBuildings() {
        // Make sure "deleted" bulletins are not downloaded again
        List<Building> bldgs;

        // Delete preivously deleted bulletins?
        /*for (int i = 0; i < buildings.size(); i++) {
            List<Bulletin> bulletins = buildings.get(i).getBulletins();
            for (int j = 0 ; j < bulletins.size(); j++) {
                if (deleted.contains(bulletins.get(j).getBulletinId())) {
                    bulletins.remove(j);
                }
            }
        }*/

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

    // Save preferences: scroll position, selected tab, expanded buildings
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Save last tab
        editor.putInt("lastTab", curTab);

        // Save current expanded buildings to be retrieved later
        editor.putStringSet("lastNearbyExpandedBuildings", nearbyExpandedBldgs);
        editor.putStringSet("lastAllExpandedBuildings", allExpandedBldgs);

        // Save list position (index & top) in each tab
        int curPos[] = sm.getCurPos();
        int nearPos[] = sm.getNearPos();
        int allPos[] = sm.getAllPos();

        editor.putInt("curPosIndex", curPos[0]);
        editor.putInt("nearPosIndex", nearPos[0]);
        editor.putInt("allPosIndex", allPos[0]);
        editor.putInt("curPosTop", curPos[1]);
        editor.putInt("nearPosTop", nearPos[1]);
        editor.putInt("allPosTop", allPos[1]);

        // Note: Only those buildings still nearby should become expanded
        // Since the fragments all call getBuildings before re-populating.
        // So only those still in the getBuildings list will be able to be
        // re-expanded!

        writeDeletedBulletins();

        editor.commit();
    }

    public void setCurBldgs(int tab, Set<String> bldgs) {
        switch (tab) {
            case NEARBY: {
                nearbyExpandedBldgs = bldgs;
                return;
            }
            case ALL: {
                allExpandedBldgs = bldgs;
                return;
            }
            default:
                return;
        }
    }

    public Set<String> getCurBldgs(int tab) {
        switch (tab) {
            case NEARBY:
                return nearbyExpandedBldgs;
            case ALL:
                return allExpandedBldgs;
            default:
                return new HashSet<String>();
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
        nearbyExpandedBldgs = settings.getStringSet(
                "lastNearbyExpandedBuildings", new HashSet<String>());
        allExpandedBldgs = settings.getStringSet("lastAllExpandedBuildings",
                new HashSet<String>());

        // Restore position in each tab
        sm.setCurPos(settings.getInt("curPosIndex", 0), settings.getInt("curPosTop", 0));
        sm.setNearbyPos(settings.getInt("nearPosIndex", 0), settings.getInt("nearPosTop", 0));
        sm.setAllPos(settings.getInt("allPosIndex", 0), settings.getInt("allPosTop", 0));

        restoreDeletedBulletins();
    }

    public ScrollManager getSM() {
        return sm;
    }

    public void toast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
                String bodyText = j == 2 ? "Volunteers needed for decision making study. "
                        + "Please contact if interested."
                        : null;
                Bulletin b = new Bulletin(title, description, bodyText,
                        "555-555-5555", fIds[j], ids[j], j);
                barr.add(b);
                if (bNames[i].equals("Engineering V"))
                    break;
            }
            Building bldg = new Building(name, barr);
            buildings.add(bldg);
        }
    }

    public void delete(int id) {
        deleted.add(id);
    }

    public void writeDeletedBulletins() {
        File myfile = getFileStreamPath("deleted_bulletins");
        try {
            if(myfile.exists() || myfile.createNewFile()){
                FileOutputStream fos = openFileOutput("deleted_bulletins", MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(deleted);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreDeletedBulletins() {
        File myfile = getFileStreamPath("deleted_bulletins");
        try {
            if(myfile.exists()){
                FileInputStream fis = openFileInput("deleted_bulletins");
                ObjectInputStream ois = new ObjectInputStream(fis);
                deleted = (ArrayList<Integer>) ois.readObject();
                fis.close();
            } else {
                deleted = new ArrayList<Integer>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void archiveBulletin(Bulletin b) {
        File myfile = getFileStreamPath("archived_bulletins");
        List<Bulletin> archivedBulletins = new ArrayList<Bulletin>();
        try {
            if(myfile.exists()){
                FileInputStream fis = openFileInput("archived_bulletins");
                ObjectInputStream ois = new ObjectInputStream(fis);
                archivedBulletins = (ArrayList<Bulletin>) ois.readObject();
                fis.close();
            } else {
                archivedBulletins = new ArrayList<Bulletin>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (archivedBulletins.contains(b)) {
            return;
        }
        archivedBulletins.add(b);
        try {
            if(myfile.exists() || myfile.createNewFile()){
                FileOutputStream fos = openFileOutput("archived_bulletins", MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(archivedBulletins);
                fos.close();
                toast("You just archived a bulletin!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Bulletin> getArchivedBulletins() {
        File myfile = getFileStreamPath("archived_bulletins");
        List<Bulletin> archivedBulletins = new ArrayList<Bulletin>();
        try {
            if(myfile.exists()){
                FileInputStream fis = openFileInput("archived_bulletins");
                ObjectInputStream ois = new ObjectInputStream(fis);
                archivedBulletins = (ArrayList<Bulletin>) ois.readObject();
                fis.close();
                toast("Bulletins retrieved!");
            } else {
                archivedBulletins = new ArrayList<Bulletin>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return archivedBulletins;
    }

}