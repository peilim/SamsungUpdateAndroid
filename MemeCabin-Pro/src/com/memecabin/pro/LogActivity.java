package com.memecabin.pro;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.utils.nagivation.StartActivity;
import com.aapbd.utils.network.HTTPHandler;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.notification.DialogUtils;
import com.aapbd.utils.print.print;
import com.aapbd.utils.storage.PersistentUser;
import com.aapbd.utils.validation.ValidateEmail;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.pro.R;

public class LogActivity extends Activity implements OnClickListener {
	private Context con;
	private LinearLayout skipLogin_ll, logViaFb_ll, LoginWithEmailAddress_ll,
			memberLoginEmail_ll, retrivePassword_ll;
	RelativeLayout loginLayout, registrationRelative, forgetpasswordRelative;
	LinearLayout registrationUserLine, loginLinear, forgetPasswordMain_ll;
	private EditText emailAddress_et, password_et, verifyPassword_et,
			memberEmail_et, memberPassword_et, enterEmailaddress_et;
	private String email, passWord, typeche;
	String emailaddress;
	private TextView faceboktextlog, registrationtextlog,
			afterregistrationlogintext, afterregistrationlogintext1,
			registrationtextlog1, longinalertmessagetextview;
	TextView registertextview, loginUser_ll, skiptextview;

	LinearLayout layoutLogin, layoutFacebook, layoutRegister;
	TextView messageshowforlog;

	// LinearLayout gonskipView;

	// Generate fb #:RZino3bDwe1JKbPv3Q3bjZmHWO4= and id: 558359717642213

	// apk fb id: 258974730962988

	// private static final String FACEBOOK_APPID = "558359717642213";
	// private static final String FACEBOOK_APPID = "841310412569994";//from DAN

	private static final String TAG = "FacebookSample";
	String userEmail, password, type;
	// private FacebookConnector facebookConnector;
	private final String userFbId = "";
	BusyDialog busyNow;
	TextView secondmessageTextview1, forgotPassword_tv;
	String publishStatus = "";

	Session fb_session;

	// end facebook

	public static LogActivity instance;

	public static LogActivity getInstance() {
		return instance;
	}
	private TextView termsView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		con = this;
		instance = this;
		RandomKeyhash();
		// tsuc=this;

		fb_session = Session.openActiveSessionFromCache(con);

		// this.facebookConnector = new FacebookConnector(
		// AppConstant.FACEBOOK_APPID, this, getApplicationContext(),
		// AppConstant.FACEBOOK_PERMISSION);

		initUI();

		// if (AppConstant.isSamsung) {
		// AppConstant.isSamsung=false;
		// showSamsungWarning();
		// }

