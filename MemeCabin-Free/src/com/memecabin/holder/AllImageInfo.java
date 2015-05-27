package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.ImageInfo;

public class AllImageInfo {

	public static Vector<ImageInfo> allImageInfo = new Vector<ImageInfo>();

	public static Vector<ImageInfo> getAllImageInfo() {
		return allImageInfo;
	}

	public static void setAllImageInfo(Vector<ImageInfo> allImageInfo) {
		AllImageInfo.allImageInfo = allImageInfo;
	}

	public static ImageInfo getImageInfo(int pos) {
		return allImageInfo.elementAt(pos);
	}

	public static void setImageInfo(ImageInfo _allImageInfo) {
		AllImageInfo.allImageInfo.addElement(_allImageInfo);
	}

	public static void removeAllImageInfo() {
		AllImageInfo.allImageInfo.removeAllElements();
	}

	public static void appendAll(Vector<ImageInfo> meme) {
		// TODO Auto-generated method stub

		AllImageInfo.allImageInfo.addAll(meme);
	}

}
