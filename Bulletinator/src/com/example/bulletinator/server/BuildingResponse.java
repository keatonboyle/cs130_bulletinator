package com.example.bulletinator.server;

import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuildingResponse extends ServerResponse {
    public BuildingResponse(String raw) {
        super(raw);
        bulletins = new HashMap<Integer, Bulletin>();
        JSONObject json = null;
        JSONArray btnArray = null;
        try {
            json = new JSONObject(raw);

            type = json.getString("type");

            btnArray = json.optJSONArray("bulletins");

            if (btnArray != null) {
                for (int ii = 0; ii < btnArray.length(); ii++) {
                    Bulletin btn = new Bulletin(btnArray.getJSONObject(ii));
                    bulletins.put(btn.getBulletinId(), btn);
                }
            }

            bld = new Building(json.getInt("bldid"),
                    json.getString("name"),
                    bulletins.keySet());
        } catch (JSONException e) {
            type = "error";
            bld = null;
        }
    }

    public Building bld;
    public Map<Integer, Bulletin> bulletins;

}