		showSamsungMessage();
		termsView = getTermsView();

	}

	
	/*
	 * 
	 * termsview
	 */

	private TextView getTermsView() {
		if (termsView == null) {

			termsView = (TextView) findViewById(R.id.registerbuyertosview);
			termsView.setFocusableInTouchMode(true);

			// termsView.setText(Html.fromHtml(getString(R.id.supporttext));

			CharSequence text = termsView.getText();

			final MovementMethod m = termsView.getMovementMethod();
			if (m == null || !(m instanceof LinkMovementMethod)) {
				termsView.setMovementMethod(LinkMovementMethod.getInstance());
			}

			text = setSpanBetweenTokens(text, "#", new ForegroundColorSpan(
					0xFF4444FF), new UnderlineSpan(), new ClickableSpan() {
				@Override
				public void onClick(final View widget) {
					openTerms(con);

				}
			});

			text = setSpanBetweenTokens(text, "##", new ForegroundColorSpan(
					0xFF4444FF), new UnderlineSpan(), new ClickableSpan() {
				@Override
				public void onClick(final View widget) {
					openPrivacy(con);

				}
			});

			termsView.setText(text);

		}
		return termsView;
	}

	protected void openPrivacy(Context con2) {
		// TODO Auto-generated method stub
		
		StartActivity.toebsite(con2, AllURL.privacyURL);
		
	}


	protected void openTerms(Context con2) {
		// TODO Auto-generated method stub
		StartActivity.toebsite(con2, AllURL.termURL);

	}


	/**
	 * Given either a Spannable String or a regular String and a token, apply
	 * the given CharacterStyle to the span between the tokens, and also remove
	 * tokens.
	 * <p>
	 * For example, {@code setSpanBetweenTokens("Hello ##world##!", "##", new
	 * ForegroundColorSpan(0xFFFF0000));} will return a CharSequence
	 * {@code "Hello world!"} with {@code world} in red.
	 * 
	 * @param text
	 *            The text, with the tokens, to adjust.
	 * @param token
	 *            The token string; there should be at least two instances of
	 *            token in text.
	 * @param cs
	 *            The style to apply to the CharSequence. WARNING: You cannot
	 *            send the same two instances of this parameter, otherwise the
	 *            second call will remove the original span.
	 * @return A Spannable CharSequence with the new style applied.
	 * 
	 * @see http 
	 *      ://developer.android.com/reference/android/text/style/CharacterStyle
	 *      .html
	 */

	public static CharSequence setSpanBetweenTokens(CharSequence text,
			final String token, final CharacterStyle... cs) {
		// Start and end refer to the points where the span will apply
		final int tokenLen = token.length();
		final int start = text.toString().indexOf(token) + tokenLen;
		final int end = text.toString().indexOf(token, start);

		if (start > -1 && end > -1) {
			// Copy the spannable string to a mutable spannable string
			final SpannableStringBuilder ssb = new SpannableStringBuilder(text);
			for (final CharacterStyle c : cs) {
				ssb.setSpan(c, start, end, 0);
			}

			// Delete the tokens before and after the span
			ssb.delete(end, end + tokenLen);
			ssb.delete(start - tokenLen, start);

			text = ssb;
		}

		return text;
	}
	
	private void showSamsungMessage() {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.app_name),
					"No Inernet connection");
			return;
		}
		/*
		 * 
		 * log entry now
		 */

		busyNow = new BusyDialog(con, true);
		busyNow.show();

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					final String response = HTTPHandler.dataFromGet(AllURL
							.getSamsungMsgURL());

					print.message("response is: " + response);

					if (!(response.contains("<!DOCTYPE html>"))) {
						final JSONObject obj = new JSONObject(response);

						publishStatus = obj.getJSONArray("result")
								.getJSONObject(0).optString("IsPublish");
					}

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

						if (busyNow != null) {
							busyNow.dismis();
						}

						if (publishStatus.equalsIgnoreCase("on")) {
							showSamsungWarning();
						}
					}

				});

			}
		});

		d.start();
	}

	private void showSamsungWarning() {
		// TODO Auto-generated method stub

		final Dialog dialog = new Dialog(con,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setTitle(null);

		final WindowManager.LayoutParams layoutParams = dialog.getWindow()
				.getAttributes();
		layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount = .7f;
		dialog.getWindow().setAttributes(layoutParams);

		dialog.setContentView(R.layout.samsung_warning);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		final LinearLayout closeButton = (LinearLayout) dialog
				.findViewById(R.id.warninglayout);
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});

		dialog.show();

	}

	public void exitCurrentActivity() {

		LogActivity.this.finish();

	}

	public void toastMessage(String mes) {

		Toast.makeText(con, mes, 1000).show();
	}

	private void initUI() {
		registrationRelative = (RelativeLayout) findViewById(R.id.registrationRelative);
		loginLayout = (RelativeLayout) findViewById(R.id.loginLayout);
		loginLinear = (LinearLayout) findViewById(R.id.loginLinear);
		forgetPasswordMain_ll = (LinearLayout) findViewById(R.id.forgetPasswordMain_ll);
		forgotPassword_tv = (TextView) findViewById(R.id.forgotPassword_tv);
		forgetpasswordRelative = (RelativeLayout) findViewById(R.id.forgetpasswordRelative);
		retrivePassword_ll = (LinearLayout) findViewById(R.id.retrivePassword_ll);
		enterEmailaddress_et = (EditText) findViewById(R.id.enterEmailaddress_et);

		secondmessageTextview1 = (TextView) findViewById(R.id.secondmessageTextview1);
		skipLogin_ll = (LinearLayout) findViewById(R.id.skipLogin_ll);
		logViaFb_ll = (LinearLayout) findViewById(R.id.logViaFb_ll);
		LoginWithEmailAddress_ll = (LinearLayout) findViewById(R.id.LoginWithEmailAddress_ll);
		memberLoginEmail_ll = (LinearLayout) findViewById(R.id.memberLoginEmail_ll);
		registrationUserLine = (LinearLayout) findViewById(R.id.registrationUserLine);
		emailAddress_et = (EditText) findViewById(R.id.emailAddress_et);
		password_et = (EditText) findViewById(R.id.password_et);
		verifyPassword_et = (EditText) findViewById(R.id.verifyPassword_et);
		memberEmail_et = (EditText) findViewById(R.id.memberEmail_et);
		memberPassword_et = (EditText) findViewById(R.id.memberPassword_et);
		faceboktextlog = (TextView) findViewById(R.id.faceboktextlog);
		registrationtextlog = (TextView) findViewById(R.id.registrationtextlog);
		afterregistrationlogintext = (TextView) findViewById(R.id.afterregistrationlogintext);
		afterregistrationlogintext1 = (TextView) findViewById(R.id.afterregistrationlogintext1);
		registrationtextlog1 = (TextView) findViewById(R.id.registrationtextlog1);
		registertextview = (TextView) findViewById(R.id.registertextview);
		loginUser_ll = (TextView) findViewById(R.id.loginUser_ll);
		skiptextview = (TextView) findViewById(R.id.skiptextview);
		longinalertmessagetextview = (TextView) findViewById(R.id.toplayoutText);

		skipLogin_ll.setOnClickListener(this);
		logViaFb_ll.setOnClickListener(this);
		LoginWithEmailAddress_ll.setOnClickListener(this);
		memberLoginEmail_ll.setOnClickListener(this);
		registrationUserLine.setOnClickListener(this);
		loginLinear.setOnClickListener(this);
		forgetPasswordMain_ll.setOnClickListener(this);
		retrivePassword_ll.setOnClickListener(this);

		final Typeface tf = Typeface.createFromAsset(getAssets(),
				"fond/ofront.otf");
		faceboktextlog.setTypeface(tf);
		registrationtextlog.setTypeface(tf);
		afterregistrationlogintext.setTypeface(tf);
		afterregistrationlogintext1.setTypeface(tf);
		registrationtextlog1.setTypeface(tf);
		registertextview.setTypeface(tf);
		loginUser_ll.setTypeface(tf);
		skiptextview.setTypeface(tf);
		memberEmail_et.setTypeface(tf);
		memberPassword_et.setTypeface(tf);
		enterEmailaddress_et.setTypeface(tf);
		emailAddress_et.setTypeface(tf);
		password_et.setTypeface(tf);
		verifyPassword_et.setTypeface(tf);
	}

	public void loginFacebook() {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}
		busyNow = new BusyDialog(con, true);
		busyNow.show();

		if (fb_session != null && fb_session.isOpened()) {
			makeMeRequest(fb_session);
			Log.i("Facebook Login State == >", "Facebook Login State");
		} else {
			if (fb_session == null) {
				fb_session = new Session(con);
			}
			Session.setActiveSession(fb_session);
			ConnectToFacebook();
			Log.i("Facebook not Login State == >", "Facebook Not login State");
		}

	}

	private void ConnectToFacebook() {
		final Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			Log.i("ConnectToFacebook  if == >", "ConnectToFacebook if");
			final OpenRequest newSession = new Session.OpenRequest(this);
			newSession.setCallback(callback);
			session.openForRead(newSession);
			try {
				final Session.OpenRequest request = new Session.OpenRequest(
						this);
				request.setPermissions(Arrays.asList("email"));
			} catch (final Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.i("ConnectToFacebook  else == >", "ConnectToFacebook else");
			Session.openActiveSession(this, true, callback);
		}
	}

	private final Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			makeMeRequest(session);
		}
	}

	private void makeMeRequest(final Session session) {
		final Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						try {
							// UserInfoDisplay(user);
							// FrieddsInfoDisplay(session);

							busyNow.dismis();

							// final String mail = user.asMap().get("email")
							// .toString().trim();
							final String fid = user.getId();

							// facebookLoginWithEmail(AllURL.getLoginWithFacebook(
							// mail, fid));

							final String url = AllURL.getRegister(fid, "",
									"facebook");
							facebookLoginWithEmail(url);

							// Log.e("Email", ">>>>>" +
							// user.asMap().get("email"));
							// Log.e("response", ">>>>>" + response.toString());

						} catch (final Exception e) {
							e.printStackTrace();
						}
					}

				});
		request.executeAsync();
	}

	// private void getEmail() {
	//
	// final Thread t = new Thread() {
	// @Override
	// public void run() {
	//
	// try {
	// // facebookConnector.getMe();
	//
	// userFbId = facebookConnector.getMyfbId();
	//
	// Log.e("get ID.......>>>", "" + userFbId);
	//
	// Log.e("FB permission ", ""
	// + facebookConnector.getFacebook().getSession()
	// .getPermissions().toString());
	//
	// runOnUiThread(new Runnable() {
	// @Override
	// public void run() {
	//
	// if (busyNow != null) {
	// busyNow.dismis();
	// }
	//
	// if (userFbId == null) {
	// AlertMessage
	// .showMessage(con, "error",
	// "Something went wrong. No Facebook user details found");
	// // toastMessage("Something went wrong.");
	// return;
	// }
	//
	// // toastMessage("user FB id is " + userFbId);
	//
	// final String url = AllURL.getRegister(userFbId, "",
	// "facebook");
	// facebookLoginWithEmail(url);
	//
	// }
	// });
	//
	// } catch (final Exception ex) {
	// Log.e(TAG, "Error sending msg", ex);
	// }
	// }
	// };
	// t.start();
	//
	// Log.e("eserEmail", "UserID" + userFbId);
	//
	// }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// uiHelper.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

		Log.e("stated", "onactivity");
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		// uiHelper.onSaveInstanceState(savedState);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.skipLogin_ll) {

			SharedPreferencesHelper.setUsingskip(con, true);
			SharedPreferencesHelper.setLogin(con, true);

			if (AppConstant.fromscreen.equalsIgnoreCase("registrationbtn")) {
				final Intent ii = new Intent(con, HomeActivity.class);
				startActivity(ii);
				exitCurrentActivity();

			}

			else if (AppConstant.fromscreen.equalsIgnoreCase("main")) {
				final Intent ii = new Intent(con, HomeActivity.class);
				startActivity(ii);
				exitCurrentActivity();

			} else if (AppConstant.fromscreen.equalsIgnoreCase("allswipe")) {

				/*
				 * final Intent ii = new Intent(con, MemeSwipeActivity.class);
				 * startActivity(ii);
				 */
				exitCurrentActivity();
			} else if (AppConstant.fromscreen.equalsIgnoreCase("motiswipe")) {
				// final Intent ii = new Intent(con,
				// MotivationalSwipeActivity.class);
				// startActivity(ii);
				exitCurrentActivity();

			} else if (AppConstant.fromscreen.equalsIgnoreCase("home")) {

				final Intent ii = new Intent(con, HomeActivity.class);
				startActivity(ii);
				exitCurrentActivity();

			} else if (AppConstant.fromscreen.equalsIgnoreCase("racyswipe")) {
				//
				// final Intent ii = new Intent(con,
				// RacySwipeActivity.class);
				// startActivity(ii);
				exitCurrentActivity();

			} else if (AppConstant.fromscreen.equalsIgnoreCase("tendswipe")) {
				//
				// final Intent ii = new Intent(con,
				// TendingSwipeScrinActivity.class);
				// startActivity(ii);
				exitCurrentActivity();

			} else if (AppConstant.fromscreen.equalsIgnoreCase("favorites")) {
				//
				// final Intent ii = new Intent(con,
				// FavoritesActivity.class);
				// startActivity(ii);
				exitCurrentActivity();
			} else if (AppConstant.fromscreen.equalsIgnoreCase("spiffyscreen")) {

				exitCurrentActivity();
			}
			// else
			// {
			//
			// final Intent i = new Intent(con, HomeActivity.class);
			// startActivity(i);
			// exitCurrentActivity();
			// }

		} else if (v.getId() == R.id.LoginWithEmailAddress_ll) {

			// Registration
			// LoginWithEmailAddress_ll

			/*
			 * reset old login field value
			 */

			memberEmail_et.setText("");
			memberPassword_et.setText("");

			AppConstant.loginScreenIndex = 1;

			final Animation animationFadeOut = AnimationUtils.loadAnimation(
					LogActivity.this, R.anim.fadeout);
			loginLayout.startAnimation(animationFadeOut);
			logViaFb_ll.startAnimation(animationFadeOut);

			final Animation animationFadeIn = AnimationUtils.loadAnimation(
					LogActivity.this, R.anim.fadein);
			registrationRelative.startAnimation(animationFadeIn);
			logViaFb_ll.startAnimation(animationFadeIn);

			LoginWithEmailAddress_ll.setVisibility(View.GONE);
			secondmessageTextview1.setVisibility(View.GONE);
			longinalertmessagetextview.setVisibility(View.GONE);
			skipLogin_ll.setVisibility(View.GONE);
			loginLayout.setVisibility(View.GONE);

			// logViaFb_ll.setVisibility(View.GONE);

			memberLoginEmail_ll.setVisibility(View.VISIBLE);// Login first level
			registrationRelative.setVisibility(View.VISIBLE);// Registration
																// form
			logViaFb_ll.setVisibility(View.VISIBLE); // facebook first level

		} else if (v.getId() == R.id.memberLoginEmail_ll) {

			// Login screen

			// SharedPreferencesHelper.setUsingskip(con, false);

			AppConstant.loginScreenIndex = 1;

			final Animation animationFadeOut = AnimationUtils.loadAnimation(
					LogActivity.this, R.anim.fadeout);

			registrationRelative.startAnimation(animationFadeOut);
			logViaFb_ll.startAnimation(animationFadeOut);

			final Animation animationFadeIn = AnimationUtils.loadAnimation(
					LogActivity.this, R.anim.fadein);
			loginLayout.startAnimation(animationFadeIn);
			logViaFb_ll.startAnimation(animationFadeIn);

			registrationRelative.setVisibility(View.GONE);
			secondmessageTextview1.setVisibility(View.GONE);
			memberLoginEmail_ll.setVisibility(View.GONE);
			skipLogin_ll.setVisibility(View.GONE);

			loginLayout.setVisibility(View.VISIBLE);// Login form
			longinalertmessagetextview.setVisibility(View.VISIBLE);
			LoginWithEmailAddress_ll.setVisibility(View.VISIBLE);
			// logViaFb_ll.setVisibility(View.GONE);
			logViaFb_ll.setVisibility(View.VISIBLE);

			forgotPassword_tv.setText(Html
					.fromHtml(getString(R.string.forgotPassword)));

		} else if (v.getId() == R.id.logViaFb_ll) {

			AppConstant.loginScreenIndex = 1;

			loginLayout.setVisibility(View.GONE);
			registrationRelative.setVisibility(View.GONE);
			longinalertmessagetextview.setVisibility(View.GONE);
			LoginWithEmailAddress_ll.setVisibility(View.VISIBLE);
			memberLoginEmail_ll.setVisibility(View.VISIBLE);
			skipLogin_ll.setVisibility(View.VISIBLE);
			// gonskipView.setVisibility(View.VISIBLE);
			loginFacebook();

		} else if (v.getId() == R.id.registrationUserLine) {
			// Registratiion

			checkData();

		} else if (v.getId() == R.id.loginLinear) {
			// Login

			checVeryfy();

		} else if (v.getId() == R.id.forgetPasswordMain_ll) {
			// gorgetpassword

			AppConstant.loginScreenIndex = 1;

			secondmessageTextview1.setVisibility(View.GONE);
			LoginWithEmailAddress_ll.setVisibility(View.GONE);
			logViaFb_ll.setVisibility(View.GONE);
			memberLoginEmail_ll.setVisibility(View.GONE);
			skipLogin_ll.setVisibility(View.GONE);
			longinalertmessagetextview.setVisibility(View.GONE);
			loginLayout.setVisibility(View.GONE);
			registrationRelative.setVisibility(View.GONE);
			forgetpasswordRelative.setVisibility(View.VISIBLE);
		} else if (v.getId() == R.id.retrivePassword_ll) {
			checkEmail();
		}

	}

	public void checkEmail() {
		emailaddress = enterEmailaddress_et.getText().toString().trim();

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

						enterEmailaddress_et.setText("");
						toastMessage(json.optString("message") + "");
						forgetpasswordRelative.setVisibility(View.VISIBLE);

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

	private void checVeryfy() {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		} else if (TextUtils.isEmpty(memberEmail_et.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterEmail));
			return;
		} else if (!ValidateEmail.validateEmail(memberEmail_et.getText()
				.toString())) {
			AlertMessage.showMessage(con, "Error",
					getString(R.string.PleaseEnterValidEmail));
			return;
		} else if (TextUtils.isEmpty(memberPassword_et.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterPassword));
			return;
		} else {
			email = memberEmail_et.getText().toString();
			passWord = memberPassword_et.getText().toString();
			typeche = "normal";
			final String url = AllURL.getLogin(email, passWord, typeche);
			loadLogin(url);
		}
	}

	protected void loadLogin(final String url) {
		// TODO Auto-generated method stub

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		// TODO Auto-generated method stub

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

				try {
					final JSONObject json = new JSONObject(new String(response));
					Log.e("login ", ">>" + new String(response));
					AppConstant.status = json.optString("status");
					AppConstant.messageregistration = json.optString("message");
					AppConstant.userID = json.optString("userID");

					if (AppConstant.status.equalsIgnoreCase("1")) {

						SharedPreferencesHelper.setLogin(con, true);

						SharedPreferencesHelper.setUserID(con,
								AppConstant.userID);

						SharedPreferencesHelper.setUsingskip(con, false);

						memberEmail_et.setText("");
						memberPassword_et.setText("");

						//
						// Toast.makeText(con,
						// getString(R.string.loginSuccessfully), 3000)
						// .show();
						// Using Login

						if (AppConstant.fromscreen
								.equalsIgnoreCase("registrationbtn")) {
							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("main")) {
							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("allswipe")) {

							/*
							 * final Intent ii = new Intent(con,
							 * MemeSwipeActivity.class); startActivity(ii);
							 */
							exitCurrentActivity();
						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("motiswipe")) {
							// final Intent ii = new Intent(con,
							// MotivationalSwipeActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("home")) {

							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("racyswipe")) {
							//
							// final Intent ii = new Intent(con,
							// RacySwipeActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("tendswipe")) {
							//
							// final Intent ii = new Intent(con,
							// TendingSwipeScrinActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("favorites")) {
							//
							// final Intent ii = new Intent(con,
							// FavoritesActivity.class);
							// startActivity(ii);
							exitCurrentActivity();
						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("okbtn")) {

							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("spiffyscreen")) {

							exitCurrentActivity();
						}

						// Logged-in successfully.

						toastMessage("Logged-in successfully.");
						// end

						// final Intent i = new Intent(con, HomeActivity.class);
						// startActivity(i);
						// finish();

					} else if (AppConstant.status.equalsIgnoreCase("0")) {

						/*
						 * error
						 */
						AlertMessage.showMessage(con, "",
								json.optString("message") + "");
					} else if (AppConstant.status.equalsIgnoreCase("2")) {

						/*
						 * need verification
						 */

						DialogUtils.createDialog(con, "",
								json.optString("message"),
								"Resend Verification", "Okay",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										dialog.dismiss();

										resendLink(AllURL
												.getVerificationLink(email));

									}

								}, new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub

										arg0.dismiss();
									}
								}).show();

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

	public void RandomKeyhash() {

		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo("com.sdl.memes",
					PackageManager.GET_SIGNATURES);
			for (final Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				final String something = new String(Base64.encode(md.digest(),
						0));
				// String something = new
				// String(Base64.encodeBytes(md.digest()));
				Log.e("hash key", something);
			}
		} catch (final NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		} catch (final NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (final Exception e) {
			Log.e("exception", e.toString());
		}
	}

	protected void facebookLoginWithEmail(final String url) {

		// toastMessage("Got facebook data, now hitting into amazon");

		busyNow = new BusyDialog(con, true);
		busyNow.show();

		Log.e("FB login URL", url + "");

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

					Log.e("Server response : ", ":" + new String(response));

					final String messageStr = json.optString("message");
					AppConstant.userID = json.optString("userID");

					Log.e("messageStr", ":" + messageStr);
					Log.e("userID", ":" + AppConstant.userID);

					if (AppConstant.status.equalsIgnoreCase("1")) {

						AppConstant.flag = true;

						SharedPreferencesHelper.setLogin(con, true);

						SharedPreferencesHelper.setUsingskip(con, false);

						SharedPreferencesHelper.setUserID(con,
								AppConstant.userID);
						PersistentUser.setLogin(con);
						// Check Skip or Login .........................fb

						Log.e("Screen name ;", ">>" + AppConstant.fromscreen);

						if (AppConstant.fromscreen
								.equalsIgnoreCase("registrationbtn")) {
							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						}

						else if (AppConstant.fromscreen
								.equalsIgnoreCase("main")) {
							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("allswipe")) {

							// final Intent ii = new Intent(con,
							// MemeSwipeActivity.class);
							// startActivity(ii);
							exitCurrentActivity();
						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("motiswipe")) {
							// final Intent ii = new Intent(con,
							// MotivationalSwipeActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("home")) {

							final Intent ii = new Intent(con,
									HomeActivity.class);
							startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("racyswipe")) {
							//
							// final Intent ii = new Intent(con,
							// RacySwipeActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("tendswipe")) {

							// final Intent ii = new Intent(con,
							// TendingSwipeScrinActivity.class);
							// startActivity(ii);
							exitCurrentActivity();

						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("favorites")) {

							// final Intent ii = new Intent(con,
							// FavoritesActivity.class);
							// startActivity(ii);
							exitCurrentActivity();
						} else if (AppConstant.fromscreen
								.equalsIgnoreCase("spiffyscreen")) {

							exitCurrentActivity();
						}
						// else{
						// final Intent ii = new Intent(con,
						// HomeActivity.class);
						// startActivity(ii);
						// }

						toastMessage("Logged-in successfully.");

						// AppConstant.flag = true;

					} else {

						AlertMessage.showMessage(con, "", messageStr + "");
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

	protected void resendLink(final String url) {

		busyNow = new BusyDialog(con, true);
		busyNow.show();

		Log.e("FB login URL", url + "");

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

					Log.e("Server response : ", ":" + new String(response));

					final String messageStr = json.optString("message");

					AlertMessage.showMessage(con, "", messageStr + "");

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

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (AppConstant.loginScreenIndex == 0) {

				exitCurrentActivity();

			} else {

				forgetpasswordRelative.setVisibility(View.GONE);
				loginLayout.setVisibility(View.GONE);
				registrationRelative.setVisibility(View.GONE);
				longinalertmessagetextview.setVisibility(View.GONE);

				LoginWithEmailAddress_ll.setVisibility(View.VISIBLE);
				memberLoginEmail_ll.setVisibility(View.VISIBLE);
				secondmessageTextview1.setVisibility(View.VISIBLE);
				logViaFb_ll.setVisibility(View.VISIBLE);
				skipLogin_ll.setVisibility(View.VISIBLE);
				// gonskipView.setVisibility(View.VISIBLE);

				AppConstant.loginScreenIndex = 0;
			}

		}

		return true;

	}

	private void checkData() {
		if (TextUtils.isEmpty(emailAddress_et.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterEmail));
			return;
		} else if (!ValidateEmail.validateEmail(emailAddress_et.getText()
				.toString())) {
			AlertMessage.showMessage(con, "Error",
					getString(R.string.PleaseEnterValidEmail));
			return;
		} else if (TextUtils.isEmpty(password_et.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterPassword));
			return;
		} else if (password_et.getText().toString()
				.equals(verifyPassword_et.getText().toString()) == false) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PasswordDoesNotMatch));
			return;
		} else {
			userEmail = emailAddress_et.getText().toString();
			password = password_et.getText().toString();
			type = "normal";

			final String url = AllURL.getRegister(userEmail, password, type);
			loadReg(url);

		}
	}

	protected void loadReg(final String url) {

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

						// toastMessage(getString(R.string.RegistationSuccessfully));

						DialogUtils.createDialog(con, "",
								json.optString("message"), "Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										emailAddress_et.setText("");
										password_et.setText("");
										verifyPassword_et.setText("");

										AppConstant.loginScreenIndex = 1;

										final Animation animationFadeOut = AnimationUtils
												.loadAnimation(
														LogActivity.this,
														R.anim.fadeout);

										registrationRelative
												.startAnimation(animationFadeOut);
										logViaFb_ll
												.startAnimation(animationFadeOut);

										final Animation animationFadeIn = AnimationUtils
												.loadAnimation(
														LogActivity.this,
														R.anim.fadein);
										loginLayout
												.startAnimation(animationFadeIn);
										logViaFb_ll
												.startAnimation(animationFadeIn);

										registrationRelative
												.setVisibility(View.GONE);
										secondmessageTextview1
												.setVisibility(View.GONE);
										memberLoginEmail_ll
												.setVisibility(View.GONE);
										skipLogin_ll.setVisibility(View.GONE);

										loginLayout.setVisibility(View.VISIBLE);// Login
																				// form
										longinalertmessagetextview
												.setVisibility(View.VISIBLE);
										LoginWithEmailAddress_ll
												.setVisibility(View.VISIBLE);
										// logViaFb_ll.setVisibility(View.GONE);
										logViaFb_ll.setVisibility(View.VISIBLE);

										forgotPassword_tv.setText(Html
												.fromHtml(getString(R.string.forgotPassword)));

									}

								}).show();
						;

					} else {

						AlertMessage.showMessage(con, "",
								json.optString("message"));
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
