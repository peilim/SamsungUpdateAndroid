package com.memecabin.holder;

import java.util.Vector;

import com.memecabin.model.DashText;

public class AllDashText {

	public static Vector<DashText> alldashtext = new Vector<DashText>();

	public static Vector<DashText> getAlldashtext() {
		return alldashtext;
	}

	public static void setAlldashtext(Vector<DashText> alldashtext) {
		AllDashText.alldashtext = alldashtext;
	}

	public static void removeAll() {
		AllDashText.alldashtext.removeAllElements();
	}

	public static void addDashtext(DashText dashtext) {
		AllDashText.alldashtext.addElement(dashtext);
	}

}
