package com.aacom.memecabin.utils;

import com.aapbd.utils.network.UrlUtils;

public class BaseURL {

	public static String HTTP = "http://thememecabin.com/api/";
	public static String ImagePath = "http://thememecabin.com/uploads/";

	/**
	 * @return the hTTP
	 */
	public static String makeHTTPURL(String param) {
		return BaseURL.HTTP + UrlUtils.encode(param);
	}

	/**
	 * @param hTTP
	 *            the hTTP to set
	 */
	public static void setHTTP(final String hTTP) {
		BaseURL.HTTP = hTTP;
	}

}
