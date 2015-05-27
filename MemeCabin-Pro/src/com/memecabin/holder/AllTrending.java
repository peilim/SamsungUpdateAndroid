package com.memecabin.holder;

import java.util.Vector;

import com.aapbd.utils.print.print;
import com.memecabin.model.ImageInfo;

public class AllTrending {

	public static Vector<ImageInfo> allTrending = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllTrending() {
		return allTrending;
	}

	public static void setAllTrending(Vector<ImageInfo> allTrending) {
		AllTrending.allTrending = allTrending;
	}

	public static ImageInfo getTrending(int pos) {
		return allTrending.elementAt(pos);
	}

	public static void setTrending(ImageInfo _allTrending) {
		AllTrending.allTrending.addElement(_allTrending);
	}

	public static void removeAllTrending() {
		AllTrending.allTrending.removeAllElements();
	}

	public static void getAllTrendingExceptRacy() {

		final Vector<ImageInfo> temp = new Vector<ImageInfo>();

		for (final ImageInfo img : allTrending) {

			if (img.getImageCategory().trim().equalsIgnoreCase("3")) {
				print.message("Ignore racy meme at Trending list");
				continue;
			}

			temp.addElement(img);
		}
		/*
		 * replace old list
		 */

		setAllTrending(temp);
	}

	public static void appendAll(Vector<ImageInfo> _allMotivational) {
		allTrending.addAll(_allMotivational);
	}

}
