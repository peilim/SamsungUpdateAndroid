package com.memecabin.free;

import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.GlobalUtils;
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
import com.flurry.android.ads.FlurryAdBanner;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.AllTrending;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.TrendingParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Picasso;

public class TendingScrctivity extends Activity implements OnClickListener {
	private Context con;
	private LinearLayout tenScrback_ll, tenScrSlide_ll, tenScrTop7_ll,
			tenScrTop30_ll, tenScrTop90_ll, tenScrToptodays_ll;
	private ImageView tenScrHome_im, tenScrPref_im, tenScrLike_im;

	private LinearLayout last2days_ll, topOption_ll;
	private ListView List;
	private TextView tendingTitle;

	private MyLibraryIconicAdapter customerAdapter;
	private String likeTextStr;
	ImageInfo globalQuery;
	DatabaseHandler db;
	private String like_count;
	private String todayslike, s7dayslike, t30dayslike, n90dayslike;
	private int globalPos = 0;
	// ImageInfo query;

	// ////=========== for Sliding=====================
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	private DisplayMetrics metrics;
	LinearLayout menuPanel, slidingPanel;
	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;
	private PullToRefreshListView mPullRefreshListView;
	int old_pos, loadNumber;
	public boolean isLoad = false;
	private static int previoiscount = 0;

	FrameLayout addlayouttrending;
	String adSpaceName;

	RelativeLayout rlDecide;
	TextView txtLogin;
	TextView txtNoThanks;
	int width;

	public static TendingScrctivity instance;

	public static TendingScrctivity getInstance() {
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
	private SlidingMenuActions menuActions;
	/* Jin Remove Mopub ADS
	MoPubView moPubView;
	*/
	private ViewGroup adsBannerView;
	private AdView	  googleAdsBannerView;
	private FlurryAdBanner mFlurryAdBanner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.trendingscr);
		con = this;
		db = new DatabaseHandler(con);
		instance = this;
		initUI();

