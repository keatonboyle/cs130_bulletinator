package com.example.bulletinator.data;

import android.location.Location;

import com.example.bulletinator.helpers.CallbackListener;
import com.example.bulletinator.helpers.Rectangle;
import com.example.bulletinator.server.AllBuildingsResponse;
import com.example.bulletinator.server.BinResponse;
import com.example.bulletinator.server.BuildingResponse;
import com.example.bulletinator.server.DummyResponse;
import com.example.bulletinator.server.EverythingResponse;
import com.example.bulletinator.server.GPSRequest;
import com.example.bulletinator.server.GPSResponse;
import com.example.bulletinator.server.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    private static AppData instance = null;

    public static final String baseurl = "http://linux.ucla.edu/~cs130s/get.php";

    protected AppData() {
        bulletins = new HashMap<Integer, Bulletin>();
        buildings = new HashMap<Integer, Building>();
    }


    public static AppData getInstance(CallbackListener<ServerResponse> mainThread) {
        if (instance == null) {
            instance = new AppData();
        }
        instance.mainThread = mainThread;
        return instance;
    }

    public static void setLoc(Location loc) {
        if (instance.isLocationRelevant(loc)) {
            instance.lat = loc.getLatitude();
            instance.lon = loc.getLongitude();

            instance.getFromGPS(loc);
        }
    }

    public static void getFromGPS(Location l) {
        GPSRequest gpsr =
                new GPSRequest(instance.mainThread, instance.baseurl,
                        instance.lat, instance.lon);

        gpsr.send();
    }


    public static boolean isLocationRelevant(Location loc) {
        return ((bounds == null) ||
                (bounds.isOutside(loc.getLatitude(), loc.getLongitude())));
    }

    public static AppData update(DummyResponse dr) {
        instance.dummy = dr.title;
        return instance;
    }

    public static AppData update(EverythingResponse er) {
        instance.buildings.putAll(er.buildings);
        instance.bulletins.putAll(er.bulletins);
        return instance;
    }

    public static AppData update(BuildingResponse bldr) {
        if (bldr.bld != null) {
            instance.buildings.put(bldr.bld.getId(), bldr.bld);

            instance.bulletins.putAll(bldr.bulletins);
        }

        return instance;
    }

    public static AppData update(AllBuildingsResponse bldr) {
        for (Map.Entry<Integer, Building> bldEntry : bldr.buildings.entrySet()) {
            if (!instance.buildings.containsKey(bldEntry.getKey())) {
                instance.buildings.put(bldEntry.getKey(), bldEntry.getValue());
            }
        }

        return instance;
    }

    public static AppData update(GPSResponse gpsr) {
        instance.curBld = gpsr.curBld;

      /* insert the building if it isn't here yet */
        if (!instance.buildings.containsKey(gpsr.curBld.getId())) {
            instance.buildings.put(gpsr.curBld.getId(), gpsr.curBld);
        }

      /* insert any new bulletins */
        instance.bulletins.putAll(gpsr.bulletins);

      /* insert any buildings that haven't been downloaded */
        for (Map.Entry<Integer, Building> bldEntry : gpsr.nearBuildings.entrySet()) {
            if (!instance.buildings.containsKey(bldEntry.getKey())) {
                instance.buildings.put(bldEntry.getKey(), bldEntry.getValue());
            }
        }

      /* set the bounds rectangle */
        instance.bounds = gpsr.bounds;

        return instance;
    }

    public static AppData update(BinResponse br) {
        return instance;
    }

    public static AppData update(Building bld) {
        return instance;
    }

    /* Accessor functions */
    public static Bulletin getBulletin(int btnid) {
        return instance.bulletins.get(btnid);
    }

    public static Building getBuilding(int bldid) {
        return instance.buildings.get(bldid);
    }

    public static List<Building> getAllBuildings() {
        List<Building> bldList = new ArrayList<Building>();

        bldList.addAll(instance.buildings.values());

        return bldList;
    }

    public static List<Bulletin> getBulletinsIn(Building bld) {
        List<Bulletin> btnList = new ArrayList<Bulletin>();

        for (Integer ii : bld.getBtnIds()) {
            Bulletin btn = instance.bulletins.get(ii);
            if (btn != null) {
                btnList.add(btn);
            }
        }

        return btnList;
    }

    public static List<Bulletin> getBulletinsIn(int bldid) {
        //TODO: Check if bulletins are missing
        Building bld = instance.buildings.get(bldid);

        if (bld == null) return null;

        return getBulletinsIn(bld);
    }

    public static String getSummaryString() {
        String running = "";

        for (Building bld : buildings.values()) {
            running += (bld.getName() + "\n");
            for (Bulletin btn : instance.getBulletinsIn(bld)) {
                running += ("   [" + btn.getBulletinId() + "] " +
                        btn.getTitle() + "\n");
            }
        }

        return running;
    }

    public static String getNiceCoords() {
        return (
                Location.convert(Math.abs(instance.lat), Location.FORMAT_MINUTES) +
                        " " + (instance.lat >= 0 ? "N" : "S") + ", " +
                        Location.convert(Math.abs(instance.lon), Location.FORMAT_MINUTES) +
                        " " + (instance.lon >= 0 ? "E" : "W"));
    }

    public static List<Building> getNearbyBuildings() {
        return instance.getAllBuildings();
    }

    public static List<Building> getCurrentBuilding() {
        List<Building> lb = new ArrayList<Building>();
        lb.add(instance.curBld != null ? instance.curBld :
                instance.buildings.entrySet().iterator().next().getValue());

        return lb;
    }

    private static String dummy;
    private static CallbackListener<ServerResponse> mainThread;
    private static double lat;
    private static double lon;
    private static Rectangle bounds;
    private static Map<Integer, Bulletin> bulletins;
    private static Map<Integer, Building> buildings;
    private static Building curBld;
}

