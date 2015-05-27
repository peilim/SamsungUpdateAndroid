package com.memecabin.free;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.validation.ValidateEmail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;

public class PreferencesActivity extends Activity implements OnClickListener {
	private Context con;
	private ImageView preBack_im, preBottmBackunPre_iv;
	private LinearLayout changePasswordPre_ll, savepassword_ll, forgotpass_ll;
	// private LinearLayout preUnlockCode_ll;
	private ToggleButton preEveryOne_tg, preMotivIns_tg, preRacyMeme_tg,
			preMemeHome_tg, preSpiffyHome_tg, preSDLHome_tg, preLock_tg;
	private RelativeLayout shoCodeViewPref_rl, shoCodeViewPref1_rl,
			changePasswordPref_rl, prefenecLayout,
			forgetpasswordRelativepreference;
	private DatabaseHandler db;
	private EditText entercodeP1_et, entercodeP2_et, entercodeP3_et,
			entercodeP4_et, oldpassword_et, newpassword_et, repassword_tv;
	private EditText enterEmailaddresspreference_et;
	private LinearLayout retrivePasswordpreference_ll;
	Button btnChangePassword;

	private String emailaddress;
	// private boolean lockFlag = false;
	private EditText enterpreference1_et, enterpreference2_et,
			enterpreference3_et, enterpreference4_et;

