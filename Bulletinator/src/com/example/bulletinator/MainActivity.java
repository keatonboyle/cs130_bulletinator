package com.example.bulletinator;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bulletinator.data.AppData;
import com.example.bulletinator.data.Bulletin;
import com.example.bulletinator.fragments.AllFragment;
import com.example.bulletinator.fragments.CurrentFragment;
import com.example.bulletinator.fragments.NearbyFragment;
import com.example.bulletinator.fragments.ParentFragment;
import com.example.bulletinator.gps.LocationModule;
import com.example.bulletinator.helpers.FunctionObj;
import com.example.bulletinator.helpers.ScrollManager;
import com.example.bulletinator.helpers.TabListener;
import com.example.bulletinator.server.ServerResponse;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {
    public static final String BULLETIN = "BULLETIN";
    public static final int CURRENT = 0, NEARBY = 1, ALL = 2;
    private Set<String> nearbyExpandedBldgs, allExpandedBldgs;
    private int curTab;
    private ScrollManager sm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sm = new ScrollManager();
        restorePreferences();
        setUpGUI();
        setUpGPS();
    }

    /*
        public void locationCallback(Location l) {
            //toast(Double.toString(l.getLatitude()) + ", " +
            //      Double.toString(l.getLongitude()));

            GPSRequest gpsr =
                    new GPSRequest(
                            new FunctionObj<ServerResponse>() {
                                public void call(ServerResponse sr) {
                                    gotDataFromGPSResponse(sr);
                                }
                            },
                            AppData.baseurl,
                            l.getLatitude(),
                            l.getLongitude()
                    );

            gpsr.send();
            toast("sent");
        }
    */
    public void selectBulletin(Bulletin b) {
        Intent intent = new Intent(this, BulletinActivity.class);
        intent.putExtra(BULLETIN, b);
        startActivity(intent);
    }

    // fills a fragment's list of buildings, and requests more buildings if we're lacking the info
    public boolean wantBuildingsFor(ParentFragment fragment) {
        switch (curTab) {
            case CURRENT: {
                return AppData.wantCurrentBuilding(fragment);
            }
            case NEARBY: {
                return AppData.wantNearbyBuildings(fragment);
            }
            default:
                return AppData.wantAllBuildings(fragment);
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

        editor.commit();
    }

    public void setCurExpandedBldgs(int tab, Set<String> bldgs) {
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

    public Set<String> getCurExpandedBldgs(int tab) {
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
    }

    public ScrollManager getSM() {
        return sm;
    }

    public void toast(String t) {
        Context context = getApplicationContext();
        CharSequence text = t;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void setUpGUI() {
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

    }


    public void setUpGPS() {

        AppData.getInstance(
                new FunctionObj<ServerResponse>() {
                    public void call(ServerResponse sr) {
                        notifyMain(sr);
                    }
                });

        //AppData.loadEverything();

        LocationModule mlm = new LocationModule(this);
        mlm.run();

    }

    public void notifyMain(ServerResponse sr) {
        String buildingName = null;
        if ((buildingName = AppData.singleGetLocation()) != null) {
            toast("Entered " + buildingName);
        }
    }

}