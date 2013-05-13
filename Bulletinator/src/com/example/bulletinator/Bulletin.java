package com.example.bulletinator;

import java.io.Serializable;

public class Bulletin implements Serializable {
	private static final long serialVersionUID = 1L;
	// Text shown in bulletin activity
	private String bodyText;
	private String title;
	// Used for bulletin preview
	private String description;
	private String contact;
	// Used for location of image
	private int imageId;
	private int iconId;
	private int layoutType;
	private int bltId;

	public Bulletin(String t, String d, String b, String c, int iId, int icId, int bId, int l) {
		bodyText = b;
		description = d;
		title = t;
		imageId = iId;
		contact = c;
		iconId =icId;
		bltId = bId;
		// Use if statements to decide the layout type (image view, no image view, etc.)
		layoutType = l;
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
	
	public int getLayoutType() {
		return layoutType;
	}
	
	public int getBulletinId() {
		return bltId;
	}
}
