package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.TopMemesInfo;

public class AllTopMemesInfo {

	public static Vector<TopMemesInfo> allTopMemesInfo = new Vector<TopMemesInfo>();

	public static Vector<TopMemesInfo> getAllTopMemesInfo() {
		return allTopMemesInfo;
	}

	public static void setAllTopMemesInfo(Vector<TopMemesInfo> allTopMemesInfo) {
		AllTopMemesInfo.allTopMemesInfo = allTopMemesInfo;
	}

	public static TopMemesInfo getTopMemesInfo(int pos) {
		return allTopMemesInfo.elementAt(pos);
	}

	public static void setTopMemesInfo(TopMemesInfo _allTopMemesInfo) {
		AllTopMemesInfo.allTopMemesInfo.addElement(_allTopMemesInfo);
	}

	public static void removeAllTopMemesInfo() {
		AllTopMemesInfo.allTopMemesInfo.removeAllElements();
	}

}
