package com.example.bulletinator.server;

import com.example.bulletinator.data.Building;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AllBuildingsResponse extends ServerResponse {
    public AllBuildingsResponse(String raw) {
        super(raw);

        buildings = new HashMap<Integer, Building>();

        JSONObject json = null;
        try {
            json = new JSONObject(raw);

            type = json.getString("type");

            JSONArray bldArray = json.getJSONArray("buildings");

            for (int ii = 0; ii < bldArray.length(); ii++) {
                Building bld =
                        new Building(bldArray.getJSONObject(ii).getInt("bldid"),
                                bldArray.getJSONObject(ii).getString("name"),
                                new HashSet<Integer>());

                buildings.put(bld.getId(), bld);
            }
        } catch (JSONException e) {
            type = "error";
            buildings = null;
        }
    }

    public Map<Integer, Building> buildings;

}
