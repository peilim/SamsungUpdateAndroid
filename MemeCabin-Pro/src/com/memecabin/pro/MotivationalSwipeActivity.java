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
import com.memecabin.holder.AllMotivational;
import com.memecabin.holder.AllReload;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.MotivationalParser;
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

public class MotivationalSwipeActivity extends FragmentActivity implements
		OnClickListener {
	private Context con;
	private LinearLayout motiSwipeBack_ll, motiHere_ll, motiFirst_ll,
			motifavri_ll;
	private ImageView motiSwipeHome_im, motiSwipeRel_im, motiSwipeShare_im,
			motiSwipeFav_im, motiLike_im;
	private RelativeLayout motiAfterright_ll, motiAfterLeft_rl,
			motiShareImage_rl;
	private LinearLayout motifirstVp_ll, motimessageNotShow_ll,
			motiumCancle_ll;
	private TextView motiliketotal_tv, addfavoritesTv;
	private ViewPager motiimageView1;
	private ImageView motiimagesavelocal_im, motisharefb_im,
			motisharetwitter_im, motisendemail_im, motisendMMs_im,
			motivationReportImage;

	private MyPagerAdapter myPagerAdapter;
	public int currentIndex = 0;
	public boolean iniLoad = true;
	DatabaseHandler db;
	ImageInfo query;
	String like_count = "";
	Vector<ImageInfo> imageInfos;
	Handler handler = new Handler();
	Animation animationFadeIn;
	Animation animationFadeOut;
	private Target loadTarget;

	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";
	String path = "";
	static File dir = null;
	private File file;
	FavoriteManager favoriteManager;

	int counter = 0;

	RelativeLayout rlTotalPanel;
	RelativeLayout rlDecide;
	TextView txtLogin;
	TextView txtNoThanks;

	EditText commentstextforshare;
	RelativeLayout sharecommntsviewmoti;
	TextView buttontexshare;
	LinearLayout sharebtfbortwitter;

	LinearLayout swipeHeadmoti_ll;

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	/*
	 * control pagination
	 */
	private boolean hasMoreItem = true;
	private int currentPage = 1;

	Handler adHandler;
	private InterstitialAd mInterstitialAd;

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
		setContentView(R.layout.motiswipe);
		con = this;
		activity = this;
		db = new DatabaseHandler(con);
		favoriteManager = new FavoriteManager(con);
		animationFadeIn = AnimationUtils.loadAnimation(con, R.anim.fadein);
		animationFadeOut = AnimationUtils.loadAnimation(con, R.anim.fadeout);

		fb_session = Session.openActiveSessionFromCache(con);

		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
			initAd();
			*/
		}

		/*
		 * control pagination
		 */
		final int totalCount = AllMotivational.getAllMotivational().size();
		final float cnt = (float) totalCount
				/ AppConstant.PAGINATION_ITEM_COUNT;
		currentPage = (int) Math.ceil(cnt);

		if (currentPage == 0) {
			currentPage = 1;
		}
		if (totalCount >= AppConstant.totalMotivationalMeme) {
			hasMoreItem = false;
		}

		initUI();

	}

	@Override
	protected void onPause() {
		super.onPause();

		AppConstant.readFlagmoti = true;
	}

	private void initUI() {
		motivationReportImage = (ImageView) findViewById(R.id.motivationReportImage);

		// viewPager = (ViewPager) findViewById(R.id._viewPager);
		// transparent = (ImageView) findViewById(R.id.transparent);
		// transparent.setVisibility(View.VISIBLE);
		motiSwipeBack_ll = (LinearLayout) findViewById(R.id.motiSwipeBack_ll);

		motiHere_ll = (LinearLayout) findViewById(R.id.motiHere_ll);
		motiFirst_ll = (LinearLayout) findViewById(R.id.motiFirst_ll);
		motifavri_ll = (LinearLayout) findViewById(R.id.motifavri_ll);

		motifirstVp_ll = (LinearLayout) findViewById(R.id.motifirstVp_ll);
		motimessageNotShow_ll = (LinearLayout) findViewById(R.id.motimessageNotShow_ll);
		motiumCancle_ll = (LinearLayout) findViewById(R.id.motiumCancle_ll);

		motiAfterright_ll = (RelativeLayout) findViewById(R.id.motiAfterright_ll);
		motiAfterLeft_rl = (RelativeLayout) findViewById(R.id.motiAfterLeft_rl);
		motiShareImage_rl = (RelativeLayout) findViewById(R.id.motiShareImage_rl);
		swipeHeadmoti_ll = (LinearLayout) findViewById(R.id.swipeHeadmoti_ll);

		motiSwipeHome_im = (ImageView) findViewById(R.id.motiSwipeHome_im);
		motiSwipeShare_im = (ImageView) findViewById(R.id.motiSwipeShare_im);
		motiSwipeRel_im = (ImageView) findViewById(R.id.motiSwipeRel_im);
		motiSwipeFav_im = (ImageView) findViewById(R.id.motiSwipeFav_im);
		motiLike_im = (ImageView) findViewById(R.id.motiLike_im);
		motiimagesavelocal_im = (ImageView) findViewById(R.id.motiimagesavelocal_im);
		motisharefb_im = (ImageView) findViewById(R.id.motisharefb_im);
		motisharetwitter_im = (ImageView) findViewById(R.id.motisharetwitter_im);
		motisendemail_im = (ImageView) findViewById(R.id.motisendemail_im);
		motisendMMs_im = (ImageView) findViewById(R.id.motisendMMs_im);

		motiimageView1 = (ViewPager) findViewById(R.id.motiimageView1);

		motiliketotal_tv = (TextView) findViewById(R.id.motiliketotal_tv);
		addfavoritesTv = (TextView) findViewById(R.id.addfavoritesTv);

		rlTotalPanel = (RelativeLayout) findViewById(R.id.rlTotalPanel);
		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);

		sharecommntsviewmoti = (RelativeLayout) findViewById(R.id.sharecommntsviewmoti);
		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);
		buttontexshare = (TextView) findViewById(R.id.buttontexshare);

		motiSwipeBack_ll.setOnClickListener(this);
		motiSwipeHome_im.setOnClickListener(this);
		motiSwipeShare_im.setOnClickListener(this);
		motiSwipeRel_im.setOnClickListener(this);
		motiSwipeFav_im.setOnClickListener(this);
		motiLike_im.setOnClickListener(this);
		motiimagesavelocal_im.setOnClickListener(this);
		motisharefb_im.setOnClickListener(this);
		motisharetwitter_im.setOnClickListener(this);
		motisendemail_im.setOnClickListener(this);
		motisendMMs_im.setOnClickListener(this);

		motiHere_ll.setOnClickListener(this);
		motiFirst_ll.setOnClickListener(this);
		motifavri_ll.setOnClickListener(this);

		motiShareImage_rl.setOnClickListener(this);

		motifirstVp_ll.setOnClickListener(this);
		motimessageNotShow_ll.setOnClickListener(this);
		motiumCancle_ll.setOnClickListener(this);
		motiAfterright_ll.setOnClickListener(this);
		motiAfterLeft_rl.setOnClickListener(this);

		rlTotalPanel.setOnClickListener(this);
		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);
		sharebtfbortwitter.setOnClickListener(this);
		motivationReportImage.setOnClickListener(this);

		// viewPagerAdapterMoti = new
		// MyFragmentPagerAdapter(getSupportFragmentManager());
		// motiimageView1.setAdapter(viewPagerAdapterMoti);

		imageInfos = AllMotivational.getAllMotivational();
		myPagerAdapter = new MyPagerAdapter(con, imageInfos, swipeHeadmoti_ll);
		motiimageView1.setAdapter(myPagerAdapter);

		// ......................View Pager.................
		motiimageView1.setCurrentItem(SharedPreferencesHelper
				.getMotiViewpagerPosition(con));

		if (!(AllMotivational.getAllMotivational().size() > AppConstant.position)) {
			return;
		}

		query = AllMotivational.getAllMotivational().elementAt(
				AppConstant.position);

		AppConstant.imageid = query.getImageID();

		motiliketotal_tv.setText(getResources().getString(R.string.totallikes)
				+ "(" + query.getLikeCount() + ")");
		// motiliketotal_tv.setText("Total Likes (" + query.getLikeCount() +
		// ")");

		if (query != null) {

			db.addRead(query);

		}
		// if (db.isFavorite(query.getImageID(), query.getImageCategory()))
		if (query.getFavorite().equalsIgnoreCase("1")) {
			motiSwipeFav_im.setImageResource(R.drawable.motifavo);
		} else {
			motiSwipeFav_im.setImageResource(R.drawable.motistar);
		}

		// if (db.isLikeImage(query.getImageID(), query.getImageCategory()))
		if (query.getIsLike().equalsIgnoreCase("1")) {

			motiLike_im.setImageResource(R.drawable.motilike);
		} else {
			motiLike_im.setImageResource(R.drawable.beforelike);
		}

		motiimageView1.setOnPageChangeListener(new OnPageChangeListener() {

			/*
			 * boolean isLastPage; int selectedIndex; boolean isCallHappened;
			 * int pos = 0; String tag = "ViewPager";
			 */

			@Override
			public void onPageScrollStateChanged(int state) {

				if (motiimageView1.getCurrentItem() == AppConstant.totalMotivationalMeme - 1) {

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
							motiAfterright_ll.setVisibility(View.VISIBLE);
						}

					}

				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				/* By Jin
				if (positionOffset > 0) {
					motiSwipeFav_im.setImageResource(R.drawable.beforfavorite);
				} else if (positionOffset == 0.0f) {

					if (query.getFavorite().equalsIgnoreCase("1")) {
						motiSwipeFav_im.setImageResource(R.drawable.motifavo);
					} else {
						motiSwipeFav_im.setImageResource(R.drawable.motistar);
					}
				}
				*/

			}

			@Override
			public void onPageSelected(int arg0) {
				// pos++;
				// counter++;
				// selectedIndex = arg0;
				// transparent.setVisibility(View.VISIBLE);
				swipeHeadmoti_ll.setVisibility(View.VISIBLE);
				final int index = motiimageView1.getCurrentItem();

				AppConstant.position = index;
				currentIndex = index;

				SharedPreferencesHelper.setMotiViewpagerPosition(con, index);

				query = AllMotivational.getAllMotivational().elementAt(index);

				AppConstant.image = query.getImageUrl();
				AppConstant.totalLike = query.getLikeCount();
				AppConstant.imageid = query.getImageID();
				motiliketotal_tv.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");
				// motiliketotal_tv.setText("Total Likes (" +
				// query.getLikeCount()+ ")");

				if (query != null) {

					db.addRead(query);
					AllMotivational.getAllMotivational().elementAt(index)
							.setIsRead("1");

				}

				// if (db.isLikeImage(query.getImageID(),
				// query.getImageCategory()))
				if (query.getIsLike().equalsIgnoreCase("1")) {

					motiLike_im.setImageResource(R.drawable.motilike);
				} else {
					motiLike_im.setImageResource(R.drawable.beforelike);
				}

				// if (db.isFavorite(query.getImageID(),
				// query.getImageCategory()))
				if (query.getFavorite().equalsIgnoreCase("1")) {
					motiSwipeFav_im.setImageResource(R.drawable.motifavo);
				} else {
					motiSwipeFav_im.setImageResource(R.drawable.motistar);
				}

				final int vectorSize = AllMotivational.getAllMotivational()
						.size();

				if (currentIndex + 1 == vectorSize) {

					//

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

				// Update latest data from server
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
			final String url = AllURL.getMotivationalByPage(
					PersistentUser.getUserID(con), currentPage,
					AppConstant.PAGINATION_ITEM_COUNT);
			final AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] response) {

					try {
						MotivationalParser.parseNext(new String(response));
					} catch (final JSONException e) {
						e.printStackTrace();
					} catch (final IOException e) {
						e.printStackTrace();
					}
					final int newItemCount = AllMotivational
							.getAllMotivational().size();
					if (newItemCount >= AppConstant.totalMotivationalMeme) {
						hasMoreItem = false;
					}

					synchronized (MotivationalSwipeActivity.class) {
						myPagerAdapter.notifyDataSetChanged();
						motiimageView1.invalidate();
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
							motiliketotal_tv.setText(getResources().getString(
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

				addfavoritesTv.setVisibility(View.GONE);

				addfavoritesTv.startAnimation(animationFadeOut);

			}
		}, 1000);
	}

	public void exitCurrentActivity() {

		MotivationalSwipeActivity.this.finish();

	}

	public void toastMessage(String mes) {

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.motiSwipeBack_ll) {

			if (AppConstant.motishareview) {
				AppConstant.motishareview = false;
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_motivation_activity);
				exitCurrentActivity();
			} else {

				if (AppConstant.readmotishare) {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_close_share_option);
					motiShareImage_rl.setVisibility(View.GONE);
					AppConstant.readmotishare = false;

				} else {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_go_back_to_motivation_activity);
					AppConstant.readFlagmoti = true;
					exitCurrentActivity();

				}
			}

		} else if (v.getId() == R.id.motiSwipeHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			exitCurrentActivity();
			if (MotiInspiActivity.getInstance() != null) {
				MotiInspiActivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.motiSwipeRel_im) {

			// Reload
			if (SharedPreferencesHelper.getMotivationSwipeDialog(con)) {
				motiimageView1.setCurrentItem(0);
			} else {
				motiAfterLeft_rl.setVisibility(View.VISIBLE);
			}
			// motiimageView1.setCurrentItem(0);

			// final String reUrl = AllURL.getReloadinfo(AppConstant.imageid);
			// reload(reUrl);

		} else if (v.getId() == R.id.motiSwipeShare_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			motiShareImage_rl.setVisibility(View.VISIBLE);
			AppConstant.readmotishare = true;

			// final Intent ii = new Intent(con, MemeShareActivity.class);
			// startActivity(ii);

		} else if (v.getId() == R.id.motiSwipeFav_im) {

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
						// db.addFavoritesImg(query);
						favoriteManager.addToFavoriteOnline(query);

						motiSwipeFav_im.setImageResource(R.drawable.motifavo);

						addfavoritesTv.setText(""
								+ getResources().getString(
										R.string.addfavorites));

						addfavoritesTv.startAnimation(animationFadeIn);
						addfavoritesTv.setVisibility(View.VISIBLE);

						removeImageAfter1seconds();

						// toastMessage("Added to Favorites");

					} else {

						query.setFavorite("0");
						// addfavoritesTv
						favoriteManager.removeFromFavoriteOnline(query);
						motiSwipeFav_im.setImageResource(R.drawable.motistar);
						addfavoritesTv.setText(""
								+ getResources().getString(
										R.string.removefavorites));
						addfavoritesTv.startAnimation(animationFadeIn);
						addfavoritesTv.setVisibility(View.VISIBLE);

						removeImageAfter1seconds();

						// toastMessage("Removed from Favorites");

					}
				}
			}

		} else if (v.getId() == R.id.motiLike_im) {

			// Log.e("Liek status : ",">>> "+db.isLikeImage(AppConstant.imageid));
			if (SharedPreferencesHelper.getUsingskip(con)) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);
				// toastMessage("You are not authority like or dislike this memes");
				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_like_or_unlike_meme);
				// if (db.isLikeImage(AppConstant.imageid, "2"))
				if (query.getIsLike().equalsIgnoreCase("1"))

				{
					final String urlunlike = AllURL.getDisLike(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));

					Log.e("urlunlike: ", ">>> " + urlunlike);

					disLikeMeme(urlunlike);
				} else {
					final String urllike = AllURL.getLikeUpdate(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));

					Log.e("urllike: ", ">>> " + urllike);

					updatLikeMeme(urllike);
				}
			}

		} else if (v.getId() == R.id.motiHere_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keep_me_here);
			// motiimageView1.setCurrentItem(motiimageView1.getCurrentItem() -
			// 1);
			motiAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.motiFirst_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			// if (!SharedPreferencesHelper.getMotivationSwipeDialog(con)) {
			// motiAfterright_ll.setVisibility(View.GONE);
			// motiAfterLeft_rl.setVisibility(View.VISIBLE);
			// } else {
			motiAfterright_ll.setVisibility(View.GONE);
			motiimageView1.setCurrentItem(0);
			//
			// }

		} else if (v.getId() == R.id.motifavri_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_favorite_meme);
			final Intent ii = new Intent(con, FavoritesActivity.class);
			exitCurrentActivity();
			if (MotiInspiActivity.getInstance() != null) {
				MotiInspiActivity.getInstance().finish();
			}
			startActivity(ii);
		} else if (v.getId() == R.id.motifirstVp_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			motiimageView1.setCurrentItem(0);
			motiAfterLeft_rl.setVisibility(View.GONE);
		} else if (v.getId() == R.id.motimessageNotShow_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first_dont_show_again);
			motiAfterLeft_rl.setVisibility(View.GONE);
			SharedPreferencesHelper.setMotivationSwipeDialog(con, true);

		} else if (v.getId() == R.id.motiumCancle_ll) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_cancel_all);
			motiAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.motiimagesavelocal_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_create_bitmap_to_share);
			// Imave Save own device
			// final String imageurl = "" + AppConstant.imageBaseUrl
			// + AppConstant.image;
			//
			// Picasso.with(this).load(imageurl).into(target);

			getbitmapFromServer();

		} else if (v.getId() == R.id.motisharefb_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_facebook);
			// Image share fb

			SharedPreferencesHelper.setFbmeme(con, true);

			motiimagesavelocal_im.setClickable(false);
			motisharefb_im.setClickable(false);
			motisharetwitter_im.setClickable(false);
			motisendemail_im.setClickable(false);
			motisendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			// Remove : Sharing this from the MemeCabin app! Get it on Android
			// and iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");
			sharecommntsviewmoti.setVisibility(View.VISIBLE);
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

		} else if (v.getId() == R.id.motisharetwitter_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_twitter);
			// Image share twitter

			SharedPreferencesHelper.setTwittermeme(con, true);

			motiimagesavelocal_im.setClickable(false);
			motisharefb_im.setClickable(false);
			motisharetwitter_im.setClickable(false);
			motisendemail_im.setClickable(false);
			motisendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
			sharecommntsviewmoti.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.motisendemail_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_email);
			// Image send Email

			getbitmapFromServerForShare("email");

		} else if (v.getId() == R.id.motisendMMs_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_mms);
			// Image send MMs/SMS
			getbitmapFromServerForShare("mms");

		} else if (v.getId() == R.id.motiShareImage_rl) {

			if (sharecommntsviewmoti.getVisibility() == View.VISIBLE) {

				SharedPreferencesHelper.setFbmeme(con, false);
				SharedPreferencesHelper.setTwittermeme(con, false);

				motiimagesavelocal_im.setClickable(true);
				motisharefb_im.setClickable(true);
				motisharetwitter_im.setClickable(true);
				motisendemail_im.setClickable(true);
				motisendMMs_im.setClickable(true);

				closeKeyBoard(v);
				sharecommntsviewmoti.setVisibility(View.GONE);

			} else if (motiShareImage_rl.getVisibility() == View.VISIBLE
					&& sharecommntsviewmoti.getVisibility() == View.GONE) {

				AppConstant.motishareview = true;
				motiShareImage_rl.setVisibility(View.GONE);
			}

			/*
			 * AppConstant.motishareview = true;
			 * motiShareImage_rl.setVisibility(View.GONE);
			 */

		} else if (v.getId() == R.id.rlDecide) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtLogin) {
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_to_login);

			AppConstant.fromscreen = "allswipe";
			AppConstant.position = currentIndex;

			final Intent loginIntent = new Intent(con, LogActivity.class);
			startActivity(loginIntent);

			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtNoThanks) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.motiAfterright_ll) {
			// motiimageView1.setCurrentItem(motiimageView1.getCurrentItem());
			motiAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.motiAfterLeft_rl) {

			motiAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.sharebtfbortwitter) {

			closeKeyBoard(v);

			if (SharedPreferencesHelper.getFbmeme(con)) {

				motiimagesavelocal_im.setClickable(true);
				motisharefb_im.setClickable(true);
				motisharetwitter_im.setClickable(true);
				motisendemail_im.setClickable(true);
				motisendMMs_im.setClickable(true);

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

				motiimagesavelocal_im.setClickable(true);
				motisharefb_im.setClickable(true);
				motisharetwitter_im.setClickable(true);
				motisendemail_im.setClickable(true);
				motisendMMs_im.setClickable(true);

				SharedPreferencesHelper.setTwittermeme(con, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.motivationReportImage) {
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

							db.addLike(query);

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

					Log.e("new String(response)", ">>>>" + new String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("0");
							motiLike_im.setImageResource(R.drawable.beforelike);
							db.removeLike(query);

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

							motiliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// motiliketotal_tv.setText("Total Likes ("+
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

										if (makeMeRequest(myImage, value, "",
												con)) {
											// toastMessage("Image posted successfully!");
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											commentstextforshare.setText("");
											sharecommntsviewmoti
													.setVisibility(View.GONE);
										} else {
											if (busyDialog != null) {
												busyDialog.dismis();
											}
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
							sharecommntsviewmoti.setVisibility(View.GONE);
						}
					});
		}

	}

	public void decide() {
		final CharSequence[] items = { "Got it, take me to login.",
				"Eek. No thanks." };
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Oops!\n You have to be logged-in to save your favorite memes for easy access later!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					// AppConstant.fromswipescreen= true;
					AppConstant.fromscreen = "allswipe";
					AppConstant.position = currentIndex;
					final Intent ii = new Intent(con, LogActivity.class);
					startActivity(ii);
					// MotivationalSwipeActivity.this.finish();
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
		if (rlDecide.getVisibility() == View.VISIBLE) {
			rlDecide.setVisibility(View.GONE);
		} else {

			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {

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