		// Add New ADS
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// add call
		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/*
			 * Jin Remove Mopub ADS addShow();
			 */
			int width = dm.widthPixels;
			adsBannerView = (ViewGroup) findViewById(R.id.adView);
			googleAdsBannerView = (AdView) findViewById(R.id.google_adView);
			googleAdsBannerView.setAdListener(new AdListener() {
				@Override
				public void onAdFailedToLoad(int errorCode) {
					mFlurryAdBanner = new FlurryAdBanner(
							TendingScrctivity.this, adsBannerView,
							"2015MemeCabin-Banner");
					// optional allow us to get callbacks for ad events,
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

		final Display display = getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		width = size.x;
		Log.e("Screen width Device:", ">>" + width);

	}

	private void initUI() {

		// AppConstant.twitterFlag = false;

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

		metrics = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// panelWidth = (int) (metrics.widthPixels);

		tenScrHome_im = (ImageView) findViewById(R.id.tenScrHome_im);
		tenScrPref_im = (ImageView) findViewById(R.id.tenScrPref_im);
		tenScrLike_im = (ImageView) findViewById(R.id.tenScrLike_im);

		tendingTitle = (TextView) findViewById(R.id.tendingTitle);

		tenScrback_ll = (LinearLayout) findViewById(R.id.tenScrback_ll);
		tenScrSlide_ll = (LinearLayout) findViewById(R.id.tenScrSlide_ll);
		tenScrTop7_ll = (LinearLayout) findViewById(R.id.tenScrTop7_ll);
		tenScrTop30_ll = (LinearLayout) findViewById(R.id.tenScrTop30_ll);
		tenScrTop90_ll = (LinearLayout) findViewById(R.id.tenScrTop90_ll);
		tenScrToptodays_ll = (LinearLayout) findViewById(R.id.tenScrToptodays_ll);

		rlDecide = (RelativeLayout) findViewById(R.id.rlDecide);
		txtLogin = (TextView) findViewById(R.id.txtLogin);
		txtNoThanks = (TextView) findViewById(R.id.txtNoThanks);

		// topMemeList2days_li = (ListView)
		// findViewById(R.id.topMemeList2days_li);
		// topMemeList7days_li = (ListView)
		// findViewById(R.id.topMemeList7days_li);
		// topMemeList30days_li = (ListView)
		// findViewById(R.id.topMemeList30days_li);
		// topMemeList90days_li = (ListView)
		// findViewById(R.id.topMemeList90days_li);

		// =======view======

		last2days_ll = (LinearLayout) findViewById(R.id.last2days_ll);
		// last7days_ll = (LinearLayout) findViewById(R.id.last7days_ll);
		// last30days_ll = (LinearLayout) findViewById(R.id.last30days_ll);
		// last90days_ll = (LinearLayout) findViewById(R.id.last90days_ll);

		topOption_ll = (LinearLayout) findViewById(R.id.topOption_ll);

		// =====view end=====

		tenScrHome_im.setOnClickListener(this);
		tenScrPref_im.setOnClickListener(this);
		tenScrLike_im.setOnClickListener(this);
		tenScrback_ll.setOnClickListener(this);
		tenScrSlide_ll.setOnClickListener(this);

		tenScrTop7_ll.setOnClickListener(this);
		tenScrTop30_ll.setOnClickListener(this);
		tenScrTop90_ll.setOnClickListener(this);
		tenScrToptodays_ll.setOnClickListener(this);

		rlDecide.setOnClickListener(this);
		txtLogin.setOnClickListener(this);
		txtNoThanks.setOnClickListener(this);

		isLoad = true;
		loadNumber = 1;

		TendingScrctivity.previoiscount = -1;

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.topMemeList2days_li);

		old_pos = mPullRefreshListView.getRefreshableView()
				.getFirstVisiblePosition() + 1;

		// mPullRefreshListView.getLoadingLayoutProxy().setTextColor(R.color.white);

		final String label = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

		// String abs=R.color.

		// mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {

						final String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// String abs=R.color.
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						try {
							if (isLoad) {
								loadNumber++;

								if (AppConstant.loadTreding
										.equalsIgnoreCase("1")) {
									loadTreding("1");

								} else if (AppConstant.loadTreding
										.equalsIgnoreCase("7")) {
									loadTreding("7");

								} else if (AppConstant.loadTreding
										.equalsIgnoreCase("30")) {
									loadTreding("30");

								} else if (AppConstant.loadTreding
										.equalsIgnoreCase("90")) {
									loadTreding("90");
								}

								//
								final int size = AllTrending.getAllTrending()
										.size();
								if (previoiscount < size) {
									loadNumber++;
									if (AppConstant.loadTreding
											.equalsIgnoreCase("1")) {

										loadTreding("1");

									} else if (AppConstant.loadTreding
											.equalsIgnoreCase("7")) {

										loadTreding("7");

									} else if (AppConstant.loadTreding
											.equalsIgnoreCase("30")) {

										loadTreding("30");

									} else if (AppConstant.loadTreding
											.equalsIgnoreCase("90")) {

										loadTreding("90");
									}
								}

								Log.d("previoiscount are", "" + previoiscount);
								Log.d("size are previoiscount", ""
										+ AllTrending.getAllTrending().size());

							} else {
								mPullRefreshListView.onRefreshComplete();
								mPullRefreshListView.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (isLoad != false) {
											// mPullRefreshListView
											// .getRefreshableView()
											// .setSelection(
											// (loadNumber - 1)
											// * AppConstant.maxLoad);
										}

									}
								});

							}

						} catch (final Exception e) {
							mPullRefreshListView.onRefreshComplete();

							mPullRefreshListView.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (isLoad != false) {
										// mPullRefreshListView
										// .getRefreshableView()
										// .setSelection(
										// (loadNumber - 1)
										// * AppConstant.maxLoad);
										//
									}
								}
							});
						}

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {

						mPullRefreshListView.onRefreshComplete();

						mPullRefreshListView.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (isLoad != false) {
									// mPullRefreshListView
									// .getRefreshableView()
									// .setSelection(
									// (loadNumber - 1)
									// * AppConstant.maxLoad);
								}
							}
						});

					}
				});

		List = mPullRefreshListView.getRefreshableView();

	}

	public void exitCurrentActivity() {

		TendingScrctivity.this.finish();

	}

	public void toastMessage(String mes) {

		// Toast.makeText(con, mes, 3000).show();

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tenScrHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			TendingScrctivity.this.finish();

		} else if (v.getId() == R.id.tenScrPref_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_preference);
			final Intent ii = new Intent(con, PreferencesActivity.class);
			startActivity(ii);
			overridePendingTransition(R.anim.slide_in_up, R.anim.noslide);

		} else if (v.getId() == R.id.tenScrLike_im) {
			//
			// final Intent ii = new Intent(con, FavoritesActivity.class);
			// startActivity(ii);

		} else if (v.getId() == R.id.tenScrback_ll) {
			if (AppConstant.firstimeflage > 1) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_to_tranding_list);
				AppConstant.firstimeflage--;
				topOption_ll.setVisibility(View.VISIBLE);
				last2days_ll.setVisibility(View.GONE);

			} else {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_home);
				exitCurrentActivity();

			}

		} else if (v.getId() == R.id.tenScrSlide_ll) {

			menu.toggle();

		} else if (v.getId() == R.id.tenScrTop7_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_seven_days_tranding_list);
			AppConstant.loadTreding = "7";

			AppConstant.firstimeflage++;

			tendingTitle.setText(""
					+ getResources().getString(R.string.toplast7));
			likeTextStr = "7 Days";
			AppConstant.treadingMemesName = "Top Memes: Last 7 Days";

			customerAdapter = new MyLibraryIconicAdapter(con);
			List.setAdapter(customerAdapter);
			customerAdapter.notifyDataSetChanged();

			loadTreding("7");

			topOption_ll.setVisibility(View.GONE);
			last2days_ll.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.tenScrTop30_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_thirty_days_tranding_list);
			AppConstant.loadTreding = "30";
			AppConstant.firstimeflage++;
			tendingTitle.setText(""
					+ getResources().getString(R.string.toplast30));
			likeTextStr = "30 Days";
			AppConstant.treadingMemesName = "Top Memes: Last 30 Days";

			loadTreding("30");

			topOption_ll.setVisibility(View.GONE);
			last2days_ll.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.tenScrTop90_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_ninty_days_tranding_list);
			AppConstant.loadTreding = "90";
			AppConstant.firstimeflage++;
			tendingTitle.setText(""
					+ getResources().getString(R.string.toplast90));
			AppConstant.treadingMemesName = "Top Memes: Last 90 Days";

			loadTreding("90");
			likeTextStr = "90 Days";

			topOption_ll.setVisibility(View.GONE);
			last2days_ll.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.tenScrToptodays_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_todays_tranding_list);
			AppConstant.loadTreding = "1";
			AppConstant.firstimeflage++;
			tendingTitle.setText(""
					+ getResources().getString(R.string.todaysMemes));
			AppConstant.treadingMemesName = "Today's Top Memes";

			loadTreding("1");

			likeTextStr = "Today's Likes";

			topOption_ll.setVisibility(View.GONE);
			last2days_ll.setVisibility(View.VISIBLE);

		} else if (v.getId() == R.id.rlDecide) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtLogin) {
			AnalyticsTracker
					.sendTrackData(this, R.string.analytics_go_to_login);
			AppConstant.fromscreen = "tendswipe";
			// AppConstant.position = currentIndex;

			final Intent loginIntent = new Intent(con, LogActivity.class);
			startActivity(loginIntent);

			rlDecide.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txtNoThanks) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_login_decide);
			rlDecide.setVisibility(View.GONE);
		}

	}

	String respones_Treding = "";

	protected void loadTreding(String value) {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, "Internet Connection Problem",
					"Please check the Internet Connection");
			return;
		}
		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final String url = AllURL.getTendingMemeUrl(AppConstant.userID, value);

		Log.e("Trending URL is ", url);

		final AsyncHttpClient client = new AsyncHttpClient();
		client.put(url, new AsyncHttpResponseHandler() {

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

				if (mPullRefreshListView != null) {
					mPullRefreshListView.onRefreshComplete();

				}

				Log.e("response", "" + new String(response));

				try {
					if (TrendingParser.connect(con, new String(response))) {
						Log.e("Parse Complete", ":");

					}
					Log.e("SiZe", "" + AllTrending.getAllTrending().size());
				} catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * 
				 * check if RACY is password protected
				 */

				if (AllTrending.getAllTrending().size() != 0) {

					if (SharedPreferencesHelper.getLockflage(con)
							.equalsIgnoreCase("on")) {
						/*
						 * discard Racy MEME's
						 */

						AllTrending.getAllTrendingExceptRacy();

					}
				}

				if (AppConstant.status.equalsIgnoreCase("1")) {

					customerAdapter = new MyLibraryIconicAdapter(con);
					List.setAdapter(customerAdapter);
					customerAdapter.notifyDataSetChanged();
				} else {

					toastMessage("Memes not found....");
					return;
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
				busyNow.dismis();

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// if (AppConstant.trendingRefresh) {
		customerAdapter = new MyLibraryIconicAdapter(con);
		List.setAdapter(customerAdapter);
		customerAdapter.notifyDataSetChanged();

	}

	class MyLibraryIconicAdapter extends ArrayAdapter<ImageInfo> {
		Context context;

		MyLibraryIconicAdapter(Context context) {
			super(context, R.layout.trendtop_rows, AllTrending.getAllTrending());

			this.context = context;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.trendtop_rows, null);

			}

			if (position < AllTrending.getAllTrending().size()) {

				final ImageInfo query = AllTrending.getAllTrending().elementAt(
						position);

				final ImageView tedingImageSwip_im = (ImageView) v
						.findViewById(R.id.tedingImageSwip_im);
				final TextView todaysLike_tv = (TextView) v
						.findViewById(R.id.todaysLike_tv);

				final TextView slcounttv = (TextView) v
						.findViewById(R.id.slcounttv);

				slcounttv.setText("#" + (position + 1));

				final TextView totalLike_tv = (TextView) v
						.findViewById(R.id.totalLike_tv);
				final ImageView liketeding_im = (ImageView) v
						.findViewById(R.id.liketeding_im);

				totalLike_tv.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");

				if (likeTextStr.equalsIgnoreCase("7 Days")) {
					todaysLike_tv.setText(getResources().getString(
							R.string.seve7likes)
							+ "(" + query.getS7daytotalLike() + ")");
				} else if (likeTextStr.equalsIgnoreCase("30 Days")) {
					todaysLike_tv.setText(getResources().getString(
							R.string.thirty30likes)
							+ "(" + query.getT30daytotalLike() + ")");
				} else if (likeTextStr.equalsIgnoreCase("90 Days")) {
					todaysLike_tv.setText(getResources().getString(
							R.string.ninity90likes)
							+ "(" + query.getN90daytotalLike() + ")");
				} else {
					todaysLike_tv.setText(getResources().getString(
							R.string.todays2likes)
							+ "(" + query.getDayslikecount() + ")");
				}

				// if (db.isLikeImage(query.getImageID(),
				// query.getImageCategory()))
				if (query.getIsLike().equalsIgnoreCase("1")) {

					liketeding_im.setImageResource(R.drawable.likeshow);
				} else {
					liketeding_im.setImageResource(R.drawable.tredinglikeafter);
				}

				// liketeding_im.setBackgroundResource(R.drawable.likeshow);

				if (width > 480) {
					Picasso.with(con)
							.load(AppConstant.thumbsMedium
									+ query.getImageUrl()).noFade()
							.placeholder(R.drawable.place_holder)
							.into(tedingImageSwip_im);
				} else {
					Picasso.with(con)
							.load(AppConstant.thumbSmall + query.getImageUrl())
							.noFade().placeholder(R.drawable.place_holder)
							.into(tedingImageSwip_im);
				}

				// Log.e("Picaso url : ",
				// ":" + AppConstant.imageBaseUrl + query.getImageUrl());

				liketeding_im.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AnalyticsTracker.sendTrackData(TendingScrctivity.this,
								R.string.analytics_open_login_decide);
						globalPos = position;

						globalQuery = AllTrending.getAllTrending().elementAt(
								globalPos);

						// if
						// (!db.isLikeImage(globalQuery.getImageID(),globalQuery.getImageCategory()))
						if (!globalQuery.getIsLike().equalsIgnoreCase("1"))

						{

							final String urllike = AllURL.getLikeUpdate(
									globalQuery.getImageID(),
									SharedPreferencesHelper.getUserID(con));

							if (SharedPreferencesHelper.getUsingskip(con)) {
								// toastMessage("You are not authority like this memes");
								rlDecide.setVisibility(View.VISIBLE);
							} else {
								updatLikeMeme(urllike, likeTextStr,
										todaysLike_tv, totalLike_tv,
										liketeding_im);
							}

						} else {

							final String urlunlike = AllURL.getDisLike(
									globalQuery.getImageID(),
									SharedPreferencesHelper.getUserID(con));
							if (SharedPreferencesHelper.getUsingskip(con)) {

								toastMessage("You are not authority dislike this memes");

							} else {
								disLikeMeme(urlunlike, likeTextStr,
										todaysLike_tv, totalLike_tv,
										liketeding_im);
							}

						}

					}
				});

				v.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AnalyticsTracker
								.sendTrackData(
										TendingScrctivity.this,
										R.string.analytics_open_tranding_meme_swip_activity);
						final ImageInfo query = AllTrending.getAllTrending()
								.elementAt(position);
						AppConstant.position = position;
						AppConstant.image = query.getImageUrl();
						SharedPreferencesHelper.setTrendViewpagerPosition(con,
								AppConstant.position);

						final Intent ii = new Intent(con,
								TendingSwipeScrinActivity.class);
						startActivity(ii);

					}
				});

			}
			return v;

		}
	}

	protected void updatLikeMeme(final String url, final String daystype,
			final TextView todaylike, final TextView all,
			final ImageView likebtn) {

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

					Log.e("dislike ", "; " + new String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");
					todayslike = json.getString("daytotalLike");
					s7dayslike = json.getString("7daytotalLike");
					t30dayslike = json.getString("30daytotalLike");
					n90dayslike = json.getString("90daytotalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (globalQuery != null) {

							globalQuery.setLikeCount(like_count);
							// globalQuery.setDayslikecount(todayslike);
							// globalQuery.setS7daytotalLike(s7dayslike);
							// globalQuery.setT30daytotalLike(t30dayslike);
							// globalQuery.setN90daytotalLike(n90dayslike);

							globalQuery.setIsLike("1");

							if (daystype.equalsIgnoreCase("7 Days")) {
								final int s7dayslike = Integer
										.parseInt(globalQuery
												.getS7daytotalLike()) + 1;
								globalQuery.setS7daytotalLike("" + s7dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setS7daytotalLike("" + s7dayslike);

							} else if (daystype.equalsIgnoreCase("30 Days")) {
								final int t30dayslike = Integer
										.parseInt(globalQuery
												.getT30daytotalLike()) + 1;
								globalQuery
										.setT30daytotalLike("" + t30dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setT30daytotalLike("" + t30dayslike);

							} else if (daystype.equalsIgnoreCase("90 Days")) {
								final int n90dayslike = Integer
										.parseInt(globalQuery
												.getN90daytotalLike()) + 1;
								globalQuery
										.setN90daytotalLike("" + n90dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setN90daytotalLike("" + n90dayslike);
							} else {
								final int todayslike = Integer
										.parseInt(globalQuery
												.getDayslikecount()) + 1;
								globalQuery.setDayslikecount("" + todayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setDayslikecount("" + todayslike);
							}

							db.addLike(globalQuery);

							all.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + globalQuery.getLikeCount() + ")");

							if (likeTextStr.equalsIgnoreCase("7 Days")) {
								todaylike.setText(getResources().getString(
										R.string.seve7likes)
										+ "("
										+ globalQuery.getS7daytotalLike()
										+ ")");
							} else if (likeTextStr.equalsIgnoreCase("30 Days")) {
								todaylike.setText(getResources().getString(
										R.string.thirty30likes)
										+ "("
										+ globalQuery.getT30daytotalLike()
										+ ")");
							} else if (likeTextStr.equalsIgnoreCase("90 Days")) {
								todaylike.setText(getResources().getString(
										R.string.ninity90likes)
										+ "("
										+ globalQuery.getN90daytotalLike()
										+ ")");
							} else {
								todaylike.setText(getResources().getString(
										R.string.todays2likes)
										+ "("
										+ globalQuery.getDayslikecount()
										+ ")");
							}

							likebtn.setImageResource(R.drawable.likeshow);

							// List.setAdapter(customerAdapter);
							// customerAdapter.notifyDataSetChanged();

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

	protected void disLikeMeme(final String url, final String daystype,
			final TextView todaylike, final TextView all,
			final ImageView likebtn) {

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

					Log.e("dislike ", "; " + new String(response));

					AppConstant.status = json.optString("status");
					like_count = json.getString("totalLike");
					todayslike = json.getString("daytotalLike");
					s7dayslike = json.getString("7daytotalLike");
					t30dayslike = json.getString("30daytotalLike");
					n90dayslike = json.getString("90daytotalLike");

					if (AppConstant.status.equalsIgnoreCase("1")) {
						if (globalQuery != null) {

							globalQuery.setLikeCount(like_count);
							// globalQuery.setDayslikecount(todayslike);
							// globalQuery.setS7daytotalLike(s7dayslike);
							// globalQuery.setT30daytotalLike(t30dayslike);
							// globalQuery.setN90daytotalLike(n90dayslike);
							globalQuery.setIsLike("0");

							if (daystype.equalsIgnoreCase("7 Days")) {
								final int s7dayslike = Integer
										.parseInt(globalQuery
												.getS7daytotalLike()) - 1;
								globalQuery.setS7daytotalLike("" + s7dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setS7daytotalLike("" + s7dayslike);

							} else if (daystype.equalsIgnoreCase("30 Days")) {
								final int t30dayslike = Integer
										.parseInt(globalQuery
												.getT30daytotalLike()) - 1;
								globalQuery
										.setT30daytotalLike("" + t30dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setT30daytotalLike("" + t30dayslike);

							} else if (daystype.equalsIgnoreCase("90 Days")) {
								final int n90dayslike = Integer
										.parseInt(globalQuery
												.getN90daytotalLike()) - 1;
								globalQuery
										.setN90daytotalLike("" + n90dayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setN90daytotalLike("" + n90dayslike);
							} else {
								final int todayslike = Integer
										.parseInt(globalQuery
												.getDayslikecount()) - 1;
								globalQuery.setDayslikecount("" + todayslike);
								AllTrending.getAllTrending()
										.elementAt(globalPos)
										.setDayslikecount("" + todayslike);
							}

							db.removeLike(globalQuery);
							// List.setAdapter(customerAdapter);
							// customerAdapter.notifyDataSetChanged();

							all.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + globalQuery.getLikeCount() + ")");

							if (likeTextStr.equalsIgnoreCase("7 Days")) {
								todaylike.setText(getResources().getString(
										R.string.seve7likes)
										+ "("
										+ globalQuery.getS7daytotalLike()
										+ ")");
							} else if (likeTextStr.equalsIgnoreCase("30 Days")) {
								todaylike.setText(getResources().getString(
										R.string.thirty30likes)
										+ "("
										+ globalQuery.getT30daytotalLike()
										+ ")");
							} else if (likeTextStr.equalsIgnoreCase("90 Days")) {
								todaylike.setText(getResources().getString(
										R.string.ninity90likes)
										+ "("
										+ globalQuery.getN90daytotalLike()
										+ ")");
							} else {
								todaylike.setText(getResources().getString(
										R.string.todays2likes)
										+ "("
										+ globalQuery.getDayslikecount()
										+ ")");
							}

							likebtn.setImageResource(R.drawable.tredinglikeafter);

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
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.e(TAG, "onActivityResult handled by IABUtil.");
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

	/* Jin Remove Mopub ADS
	private void addShow() {

		moPubView = (MoPubView) findViewById(R.id.adview);
		moPubView.setAdUnitId(AppConstant.mopubAdsUnitId);
		moPubView.loadAd();
		moPubView.setBannerAdListener(new BannerAdListener() {

			@Override
			public void onBannerLoaded(MoPubView arg0) {
				// TODO Auto-generated method stub

				moPubView.setVisibility(View.VISIBLE);

				Log.e("ads", "onBannerLoaded");

			}

			@Override
			public void onBannerFailed(MoPubView arg0, MoPubErrorCode arg1) {
				// TODO Auto-generated method stub
				Log.e("ads", "onBannerFailed");

			}

			@Override
			public void onBannerExpanded(MoPubView arg0) {
				// TODO Auto-generated method stub
				Log.e("ads", "onBannerExpanded");

			}

			@Override
			public void onBannerCollapsed(MoPubView arg0) {
				// TODO Auto-generated method stub
				Log.e("ads", "onBannerCollapsed");

			}

			@Override
			public void onBannerClicked(MoPubView arg0) {
				// TODO Auto-generated method stub
				Log.e("ads", "onBannerClicked");

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
