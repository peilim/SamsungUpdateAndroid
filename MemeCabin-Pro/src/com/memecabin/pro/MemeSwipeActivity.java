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
import com.learnncode.demotwitterimagepost.HelperMethods;
import com.learnncode.demotwitterimagepost.HelperMethods.TwitterCallback;
import com.learnncode.demotwitterimagepost.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.AllImageInfo;
import com.memecabin.holder.AllReload;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.MemeParser;
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

public class MemeSwipeActivity extends FragmentActivity implements
		OnClickListener {
	private Context con;
	private LinearLayout memeSwipeBack_ll, memeHere_ll, memeFirst_ll,
			memefavri_ll;
	private ImageView memeSwipeHome_im, memeSwipeRel_im, memeSwipeShare_im,
			memeSwipeFav_im, memeLike_im;
	private RelativeLayout memeAfterright_ll, viewfirstVp_rl, memeallshare_rl;
	private LinearLayout firstVp_ll, messageNotShow_ll, umCancle_ll;
	private TextView likeshowmeme_tv, addtofavoritesTv;
	private ImageView memeShareSave_im, memeShareFaceBook_im,
			memeShareTwitter_im, memeShareEmail_im, memeShareMMS_im,
			reportImage;

	private MyPagerAdapter myPagerAdapter;
	public int currentIndex = 0;
	public boolean iniLoad = true;
	DatabaseHandler db;
	ImageInfo query;
	String like_count = "";
	Vector<ImageInfo> imageInfos;
	private ViewPager viewPager;

	Animation animationFadeIn;
	Animation animationFadeOut;

	// fade
	int fadeInDuration = 500;
	int timeBetween = 3000;
	int fadeOutDuration = 1000;
	Handler handler = new Handler();

	boolean success = false;
	static File dir = null;
	private File file;

	FavoriteManager favoriteManager;

	private static final String twitter_consumer_key = "Bjm2ThbJgANH8rWB1v5hiFOTe";
	private static final String twitter_secret_key = "jEdpNeoAJwY8PX3cDNQ097Fhx64DDjtIxISwJH9CuZL8XjqqNl";

	// private static File dir = null;

	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";

	String path = "";
	private Target loadTarget;

	Handler adHandler;
	// private InterstitialAd mInterstitialAd;
	int courter;

	RelativeLayout rlDecide;
	TextView txtLogin;
	TextView txtNoThanks;

	EditText commentstextforshare;
	RelativeLayout sharecommntsviewmeme;
	TextView buttontexshare;
	LinearLayout sharebtfbortwitter;

	// LinearLayout likememeview;
	// RelativeLayout totoalviewMeme;
	LinearLayout swipeHead_ll;

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	/*
	 * control pagination
	 */
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
		setContentView(R.layout.memeswipe);
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
		final int totalCount = AllImageInfo.getAllImageInfo().size();
		final float cnt = (float) totalCount
				/ AppConstant.PAGINATION_ITEM_COUNT;
		currentPage = (int) Math.ceil(cnt);

		if (currentPage == 0) {
			currentPage = 1;
		}
		if (totalCount >= AppConstant.totalEveryoneMeme) {
			hasMoreItem = false;
		}

		/*
		 * end of pagination
		 */

		initUI();

	}

	@Override
	protected void onPause() {
		super.onPause();

		AppConstant.readFlagmeme = true;
	}

	private void initUI() {
		viewPager = (ViewPager) findViewById(R.id._viewPager);
		memeSwipeBack_ll = (LinearLayout) findViewById(R.id.memeSwipeBack_ll);
		memeHere_ll = (LinearLayout) findViewById(R.id.memeHere_ll);
		memeFirst_ll = (LinearLayout) findViewById(R.id.memeFirst_ll);
		memefavri_ll = (LinearLayout) findViewById(R.id.memefavri_ll);

		firstVp_ll = (LinearLayout) findViewById(R.id.firstVp_ll);
		messageNotShow_ll = (LinearLayout) findViewById(R.id.messageNotShow_ll);
		umCancle_ll = (LinearLayout) findViewById(R.id.umCancle_ll);
		// likememeview = (LinearLayout) findViewById(R.id.likememeview);
		swipeHead_ll = (LinearLayout) findViewById(R.id.swipeHead_ll);

		memeAfterright_ll = (RelativeLayout) findViewById(R.id.memeAfterright_ll);
		viewfirstVp_rl = (RelativeLayout) findViewById(R.id.viewfirstVp_rl);
		memeallshare_rl = (RelativeLayout) findViewById(R.id.memeallshare_rl);
		// totoalviewMeme = (RelativeLayout) findViewById(R.id.totoalviewMeme);

		memeSwipeHome_im = (ImageView) findViewById(R.id.memeSwipeHome_im);
		memeSwipeRel_im = (ImageView) findViewById(R.id.memeSwipeRel_im);
		memeSwipeShare_im = (ImageView) findViewById(R.id.memeSwipeShare_im);
		memeSwipeFav_im = (ImageView) findViewById(R.id.memeSwipeFav_im);
		memeLike_im = (ImageView) findViewById(R.id.memeLike_im);
		memeShareSave_im = (ImageView) findViewById(R.id.memeShareSave_im);
		memeShareFaceBook_im = (ImageView) findViewById(R.id.memeShareFaceBook_im);
		memeShareTwitter_im = (ImageView) findViewById(R.id.memeShareTwitter_im);
		memeShareEmail_im = (ImageView) findViewById(R.id.memeShareEmail_im);
		memeShareMMS_im = (ImageView) findViewById(R.id.memeShareMMS_im);
		reportImage = (ImageView) findViewById(R.id.reportImage);

		likeshowmeme_tv = (TextView) findViewById(R.id.likeshowmeme_tv);
		addtofavoritesTv = (TextView) findViewById(R.id.addtofavoritesTv);

		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);

		sharecommntsviewmeme = (RelativeLayout) findViewById(R.id.sharecommntsviewmeme);
		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);
		buttontexshare = (TextView) findViewById(R.id.buttontexshare);

		memeSwipeBack_ll.setOnClickListener(this);
		memeSwipeHome_im.setOnClickListener(this);
		memeSwipeRel_im.setOnClickListener(this);
		memeSwipeShare_im.setOnClickListener(this);
		memeSwipeFav_im.setOnClickListener(this);
		memeLike_im.setOnClickListener(this);
		memeShareSave_im.setOnClickListener(this);
		memeShareFaceBook_im.setOnClickListener(this);
		memeShareTwitter_im.setOnClickListener(this);
		memeShareEmail_im.setOnClickListener(this);
		memeShareMMS_im.setOnClickListener(this);

		memeHere_ll.setOnClickListener(this);
		memeFirst_ll.setOnClickListener(this);
		memefavri_ll.setOnClickListener(this);

		firstVp_ll.setOnClickListener(this);
		messageNotShow_ll.setOnClickListener(this);
		umCancle_ll.setOnClickListener(this);
		memeAfterright_ll.setOnClickListener(this);
		viewfirstVp_rl.setOnClickListener(this);

		memeallshare_rl.setOnClickListener(this);

		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);
		reportImage.setOnClickListener(this);

		// viewPager.setOnClickListener(this);

		// totoalviewMeme.setOnClickListener(this);

		sharebtfbortwitter.setOnClickListener(this);

		// viewPagerAdapterMeme = new
		// MyFragmentPagerAdapter(getSupportFragmentManager());
		// myViewPager.setAdapter(viewPagerAdapterMeme);

		imageInfos = AllImageInfo.getAllImageInfo();
		myPagerAdapter = new MyPagerAdapter(con, imageInfos, swipeHead_ll);
		viewPager.setAdapter(myPagerAdapter);
		viewPager.setCurrentItem(SharedPreferencesHelper
				.getMemeViewpagerPosition(con));

		Log.e("Meme swipe",
				"count "
						+ SharedPreferencesHelper.getMemeViewpagerPosition(con));

		// ......................View Pager.................
		// myViewPager.setCurrentItem(AppConstant.position);
		query = AllImageInfo.getAllImageInfo().elementAt(AppConstant.position);
		AppConstant.imageid = query.getImageID();

		likeshowmeme_tv.setText(getResources().getString(R.string.totallikes)
				+ "(" + query.getLikeCount() + ")");
		// likeshowmeme_tv.setText("Total Likes (" + query.getLikeCount() +
		// ")");

		if (query != null) {

			db.addRead(query);

		}

		// if (db.isLikeImage(query.getImageID(), query.getImageCategory()))
		if (query.getIsLike().equalsIgnoreCase("1")) {

			memeLike_im.setImageResource(R.drawable.alllikeafter);
		} else {
			memeLike_im.setImageResource(R.drawable.beforelike);
		}

		// if (db.isFavorite(query.getImageID(), query.getImageCategory()))
		if (query.getFavorite().equalsIgnoreCase("1")) {
			memeSwipeFav_im.setImageResource(R.drawable.afterfavememe);

			// addtofavoritesTv.setVisibility(View.GONE);
			// removefavoritesTv.setVisibility(View.VISIBLE);
			// removefavoritesTv.startAnimation(animationFadeIn);
			// removeImageAfter1seconds();

		} else {
			memeSwipeFav_im.setImageResource(R.drawable.favcon);
			// removefavoritesTv.setVisibility(View.GONE);
			// addtofavoritesTv.setVisibility(View.VISIBLE);
			// addtofavoritesTv.startAnimation(animationFadeIn);
			//
			// removeImageAfter1seconds();
		}

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			/*
			 * boolean isLastPage; int selectedIndex; boolean isCallHappened;
			 * int pos = 0; String tag = "ViewPager";
			 */

			@Override
			public void onPageScrollStateChanged(int state) {

				if (viewPager.getCurrentItem() == AppConstant.totalEveryoneMeme - 1) {

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
							memeAfterright_ll.setVisibility(View.VISIBLE);

						}

					}

				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				// Log.e("position",""+position);
				// Log.e("positionOffset",""+positionOffset);
				// Log.e("positionOffsetPixels",""+positionOffsetPixels);

				/* By Jin
				if (positionOffset > 0) {
					memeSwipeFav_im.setImageResource(R.drawable.memestarlike);
				} else if (positionOffset == 0.0f) {

					if (query.getFavorite().equalsIgnoreCase("1"))

					{
						memeSwipeFav_im
								.setImageResource(R.drawable.afterfavememe);

					} else {
						memeSwipeFav_im.setImageResource(R.drawable.favcon);

					}
				}
				*/
			}

			@Override
			public void onPageSelected(int arg0) {

				// courter++;

				/*
				 * pos++; Log.d(tag, "OnPageSelected Pos: " + pos);
				 * selectedIndex = arg0; // isCallHappened = false;
				 */

				swipeHead_ll.setVisibility(View.VISIBLE);
				final int index = viewPager.getCurrentItem();
				AppConstant.position = index;

				SharedPreferencesHelper.setMemeViewpagerPosition(con, index);

				currentIndex = index;
				Log.e("index onPageScrollStateChanged : ", "" + index);
				query = AllImageInfo.getAllImageInfo().elementAt(index);
				AppConstant.image = query.getImageUrl();
				AppConstant.totalLike = query.getLikeCount();
				AppConstant.imageid = query.getImageID();
				// likeshowmeme_tv.setText("Total Likes " +
				// AppConstant.totalLike);
				// likeshowmeme_tv.setText("Total Likes (" +
				// AppConstant.totalLike+ ")");
				likeshowmeme_tv.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");
				if (query != null) {

					db.addRead(query);
					AllImageInfo.getAllImageInfo().elementAt(index)
							.setIsRead("1");

				}

				// if (db.isLikeImage(query.getImageID(),
				// query.getImageCategory()))
				if (query.getIsLike().equalsIgnoreCase("1")) {

					memeLike_im.setImageResource(R.drawable.alllikeafter);
				} else {
					memeLike_im.setImageResource(R.drawable.beforelike);
				}

				// if (db.isFavorite(query.getImageID(),
				// query.getImageCategory()))
				if (query.getFavorite().equalsIgnoreCase("1"))

				{
					memeSwipeFav_im.setImageResource(R.drawable.afterfavememe);

				} else {
					memeSwipeFav_im.setImageResource(R.drawable.favcon);

				}

				final int vectorSize = AllImageInfo.getAllImageInfo().size();

				if (currentIndex + 1 == vectorSize) {

					if (hasMoreItem) {
						getMemeListFromServer();
					}

				}

				if (!SharedPreferencesHelper.isAddDisable(con)) {

					courter++;
					if (courter == 20) {

						/* Jin Remove Mopub ADS
						initAd();
						*/

						courter = 0;
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
			final String url = AllURL.geteveryoneMemesByPage(
					PersistentUser.getUserID(con), currentPage,
					AppConstant.PAGINATION_ITEM_COUNT);
			final AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] response) {

					try {
						MemeParser.parseNext(new String(response));
					} catch (final JSONException e) {
						e.printStackTrace();
					} catch (final IOException e) {
						e.printStackTrace();
					}
					final int newItemCount = AllImageInfo.getAllImageInfo()
							.size();
					if (newItemCount >= AppConstant.totalEveryoneMeme) {
						hasMoreItem = false;
					}

					synchronized (MemeSwipeActivity.class) {
						myPagerAdapter.notifyDataSetChanged();
						viewPager.invalidate();
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
							likeshowmeme_tv.setText(getResources().getString(
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

				addtofavoritesTv.setVisibility(View.GONE);

				addtofavoritesTv.startAnimation(animationFadeOut);

			}
		}, 1000);
	}

	public void exitCurrentActivity() {

		MemeSwipeActivity.this.finish();

	}

	public void toastMessage(String mes) {

		// Toast.makeText(con, mes, 3000).show();

		final Toast toast = Toast.makeText(getApplicationContext(), mes,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.memeSwipeBack_ll) {
			AppConstant.readFlagmeme = true;

			if (AppConstant.shareviewclose) {
				AppConstant.shareviewclose = false;
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_all_meme);
				exitCurrentActivity();

			} else {

				if (AppConstant.readallmemeshare) {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_close_share_option);
					AppConstant.readallmemeshare = false;
					memeallshare_rl.setVisibility(View.GONE);

				} else {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_go_back_to_all_meme);
					// AppConstant.readFlagmeme = true;
					exitCurrentActivity();
				}
			}

		} else if (v.getId() == R.id.memeSwipeHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);

			exitCurrentActivity();

			if (MemesEverUnreadActivity.getInstance() != null) {
				MemesEverUnreadActivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.memeSwipeRel_im) {

			// viewPager.setCurrentItem(0);
			if (SharedPreferencesHelper.getMemeSwipeDialog(con)) {
				viewPager.setCurrentItem(0);
			} else {
				viewfirstVp_rl.setVisibility(View.VISIBLE);
			}

			// final String reUrl = AllURL.getReloadinfo(AppConstant.imageid);
			// reload(reUrl);

		} else if (v.getId() == R.id.memeSwipeShare_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			memeallshare_rl.setVisibility(View.VISIBLE);
			AppConstant.readallmemeshare = true;

			// final Intent ii = new Intent(con,MemeShareActivity.class);
			// startActivity(ii);
			// exitCurrentActivity();

		} else if (v.getId() == R.id.memeSwipeFav_im) {
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
						favoriteManager.addToFavoriteOnline(query);
						memeSwipeFav_im
								.setImageResource(R.drawable.afterfavememe);

						addtofavoritesTv.setText(""
								+ getResources().getString(
										R.string.addfavorites));
						addtofavoritesTv.startAnimation(animationFadeIn);
						addtofavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

					} else {

						query.setFavorite("0");
						db.removeFavoritesImg(query);
						favoriteManager.removeFromFavoriteOnline(query);
						memeSwipeFav_im.setImageResource(R.drawable.favcon);

						addtofavoritesTv.setText(""
								+ getResources().getString(
										R.string.removefavorites));
						addtofavoritesTv.startAnimation(animationFadeIn);
						addtofavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

					}
				}

			}

		} else if (v.getId() == R.id.memeLike_im) {

			if (SharedPreferencesHelper.getUsingskip(con)) {
				// toastMessage("You are not authority like or dislike this memes");
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);
				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_like_or_unlike_meme);
				// if (!db.isLikeImage(AppConstant.imageid, "1"))
				if (!query.getIsLike().equalsIgnoreCase("1"))

				{

					final String urllike = AllURL.getLikeUpdate(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));
					// likeshowmeme_tv.set
					updatLikeMeme(urllike);
				} else {

					final String urlunlike = AllURL.getDisLike(
							AppConstant.imageid,
							SharedPreferencesHelper.getUserID(con));
					disLikeMeme(urlunlike);
				}

			}

		} else if (v.getId() == R.id.memeHere_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keep_me_here);
			memeAfterright_ll.setVisibility(View.GONE);
			// viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

		} else if (v.getId() == R.id.memeFirst_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			// if (!SharedPreferencesHelper.getMemeSwipeDialog(con)) {
			// memeAfterright_ll.setVisibility(View.GONE);
			// viewfirstVp_rl.setVisibility(View.VISIBLE);
			// } else {
			memeAfterright_ll.setVisibility(View.GONE);
			viewPager.setCurrentItem(0);
			// }
		} else if (v.getId() == R.id.memefavri_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_favorite_meme);
			final Intent ii = new Intent(con, FavoritesActivity.class);
			exitCurrentActivity();
			if (MemesEverUnreadActivity.getInstance() != null) {
				MemesEverUnreadActivity.getInstance().finish();
			}
			startActivity(ii);
		} else if (v.getId() == R.id.firstVp_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			viewfirstVp_rl.setVisibility(View.GONE);
			viewPager.setCurrentItem(0);
		} else if (v.getId() == R.id.messageNotShow_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first_dont_show_again);
			viewfirstVp_rl.setVisibility(View.GONE);
			SharedPreferencesHelper.setMemeSwipeDialog(con, true);

		} else if (v.getId() == R.id.umCancle_ll) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_cancel_all);
			viewfirstVp_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.memeShareSave_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_create_bitmap_to_share);
			// Image Save in own device

			// final String imageurl = "" + AppConstant.imageBaseUrl
			// + AppConstant.image;
			//
			// Picasso.with(this).load(imageurl).into(target);

			getbitmapFromServer();

		} else if (v.getId() == R.id.memeShareFaceBook_im) {
			SharedPreferencesHelper.setFbmeme(con, true);

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_facebook);
			// Image Share fb

			memeShareSave_im.setClickable(false);
			memeShareFaceBook_im.setClickable(false);
			memeShareTwitter_im.setClickable(false);
			memeShareEmail_im.setClickable(false);
			memeShareMMS_im.setClickable(false);

			commentstextforshare.setText("");
			// Remove : Sharing this from the MemeCabin app! Get it on Android
			// and iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");
			sharecommntsviewmeme.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			/*
			 * Log.e("facbook session", ":" +
			 * facebookConnector.getFacebook().isSessionValid());
			 * 
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

		} else if (v.getId() == R.id.memeShareTwitter_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_twitter);
			// Image Share Twitter
			/*
			 * final String imageurl = "" + AppConstant.imageBaseUrl +
			 * AppConstant.image;
			 */

			// loadData(imageurl, "twitter");
			SharedPreferencesHelper.setTwittermeme(con, true);
			memeShareSave_im.setClickable(false);
			memeShareFaceBook_im.setClickable(false);
			memeShareTwitter_im.setClickable(false);
			memeShareEmail_im.setClickable(false);
			memeShareMMS_im.setClickable(false);

			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
			sharecommntsviewmeme.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.memeShareEmail_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_email);
			// Image send via Email
			/*
			 * final String imageurl = "" + AppConstant.imageBaseUrl +
			 * AppConstant.image;
			 */

			// loadData(imageurl, "email");

			getbitmapFromServerForShare("email");

		} else if (v.getId() == R.id.memeShareMMS_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_mms);
			// Image send via MMS/SMS

			getbitmapFromServerForShare("mms");
		} else if (v.getId() == R.id.memeallshare_rl) {
			// no action

			if (sharecommntsviewmeme.getVisibility() == View.VISIBLE) {

				SharedPreferencesHelper.setFbmeme(con, false);
				SharedPreferencesHelper.setTwittermeme(con, false);

				memeShareSave_im.setClickable(true);
				memeShareFaceBook_im.setClickable(true);
				memeShareTwitter_im.setClickable(true);
				memeShareEmail_im.setClickable(true);
				memeShareMMS_im.setClickable(true);
				closeKeyBoard(v);
				sharecommntsviewmeme.setVisibility(View.GONE);

			} else if (memeallshare_rl.getVisibility() == View.VISIBLE
					&& sharecommntsviewmeme.getVisibility() == View.GONE) {

				AppConstant.shareviewclose = true;
				memeallshare_rl.setVisibility(View.GONE);
			}

		} else if (v.getId() == R.id.memeAfterright_ll) {
			// viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			memeAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.viewfirstVp_rl) {
			AppConstant.readallmemeshare = true;
			viewfirstVp_rl.setVisibility(View.GONE);

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

		} else if (v.getId() == R.id.sharebtfbortwitter) {

			closeKeyBoard(v);

			if (SharedPreferencesHelper.getFbmeme(con)) {

				memeShareSave_im.setClickable(true);
				memeShareFaceBook_im.setClickable(true);
				memeShareTwitter_im.setClickable(true);
				memeShareEmail_im.setClickable(true);
				memeShareMMS_im.setClickable(true);

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

				memeShareSave_im.setClickable(true);
				memeShareFaceBook_im.setClickable(true);
				memeShareTwitter_im.setClickable(true);
				memeShareEmail_im.setClickable(true);
				memeShareMMS_im.setClickable(true);

				SharedPreferencesHelper.setTwittermeme(con, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.reportImage) {

			final Intent i = new Intent(con, ReportActivity.class);
			startActivity(i);

		}
		/*
		 * else if(v.getId() == R.id.totoalviewMeme){
		 * if(swipeHead_ll.getVisibility() == View.VISIBLE){
		 * swipeHead_ll.setVisibility(View.GONE); }
		 * 
		 * else{ swipeHead_ll.setVisibility(View.VISIBLE);
		 * 
		 * }
		 * 
		 * }
		 */

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
							memeLike_im
									.setImageResource(R.drawable.alllikeafter);

							query.setIsLike("1");

							db.addLike(query);

							// toastMessage("Like successed");
							// likeshowmeme_tv.setText("Total Likes ("+
							// query.getLikeCount() + ")");
							likeshowmeme_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
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
							memeLike_im.setImageResource(R.drawable.beforelike);

							query.setIsLike("0");

							db.removeLike(query);

							// likeshowmeme_tv.setText("Total Likes ("+
							// query.getLikeCount() + ")");
							likeshowmeme_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");

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

							// likeshowmeme_tv.setText("Total Likes ("+
							// AppConstant.totalLike + ")");
							likeshowmeme_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");

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

								// final String imagePath = MakeBitmapAndSave
								// .saveBitmapPathToShare(myImage, "");

								final String value = commentstextforshare
										.getText().toString();

								// final Bitmap b = BitmapFactory
								// .decodeFile(imagePath);

								AppConstant.fbImage = myImage;

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
											sharecommntsviewmeme
													.setVisibility(View.GONE);
										} else {
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											// toastMessage("Facebook post failed!");
											commentstextforshare.setText("");
											sharecommntsviewmeme
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
											sharecommntsviewmeme
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
					AppConstant.fromscreen = "allswipe";
					AppConstant.position = currentIndex;
					final Intent ii = new Intent(con, LogActivity.class);
					startActivity(ii);
					// MemeSwipeActivity.this.finish();
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

					Log.d(TAG, "----------------response----------------"
							+ response);
					Toast.makeText(con,
							getString(R.string.image_posted_on_twitter),
							Toast.LENGTH_SHORT).show();
					commentstextforshare.setText("");
					sharecommntsviewmeme.setVisibility(View.GONE);
				}
			});
		}

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

		mInterstitial = new MoPubInterstitial(MemeSwipeActivity.this,
				AppConstant.mopubAdsUnitIdFullScreen);
		mInterstitial.setKeywords("images");
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
