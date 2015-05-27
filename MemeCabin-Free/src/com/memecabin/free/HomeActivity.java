package com.memecabin.free;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.iap1.util.IabHelper;
import com.aapbd.iap1.util.IabResult;
import com.aapbd.iap1.util.Inventory;
import com.aapbd.iap1.util.Purchase;
import com.aapbd.utils.display.DisplayUtils;
import com.aapbd.utils.image.BitmapUtils;
import com.aapbd.utils.network.HTTPHandler;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.print.print;
import com.aapbd.utils.view.AppRater;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.memecabin.badgeView.BadgeView;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.CriticMessageFromDan;
import com.memecabin.model.ImageInfo;

public class HomeActivity extends Activity implements OnClickListener {
	private Context con;

	// private GridView grid;
	// private MyLibraryIconicAdapter adapter;
	private DatabaseHandler db;
	private ImageView preferencehome_tv, logouthome_iv;
	private LinearLayout firsthome, noWorristhome_ll, iKnowhome_ll,
			yIlikehome_ll, checkOuthome_ll;

	private RelativeLayout racyPopUp_rl, homeparentRelativeLayout,
			shoCodeViewRacy_r2;
	private EditText entercodeP1_et, entercodeP2_et, entercodeP3_et,
			entercodeDone_et;
	private EditText edTxtCd1, edTxtCd2, edTxtCd3, edTxtCd4;

	private EditText entercodeP111_et, entercodeP222_et, entercodeP333_et,
			entercodeDone44_et;
	private RelativeLayout shoCodeViewRacy_rl, shoCodeViewRacy1_rl;
	private LinearLayout racyMemePopUP_ll, firspasscode_ll, secondpasscode_ll,
			cameraheadnoaction_ll, noactioncamerabg_ll;
	private RelativeLayout racyCamera_rl;
	private TextView racyTitlePopup;

	RelativeLayout rlRateAppDialog;
	LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear;

	private TextView messageTitel, messagesubtitle;
	private LinearLayout danmessageshowLinear, danmeassageshownexttimeLinear,
			danmessagenotshowLinear, danallracynoactionLinear;
	private RelativeLayout denmessagefromapiRelative;

	RelativeLayout addsviewshow;
	LinearLayout nahichangemind, disableaddheadinglinear;

	// LinearLayout llGridHolder;

	// ImageView imgComingSoon;
	LinearLayout llMenuRowOne;
	LinearLayout llMenuRowTwo;
	LinearLayout llMenuRowThree;
	LinearLayout llAllMeme;
	LinearLayout llMotiveInspMeme;
	LinearLayout llRacyMeme;
	LinearLayout llSpiffyGif;
	LinearLayout lltrendingMeme;
	LinearLayout lluploadMeme;
	LinearLayout llfavoriteMeme;
	LinearLayout llRemoveAds;
	LinearLayout llSDLAps;
	TextView txtAllMemeNum;
	TextView txtSpiffybadgeicon;
	TextView txtMotiveInspMemeNum;
	TextView txtRaclyMemeNum;
	// RelativeLayout rlComingSoonHolder;

	BadgeView allMemeBadge;
	BadgeView motivationInpMemeBadge;
	BadgeView racyMemeBadge;

	TextView txtVwFavoriteMeme;

	// camera
	private LinearLayout imageSend_ll;
	private ImageView cameraBtn;
	public final int imagecaptureid = 0;
	public final int galarytakid = 1;
	private TextView sendImageBg_tv;
	private RelativeLayout racyAfterSubImage_rl;
	// private LinearLayout imageUploate_ll;
	String imageLocal = "";
	private boolean imageshow = false;
	private File file;
	private static File dir = null;

	Vector<ImageInfo> temp = new Vector<ImageInfo>();

	String mimeType = "image/jpeg";

	public static HomeActivity instance;

	public static HomeActivity getInstance() {
		return instance;
	}

	/*
	 * 
	 * IN app DONE
	 */

	// Debug tag, for logging
	static final String TAG = "AAPBDINAPP";

	// Does the user have the premium upgrade?
	boolean mIsPremium = false;

	// Does the user have an active subscription to the infinite gas plan?
	boolean mSubscribedToYearLy = false;

	// SKUs for our products: the premium upgrade (non-consumable) and gas
	// (consumable)

	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	// The helper object
	IabHelper mHelper;
	private BusyDialog busy = null;

