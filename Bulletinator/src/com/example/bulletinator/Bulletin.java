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

	public Bulletin(String t, String d, String b, String c, int iId) {
		bodyText = b;
		description = d;
		title = t;
		imageId = iId;
		contact = c;
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
}
