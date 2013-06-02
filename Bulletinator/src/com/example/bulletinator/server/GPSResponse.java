package com.example.bulletinator.server;

import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;
import com.example.bulletinator.helpers.Rectangle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GPSResponse extends ServerResponse {
    public GPSResponse(String raw) {
        super(raw);
        bulletins = new HashMap<Integer, Bulletin>();
        nearBuildings = new HashMap<Integer, Building>();

        JSONObject json = null;
        JSONObject curBldJson = null;
        JSONObject rectJson = null;
        JSONArray btnArray = null;
        JSONArray nearbyBldArray = null;

        try {
            json = new JSONObject(raw);

         /* common elements */
            type = json.getString("type");

         /* current building */
            curBldJson = json.optJSONObject("curbuild");

            if (curBldJson != null) {
                btnArray = curBldJson.optJSONArray("bulletins");

                if (btnArray != null) {
                    for (int ii = 0; ii < btnArray.length(); ii++) {
                        Bulletin btn = new Bulletin(btnArray.getJSONObject(ii));
                        bulletins.put(btn.getBulletinId(), btn);
                    }
                }

                curBld = new Building(curBldJson.getInt("bldid"),
                        curBldJson.getString("name"),
                        bulletins.keySet());

            }

         /* nearby buildings */
            nearbyBldArray = json.optJSONArray("nearbuild");

            if (nearbyBldArray != null) {
                for (int ii = 0; ii < nearbyBldArray.length(); ii++) {
                    JSONObject bldJson = nearbyBldArray.getJSONObject(ii);

                    nearBuildings.put(bldJson.getInt("bldid"),
                            new Building(bldJson.getInt("bldid"),
                                    bldJson.getString("name"),
                                    null));
                }
            }

         /* bounds rectangle */
            rectJson = json.getJSONObject("rect");
            bounds = new Rectangle(rectJson.getDouble("north"),
                    rectJson.getDouble("east"),
                    rectJson.getDouble("south"),
                    rectJson.getDouble("west"));


        } catch (JSONException e) {
            type = "error";
            curBld = null;
            bounds = null;
        }
    }

    public Building curBld;
    public Map<Integer, Bulletin> bulletins;
    public Map<Integer, Building> nearBuildings;
    public Rectangle bounds;
}




