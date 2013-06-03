package com.example.bulletinator.server;

import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EverythingResponse extends ServerResponse {
    public EverythingResponse(String raw) {
        super(raw);
        JSONObject json = null;
        JSONArray bldArray = null;
        JSONArray btnArray = null;
        buildings = new HashMap<Integer, Building>();
        bulletins = new HashMap<Integer, Bulletin>();
        try {
            json = new JSONObject(raw);

            type = json.getString("type");

            bldArray = json.getJSONArray("buildings");

            for (int ii = 0; ii < bldArray.length(); ii++) {
                JSONObject bldObj = bldArray.getJSONObject(ii);
                JSONArray btnIdArray = bldObj.optJSONArray("bulletins");
                HashSet<Integer> btnIdSet = new HashSet<Integer>();

                if (btnIdArray != null) {
                    for (int jj = 0; jj < btnIdArray.length(); jj++) {
                        btnIdSet.add(btnIdArray.getInt(jj));
                    }
                }


                Building bld =
                        new Building(bldObj.getInt("bldid"),
                                bldObj.getString("name"),
                                btnIdSet);

                buildings.put(bld.getId(), bld);
            }

            btnArray = json.optJSONArray("bulletins");

            if (btnArray != null) {
                for (int ii = 0; ii < btnArray.length(); ii++) {
                    Bulletin btn = new Bulletin(btnArray.getJSONObject(ii));
                    bulletins.put(btn.getBulletinId(), btn);
                }
            }


        } catch (JSONException e) {
            type = "problem";
            title = "problem";
        }
    }

    public String title;
    public Map<Integer, Building> buildings;
    public Map<Integer, Bulletin> bulletins;
}
