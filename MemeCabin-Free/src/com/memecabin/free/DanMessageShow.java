/**
 * 
 */
package com.memecabin.free;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.utils.nagivation.StartActivity;
import com.memecabin.model.Record;

public class DanMessageShow {

	private final Context context;
	private Dialog dd;

	Button linkbunclick, nextimebtnclick, notshowbtnagain;
	TextView titleMessage, bodymessage;
	private Record record = null;

	public DanMessageShow(Context context) {
		this.context = context;
	}

	public void openDanMessageShow() {
		dd = new Dialog(context);
		dd.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dd.setContentView(R.layout.initialmessage);
		dd.show();

		titleMessage = (TextView) dd.findViewById(R.id.titleMessage);
		bodymessage = (TextView) dd.findViewById(R.id.bodymessage);

		linkbunclick = (Button) dd.findViewById(R.id.linkbunclick);
		nextimebtnclick = (Button) dd.findViewById(R.id.nextimebtnclick);
		notshowbtnagain = (Button) dd.findViewById(R.id.notshowbtnagain);

		try {

			record = AppConstant.criticMessage.getRecord().get(0);
			titleMessage.setText(record.getTitle());

			bodymessage.setText(record.getMessage());

			linkbunclick.setText(record.getTop_button_text());
			nextimebtnclick.setText(record.getMiddle_button_text());
			notshowbtnagain.setText(record.getBotton_button_text());

			if (record.getUrl().trim().equalsIgnoreCase("")) {
				linkbunclick.setVisibility(View.GONE);

			} else {
				linkbunclick.setVisibility(View.VISIBLE);
			}

		} catch (final Exception e) {
			dd.dismiss();
		}

		linkbunclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				StartActivity.toebsite(context, record.getUrl().trim());

				dd.dismiss();

			}
		});
		nextimebtnclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dd.dismiss();

			}
		});
		notshowbtnagain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * add counter logic
				 */

				SharedPreferencesHelper.setDanMsgShow(context, false);

				dd.dismiss();

			}
		});
	}

}
