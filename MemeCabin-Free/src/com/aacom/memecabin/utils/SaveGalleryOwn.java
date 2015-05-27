package com.aacom.memecabin.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class SaveGalleryOwn {

	public static void saveGallery(Context con) {

		MediaScannerConnection.scanFile(con, new String[] { Environment
				.getExternalStorageDirectory().toString() }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * android.media.MediaScannerConnection.OnScanCompletedListener
					 * #onScanCompleted(java.lang.String, android.net.Uri)
					 */
					@Override
					public void onScanCompleted(String path, Uri uri) {
						Log.i("ExternalStorage", "Scanned " + path + ":");
						Log.i("ExternalStorage", "-> uri=" + uri);
					}

				});

	}
}
