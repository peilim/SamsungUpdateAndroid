package com.memecabin.pro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.FavoriteManager;
import com.aacom.memecabin.utils.MMSSender;
import com.aacom.memecabin.utils.MakeBitmapAndSave;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.storage.PersistentUser;
import com.facebook.Request;
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
import com.memecabin.holder.AllRacy;
import com.memecabin.holder.AllReload;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.RacyParser;
import com.memecabin.parser.ReloadlParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import com.memecabin.pro.R;

public class RacySwipeActivity extends FragmentActivity implements
		OnClickListener {
	private Context con;
	private LinearLayout racySwipeBack_ll, racyHere_ll, racyFirst_ll,
			racyfavri_ll;
	private ImageView racySwipeHome_im, racySwipeRel_im, racySwipeShare_im,
			racySwipeFav_im, racyLike_im;
	private RelativeLayout racyAfterright_ll, racyAfterLeft_rl,
			racySharememe_rl;
	private LinearLayout racyfirstVp_ll, racymessageNotShow_ll,
			racyumCancle_ll;
	private TextView racyliketotal_tv, addracyfavoritesTv;
	private ViewPager racyimageView1;
	public int currentIndex = 0;
	public boolean iniLoad = true;
	private ImageView racysaveimage_im, racysharefb_im, racysharetwitter_im,
			racysendemail_im, racysendMMs_im, racyReportImage;

	DatabaseHandler db;
	ImageInfo query;
	String like_count = "";
	Vector<ImageInfo> imageInfos;
	private MyPagerAdapter myPagerAdapter;
	Handler handler = new Handler();
	Animation animationFadeIn;
	Animation animationFadeOut;

	FavoriteManager favoriteManger;

	private Target loadTarget;

	RelativeLayout rlDecide;
	TextView txtLogin;
	TextView txtNoThanks;
	int counter = 0;

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	// private static final String FACEBOOK_APPID = "558359717642213";
	private static final String FACEBOOK_APPID = "841310412569994";// from DAN
	private static final String[] FACEBOOK_PERMISSION = { "publish_stream" };
	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";
	String path = "";
	static File dir = null;
	private File file;

	EditText commentstextforshare;
	RelativeLayout sharecommntsviewracy;
	TextView buttontexshare;
	LinearLayout sharebtfbortwitter;

	LinearLayout swipeHeadracy_ll;

	Handler adHandler;
	private InterstitialAd mInterstitialAd;

	private boolean hasMoreItem = true;
	private int currentPage = 1;

	// ImageView transparent;
	private boolean wasDragging = false;

	/* Jin Remove Mopub ADS
	private MoPubInterstitial mInterstitial;
	private MoPubView moPubView;
	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.racyswipe);
		con = this;
		activity = this;
		db = new DatabaseHandler(con);
		favoriteManger = new FavoriteManager(con);
		animationFadeIn = AnimationUtils.loadAnimation(con, R.anim.fadein);
		animationFadeOut = AnimationUtils.loadAnimation(con, R.anim.fadeout);

		fb_session = Session.openActiveSessionFromCache(con);

		/*
		 * control pagination
		 */
		final int totalCount = AllRacy.getAllRacy().size();
		final float cnt = (float) totalCount
				/ AppConstant.PAGINATION_ITEM_COUNT;
		currentPage = (int) Math.ceil(cnt);

		if (currentPage == 0) {
			currentPage = 1;
		}
		if (totalCount >= AppConstant.totalRacyMeme) {
			hasMoreItem = false;
		}

		initUI();

		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
			initAd();
			*/
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		AppConstant.readFlagRacy = true;
	}

	private void initUI() {
		racyReportImage = (ImageView) findViewById(R.id.racyReportImage);
		racySwipeBack_ll = (LinearLayout) findViewById(R.id.racySwipeBack_ll);

		racyHere_ll = (LinearLayout) findViewById(R.id.racyHere_ll);
		racyFirst_ll = (LinearLayout) findViewById(R.id.racyFirst_ll);
		racyfavri_ll = (LinearLayout) findViewById(R.id.racyfavri_ll);

		racyfirstVp_ll = (LinearLayout) findViewById(R.id.racyfirstVp_ll);
		racymessageNotShow_ll = (LinearLayout) findViewById(R.id.racymessageNotShow_ll);
		racyumCancle_ll = (LinearLayout) findViewById(R.id.racyumCancle_ll);
		swipeHeadracy_ll = (LinearLayout) findViewById(R.id.swipeHeadracy_ll);

		racyAfterright_ll = (RelativeLayout) findViewById(R.id.racyAfterright_ll);
		racyAfterLeft_rl = (RelativeLayout) findViewById(R.id.racyAfterLeft_rl);
		racySharememe_rl = (RelativeLayout) findViewById(R.id.racySharememe_rl);

		racySwipeHome_im = (ImageView) findViewById(R.id.racySwipeHome_im);
		racySwipeShare_im = (ImageView) findViewById(R.id.racySwipeShare_im);
		racySwipeRel_im = (ImageView) findViewById(R.id.racySwipeRel_im);
		racySwipeFav_im = (ImageView) findViewById(R.id.racySwipeFav_im);
		racyLike_im = (ImageView) findViewById(R.id.racyLike_im);
		racysaveimage_im = (ImageView) findViewById(R.id.racysaveimage_im);
		racysharefb_im = (ImageView) findViewById(R.id.racysharefb_im);
		racysharetwitter_im = (ImageView) findViewById(R.id.racysharetwitter_im);
		racysendemail_im = (ImageView) findViewById(R.id.racysendemail_im);
		racysendMMs_im = (ImageView) findViewById(R.id.racysendMMs_im);

		racyimageView1 = (ViewPager) findViewById(R.id.racyimageView1);

		racyliketotal_tv = (TextView) findViewById(R.id.racyliketotal_tv);
		addracyfavoritesTv = (TextView) findViewById(R.id.addracyfavoritesTv);

		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);

		sharecommntsviewracy = (RelativeLayout) findViewById(R.id.sharecommntsviewracy);
		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);
		buttontexshare = (TextView) findViewById(R.id.buttontexshare);

		racySwipeBack_ll.setOnClickListener(this);
		racySwipeHome_im.setOnClickListener(this);
		racySwipeShare_im.setOnClickListener(this);
		racySwipeRel_im.setOnClickListener(this);
		racySwipeFav_im.setOnClickListener(this);
		racyLike_im.setOnClickListener(this);
		racysaveimage_im.setOnClickListener(this);
		racysharefb_im.setOnClickListener(this);
		racysharetwitter_im.setOnClickListener(this);
		racysendemail_im.setOnClickListener(this);
		racysendMMs_im.setOnClickListener(this);
		racySharememe_rl.setOnClickListener(this);

		racyHere_ll.setOnClickListener(this);
		racyFirst_ll.setOnClickListener(this);
		racyfavri_ll.setOnClickListener(this);

		racyfirstVp_ll.setOnClickListener(this);
		racymessageNotShow_ll.setOnClickListener(this);
		racyumCancle_ll.setOnClickListener(this);
		racyAfterright_ll.setOnClickListener(this);
		racyAfterLeft_rl.setOnClickListener(this);

		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);
		sharebtfbortwitter.setOnClickListener(this);
		racyReportImage.setOnClickListener(this);

		// viewPagerAdapterRacy = new
		// MyFragmentPagerAdapter(getSupportFragmentManager());
		// racyimageView1.setAdapter(viewPagerAdapterRacy);

		imageInfos = AllRacy.getAllRacy();
		myPagerAdapter = new MyPagerAdapter(con, imageInfos, swipeHeadracy_ll);
		racyimageView1.setAdapter(myPagerAdapter);
		racyimageView1.setCurrentItem(SharedPreferencesHelper
				.getRacyViewpagerPosition(con));

		if (!(AllRacy.getAllRacy().size() > AppConstant.position)) {
			return;
		}

		query = AllRacy.getAllRacy().elementAt(AppConstant.position);
		AppConstant.imageid = query.getImageID();

		// racyliketotal_tv.setText(getResources().getString(R.string.totallikes)+"("
		// + query.getLikeCount() + ")");
		// racyliketotal_tv.setText("Total Likes (" + query.getLikeCount() +
		// ")");
		racyliketotal_tv.setText(getResources().getString(R.string.totallikes)
				+ "(" + query.getLikeCount() + ")");

		if (query != null) {

			db.addRead(query);

		}

		// if (db.isLikeImage(query.getImageID(), query.getImageCategory()))
		if (query.getIsLike().equalsIgnoreCase("1")) {

			racyLike_im.setImageResource(R.drawable.afterlikeracy);
		} else {
			racyLike_im.setImageResource(R.drawable.beforelike);
		}

		if (query.getFavorite().equalsIgnoreCase("1")) {
			racySwipeFav_im.setImageResource(R.drawable.afterfaveracy);
		} else {
			racySwipeFav_im.setImageResource(R.drawable.racystarbottomfav);
		}

		racyimageView1.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {

				if (racyimageView1.getCurrentItem() == AppConstant.totalRacyMeme - 1) {

					if (state == ViewPager.SCROLL_STATE_DRAGGING) {
						Log.e("View pager on scrolled state change ",
								"Scrolling dragging");

						wasDragging = true;
					}

					if (state == ViewPager.SCROLL_STATE_SETTLING) {
						Log.e("View pager on scrolled state change ",
								"Scrolling settling");
						wasDragging = false;

					}

					if (state == ViewPager.SCROLL_STATE_IDLE) {
						Log.e("View pager on scrolled state change ",
								"Scrolling idle");

						if (wasDragging) {
							racyAfterright_ll.setVisibility(View.VISIBLE);
						}

					}

				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				/* By Jin
				Log.e("position", "" + position);
				Log.e("positionOffset", "" + positionOffset);
				Log.e("positionOffsetPixels", "" + positionOffsetPixels);

				if (positionOffset > 0) {
					racySwipeFav_im
							.setImageResource(R.drawable.racystarbottomfav2);
				} else if (positionOffset == 0.0f) {

					if (query.getFavorite().equalsIgnoreCase("1"))

					{
						racySwipeFav_im
								.setImageResource(R.drawable.afterfaveracy);

					} else {
						racySwipeFav_im
								.setImageResource(R.drawable.racystarbottomfav);

					}
				}
				*/
			}

			@Override
			public void onPageSelected(int arg0) {

				// pos++;
				// counter++;
				// Log.d(tag, "OnPageSelected Pos: " + pos);

				// isCallHappened = false;
				swipeHeadracy_ll.setVisibility(View.VISIBLE);
				final int index = racyimageView1.getCurrentItem();
				currentIndex = index;
				AppConstant.position = index;

				SharedPreferencesHelper.setRacyViewpagerPosition(con, index);
				query = AllRacy.getAllRacy().elementAt(index);
				AppConstant.image = query.getImageUrl();
				AppConstant.totalLike = query.getLikeCount();
				AppConstant.imageid = query.getImageID();
				racyliketotal_tv.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");

				if (query != null) {

					db.addRead(query);
					AllRacy.getAllRacy().elementAt(index).setIsRead("1");

				}

				if (query.getIsLike().equalsIgnoreCase("1")) {

					racyLike_im.setImageResource(R.drawable.afterlikeracy);
				} else {
					racyLike_im.setImageResource(R.drawable.beforelike);
				}

				if (query.getFavorite().equalsIgnoreCase("1")) {
					racySwipeFav_im.setImageResource(R.drawable.afterfaveracy);
				} else {
					racySwipeFav_im
							.setImageResource(R.drawable.racystarbottomfav);
				}

				final int vectorSize = AllRacy.getAllRacy().size();
				if (currentIndex + 1 == vectorSize) {

					if (hasMoreItem) {
						getMemeListFromServer();
					}

				}

				if (!SharedPreferencesHelper.isAddDisable(con)) {

					counter++;
					if (counter == 20) {

						/* Jin Remove Mopub ADS
						initAd();
						*/

						counter = 0;
					}
				}

				loadMemeDetail(query.getImageID());

			}

		});

	}

	BusyDialog busyDialog = null;

	// Get new meme list from server
	private void getMemeListFromServer() {
		if (hasMoreItem) {
			busyDialog = new BusyDialog(con, false);
			busyDialog.show();
			currentPage++;
			final String url = AllURL.getRecyMemeByPage(
					PersistentUser.getUserID(con), currentPage,
					AppConstant.PAGINATION_ITEM_COUNT);
			final AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] response) {

					try {
						RacyParser.parseNext(new String(response));
					} catch (final JSONException e) {
						e.printStackTrace();
					} catch (final IOException e) {
						e.printStackTrace();
					}
					final int newItemCount = AllRacy.getAllRacy().size();
					if (newItemCount >= AppConstant.totalRacyMeme) {
						hasMoreItem = false;
					}

					synchronized (RacySwipeActivity.class) {
						myPagerAdapter.notifyDataSetChanged();
						racyimageView1.invalidate();
					}

					if (busyDialog != null) {
						busyDialog.dismis();
					}
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					if (busyDialog != null) {
						busyDialog.dismis();
					}
				}
			});
		} else {

		}
	}

	private void loadMemeDetail(String memeId) {
		// Check net
		if (!NetInfo.isOnline(con)) {
			// AlertMessage.showMessage(con, con.getString(R.string.Status),
			// con.getString(R.string.checkInternet));
			return;
		}
		// Get url
		final String url = AllURL.getMemeDetailUrl(memeId);
		// Call async task

		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// TODO Auto-generated method stub
				try {
					final JSONObject json = new JSONObject(new String(response));
					final String status = json.optString("status");
					final String msg = json.optString("message");
					if (status.equals("1")) {
						final JSONArray tempArr = json.getJSONArray("result");
						if (tempArr.length() > 0) {
							final JSONObject memeDetailJson = tempArr
									.getJSONObject(0);
							query.setLikeCount(memeDetailJson
									.optString("allLike"));
							racyliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");

						}
					} else {
						System.out.println("Meme detial load failed. " + msg);
					}
				} catch (final Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});

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

	public boolean makeMeRequest(final Bitmap b, final String msg,
			final String caption, final Context con1) {

		fb_session = Session.openActiveSessionFromCache(con);
		fb_session
				.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						activity, AppConstant.PERMISSIONS));
		final Request uploadRequest = Request.newUploadPhotoRequest(fb_session,
				AppConstant.fbImage, new Request.Callback() {
					@Override
					public void onCompleted(Response response) {

						facebookFlag = true;

						Log.e("Facebook respors ", ">>" + response.toString());
						AppConstant.fbImage = null;

						Toast.makeText(con1, "Photo uploaded successfully",
								Toast.LENGTH_LONG).show();

					}
				});
		final Bundle param = uploadRequest.getParameters();
		param.putString("message", msg);

		uploadRequest.setParameters(param);
		uploadRequest.executeAsync();

		return facebookFlag;
	}

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

	public void removeImageAfter1seconds()

	{

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				addracyfavoritesTv.setVisibility(View.GONE);

				addracyfavoritesTv.startAnimation(animationFadeOut);

			}
		}, 1000);
	}

	public void exitCurrentActivity() {

		RacySwipeActivity.this.finish();

	}

	public void toastMessage(String mes) {

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.racySwipeBack_ll) {
			if (AppConstant.racyshareview) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_all_racy_activity);
				AppConstant.racyshareview = false;
				exitCurrentActivity();

			} else {

				if (AppConstant.readracyshare) {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_close_share_option);
					racySharememe_rl.setVisibility(View.GONE);
					AppConstant.readracyshare = false;
				} else {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_go_back_to_all_racy_activity);
					AppConstant.readFlagRacy = true;
					exitCurrentActivity();
				}
			}

		} else if (v.getId() == R.id.racySwipeHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			exitCurrentActivity();

			if (RacyAllUnreadActivity.getInstance() != null) {
				RacyAllUnreadActivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.racySwipeRel_im) {
			// Do Refresh

			if (SharedPreferencesHelper.getRacySwipeDialog(con)) {

				racyimageView1.setCurrentItem(0);
				// racyAfterLeft_rl.setVisibility(View.GONE);
			} else {
				racyAfterLeft_rl.setVisibility(View.VISIBLE);
			}

			// racyimageView1.setCurrentItem(0);

			// final String reUrl = AllURL.getReloadinfo(AppConstant.imageid);
			// reload(reUrl);

		} else if (v.getId() == R.id.racySwipeShare_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			racySharememe_rl.setVisibility(View.VISIBLE);
			AppConstant.readracyshare = true;

			// final Intent ii = new Intent(con, MemeShareActivity.class);
			// startActivity(ii);

		} else if (v.getId() == R.id.racySwipeFav_im) {
			if (SharedPreferencesHelper.getUsingskip(con)) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);
				// decide();
				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_make_favorite);
				if (query != null) {
					// if
					// (!db.isFavorite(query.getImageID(),query.getImageCategory()))
					if (!query.getFavorite().equalsIgnoreCase("1")) {
						query.setFavorite("1");
						db.addFavoritesImg(query);
						favoriteManger.addToFavoriteOnline(query);

						racySwipeFav_im
								.setImageResource(R.drawable.afterfaveracy);

						addracyfavoritesTv.setText(""
								+ getResources().getString(
										R.string.addfavorites));
						addracyfavoritesTv.startAnimation(animationFadeIn);
						addracyfavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

						// toastMessage("Added to Favorites");

					} else {

						query.setFavorite("0");
						db.removeFavoritesImg(query);
						favoriteManger.removeFromFavoriteOnline(query);

						racySwipeFav_im
								.setImageResource(R.drawable.racystarbottomfav);

						addracyfavoritesTv.setText(""
								+ getResources().getString(
										R.string.removefavorites));
						addracyfavoritesTv.startAnimation(animationFadeIn);
						addracyfavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

						// toastMessage("Removed from Favorites");

					}
				}
			}

		} else if (v.getId() == R.id.racyLike_im) {
			if (SharedPreferencesHelper.getUsingskip(con)) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);
				// toastMessage("You are not authority like or dislike this memes");
				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_like_or_unlike_meme);
				// if (!db.isLikeImage(AppConstant.imageid, "3"))
				if (!query.getIsLike().equalsIgnoreCase("1"))

				{

					final String urllike = AllURL.getLikeUpdate(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));

					updatLikeMeme(urllike);
				} else {

					final String urlunlike = AllURL.getDisLike(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));
					disLikeMeme(urlunlike);
				}
			}

		} else if (v.getId() == R.id.racyHere_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keep_me_here);
			// racyimageView1.setCurrentItem(racyimageView1.getCurrentItem() -
			// 1);
			racyAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.racyFirst_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			// if (!SharedPreferencesHelper.getRacySwipeDialog(con)) {
			// racyAfterright_ll.setVisibility(View.GONE);
			// racyAfterLeft_rl.setVisibility(View.VISIBLE);
			// } else {
			racyAfterright_ll.setVisibility(View.GONE);
			racyimageView1.setCurrentItem(0);
			//
			// }

		} else if (v.getId() == R.id.racyfavri_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_favorite_meme);
			final Intent ii = new Intent(con, FavoritesActivity.class);
			exitCurrentActivity();
			if (RacyAllUnreadActivity.getInstance() != null) {
				RacyAllUnreadActivity.getInstance().finish();
			}
			startActivity(ii);
		} else if (v.getId() == R.id.racyfirstVp_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			racyAfterLeft_rl.setVisibility(View.GONE);
			racyimageView1.setCurrentItem(0);
		} else if (v.getId() == R.id.racymessageNotShow_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first_dont_show_again);
			SharedPreferencesHelper.setRacySwipeDialog(con, true);
			racyAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.racyumCancle_ll) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_cancel_all);
			racyAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.racysaveimage_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_create_bitmap_to_share);
			// Image save own devic

			// final String imageurl = "" + AppConstant.imageBaseUrl
			// + AppConstant.image;
			//
			// Picasso.with(this).load(imageurl).into(target);

			getbitmapFromServer();

		} else if (v.getId() == R.id.racysharefb_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_facebook);
			// image share fb

			SharedPreferencesHelper.setFbmeme(con, true);

			racysaveimage_im.setClickable(false);
			racysharefb_im.setClickable(false);
			racysharetwitter_im.setClickable(false);
			racysendemail_im.setClickable(false);
			racysendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			// Remove: Sharing this from the MemeCabin app! Get it on Android
			// and iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");
			sharecommntsviewracy.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			/*
			 * if (facebookConnector.getFacebook().isSessionValid()) {
			 * 
			 * getbitmapFromServerForShare("facebook");
			 * 
			 * // loadData(imageurl, "facebook"); } else { final
			 * SessionEvents.AuthListener listener = new
			 * SessionEvents.AuthListener() {
			 * 
			 * @Override public void onAuthSucceed() {
			 * 
			 * getbitmapFromServerForShare("facebook");
			 * 
			 * // loadData(imageurl, "facebook"); }
			 * 
			 * @Override public void onAuthFail(String error) {
			 * 
			 * } }; SessionEvents.addAuthListener(listener);
			 * facebookConnector.login(); }
			 */

		} else if (v.getId() == R.id.racysharetwitter_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_twitter);
			// Image share twitter

			SharedPreferencesHelper.setTwittermeme(con, true);

			racysaveimage_im.setClickable(false);
			racysharefb_im.setClickable(false);
			racysharetwitter_im.setClickable(false);
			racysendemail_im.setClickable(false);
			racysendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
			sharecommntsviewracy.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.racysendemail_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_email);
			// Image send mail
			getbitmapFromServerForShare("email");

		} else if (v.getId() == R.id.racysendMMs_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_mms);
			// Image send MMS/SMS
			getbitmapFromServerForShare("mms");

		} else if (v.getId() == R.id.racySharememe_rl) {

			if (sharecommntsviewracy.getVisibility() == View.VISIBLE) {

				SharedPreferencesHelper.setFbmeme(con, false);
				SharedPreferencesHelper.setTwittermeme(con, false);

				racysaveimage_im.setClickable(true);
				racysharefb_im.setClickable(true);
				racysharetwitter_im.setClickable(true);
				racysendemail_im.setClickable(true);
				racysendMMs_im.setClickable(true);

				closeKeyBoard(v);
				sharecommntsviewracy.setVisibility(View.GONE);

			} else if (racySharememe_rl.getVisibility() == View.VISIBLE
					&& sharecommntsviewracy.getVisibility() == View.GONE) {

				AppConstant.racyshareview = true;
				racySharememe_rl.setVisibility(View.GONE);
			}

			// AppConstant.racyshareview = true;
			//
			// racySharememe_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.racyAfterright_ll) {
			// racyimageView1.setCurrentItem(racyimageView1.getCurrentItem() -
			// 1);
			racyAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.racyAfterLeft_rl) {

			racyAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.rlDecide) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtLogin) {
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_to_login);
			AppConstant.fromscreen = "racyswipe";
			AppConstant.position = currentIndex;

			final Intent loginIntent = new Intent(con, LogActivity.class);
			startActivity(loginIntent);

			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtNoThanks) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.sharebtfbortwitter) {

			closeKeyBoard(v);

			if (SharedPreferencesHelper.getFbmeme(con)) {

				racysaveimage_im.setClickable(true);
				racysharefb_im.setClickable(true);
				racysharetwitter_im.setClickable(true);
				racysendemail_im.setClickable(true);
				racysendMMs_im.setClickable(true);

				SharedPreferencesHelper.setFbmeme(con, false);

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

				// Log.e("facbook session", ":"
				// + facebookConnector.getFacebook().isSessionValid());
				//
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
				//
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
			} else if (SharedPreferencesHelper.getTwittermeme(con)) {

				racysaveimage_im.setClickable(true);
				racysharefb_im.setClickable(true);
				racysharetwitter_im.setClickable(true);
				racysendemail_im.setClickable(true);
				racysendMMs_im.setClickable(true);

				SharedPreferencesHelper.setTwittermeme(con, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.racyReportImage) {
			final Intent i = new Intent(con, ReportActivity.class);
			startActivity(i);
		}

	}

	protected void updatLikeMeme(final String url) {

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
					like_count = json.getString("totalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("1");
							racyLike_im
									.setImageResource(R.drawable.afterlikeracy);

							db.addLike(query);
							racyliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// racyliketotal_tv.setText("Total Likes ("+
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
					like_count = json.getString("totalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("0");
							racyLike_im.setImageResource(R.drawable.beforelike);
							db.removeLike(query);
							racyliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// racyliketotal_tv.setText("Total Likes ("+
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

	protected void reload(final String url) {

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

				try {
					if (ReloadlParser.connect(con, new String(response))) {

						Log.e("Reload:", "" + new String(response));

						query = AllReload.getAllReload().elementAt(0);

						AppConstant.totalLike = query.getLikeCount();

						if (AppConstant.status.equalsIgnoreCase("1")) {

							racyliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// racyliketotal_tv.setText("Total Likes ("+
							// AppConstant.totalLike + ")");

						} else {

						}

					}

				} catch (final JSONException e) {
					e.printStackTrace();
				} catch (final IOException e) {
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

	// private final Target target = new Target() {
	// @Override
	// public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from)
	// {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	//
	// final long timestr = System.currentTimeMillis();
	//
	// final File file = new File(Environment
	// .getExternalStorageDirectory().getPath()
	//
	// + "/MEMECABIN" + "/" + timestr + "meme.jpg");
	// try {
	// file.createNewFile();
	// final FileOutputStream ostream = new FileOutputStream(
	// file);
	// bitmap.compress(CompressFormat.JPEG, 75, ostream);
	// ostream.close();
	// } catch (final Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// }).start();
	// toastMessage("Image saved successed");
	// racySharememe_rl.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void onBitmapFailed(Drawable errorDrawable) {
	// }
	//
	// @Override
	// public void onPrepareLoad(Drawable placeHolderDrawable) {
	// if (placeHolderDrawable != null) {
	// }
	// }
	// };
	private void getbitmapFromServerForShare(final String shareType) {

		final BusyDialog busyDialog = new BusyDialog(con, true);
		busyDialog.show();

		loadTarget = new Target() {

			@Override
			public void onPrepareLoad(Drawable drawable) {

			}

			@Override
			public void onBitmapLoaded(final Bitmap bmp, LoadedFrom from) {

				if (shareType.equalsIgnoreCase("facebook")) {

					final Thread t = new Thread() {
						@Override
						public void run() {

							try {
								// facebookConnector.getMe();

								final Bitmap myImage = MakeBitmapAndSave
										.createMyBitmap(con, bmp);

								AppConstant.fbImage = myImage;

								final String value = commentstextforshare
										.getText().toString();

								runOnUiThread(new Runnable() {
									@Override
									public void run() {

										if (busyDialog != null) {
											busyDialog.dismis();
										}

										if (makeMeRequest(myImage, value, "",
												con)) {
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											// toastMessage("Image posted successfully!");
											commentstextforshare.setText("");
											sharecommntsviewracy
													.setVisibility(View.GONE);
										} else {
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											// toastMessage("Facebook post failed!");
											commentstextforshare.setText("");
											sharecommntsviewracy
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

					if (busyDialog != null) {
						busyDialog.dismis();
					}

					final Bitmap myImage = MakeBitmapAndSave.createMyBitmap(
							con, bmp);

					final String imagePath = MakeBitmapAndSave
							.saveBitmapPathToShare(myImage, "");

					final Intent exportMessageIntent = new Intent(
							android.content.Intent.ACTION_SEND_MULTIPLE);
					exportMessageIntent.setType("text/plain");
					exportMessageIntent.setType("application/octet-stream");
					final ArrayList<Uri> uris = new ArrayList<Uri>();

					final String[] filePaths = new String[] { imagePath };
					for (final String file : filePaths) {
						final File fileIn = new File(file);
						final Uri u = Uri.fromFile(fileIn);
						uris.add(u);
					}

					exportMessageIntent.putParcelableArrayListExtra(
							Intent.EXTRA_STREAM, uris);

					exportMessageIntent
							.putExtra(Intent.ACTION_DEFAULT, "test/");

					exportMessageIntent.putExtra(Intent.EXTRA_SUBJECT,
							"An amazing meme from the MemeCabin app!");

					exportMessageIntent
							.putExtra(
									Intent.EXTRA_TEXT,
									"I sent you this from the MemeCabin app! Download it today for Apple and Android phones and tablets at www.memecabin.com!");

					startActivity(exportMessageIntent);
				} else if (shareType.equalsIgnoreCase("twitter")) {

					final Bitmap myImage = MakeBitmapAndSave.createMyBitmap(
							con, bmp);

					final String imagePath = MakeBitmapAndSave
							.saveBitmapPathToShare(myImage, "");
					final String value = commentstextforshare.getText()
							.toString();

					AppConstant.twitterImage = imagePath;

					if (LoginActivity.isActive(con)) {

						SharedPreferencesHelper.setTwitterFlag(con, false);

						try {

							HelperMethods.postToTwitterWithImage(con,
									(Activity) con, AppConstant.twitterImage,
									value, new TwitterCallback() {

										@Override
										public void onFinsihed(Boolean response) {
											if (busyDialog != null) {
												busyDialog.dismis();
											}

											Log.d(TAG,
													"----------------response----------------"
															+ response);
											Toast.makeText(
													con,
													getString(R.string.image_posted_on_twitter),
													Toast.LENGTH_SHORT).show();
											commentstextforshare.setText("");
											sharecommntsviewracy
													.setVisibility(View.GONE);

										}
									});

						} catch (final Exception ex) {
							if (busyDialog != null) {
								busyDialog.dismis();
							}
						}
					} else {

						if (busyDialog != null) {
							busyDialog.dismis();
						}

						SharedPreferencesHelper.setTwitterFlag(con, true);

						startActivity(new Intent(con, LoginActivity.class));
					}

				} else if (shareType.equalsIgnoreCase("mms")) {

					if (busyDialog != null) {
						busyDialog.dismis();
					}

					final Bitmap myImage = MakeBitmapAndSave.createMyBitmap(
							con, bmp);

					final String imagePath = MakeBitmapAndSave
							.saveBitmapPathToShare(myImage, "mms");

					Log.e("imagePath", ">>" + imagePath);

					MMSSender
							.mmsImageToAll(
									con,
									"Just wanted to share this meme from the MemeCabin app!",
									new File(imagePath));

					// Intent sendIntent = new Intent(Intent.ACTION_SEND);
					// sendIntent.setClassName("com.android.mms",
					// "com.android.mms.ui.ComposeMessageActivity");
					// sendIntent.putExtra("sms_body", "");
					// sendIntent.putExtra(Intent.EXTRA_STREAM,
					// Uri.parse("file://"+imagePath));
					// sendIntent.setType("image/png");
					// startActivity(sendIntent);

				}
				// createBitmapShare(bmp);

				// bmp.recycle();
			}

			@Override
			public void onBitmapFailed(Drawable drawable) {

				if (busyDialog != null) {
					busyDialog.dismis();
				}

				// toastMessage("Image saved failed,try again!");

			}
		};

		Picasso.with(con).load(AppConstant.imageBaseUrl + query.getImageUrl())
				.noFade().skipMemoryCache().into(loadTarget);

	}

	private void getbitmapFromServer() {

		final BusyDialog busyDialog = new BusyDialog(con, true);
		busyDialog.show();

		loadTarget = new Target() {

			@Override
			public void onPrepareLoad(Drawable drawable) {

			}

			@Override
			public void onBitmapLoaded(Bitmap bmp, LoadedFrom from) {

				if (busyDialog != null) {
					busyDialog.dismis();
				}

				final Bitmap myImage = MakeBitmapAndSave.createMyBitmap(con,
						bmp);

				MakeBitmapAndSave.saveBitmapToDevice(con, myImage);

				// bmp.recycle();
			}

			@Override
			public void onBitmapFailed(Drawable drawable) {

				if (busyDialog != null) {
					busyDialog.dismis();
				}

				toastMessage("Image saved failed,try again!");

			}
		};

		Picasso.with(con).load(AppConstant.imageBaseUrl + query.getImageUrl())
				.noFade().skipMemoryCache().into(loadTarget);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (SharedPreferencesHelper.getTwitterFlag(con)) {

			final BusyDialog busyDialog1 = new BusyDialog(con, true);
			busyDialog1.show();
			final String valuse = commentstextforshare.getText().toString();
			HelperMethods.postToTwitterWithImage(con, (Activity) con,
					AppConstant.twitterImage, valuse, new TwitterCallback() {

						@Override
						public void onFinsihed(Boolean response) {
							if (busyDialog1 != null) {
								busyDialog1.dismis();
							}

							SharedPreferencesHelper.setTwitterFlag(con, false);
							Log.d(TAG,
									"----------------response----------------"
											+ response);
							Toast.makeText(
									con,
									getString(R.string.image_posted_on_twitter),
									Toast.LENGTH_SHORT).show();
							commentstextforshare.setText("");
							sharecommntsviewracy.setVisibility(View.GONE);
						}
					});
		}

		/*
		 * if (!SharedPreferencesHelper.isAddDisable(con)) {
		 * 
		 * addShow(); initAd(); }
		 */

	}

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
					// AppConstant.fromswipescreen= true;
					AppConstant.fromscreen = "racyswipe";
					AppConstant.position = currentIndex;
					final Intent ii = new Intent(con, LogActivity.class);
					startActivity(ii);
					// RacySwipeActivity.this.finish();
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

}