	/*
	 * ************ In app END
	 */

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnalyticsTracker.sendTrackData(this, R.string.analytics_open_home);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			final View decorView = getWindow().getDecorView();
			final int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		} else {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		setContentView(R.layout.home);
		FlurryAgent.setLogEnabled(false);
        FlurryAgent.setLogLevel(2);
        FlurryAgent.init(this, "VRN76R93CBTJ28GTWT83");//"GZ97GPKXH3R3RRF99JRG");
		
		// Code to get KeyHash value.
		  try {
		   PackageInfo info = getPackageManager().getPackageInfo(
		   // "com.code2care.thebuddhaquotes",
		     getPackageName(), PackageManager.GET_SIGNATURES);
		   for (Signature signature : info.signatures) {
		    MessageDigest md = MessageDigest.getInstance("SHA");
		    md.update(signature.toByteArray());
		    Log.e(TAG, "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
		   }
		  } catch (NameNotFoundException e) {
		   Log.e(TAG, "Exception(NameNotFoundException) : " + e);
		  } catch (NoSuchAlgorithmException e) {
		   Log.e(TAG, "Exception(NoSuchAlgorithmException) : " + e);
		  }

		/*
		 * set screen size to app constant for that session
		 */

		final int width = DisplayUtils.getScreenSize(this).x;
		final int height = DisplayUtils.getScreenSize(this).y;

		print.message("Screen width and height ", width + " " + height);

		AppConstant.minScreenWidth = width * 80 / 100;
		AppConstant.minScreenHeight = height * 80 / 100;

		con = this;
		instance = this;
		db = new DatabaseHandler(con);
		initUI();

//		loadMessage();

		if (SharedPreferencesHelper.getshowRateAppDialog(con)) {
			if (SharedPreferencesHelper.getRateAppLeter(con)) {
				final Calendar c = Calendar.getInstance();
				final long currrentTime = c.getTimeInMillis();
				if (currrentTime
						- SharedPreferencesHelper.getRateAppLeterTime(con) >= AppConstant.TWENTY_FOUR_HOUR_IN_MILLISECOND) {
					rlRateAppDialog.setVisibility(View.GONE);
					SharedPreferencesHelper.setLoginCount(con, 0);
					SharedPreferencesHelper.setRateAppLeter(con, false);
				}

			} else {
				final int count = SharedPreferencesHelper.getLoginCount(con);
				// SharedPreferencesHelper.setLoginCount(con, count++);
				if (count >= 3) {
					// Show Dialog
					if (!(denmessagefromapiRelative.getVisibility() == View.VISIBLE)) {

						rlRateAppDialog.setVisibility(View.GONE);
						SharedPreferencesHelper.setLoginCount(con, 0);

					}

				}
			}
		}

		// Log.e("Key Hash ", KeyHashManager.getKeyHash(con));

		initBIllng();
	}

	private void showDanMessage() {
		final String title = SharedPreferencesHelper.getDanMsgTitle(con);
		final String subTitle = SharedPreferencesHelper.getDanMsgSubTitle(con);
		messageTitel.setText(title);
		messagesubtitle.setText(subTitle);
		if (!(rlRateAppDialog.getVisibility() == View.VISIBLE)) {

			denmessagefromapiRelative.setVisibility(View.VISIBLE);

		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		AppConstant.readFlagmoti = false;
		AppConstant.readFlagmeme = false;
		AppConstant.readFlagRacy = false;
	}

	public void exitCurrentActivity() {

		HomeActivity.this.finish();
	}

	public void toastMessage(String mes) {
		Toast.makeText(con, mes, 3000).show();
	}

	private void initUI() {

		addsviewshow = (RelativeLayout) findViewById(R.id.addsviewshow);
		nahichangemind = (LinearLayout) findViewById(R.id.nahichangemind);

		preferencehome_tv = (ImageView) findViewById(R.id.preferencehome_tv);
		// imgComingSoon = (ImageView) findViewById(R.id.imgComingSoon);
		racyPopUp_rl = (RelativeLayout) findViewById(R.id.racyPopUp_rl);
		shoCodeViewRacy_r2 = (RelativeLayout) findViewById(R.id.shoCodeViewRacy_r2);
		homeparentRelativeLayout = (RelativeLayout) findViewById(R.id.homeparentRelativeLayout);
		shoCodeViewRacy_rl = (RelativeLayout) findViewById(R.id.shoCodeViewRacy_rl);
		shoCodeViewRacy1_rl = (RelativeLayout) findViewById(R.id.shoCodeViewRacy1_rl);
		firsthome = (LinearLayout) findViewById(R.id.firsthome);
		noWorristhome_ll = (LinearLayout) findViewById(R.id.noWorristhome_ll);
		iKnowhome_ll = (LinearLayout) findViewById(R.id.iKnowhome_ll);
		yIlikehome_ll = (LinearLayout) findViewById(R.id.yIlikehome_ll);
		checkOuthome_ll = (LinearLayout) findViewById(R.id.checkOuthome_ll);
		racyMemePopUP_ll = (LinearLayout) findViewById(R.id.racyMemePopUP_ll);
		firspasscode_ll = (LinearLayout) findViewById(R.id.firspasscode_ll);
		secondpasscode_ll = (LinearLayout) findViewById(R.id.secondpasscode_ll);
		cameraheadnoaction_ll = (LinearLayout) findViewById(R.id.cameraheadnoaction_ll);
		noactioncamerabg_ll = (LinearLayout) findViewById(R.id.noactioncamerabg_ll);
		disableaddheadinglinear = (LinearLayout) findViewById(R.id.disableaddheadinglinear);
		racyCamera_rl = (RelativeLayout) findViewById(R.id.racyCamera_rl);
		logouthome_iv = (ImageView) findViewById(R.id.logouthome_iv);

		denmessagefromapiRelative = (RelativeLayout) findViewById(R.id.denmessagefromapiRelative);
		danmessageshowLinear = (LinearLayout) findViewById(R.id.danmessageshowLinear);
		danmeassageshownexttimeLinear = (LinearLayout) findViewById(R.id.danmeassageshownexttimeLinear);
		danmessagenotshowLinear = (LinearLayout) findViewById(R.id.danmessagenotshowLinear);
		danallracynoactionLinear = (LinearLayout) findViewById(R.id.danallracynoactionLinear);

		messageTitel = (TextView) findViewById(R.id.messageTitel);
		messagesubtitle = (TextView) findViewById(R.id.messagesubtitle);

		rlRateAppDialog = (RelativeLayout) findViewById(R.id.rlRateAppDialog);
		fivestarlinear = (LinearLayout) findViewById(R.id.fivestarlinear);
		rmaindmelinear = (LinearLayout) findViewById(R.id.rmaindmelinear);
		noidontwannalinear = (LinearLayout) findViewById(R.id.noidontwannalinear);

		llMenuRowOne = (LinearLayout) findViewById(R.id.llMenuRowOne);
		llMenuRowTwo = (LinearLayout) findViewById(R.id.llMenuRowTwo);
		llMenuRowThree = (LinearLayout) findViewById(R.id.llMenuRowThree);
		llAllMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_all_meme, null);
		llMotiveInspMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_motive_meme, null);
		llRacyMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_racy_meme, null);
		llSpiffyGif = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_spiffy_gift, null);
		lltrendingMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_trending_meme, null);
		lluploadMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_upload_meme, null);
		llfavoriteMeme = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_favorite_meme, null);
		llRemoveAds = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_remove_ads, null);
		llSDLAps = (LinearLayout) getLayoutInflater().inflate(
				R.layout.dashboard_menu_sdl_app, null);

		// rlComingSoonHolder = (RelativeLayout)
		// findViewById(R.id.rlComingSoonHolder);

		txtVwFavoriteMeme = (TextView) llfavoriteMeme
				.findViewById(R.id.txtVwFavoriteMeme);
		txtAllMemeNum = (TextView) llAllMeme.findViewById(R.id.txtAllMemeNum);
		txtSpiffybadgeicon = (TextView) llSpiffyGif
				.findViewById(R.id.txtSpiffybadgeicon);
		txtMotiveInspMemeNum = (TextView) llMotiveInspMeme
				.findViewById(R.id.txtMotiveInspMemeNum);
		txtRaclyMemeNum = (TextView) llRacyMeme
				.findViewById(R.id.txtRacyMemeNum);

		// hide intially

		txtAllMemeNum.setVisibility(View.GONE);
		txtMotiveInspMemeNum.setVisibility(View.GONE);
		txtRaclyMemeNum.setVisibility(View.GONE);
		txtSpiffybadgeicon.setVisibility(View.GONE);

		racyTitlePopup = (TextView) findViewById(R.id.racyTitlePopup);
		racyTitlePopup
				.setText("" + getResources().getString(R.string.goodIdea));

		entercodeP1_et = (EditText) findViewById(R.id.entercodeP11_et);
		entercodeP2_et = (EditText) findViewById(R.id.entercodeP22_et);
		entercodeP3_et = (EditText) findViewById(R.id.entercodeP33_et);
		entercodeDone_et = (EditText) findViewById(R.id.entercodeDone_et);

		entercodeP111_et = (EditText) findViewById(R.id.entercodeP111_et);
		entercodeP222_et = (EditText) findViewById(R.id.entercodeP222_et);
		entercodeP333_et = (EditText) findViewById(R.id.entercodeP333_et);
		entercodeDone44_et = (EditText) findViewById(R.id.entercodeDone44_et);

		edTxtCd1 = (EditText) findViewById(R.id.edtxtPassC1);
		edTxtCd2 = (EditText) findViewById(R.id.edtxtPassC2);
		edTxtCd3 = (EditText) findViewById(R.id.edtxtPassC3);
		edTxtCd4 = (EditText) findViewById(R.id.edtxtPassC4);

		nahichangemind.setOnClickListener(this);

		preferencehome_tv.setOnClickListener(this);
		homeparentRelativeLayout.setOnClickListener(this);
		firsthome.setOnClickListener(this);
		noWorristhome_ll.setOnClickListener(this);
		iKnowhome_ll.setOnClickListener(this);
		yIlikehome_ll.setOnClickListener(this);
		checkOuthome_ll.setOnClickListener(this);
		cameraheadnoaction_ll.setOnClickListener(this);
		noactioncamerabg_ll.setOnClickListener(this);
		fivestarlinear.setOnClickListener(this);
		rmaindmelinear.setOnClickListener(this);
		noidontwannalinear.setOnClickListener(this);
		addsviewshow.setOnClickListener(this);
		disableaddheadinglinear.setOnClickListener(this);

		danmessageshowLinear.setOnClickListener(this);
		danmeassageshownexttimeLinear.setOnClickListener(this);
		danmessagenotshowLinear.setOnClickListener(this);
		danallracynoactionLinear.setOnClickListener(this);
		// imgComingSoon.setOnClickListener(this);
		racyPopUp_rl.setOnClickListener(this);

		llAllMeme.setOnClickListener(allMemeClick);
		llMotiveInspMeme.setOnClickListener(motiveMemeClick);
		llRacyMeme.setOnClickListener(racyMemeClick);
		llSpiffyGif.setOnClickListener(spiffyGifClick);
		lltrendingMeme.setOnClickListener(trandingMemeClick);
		lluploadMeme.setOnClickListener(uploadMemeClick);
		llfavoriteMeme.setOnClickListener(favMemclick);
		llRemoveAds.setOnClickListener(removeAdsClick);
		llSDLAps.setOnClickListener(sdlClick);

		// Logout from HOME
		logouthome_iv.setOnClickListener(this);

		// camera
		cameraBtn = (ImageView) findViewById(R.id.cameraBtn);
		sendImageBg_tv = (TextView) findViewById(R.id.sendImageBg_tv);
		imageSend_ll = (LinearLayout) findViewById(R.id.imageSend_ll);

		racyAfterSubImage_rl = (RelativeLayout) findViewById(R.id.racyAfterSubImage_rl);
		// imageUploate_ll = (LinearLayout) findViewById(R.id.imageUploate_ll);

		imageSend_ll.setOnClickListener(this);
		cameraBtn.setOnClickListener(this);
		racyAfterSubImage_rl.setOnClickListener(this);
		// end

		entercodeP1_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				Log.e("edit 1", "call...............");
				// entercodeP2_et.setFocusable(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// Log.e("edit 1", "after call...............");

				entercodeP2_et.requestFocus();
			}
		});

		entercodeP2_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeP3_et.setFocusable(true);

				Log.e("edit 2", " call...............");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// Log.e("edit 2", "after call...............");

				entercodeP3_et.requestFocus();

			}
		});

		entercodeP3_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeDone_et.setFocusable(true);

				Log.e("edit 3", " call...............");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// Log.e("edit 3", "after call...............");

				entercodeDone_et.requestFocus();

			}
		});

		entercodeDone_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeP1_et.setFocusable(true);

				Log.e("edit 4", " call...............");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				Log.e("edit 4", "after call...............");

				if (!TextUtils.isEmpty(entercodeDone_et.getText().toString()
						.trim())) {
					setPassCode();
				}

				// firstTimeshow = true;

			}
		});

		// second time passcoded

		// entercodeDone44_et.clearFocus();
		// entercodeP111_et.requestFocus();

		entercodeP111_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code

				// entercodeP222_et.setFocusable(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// if (checkfirstTimeshow)
				entercodeP222_et.requestFocus();

			}
		});

		entercodeP222_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeP333_et.setFocusable(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// if (checkfirstTimeshow)
				entercodeP333_et.requestFocus();

			}
		});

		entercodeP333_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeDone44_et.setFocusable(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// if (checkfirstTimeshow)
				entercodeDone44_et.requestFocus();

			}
		});

		entercodeDone44_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeP111_et.setFocusable(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// entercodeP111_et.requestFocus();
				// entercodeP111_et.setFocusable(false);
				// entercodeP111_et.clearFocus();
				// if (AppConstant.checkverfypasscode) {
				// if(checkfirstTimeshow)
				// {
				// checkfirstTimeshow=false;
				if (!TextUtils.isEmpty(entercodeDone44_et.getText().toString()
						.trim())) {

					closeKeyBoard(entercodeDone44_et);
					checkPaassword();
				}
				// }

				// checkfirstTimeshow = true;

				// }
			}
		});

		edTxtCd1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				edTxtCd2.requestFocus();
			}
		});

		edTxtCd2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				edTxtCd3.requestFocus();
			}
		});

		edTxtCd3.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				edTxtCd4.requestFocus();
			}
		});

		edTxtCd4.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(edTxtCd4.getText().toString().trim())) {
					closeKeyBoard(edTxtCd4);
					checkRacyPass();
				}
			}
		});

		// Dan message

		// final String url = AllURL.getmessageFromDen();
		//
		// if(SharedPreferencesHelper.getMsgShowLeter(con)){
		//
		// if(SharedPreferencesHelper.getMsgOnOff(con).equalsIgnoreCase("on")){
		//
		// SharedPreferencesHelper.setMsgShowLeter(con, false);
		// messageTitel.setText(SharedPreferencesHelper.getMsgTitle(con));
		// messagesubtitle.setText(SharedPreferencesHelper.getMsgSubTitle(con));
		// denmessagefromapiRelative.setVisibility(View.VISIBLE);
		//
		// }
		//
		//
		//
		// }else{
		// if(SharedPreferencesHelper.getMsgShowNot(con)){
		// //no action
		//
		// }else{
		// loadMessage(url);
		// }
		// }
		// end Dan message

	}

	private void checkRacyPass() {
		final String passChar1 = edTxtCd1.getText().toString().trim();
		final String passChar2 = edTxtCd2.getText().toString().trim();
		final String passChar3 = edTxtCd3.getText().toString().trim();
		final String passChar4 = edTxtCd4.getText().toString().trim();

		final String pass = passChar1 + passChar2 + passChar3 + passChar4;

		edTxtCd1.setText("");
		edTxtCd2.setText("");
		edTxtCd3.setText("");
		edTxtCd4.setText("");

		if (SharedPreferencesHelper.getPassCode(con).equals(pass)) {
			shoCodeViewRacy_r2.setVisibility(View.GONE);
			closeKeyBoard(edTxtCd4);
			final Intent ii = new Intent(con, RacyAllUnreadActivity.class);
			startActivity(ii);
		} else {
			edTxtCd1.setText("");
			edTxtCd2.setText("");
			edTxtCd3.setText("");
			edTxtCd4.setText("");
			edTxtCd1.requestFocus();
			Toast.makeText(con,
					"Passcode does not match, Please try again.....",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void openKeyBoard(final View v) {
		final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInputFromWindow(
				v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED,
				0);

	}

	public void closeKeyBoard(final View v) {

		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

	}

	@Override
	protected void onResume() {
		super.onResume();

		buildCustomDashBoard();

		AppConstant.homeScreenIndex = 1;

		AppConstant.userID = SharedPreferencesHelper.getUserID(con);
		/*
		 * Date date = new Date(); long lunchtimedate = date.getTime(); String
		 * lunchtime= ""+lunchtimedate;
		 */

		if (AppConstant.upcomingCallOnce) {

			loadUpcoming("android", SharedPreferencesHelper.getLaunchTime(con),
					SharedPreferencesHelper.getUserID(con));

		} else {
			updateBageNumber(txtAllMemeNum, AppConstant.currentAllMemeBadge,
					SharedPreferencesHelper.getShowAllMemeNotify(con));
			updateBageNumber(txtMotiveInspMemeNum,
					AppConstant.currentMotiBadge,
					SharedPreferencesHelper.getShowMotiveMemeNotify(con));
			updateBageNumber(txtRaclyMemeNum, AppConstant.currentRacyBadge,
					SharedPreferencesHelper.getShowRacyMemeNotify(con));
			updateBageNumber(txtSpiffybadgeicon, AppConstant.currentSpiffBadge,
					SharedPreferencesHelper.getShowSpiffyNotify(con));

			updateFavoriteText(SharedPreferencesHelper.getTotalMyFavNumber(con));

		}

		// buildCustomDashBoard();

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.preferencehome_tv) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_preference);
			final Intent ii = new Intent(con, PreferencesActivity.class);
			startActivity(ii);
			overridePendingTransition(R.anim.slide_in_up, R.anim.noslide);

		} else if (v.getId() == R.id.firsthome) {
			// noAction

		} else if (v.getId() == R.id.noWorristhome_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_racy_no_worry);
			racyPopUp_rl.setVisibility(View.GONE);
			final Intent ii = new Intent(con, RacyAllUnreadActivity.class);
			startActivity(ii);

		} else if (v.getId() == R.id.iKnowhome_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_racy_dont_show);
			// AppConstant.racyflage = "1";
			SharedPreferencesHelper.setRacyflage(con, true);
			racyPopUp_rl.setVisibility(View.GONE);
			final Intent ii = new Intent(con, RacyAllUnreadActivity.class);
			startActivity(ii);

		} else if (v.getId() == R.id.yIlikehome_ll) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_racy_set_pass);
			if (SharedPreferencesHelper.getLockflage(con)
					.equalsIgnoreCase("on")) {

				openKeyBoard(entercodeP111_et);
				// checkfirstTimeshow = true;
				entercodeP111_et.requestFocus();
				// entercodeDone44_et.cFocus();

				// AppConstant.homeScreenIndex = 0;

				racyPopUp_rl.setVisibility(View.GONE);
				shoCodeViewRacy1_rl.setVisibility(View.VISIBLE);
				shoCodeViewRacy_rl.setVisibility(View.GONE);

				// shoCodeViewRacy1_rl.setVisibility(View.VISIBLE);

			} else {

				openKeyBoard(entercodeP1_et);

				// firstTimeshow= true;
				entercodeP1_et.requestFocus();

				racyPopUp_rl.setVisibility(View.GONE);
				shoCodeViewRacy_rl.setVisibility(View.VISIBLE);
				shoCodeViewRacy1_rl.setVisibility(View.GONE);

			}

		} else if (v.getId() == R.id.checkOuthome_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_racy_dont_show);

			AppConstant.homeScreenIndex = 0;
			// db.updateDashStatus("racy", "off");
			SharedPreferencesHelper.setActionReminder(con, "off");
			buildCustomDashBoard();
			racyPopUp_rl.setVisibility(View.GONE);
			/*
			 * adapter = new MyLibraryIconicAdapter(con);
			 * 
			 * grid.setVerticalScrollBarEnabled(false);
			 * grid.setAdapter(adapter); adapter.notifyDataSetChanged();
			 */

		} else if (v.getId() == R.id.homeparentRelativeLayout) {
			racyPopUp_rl.setVisibility(View.GONE);
			racyCamera_rl.setVisibility(View.GONE);
			racyAfterSubImage_rl.setVisibility(View.GONE);
			shoCodeViewRacy_rl.setVisibility(View.GONE);
			shoCodeViewRacy1_rl.setVisibility(View.GONE);
			shoCodeViewRacy_r2.setVisibility(View.GONE);

			closeKeyBoard(v);
			// shoCodeViewRacy_rl.setVisibility(View.GONE);
			// shoCodeViewRacy1_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.imageSend_ll) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_send_meme);
			AppConstant.homeScreenIndex = 0;

			if (imageshow) {
				if (imageLocal.length() == 0) {
					AlertMessage.showMessage(con, getString(R.string.app_name),
							"Please select your image first");
					return;
				} else {
					final String urlMemes = AllURL.setRacyMemes();
					loadRacyMemes(urlMemes);

				}
			}

			// imageUploate_ll.setVisibility(View.GONE);
			// racyAfterSubImage_rl.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.cameraBtn) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_meme_source);

			cameraPopUp();
		} else if (v.getId() == R.id.racyAfterSubImage_rl) {

			racyAfterSubImage_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.cameraheadnoaction_ll) {
			// action not assigned
		} else if (v.getId() == R.id.noactioncamerabg_ll) {
			// action not assigned
		} else if (v.getId() == R.id.logouthome_iv) {
			// Logout button from Home
			AnalyticsTracker.sendTrackData(this, R.string.analytics_logout);
			AppConstant.fromscreen = "main";
			SharedPreferencesHelper.setUserID(con, "");
			SharedPreferencesHelper.setLogin(con, false);
			SharedPreferencesHelper.setUsingskip(con, false);

			final Intent ii = new Intent(con, LogActivity.class);
			startActivity(ii);
			HomeActivity.this.finish();

		} else if (v.getId() == R.id.fivestarlinear) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_app_rate);
			// Yes five Star
			AppRater.showRateDialog(con);

		} else if (v.getId() == R.id.rmaindmelinear) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_app_rate_leter);
			// Remaind me
			SharedPreferencesHelper.setRateAppLeter(con, true);
			final Calendar c = Calendar.getInstance();
			SharedPreferencesHelper.setRateAppLeterTime(con,
					c.getTimeInMillis());
			rlRateAppDialog.setVisibility(View.GONE);
		} else if (v.getId() == R.id.noidontwannalinear) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_app_rate_dont_show);
			// No I don't wanna
			rlRateAppDialog.setVisibility(View.GONE);
			SharedPreferencesHelper.setShowRateAppDialog(con, false);
		} else if (v.getId() == R.id.danmessageshowLinear) {
			// Den message show at this Link
			// SharedPreferencesHelper.setMsgShowNot(con, true);
			denmessagefromapiRelative.setVisibility(View.GONE);
			SharedPreferencesHelper.setDanMsgGotIt(con, true);
			// Log.e("URL",":"+urlDan);

			if (SharedPreferencesHelper.getDanMsgUrl(con).length() != 0) {
				final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(SharedPreferencesHelper.getDanMsgUrl(con)));
				startActivity(browserIntent);
			}

		} else if (v.getId() == R.id.danmeassageshownexttimeLinear) {
			// Message show after perticular time
			SharedPreferencesHelper.setDanMsgRemindMeLeter(con, true);
			final Calendar c = Calendar.getInstance();
			final long currentTime = c.getTimeInMillis();
			SharedPreferencesHelper.setDanMsgLastRemindMeTime(con, currentTime);
			// SharedPreferencesHelper.setMessageLeter(con, true);
			denmessagefromapiRelative.setVisibility(View.GONE);

		} else if (v.getId() == R.id.danmessagenotshowLinear) {
			// Message not show next time
			SharedPreferencesHelper.setDanMsgDontShow(con, true);
			denmessagefromapiRelative.setVisibility(View.GONE);

		} else if (v.getId() == R.id.danallracynoactionLinear) {
			// no action

		}
		/*
		 * else if (v.getId() == R.id.imgComingSoon) { // SDL app link
		 * 
		 * final String appPackageName = con.getResources().getString(
		 * R.string.package_name_sdl_app);
		 * 
		 * if (isPackageInstalled(appPackageName)) { // Open the app final
		 * Intent i = con.getPackageManager()
		 * .getLaunchIntentForPackage(appPackageName);
		 * i.addCategory(Intent.CATEGORY_LAUNCHER); startActivity(i); } else {
		 * // Open market try { startActivity(new Intent(Intent.ACTION_VIEW,
		 * Uri.parse("market://details?id=" + appPackageName))); } catch (final
		 * android.content.ActivityNotFoundException anfe) { startActivity(new
		 * Intent( Intent.ACTION_VIEW,
		 * Uri.parse("http://play.google.com/store/apps/details?id=" +
		 * appPackageName))); } }
		 * 
		 * }
		 */
		else if (v.getId() == R.id.racyPopUp_rl) {

			/*
			 * imgComingSoon.setClickable(true); llAllMeme.setClickable(true);
			 * llMotiveInspMeme.setClickable(true);
			 * lltrendingMeme.setClickable(true);
			 * lluploadMeme.setClickable(true);
			 * llfavoriteMeme.setClickable(true);
			 * preferencehome_tv.setClickable(true);
			 * logouthome_iv.setClickable(true);
			 */

			racyPopUp_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.nahichangemind) {
			// view gone
			addsviewshow.setVisibility(View.GONE);
		} else if (v.getId() == R.id.addsviewshow) {

			addsviewshow.setVisibility(View.GONE);
		} else if (v.getId() == R.id.disableaddheadinglinear) {

			// No Action here.

		}
	}

	private boolean isPackageInstalled(String packagename) {
		final PackageManager pm = con.getPackageManager();
		try {
			pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (final NameNotFoundException e) {
			return false;
		}
	}

	private final OnClickListener allMemeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Log.e("AppConstant.currentAllMemeBadge", ""
					+ AppConstant.currentAllMemeBadge);
			Log.e("SharedPreferencesHelper.getAllMemes(con)", ""
					+ SharedPreferencesHelper.getAllMemes(con));

			if (AppConstant.currentAllMemeBadge > 0) {
				final int prememevalue = SharedPreferencesHelper
						.getAllMemes(con);
				final int newvalue = AppConstant.currentAllMemeBadge
						+ prememevalue;
				SharedPreferencesHelper.setAllMemes(con, newvalue);
				AppConstant.currentAllMemeBadge = 0;
			}

			Log.e("after llMemes(con)",
					"" + SharedPreferencesHelper.getAllMemes(con));

			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_all_meme);
			final Intent ii = new Intent(con, MemesEverUnreadActivity.class);
			// ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
		}
	};

	private final OnClickListener motiveMemeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (AppConstant.currentMotiBadge > 0) {
				final int prememevalue = SharedPreferencesHelper
						.getMotivationMemes(con);
				final int newvalue = AppConstant.currentMotiBadge
						+ prememevalue;
				SharedPreferencesHelper.setMotivationalMemes(con, newvalue);
				AppConstant.currentMotiBadge = 0;
			}

			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_motivation_meme);
			final Intent ii = new Intent(con, MotiInspiActivity.class);
			// ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
		}
	};

	private final OnClickListener racyMemeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (AppConstant.currentRacyBadge > 0) {
				final int prememevalue = SharedPreferencesHelper
						.getRacyMemes(con);
				final int newvalue = AppConstant.currentRacyBadge
						+ prememevalue;
				SharedPreferencesHelper.setRacyMemes(con, newvalue);
				AppConstant.currentRacyBadge = 0;
			}

			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_recy_meme);

			AppConstant.homeScreenIndex = 1;

			// if (AppConstant.racyflage.equalsIgnoreCase("1"))
			if (SharedPreferencesHelper.getRacyflage(con)) {

				final Intent ii = new Intent(con, RacyAllUnreadActivity.class);
				startActivity(ii);

			} else {
				// AppConstant.racyflage = "";
				// SharedPreferencesHelper.setRacyflage(con, false);

				if (SharedPreferencesHelper.getLockflage(con).equalsIgnoreCase(
						"off")) {

					racyPopUp_rl.setVisibility(View.VISIBLE);

				} else {
					shoCodeViewRacy_r2.setVisibility(View.VISIBLE);
					openKeyBoard(edTxtCd1);
					edTxtCd1.requestFocus();
				}

			}
		}
	};

	private final OnClickListener spiffyGifClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (AppConstant.currentSpiffBadge > 0) {
				final int prespiffvalue = SharedPreferencesHelper
						.getSpiffy(con);
				final int newvalue = AppConstant.currentSpiffBadge
						+ prespiffvalue;
				SharedPreferencesHelper.setSpiffy(con, newvalue);
				AppConstant.currentSpiffBadge = 0;
			}

			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_spiffy);

			final Intent intent = new Intent(con, SpiffyGifActivity.class);
			startActivity(intent);
		}
	};

	private final OnClickListener trandingMemeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_trending_meme);
			final Intent ii = new Intent(con, TendingScrctivity.class);
			// ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
		}
	};

	private final OnClickListener uploadMemeClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_share_meme);
			AppConstant.homeScreenIndex = 1;

			racyCamera_rl.setVisibility(View.VISIBLE);
		}
	};

	private final OnClickListener favMemclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnalyticsTracker.sendTrackData(HomeActivity.this,
					R.string.analytics_open_favorite_meme);
			final Intent ii = new Intent(con, FavoritesActivity.class);

			startActivity(ii);
		}
	};

	private final OnClickListener removeAdsClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			addsviewshow.setVisibility(View.VISIBLE);
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://go.danoah.com/memepro"));
			startActivity(browserIntent);

		}
	};

	private final OnClickListener sdlClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String appPackageName = con.getResources().getString(
					R.string.package_name_sdl_app);

			if (isPackageInstalled(appPackageName)) { // Open the app
				final Intent i = con.getPackageManager()
						.getLaunchIntentForPackage(appPackageName);
				i.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(i);
			} else { // Open market
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("market://details?id=" + appPackageName)));
				} catch (final android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ appPackageName)));
				}
			}
		}
	};

	protected void loadUpcoming(String value, String time, String userId) {

		Log.e("upcoming", " API called");

		if (!NetInfo.isOnline(con)) {

			return;
		}
		// final BusyDialog busyNow = new BusyDialog(con, true);
		// busyNow.show();
		final String url = AllURL.getUpcomingApp(value, time, userId);

		Log.e("URL ", ">> " + url);

		// final AsyncHttpClient client = new AsyncHttpClient();
		// client.put(BaseURL.HTTP + "upComingApps" + "?type=" + value,new
		// AsyncHttpResponseHandler()
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {

				/*
				 * if (busyNow != null) { busyNow.dismis(); }
				 */

				JSONObject json = null;

				try {

					json = new JSONObject(new String(response));

					Log.e("Upcoming JSON ", ">>>" + new String(response));

					AppConstant.status = json.optString("status");

					if (AppConstant.status.toString().trim()
							.equalsIgnoreCase("1")) {

						final JSONArray jsonarray = json.getJSONArray("result");

						for (int i = 0; i < jsonarray.length(); i++) {

							final JSONObject jsonHolder = jsonarray
									.getJSONObject(i);

							// AppConstant.appId=jsonHolder.optString("id");
							SharedPreferencesHelper.setAppId(con,
									jsonHolder.optString("id"));
							SharedPreferencesHelper.setAppIcon(con,
									jsonHolder.optString("image"));
							SharedPreferencesHelper.setAppLink(con,
									jsonHolder.optString("link"));
						}

						SharedPreferencesHelper.setUpcomingAds(con, false);

						final String total_everyoneMemes = json
								.optString("total_everyoneMemes");
						final String total_motivationalMemes = json
								.optString("total_motivationalMemes");
						final String total_racyMemes = json
								.optString("total_racyMemes");
						final String total_spiffyMemes = json
								.optString("total_spiffyMemes");
						String total_favorite = json
								.optString("total_myFavourite");

						AppConstant.myFavorite = total_favorite;

						Log.e("total_everyoneMemes", ">>" + total_everyoneMemes);
						Log.e("total_motivationalMemes", ">>"
								+ total_motivationalMemes);
						Log.e("total_everyoneMemes", ">>" + total_everyoneMemes);
						Log.e("total_spiffyMemes", ">>" + total_spiffyMemes);

						if (isNumberCheck(total_everyoneMemes)) {

							final int oldValue = SharedPreferencesHelper
									.getAllMemes(con);
							final int newValue = Integer
									.parseInt(total_everyoneMemes);
							final int diff = Math.abs(newValue - oldValue);
							AppConstant.currentAllMemeBadge = diff;
							// SharedPreferencesHelper.setAllMemes(con,diff);

						} else {
							SharedPreferencesHelper.setAllMemes(con, 0);
						}

						if (isNumberCheck(total_motivationalMemes)) {
							final int oldValuemoti = SharedPreferencesHelper
									.getMotivationMemes(con);
							final int newValuemoti = Integer
									.parseInt(total_motivationalMemes);
							final int diffmoti = Math.abs(newValuemoti
									- oldValuemoti);
							AppConstant.currentMotiBadge = diffmoti;
							// SharedPreferencesHelper.setMotivationalMemes(con,diffmoti);

						} else {
							SharedPreferencesHelper
									.setMotivationalMemes(con, 0);
						}

						if (isNumberCheck(total_racyMemes)) {

							final int oldValueracy = SharedPreferencesHelper
									.getRacyMemes(con);
							final int newValueracy = Integer
									.parseInt(total_racyMemes);
							final int diffracy = Math.abs(newValueracy
									- oldValueracy);
							AppConstant.currentRacyBadge = diffracy;

						} else {
							SharedPreferencesHelper.setRacyMemes(con, 0);
						}

						if (isNumberCheck(total_spiffyMemes)) {
							final int oldValuespiffy = SharedPreferencesHelper
									.getSpiffy(con);
							final int newValuespiffy = Integer
									.parseInt(total_spiffyMemes);
							final int diffspiffy = Math.abs(newValuespiffy
									- oldValuespiffy);
							AppConstant.currentSpiffBadge = diffspiffy;

						} else {
							SharedPreferencesHelper.setSpiffy(con, 0);
						}

						if (TextUtils.isEmpty(total_favorite)) {
							total_favorite = "0";
						}
						SharedPreferencesHelper.setTotalMyFavNumber(con,
								total_favorite);

						updateBageNumber(txtAllMemeNum,
								AppConstant.currentAllMemeBadge,
								SharedPreferencesHelper
										.getShowAllMemeNotify(con));
						updateBageNumber(txtMotiveInspMemeNum,
								AppConstant.currentMotiBadge,
								SharedPreferencesHelper
										.getShowMotiveMemeNotify(con));
						updateBageNumber(txtRaclyMemeNum,
								AppConstant.currentRacyBadge,
								SharedPreferencesHelper
										.getShowRacyMemeNotify(con));
						updateBageNumber(txtSpiffybadgeicon,
								AppConstant.currentSpiffBadge,
								SharedPreferencesHelper
										.getShowSpiffyNotify(con));

						updateFavoriteText(total_favorite);

					}

				} catch (final JSONException e) {
					e.printStackTrace();
				}

				if (AppConstant.status.equalsIgnoreCase("1")) {

					// Set Icon
					// loadUpcomingImage();

				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401,
				// 403, 404)

				/*
				 * dismiss the dialog
				 */
				// busyNow.dismis();

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	public boolean isNumberCheck(String num) {
		boolean flag = false;

		try {

			Integer.parseInt(num);

			flag = true;

		} catch (final Exception e) {
			flag = false;
		}

		return flag;

	}

	public void setPassCode() {
		final String firsCode, secondCode, thirdCode, nLastCode;

		firsCode = entercodeP1_et.getText().toString().trim();
		secondCode = entercodeP2_et.getText().toString().trim();
		thirdCode = entercodeP3_et.getText().toString().trim();
		nLastCode = entercodeDone_et.getText().toString().trim();

		AppConstant.matchpasscode = firsCode + secondCode + thirdCode
				+ nLastCode;
		SharedPreferencesHelper.setPassCode(con, AppConstant.matchpasscode);

		// SharedPreferencesHelper.setLockflage(con, "on");
		// SharedPreferencesHelper.setToggleStatus(con, "on");

		// Log.e("First time Passcode: ", "" + AppConstant.matchpasscode);

		// shoCodeViewRacy_rl.setVisibility(View.GONE);

		// AppConstant.checkverfypasscode = true;

		// if (AppConstant.checkverfypasscode) {
		entercodeP1_et.setText("");
		entercodeP2_et.setText("");
		entercodeP3_et.setText("");
		entercodeDone_et.setText("");
		// closeKeyBoard(entercodeDone_et);
		shoCodeViewRacy_rl.setVisibility(View.GONE);
		shoCodeViewRacy1_rl.setVisibility(View.VISIBLE);
		// racyTitlePopup.setText(""
		// + getResources().getString(R.string.enterpasscodeagain));
		// racyTitlePopup.setTextSize(12.0f);

		entercodeP111_et.requestFocus();
		// entercodeDone_et.clearFocus();

		// checkfirstTimeshow=false;
		// }

	}

	private void checkPaassword() {
		final String fir1, seco2, third3, nLast4;

		fir1 = entercodeP111_et.getText().toString().trim();
		seco2 = entercodeP222_et.getText().toString().trim();
		third3 = entercodeP333_et.getText().toString().trim();
		nLast4 = entercodeDone44_et.getText().toString().trim();

		AppConstant.veryfypasscode = fir1 + seco2 + third3 + nLast4;
		// Log.e("Second time pass word:", "" + AppConstant.veryfypasscode);
		// SharedPreferencesHelper.setPassCode(con, AppConstant.veryfypasscode);

		if (AppConstant.veryfypasscode.equals(SharedPreferencesHelper
				.getPassCode(con))) {
			// closeKeyBoard(entercodeDone44_et);

			closeKeyBoard(entercodeDone44_et);

			SharedPreferencesHelper.setLockflage(con, "on");
			// SharedPreferencesHelper.setToggleStatus(con, "off");
			SharedPreferencesHelper
					.setPassCode(con, AppConstant.veryfypasscode);

			// checkfirstTimeshow=false;

			// AppConstant.checkverfypasscode = true;
			entercodeP111_et.setText("");
			entercodeP222_et.setText("");
			entercodeP333_et.setText("");
			entercodeDone44_et.setText("");

			shoCodeViewRacy1_rl.setVisibility(View.GONE);
			shoCodeViewRacy_rl.setVisibility(View.GONE);

			final Intent ii = new Intent(con, RacyAllUnreadActivity.class);
			startActivity(ii);

		} else {

			// checkfirstTimeshow=false;

			entercodeP111_et.setText("");
			entercodeP222_et.setText("");
			entercodeP333_et.setText("");
			entercodeDone44_et.setText("");
			entercodeP111_et.requestFocus();
			toastMessage("Passcode does not match, Please try again.....");
		}
	}

	public void cameraPopUp() {
		final CharSequence[] items = { getString(R.string.useCamera),
				getString(R.string.chooseFromLibrary),
				getString(R.string.cancel) };
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.upload));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:

					AnalyticsTracker.sendTrackData(HomeActivity.this,
							R.string.analytics_select_camera);
					// if(!AppConstant.fromcamera){
					// cameraBtn.setBackgroundResource(R.drawable.camera);
					// AppConstant.fromcamera=true;
					cameraImage();

					// }

					break;

				case 1:
					AnalyticsTracker.sendTrackData(HomeActivity.this,
							R.string.analytics_select_library);
					GalaryImage();
					break;
				case 2:
					// onStopRecording();
					break;
				default:

					return;
				}

			}
		});
		builder.show();
		builder.create();

	}

	public void cameraImage() {

		final Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(i, imagecaptureid);

		// final Intent intent = new
		// Intent("android.media.action.IMAGE_CAPTURE");
		// final File file = new File(Environment.getExternalStorageDirectory()
		// + File.separator + "image.jpg");
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		// startActivityForResult(intent, imagecaptureid);
	}

	public void GalaryImage() {

		final Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				galarytakid);

		// final Intent intent2 = new Intent(Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		// startActivityForResult(intent2, galarytakid);
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {

		Log.e("onacivity", "onActivityResult home");

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...

			Log.e("onacivity", "not handler");

			// Log.e("requestCode", "are" + requestCode);
			// Log.e("resultCode", "are" + resultCode);

			if (requestCode == galarytakid && resultCode == Activity.RESULT_OK) {

				// Log.e("In gallelrly", "lllll..........");

				try {

					final Uri selectedImageUri = data.getData();

					imageLocal = getRealPathFromURI(con, selectedImageUri);
					AppConstant.racyImageUrl = imageLocal;

					Log.e("///////Local image ", "::" + imageLocal);

					if (imageLocal.endsWith("gif")) {
						mimeType = "image/gif";

						Log.e("gif ", "::" + imageLocal);

						sendImageBg_tv
								.setBackgroundResource(R.drawable.senditfinal);
						cameraBtn.setImageBitmap(BitmapFactory
								.decodeFile(imageLocal));

						imageshow = true;

					} else {
						mimeType = "application/octet-stream";
						Log.e("png ", "::" + imageLocal);

						final Bitmap bitmap = BitmapFactory
								.decodeStream(getContentResolver()
										.openInputStream(selectedImageUri));

						setToImageView(bitmap);

					}

				} catch (final Exception e) {
					return;
				}

			} else if (requestCode == imagecaptureid
					&& resultCode == Activity.RESULT_OK) {

				Log.e("Camera", "called//////////////");

				try {

					final Bundle extras = data.getExtras();
					final Bitmap b = (Bitmap) extras.get("data");

					setToImageView(b);

					// cameraBtn.setImageResource(R.drawable.ic_launcher);

				} catch (final Exception e) {
					return;
				}

			}

			super.onActivityResult(requestCode, resultCode, data);
		} else {

			if (requestCode == imagecaptureid
					&& resultCode == Activity.RESULT_OK) {

				Log.e("Camera", "called//////////////");

				try {

					final Bundle extras = data.getExtras();
					final Bitmap b = (Bitmap) extras.get("data");

					setToImageView(b);

					// cameraBtn.setImageResource(R.drawable.ic_launcher);

				} catch (final Exception e) {
					return;
				}

			}
			Log.e(TAG,
					"onActivityResult handled by IABUtil....................");
		}

	}

	private void setToImageView(Bitmap bitmap) {
		imageLocal = "";

		try {
			final Bitmap bit = BitmapUtils.getResizedBitmap(bitmap, 1024);

			imageLocal = saveBitmapIntoSdcard(bit, "racyimage.png");
			Log.e("camera saved URL :  ", " " + imageLocal);
			AppConstant.racyImageUrl = imageLocal;
			// AppConstant.fromcamera= true
			cameraBtn.setImageDrawable(null);
			cameraBtn.setImageURI(Uri.parse(imageLocal));
			imageshow = true;

		} catch (final IOException e) {
			e.printStackTrace();

			imageLocal = "";
			Log.e("camera saved URL :  ", e.toString());

		}
		// AppConstant.racyImageUrl = imageLocal;
		sendImageBg_tv.setBackgroundResource(R.drawable.senditfinal);
	}

	private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
			throws IOException {
		/*
		 * 
		 * check the path and create if needed
		 */
		createBaseDirctory();

		try {

			new Date();

			OutputStream out = null;
			file = new File(HomeActivity.dir, "/" + filename);

			if (file.exists()) {
				file.delete();
			}

			out = new FileOutputStream(file);

			bitmap22.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.flush();
			out.close();
			// Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
			return file.getAbsolutePath();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void createBaseDirctory() {

		final String extStorageDirectory = Environment
				.getExternalStorageDirectory().toString();
		dir = new File(extStorageDirectory + "/MEMECABIN");

		if (HomeActivity.dir.mkdir()) {
			System.out.println("Directory created");
		} else {
			System.out.println("Directory is not created or exists");
		}
	}

	protected void loadRacyMemes(final String url) {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final AsyncHttpClient client = new AsyncHttpClient();
		final RequestParams param = new RequestParams();
		final File imgFile = new File(AppConstant.racyImageUrl);
		try {
			param.put("userfile", imgFile, mimeType);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// RequestParams p=AllURL.set("userfile",AppConstant.racyImageUrl);
		// Log.e("IMAGE URL ", ">>" + AppConstant.racyImageUrl);

		client.post(url, param, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				if (busyNow != null) {
					busyNow.dismis();
				}

				Log.e("resposne ", ">>" + new String(response));

				JSONObject json = null;

				try {
					json = new JSONObject(new String(response));

					AppConstant.status = json.optString("status");

					// Log.e("AppConstant.status", ">>" + AppConstant.status);

					if (AppConstant.status.equalsIgnoreCase("1")) {

						racyCamera_rl.setVisibility(View.GONE);
						racyAfterSubImage_rl.setVisibility(View.VISIBLE);
						cameraBtn.setImageResource(R.drawable.camera);
						sendImageBg_tv.setBackgroundResource(R.drawable.sendit);

						// toastMessage("Racy uploaded successed");

					} else {

						toastMessage("Image isn't uploaded!");
						return;
					}

				} catch (final JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)

				if (busyNow != null) {
					busyNow.dismis();
				}
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried

			}
		});

	}

	/*
	 * @Override public boolean onKeyDown(final int keyCode, final KeyEvent
	 * event) { if (keyCode == KeyEvent.KEYCODE_BACK) {
	 * 
	 * if (AppConstant.homeScreenIndex == 0) { HomeActivity.this.finish(); }
	 * else { AppConstant.homeScreenIndex = 0;
	 * racyPopUp_rl.setVisibility(View.GONE);
	 * racyCamera_rl.setVisibility(View.GONE); //
	 * racyPopUp_rl.setVisibility(View.GONE);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return true;
	 * 
	 * }
	 */

	public void decide() {
		final CharSequence[] items = { "Got it, take me to login.",
				"Eek. No thanks." };
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Oops! You have to be logged-in to save your favorite memes for easy access later!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					AppConstant.fromscreen = "home";
					final Intent ii = new Intent(con, LogActivity.class);
					startActivity(ii);
					HomeActivity.this.finish();
					break;
				case 1:

					// final Intent i = new Intent(con,FavoritesActivity.class);
					// startActivity(i);
					break;

				default:

					return;
				}

			}
		});
		builder.show();
		builder.create();

	}

	@Override
	public void onBackPressed() {
		if (shoCodeViewRacy_rl.getVisibility() == View.VISIBLE) {
			shoCodeViewRacy_rl.setVisibility(View.GONE);
		} else if (shoCodeViewRacy1_rl.getVisibility() == View.VISIBLE) {
			shoCodeViewRacy1_rl.setVisibility(View.GONE);
		} else if (racyPopUp_rl.getVisibility() == View.VISIBLE) {

			// imgComingSoon.setClickable(true);
			llAllMeme.setClickable(true);
			llMotiveInspMeme.setClickable(true);
			lltrendingMeme.setClickable(true);
			lluploadMeme.setClickable(true);
			llfavoriteMeme.setClickable(true);
			llRemoveAds.setClickable(true);
			llSpiffyGif.setClickable(true);
			llSDLAps.setClickable(true);
			preferencehome_tv.setClickable(true);
			logouthome_iv.setClickable(true);
			racyPopUp_rl.setVisibility(View.GONE);

		} else if (racyCamera_rl.getVisibility() == View.VISIBLE) {
			racyCamera_rl.setVisibility(View.GONE);
		} else if (shoCodeViewRacy_r2.getVisibility() == View.VISIBLE) {
			shoCodeViewRacy_r2.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}

	static class HomeMenuHolder {
		LinearLayout linearLayout;
		ImageView imageView;
		TextView textView;
	}

	private void updateBadgeOnIcon(BadgeView badgeView, int number,
			View parentView) {
		if (badgeView == null) {
			badgeView = new BadgeView(con, parentView);
		}

		badgeView.setBackgroundResource(R.drawable.badge_ifaux);
		badgeView.setTextSize(12);
		badgeView.setBadgePosition(2);
		badgeView.setBadgeMargin(2);

		if (number < 99) {
			badgeView.setText("" + number);
		} else {
			badgeView.setText("99" + "+");
		}
		if (number > 0) {
			badgeView.show();
		}
	}

	private void updateFavoriteText(String number) {
		final StringBuilder builder = new StringBuilder();
		builder.append(getString(R.string.text_dashboard_favorite));
		builder.append(" (");
		builder.append(number);
		builder.append(")");
		txtVwFavoriteMeme.setText(builder.toString());
	}

	/**
	 * 
	 * @param v
	 *            TextView to show the bage number
	 * @param number
	 *            The number of notification to show on text view (v)
	 * @param isShowNotification
	 *            Show notification is on or off in preference tab
	 */
	private void updateBageNumber(TextView v, int number,
			boolean isShowNotification) {
		if (isShowNotification) {
			if (number <= 0) {
				v.setVisibility(View.GONE);
			} else {
				v.setVisibility(View.VISIBLE);
			}
			if (number > 99) {
				v.setText("99+");
			} else {
				v.setText(number + "");
			}
		} else {
			v.setVisibility(View.GONE);
		}
	}

	private static final int MENU_ITEM_ALL_MEME = 1;
	private static final int MENU_ITEM_MOTIVE_MEME = 2;
	private static final int MENU_ITEM_RACY_MEME = 3;
	private static final int MENU_ITEM_SPIFFY_GIF = 4;
	private static final int MENU_ITEM_TRENDING_MEME = 5;
	private static final int MENU_ITEM_UPLOAD_MEME = 6;
	private static final int MENU_ITEM_REMOVE_ADS = 7;
	private static final int MENU_ITEM_FAVORITE_MEME = 8;
	private static final int MENU_ITEM_SDL_APP = 9;

	private void buildCustomDashBoard() {
		final Vector<Integer> menuItems = new Vector<Integer>();
		menuItems.addElement(MENU_ITEM_ALL_MEME);
		menuItems.addElement(MENU_ITEM_MOTIVE_MEME);
		if (!SharedPreferencesHelper.getActionReminder(con).equalsIgnoreCase(
				"off")) {
			menuItems.addElement(MENU_ITEM_RACY_MEME);
		}
		if (!SharedPreferencesHelper.getSpiffyOnOff(con)
				.equalsIgnoreCase("off")) {
			menuItems.addElement(MENU_ITEM_SPIFFY_GIF);
		}

		menuItems.addElement(MENU_ITEM_TRENDING_MEME);
		menuItems.addElement(MENU_ITEM_UPLOAD_MEME);
		menuItems.addElement(MENU_ITEM_FAVORITE_MEME);
		if (!SharedPreferencesHelper.isAddDisable(con)) {
			menuItems.addElement(MENU_ITEM_REMOVE_ADS);
		}

		if (!SharedPreferencesHelper.getSDLOnOff(con).equalsIgnoreCase("off")) {
			menuItems.addElement(MENU_ITEM_SDL_APP);
		}

		llMenuRowOne.removeAllViews();
		llMenuRowTwo.removeAllViews();
		llMenuRowThree.removeAllViews();
		for (int i = 0; i < menuItems.size(); i++) {
			LinearLayout llTemp = new LinearLayout(con);
			switch (menuItems.get(i)) {
			case MENU_ITEM_ALL_MEME:
				llTemp = llAllMeme;
				break;
			case MENU_ITEM_MOTIVE_MEME:
				llTemp = llMotiveInspMeme;
				break;
			case MENU_ITEM_RACY_MEME:
				llTemp = llRacyMeme;
				break;
			case MENU_ITEM_SPIFFY_GIF:
				llTemp = llSpiffyGif;
				break;
			case MENU_ITEM_TRENDING_MEME:
				llTemp = lltrendingMeme;
				break;
			case MENU_ITEM_UPLOAD_MEME:
				llTemp = lluploadMeme;
				break;
			case MENU_ITEM_FAVORITE_MEME:
				llTemp = llfavoriteMeme;
				break;
			case MENU_ITEM_REMOVE_ADS:
				llTemp = llRemoveAds;
				break;
			case MENU_ITEM_SDL_APP:
				llTemp = llSDLAps;
				break;
			}
			LayoutParams params = (LayoutParams) llTemp.getLayoutParams();
			if (params == null) {
				params = new LayoutParams(0,
						android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			}
			params.width = 0;
			params.weight = 1;
			llTemp.setLayoutParams(params);
			if (i >= 7) {
				llMenuRowThree.addView(llTemp);
			} else if (i >= 3) { // Add to 2nd row
				if(i != 3)
					llMenuRowTwo.addView(llTemp);
			} else { // Add to 1st row
				llMenuRowOne.addView(llTemp);
			}
		}
	}

	protected void loadPopUp() {
		Log.e("criticMessage", ">>" + "called");

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					final String criticMessage = HTTPHandler.dataFromGet(AllURL
							.criticalMessageFromDan());

					Log.e("criticMessage", ">>" + criticMessage);
					final Gson g = new Gson();

					AppConstant.criticMessage = g.fromJson(criticMessage,
							CriticMessageFromDan.class);

				} catch (final IOException e) {
					e.printStackTrace();
				} catch (final URISyntaxException e) {
					e.printStackTrace();
				} catch (final Exception e) {
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/*
						 * show critic dialog
						 */
						try {
							if (AppConstant.criticMessage != null) {
								if (AppConstant.criticMessage.getRecord()
										.get(0).getIsPublish()
										.equalsIgnoreCase("on")) {
									/*
									 * add counter logic
									 */
									int danMsgCount;

									try {

										danMsgCount = Integer
												.parseInt(AppConstant.criticMessage
														.getRecord().get(0)
														.getCounter());

									} catch (final Exception e2) {
										// TODO: handle exception
										danMsgCount = 0;
									}

									if (danMsgCount > SharedPreferencesHelper
											.danMsgCount(con)) {
										SharedPreferencesHelper.setDanMsgCount(
												con, danMsgCount);
										danMessageShow();
									} else {
										if (SharedPreferencesHelper
												.danMsgShow(con)) {
											danMessageShow();
										}
									}

								} else {
									/*
									 * no need to show the dialog
									 */
								}
							}

						} catch (final Exception e) {

						}
					}

				});

			}
		});

		d.start();
	}

	protected void loadMessage() {
		// TODO Auto-generated method stub

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		// TODO Auto-generated method stub

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final String url = AllURL.getmessageFromDen();
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				if (busyNow != null) {
					busyNow.dismis();
				}

				try {
					final JSONObject json = new JSONObject(new String(response));

					Log.e("server : ", ":" + new String(response));

					AppConstant.status = json.optString("status");

					if (AppConstant.status.equalsIgnoreCase("1")) {

						final JSONArray arrayObj = json.getJSONArray("result");

						final JSONObject resultObj = arrayObj.getJSONObject(0);

						final String cnt = resultObj.optString("counter");
						String title = "";
						String url = "";
						String subTitle = "";
						String isPublished = "";
						final int counter = Integer.parseInt(cnt);
						if (counter > SharedPreferencesHelper
								.getDanMsgCounter(con)) { // new message
							title = resultObj.optString("title");
							subTitle = resultObj.optString("subtitle");
							url = resultObj.optString("url");
							isPublished = resultObj.optString("IsPublish");
							SharedPreferencesHelper.setDanMsgTitle(con, title);
							SharedPreferencesHelper.setDanMsgSubTitle(con,
									subTitle);
							SharedPreferencesHelper.setDanMsgUrl(con, url);
							if (isPublished.equalsIgnoreCase("on")) {
								SharedPreferencesHelper
										.setDanMsgLastRemindMeTime(con, 0);
								SharedPreferencesHelper.setDanMsgRemindMeLeter(
										con, false);
								// Show msg

								showDanMessage();
							}
							SharedPreferencesHelper.setDanMsgCounter(con,
									counter);
							SharedPreferencesHelper.setDanMsgDontShow(con,
									false);
							SharedPreferencesHelper.setDanMsgGotIt(con, false);

						} else { // Old message
							if (!SharedPreferencesHelper.getDanMsgDontShow(con)
									&& !SharedPreferencesHelper
											.getDanMsgGotIt(con)) {
								if (SharedPreferencesHelper
										.getDanMsgRemindMeLeter(con)) {
									final long lastRemindTime = SharedPreferencesHelper
											.getDanMsgLastRemindMeTime(con);
									final Calendar c = Calendar.getInstance();
									final long currentTime = c
											.getTimeInMillis();
									if (currentTime - lastRemindTime >= AppConstant.TWENTY_FOUR_HOUR_IN_MILLISECOND) {
										SharedPreferencesHelper
												.setDanMsgLastRemindMeTime(con,
														0);
										SharedPreferencesHelper
												.setDanMsgRemindMeLeter(con,
														false);
										// Show msg
										showDanMessage();
										SharedPreferencesHelper
												.setDanMsgDontShow(con, false);
										SharedPreferencesHelper.setDanMsgGotIt(
												con, false);
									}
								}
							}
						}
					}
				} catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				loadPopUp();

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)

				if (busyNow != null) {
					busyNow.dismis();
				}
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried

			}
		});

	}

	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			final String[] proj = { MediaColumns.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null,
					null, null);
			final int column_index = cursor
					.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public void danMessageShow() {
		final DanMessageShow pl = new DanMessageShow(con);
		pl.openDanMessageShow();
	}

	/* ******************************************************************************************
	 * buy things
	 * 
	 * in app billing part
	 */

	public void buyLifeTime(View v) {

		Log.e(TAG, "Launching purchase flow for gas.");

		// launch the gas purchase UI flow.
		// We will be notified of completion via mPurchaseFinishedListener
		setWaitScreen(true);

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
		final String payload = "";

		mHelper.launchPurchaseFlow(this, AppConstant.SKU_PREMIUM, RC_REQUEST,
				mPurchaseFinishedListener, payload);

	}

	// "Subscribe to infinite gas" button clicked. Explain to user, then start
	// purchase
	// flow for subscription.
	public void buyForOneYear(View arg0) {
		if (!mHelper.subscriptionsSupported()) {
			complain("Subscriptions not supported on your device yet. Sorry!");
			return;
		}

		/*
		 * TODO: for security, generate your payload here for verification. See
		 * the comments on verifyDeveloperPayload() for more info. Since this is
		 * a SAMPLE, we just use an empty string, but on a production app you
		 * should carefully generate this.
		 */
		final String payload = "";

		setWaitScreen(true);
		Log.e(TAG, "Launching purchase flow for infinite gas subscription.");
		mHelper.launchPurchaseFlow(this, AppConstant.SKU_YEARLY,
				IabHelper.ITEM_TYPE_SUBS, RC_REQUEST,
				mPurchaseFinishedListener, payload);
	}

	private void setWaitScreen(boolean flag) {
		// TODO Auto-generated method stub
		if (busy == null) {
			busy = new BusyDialog(con, true);
		}

		if (flag) {

			busy.show();

		} else {
			busy.dismis();
		}

	}

	private void initBIllng() {
		// TODO Auto-generated method stub

		//
		// Create the helper, passing it our context and the public key to
		// verify signatures with
		Log.e(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(this, AppConstant.base64EncodedPublicKey);

		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.e(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				Log.e(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Hooray, IAB is fully set up. Now, let's get an inventory of
				// stuff we own.
				Log.e(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}

	// Listener that's called when we finish querying the items and
	// subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			Log.e(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}

			Log.e(TAG, "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Do we have the premium upgrade?
			final Purchase premiumPurchase = inventory
					.getPurchase(AppConstant.SKU_PREMIUM);
			mIsPremium = premiumPurchase != null
					&& verifyDeveloperPayload(premiumPurchase);
			Log.e(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

			// Do we have the infinite gas plan?
			final Purchase infiniteGasPurchase = inventory
					.getPurchase(AppConstant.SKU_YEARLY);
			mSubscribedToYearLy = infiniteGasPurchase != null
					&& verifyDeveloperPayload(infiniteGasPurchase);
			Log.e(TAG, "User "
					+ (mSubscribedToYearLy ? "HAS" : "DOES NOT HAVE")
					+ " infinite gas subscription.");

			// // Check for gas delivery -- if we own gas, we should fill up the
			// tank immediately
			// Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
			// if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
			// Log.e(TAG, "We have gas. Consuming it.");
			// mHelper.consumeAsync(inventory.getPurchase(SKU_GAS),
			// mConsumeFinishedListener);
			// return;
			// }

			updateUi(mIsPremium, mSubscribedToYearLy);

			Log.e(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		p.getDeveloperPayload();

		/*
		 * TODO: verify that the developer payload of the purchase is correct.
		 * It will be the same one that you sent when initiating the purchase.
		 * 
		 * WARNING: Locally generating a random string when starting a purchase
		 * and verifying it here might seem like a good approach, but this will
		 * fail in the case where the user purchases an item on one device and
		 * then uses your app on a different device, because on the other device
		 * you will not have access to the random string you originally
		 * generated.
		 * 
		 * So a good developer payload has these characteristics:
		 * 
		 * 1. If two different users purchase an item, the payload is different
		 * between them, so that one user's purchase can't be replayed to
		 * another user.
		 * 
		 * 2. The payload must be such that you can verify it even when the app
		 * wasn't the one who initiated the purchase flow (so that items
		 * purchased by the user on one device work on other devices owned by
		 * the user).
		 * 
		 * Using your own server to store and verify developer payloads across
		 * app installations is recommended.
		 */

		return true;
	}

	// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		@Override
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.e(TAG, "Purchase finished: " + result + ", purchase: "
					+ purchase);
			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				setWaitScreen(false);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				setWaitScreen(false);
				return;
			}

			Log.e(TAG, "Purchase successful.");

			if (purchase.getSku().equals(AppConstant.SKU_PREMIUM)) {
				// bought the premium upgrade!
				Log.e(TAG, "Purchase is premium upgrade. Congratulating user.");
				alert("Thank you for upgrading to premium!");
				mIsPremium = true;
				updateUi(mIsPremium, mSubscribedToYearLy);
				setWaitScreen(false);
			} else if (purchase.getSku().equals(AppConstant.SKU_YEARLY)) {
				// bought the infinite gas subscription
				Log.e(TAG, "Infinite gas subscription purchased.");
				alert("Thank you for subscribing to infinite gas!");
				mSubscribedToYearLy = true;
				// mTank = TANK_MAX;
				updateUi(mIsPremium, mSubscribedToYearLy);
				setWaitScreen(false);
			}
		}
	};

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		@Override
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.e(TAG, "Consumption finished. Purchase: " + purchase
					+ ", result: " + result);

			// We know this is the "gas" sku because it's the only one we
			// consume,
			// so we don't check which sku was consumed. If you have more than
			// one
			// sku, you probably should check...
			if (result.isSuccess()) {
				// successfully consumed, so we apply the effects of the item in
				// our
				// game world's logic, which in our case means filling the gas
				// tank a bit
				Log.e(TAG, "Consumption successful. Provisioning.");
				// mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
				// saveData();
				// alert("You filled 1/4 tank. Your tank is now " +
				// String.valueOf(mTank) + "/4 full!");
			} else {
				complain("Error while consuming: " + result);
			}
			// updateUi();
			// setWaitScreen(false);
			Log.e(TAG, "End consumption flow.");
		}
	};

	// We're being destroyed. It's important to dispose of the helper here!
	@Override
	public void onDestroy() {
		super.onDestroy();

		// very important:
		Log.e(TAG, "Destroying helper.");
		if (mHelper != null) {
			mHelper.dispose();
		}
		mHelper = null;
	}

	protected void updateUi(boolean mIsPremium2, boolean mSubscribedToYearLy2) {
		// TODO Auto-generated method stub

		if (mIsPremium2 || mSubscribedToYearLy2) {
			SharedPreferencesHelper.setAddDisable(con, true);

			/*
			 * set type
			 */

			if (mIsPremium2) {
				SharedPreferencesHelper.setLifeTimePurchase(con, true);

			} else if (mSubscribedToYearLy2) {
				SharedPreferencesHelper.setYearlyPurchase(con, true);

			}
		} else {
			/*
			 * no purchase yet
			 */
			SharedPreferencesHelper.setAddDisable(con, false);

			SharedPreferencesHelper.setYearlyPurchase(con, false);
			SharedPreferencesHelper.setLifeTimePurchase(con, false);

		}

		/*
		 * update the screen.
		 */
		buildCustomDashBoard();

	}

	void complain(String message) {
		Log.e(TAG, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		final AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.e(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

	/*
	 * End of In app billing ........
	 * 
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */

}
