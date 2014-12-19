package com.omegar.RSSparser;

import java.io.Serializable;

/**
 * Allows the ability to store values from an RSS feed, before
 * being added to an RSSFeed object. Created by the DOMParser
 * when parsing an RSSFeed. Performs no complex methods, just 
 * stores the data of a feed item.
 */
public class RSSItem implements Serializable {

	// Create the strings we need to store
	private String	title;
    private String description;
    private String date;
    private String author;
    private String URL;
	// Serializable ID
	private static final long serialVersionUID = 1L;

	void setTitle(String nTitle) {
		// Sets the title
		title = nTitle;
	}

	void setAuthor(String nAuthor) {
		// Sets the author
		author = nAuthor;
	}

	void setURL(String nURL) {
		// Sets the URL
		URL = nURL;
	}

	void setDescription(String nDescription) {
		// Sets the description
		description = nDescription;
	}

	void setDate(String nDate) {
		// Sets the date
		date = nDate;
	}

	void setThumb(String nThumb) {
		// Sets the thumbnail
        String thumb = nThumb;
	}

	public String getTitle() {
		// Returns the title
		return title;
	}

	public String getDescription() {
		// Returns the description
		return description;
	}

	public String getDate() {
		// Returns the date
		return date;
	}

	public String getAuthor(){
		// Returns the author
		return author;
	}

	public String getURL(){
		// Returns the URL
		return URL;
	}
}
