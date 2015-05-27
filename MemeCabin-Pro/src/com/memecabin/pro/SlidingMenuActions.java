package com.memecabin.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.utils.view.AppRater;
import com.aapbd.utils.view.OpenAppInsideorOutside;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.memecabin.pro.R;

public class SlidingMenuActions implements OnClickListener {

	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear;
	private LinearLayout disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind;
	private LinearLayout singledateheadinglinear, yahtakeme2sateit,
			remindmenext, bahidontcare;
	Context context;
	SlidingMenu menu;
	AppRater appRater;
	Activity activity;

	public SlidingMenuActions(Context con, Activity act, SlidingMenu menu,
			RelativeLayout appRate_rl, RelativeLayout disableAdd_rl,
			RelativeLayout getSdl_rl, RelativeLayout singleFB_rl,
			RelativeLayout silgeInstagram_rl,
			RelativeLayout logoutRelativeLayout,
			RelativeLayout dialogRelativelayout,
			RelativeLayout singledadRelativelayout,
			RelativeLayout rateappRelativelayout,
			RelativeLayout fullscreenslide, RelativeLayout getnewapps,
			LinearLayout fivestarlinear, LinearLayout rmaindmelinear,
			LinearLayout noidontwannalinear, LinearLayout rateheadinglinear,
			LinearLayout disableaddheadinglinear, LinearLayout disablefor99,
			LinearLayout disableforever99, LinearLayout nahichangemind,
			LinearLayout singledateheadinglinear,
			LinearLayout yahtakeme2sateit, LinearLayout remindmenext,
			LinearLayout bahidontcare) {
		this.context = con;
		this.activity = act;
		this.menu = menu;
		this.appRate_rl = appRate_rl;
		this.disableAdd_rl = disableAdd_rl;
		this.getSdl_rl = getSdl_rl;
		this.singleFB_rl = singleFB_rl;
		this.silgeInstagram_rl = silgeInstagram_rl;
		this.logoutRelativeLayout = logoutRelativeLayout;
		this.dialogRelativelayout = dialogRelativelayout;
		this.singledadRelativelayout = singledadRelativelayout;
		this.rateappRelativelayout = rateappRelativelayout;
		this.fullscreenslide = fullscreenslide;
		this.getnewapps = getnewapps;

		this.rateheadinglinear = rateheadinglinear;
		this.fivestarlinear = fivestarlinear;
		this.rmaindmelinear = rmaindmelinear;
		this.noidontwannalinear = noidontwannalinear;

		this.disableaddheadinglinear = disableaddheadinglinear;
		this.disablefor99 = disablefor99;
		this.disableforever99 = disableforever99;
		this.nahichangemind = nahichangemind;

		this.singledateheadinglinear = singledateheadinglinear;
		this.yahtakeme2sateit = yahtakeme2sateit;
		this.remindmenext = remindmenext;
		this.bahidontcare = bahidontcare;

		initUI();
		appRater = new AppRater();

	}

