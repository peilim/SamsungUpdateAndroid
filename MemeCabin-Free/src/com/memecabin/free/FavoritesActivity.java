package com.memecabin.free;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.holder.AllFavoriteMeme;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.MemeParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Picasso;

public class FavoritesActivity extends Activity implements OnClickListener {
	private Context con;
	private RelativeLayout ifImplt_rl, showdialogusingskip;
	private MyLibraryIconicAdapter customerAdapter;
	private GridView gridView1;
	private TextView whatdo_tv, countfavorites_tv;
	private ImageView favHome_im, favReload_im, favShare_im, favFav_im;
	Button fromfavoriteLogin_im;
	private LinearLayout favSlide_ll;
	// DatabaseHandler db;
	// int favsize;
	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;

	int width;

	PullToRefreshGridView favoriteMemeGrid;

	public static FavoritesActivity instance;

	public static FavoritesActivity getInstance() {
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
		setContentView(R.layout.favoritescr);
		con = this;

		instance = this;
		AllFavoriteMeme.removeAllImageInfo();

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
							FavoritesActivity.this, adsBannerView,
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

		ifImplt_rl = (RelativeLayout) findViewById(R.id.ifImplt_rl);

		favSlide_ll = (LinearLayout) findViewById(R.id.favSlide_ll);

		favHome_im = (ImageView) findViewById(R.id.favHome_im);
		favReload_im = (ImageView) findViewById(R.id.favReload_im);
		favShare_im = (ImageView) findViewById(R.id.favShare_im);
		favFav_im = (ImageView) findViewById(R.id.favFav_im);
		fromfavoriteLogin_im = (Button) findViewById(R.id.fromfavoriteLogin_im);
		showdialogusingskip = (RelativeLayout) findViewById(R.id.showdialogusingskip);

		whatdo_tv = (TextView) findViewById(R.id.whatdo_tv);
		countfavorites_tv = (TextView) findViewById(R.id.countfavorites_tv);
		favoriteMemeGrid = (PullToRefreshGridView) findViewById(R.id.favoriteMemeGrid);
		gridView1 = favoriteMemeGrid.getRefreshableView();
		// gridView1 = (GridView) findViewById(R.id.gridView1);

		customerAdapter = new MyLibraryIconicAdapter(con);
		gridView1.setAdapter(customerAdapter);
		customerAdapter.notifyDataSetChanged();

		favSlide_ll.setOnClickListener(this);
		favHome_im.setOnClickListener(this);
		favReload_im.setOnClickListener(this);
		favShare_im.setOnClickListener(this);
		favFav_im.setOnClickListener(this);
		fromfavoriteLogin_im.setOnClickListener(this);

		/*
		 * // DummyData(); final int si = db.getAllFavouriteImage().size();
		 * 
		 * if (si == 0) {
		 * 
		 * ifImplt_rl.setVisibility(View.VISIBLE);
		 * whatdo_tv.setVisibility(View.GONE);
		 * 
		 * }
		 */

		ifImplt_rl.setVisibility(View.VISIBLE);
		whatdo_tv.setVisibility(View.GONE);

		// favoriteMemeGrid.setMode(Mode.BOTH);

		favoriteMemeGrid
				.setOnRefreshListener(new OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// TODO Auto-generated method stub
						getAllFavorite();
						/*
						 * if(favoriteMemeGrid.getMode() ==
						 * Mode.PULL_FROM_START){ } else
						 * if(favoriteMemeGrid.getMode() == Mode.PULL_FROM_END){
						 * 
						 * }
						 */
					}
				});

		// getAllFavorite();

	}

	private void getAllFavorite() {

		// Start busy dialog

		final BusyDialog busyDialog = new BusyDialog(con, true);
		busyDialog.show();

		if (!NetInfo.isOnline(con)) { // Get all local data
			// temp = db.getAllFavouriteImage();
			favoriteMemeGrid.onRefreshComplete();

			if (busyDialog != null) {
				busyDialog.dismis();
			}
		} else { // Get all server data
			final String url = AllURL.listFavorites(SharedPreferencesHelper
					.getUserID(con));
			final AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] response) {
					// TODO Auto-generated method stub
					if (busyDialog != null) {
						busyDialog.dismis();
					}
					// Parse data from server
					try {
						if (MemeParser.parseFavoriteMeme(con, new String(
								response))) {
							// temp = AllFavoriteMeme.getAllImageInfo();
							if (AllFavoriteMeme.getAllImageInfo().size() > 0) {
								ifImplt_rl.setVisibility(View.GONE);
								whatdo_tv.setVisibility(View.VISIBLE);
							}
							// Start sycn between localdb and server
							// syncData();
						} else {
							// temp = db.getAllFavouriteImage();
						}
					} catch (final Exception ex) {
						ex.printStackTrace();
					}

					countfavorites_tv.setText("Your Saved life Favorites("
							+ AllFavoriteMeme.getAllImageInfo().size() + ")");

					customerAdapter = new MyLibraryIconicAdapter(con);
					gridView1.setAdapter(customerAdapter);
					customerAdapter.notifyDataSetChanged();
					favoriteMemeGrid.onRefreshComplete();
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					// temp = db.getAllFavouriteImage();

					customerAdapter = new MyLibraryIconicAdapter(con);
					gridView1.setAdapter(customerAdapter);
					customerAdapter.notifyDataSetChanged();
					favoriteMemeGrid.onRefreshComplete();

					countfavorites_tv.setText("Your Saved life Favorites("
							+ AllFavoriteMeme.getAllImageInfo().size() + ")");

					if (busyDialog != null) {
						busyDialog.dismis();
					}
				}
			});
		}

	}

	/*
	 * int currentPage = 0; private void loadFavPage(){ currentPage++; String
	 * url = AllURL.get }
	 */
	public void exitCurrentActivity() {

		FavoritesActivity.this.finish();

	}

	public void favorithumnailrelative(View v) {

		AppConstant.favflage = false;
		customerAdapter = new MyLibraryIconicAdapter(con);
		gridView1.setAdapter(customerAdapter);
		customerAdapter.notifyDataSetChanged();

	}

	public void toastMessage(String mes) {

		Toast.makeText(con, mes, 3000).show();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.favHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			exitCurrentActivity();

		} else if (v.getId() == R.id.favShare_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_preference);
			final Intent ii = new Intent(con, PreferencesActivity.class);
			startActivity(ii);
			overridePendingTransition(R.anim.slide_in_up, R.anim.noslide);

		} else if (v.getId() == R.id.favSlide_ll) {
			menu.toggle();

		} else if (v.getId() == R.id.fromfavoriteLogin_im) {
			AnalyticsTracker.sendTrackData(this, R.string.analytics_open_login);
			AppConstant.fromscreen = "favorites";

			final Intent ii = new Intent(con, LogActivity.class);
			startActivity(ii);
			// exitCurrentActivity();
		}

	}

	class MyLibraryIconicAdapter extends ArrayAdapter<ImageInfo> {
		Context context;

		// Vector<ImageInfo> temp = new Vector<ImageInfo>();

		MyLibraryIconicAdapter(Context context) {
			super(context, R.layout.favorits_rows, AllFavoriteMeme
					.getAllImageInfo());
			/*
			 * if(temp == null){ temp = db.getAllFavouriteImage(); }
			 */
			// temp.removeAllElements();
			if (AllFavoriteMeme.getAllImageInfo() != null) {
				Log.e("inintial size", ">>"
						+ AllFavoriteMeme.getAllImageInfo().size());
			} else {
				Log.e("initial size", ">> 0");
			}

			this.context = context;
			// temp = AllFavoriteMeme.getAllImageInfo();
			// favsize = db.getAllFavouriteImage().size();
			// favsize = AllFavoriteMeme.getAllImageInfo().size();
			// AppConstant.favsize = favsize;
			// Log.e("temp", ">>>" + temp.size());
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.favorits_rows, null);

			}

			if (position < AllFavoriteMeme.getAllImageInfo().size()) {

				final ImageInfo query = AllFavoriteMeme.getAllImageInfo()
						.elementAt(position);

				final ImageView favImage1_im = (ImageView) v
						.findViewById(R.id.favImage1_im);

				final LayoutParams params = favImage1_im.getLayoutParams();
				// params.height = params.width;
				params.height = gridView1.getWidth() / 4 - 8;
				favImage1_im.setLayoutParams(params);

				final Button delbt = (Button) v.findViewById(R.id.delbtn);
				/*
				 * Picasso.with(con).load(BaseURL.ImagePath +
				 * query.getImageUrl())
				 * .placeholder(R.drawable.place_holder).resize(200, 200)
				 * .centerInside().skipMemoryCache().into(favImage1_im);
				 */

				if (width > 480) {
					Picasso.with(con)
							.load(AppConstant.thumbsMedium
									+ query.getImageUrl()).noFade().fit()
							.centerInside()
							.placeholder(R.drawable.place_holder)
							.into(favImage1_im);
				} else {
					Picasso.with(con)
							.load(AppConstant.thumbSmall + query.getImageUrl())
							.noFade().fit().centerInside()
							.placeholder(R.drawable.place_holder)
							.into(favImage1_im);
				}

				// Log.e("Image Url", "" + query.getImageUrl());
				if (AppConstant.favflage) {
					delbt.setVisibility(View.VISIBLE);
				} else {
					delbt.setVisibility(View.GONE);
				}

				v.setOnLongClickListener(new View.OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						// db.setAllON("1");
						AnalyticsTracker.sendTrackData(FavoritesActivity.this,
								R.string.analytics_open_remove_favorite_option);
						AppConstant.favflage = true;
						// customerAdapter = new MyLibraryIconicAdapter(con);
						// gridView1.setAdapter(customerAdapter);
						// gridView1.invalidate();
						customerAdapter.notifyDataSetChanged();

						return false;
					}
				});
				delbt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AnalyticsTracker.sendTrackData(FavoritesActivity.this,
								R.string.analytics_remove_from_favorite);

						// temp.removeElementAt(AppConstant.position);

						// temp.remove(AppConstant.position);
						removeFromFavoriteOnline(query);

						// delbt.setVisibility(View.GONE);

						// Log.e("how Images", ""+temp.size());

					}
				});

				v.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AnalyticsTracker.sendTrackData(FavoritesActivity.this,
								R.string.analytics_open_favorite_swipe);
						AppConstant.position = position;
						SharedPreferencesHelper.setFevoriteViewpagerPosition(
								con, position);

						AppConstant.image = query.getImageUrl();

						final Intent ii = new Intent(con,
								FavoritesSwipeActivity.class);
						startActivity(ii);

					}
				});

			}
			return v;

		}
	}

	private void removeFromFavoriteOnline(final ImageInfo img) {
		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		// Call api
		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();
		final String url = AllURL.unFavorites(AppConstant.userID,
				img.getImageID());
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// TODO Auto-generated method stub
				if (busyNow != null) {
					busyNow.dismis();
				}

				try {
					final JSONObject json = new JSONObject(new String(response));
					final String status = json.optString("status");
					final String msg = json.optString("message");
					if (status.equals("1")) {
						System.out.println(msg + ": Meme unfavorite success");
						Log.e("AllFavoriteMeme.getAllImageInfo() sizd", "::"
								+ AllFavoriteMeme.getAllImageInfo().size());
						AllFavoriteMeme.removeFromID(img);

						Log.e("after AllFavoriteMeme.getAllImageInfo() sizd",
								"::" + AllFavoriteMeme.getAllImageInfo().size());

						customerAdapter = new MyLibraryIconicAdapter(con);
						gridView1.setAdapter(customerAdapter);
						customerAdapter.notifyDataSetChanged();
						favoriteMemeGrid.onRefreshComplete();

					} else {
						Log.e("value ", msg + ": unfavorite failed.");
					}
				} catch (final Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				if (busyNow != null) {
					busyNow.dismis();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Log.e("is on resume of fav", "yes");

		if (SharedPreferencesHelper.getUsingskip(con)) {
			whatdo_tv.setVisibility(View.GONE);
			showdialogusingskip.setVisibility(View.VISIBLE);

		} else {

			whatdo_tv.setVisibility(View.GONE);
			showdialogusingskip.setVisibility(View.GONE);

			getAllFavorite();
		}

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

	/*
	 * End of In app billing ........
	 * 
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */

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
