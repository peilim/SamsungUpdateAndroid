package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.ImageInfo;

public class AllReload {

	public static Vector<ImageInfo> allReload = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllReload() {
		return allReload;
	}

	public static void setAllReload(Vector<ImageInfo> allReload) {
		AllReload.allReload = allReload;
	}

	public static ImageInfo getReload(int pos) {
		return allReload.elementAt(pos);
	}

	public static void setReload(ImageInfo _allReload) {
		AllReload.allReload.addElement(_allReload);
	}

	public static void removeAllReload() {
		AllReload.allReload.removeAllElements();
	}

}
