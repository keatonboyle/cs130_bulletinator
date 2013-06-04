package com.example.bulletinator.data;

import com.example.bulletinator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Bulletin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title, bodyText, description, contact, category;
    private int imageId, iconId, btnId;

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

        if (category.equals("propaganda")) {
            iconId = R.drawable.propaganda;
        }
        else if (category.equals("scholarship")) {
            iconId = R.drawable.scholarship;
        }
        else if (category.equals("job")) {
            iconId = R.drawable.job;
        }
        else {
            iconId = R.drawable.event;
        }
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
}
