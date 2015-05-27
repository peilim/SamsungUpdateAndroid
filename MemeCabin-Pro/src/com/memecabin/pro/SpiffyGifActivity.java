package com.memecabin.pro;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.MMSSender;
import com.aacom.memecabin.utils.MakeBitmapAndSave;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.testnetwork.HTTPHandler;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.print.print;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.google.android.gms.ads.InterstitialAd;
import com.learnncode.demotwitterimagepost.HelperMethods;
import com.learnncode.demotwitterimagepost.HelperMethods.TwitterCallback;
import com.learnncode.demotwitterimagepost.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.AllSpicyInfo;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.SpicyParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Target;
import com.memecabin.pro.R;

public class SpiffyGifActivity extends Activity implements OnClickListener {

	private Context context, con;
	int mNum = 0;
	RelativeLayout viewfirstORdecide, memeallshare_rl;
	LinearLayout firstVp_ll, messageNotShow_ll, umCancle_ll;
	ImageView memeSwipeRel_im, memeSwipeShare_im;
	ImageView memeShareSave_im, memeShareFaceBook_im, memeShareTwitter_im,
			memeShareEmail_im, memeShareMMS_im, reportImage;

	private static final String TAG = "FacebookSample";
	RelativeLayout sharecommntsviewmoti;
	EditText commentstextforshare;
	LinearLayout sharebtfbortwitter;
	TextView buttontexshare;
	ImageView markBookmark, motiLike_im;
	TextView motiliketotal_tv;
	String like_count = "";
	DatabaseHandler db;

	ViewPager _viewPager;
	MyPagerAdapterWeb myPagerAdapter;
	Vector<ImageInfo> imageInfos;
	ImageInfo query;
	public int currentIndex = 0;
	int counter = 0;
	RelativeLayout rlDecide, bookMarkView;
	TextView txtLogin;
	TextView txtNoThanks;

	private Target loadTarget;
	LinearLayout swipeHeadmoti_ll;
	Button MoveBookmarkHere, GotoBookmark, Cancel;

	LinearLayout motiHere_ll, motiFirst_ll;
	RelativeLayout lastshowview;
	TextView titlespiffy;

	Handler adHandler;
	private InterstitialAd mInterstitialAd;

	RelativeLayout bookmarkshow;
	Animation animationFadeOut;