	public SlidingMenuActions(Context con, SlidingMenu menu,
			RelativeLayout appRate_rl, RelativeLayout disableAdd_rl,
			RelativeLayout getSdl_rl, RelativeLayout singleFB_rl,
			RelativeLayout silgeInstagram_rl,
			RelativeLayout logoutRelativeLayout,
			RelativeLayout dialogRelativelayout,
			RelativeLayout singledadRelativelayout,
			RelativeLayout rateappRelativelayout,
			RelativeLayout fullscreenslide, RelativeLayout getnewapps,
			LinearLayout fivestarlinear, LinearLayout rmaindmelinear,
			LinearLayout noidontwannalinear, LinearLayout rateheadinglinear,
			LinearLayout disableaddheadinglinear, LinearLayout disablefor99,
			LinearLayout disableforever99, LinearLayout nahichangemind,
			LinearLayout singledateheadinglinear,
			LinearLayout yahtakeme2sateit, LinearLayout remindmenext,
			LinearLayout bahidontcare) {
		this.context = con;
		this.menu = menu;
		this.appRate_rl = appRate_rl;
		this.disableAdd_rl = disableAdd_rl;
		this.getSdl_rl = getSdl_rl;
		this.singleFB_rl = singleFB_rl;
		this.silgeInstagram_rl = silgeInstagram_rl;
		this.logoutRelativeLayout = logoutRelativeLayout;
		this.dialogRelativelayout = dialogRelativelayout;
		this.singledadRelativelayout = singledadRelativelayout;
		this.rateappRelativelayout = rateappRelativelayout;
		this.fullscreenslide = fullscreenslide;
		this.getnewapps = getnewapps;

		this.rateheadinglinear = rateheadinglinear;
		this.fivestarlinear = fivestarlinear;
		this.rmaindmelinear = rmaindmelinear;
		this.noidontwannalinear = noidontwannalinear;

		this.disableaddheadinglinear = disableaddheadinglinear;
		this.disablefor99 = disablefor99;
		this.disableforever99 = disableforever99;
		this.nahichangemind = nahichangemind;

		this.singledateheadinglinear = singledateheadinglinear;
		this.yahtakeme2sateit = yahtakeme2sateit;
		this.remindmenext = remindmenext;
		this.bahidontcare = bahidontcare;

		initUI();
		appRater = new AppRater();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		appRate_rl = (RelativeLayout) menu.findViewById(R.id.appRate_rl);
		disableAdd_rl = (RelativeLayout) menu.findViewById(R.id.disableAdd_rl);
		getSdl_rl = (RelativeLayout) menu.findViewById(R.id.getSdl_rl);
		singleFB_rl = (RelativeLayout) menu.findViewById(R.id.singleFB_rl);
		getnewapps = (RelativeLayout) menu.findViewById(R.id.getnewapps);
		singledadRelativelayout = (RelativeLayout) menu
				.findViewById(R.id.singledadRelativelayout);
		rateappRelativelayout = (RelativeLayout) menu
				.findViewById(R.id.rateappRelativelayout);
		silgeInstagram_rl = (RelativeLayout) menu
				.findViewById(R.id.silgeInstagram_rl);
		logoutRelativeLayout = (RelativeLayout) menu
				.findViewById(R.id.logoutRelativeLayout);
		dialogRelativelayout = (RelativeLayout) menu
				.findViewById(R.id.dialogRelativelayout);
		fullscreenslide = (RelativeLayout) menu
				.findViewById(R.id.fullscreenslide);

		rateheadinglinear = (LinearLayout) menu
				.findViewById(R.id.rateheadinglinear);
		fivestarlinear = (LinearLayout) menu.findViewById(R.id.fivestarlinear);
		rmaindmelinear = (LinearLayout) menu.findViewById(R.id.rmaindmelinear);
		noidontwannalinear = (LinearLayout) menu
				.findViewById(R.id.noidontwannalinear);

		disableaddheadinglinear = (LinearLayout) menu
				.findViewById(R.id.disableaddheadinglinear);
		disablefor99 = (LinearLayout) menu.findViewById(R.id.disablefor99);
		disableforever99 = (LinearLayout) menu
				.findViewById(R.id.disableforever99);
		nahichangemind = (LinearLayout) menu.findViewById(R.id.nahichangemind);

		singledateheadinglinear = (LinearLayout) menu
				.findViewById(R.id.singledateheadinglinear);
		yahtakeme2sateit = (LinearLayout) menu
				.findViewById(R.id.yahtakeme2sateit);
		remindmenext = (LinearLayout) menu.findViewById(R.id.remindmenext);
		bahidontcare = (LinearLayout) menu.findViewById(R.id.bahidontcare);

		appRate_rl.setOnClickListener(this);
		disableAdd_rl.setOnClickListener(this);
		getSdl_rl.setOnClickListener(this);
		singleFB_rl.setOnClickListener(this);
		silgeInstagram_rl.setOnClickListener(this);
		logoutRelativeLayout.setOnClickListener(this);
		singledadRelativelayout.setOnClickListener(this);
		rateappRelativelayout.setOnClickListener(this);
		fullscreenslide.setOnClickListener(this);
		getnewapps.setOnClickListener(this);

		rateheadinglinear.setOnClickListener(this);
		fivestarlinear.setOnClickListener(this);
		rmaindmelinear.setOnClickListener(this);
		noidontwannalinear.setOnClickListener(this);

		disableaddheadinglinear.setOnClickListener(this);
		nahichangemind.setOnClickListener(this);

		singledateheadinglinear.setOnClickListener(this);
		yahtakeme2sateit.setOnClickListener(this);
		remindmenext.setOnClickListener(this);
		bahidontcare.setOnClickListener(this);

		// dialogRelativelayout.setOnClickListener(this);

		/*
		 * disable menu for purchase..
		 */

		if (SharedPreferencesHelper.getLifeTimePurchase(context)) {
			/*
			 * hide purchase option if this is lifetime purchased
			 */
			disableAdd_rl.setVisibility(View.GONE);
		} else {
			disableAdd_rl.setVisibility(View.VISIBLE);

			/*
			 * hide yearly subscription if this is done already.
			 */
			if (SharedPreferencesHelper.getYearlyPurchase(context)) {
				disablefor99.setVisibility(View.GONE);
			} else {
				disablefor99.setVisibility(View.VISIBLE);

			}

		}

	}

