/**
 *
 */
package com.memecabin.pro;

import java.util.HashMap;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * @author Ashraful
 * 
 */ 
public class AnalyticsMemeCabin extends Application {

	private static final String PROPERTY_ID = "UA-55801508-1";

	public enum TrackerName {
		APP_TRACKER, GLOBAL_TRACKER, ECOMMERS_TRACKER,
	}

	HashMap<TrackerName, Tracker> trackers = new HashMap<AnalyticsMemeCabin.TrackerName, Tracker>();

	public AnalyticsMemeCabin() {
		super();
	}

	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!trackers.containsKey(trackerId)) {
			final GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

			// Tracker t = (trackerId == TrackerName.APP_TRACKER) ?
			// analytics.newTracker(PROPERTY_ID) : (trackerId ==
			// TrackerName.GLOBAL_TRACKER) ?
			// analytics.newTracker(R.xml.global_tracker) :
			// analytics.newTracker(R.xml.ecommerce_tracker);
			final Tracker t = trackerId == TrackerName.APP_TRACKER ? analytics
					.newTracker(PROPERTY_ID) : null;
			trackers.put(trackerId, t);
		}

		return trackers.get(trackerId);
	}

}
