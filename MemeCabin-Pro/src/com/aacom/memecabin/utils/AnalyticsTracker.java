/**
 *
 */
package com.aacom.memecabin.utils;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.memecabin.pro.AnalyticsMemeCabin;
import com.memecabin.pro.AnalyticsMemeCabin.TrackerName;

/**
 * @author Ashraful
 * 
 */
public class AnalyticsTracker {

	/**
	 * Create the tracker and send the data to google analytics
	 * 
	 * @param activity
	 *            Source activity for this event
	 * @param strId
	 *            Screen name String id for the event
	 */
	public static void sendTrackData(Activity activity, int strId) {
		// Get tracker.
		final Tracker t = ((AnalyticsMemeCabin) activity.getApplication())
				.getTracker(TrackerName.APP_TRACKER);

		final String screenName = activity.getString(strId);
		// Set screen name.
		t.setScreenName(screenName);

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
	}
}
