package com.example.bulletinator.server;

import com.example.bulletinator.data.Building;
import com.example.bulletinator.data.Bulletin;

import org.json.*;

import java.util.HashSet;
import java.util.Map;

public class EverythingResponse extends ServerResponse {
    public EverythingResponse(String raw) {
        super(raw);
        JSONObject json = null;
        JSONArray bldArray = null;
        JSONArray btnArray = null;
        try {
            json = new JSONObject(raw);

            type = json.getString("type");

            bldArray = json.getJSONArray("buildings");

            for (int ii = 0; ii < bldArray.length(); ii++) {
                Building bld =
                        new Building(bldArray.getJSONObject(ii).getInt("bldid"),
                                bldArray.getJSONObject(ii).getString("name"),
                                new HashSet<Integer>());

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
    public Map<Integer,Building> buildings;
    public Map<Integer,Bulletin> bulletins;
}
