package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.ImageInfo;

public class AllSpicyInfo {

	public static Vector<ImageInfo> allSpicyInfo = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllSpicyInfo() {
		return allSpicyInfo;
	}

	public static void setAllSpicyInfo(Vector<ImageInfo> allSpicyInfo) {
		AllSpicyInfo.allSpicyInfo = allSpicyInfo;
	}

	public static ImageInfo getImageInfo(int pos) {
		return allSpicyInfo.elementAt(pos);
	}

	public static void setImageInfo(ImageInfo _allSpicyInfo) {
		AllSpicyInfo.allSpicyInfo.addElement(_allSpicyInfo);
	}

	public static void removeAllSpicyInfo() {
		AllSpicyInfo.allSpicyInfo.removeAllElements();
	}

	public static void appendAll(Vector<ImageInfo> _allMotivational) {
		allSpicyInfo.addAll(_allMotivational);
	}

}
