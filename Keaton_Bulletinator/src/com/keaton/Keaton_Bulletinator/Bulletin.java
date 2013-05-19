package com.keaton.Keaton_Bulletinator;

import java.io.Serializable;
import org.json.*;

public class Bulletin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title, bodyText, description, contact, category;
    private int imageId, iconId, bltId;

    public Bulletin(String t, String d, String b, String c, int iId, int icId,
                    int bId, String cat) {
        bodyText = b;
        description = d;
        title = t;
        imageId = iId;
        contact = c;
        iconId = icId;
        bltId = bId;
        category = cat;
    }

    public Bulletin(JSONObject json)
    {
       try
       {
          bodyText = json.getString("bodytext");
          description = json.getString("shortdesc");
          title = json.getString("title");
          imageId = json.getInt("fid");
          contact = json.getString("contact");
          category = json.getString("category");
       }
       catch (JSONException e)
       {
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
        return bltId;
    }
}
