package com.memecabin.free;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.FavoriteManager;
import com.aacom.memecabin.utils.GlobalUtils;
import com.aacom.memecabin.utils.MMSSender;
import com.aacom.memecabin.utils.MakeBitmapAndSave;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.iap1.util.IabHelper;
import com.aapbd.iap1.util.IabResult;
import com.aapbd.iap1.util.Inventory;
import com.aapbd.iap1.util.Purchase;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.appsnack.ad.helpers.model.ASNKAdRequest;
import com.appsnack.ad.helpers.model.ASNKDemographics;
import com.appsnack.ad.helpers.model.ASNKEventListener;
import com.appsnack.ad.helpers.model.ASNKDemographics.Gender;
import com.appsnack.ad.view.AppsnackView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdInterstitial;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.learnncode.demotwitterimagepost.HelperMethods;
import com.learnncode.demotwitterimagepost.HelperMethods.TwitterCallback;
import com.learnncode.demotwitterimagepost.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.AllReload;
import com.memecabin.holder.AllTrending;
import com.memecabin.model.ImageInfo;
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

public class TendingSwipeScrinActivity extends FragmentActivity implements
		OnClickListener {
	private Context con;
	private TextView trendingtoalalLike, treadingposition_tv,
			treadingTitelSwipe_tv;
	private LinearLayout tenSwipScrBack_ll, tenSwipeSlide_ll;
	private LinearLayout trenHere_ll, trenFirst_ll, trenfavri_ll;
	private LinearLayout trenfirstVp_ll, trenmessageNotShow_ll,
			trenumCancle_ll;
	private RelativeLayout trenAfterright_ll, trenAfterLeft_rl,
			trenSharememe_rl, screenimageframe_rl;
	private ImageView tenSwipeHome_im, tendSwipShare_im, tendSwipeFav_im,
			tenLike_im, tendingReload;
	private TextView todayslikeTextVies, s7dayslikeTextVies,
			t30dayslikeTexVies, addtendingfavoritesTv;
	private ViewPager tenSwipe_vp;
	private ImageView tendingsaveimage_im, tendingsharefb_im,
			tendingsharetwitter_im, tendingsendemail_im, tendingsendMMs_im,
			screenshot_im;
	private LinearLayout saveimagescreenshot_rl;
	private RelativeLayout rlBottomBorder;
	// private MyFragmentPagerAdapter viewPagerAdapter;
	public int currentIndex = 0;
	public boolean iniLoad = true;
	DatabaseHandler db;
	ImageInfo query;
	String like_count = "", todayslike_count = "", thirty_likecount = "",
			seven_likecount = "";

	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;

	RelativeLayout rlDecide;
	TextView txtLogin;
	TextView txtNoThanks;

	Vector<ImageInfo> imageInfos;
	private MyPagerAdapter myPagerAdapter;
	Animation animationFadeIn;
	Animation animationFadeOut;
	Handler handler = new Handler();

	FavoriteManager favoriteManager;

	private Target loadTarget;

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;
	// private static final String FACEBOOK_APPID = "558359717642213";
	private static final String FACEBOOK_APPID = "841310412569994";// from DAN
	private static final String[] FACEBOOK_PERMISSION = { "publish_stream" };
	private static final String MSG = "Message from FacebookSample";
	String path = "";
	static File dir = null;
	private File file;

	int courter = 0;

	TextView onlyconstracturecal;

	EditText commentstextforshare;
	RelativeLayout sharecommntsviewtend;
	TextView buttontexshare;
	LinearLayout sharebtfbortwitter;

	// Pointer selectedPointer;

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

	private SlidingMenuActions menuActions;

	ImageView tradingReportImage;

	/* Jin Remove Mopub ADS
	private MoPubInterstitial mInterstitial;
	private MoPubView moPubView;
	*/
	private ViewGroup adsBannerView;
	private AdView	  googleAdsBannerView;
	private FlurryAdBanner mFlurryAdBanner = null;
	private InterstitialAd mInterstitialAd;
	private FlurryAdInterstitial mFlurryAdInterstitial = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.trendingswipescr);
		con = this;
		activity = this;
		db = new DatabaseHandler(con);
		favoriteManager = new FavoriteManager(con);
		animationFadeIn = AnimationUtils.loadAnimation(con, R.anim.fadein);
		animationFadeOut = AnimationUtils.loadAnimation(con, R.anim.fadeout);
		// selectedPointer = AppManager.getInstance().selectedPointer;

		fb_session = Session.openActiveSessionFromCache(con);

		initUI();

		// Add New ADS
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// add call
		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/*
			 * Jin Remove Mopub ADS addShow();
			 */
			mFlurryAdInterstitial = new FlurryAdInterstitial(this,
					"2015MemeCabin-Intersitial");
			initAd();

			int width = dm.widthPixels;
			adsBannerView = (ViewGroup) findViewById(R.id.adview);
			googleAdsBannerView = (AdView) findViewById(R.id.google_adView);
			googleAdsBannerView.setAdListener(new AdListener() {
				@Override
				public void onAdFailedToLoad(int errorCode) {
					mFlurryAdBanner = new FlurryAdBanner(
							TendingSwipeScrinActivity.this, adsBannerView,
							"2015MemeCabin-Banner");

					mFlurryAdBanner.fetchAndDisplayAd();
				}
			});
			
			googleAdsBannerView.setVisibility(View.VISIBLE);
			AdRequest adRequest = new AdRequest.Builder().build();
			googleAdsBannerView.loadAd(adRequest);

			/*
			ASNKAdRequest request = new ASNKAdRequest()
					.setPubId("MemeCabinView").setAdSpaceName("Android")
					.setAdSpaceIntegrationType(ASNKAdRequest.TYPE_BANNER)
					.setAdSpaceWidth(width).setAdSpaceHeight(50)
					.setKeywords("optional keywords");

			AppsnackView ad = new AppsnackView(this, request);

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					(int) GlobalUtils.convertDpToPixel(50, this));
			ad.setLayoutParams(layoutParams);
			adsBannerView.addView(ad);

			ad.setASNKEventListener(new ASNKEventListener() {

				@Override
				public void onAdLoaded(String network) {
					// ad successfully loaded
				}

				@Override
				public void onAdFailed(String network, String message,
						int errorCode) {
					// ad failed to load
					googleAdsBannerView.setVisibility(View.VISIBLE);
					AdRequest adRequest = new AdRequest.Builder().build();
					googleAdsBannerView.loadAd(adRequest);
				}

				@Override
				public void onAdExpanded(String network) {
					// ad expanded
				}

				@Override
				public void onAdExpandClosed(String network) {
					// expand was closed
				}

				@Override
				public void onAdCustomEvent(String network, String event) {
					// mediation network specific event has been fired
				}

				@Override
				public void onAdChanged(String network) {
					// ad network was changed
				}

				@Override
				public void onAdActionPause() {
					// recommendation to pause application logics
				}

				@Override
				public void onAdActionResume() {
					// recommendation to resume application logics
				}

				@Override
				public void onAdActionPreloadReady() {
					// requested preloaded ad is ready to be displayed
				}

				@Override
				public void onAdActionPreloadFailed() {
					// requested preloaded ad failed
				}
			});

			ASNKDemographics.getInstance().setZipCode(61157).setAge(1988)
					.setGender(Gender.MALE);
					
			*/

		}

		initBIllng();

	}

	private void initUI() {

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset12);
		menu.setFadeDegree(0.1f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.slidememeaction);
		menuActions = new SlidingMenuActions(con, menu, appRate_rl,
				disableAdd_rl, getSdl_rl, singleFB_rl, silgeInstagram_rl,
				logoutRelativeLayout, dialogRelativelayout, fullscreenslide,
				getnewapps, singledadRelativelayout, rateappRelativelayout,
				fivestarlinear, rmaindmelinear, noidontwannalinear,
				rateheadinglinear, disableaddheadinglinear, disablefor99,
				disableforever99, nahichangemind, singledateheadinglinear,
				yahtakeme2sateit, remindmenext, bahidontcare);
		tradingReportImage = (ImageView) findViewById(R.id.tradingReportImage);
		tenSwipScrBack_ll = (LinearLayout) findViewById(R.id.tenSwipScrBack_ll);
		tenSwipeSlide_ll = (LinearLayout) findViewById(R.id.tenSwipeSlide_ll);

		trenHere_ll = (LinearLayout) findViewById(R.id.trenHere_ll);
		trenFirst_ll = (LinearLayout) findViewById(R.id.trenFirst_ll);
		trenfavri_ll = (LinearLayout) findViewById(R.id.trenfavri_ll);

		trenfirstVp_ll = (LinearLayout) findViewById(R.id.trenfirstVp_ll);
		trenmessageNotShow_ll = (LinearLayout) findViewById(R.id.trenmessageNotShow_ll);
		trenumCancle_ll = (LinearLayout) findViewById(R.id.trenumCancle_ll);

		trenAfterright_ll = (RelativeLayout) findViewById(R.id.trenAfterright_ll);
		trenAfterLeft_rl = (RelativeLayout) findViewById(R.id.trenAfterLeft_rl);
		// trenSharememe_rl = (RelativeLayout)
		// findViewById(R.id.trenSharememe_rl);
		screenimageframe_rl = (RelativeLayout) findViewById(R.id.screenimageframe_rl);
		// aveimagescreenshot_rl = (LinearLayout)
		// findViewById(R.id.saveimagescreenshot_rl);

		sharecommntsviewtend = (RelativeLayout) findViewById(R.id.sharecommntsviewtend);
		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);
		buttontexshare = (TextView) findViewById(R.id.buttontexshare);

		tenSwipeHome_im = (ImageView) findViewById(R.id.tenSwipeHome_im);
		tendSwipShare_im = (ImageView) findViewById(R.id.tendSwipShare_im);
		tendSwipeFav_im = (ImageView) findViewById(R.id.tendSwipeFav_im);
		tenLike_im = (ImageView) findViewById(R.id.tenLike_im);
		tenSwipe_vp = (ViewPager) findViewById(R.id.tenSwipe_vp);
		tendingReload = (ImageView) findViewById(R.id.tendingReload);
		tendingsaveimage_im = (ImageView) findViewById(R.id.tendingsaveimage_im);
		tendingsharefb_im = (ImageView) findViewById(R.id.tendingsharefb_im);
		tendingsharetwitter_im = (ImageView) findViewById(R.id.tendingsharetwitter_im);
		tendingsendemail_im = (ImageView) findViewById(R.id.tendingsendemail_im);
		tendingsendMMs_im = (ImageView) findViewById(R.id.tendingsendMMs_im);
		// screenshot_im = (ImageView) findViewById(R.id.screenshot_im);

		// rlBottomBorder = (RelativeLayout) findViewById(R.id.rlBottomBorder);

		trendingtoalalLike = (TextView) findViewById(R.id.trendingtoalalLike);
		treadingposition_tv = (TextView) findViewById(R.id.treadingposition_tv);
		treadingTitelSwipe_tv = (TextView) findViewById(R.id.treadingTitelSwipe_tv);
		addtendingfavoritesTv = (TextView) findViewById(R.id.addtendingfavoritesTv);
		onlyconstracturecal = (TextView) findViewById(R.id.onlyconstracturecal);

		todayslikeTextVies = (TextView) findViewById(R.id.todayslikeTextVies);
		s7dayslikeTextVies = (TextView) findViewById(R.id.s7dayslikeTextVies);
		t30dayslikeTexVies = (TextView) findViewById(R.id.t30dayslikeTexVies);

		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);

		treadingTitelSwipe_tv.setText("" + AppConstant.treadingMemesName);

		tenSwipScrBack_ll.setOnClickListener(this);
		tenSwipeSlide_ll.setOnClickListener(this);
		tenSwipeHome_im.setOnClickListener(this);
		tendSwipShare_im.setOnClickListener(this);
		tenLike_im.setOnClickListener(this);
		tendSwipeFav_im.setOnClickListener(this);
		tendingReload.setOnClickListener(this);
		tendingsaveimage_im.setOnClickListener(this);
		tendingsharefb_im.setOnClickListener(this);
		tendingsharetwitter_im.setOnClickListener(this);
		tendingsendemail_im.setOnClickListener(this);
		tendingsendMMs_im.setOnClickListener(this);
		screenimageframe_rl.setOnClickListener(this);

		trenHere_ll.setOnClickListener(this);
		trenFirst_ll.setOnClickListener(this);
		trenfavri_ll.setOnClickListener(this);
		trenfirstVp_ll.setOnClickListener(this);
		trenmessageNotShow_ll.setOnClickListener(this);
		trenumCancle_ll.setOnClickListener(this);
		trenAfterright_ll.setOnClickListener(this);
		trenAfterLeft_rl.setOnClickListener(this);

		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);
		sharebtfbortwitter.setOnClickListener(this);
		tradingReportImage.setOnClickListener(this);

		// viewPagerAdapter = new MyFragmentPagerAdapter(
		// getSupportFragmentManager());
		// tenSwipe_vp.setAdapter(viewPagerAdapter);
		imageInfos = AllTrending.getAllTrending();

		myPagerAdapter = new MyPagerAdapter(con, imageInfos,
				onlyconstracturecal);
		tenSwipe_vp.setAdapter(myPagerAdapter);

		// ......................View Pager.................
		treadingposition_tv.setText("#" + (AppConstant.position + 1));
		tenSwipe_vp.setCurrentItem(SharedPreferencesHelper
				.getTrendViewpagerPosition(con));

		if (!(AllTrending.getAllTrending().size() > AppConstant.position)) {
			return;
		}

		query = AllTrending.getAllTrending().elementAt(AppConstant.position);
		AppConstant.imageid = query.getImageID();
		AppConstant.imagecategory = query.getImageCategory();

		// todayslikeTextVies.setText(getResources().getString(R.string.like2days)+
		// "(" + query.getDayslikecount() + ")");
		// s7dayslikeTextVies.setText("7 Days (" + query.getS7daytotalLike() +
		// ")");
		// t30dayslikeTexVies.setText("30 Days (" + query.getT30daytotalLike()+
		// ")");
		trendingtoalalLike.setText(getResources()
				.getString(R.string.totallikes)
				+ "("
				+ query.getLikeCount()
				+ ")");
		todayslikeTextVies.setText(getResources().getString(R.string.like2days)
				+ "(" + query.getDayslikecount() + ")");
		s7dayslikeTextVies.setText(getResources()
				.getString(R.string.seve7likes)
				+ "("
				+ query.getS7daytotalLike() + ")");
		t30dayslikeTexVies.setText(getResources().getString(
				R.string.thirty30likes)
				+ "(" + query.getT30daytotalLike() + ")");

		// if (db.isLikeImage(query.getImageID(), query.getImageCategory()))
		if (query.getIsLike().equalsIgnoreCase("1")) {

			tenLike_im.setImageResource(R.drawable.likeshow);
		} else {
			tenLike_im.setImageResource(R.drawable.tredinglikeafter);
		}

		// if (db.isFavorite(query.getImageID(), query.getImageCategory()))
		if (query.getFavorite().equalsIgnoreCase("1"))

		{
			tendSwipeFav_im.setImageResource(R.drawable.afterfavetendingstar);
		} else {
			tendSwipeFav_im.setImageResource(R.drawable.tedstar);
		}

		tenSwipe_vp.setOnPageChangeListener(new OnPageChangeListener() {

			// boolean isLastPage;
			// int selectedIndex;
			// boolean isCallHappened;
			// int pos = 0;
			// int courter = 0;
			// String tag = "ViewPager";

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				/* By Jin
				if (positionOffset > 0) {
					tendSwipeFav_im.setImageResource(R.drawable.tedstar2);
				} else if (positionOffset == 0.0f) {

					if (query.getFavorite().equalsIgnoreCase("1"))

					{
						tendSwipeFav_im
								.setImageResource(R.drawable.afterfavetendingstar);
					} else {
						tendSwipeFav_im.setImageResource(R.drawable.tedstar);
					}
				}
				*/

			}

			@Override
			public void onPageSelected(int arg0) {
				// courter++;
				// pos++;
				// Log.d(tag, "OnPageSelected Pos: " + pos);
				// selectedIndex = arg0;
				// isCallHappened = false;

				final int index = tenSwipe_vp.getCurrentItem();
				currentIndex = index;
				AppConstant.position = index;
				SharedPreferencesHelper.setTrendViewpagerPosition(con, index);

				query = AllTrending.getAllTrending().elementAt(index);
				AppConstant.image = query.getImageUrl();
				AppConstant.totalLike = query.getLikeCount();
				AppConstant.imageid = query.getImageID();

				AppConstant.imagecategory = query.getImageCategory();
				treadingposition_tv.setText("#" + (currentIndex + 1));
				trendingtoalalLike.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");
				todayslikeTextVies.setText(getResources().getString(
						R.string.like2days)
						+ "(" + query.getDayslikecount() + ")");
				s7dayslikeTextVies.setText(getResources().getString(
						R.string.seve7likes)
						+ "(" + query.getS7daytotalLike() + ")");
				t30dayslikeTexVies.setText(getResources().getString(
						R.string.thirty30likes)
						+ "(" + query.getT30daytotalLike() + ")");
				// if (db.isLikeImage(query.getImageID(),
				// query.getImageCategory()))
				if (query.getIsLike().equalsIgnoreCase("1")) {

					tenLike_im.setImageResource(R.drawable.likeshow);
				} else {
					tenLike_im.setImageResource(R.drawable.tredinglikeafter);
				}

				if (query.getFavorite().equalsIgnoreCase("1"))

				{
					tendSwipeFav_im
							.setImageResource(R.drawable.afterfavetendingstar);
				} else {
					tendSwipeFav_im.setImageResource(R.drawable.tedstar);
				}

				final int vectorSize = AllTrending.getAllTrending().size();

				if (currentIndex + 1 == vectorSize) {

					trenAfterright_ll.setVisibility(View.VISIBLE);

				}

				if (!SharedPreferencesHelper.isAddDisable(con)) {

					courter++;
					if (courter == 20) {

						initAd();
						
						courter = 0;
					}
				}

				loadMemeDetail(query.getImageID());

			}

		});

	}

	public void exitCurrentActivity() {

		TendingSwipeScrinActivity.this.finish();

	}

	public void toastMessage(String mes) {

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
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
							trendingtoalalLike.setText(getResources()
									.getString(R.string.totallikes)
									+ "("
									+ query.getLikeCount() + ")");
							todayslikeTextVies.setText(getResources()
									.getString(R.string.like2days)
									+ "("
									+ query.getDayslikecount() + ")");
							s7dayslikeTextVies.setText(getResources()
									.getString(R.string.seve7likes)
									+ "("
									+ query.getS7daytotalLike() + ")");
							t30dayslikeTexVies.setText(getResources()
									.getString(R.string.thirty30likes)
									+ "("
									+ query.getT30daytotalLike() + ")");

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

	public void removeImageAfter1seconds()

	{

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				addtendingfavoritesTv.setVisibility(View.GONE);

				addtendingfavoritesTv.startAnimation(animationFadeOut);

			}
		}, 1000);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tenSwipScrBack_ll) {
			AppConstant.trendingRefresh = true;

			if (AppConstant.trensview) {
				AppConstant.trensview = false;
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_motivation_activity);
				AllTrending.getAllTrending().remove(
						AllTrending.getAllTrending().size() - 1);
				exitCurrentActivity();

			} else {

				if (AppConstant.readtrenshare) {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_close_share_option);
					AppConstant.readtrenshare = false;
					SharedPreferencesHelper.getTrendViewpagerPosition(con);
					screenimageframe_rl.setVisibility(View.GONE);
					tenSwipe_vp.setVisibility(View.VISIBLE);

				} else {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_go_back_to_motivation_activity);
					AllTrending.getAllTrending().remove(
							AllTrending.getAllTrending().size() - 1);
					exitCurrentActivity();
				}
			}

		} else if (v.getId() == R.id.tenSwipeSlide_ll) {

			menu.toggle();

		} else if (v.getId() == R.id.tendSwipShare_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			// AppConstant.onpageChangeview= false;
			AppConstant.readtrenshare = true;
			SharedPreferencesHelper
					.setTrendViewpagerPosition(con, currentIndex);
			// tenSwipe_vp.setVisibility(View.VISIBLE);
			screenimageframe_rl.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.tenSwipeHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			TendingSwipeScrinActivity.this.finish();

			if (TendingScrctivity.getInstance() != null) {
				TendingScrctivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.tenLike_im) {
			if (SharedPreferencesHelper.getUsingskip(con)) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_open_login_decide);
				// toastMessage("You are not authority like or dislike this memes");
				rlDecide.setVisibility(View.VISIBLE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_like_or_unlike_meme);
				// if
				// (!db.isLikeImage(AppConstant.imageid,AppConstant.imagecategory))
				if (!query.getIsLike().equalsIgnoreCase("1")) {

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

		} else if (v.getId() == R.id.trenHere_ll) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keep_me_here);
			trenAfterright_ll.setVisibility(View.GONE);
			tenSwipe_vp.setCurrentItem(tenSwipe_vp.getCurrentItem() - 1);

		} else if (v.getId() == R.id.trenFirst_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			// if (!SharedPreferencesHelper.getTradingSwipeDialog(con)) {
			// trenAfterright_ll.setVisibility(View.GONE);
			// trenAfterLeft_rl.setVisibility(View.VISIBLE);
			// } else {
			trenAfterright_ll.setVisibility(View.GONE);
			tenSwipe_vp.setCurrentItem(0);
			//
			// }

		} else if (v.getId() == R.id.trenfavri_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_favorite_meme);
			final Intent ii = new Intent(con, FavoritesActivity.class);
			startActivity(ii);
			exitCurrentActivity();
			if (TendingScrctivity.getInstance() != null) {
				TendingScrctivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.trenfirstVp_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			trenAfterLeft_rl.setVisibility(View.GONE);
			tenSwipe_vp.setCurrentItem(0);

		} else if (v.getId() == R.id.trenmessageNotShow_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first_dont_show_again);
			trenAfterLeft_rl.setVisibility(View.GONE);
			SharedPreferencesHelper.setTradingSwipeDialog(con, true);

		} else if (v.getId() == R.id.trenumCancle_ll) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_cancel_all);
			trenAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.tendSwipeFav_im) {
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

						tendSwipeFav_im
								.setImageResource(R.drawable.afterfavetendingstar);

						addtendingfavoritesTv.setText(""
								+ getResources().getString(
										R.string.addfavorites));
						addtendingfavoritesTv.startAnimation(animationFadeIn);
						addtendingfavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

						// toastMessage("Added to Favorites");

					} else {

						query.setFavorite("0");
						db.removeFavoritesImg(query);
						favoriteManager.removeFromFavoriteOnline(query);

						tendSwipeFav_im.setImageResource(R.drawable.tedstar);

						addtendingfavoritesTv.setText(""
								+ getResources().getString(
										R.string.removefavorites));
						addtendingfavoritesTv.startAnimation(animationFadeIn);
						addtendingfavoritesTv.setVisibility(View.VISIBLE);
						removeImageAfter1seconds();

						// toastMessage("Removed from Favorites");

					}
				}

			}

		} else if (v.getId() == R.id.tendingReload) {
			// Reload
			// tenSwipe_vp.setCurrentItem(0);
			if (SharedPreferencesHelper.getTradingSwipeDialog(con)) {

				tenSwipe_vp.setCurrentItem(0);

			} else {
				trenAfterLeft_rl.setVisibility(View.VISIBLE);
			}

			// final String reUrl = AllURL.getReloadinfo(AppConstant.imageid);
			// reload(reUrl);

		} else if (v.getId() == R.id.tendingsaveimage_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_create_bitmap_to_share);

			getbitmapFromServer();

		} else if (v.getId() == R.id.tendingsharefb_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_facebook);

			SharedPreferencesHelper.setFbmeme(con, true);

			tendingsaveimage_im.setClickable(false);
			tendingsharefb_im.setClickable(false);
			tendingsharetwitter_im.setClickable(false);
			tendingsendemail_im.setClickable(false);
			tendingsendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			// Remove : Sharing this from the MemeCabin app! Get it on Android
			// and iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");
			sharecommntsviewtend.setVisibility(View.VISIBLE);
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

		} else if (v.getId() == R.id.tendingsharetwitter_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_twitter);
			// Image share twitter

			SharedPreferencesHelper.setTwittermeme(con, true);

			tendingsaveimage_im.setClickable(false);
			tendingsharefb_im.setClickable(false);
			tendingsharetwitter_im.setClickable(false);
			tendingsendemail_im.setClickable(false);
			tendingsendMMs_im.setClickable(false);

			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
			sharecommntsviewtend.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.tendingsendemail_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_email);
			// Image send email
			getbitmapFromServerForShare("email");

		} else if (v.getId() == R.id.tendingsendMMs_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_mms);
			// Image send MMS/SMS
			getbitmapFromServerForShare("mms");

		} else if (v.getId() == R.id.screenimageframe_rl) {

			if (sharecommntsviewtend.getVisibility() == View.VISIBLE) {

				SharedPreferencesHelper.setFbmeme(con, false);
				SharedPreferencesHelper.setTwittermeme(con, false);

				tendingsaveimage_im.setClickable(true);
				tendingsharefb_im.setClickable(true);
				tendingsharetwitter_im.setClickable(true);
				tendingsendemail_im.setClickable(true);
				tendingsendMMs_im.setClickable(true);

				closeKeyBoard(v);
				sharecommntsviewtend.setVisibility(View.GONE);

			} else if (screenimageframe_rl.getVisibility() == View.VISIBLE
					&& sharecommntsviewtend.getVisibility() == View.GONE) {

				AppConstant.trensview = true;
				screenimageframe_rl.setVisibility(View.GONE);
			}

			/*
			 * AppConstant.trensview = true;
			 * screenimageframe_rl.setVisibility(View.GONE);
			 */

		} else if (v.getId() == R.id.trenAfterright_ll) {

			trenAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.trenAfterLeft_rl) {

			trenAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.rlDecide) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtLogin) {
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_to_login);
			AppConstant.fromscreen = "tendswipe";
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

				tendingsaveimage_im.setClickable(true);
				tendingsharefb_im.setClickable(true);
				tendingsharetwitter_im.setClickable(true);
				tendingsendemail_im.setClickable(true);
				tendingsendMMs_im.setClickable(true);

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

				tendingsaveimage_im.setClickable(true);
				tendingsharefb_im.setClickable(true);
				tendingsharetwitter_im.setClickable(true);
				tendingsendemail_im.setClickable(true);
				tendingsendMMs_im.setClickable(true);

				SharedPreferencesHelper.setTwittermeme(con, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.tradingReportImage) {

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

					// Log.e("Server response : ", ">>>" + new
					// String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");
					todayslike_count = json.getString("daytotalLike");
					seven_likecount = json.getString("7daytotalLike");
					thirty_likecount = json.getString("30daytotalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (query != null) {

							query.setLikeCount(like_count);
							query.setIsLike("1");
							// query.setDayslikecount(todayslike_count);
							// query.setS7daytotalLike(seven_likecount);
							// query.setT30daytotalLike(thirty_likecount);

							final int todayslike = Integer.parseInt(query
									.getDayslikecount()) + 1;
							final int s7dayslike = Integer.parseInt(query
									.getS7daytotalLike()) + 1;
							final int t30dayslike = Integer.parseInt(query
									.getT30daytotalLike()) + 1;

							query.setDayslikecount("" + todayslike);
							query.setS7daytotalLike("" + s7dayslike);
							query.setT30daytotalLike("" + t30dayslike);

							tenLike_im.setImageResource(R.drawable.likeshow);

							// AllTrending.getAllTrending().elementAt(currentIndex).setDayslikecount(query.getDayslikecount());
							AllTrending.getAllTrending()
									.elementAt(currentIndex)
									.setLikeCount(query.getLikeCount());
							// AllTrending.getAllTrending().elementAt(currentIndex).setS7daytotalLike(query.getS7daytotalLike());
							// AllTrending.getAllTrending().elementAt(currentIndex).setT30daytotalLike(query.getT30daytotalLike());

							db.addLike(query);
							trendingtoalalLike.setText(getResources()
									.getString(R.string.totallikes)
									+ "("
									+ query.getLikeCount() + ")");

							todayslikeTextVies.setText(getResources()
									.getString(R.string.like2days)
									+ "("
									+ query.getDayslikecount() + ")");
							s7dayslikeTextVies.setText(getResources()
									.getString(R.string.seve7likes)
									+ "("
									+ query.getS7daytotalLike() + ")");

							t30dayslikeTexVies.setText(getResources()
									.getString(R.string.thirty30likes)
									+ "("
									+ query.getT30daytotalLike() + ")");

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
							final int todayslike = Integer.parseInt(query
									.getDayslikecount()) - 1;
							final int s7dayslike = Integer.parseInt(query
									.getS7daytotalLike()) - 1;
							final int t30dayslike = Integer.parseInt(query
									.getT30daytotalLike()) - 1;

							query.setDayslikecount("" + todayslike);
							query.setS7daytotalLike("" + s7dayslike);
							query.setT30daytotalLike("" + t30dayslike);
							tenLike_im
									.setImageResource(R.drawable.tredinglikeafter);
							db.removeLike(query);

							trendingtoalalLike.setText(getResources()
									.getString(R.string.totallikes)
									+ "("
									+ query.getLikeCount() + ")");
							todayslikeTextVies.setText(getResources()
									.getString(R.string.like2days)
									+ "("
									+ query.getDayslikecount() + ")");
							s7dayslikeTextVies.setText(getResources()
									.getString(R.string.seve7likes)
									+ "("
									+ query.getS7daytotalLike() + ")");
							t30dayslikeTexVies.setText(getResources()
									.getString(R.string.thirty30likes)
									+ "("
									+ query.getT30daytotalLike() + ")");
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

							trendingtoalalLike.setText(getResources()
									.getString(R.string.totallikes)
									+ "("
									+ AppConstant.totalLike + ")");
							t30dayslikeTexVies.setText("30 Days ("
									+ query.getT30daytotalLike() + ")");
							s7dayslikeTextVies.setText("7 Days ("
									+ query.getS7daytotalLike() + ")");
							todayslikeTextVies.setText(getResources()
									.getString(R.string.like2days)
									+ "("
									+ query.getDayslikecount() + ")");

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
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											// toastMessage("Image posted successfully!");
											commentstextforshare.setText("");
											sharecommntsviewtend
													.setVisibility(View.GONE);
										} else {
											if (busyDialog != null) {
												busyDialog.dismis();
											}
											// toastMessage("Facebook post failed!");
											commentstextforshare.setText("");
											sharecommntsviewtend
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
											sharecommntsviewtend
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
							sharecommntsviewtend.setVisibility(View.GONE);
						}
					});
		}

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
					AppConstant.fromscreen = "tendswipe";
					AppConstant.position = currentIndex;
					final Intent ii = new Intent(con, LogActivity.class);
					startActivity(ii);
					// TendingSwipeScrinActivity.this.finish();
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
		// TODO Auto-generated method stub
		super.onBackPressed();
		AllTrending.getAllTrending().remove(
				AllTrending.getAllTrending().size() - 1);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...

			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);

			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.e(TAG, "onActivityResult handled by IABUtil.");
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

		/* Jin Remove Mopub ADS
		if (mInterstitial != null) {
			mInterstitial.destroy();
		}
		if (moPubView != null) {
			moPubView.destroy();
		}
		*/

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

		menuActions.refreshAdbar();

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

	private void initAd() {

		 mInterstitialAd = new InterstitialAd(this);
		 mInterstitialAd.setAdUnitId(getString(R.string.admob_fullscreen_ad_id));
		 final AdRequest adRequest = new AdRequest.Builder().build();
		 mInterstitialAd.loadAd(adRequest);
		 
		 mInterstitialAd.setAdListener(new AdListener() {
			 @Override
			 public void onAdLoaded() {
				 mInterstitialAd.show();
			 }
			 
			 @Override
			 public void onAdFailedToLoad(int errorCode) {
				if (mFlurryAdInterstitial != null) {
					if (mFlurryAdInterstitial.isReady()) {
						mFlurryAdInterstitial.displayAd();
					} else {
						mFlurryAdInterstitial = new FlurryAdInterstitial(
								TendingSwipeScrinActivity.this,
								"2015MemeCabin-Intersitial");
						mFlurryAdInterstitial.fetchAd();
					}
				}
			 }
			 
		 });
		 
		 
//		 adHandler = new Handler();
//		 adLoad.run();
	}
	/* Jin Remove Mopub ADS
	
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
