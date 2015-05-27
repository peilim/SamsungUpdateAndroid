package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.ImageInfo;

public class AllMotivational {

	public static Vector<ImageInfo> allMotivational = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllMotivational() {
		return allMotivational;
	}

	public static void setAllMotivational(Vector<ImageInfo> allMotivational) {
		AllMotivational.allMotivational = allMotivational;
	}

	public static ImageInfo getMotivational(int pos) {
		return allMotivational.elementAt(pos);
	}

	public static void setMotivational(ImageInfo _allMotivational) {
		AllMotivational.allMotivational.addElement(_allMotivational);
	}

	public static void removeAllMotivational() {
		AllMotivational.allMotivational.removeAllElements();
	}

	public static void appendAll(Vector<ImageInfo> _allMotivational) {
		allMotivational.addAll(_allMotivational);
	}

}