	private boolean firstTimeFocus = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.preferences);
		con = this;
		db = new DatabaseHandler(con);
		initUI();

	}

	private void initUI() {
		prefenecLayout = (RelativeLayout) findViewById(R.id.prefenecLayout);

		preBack_im = (ImageView) findViewById(R.id.preBack_im);
		preBottmBackunPre_iv = (ImageView) findViewById(R.id.preBottmBackunPre_iv);
		changePasswordPre_ll = (LinearLayout) findViewById(R.id.changePasswordPre_ll);
		btnChangePassword = (Button) findViewById(R.id.btnChangePass);
		// preUnlockCode_ll = (LinearLayout)
		// findViewById(R.id.preUnlockCode_ll);
		savepassword_ll = (LinearLayout) findViewById(R.id.savepassword_ll);
		forgotpass_ll = (LinearLayout) findViewById(R.id.forgotpass_ll);
		retrivePasswordpreference_ll = (LinearLayout) findViewById(R.id.retrivePasswordpreference_ll);

		shoCodeViewPref_rl = (RelativeLayout) findViewById(R.id.shoCodeViewPref_rl);
		shoCodeViewPref1_rl = (RelativeLayout) findViewById(R.id.shoCodeViewPref1_rl);

		changePasswordPref_rl = (RelativeLayout) findViewById(R.id.changePasswordPref_rl);
		forgetpasswordRelativepreference = (RelativeLayout) findViewById(R.id.forgetpasswordRelativepreference);

		preEveryOne_tg = (ToggleButton) findViewById(R.id.preEveryOne_tg);
		preMotivIns_tg = (ToggleButton) findViewById(R.id.preMotivIns_tg);
		preRacyMeme_tg = (ToggleButton) findViewById(R.id.preRacyMeme_tg);
		preMemeHome_tg = (ToggleButton) findViewById(R.id.preMemeHome_tg);
		preSpiffyHome_tg = (ToggleButton) findViewById(R.id.preSpiffyHome_tg);
		preSDLHome_tg = (ToggleButton) findViewById(R.id.preSDLHome_tg);

		preLock_tg = (ToggleButton) findViewById(R.id.preLock_tg);

		entercodeP1_et = (EditText) findViewById(R.id.entercodeP1_et);
		entercodeP2_et = (EditText) findViewById(R.id.entercodeP2_et);
		entercodeP3_et = (EditText) findViewById(R.id.entercodeP3_et);
		entercodeP4_et = (EditText) findViewById(R.id.entercodeP4_et);
		enterpreference1_et = (EditText) findViewById(R.id.enterpreference1_et);
		enterpreference2_et = (EditText) findViewById(R.id.enterpreference2_et);
		enterpreference3_et = (EditText) findViewById(R.id.enterpreference3_et);
		enterpreference4_et = (EditText) findViewById(R.id.enterpreference4_et);

		oldpassword_et = (EditText) findViewById(R.id.oldpassword_et);
		newpassword_et = (EditText) findViewById(R.id.newpassword_et);
		repassword_tv = (EditText) findViewById(R.id.repassword_tv);

		enterEmailaddresspreference_et = (EditText) findViewById(R.id.enterEmailaddresspreference_et);

		savepassword_ll.setOnClickListener(this);
		forgotpass_ll.setOnClickListener(this);
		preBack_im.setOnClickListener(this);
		preBottmBackunPre_iv.setOnClickListener(this);
		changePasswordPre_ll.setOnClickListener(this);
		btnChangePassword.setOnClickListener(this);
		prefenecLayout.setOnClickListener(this);
		retrivePasswordpreference_ll.setOnClickListener(this);

		// changePasswordPref_rl.setOnClickListener(this);
		// shoCodeViewPref_rl.setOnClickListener(this);

		if (SharedPreferencesHelper.getLockflage(con).equalsIgnoreCase("on")) {

			preLock_tg.setChecked(true);
			preLock_tg.setBackgroundResource(R.drawable.lock);

		} else {
			preLock_tg.setChecked(false);
			preLock_tg.setBackgroundResource(R.drawable.lockoff);
		}

		preLock_tg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				firstTimeFocus = false;
				entercodeP1_et.requestFocus();

				openKeyBoard(entercodeP1_et);

				// changePasswordPre_ll.setVisibility(View.INVISIBLE);

				shoCodeViewPref_rl.setVisibility(View.VISIBLE);

				entercodeP1_et.setText("");
				entercodeP2_et.setText("");
				entercodeP3_et.setText("");
				entercodeP4_et.setText("");
			}

		});

		entercodeP1_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code

				Log.e("Focus 1", "calling ......");
				// entercodeP2_et.setFocusable(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				Log.e("Focus 1", "after calling ......");
				if (firstTimeFocus) {
					entercodeP2_et.requestFocus();
				}
				//

			}
		});

		entercodeP2_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code
				// entercodeP3_et.setFocusable(true);

				Log.e("Focus 2", "calling ......");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				Log.e("Focus 2", "after calling ......");

				if (firstTimeFocus) {
					entercodeP3_et.requestFocus();
				}

			}
		});

		entercodeP3_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code

				// entercodeP1_et.requestFocus();

				Log.e("Focus 3", " calling ......");

				// Log.e("THis sdl","THir ......");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				if (firstTimeFocus) {
					entercodeP4_et.requestFocus();
				}

				Log.e("Focus 3", "after calling ......");

				// entercodeP4_et.requestFocus();

			}
		});

		entercodeP4_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// here is your code

				// entercodeP1_et.requestFocus();

				Log.e("Focus 3", " calling ......");

				// Log.e("THis sdl","THir ......");

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				if (firstTimeFocus) {
					closeKeyBoard(entercodeP4_et);
				}

				firstTimeFocus = true;

				Log.e("Focus 3", "after calling ......");

				if (!TextUtils.isEmpty(entercodeP4_et.getText().toString()
						.trim())) {
					checkData();
				}

				// entercodeP4_et.requestFocus();

			}
		});

		// second time
		enterpreference1_et.addTextChangedListener(new TextWatcher() {

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
				enterpreference2_et.requestFocus();

			}
		});

		enterpreference2_et.addTextChangedListener(new TextWatcher() {

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
				enterpreference3_et.requestFocus();

			}
		});

		enterpreference3_et.addTextChangedListener(new TextWatcher() {

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
				enterpreference4_et.requestFocus();

			}
		});

		enterpreference4_et.addTextChangedListener(new TextWatcher() {

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
				if (!TextUtils.isEmpty(enterpreference4_et.getText().toString()
						.trim())) {

					closeKeyBoard(enterpreference4_et);
					// checkPaassword();
				}
				// }

				// checkfirstTimeshow = true;

				// }
			}
		});

		// end second time

		if (!SharedPreferencesHelper.getActionReminder(con).equalsIgnoreCase(
				"off")) {

			preMemeHome_tg.setChecked(true);
			preMemeHome_tg.setBackgroundResource(R.drawable.showon);

		} else {

			preMemeHome_tg.setChecked(false);
			preMemeHome_tg.setBackgroundResource(R.drawable.showoff);
		}

		preMemeHome_tg
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {

							// db.updateDashStatus("racy", "on");
							preMemeHome_tg
									.setBackgroundResource(R.drawable.showon);
							SharedPreferencesHelper
									.setActionReminder(con, "on");

						} else {
							// db.updateDashStatus("racy", "off");
							preMemeHome_tg
									.setBackgroundResource(R.drawable.showoff);
							SharedPreferencesHelper.setActionReminder(con,
									"off");

						}

					}

				});

		// spiffy
		if (!SharedPreferencesHelper.getSpiffyOnOff(con)
				.equalsIgnoreCase("off")) {

			preSpiffyHome_tg.setChecked(true);
			preSpiffyHome_tg.setBackgroundResource(R.drawable.showon);

		} else {

			preSpiffyHome_tg.setChecked(false);
			preSpiffyHome_tg.setBackgroundResource(R.drawable.showoff);
		}

		preSpiffyHome_tg
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {

							// db.updateDashStatus("racy", "on");
							preSpiffyHome_tg
									.setBackgroundResource(R.drawable.showon);
							SharedPreferencesHelper.setSpiffyOnoff(con, "on");

						} else {
							// db.updateDashStatus("racy", "off");
							preSpiffyHome_tg
									.setBackgroundResource(R.drawable.showoff);
							SharedPreferencesHelper.setSpiffyOnoff(con, "off");

						}

					}

				});

		// sdl
		if (!SharedPreferencesHelper.getSDLOnOff(con).equalsIgnoreCase("off")) {

			preSDLHome_tg.setChecked(true);
			preSDLHome_tg.setBackgroundResource(R.drawable.showon);

		} else {

			preSDLHome_tg.setChecked(false);
			preSDLHome_tg.setBackgroundResource(R.drawable.showoff);
		}

		preSDLHome_tg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {

					// db.updateDashStatus("racy", "on");
					preSDLHome_tg.setBackgroundResource(R.drawable.showon);
					SharedPreferencesHelper.setSDLonoff(con, "on");

				} else {
					// db.updateDashStatus("racy", "off");
					preSDLHome_tg.setBackgroundResource(R.drawable.showoff);
					SharedPreferencesHelper.setSDLonoff(con, "off");

				}

			}

		});

		if (SharedPreferencesHelper.getShowAllMemeNotify(con)) {
			preEveryOne_tg.setChecked(true);
			// preEveryOne_tg.setBackgroundResource(R.drawable.showon);
		} else {
			preEveryOne_tg.setChecked(false);
			// preEveryOne_tg.setBackgroundResource(R.drawable.showoff);
		}
		if (SharedPreferencesHelper.getShowMotiveMemeNotify(con)) {
			preMotivIns_tg.setChecked(true);
			// preMotivIns_tg.setBackgroundResource(R.drawable.showon);
		} else {
			preMotivIns_tg.setChecked(false);
			// preMotivIns_tg.setBackgroundResource(R.drawable.showoff);
		}
		if (SharedPreferencesHelper.getShowRacyMemeNotify(con)) {
			preRacyMeme_tg.setChecked(true);
			// preRacyMeme_tg.setBackgroundResource(R.drawable.showon);
		} else {
			preRacyMeme_tg.setChecked(false);
			// preRacyMeme_tg.setBackgroundResource(R.drawable.showoff);
		}

		preEveryOne_tg.setOnCheckedChangeListener(toggleCheckChengeListener);
		preMotivIns_tg.setOnCheckedChangeListener(toggleCheckChengeListener);
		preRacyMeme_tg.setOnCheckedChangeListener(toggleCheckChengeListener);

	}

	private final OnCheckedChangeListener toggleCheckChengeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			if (buttonView.getId() == preEveryOne_tg.getId()) {
				if (preEveryOne_tg.isChecked()) {
					// preEveryOne_tg.setBackgroundResource(R.drawable.showon);
					SharedPreferencesHelper.setShowAllMemeNotify(con, true);
				} else {
					// preEveryOne_tg.setBackgroundResource(R.drawable.showoff);
					SharedPreferencesHelper.setShowAllMemeNotify(con, false);
				}
			} else if (buttonView.getId() == preMotivIns_tg.getId()) {
				if (preMotivIns_tg.isChecked()) {
					// preMotivIns_tg.setBackgroundResource(R.drawable.showon);
					SharedPreferencesHelper.setShowMotiveMemeNotify(con, true);
				} else {
					// preMotivIns_tg.setBackgroundResource(R.drawable.showoff);
					SharedPreferencesHelper.setShowMotiveMemeNotify(con, false);
				}
			} else if (buttonView.getId() == preRacyMeme_tg.getId()) {
				if (preRacyMeme_tg.isChecked()) {
					// preRacyMeme_tg.setBackgroundResource(R.drawable.showon);
					SharedPreferencesHelper.setShowRacyMemeNotify(con, true);
				} else {
					// preRacyMeme_tg.setBackgroundResource(R.drawable.showoff);
					SharedPreferencesHelper.setShowRacyMemeNotify(con, false);
				}
			}
		}
	};

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

	public void toastMessage(String mes) {

		// Toast.makeText(con, mes, 3000).show();

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.preBack_im) {

			if (AppConstant.prefBckFlage) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_close_password_change_option);
				AppConstant.prefBckFlage = false;
				changePasswordPref_rl.setVisibility(View.GONE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_previous_activity);
				PreferencesActivity.this.finish();
				overridePendingTransition(R.anim.noslide, R.anim.slide_down_out);

			}

		} else if (v.getId() == R.id.preBottmBackunPre_iv) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			if (MemesEverUnreadActivity.getInstance() != null) {
				MemesEverUnreadActivity.getInstance().finish();
			}
			if (MotiInspiActivity.getInstance() != null) {
				MotiInspiActivity.getInstance().finish();
			}
			if (RacyAllUnreadActivity.getInstance() != null) {
				RacyAllUnreadActivity.getInstance().finish();
			}
			if (TendingScrctivity.getInstance() != null) {
				TendingScrctivity.getInstance().finish();
			}
			if (FavoritesActivity.getInstance() != null) {
				FavoritesActivity.getInstance().finish();
			}

			PreferencesActivity.this.finish();
			overridePendingTransition(R.anim.noslide, R.anim.slide_down_out);

		} else if (v.getId() == R.id.btnChangePass) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_change_password);
			if (SharedPreferencesHelper.getUsingskip(con)) {

				Toast.makeText(con,
						"You need to be logged in to retrieve password", 500)
						.show();

			} else {
				AppConstant.prefBckFlage = true;

				changePasswordPref_rl.setVisibility(View.VISIBLE);
			}

		}

		else if (v.getId() == R.id.savepassword_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_change_password);
			changepassCheck();

		} else if (v.getId() == R.id.forgotpass_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_forgot_password);
			// forgot password

			changePasswordPref_rl.setVisibility(View.GONE);
			forgetpasswordRelativepreference.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.prefenecLayout) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_all_popup);
			AppConstant.prefBckFlage = true;

			final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(entercodeP1_et.getWindowToken(), 0);

			if (changePasswordPref_rl.getVisibility() == View.VISIBLE) {
				changePasswordPref_rl.setVisibility(View.GONE);

			} else if (shoCodeViewPref_rl.getVisibility() == View.VISIBLE) {
				shoCodeViewPref_rl.setVisibility(View.GONE);
			} else if (forgetpasswordRelativepreference.getVisibility() == View.VISIBLE) {
				forgetpasswordRelativepreference.setVisibility(View.GONE);
			}

		} else if (v.getId() == R.id.retrivePasswordpreference_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_retrive_password);
			// API call
			if (SharedPreferencesHelper.getUsingskip(con)) {

				Toast.makeText(con,
						"You need to be logged in to retrieve password", 500)
						.show();

			} else {

				checkEmail();

			}

		}

	}

	public void checkData() {

		String fst, sec, thir, fouth;

		fst = entercodeP1_et.getText().toString().trim();
		sec = entercodeP2_et.getText().toString().trim();
		thir = entercodeP3_et.getText().toString().trim();
		fouth = entercodeP4_et.getText().toString().trim();

		// if (fst.length() == 0) {
		// AlertMessage.showMessage(con, getString(R.string.Status),
		// getString(R.string.firstCode));
		//
		// return;
		// }
		// if (sec.length() == 0) {
		// AlertMessage.showMessage(con, getString(R.string.Status),
		// getString(R.string.secondCode));
		// return;
		//
		// }
		// if (thir.length() == 0) {
		// AlertMessage.showMessage(con, getString(R.string.Status),
		// getString(R.string.thirdCode));
		// return;
		//
		// }
		// if (fouth.length() == 0) {
		// AlertMessage.showMessage(con, getString(R.string.Status),
		// getString(R.string.nlastCode));
		// return;
		//
		// }

		final String concatvalue = fst + sec + thir + fouth;

		Log.e("Lock status  ",
				">>>>" + SharedPreferencesHelper.getLockflage(con));

		if (SharedPreferencesHelper.getLockflage(con).equalsIgnoreCase("off")) {
			// AppConstant.checkverfypasscode = true;
			SharedPreferencesHelper.setPassCode(con, concatvalue);
			SharedPreferencesHelper.setLockflage(con, "on");
			// SharedPreferencesHelper.setToggleStatus(con, "on");
			preLock_tg.setChecked(true);
			preLock_tg.setBackgroundResource(R.drawable.lock);
			shoCodeViewPref_rl.setVisibility(View.GONE);
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.successmsg));
		} else {

			if (SharedPreferencesHelper.getPassCode(con).equals(concatvalue)) {
				shoCodeViewPref_rl.setVisibility(View.GONE);
				SharedPreferencesHelper.setLockflage(con, "off");
				// SharedPreferencesHelper.setToggleStatus(con, "off");
				SharedPreferencesHelper.setPassCode(con, "");

				preLock_tg.setChecked(false);
				preLock_tg.setBackgroundResource(R.drawable.lockoff);
				AppConstant.checkverfypasscode = false;
				AlertMessage.showMessage(con, getString(R.string.Status),
						getString(R.string.unlocksuccessmsg));
			} else {
				firstTimeFocus = false;
				entercodeP1_et.requestFocus();
				// entercodeP4_et.clearFocus();

				entercodeP1_et.setText("");
				entercodeP2_et.setText("");
				entercodeP3_et.setText("");
				entercodeP4_et.setText("");
				AlertMessage.showMessage(con, getString(R.string.Status),
						getString(R.string.alertMessage));
			}

			// if (SharedPreferencesHelper.getPassCode(con).length() == 0) {
			// AppConstant.checkverfypasscode = true;
			// SharedPreferencesHelper.setPassCode(con, concatvalue);
			// SharedPreferencesHelper.setLockflage(con, "on");
			// SharedPreferencesHelper.setToggleStatus(con, "on");
			// shoCodeViewPref_rl.setVisibility(View.INVISIBLE);
			// preLock_tg.setChecked(true);
			// preLock_tg.setBackgroundResource(R.drawable.lock);
			//
			// AlertMessage.showMessage(con, getString(R.string.Status),
			// getString(R.string.successmsg));
			//
			// } else {
			//
			// }

		}

	}

	public void changepassCheck() {
		String odl, newpass, repeatpass;
		odl = oldpassword_et.getText().toString().trim();
		newpass = newpassword_et.getText().toString().trim();
		repeatpass = repassword_tv.getText().toString().trim();

		if (TextUtils.isEmpty(odl)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.enterOldpass));

			return;
		} else if (TextUtils.isEmpty(newpass)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.enterNewpass));

			return;
		} else if (TextUtils.isEmpty(repeatpass)) {

			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.enterRepeatpass));

			return;
		} else if (newpassword_et.getText().equals(repeatpass)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.passwordnotsame));
			return;
		} else {
			final String urlpass = AllURL.getNewPassword(AppConstant.userID,
					odl, newpass);
			loadDataChangePassword(urlpass);
		}

	}

	protected void loadDataChangePassword(final String url) {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

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

				JSONObject json = null;
				try {
					json = new JSONObject(new String(response));

					AppConstant.status = json.optString("status");

					if (AppConstant.status.equalsIgnoreCase("1")) {

						oldpassword_et.setText("");
						newpassword_et.setText("");
						repassword_tv.setText("");

						toastMessage(getString(R.string.passwordChangedSuccess));

					} else {
						oldpassword_et.setText("");
						newpassword_et.setText("");
						repassword_tv.setText("");

						toastMessage(getString(R.string.passwordChangedUnsuccess));
						return;
					}

				} catch (final JSONException e) {
					// TODO Auto-generated catch block
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

	public void checkEmail() {
		emailaddress = enterEmailaddresspreference_et.getText().toString()
				.trim();

		if (!ValidateEmail.validateEmail(emailaddress)) {

			AlertMessage.showMessage(con, "Error",
					getString(R.string.PleaseEnterValidEmail));
			return;

		} else if (TextUtils.isEmpty(emailaddress)) {
			AlertMessage.showMessage(con, "Error",
					getString(R.string.PleaseEnterEmail));
			return;

		} else {
			final String urlret = AllURL.getPassword(emailaddress);
			loadDatapass(urlret);
		}

	}

	protected void loadDatapass(final String url) {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

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

				JSONObject json = null;

				try {
					json = new JSONObject(new String(response));

					Log.e("Response for getPass>>>>>:", ""
							+ new String(response));

					AppConstant.status = json.optString("status");

					if (AppConstant.status.equalsIgnoreCase("1")) {

						enterEmailaddresspreference_et.setText("");
						toastMessage(json.optString("message") + "");
						forgetpasswordRelativepreference
								.setVisibility(View.GONE);

					} else {

						toastMessage(json.optString("message") + "");
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

}
