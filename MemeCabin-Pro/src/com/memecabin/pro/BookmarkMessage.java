/**
 * 
 */
package com.memecabin.pro;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.memecabin.pro.R;

public class BookmarkMessage {

	private final Context context;
	private Dialog dd;

	public BookmarkMessage(Context context) {
		this.context = context;
	}

	public void openBookmarkMessage() {
		dd = new Dialog(context);
		dd.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dd.setContentView(R.layout.bookmark);
		dd.show();

		try {

		} catch (final Exception e) {
			dd.dismiss();
		}

	}

}
