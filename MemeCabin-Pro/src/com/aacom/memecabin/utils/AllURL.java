package com.aacom.memecabin.utils;

import java.util.Vector;

import android.annotation.SuppressLint;

import com.aapbd.utils.network.KeyValue;
import com.aapbd.utils.network.UrlUtils;
import com.loopj.android.http.RequestParams;

@SuppressLint("DefaultLocale")
public class AllURL {
	
	
	
	public static String termURL="https://www.singledadlaughing.com/single-dad-laughing-app-tos";
	public static String privacyURL="https://www.singledadlaughing.com/single-dad-laughing-app-privacy-policy/";


	private static String getcommonURLWithParamAndAction(String action,
			Vector<KeyValue> params) {

		if (params == null || params.size() == 0) {
			return BaseURL.HTTP + action;
		} else {
			String pString = "";

			for (final KeyValue obj : params) {

				pString += obj.getKey().trim() + "=" + obj.getValue().trim()
						+ "&";
			}

			if (pString.endsWith("&")) {
				pString = pString.subSequence(0, pString.length() - 1)
						.toString();
			}

			return BaseURL.HTTP + action + "?" + UrlUtils.encode(pString);
		}
	}

	public static String getLogin(String email, String password, String type) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("email", email));
		temp.addElement(new KeyValue("password", password));
		temp.addElement(new KeyValue("type", type));
		return getcommonURLWithParamAndAction("signin", temp);

	}

	public static String criticalMessageFromDan() {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		// temp.addElement(new KeyValue("type", type));
		// temp.addElement(new KeyValue("date", date));
		return getcommonURLWithParamAndAction("DansPopup", temp);

	}

	public static String getSamsungMsgURL() {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		// temp.addElement(new KeyValue("type", type));
		// temp.addElement(new KeyValue("date", date));
		return getcommonURLWithParamAndAction("samsungmessage", temp);

	}

	public static String getRegister(String email, String password, String type) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("email", email));
		temp.addElement(new KeyValue("password", password));
		temp.addElement(new KeyValue("type", type));
		return getcommonURLWithParamAndAction("signup", temp);

	}

	public static String getNewPassword(String userID, String old_password,
			String new_password) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("userID", userID));
		temp.addElement(new KeyValue("old_password", old_password));
		temp.addElement(new KeyValue("new_password", new_password));
		return getcommonURLWithParamAndAction("changePassord", temp);

	}

	public static String getLikeUpdate(String memeid, String userID) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("memeid", memeid));
		temp.addElement(new KeyValue("userID", userID));
		return getcommonURLWithParamAndAction("updateMemestotalLike", temp);

	}

	public static String getDisLike(String memeid, String userID) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("memeid", memeid));
		temp.addElement(new KeyValue("userID", userID));
		return getcommonURLWithParamAndAction("updateMemesdisLike", temp);

	}

	// public static String getTredingList(String days) {
	//
	// final Vector<KeyValue> temp = new Vector<KeyValue>();
	// temp.addElement(new KeyValue("days", days));
	// return getcommonURLWithParamAndAction("topMemesList", temp);
	//
	// }

	public static String getUpcomingApps(String type) {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("type", type));
		return getcommonURLWithParamAndAction("upComingApps", temp);
	}

	public static String getPassword(String email) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("email", email));
		return getcommonURLWithParamAndAction("forgotPassword", temp);

	}

	public static String getReloadinfo(String memeId) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("memeId", memeId));
		return getcommonURLWithParamAndAction("myFavouriteMemesList", temp);

	}

	public static String setRacyMemes() {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		// temp.addElement(new KeyValue("userfile", AppConstant.racyImageUrl));
		// return getcommonURLWithParamAndAction("uploadRacyMemes", temp);
		return getcommonURLWithParamAndAction("uploadtest", temp);
	}

	// Favorites issues

	public static String makeFavorites(String userID, String memeid) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("userID", userID));
		temp.addElement(new KeyValue("memeid", memeid));
		return getcommonURLWithParamAndAction("makeFavourite", temp);
	}

	public static String unFavorites(String userID, String memeid) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("userID", userID));
		temp.addElement(new KeyValue("memeid", memeid));
		return getcommonURLWithParamAndAction("makeUnFavourite", temp);
	}

	public static String listFavorites(String userID) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("userID", userID));
		return getcommonURLWithParamAndAction("myFavouriteMemeList", temp);
	}

	public static String getUpcomingApp(String type, String date, String userID) {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("type", type));
		temp.addElement(new KeyValue("date", date));
		temp.addElement(new KeyValue("userID", userID));
		return getcommonURLWithParamAndAction("upComingApps", temp);

	}

	public static String getmessageFromDen() {

		final Vector<KeyValue> temp = new Vector<KeyValue>();
		// temp.addElement(new KeyValue("type", type));
		// temp.addElement(new KeyValue("date", date));
		return getcommonURLWithParamAndAction("DansMessage", temp);

	}

	// End favorites

	// RequestParams params = new RequestParams();

	/*
	 * post sample using loopj
	 */
	public static RequestParams getMotivational(String value, String data) {

		final RequestParams params = new RequestParams();
		params.put("key", value);
		params.put("more", data);
		return params;

	}

	// public static String getAllMemeUrl(String userID) {
	// final Vector<KeyValue> temp = new Vector<KeyValue>();
	// temp.addElement(new KeyValue("userID", userID));
	// return getcommonURLWithParamAndAction("everyoneMemesList", temp);
	// }
	//
	// public static String getMotivInspMemeUrl(String userID) {
	// final Vector<KeyValue> temp = new Vector<KeyValue>();
	// temp.addElement(new KeyValue("userID", userID));
	// return getcommonURLWithParamAndAction("motivationalMemesList", temp);
	// }
	//
	// public static String getRacyMemeUrl(String userID) {
	// final Vector<KeyValue> temp = new Vector<KeyValue>();
	// temp.addElement(new KeyValue("userID", userID));
	// return getcommonURLWithParamAndAction("racyMemesList", temp);
	// }
	//
	public static String getTendingMemeUrl(String userID, String days) {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("userID", userID));
		temp.addElement(new KeyValue("days", days));
		return getcommonURLWithParamAndAction("topMemesList", temp);
	}

	public static String getMemeDetailUrl(String memeId) {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("memeId", memeId));
		return getcommonURLWithParamAndAction("getFavorites", temp);
	}

	public static String getReportUrl(String email, String name, String memeid,
			String text) {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("email", email));
		temp.addElement(new KeyValue("name", name));
		temp.addElement(new KeyValue("memeid", memeid));
		temp.addElement(new KeyValue("text", text));
		return getcommonURLWithParamAndAction("memereport", temp);
	}

	public static String getVerificationLink(String email) {
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		// resendVerification?email=amarkotha366@gmail.com

		temp.addElement(new KeyValue("email", email));
		return getcommonURLWithParamAndAction("resendVerification", temp);
	}

	// http://54.69.158.41/api/SpiffyMemesListNew?userID=277

	// public static String getSpecityGIF(String userID) {
	// final Vector<KeyValue> temp = new Vector<KeyValue>();
	// temp.addElement(new KeyValue("userID", userID+""));
	//
	// return getcommonURLWithParamAndAction("SpiffyMemesListNew", temp);
	// }

	public static String getMotivationalByPage(String userID, int currentPage,
			int paginationItemCount) {
		// TODO Auto-generated method stub
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("page_number", currentPage + ""));
		temp.addElement(new KeyValue("item_count", paginationItemCount + ""));
		temp.addElement(new KeyValue("userID", userID + ""));

		return getcommonURLWithParamAndAction("motivationalMemesListV2", temp);
	}

	public static String getRecyMemeByPage(String userID, int currentPage,
			int paginationItemCount) {
		// TODO Auto-generated method stub
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("page_number", currentPage + ""));
		temp.addElement(new KeyValue("item_count", paginationItemCount + ""));
		temp.addElement(new KeyValue("userID", userID + ""));

		return getcommonURLWithParamAndAction("racyMemesListV2", temp);
	}

	public static String getspiffyMemesByPage(String userID, int currentPage,
			int paginationItemCount) {
		// TODO Auto-generated method stub
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("page_number", currentPage + ""));
		temp.addElement(new KeyValue("item_count", paginationItemCount + ""));
		temp.addElement(new KeyValue("userID", userID + ""));

		return getcommonURLWithParamAndAction("spiffyMemesListNewV2", temp);
	}

	public static String geteveryoneMemesByPage(String userID, int currentPage,
			int paginationItemCount) {
		// TODO Auto-generated method stub
		final Vector<KeyValue> temp = new Vector<KeyValue>();
		temp.addElement(new KeyValue("page_number", currentPage + ""));
		temp.addElement(new KeyValue("item_count", paginationItemCount + ""));
		temp.addElement(new KeyValue("userID", userID + ""));

		return getcommonURLWithParamAndAction("everyoneMemesListV2", temp);
	}

}