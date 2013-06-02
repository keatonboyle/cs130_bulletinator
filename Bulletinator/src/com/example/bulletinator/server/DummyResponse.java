package com.example.bulletinator.server;

import org.json.JSONException;
import org.json.JSONObject;

public class DummyResponse extends ServerResponse {
    public DummyResponse(String raw) {
        super(raw);
        JSONObject json = null;
        try {
            json = new JSONObject(raw);

            type = json.getString("type");
            title = json.getJSONObject("payload").getString("title");
        } catch (JSONException e) {
            type = "problem";
            title = "problem";
        }
    }

    public String title;

}
