package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

public class BoltsMeasurementEventListener extends BroadcastReceiver {
	private static BoltsMeasurementEventListener _instance;

	private final static String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
	private final static String MEASUREMENT_EVENT_NAME_KEY = "event_name";
	private final static String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
	private final static String BOLTS_MEASUREMENT_EVENT_PREFIX = "bf_";

	private final Context applicationContext;

	private BoltsMeasurementEventListener(Context context) {
		applicationContext = context.getApplicationContext();
	}

	private void open() {
		final LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(applicationContext);
		broadcastManager.registerReceiver(this, new IntentFilter(
				MEASUREMENT_EVENT_NOTIFICATION_NAME));
	}

	private void close() {
		final LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(applicationContext);
		broadcastManager.unregisterReceiver(this);
	}

	static BoltsMeasurementEventListener getInstance(Context context) {
		if (_instance != null) {
			return _instance;
		}
		_instance = new BoltsMeasurementEventListener(context);
		_instance.open();
		return _instance;
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			close();
		} finally {
			super.finalize();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final AppEventsLogger appEventsLogger = AppEventsLogger
				.newLogger(context);
		final String eventName = BOLTS_MEASUREMENT_EVENT_PREFIX
				+ intent.getStringExtra(MEASUREMENT_EVENT_NAME_KEY);
		final Bundle eventArgs = intent
				.getBundleExtra(MEASUREMENT_EVENT_ARGS_KEY);
		final Bundle logData = new Bundle();
		for (final String key : eventArgs.keySet()) {
			final String safeKey = key.replaceAll("[^0-9a-zA-Z _-]", "-")
					.replaceAll("^[ -]*", "").replaceAll("[ -]*$", "");
			logData.putString(safeKey, (String) eventArgs.get(key));
		}
		appEventsLogger.logEvent(eventName, logData);
	}
}
