package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.ImageInfo;

public class AllRacy {

	public static Vector<ImageInfo> allRacy = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllRacy() {
		return allRacy;
	}

	public static void setAllRacy(Vector<ImageInfo> allRacy) {
		AllRacy.allRacy = allRacy;
	}

	public static ImageInfo getRacy(int pos) {
		return allRacy.elementAt(pos);
	}

	public static void setRacy(ImageInfo _allRacy) {
		AllRacy.allRacy.addElement(_allRacy);
	}

	public static void removeAllRacy() {
		AllRacy.allRacy.removeAllElements();
	}

	public static void appendAll(Vector<ImageInfo> _allRacy) {
		allRacy.addAll(_allRacy);
	}

}
