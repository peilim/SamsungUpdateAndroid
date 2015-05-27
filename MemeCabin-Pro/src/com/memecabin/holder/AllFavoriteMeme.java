/**
 *
 */
package com.memecabin.holder;

import java.util.Vector;

import android.util.Log;

import com.memecabin.model.ImageInfo;

/**
 * @author Ashraful
 * 
 */
public class AllFavoriteMeme {

	public static Vector<ImageInfo> allImageInfo = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllImageInfo() {
		/*
		 * final Vector<ImageInfo> im = new Vector<ImageInfo>();
		 * im.removeAllElements(); for (final ImageInfo a : allImageInfo) { if
		 * (!a.getFavorite().equalsIgnoreCase("1")) { im.addElement(a); } }
		 * 
		 * return im;
		 */

		if (allImageInfo != null && allImageInfo.size() > 0) {
			return allImageInfo;
		}
		return new Vector<ImageInfo>();
	}

	public static void setAllImageInfo(Vector<ImageInfo> allImageInfo) {
		AllFavoriteMeme.allImageInfo = allImageInfo;
	}

	public static ImageInfo getImageInfo(int pos) {
		return allImageInfo.elementAt(pos);
	}

	public static void setImageInfo(ImageInfo _allImageInfo) {
		AllFavoriteMeme.allImageInfo.addElement(_allImageInfo);
	}

	public static void removeAllImageInfo() {
		AllFavoriteMeme.allImageInfo.removeAllElements();
	}

	public static void removeFromID(ImageInfo ii) {

		Log.e("Before size ", ">" + allImageInfo.size());
		allImageInfo.remove(ii);
		Log.e("After size ", ">" + allImageInfo.size());

		/*
		 * public static Vector<ImageInfo> allFavoriteMeme = new
		 * Vector<ImageInfo>();
		 * 
		 * public static Vector<ImageInfo> getAllFavoriteMeme() { return
		 * allFavoriteMeme; }
		 * 
		 * public static void setAllFavoriteMeme(Vector<ImageInfo>
		 * allFavoriteMeme) { AllFavoriteMeme.allFavoriteMeme = allFavoriteMeme;
		 * }
		 * 
		 * public static ImageInfo getFavoriteMeme(int pos) { return
		 * allFavoriteMeme.elementAt(pos); }
		 * 
		 * public static void setFavoriteMeme(ImageInfo _allFavoriteMeme) {
		 * AllFavoriteMeme.allFavoriteMeme.addElement(_allFavoriteMeme); }
		 * 
		 * public static void removeAllFavoriteMeme() {
		 * AllFavoriteMeme.allFavoriteMeme.removeAllElements(); }
		 */

	}

	public static void appendAll(Vector<ImageInfo> _imageList) {
		allImageInfo.addAll(_imageList);
	}
}