	final Handler handler = new Handler();
	private InputStream shareStream = null;
	private String shareType = "";

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	/* Jin Remove Mopub ADS
	private MoPubInterstitial mInterstitial;
	private MoPubView moPubView;
	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spiffy_gifts);
		context = this;
		con = this;
		activity = this;
		db = new DatabaseHandler(context);
		animationFadeOut = AnimationUtils.loadAnimation(con, R.anim.fadeout);
		fb_session = Session.openActiveSessionFromCache(con);

		initUI();

		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
			initAd();
			*/
		}

	}

	public void bookmarkMessage() {
		final BookmarkMessage ob = new BookmarkMessage(con);
		ob.openBookmarkMessage();
	}

	private void initUI() {
		// TODO Auto-generated method stub

		memeSwipeRel_im = (ImageView) findViewById(R.id.memeSwipeRel_im);
		memeSwipeShare_im = (ImageView) findViewById(R.id.memeSwipeShare_im);

		memeShareSave_im = (ImageView) findViewById(R.id.memeShareSave_im);
		memeShareFaceBook_im = (ImageView) findViewById(R.id.memeShareFaceBook_im);
		memeShareTwitter_im = (ImageView) findViewById(R.id.memeShareTwitter_im);
		memeShareEmail_im = (ImageView) findViewById(R.id.memeShareEmail_im);
		memeShareMMS_im = (ImageView) findViewById(R.id.memeShareMMS_im);
		reportImage = (ImageView) findViewById(R.id.reportImage);
		markBookmark = (ImageView) findViewById(R.id.markBookmark);
		motiLike_im = (ImageView) findViewById(R.id.motiLike_im);

		MoveBookmarkHere = (Button) findViewById(R.id.MoveBookmarkHere);
		GotoBookmark = (Button) findViewById(R.id.GotoBookmark);
		Cancel = (Button) findViewById(R.id.Cancel);

		viewfirstORdecide = (RelativeLayout) findViewById(R.id.viewfirstORdecide);
		memeallshare_rl = (RelativeLayout) findViewById(R.id.memeallshare_rl);
		sharecommntsviewmoti = (RelativeLayout) findViewById(R.id.sharecommntsviewmoti);
		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		bookMarkView = (RelativeLayout) findViewById(R.id.bookMarkView);
		lastshowview = (RelativeLayout) findViewById(R.id.lastshowview);
		bookmarkshow = (RelativeLayout) findViewById(R.id.bookmarkshow);

		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);

		firstVp_ll = (LinearLayout) findViewById(R.id.firstVp_ll);
		messageNotShow_ll = (LinearLayout) findViewById(R.id.messageNotShow_ll);
		umCancle_ll = (LinearLayout) findViewById(R.id.umCancle_ll);
		motiHere_ll = (LinearLayout) findViewById(R.id.motiHere_ll);
		motiFirst_ll = (LinearLayout) findViewById(R.id.motiFirst_ll);

		// bookmrkshowll = (LinearLayout) findViewById(R.id.bookmrkshowll);

		buttontexshare = (TextView) findViewById(R.id.buttontexshare);
		motiliketotal_tv = (TextView) findViewById(R.id.motiliketotal_tv);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);
		titlespiffy = (TextView) findViewById(R.id.titlespiffy);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);

		memeallshare_rl.setOnClickListener(this);
		memeSwipeRel_im.setOnClickListener(this);
		memeSwipeShare_im.setOnClickListener(this);
		firstVp_ll.setOnClickListener(this);
		messageNotShow_ll.setOnClickListener(this);
		umCancle_ll.setOnClickListener(this);

		memeShareSave_im.setOnClickListener(this);
		memeShareFaceBook_im.setOnClickListener(this);
		memeShareTwitter_im.setOnClickListener(this);
		memeShareEmail_im.setOnClickListener(this);
		memeShareMMS_im.setOnClickListener(this);
		reportImage.setOnClickListener(this);

		sharebtfbortwitter.setOnClickListener(this);
		sharecommntsviewmoti.setOnClickListener(this);
		markBookmark.setOnClickListener(this);
		motiLike_im.setOnClickListener(this);
		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);
		MoveBookmarkHere.setOnClickListener(this);
		GotoBookmark.setOnClickListener(this);
		Cancel.setOnClickListener(this);

		motiHere_ll.setOnClickListener(this);
		motiFirst_ll.setOnClickListener(this);

		bookmarkshow.setOnClickListener(this);

		listSpicy(AllURL.getspiffyMemesByPage(AppConstant.userID, 1, 100000));

		if (!SharedPreferencesHelper.getBookMarkMessate(con)) {

			bookmarkshow.setVisibility(View.VISIBLE);
		} else {
			bookmarkshow.setVisibility(View.GONE);
		}

	}

	public void setBack(View v) {
		AnalyticsTracker.sendTrackData(this,
				R.string.analytics_go_back_to_spiffy_gifts_activity);
		this.finish();
	}

	public void toastMessage(String mes) {

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// @Override
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// uiHelper.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

		Log.e("stated", "onactivity");
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

			getbitmapFromServerForShare("facebook");
			// makeMeRequest(globalbitmap, fbMsg, fbCaption, con);
		}
	}

	public boolean makeMeRequest(final String msg) {
		fb_session = Session.openActiveSessionFromCache(con);
		fb_session
				.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						activity, AppConstant.PERMISSIONS));

		final Bundle postParams = new Bundle();
		// postParams.putString("name", "Facebook SDK for Android");
		// postParams.putString("caption",
		// "Build great social apps and get more installs.");
		// postParams.putString("description",
		// "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		// postParams.putString("link",
		// "https://developers.facebook.com/android");
		// postParams.putString("picture",
		// "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
		// postParams.putString("title", title);
		// b.putString("name",title);
		// postParams.putString("link", url);
		postParams.putString("message", msg);
		// postParams.putString("caption", caption);
		final Request.Callback callback = new Request.Callback() {
			@Override
			public void onCompleted(Response response) {
				Log.e("Sonuc", ">>" + response.toString());

				facebookFlag = true;
				Toast.makeText(con, "Facebook message posted successfully",
						Toast.LENGTH_LONG).show();

			}
		};

		final Request request = new Request(fb_session, "me/feed", postParams,
				HttpMethod.POST, callback);
		final RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

		return facebookFlag;

	}

	// public boolean makeMeRequest(final Bitmap b, final String msg,
	// final String caption, final Context con1) {
	//
	//
	// fb_session = Session.openActiveSessionFromCache(con);
	// fb_session
	// .requestNewPublishPermissions(new Session.NewPermissionsRequest(
	// activity, AppConstant.PERMISSIONS));
	// final Request uploadRequest = Request.newUploadPhotoRequest(fb_session,
	// AppConstant.fbImage, new Request.Callback() {
	// @Override
	// public void onCompleted(Response response) {
	//
	// facebookFlag=true;
	//
	// Log.e("Facebook respors ", ">>" + response.toString());
	// AppConstant.fbImage = null;
	//
	// Toast.makeText(con1, "Photo uploaded successfully",
	// Toast.LENGTH_LONG).show();
	//
	// }
	// });
	// final Bundle param = uploadRequest.getParameters();
	// param.putString("message", msg);
	//
	// uploadRequest.setParameters(param);
	// uploadRequest.executeAsync();
	//
	// return facebookFlag;
	// }

	public void requestPermissions() {
		final Session s = Session.getActiveSession();
		if (s != null) {
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					activity, AppConstant.PERMISSIONS));
		}
	}

	public boolean checkPermissions() {
		final Session s = Session.getActiveSession();
		if (s != null) {
			Log.e("Facebook Permision", ">>" + s.getPermissions());

			return s.getPermissions().contains("publish_actions");
		} else {
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.memeSwipeRel_im) {
			// Show View for next three action in below.
			if (SharedPreferencesHelper.getSp1MessagenotShow(context)) {
				viewfirstORdecide.setVisibility(View.GONE);
			} else {
				viewfirstORdecide.setVisibility(View.VISIBLE);
			}

		} else if (v.getId() == R.id.firstVp_ll) {
			// Show first Meme
			_viewPager.setCurrentItem(0);
			viewfirstORdecide.setVisibility(View.GONE);

		} else if (v.getId() == R.id.messageNotShow_ll) {
			// This View not Show next time.
			SharedPreferencesHelper.setSp1MessagenotShow(context, true);

			viewfirstORdecide.setVisibility(View.GONE);

		} else if (v.getId() == R.id.umCancle_ll) {
			// This view Invisible.
			viewfirstORdecide.setVisibility(View.GONE);

		} else if (v.getId() == R.id.memeSwipeShare_im) {
			// Share view show
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			memeallshare_rl.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.memeallshare_rl) {
			// Share view gone
			memeallshare_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.memeShareSave_im) {
			// Local Save in Device
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_memesavelocaldevice);
			getbitmapFromServer();

		} else if (v.getId() == R.id.memeShareFaceBook_im) {
			// Share Facebook
			AnalyticsTracker.sendTrackData(this, R.string.analytics_share_fb);
			sharetype("fb");

		} else if (v.getId() == R.id.memeShareTwitter_im) {
			// Share Twitter
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_twitter);
			sharetype("twitter");
			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.memeShareEmail_im) {
			// Meme Send via Email
			AnalyticsTracker.sendTrackData(this, R.string.analytics_send_email);
			// getbitmapFromServerForShare("email");
			getGIFFromServerForShare("email");

		} else if (v.getId() == R.id.memeShareMMS_im) {
			// Meme Send via MMS
			AnalyticsTracker.sendTrackData(this, R.string.analytics_send_sms);
			// getbitmapFromServerForShare("mms");
			getGIFFromServerForShare("mms");

		} else if (v.getId() == R.id.reportImage) {
			// Report Show

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_report_admin);
			AppConstant.spiffy = "spiffy";
			AppConstant.image = query.getImageUrl();
			AppConstant.imageid = query.getImageID();
			final Intent i = new Intent(context, ReportActivity.class);
			startActivity(i);

		} else if (v.getId() == R.id.sharebtfbortwitter) {
			// Final Share button
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_sharingoption);

			if (SharedPreferencesHelper.getFbmeme(context)) {
				SharedPreferencesHelper.setFbmeme(context, false);

				fb_session = Session.openActiveSessionFromCache(con);

				if (fb_session != null && fb_session.isOpened()) {

					getbitmapFromServerForShare("facebook");
					Log.e("Facebook Login State == >", "Facebook Login State");
				} else {
					if (fb_session == null) {
						fb_session = new Session(con);
					}
					Session.setActiveSession(fb_session);
					ConnectToFacebook();
					Log.e("Facebook not Login State == >",
							"Facebook Not login State");
				}

				// if (facebookConnector.getFacebook().isSessionValid()) {
				//
				// getbitmapFromServerForShare("facebook");
				//
				// // loadData(imageurl, "facebook");
				// } else {
				// final SessionEvents.AuthListener listener = new
				// SessionEvents.AuthListener() {
				//
				// @Override
				// public void onAuthSucceed() {
				// getbitmapFromServerForShare("facebook");
				//
				// // loadData(imageurl, "facebook");
				// }
				//
				// @Override
				// public void onAuthFail(String error) {
				//
				// }
				// };
				// SessionEvents.addAuthListener(listener);
				// facebookConnector.login();
				// }

			} else if (SharedPreferencesHelper.getTwittermeme(context)) {
				SharedPreferencesHelper.setTwittermeme(context, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.sharecommntsviewmoti) {
			// Share view gone.
			sharecommntsviewmoti.setVisibility(View.GONE);

		} else if (v.getId() == R.id.markBookmark) {
			// Book Mark

			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_bookmark);
			bookMarkView.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.motiLike_im) {
			// Like / Dislike on .gif

			if (SharedPreferencesHelper.getUsingskip(context)) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);

				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_like_or_unlike_meme);
				// if (db.isLikeImage(AppConstant.imageid, "2"))

				try {
					if (query.getIsLike().equalsIgnoreCase("1"))

					{
						final String urlunlike = AllURL.getDisLike(
								AppConstant.imageid,
								SharedPreferencesHelper.getUserID(context));

						Log.e("urlunlike: ", ">>> " + urlunlike);

						disLikeMeme(urlunlike);
					} else {
						final String urllike = AllURL.getLikeUpdate(
								AppConstant.imageid,
								SharedPreferencesHelper.getUserID(context));

						Log.e("urllike: ", ">>> " + urllike);

						updatLikeMeme(urllike);
					}

				} catch (final Exception e) {

				}

			}

		} else if (v.getId() == R.id.rlDecide) {
			// Decide view (Skip or Login user)
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_viewdeceide);
			rlDecide.setVisibility(View.GONE);

		} else if (v.getId() == R.id.txtNoThanks) {
			// View gone
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtLogin) {
			// Need login

			AnalyticsTracker.sendTrackData(this, R.string.analytics_needlogin);

			AppConstant.fromscreen = "spiffyscreen";
			AppConstant.position = currentIndex;

			final Intent loginIntent = new Intent(context, LogActivity.class);
			startActivity(loginIntent);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.MoveBookmarkHere) {
			// Move Bookmark
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_setbookmark);

			SharedPreferencesHelper.setBookmark(context,
					_viewPager.getCurrentItem());
			bookMarkView.setVisibility(View.GONE);
		} else if (v.getId() == R.id.GotoBookmark) {
			// Go bookmark
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_bookmeme);

			try {
				final int bookmarkPos = SharedPreferencesHelper
						.getBookmark(context);
				if (bookmarkPos < 0) {
					Toast.makeText(context, "No bookmark found",
							Toast.LENGTH_SHORT).show();
				} else {
					_viewPager.setCurrentItem(bookmarkPos);
				}
			} catch (final Exception ex) {
			}

			bookMarkView.setVisibility(View.GONE);

		} else if (v.getId() == R.id.Cancel) {
			// Cancel view
			bookMarkView.setVisibility(View.GONE);
		} else if (v.getId() == R.id.motiHere_ll) {
			// Keep me here
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keepherememe);

			try {

				_viewPager.setCurrentItem(AppConstant.position - 1);
			} catch (final Exception e) {

			}
			lastshowview.setVisibility(View.GONE);

		} else if (v.getId() == R.id.motiFirst_ll) {
			// Back to the first Spiffy
			AnalyticsTracker.sendTrackData(this, R.string.analytics_goto1meme);

			try {
				_viewPager.setCurrentItem(0);
			} catch (final Exception e) {

			}
			lastshowview.setVisibility(View.GONE);

		} else if (v.getId() == R.id.bookmarkshow) {
			SharedPreferencesHelper.setBookMarkMessate(con, true);
			bookmarkshow.setVisibility(View.GONE);
		}
	}

	private void getGIFFromServerForShare(final String type) {
		// TODO Auto-generated method stub

		final BusyDialog busyDialog = new BusyDialog(con, true);
		busyDialog.show();

		final Thread d = new Thread(new Runnable() {

			String filePath = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				shareStream = getBitmapFromUrl(AppConstant.imageBaseUrl
						+ AppConstant.image);

				byte[] bytes;
				try {
					bytes = IOUtils.toByteArray(shareStream);
					filePath = MakeBitmapAndSave
							.saveGIFToDevice(context, bytes);

				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						if (busyDialog != null) {
							busyDialog.dismis();
						}

						if (type.equalsIgnoreCase("email")) {
							final Intent exportMessageIntent = new Intent(
									android.content.Intent.ACTION_SEND_MULTIPLE);
							exportMessageIntent.setType("text/plain");
							exportMessageIntent
									.setType("application/octet-stream");
							final ArrayList<Uri> uris = new ArrayList<Uri>();

							final String[] filePaths = new String[] { filePath };
							for (final String file : filePaths) {
								final File fileIn = new File(file);
								final Uri u = Uri.fromFile(fileIn);
								uris.add(u);
							}

							exportMessageIntent.putParcelableArrayListExtra(
									Intent.EXTRA_STREAM, uris);

							exportMessageIntent.putExtra(Intent.ACTION_DEFAULT,
									"test/");

							exportMessageIntent.putExtra(Intent.EXTRA_SUBJECT,
									"An amazing meme from the MemeCabin app!");

							exportMessageIntent
									.putExtra(
											Intent.EXTRA_TEXT,
											"I sent you this from the MemeCabin app! Download it today for Apple and Android phones and tablets at www.memecabin.com!");

							startActivity(exportMessageIntent);
						} else if (type.equalsIgnoreCase("mms")) {
							MMSSender
									.mmsImageToAll(
											context,
											"Just wanted to share this meme from the MemeCabin app!",
											new File(filePath));
						}

					}
				});

			}
		});

		d.start();

	}

	private void getbitmapFromServer() {
		// TODO Auto-generated method stub

		final BusyDialog busyDialog = new BusyDialog(context, true);
		busyDialog.show();

		final Thread d = new Thread(new Runnable() {

			String filePath = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				shareStream = getBitmapFromUrl(AppConstant.imageBaseUrl
						+ AppConstant.image);

				byte[] bytes;
				try {
					bytes = IOUtils.toByteArray(shareStream);
					filePath = MakeBitmapAndSave
							.saveGIFToDevice(context, bytes);

				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						if (busyDialog != null) {
							busyDialog.dismis();
						}

						if (filePath == null) {
							Toast.makeText(context, "Can't save the image!",
									500).show();

						} else {
							Toast.makeText(context,
									"Image is saved successfully", 500).show();

						}
					}
				});

			}
		});

		d.start();

	}

	private void sharetype(String type) {
		// TODO Auto-generated method stub

		if (type.equalsIgnoreCase("fb")) {

			SharedPreferencesHelper.setFbmeme(context, true);
			sharecommntsviewmoti.setVisibility(View.VISIBLE);
			commentstextforshare.setText("");
			// Remove : Sharing this from the MemeCabin app! Get it on Android
			// and iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");

		} else if (type.equalsIgnoreCase("twitter")) {

			SharedPreferencesHelper.setTwittermeme(context, true);
			sharecommntsviewmoti.setVisibility(View.VISIBLE);
			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
		}

	}

	private void listSpicy(final String url) {
		// TODO Auto-generated method stub

		if (!NetInfo.isOnline(context)) {
			AlertMessage.showMessage(context, "Internet Connection Problem",
					"Please check the Internet Connection");
			return;
		}

		final BusyDialog busyNow = new BusyDialog(context, true);
		busyNow.show();

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					final String respones_results = HTTPHandler
							.GetDataFromURL(url);

					Log.e("URL is ", ">>" + url);

					Log.e("Response", ">>" + respones_results);

					try {
						if (SpicyParser.connect(context, respones_results)) {

							Log.e("Parse completed", ">>");

						}

						for (final ImageInfo in : AllSpicyInfo
								.getAllSpicyInfo()) {
							Log.e("image id", in.getImageID());
							Log.e("image url", in.getImageUrl());
						}
					} catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						busyNow.dismis();

						if (AppConstant.status.equalsIgnoreCase("1")) {

							imageInfos = AllSpicyInfo.getAllSpicyInfo();

							/*
							 * add blank object to deterimine last one
							 */
							// final ImageInfo tempInfo = new ImageInfo();
							// imageInfos.addElement(tempInfo);

							swipeHeadmoti_ll = (LinearLayout) findViewById(R.id.swipeHeadmoti_ll);
							_viewPager = (ViewPager) findViewById(R.id._viewPager);
							myPagerAdapter = new MyPagerAdapterWeb(context,
									imageInfos, swipeHeadmoti_ll, bookmarkshow);
							_viewPager.setAdapter(myPagerAdapter);

							// Start ViewPager

							AppConstant.position = _viewPager.getCurrentItem();
							query = imageInfos.elementAt(AppConstant.position);

							if (query.getTitle().length() == 0) {
								swipeHeadmoti_ll
										.setBackgroundColor(Color.TRANSPARENT);
								titlespiffy.setText("");
							} else {
								titlespiffy.setText(query.getTitle());
								swipeHeadmoti_ll
										.setBackgroundResource(R.drawable.rounded_transparent);
							}

							AppConstant.image = query.getImageUrl();
							AppConstant.imageid = query.getImageID();
							motiliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							if (query.getIsLike().equalsIgnoreCase("1")) {

								motiLike_im
										.setImageResource(R.drawable.motilike);
							} else {
								motiLike_im
										.setImageResource(R.drawable.beforelike);
							}

							_viewPager
									.setOnPageChangeListener(new OnPageChangeListener() {

										@Override
										public void onPageSelected(int arg0) {
											// TODO Auto-generated method stub
											// counter++;

											swipeHeadmoti_ll
													.setVisibility(View.VISIBLE);

											final int index = _viewPager
													.getCurrentItem();

											AppConstant.position = index;
											// currentIndex = index;

											query = imageInfos.elementAt(index);
											AppConstant.imageid = query
													.getImageID();
											AppConstant.image = query
													.getImageUrl();
											// titlespiffy.setText(query.getTitle());
											if (query.getTitle().length() == 0) {
												swipeHeadmoti_ll
														.setBackgroundColor(Color.TRANSPARENT);
												titlespiffy.setText("");
											} else {
												titlespiffy.setText(query
														.getTitle());
												swipeHeadmoti_ll
														.setBackgroundResource(R.drawable.rounded_transparent);
											}
											motiliketotal_tv
													.setText(getResources()
															.getString(
																	R.string.totallikes)
															+ "("
															+ query.getLikeCount()
															+ ")");

											if (query.getIsLike()
													.equalsIgnoreCase("1")) {

												motiLike_im
														.setImageResource(R.drawable.motilike);
											} else {
												motiLike_im
														.setImageResource(R.drawable.beforelike);
											}
											if (!SharedPreferencesHelper
													.isAddDisable(con)) {

												counter++;
												if (counter == 20) {

													/* Jin Remove Mopub ADS
													initAd();
													*/

													counter = 0;
												}
											}

											final int vsize = imageInfos.size();
											if (index == vsize - 1) {
												lastshowview
														.setVisibility(View.VISIBLE);

											}

										}

										@Override
										public void onPageScrolled(int arg0,
												float arg1, int arg2) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onPageScrollStateChanged(
												int arg0) {
											// TODO Auto-generated method stub

										}
									});

							// End ViewPager

						} else {

							Toast.makeText(getApplicationContext(),
									"No data Found", 3000).show();
						}

					}

				});

			}
		});

		d.start();

	}

	private void getbitmapFromServerForShare(final String shareType1) {

		shareType = shareType1;

		final BusyDialog busyDialog = new BusyDialog(context, true);
		busyDialog.show();

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				shareStream = getBitmapFromUrl(AppConstant.imageBaseUrl
						+ AppConstant.image);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						if (busyDialog != null) {
							busyDialog.dismis();
						}

						handler.post(shareView);

					}
				});

			}
		});

		d.start();

	}

	public Runnable shareView = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (shareType.equalsIgnoreCase("facebook")) {

				final String text = commentstextforshare.getText().toString();

				final Thread t = new Thread() {
					@Override
					public void run() {

						try {

							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									if (makeMeRequest(text + "\n"
											+ AppConstant.imageBaseUrl
											+ AppConstant.image)) {

										// toastMessage("Image posted successfully!");
										commentstextforshare.setText("");
										sharecommntsviewmoti
												.setVisibility(View.GONE);
									} else {

										// toastMessage("Facebook post failed!");
										commentstextforshare.setText("");
										sharecommntsviewmoti
												.setVisibility(View.GONE);
									}

								}
							});

						} catch (final Exception ex) {
							Log.e(TAG, "Error sending msg", ex);
						}
					}
				};
				t.start();

			} else if (shareType.equalsIgnoreCase("email")) {

				final String imagePath = AppConstant.imageBaseUrl
						+ AppConstant.image;

				final Intent exportMessageIntent = new Intent(
						android.content.Intent.ACTION_SEND_MULTIPLE);
				exportMessageIntent.setType("text/plain");
				exportMessageIntent.setType("application/octet-stream");

				exportMessageIntent.putExtra(Intent.ACTION_DEFAULT, "test/");

				exportMessageIntent.putExtra(Intent.EXTRA_SUBJECT,
						"An amazing meme from the MemeCabin app!");

				exportMessageIntent
						.putExtra(
								Intent.EXTRA_TEXT,
								"I sent you this from the MemeCabin app! Download it today for Apple and Android phones and tablets at www.memecabin.com! \n"
										+ imagePath);

				startActivity(exportMessageIntent);
			} else if (shareType.equalsIgnoreCase("twitter")) {

				final String value = commentstextforshare.getText().toString();

				AppConstant.twitterImage = AppConstant.imageBaseUrl
						+ AppConstant.image;

				if (LoginActivity.isActive(context)) {

					SharedPreferencesHelper.setTwitterFlag(context, false);

					final BusyDialog busyDialog = new BusyDialog(context, true);
					busyDialog.show();

					try {

						HelperMethods.postToTwitterWithInputStream(context,
								(Activity) context, shareStream, value,
								new TwitterCallback() {

									@Override
									public void onFinsihed(Boolean response) {
										if (busyDialog != null) {
											busyDialog.dismis();
										}

										Log.d(TAG,
												"----------------response----------------"
														+ response);
										Toast.makeText(
												context,
												getString(R.string.image_posted_on_twitter),
												Toast.LENGTH_SHORT).show();
										commentstextforshare.setText("");
										sharecommntsviewmoti
												.setVisibility(View.GONE);

									}
								});

					} catch (final Exception ex) {
						if (busyDialog != null) {
							busyDialog.dismis();
						}
					}
				} else {

					SharedPreferencesHelper.setTwitterFlag(context, true);

					startActivity(new Intent(context, LoginActivity.class));
				}

			} else if (shareType.equalsIgnoreCase("mms")) {

				final String imagePath = AppConstant.imageBaseUrl
						+ AppConstant.image;

				Log.e("imagePath", ">>" + imagePath);

				MMSSender.mmsImageToAllWithoutImage(context,
						"Just wanted to share this meme from the MemeCabin app! \n"
								+ imagePath);

			}
		}
	};

	public static InputStream getBitmapFromUrl(String url) {

		print.message("GIF IMAGE URL", url + "");
		InputStream instream = null;

		HttpGet httpRequest = null;
		httpRequest = new HttpGet(url);
		final HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpRequest);
		} catch (final ClientProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		if (response != null) {
			final HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = null;
			try {
				bufHttpEntity = new BufferedHttpEntity(entity);
			} catch (final IOException e) {
				e.printStackTrace();
			}

			try {
				instream = bufHttpEntity.getContent();
			} catch (final IOException e) {
				e.printStackTrace();
			}

		}
		return instream;
	}

	protected void updatLikeMeme(final String url) {

		if (!NetInfo.isOnline(context)) {
			AlertMessage.showMessage(context, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(context, true);
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

				if (busyNow != null) {
					busyNow.dismis();
				}

				JSONObject json = null;

				try {
					json = new JSONObject(new String(response));

					Log.e("new String(response)", ">>>>" + new String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("1");
							motiLike_im.setImageResource(R.drawable.motilike);

							AllSpicyInfo.getAllSpicyInfo()
									.elementAt(AppConstant.position)
									.setIsLike("1");
							AllSpicyInfo.getAllSpicyInfo()
									.elementAt(AppConstant.position)
									.setLikeCount(like_count);
							motiliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// motiliketotal_tv.setText("Total Likes ("+
							// query.getLikeCount() + ")");
							// toastMessage("Like successed");
						}

					} else {

						// toastMessage("Like not sccessed");
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

	protected void disLikeMeme(final String url) {

		if (!NetInfo.isOnline(context)) {
			AlertMessage.showMessage(context, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(context, true);
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

					Log.e("new String(response)", ">>>>" + new String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("0");
							motiLike_im.setImageResource(R.drawable.beforelike);
							AllSpicyInfo.getAllSpicyInfo()
									.elementAt(AppConstant.position)
									.setIsLike("0");
							AllSpicyInfo.getAllSpicyInfo()
									.elementAt(AppConstant.position)
									.setLikeCount(like_count);

							motiliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// motiliketotal_tv.setText("Total Likes ("+
							// query.getLikeCount() + ")");
							// toastMessage("Dislike successed");
						}

					} else {

						// toastMessage("Dislike not sccessed");
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		/* Jin Remove Mopub ADS
		if (mInterstitial != null) {
			mInterstitial.destroy();
		}
		if (moPubView != null) {
			moPubView.destroy();
		}
		*/
		super.onDestroy();
	}

	/* Jin Remove Mopub ADS
	private void initAd() {

		mInterstitial = new MoPubInterstitial(this,
				AppConstant.mopubAdsUnitIdFullScreen);
		mInterstitial.setInterstitialAdListener(new InterstitialAdListener() {

			@Override
			public void onInterstitialShown(MoPubInterstitial arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onInterstitialLoaded(MoPubInterstitial interstitial) {
				// TODO Auto-generated method stub

				if (interstitial.isReady()) {
					mInterstitial.show();

					Log.e("Ads", "loaded");
				}

				Log.e("Ads", "onInterstitialLoaded");

			}

			@Override
			public void onInterstitialFailed(MoPubInterstitial arg0,
					MoPubErrorCode arg1) {
				// TODO Auto-generated method stub
				Log.e("Ads", "onInterstitialFailed");

			}

			@Override
			public void onInterstitialDismissed(MoPubInterstitial arg0) {
				// TODO Auto-generated method stub
				Log.e("Ads", "onInterstitialDismissed");

			}

			@Override
			public void onInterstitialClicked(MoPubInterstitial arg0) {
				// TODO Auto-generated method stub
				Log.e("Ads", "onInterstitialClicked");

			}
		});
		mInterstitial.load();

		// mInterstitialAd = new InterstitialAd(con);
		// mInterstitialAd.setAdUnitId(getString(R.string.admob_fullscreen_ad_id));
		// final AdRequest adRequest = new AdRequest.Builder().build();
		// mInterstitialAd.loadAd(adRequest);
		// adHandler = new Handler();
		// adLoad.run();
	}

	// Runnable adLoad = new Runnable() {
	//
	// @Override
	// public void run() {
	// if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
	// mInterstitialAd.show();
	// adHandler.removeCallbacks(adLoad);
	// } else {
	// adHandler.postDelayed(adLoad, 5000);
	// }
	// System.out.println("Inside handler");
	// }
	// };

	private void addShow() {

		moPubView = (MoPubView) findViewById(R.id.adview);
		moPubView.setAdUnitId(AppConstant.mopubAdsUnitId);
		moPubView.loadAd();
		moPubView.setBannerAdListener(new BannerAdListener() {

			@Override
			public void onBannerLoaded(MoPubView arg0) {
				// TODO Auto-generated method stub

				moPubView.setVisibility(View.VISIBLE);

			}

			@Override
			public void onBannerFailed(MoPubView arg0, MoPubErrorCode arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBannerExpanded(MoPubView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBannerCollapsed(MoPubView arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBannerClicked(MoPubView arg0) {
				// TODO Auto-generated method stub

			}
		});

		// final AdView mAdView = (AdView) findViewById(R.id.adView);
		// final AdRequest adRequest = new AdRequest.Builder().build();
		// mAdView.setAdListener(new AdListener() {
		//
		// @Override
		// public void onAdLoaded() {
		// // TODO Auto-generated method stub
		// super.onAdLoaded();
		// mAdView.setVisibility(View.VISIBLE);
		//
		// }
		// });
		//
		// mAdView.loadAd(adRequest);

	}
	*/

	/*
	 * public void removeImageAfter1seconds()
	 * 
	 * {
	 * 
	 * handler.postDelayed(new Runnable() {
	 * 
	 * @Override public void run() {
	 * 
	 * bookmrkshowll.setVisibility(View.GONE);
	 * 
	 * bookmrkshowll.startAnimation(animationFadeOut);
	 * 
	 * } }, 30000); }
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*
		 * if (!SharedPreferencesHelper.isAddDisable(con)) {
		 * 
		 * addShow(); initAd(); }
		 */
	}

}