	public void refreshAdbar() {
		try {

			if (SharedPreferencesHelper.getLifeTimePurchase(context)) {
				/*
				 * hide purchase option if this is lifetime purchased
				 */

				disableAdd_rl.setVisibility(View.GONE);
			} else {
				disableAdd_rl.setVisibility(View.VISIBLE);

				/*
				 * hide yearly subscription if this is done already.
				 */
				if (SharedPreferencesHelper.getYearlyPurchase(context)) {
					disablefor99.setVisibility(View.GONE);
				} else {
					disablefor99.setVisibility(View.VISIBLE);

				}

			}
		} catch (final Exception e) {

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.appRate_rl) {
			// AppRater.showRateDialog(context);
			// menu.toggle();

			/*By Jin
			if (dialogRelativelayout.getVisibility() == View.VISIBLE) {
				dialogRelativelayout.setVisibility(View.GONE);
			} else if (singledadRelativelayout.getVisibility() == View.VISIBLE) {
				singledadRelativelayout.setVisibility(View.GONE);
			}

			// dialogRelativelayout.setVisibility(View.GONE);
			// singledadRelativelayout.setVisibility(View.GONE);

			if (!AppConstant.clickrateapp) {
				rateappRelativelayout.setVisibility(View.VISIBLE);
				AppConstant.clickrateapp = true;
			} else {
				rateappRelativelayout.setVisibility(View.GONE);
				AppConstant.clickrateapp = false;
			}
			*/

		} else if (v.getId() == R.id.logoutRelativeLayout) {

			AppConstant.fromscreen = "main";
			// SharedPreferencesHelper.setUserID(context, "");
			SharedPreferencesHelper.setLogin(context, false);

			if (HomeActivity.getInstance() != null) {
				HomeActivity.getInstance().finish();
			}

			final Intent ii = new Intent(context, LogActivity.class);
			context.startActivity(ii);
			((Activity) context).finish();

			// ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			// | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			// act.startActivity(ii);
			// act.finish();
			// menu.toggle();
		} else if (v.getId() == R.id.disableAdd_rl) {

			if (rateappRelativelayout.getVisibility() == View.VISIBLE) {
				rateappRelativelayout.setVisibility(View.GONE);
			} else if (singledadRelativelayout.getVisibility() == View.VISIBLE) {
				singledadRelativelayout.setVisibility(View.GONE);
			}

			// rateappRelativelayout.setVisibility(View.GONE);
			// singledadRelativelayout.setVisibility(View.GONE);

			if (!AppConstant.clickflage) {
				dialogRelativelayout.setVisibility(View.VISIBLE);
				AppConstant.clickflage = true;
			} else {
				dialogRelativelayout.setVisibility(View.GONE);
				AppConstant.clickflage = false;
			}

			// menu.toggle();
		} else if (v.getId() == R.id.getSdl_rl) {

			/*
			 * if (!AppConstant.clicksigledad) {
			 * singledadRelativelayout.setVisibility(View.VISIBLE);
			 * AppConstant.clicksigledad = true; } else {
			 * singledadRelativelayout.setVisibility(View.GONE);
			 * AppConstant.clicksigledad = false; }
			 */
			final String appPackageName = context.getResources().getString(
					R.string.package_name_sdl_app);

			OpenAppInsideorOutside
					.openAppByPackageName(context, appPackageName);

			// menu.toggle();
		} else if (v.getId() == R.id.singleFB_rl) {

			// dialogRelativelayout.setVisibility(View.GONE);
			// singledadRelativelayout.setVisibility(View.GONE);
			// rateappRelativelayout.setVisibility(View.GONE);

			if (dialogRelativelayout.getVisibility() == View.VISIBLE) {
				dialogRelativelayout.setVisibility(View.GONE);
			} else if (singledadRelativelayout.getVisibility() == View.VISIBLE) {
				singledadRelativelayout.setVisibility(View.GONE);
			} else if (rateappRelativelayout.getVisibility() == View.VISIBLE) {
				rateappRelativelayout.setVisibility(View.GONE);
			}

			OpenAppInsideorOutside.openAppWithURL(context,
					"com.facebook.katana", "fb://facewebmodal/f?href="
							+ AppConstant.singledadurl,
					AppConstant.singledadurl);

			// context.startActivity(getFacebookIntent(AppConstant.singledadurl));

		} else if (v.getId() == R.id.silgeInstagram_rl) {

			// OpenAppInsideorOutside.openAppWithURL(context,
			// "com.instagram.android",
			// "instagram://user?username=danoah",AppConstant.instaramurl);
			OpenAppInsideorOutside.openAppWithURL(context,
					"com.instagram.android", "http://instagram.com/_u/danoah",
					AppConstant.instaramurl);
			// ontext.startActivity(openInstragram(AppConstant.instaramurl));

		} else if (v.getId() == R.id.fullscreenslide) {

			if (dialogRelativelayout.getVisibility() == View.VISIBLE) {
				dialogRelativelayout.setVisibility(View.GONE);
			} else if (singledadRelativelayout.getVisibility() == View.VISIBLE) {
				singledadRelativelayout.setVisibility(View.GONE);
			} else if (rateappRelativelayout.getVisibility() == View.VISIBLE) {
				rateappRelativelayout.setVisibility(View.GONE);
			}

			menu.toggle();

		} else if (v.getId() == R.id.getnewapps) {
			// Meme Cavin on Facebook

			if (dialogRelativelayout.getVisibility() == View.VISIBLE) {
				dialogRelativelayout.setVisibility(View.GONE);
			} else if (singledadRelativelayout.getVisibility() == View.VISIBLE) {
				singledadRelativelayout.setVisibility(View.GONE);
			} else if (rateappRelativelayout.getVisibility() == View.VISIBLE) {
				rateappRelativelayout.setVisibility(View.GONE);
			}

			// context.startActivity(getFacebookIntent(AppConstant.fburl));
			OpenAppInsideorOutside.openAppWithURL(context,
					"com.facebook.katana", "fb://facewebmodal/f?href="
							+ AppConstant.fburl, AppConstant.fburl);

		} else if (v.getId() == R.id.rateheadinglinear) {
			// no Action
		} else if (v.getId() == R.id.fivestarlinear) {
			// Yes five Star
			AppRater.showRateDialog(context);

		} else if (v.getId() == R.id.rmaindmelinear) {
			// Remaind me
			SharedPreferencesHelper.setRateAppLeter(context, true);
			rateappRelativelayout.setVisibility(View.GONE);

		} else if (v.getId() == R.id.noidontwannalinear) {
			// No I don't wanna
			SharedPreferencesHelper.setShowRateAppDialog(context, false);
			rateappRelativelayout.setVisibility(View.GONE);

		} else if (v.getId() == R.id.disableaddheadinglinear) {
			// no action

			// }
			// else if (v.getId() == R.id.disablefor99) {
			// // disable for 99
			//
			// } else if (v.getId() == R.id.disableforever99) {
			// // disable forever 99

		} else if (v.getId() == R.id.nahichangemind) {
			// nah i change mind
			dialogRelativelayout.setVisibility(View.GONE);

		} else if (v.getId() == R.id.singledateheadinglinear) {
			// no action

		} else if (v.getId() == R.id.yahtakeme2sateit) {
			// Yay take me to see it

			AppRater.showRateDialog(context);

		} else if (v.getId() == R.id.remindmenext) {
			// Remind me next time
			singledadRelativelayout.setVisibility(View.GONE);

		} else if (v.getId() == R.id.bahidontcare) {
			// Bah I don't care.
			singledadRelativelayout.setVisibility(View.GONE);
		}

	}

}
