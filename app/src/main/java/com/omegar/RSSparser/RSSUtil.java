package com.omegar.RSSparser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.TextView;

import com.omegar.Tools.LoadRSSFeed;

import java.net.URI;
import java.net.URISyntaxException;

public class RSSUtil {

	// Put your RSS feed URL here
	public static String RSSFEEDURL = "http://www.androidpit.com/feed/main.xml";

	// Change to a given feed
	public static void changeFeed(Context context){
		changeURL(context);
	}

	// Returns the filename of the stored feed
	public static String getFeedName(){
		return getDomainName(RSSFEEDURL);
	}

	// Strips a URL to the domain
	private static String getDomainName(String url) {
		String domain = null;
		try {
			domain = new URI(url).getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	// Takes a string URL as a new feed
	private static void changeURL(final Context context){
		// Set the content view
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// Set the alert title
		builder.setTitle("The link we will use:")
		// Set the alert message
		.setMessage("http://www.androidpit.com/feed/main.xml")
		// Can't exit via back button
		.setCancelable(false)
		// Set the positive button action
		.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
                RSSFEEDURL = "http://www.androidpit.com/feed/main.xml";

				// Parse the feed
				new LoadRSSFeed(context).execute();
			}
		})
		// Set the negative button actions
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				// If it's during initial loading
				if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isSetup", false)){
					// Exit the application
					((Activity)context).finish();
				} else {
					// Otherwise do nothing
					dialog.dismiss();
				}
			}
		});
		// Create dialog from builder
		AlertDialog alert = builder.create();
		// Don't exit the dialog when the screen is touched
		alert.setCanceledOnTouchOutside(false);
		// Show the alert
		alert.show();
		// Center the message
		((TextView)alert.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
		// Center the title of the dialog
		((TextView)alert.findViewById((context.getResources().getIdentifier("alertTitle", "id", "android")))).setGravity(Gravity.CENTER);
	}
}
