package com.memecabin.pro;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.memecabin.db.DatabaseHandler;
/* Jin Remove Mopub ADS
import com.mopub.common.MoPub;
*/
import com.memecabin.pro.R;

public class SplashScreenActivity extends Activity {
	Context con;

	Handler handler = new Handler();

	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashlayout);
		con = this;
		db = new DatabaseHandler(con);
		
		// Code to get KeyHash value.
		  try {
		   PackageInfo info = getPackageManager().getPackageInfo(
		   // "com.code2care.thebuddhaquotes",
		     getPackageName(), PackageManager.GET_SIGNATURES);
		   for (Signature signature : info.signatures) {
		    MessageDigest md = MessageDigest.getInstance("SHA");
		    md.update(signature.toByteArray());
		    Log.e("TAG", "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
		   }
		  } catch (NameNotFoundException e) {
		   Log.e("TAG", "Exception(NameNotFoundException) : " + e);
		  } catch (NoSuchAlgorithmException e) {
		   Log.e("TAG", "Exception(NoSuchAlgorithmException) : " + e);
		  }
		
		/* Jin Remove Mopub ADS
		  // Set location awareness and precision globally for your app:
        MoPub.setLocationAwareness(MoPub.LocationAwareness.TRUNCATED);
        MoPub.setLocationPrecision(4);
        */

		final String manufactures = android.os.Build.MANUFACTURER;

		Log.e("Device manufactures ", manufactures + "");

		if (manufactures.equalsIgnoreCase("samsung")) {

			AppConstant.isSamsung = true;
		} else {
			AppConstant.isSamsung = false;

		}

		AppConstant.fromscreen = "registrationbtn";
		/*
		 * check first time log
		 */
		AppConstant.upcomingCallOnce = false; //By Jin

		if (SharedPreferencesHelper.getShortCutFlag(con)) {
			createShortCut(); // to avoid duplicate shortcut

			final Date d = new Date();

			SharedPreferencesHelper.setLaunchTime(con,
					String.valueOf(d.getTime() / 1000));

			SharedPreferencesHelper.setShortCutFlag(con, false);
		}

		SharedPreferencesHelper.setUpcomingStatus(con, true);

		SharedPreferencesHelper.setUpcomingAds(con, true);

		// For app launch count
		int count = SharedPreferencesHelper.getLoginCount(con);
		count++;
		SharedPreferencesHelper.setLoginCount(con, count);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				if (!SharedPreferencesHelper.isLoggedIn(con)) {

					final Intent in = new Intent(con, LogActivity.class);
					startActivity(in);
					SplashScreenActivity.this.finish();

				} else {

					AppConstant.userID = SharedPreferencesHelper.getUserID(con);
					AppConstant.status = "1";

					AppConstant.fromscreen = "main";
					final Intent intent = new Intent(con, HomeActivity.class);
					startActivity(intent);
					SplashScreenActivity.this.finish();
				}

			}
		}, 1500);

	}

	public void createShortCut() {
		final Intent shortcutintent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		shortcutintent.putExtra("duplicate", false);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		final Parcelable icon = Intent.ShortcutIconResource.fromContext(con,
				R.drawable.ic_launcher);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(con,
				SplashScreenActivity.class));
		sendBroadcast(shortcutintent);
		// com.android.launcher.action
	}

}
