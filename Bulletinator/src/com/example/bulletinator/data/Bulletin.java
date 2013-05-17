package com.example.bulletinator.data;

import java.io.Serializable;

public class Bulletin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title, bodyText, description, contact;
    private int imageId, iconId, bltId;

    public Bulletin(String t, String d, String b, String c, int iId, int icId,
                    int bId) {
        bodyText = b;
        description = d;
        title = t;
        imageId = iId;
        contact = c;
        iconId = icId;
        bltId = bId;
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
