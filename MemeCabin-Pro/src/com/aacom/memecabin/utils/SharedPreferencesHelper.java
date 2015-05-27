package com.aacom.memecabin.utils;

/*
 * Copyright (C) 2010 Mathieu Favez - http://mfavez.com
 *
 *
 * This file is part of FeedGoal.
 *
 * FeedGoal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeedGoal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FeedGoal.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aapbd.utils.print.print;

public final class SharedPreferencesHelper {

	private static final String LOG_TAG = "SharedPreferencesHelper";
	private static final String PREFS_FILE_NAME = "AppPreferences";

	public static final String actionReminder = "actionReminder";
	public static final String databaseOnce = "databaseOnce";
	public static final String userId = "userId";
	public static final String appId = "appId";
	public static final String upcomingAdsLoad = "upcomingadsload";
	public static final String appIcon = "appIcon";
	public static final String LAUNCHTIME = "lunchtime";
	public static final String appLink = "appLink";
	public static final String memeswipedialog = "memeswipedialog";
	public static final String favoswipedialog = "favoswipedialog";
	public static final String motivationswipedialog = "motivationswipedialog";
	public static final String racyswipedialog = "racyswipedialog";
	public static final String tradingswipedialog = "tradingswipedialog";
	public static final String passCode = "passCode";
	public static final String passStatus = "passStatus";
	public static final String lockStatus = "lockStatus";
	public static final String toggleStatus = "toggleStatus";
	public static final String upcomingStatus = "upcomingstatus";
	public static final String usingskip = "usingskip";
	public static final String allmemes = "allmemes";
	public static final String SPIFFYALL = "spiffyall";

	private static final String messageCount = "messagecount";
	private static final String danMsgShow = "danmsgshow";

	public static final String motivationalmemes = "motivationalmemes";
	public static final String spiffyonoff = "spiffyonoff";
	public static final String sdlonoff = "sdlonoff";
	public static final String racymemes = "racymemes";
	public static final String memeViewpagerPosition = "memviewpagerposition";
	public static final String motiViewpagerPosition = "motiViewpagerPosition";
	public static final String racyViewpagerPosition = "racyViewpagerPosition";
	public static final String trendViewpagerPosition = "trendViewpagerPosition";
	public static final String favorViewpagerPosition = "favorViewpagerPosition";
	public static final String twitterFlag = "twitterFlag";
	public static final String shortCutFlag = "shortcutflag";

	public static final String RATE_APP_STR = "RateThisApp";
	public static final String LOGIN_NUMBER = "LoginNumber";
	public static final String SHOW_RATE_APP = "ShowRateApp";
	public static final String RATE_APP_LETER = "RateAppLeter";
	public static final String RATE_APP_LETER_TIME = "RateAppLeterTime";

	public static final String SHOW_NOTIFICATION = "ShowNotification";
	public static final String SHOW_ALL_MEME_NOTIFY = "ShowAllMemeNotify";
	public static final String SHOW_MOTIVE_MEME_NOTIFY = "ShowMotiveMemeNotify";
	public static final String SHOW_RACY_MEME_NOTIFY = "ShowRacyMemeNotify";
	public static final String SHOW_SPIFFY_NOTIFY = "ShowSpiffyNotify";

	public static final String TOTAL_NUMBER = "TotalNumber";
	public static final String MY_FAVORITE_NUMBER = "MyFavoriteNumber";
	public static final String ISFIRSTTIME = "firstitme";

	// public static final String REMIND_ME_LETTER_MESSAGE =
	// "remindmeletermessage";
	// public static final String DEN_MEASSAGE = "denmessage";
	// public static final String DEN_MSG_URL = "denmsgurl";
	// public static final String URL_ADVERTISE_DAN = "urladvertisedan";
	// public static final String DEN_MSG_TITLE = "denmsgtitle";
	// public static final String DEN_MSG_SUB_TITLE = "denmsgsubtitle";
	// public static final String DEN_MSG_NOT_SHOW = "denmsgnotshow";
	public static final String DAN_MSG = "DanMsg";
	public static final String DAN_MSG_REMIND_ME_TIME = "DanMsgRemindMeTime";
	public static final String DAN_MSG_REMIND_ME_LETER = "DanMsgRemindMeLeter";
	public static final String DAN_MSG_GOT_IT = "DanMsgGotIt";
	public static final String DAN_MSG_DONT_SHOW = "DanMsgDontShow";
	public static final String DAN_MSG_COUNTER = "DanMsgCounter";
	public static final String DAN_MSG_URL = "danmsgurl";
	public static final String DAN_MSG_TITLE = "danmsgtitle";
	public static final String DAN_MSG_TITLE_SUB = "danmsgtitlesub";

	public static final String DAN_MSG_ONOGG = "danmsgonoff";

	public static final String DAN_MSG_LETET = "danmsgleter";
	public static final String DAN_MSG_SHOWNOT = "danmsgshownot";

	private static final String LOGGED = "logged";
	private static final String ADD = "add";
	private static final String BOKMARKMGS = "bokmarkmgs";
	private static final String racyflage = "racyflage";
	public static final String fbmeme = "fbmeme";
	public static final String twmeme = "twmeme";
	public static final String YEARLYPURCHASE = "yearlypurchase";

	public static final String LIFETIMEPURCHASE = "lifetimepurchase";

	public static final String SP_1MESSAGENOTSHOW = "sp_1messagenotshow";

	private static final String ACTIONS_PREFERENCES = "ActionsPreferences";
	private static final String BOOKMARK = "Bookmark";

	public static String getDatabaseOnce(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.databaseOnce, "");
	}

	public static void setDataBaseOnce(final Context ctx, final String status) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.databaseOnce, status);
		editor.commit();
	}

	public static String getSpiffyOnOff(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.spiffyonoff, "");
	}

	public static String getSDLOnOff(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.sdlonoff, "");
	}

	public static String getActionReminder(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.actionReminder, "");
	}

	public static void setActionReminder(final Context ctx, final String action) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.actionReminder, action);
		editor.commit();
	}

	public static void setSpiffyOnoff(final Context ctx, final String action) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.spiffyonoff, action);
		editor.commit();
	}

	public static void setSDLonoff(final Context ctx, final String action) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.sdlonoff, action);
		editor.commit();
	}

	public static String getUserID(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.userId, "");
	}

	public static boolean isFirstTime(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.ISFIRSTTIME, false);
	}

	public static boolean getMemeSwipeDialog(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.memeswipedialog, false);
	}

	public static boolean getFavoSwipeDialog(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.favoswipedialog, false);
	}

	public static boolean getMotivationSwipeDialog(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.motivationswipedialog,
						false);
	}

	public static boolean getRacySwipeDialog(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.racyswipedialog, false);
	}

	public static boolean getTradingSwipeDialog(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.tradingswipedialog, false);
	}

	public static void setUserID(final Context ctx, final String usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.userId, usrId);
		editor.commit();
	}

	public static void setMemeSwipeDialog(final Context ctx, final boolean usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.memeswipedialog, usrId);
		editor.commit();
	}

	public static void setFavoSwipeDialog(final Context ctx, final boolean usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.favoswipedialog, usrId);
		editor.commit();
	}

	public static int danMsgCount(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(messageCount, 0);
	}

	public static void setDanMsgCount(final Context ctx, final int flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(messageCount, flag);
		editor.commit();
	}

	public static boolean danMsgShow(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(danMsgShow, true);
	}

	public static void setDanMsgShow(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(danMsgShow, flag);
		editor.commit();
	}

	public static void setMotivationSwipeDialog(final Context ctx,
			final boolean usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.motivationswipedialog, usrId);
		editor.commit();
	}

	public static void setRacySwipeDialog(final Context ctx, final boolean usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.racyswipedialog, usrId);
		editor.commit();
	}

	public static void setTradingSwipeDialog(final Context ctx,
			final boolean usrId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.tradingswipedialog, usrId);
		editor.commit();
	}

	public static String getPassCode(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.passCode, "");
	}

	public static void setPassCode(final Context ctx, final String passCode) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.passCode, passCode);
		editor.commit();
	}

	public static String getLockflage(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.lockStatus, "off");
	}

	public static void setLockflage(final Context ctx, final String lockSta) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.lockStatus, lockSta);
		editor.commit();
	}

	public static String getToggleStatus(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.toggleStatus, "");
	}

	public static void setToggleStatus(final Context ctx, final String toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.toggleStatus, toggle);
		editor.commit();
	}

	public static boolean getUpcomingStatus(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.upcomingStatus, false);
	}

	public static void setFirstTime(final Context ctx, final boolean toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.ISFIRSTTIME, toggle);
		editor.commit();
	}

	public static void setUpcomingStatus(final Context ctx, final boolean toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.upcomingStatus, toggle);
		editor.commit();
	}

	public static int getAllMemes(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.allmemes, 0);
	}

	public static void setAllMemes(final Context ctx, final int toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.allmemes, toggle);
		editor.commit();
	}

	public static int getSpiffy(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.SPIFFYALL, 0);
	}

	public static void setSpiffy(final Context ctx, final int toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.SPIFFYALL, toggle);
		editor.commit();
	}

	public static int getMotivationMemes(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.motivationalmemes, 0);
	}

	public static void setMotivationalMemes(final Context ctx, final int toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.motivationalmemes, toggle);
		editor.commit();
	}

	public static int getRacyMemes(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.racymemes, 0);
	}

	public static void setRacyMemes(final Context ctx, final int toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.racymemes, toggle);
		editor.commit();
	}

	public static int getMemeViewpagerPosition(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.memeViewpagerPosition, 0);
	}

	public static void setMemeViewpagerPosition(final Context ctx,
			final int toggle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.memeViewpagerPosition, toggle);
		editor.commit();
	}

	public static void setAppId(final Context ctx, final String appId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.appId, appId);
		editor.commit();
	}

	public static String getAppId(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.appId, "");
	}

	public static void setAppIcon(final Context ctx, final String appIcon) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.appIcon, appIcon);
		editor.commit();
	}

	public static String getAppIcon(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.appIcon, "");
	}

	public static void setUpcomingAds(final Context ctx, final boolean appIcon) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.upcomingAdsLoad, appIcon);
		editor.commit();
	}

	public static boolean getUpcomingAds(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.upcomingAdsLoad, true);
	}

	public static void setAppLink(final Context ctx, final String appLink) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.appLink, appLink);
		editor.commit();
	}

	public static String getAppLink(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.appLink, "");
	}

	public static void setUsingskip(final Context ctx, final boolean skip) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.usingskip, skip);
		editor.commit();
	}

	public static boolean getUsingskip(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.usingskip, false);
	}

	public static int getMotiViewpagerPosition(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.motiViewpagerPosition, 0);
	}

	public static void setMotiViewpagerPosition(final Context ctx,
			final int motiposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.motiViewpagerPosition,
				motiposition);
		editor.commit();
	}

	public static int getRacyViewpagerPosition(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.racyViewpagerPosition, 0);
	}

	public static void setRacyViewpagerPosition(final Context ctx,
			final int racyposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.racyViewpagerPosition,
				racyposition);
		editor.commit();
	}

	public static int getTrendViewpagerPosition(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.trendViewpagerPosition, 0);
	}

	public static void setTrendViewpagerPosition(final Context ctx,
			final int trendyposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.trendViewpagerPosition,
				trendyposition);
		editor.commit();
	}

	public static int getFevoriteViewpagerPosition(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.favorViewpagerPosition, 0);
	}

	public static void setFevoriteViewpagerPosition(final Context ctx,
			final int favoritesposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.favorViewpagerPosition,
				favoritesposition);
		editor.commit();
	}

	public static boolean getTwitterFlag(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.twitterFlag, false);
	}

	public static void setTwitterFlag(final Context ctx,
			final boolean favoritesposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.twitterFlag,
				favoritesposition);
		editor.commit();
	}

	public static boolean getShortCutFlag(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.shortCutFlag, true);
	}

	public static void setShortCutFlag(final Context ctx,
			final boolean favoritesposition) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.shortCutFlag,
				favoritesposition);
		editor.commit();
	}

	public static boolean isOnline(final Context ctx) {
		final ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return ni.isConnectedOrConnecting();
		} else {
			return false;
		}
	}

	public static void setShowRateAppDialog(Context con, boolean showRateApp) {
		final SharedPreferences prefs = con.getSharedPreferences(
				SharedPreferencesHelper.RATE_APP_STR, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SHOW_RATE_APP, showRateApp);
		editor.commit();
	}

	public static boolean getshowRateAppDialog(Context con) {
//		return con.getSharedPreferences(SharedPreferencesHelper.RATE_APP_STR,
//				Context.MODE_PRIVATE).getBoolean(
//				SharedPreferencesHelper.SHOW_RATE_APP, true);
		return false;
	}

	public static void setLoginCount(Context con, int count) {
		final SharedPreferences prefs = con.getSharedPreferences(
				SharedPreferencesHelper.RATE_APP_STR, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.LOGIN_NUMBER, count);
		editor.commit();
	}

	public static int getLoginCount(Context con) {
		return con.getSharedPreferences(SharedPreferencesHelper.RATE_APP_STR,
				Context.MODE_PRIVATE).getInt(
				SharedPreferencesHelper.LOGIN_NUMBER, 0);
	}

	public static void setRateAppLeter(Context con, boolean value) {
		final SharedPreferences prefs = con.getSharedPreferences(RATE_APP_STR,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(RATE_APP_LETER, value);
		editor.commit();
	}

	public static boolean getRateAppLeter(Context con) {
//		return con.getSharedPreferences(RATE_APP_STR, Context.MODE_PRIVATE)
//				.getBoolean(RATE_APP_LETER, false);
		return true;
	}

	public static void setRateAppLeterTime(Context context,
			long rateAppLeterTime) {
		final SharedPreferences prefs = context.getSharedPreferences(
				RATE_APP_STR, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putLong(RATE_APP_LETER_TIME, rateAppLeterTime);
		editor.commit();
	}

	public static long getRateAppLeterTime(Context context) {
//		return context.getSharedPreferences(RATE_APP_STR, Context.MODE_PRIVATE)
//				.getLong(RATE_APP_LETER_TIME, 0);
		return 0;
	}

	public static boolean isLoggedIn(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(LOGGED, false);
	}

	public static void setLogin(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(LOGGED, flag);
		editor.commit();
	}

	public static boolean isAddDisable(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(ADD, false);
	}

	public static void setAddDisable(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(ADD, flag);
		editor.commit();
	}

	public static String getLaunchTime(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.LAUNCHTIME, "");
	}

	public static void setLaunchTime(final Context ctx, final String launchtime) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);

		print.message("First time ", launchtime);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.LAUNCHTIME, launchtime);
		editor.commit();
	}

	// public static void setMessageLeter(Context con, boolean value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putBoolean(REMIND_ME_LETTER_MESSAGE, value);
	// }
	//
	// public static boolean getMessageLeter(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getBoolean(REMIND_ME_LETTER_MESSAGE, false);
	// }
	//
	// public static void setDenMsgUrl(Context con, String value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putString(DEN_MSG_URL, value);
	// }
	//
	// public static String getDenMsgUrl(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getString(DEN_MSG_URL, "");
	// }
	//
	// public static void setDenMsgTitle(Context con, String value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putString(DEN_MSG_TITLE, value);
	// }
	//
	// public static String getDenMsgTitle(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getString(DEN_MSG_TITLE, "");
	// }
	//
	// public static void setADV_URL(Context con, String value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putString(URL_ADVERTISE_DAN, value);
	// }
	//
	// public static String getADV_URL(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getString(URL_ADVERTISE_DAN, "");
	// }
	//
	// public static void setDenMsgSubTitle(Context con, String value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putString(DEN_MSG_SUB_TITLE, value);
	// }
	//
	// public static String getDenMsgSubTitle(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getString(DEN_MSG_SUB_TITLE, "");
	// }
	//
	// public static void setMsgnotShow(Context con, boolean value) {
	// final SharedPreferences prefs = con.getSharedPreferences(DEN_MEASSAGE,
	// Context.MODE_PRIVATE);
	// final Editor editor = prefs.edit();
	// editor.putBoolean(DEN_MSG_NOT_SHOW, value);
	// }
	//
	// public static boolean getMsgnotShow(Context con) {
	// return con.getSharedPreferences(DEN_MEASSAGE, Context.MODE_PRIVATE)
	// .getBoolean(DEN_MSG_NOT_SHOW, false);
	// }

	public static void setDanMsgCounter(Context context, int counter) {
		final SharedPreferences prefs = context.getSharedPreferences(DAN_MSG,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(DAN_MSG_COUNTER, counter);
		editor.commit();
	}

	public static int getDanMsgCounter(Context context) {
		return context.getSharedPreferences(DAN_MSG, Context.MODE_PRIVATE)
				.getInt(DAN_MSG_COUNTER, 0);
	}

	public static String getDanMsgUrl(final Context ctx) {
		return ctx.getSharedPreferences(SharedPreferencesHelper.DAN_MSG,
				Context.MODE_PRIVATE).getString(
				SharedPreferencesHelper.DAN_MSG_URL, "");
	}

	public static void setDanMsgUrl(final Context ctx, final String msgurl) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.DAN_MSG, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.DAN_MSG_URL, msgurl);
		editor.commit();
	}

	public static String getDanMsgTitle(final Context ctx) {
		return ctx.getSharedPreferences(SharedPreferencesHelper.DAN_MSG,
				Context.MODE_PRIVATE).getString(
				SharedPreferencesHelper.DAN_MSG_TITLE, "");
	}

	public static void setDanMsgTitle(final Context ctx, final String msgtitle) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.DAN_MSG, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.DAN_MSG_TITLE, msgtitle);
		editor.commit();
	}

	public static String getDanMsgSubTitle(final Context ctx) {
		return ctx.getSharedPreferences(SharedPreferencesHelper.DAN_MSG,
				Context.MODE_PRIVATE).getString(
				SharedPreferencesHelper.DAN_MSG_TITLE_SUB, "");
	}

	public static void setDanMsgSubTitle(final Context ctx,
			final String msgsubtitle) {

		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.DAN_MSG, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.DAN_MSG_TITLE_SUB, msgsubtitle);
		editor.commit();
	}

	public static String getDanMsgOnOff(final Context ctx) {
		return ctx.getSharedPreferences(SharedPreferencesHelper.DAN_MSG,
				Context.MODE_PRIVATE).getString(
				SharedPreferencesHelper.DAN_MSG_ONOGG, "");
	}

	public static void setDanMsgMsgOnOff(final Context ctx,
			final String msgonoff) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.DAN_MSG, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.DAN_MSG_ONOGG, msgonoff);
		editor.commit();
	}

	public static void setDanMsgLastRemindMeTime(Context context,
			long remindMeTime) {
		final SharedPreferences prefs = context.getSharedPreferences(DAN_MSG,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putLong(DAN_MSG_REMIND_ME_TIME, remindMeTime);
		editor.commit();
	}

	public static long getDanMsgLastRemindMeTime(Context context) {
		return context.getSharedPreferences(DAN_MSG, Context.MODE_PRIVATE)
				.getLong(DAN_MSG_REMIND_ME_TIME, 0);
	}

	public static void setDanMsgRemindMeLeter(Context context, boolean value) {
		final SharedPreferences prefs = context.getSharedPreferences(DAN_MSG,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(DAN_MSG_REMIND_ME_LETER, value);
		editor.commit();
	}

	public static boolean getDanMsgRemindMeLeter(Context context) {
		return context.getSharedPreferences(DAN_MSG, Context.MODE_PRIVATE)
				.getBoolean(DAN_MSG_REMIND_ME_LETER, false);
	}

	public static void setDanMsgDontShow(Context context, boolean value) {
		final SharedPreferences prefs = context.getSharedPreferences(DAN_MSG,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(DAN_MSG_DONT_SHOW, value);
		editor.commit();
	}

	public static boolean getDanMsgDontShow(Context context) {
		return context.getSharedPreferences(DAN_MSG, Context.MODE_PRIVATE)
				.getBoolean(DAN_MSG_DONT_SHOW, false);
	}

	public static void setDanMsgGotIt(Context context, boolean value) {
		final SharedPreferences prefs = context.getSharedPreferences(DAN_MSG,
				Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(DAN_MSG_GOT_IT, value);
		editor.commit();
	}

	public static boolean getDanMsgGotIt(Context context) {
		return context.getSharedPreferences(DAN_MSG, Context.MODE_PRIVATE)
				.getBoolean(DAN_MSG_GOT_IT, false);
	}

	public static void setMsgShowLeter(final Context ctx, final boolean msgleter) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.DAN_MSG_LETET, msgleter);
		editor.commit();
	}

	public static boolean getMsgShowLeter(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.DAN_MSG_LETET, false);
	}

	public static void setMsgShowNot(final Context ctx, final boolean msgshownot) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.DAN_MSG_SHOWNOT, msgshownot);
		editor.commit();
	}

	public static boolean getMsgShowNot(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.DAN_MSG_SHOWNOT, false);
	}

	public static void setTotalMyFavNumber(final Context ctx, String number) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.TOTAL_NUMBER, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.MY_FAVORITE_NUMBER, number);
		editor.commit();
	}

	public static String getTotalMyFavNumber(final Context ctx) {
		return ctx.getSharedPreferences(SharedPreferencesHelper.TOTAL_NUMBER,
				Context.MODE_PRIVATE).getString(
				SharedPreferencesHelper.MY_FAVORITE_NUMBER, "0");
	}

	public static void setShowAllMemeNotify(Context context, boolean isNotify) {
		final SharedPreferences prefs = context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SHOW_ALL_MEME_NOTIFY,
				isNotify);
		editor.commit();
	}

	public static boolean getShowAllMemeNotify(Context context) {
		return context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE).getBoolean(
						SharedPreferencesHelper.SHOW_ALL_MEME_NOTIFY, true);
	}

	public static void setShowMotiveMemeNotify(Context context, boolean isNotify) {
		final SharedPreferences prefs = context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SHOW_MOTIVE_MEME_NOTIFY,
				isNotify);
		editor.commit();
	}

	public static boolean getShowMotiveMemeNotify(Context context) {
		return context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE).getBoolean(
						SharedPreferencesHelper.SHOW_MOTIVE_MEME_NOTIFY, true);
	}

	public static void setShowRacyMemeNotify(Context context, boolean isNotify) {
		final SharedPreferences prefs = context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SHOW_RACY_MEME_NOTIFY,
				isNotify);
		editor.commit();
	}

	public static boolean getShowRacyMemeNotify(Context context) {
		return context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE).getBoolean(
						SharedPreferencesHelper.SHOW_RACY_MEME_NOTIFY, true);
	}

	public static void setShowSpiffyNotify(Context context, boolean isNotify) {
		final SharedPreferences prefs = context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SHOW_SPIFFY_NOTIFY, isNotify);
		editor.commit();
	}

	public static boolean getShowSpiffyNotify(Context context) {
		return context
				.getSharedPreferences(
						SharedPreferencesHelper.SHOW_NOTIFICATION,
						Context.MODE_PRIVATE).getBoolean(
						SharedPreferencesHelper.SHOW_SPIFFY_NOTIFY, true);
	}

	public static void setFbmeme(final Context ctx, final boolean fb) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.fbmeme, fb);
		editor.commit();
	}

	public static boolean getFbmeme(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.fbmeme, false);
	}

	public static void setTwittermeme(final Context ctx, final boolean fb) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.twmeme, fb);
		editor.commit();
	}

	public static boolean getTwittermeme(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.twmeme, false);
	}

	public static void setYearlyPurchase(final Context ctx, final boolean yearly) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.YEARLYPURCHASE, yearly);
		editor.commit();
	}

	public static boolean getYearlyPurchase(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.YEARLYPURCHASE, false);
	}

	public static void setLifeTimePurchase(final Context ctx,
			final boolean lifeTime) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.LIFETIMEPURCHASE, lifeTime);
		editor.commit();
	}

	public static boolean getLifeTimePurchase(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.LIFETIMEPURCHASE, false);
	}

	public static void setSp1MessagenotShow(final Context ctx,
			final boolean firstmessage) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.SP_1MESSAGENOTSHOW,
				firstmessage);
		editor.commit();
	}

	public static boolean getSp1MessagenotShow(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.SP_1MESSAGENOTSHOW, false);
	}

	public static int getBookmark(Context context) {
		return context.getSharedPreferences(ACTIONS_PREFERENCES,
				Context.MODE_PRIVATE).getInt(BOOKMARK, -1);
	}

	public static void setBookmark(Context context, int pos) {
		final SharedPreferences prefs = context.getSharedPreferences(
				ACTIONS_PREFERENCES, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(BOOKMARK, pos);
		editor.commit();
	}

	public static boolean getBookMarkMessate(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(BOKMARKMGS, false);
	}

	public static void setBookMarkMessate(final Context ctx,
			final boolean bookmark) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(BOKMARKMGS, bookmark);
		editor.commit();
	}

	public static boolean getRacyflage(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(racyflage, false);
	}

	public static void setRacyflage(final Context ctx, final boolean racy) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(racyflage, racy);
		editor.commit();
	}

}
