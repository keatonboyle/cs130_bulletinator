package com.example.bulletinator.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Bulletin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title, bodyText, description, contact, category;
    private int imageId, iconId, btnId;

    public Bulletin(String t, String d, String b, String c, int iId, int icId,
                    int bId) {
        bodyText = b;
        if (d.equals("") && !bodyText.equals("")) {
            description = bodyText;
        } else {
            description = d;
        }
        title = t;
        imageId = iId;
        contact = c;
        iconId = icId;
        btnId = bId;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }

    public String getContactInfo() {
        return contact;
    }

    public int getIconId() {
        return iconId;
    }

    public int getBulletinId() {
        return btnId;
    }

    public Bulletin(JSONObject json) {
        try {
            btnId = json.getInt("btnid");
            bodyText = json.getString("bodytext");
            description = json.getString("shortdesc");
            title = json.getString("title");
            imageId = json.optInt("fid");
            contact = json.getString("contact");
            category = json.getString("category");
        } catch (JSONException e) {
            btnId = 0;
            bodyText = "error";
            description = "error";
            title = "error";
            imageId = 0;
            contact = "error";
            category = "error";
        }

        //TODO: figure out iconID
        iconId = 0;
    }
}
