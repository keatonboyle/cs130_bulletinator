package com.example.bulletinator.server;

import org.json.*;

public class EverythingResponse extends ServerResponse {
    public EverythingResponse(String raw) {
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
