package com.memecabin.pro;

import java.io.IOException;
import java.util.Vector;

import org.apache.http.Header;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AnalyticsTracker;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.aapbd.iap1.util.IabHelper;
import com.aapbd.iap1.util.IabResult;
import com.aapbd.iap1.util.Inventory;
import com.aapbd.iap1.util.Purchase;
import com.aapbd.utils.display.DisplayUtils;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.holder.AllRacy;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.RacyParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Picasso;
import com.memecabin.pro.R;

public class RacyAllUnreadActivity extends Activity implements OnClickListener {
	private Context con;
	private LinearLayout racyAllBack_ll, racyAllMemo_ll, racyAllRead_ll;
	RelativeLayout racyAllImageDialog_ll;
	private GridView racyAllUnread_gv;
	private ImageView racyallUrHome_im, racyAllPreference_im,
			racyAllFavorite_im;
	private TextView racyAllunread_tv;
	private MyLibraryIconicAdapter customerAdapter;
	private LinearLayout racyAllDialoggon_ll, racyAllDoit_ll,
			racyAllDontAgain_ll, racyAllTakeMe_ll;
	private RelativeLayout racyGrideViewChage_rl, racyparentrelative;
	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;
	DatabaseHandler db;

	int width;

	PullToRefreshGridView racyAllMemeGrid;

	public static RacyAllUnreadActivity instance;

	public static RacyAllUnreadActivity getInstance() {
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

	private int currentPage = 1;
	/* Jin Remove Mopub ADS
	MoPubView moPubView;
	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.racyallunread);
		con = this;
		db = new DatabaseHandler(con);
		instance = this;
		AllRacy.removeAllRacy();

		initUI();
		// add call
		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
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

		racyAllBack_ll = (LinearLayout) findViewById(R.id.racyAllBack_ll);
		racyAllMemo_ll = (LinearLayout) findViewById(R.id.racyAllMemo_ll);
		racyAllRead_ll = (LinearLayout) findViewById(R.id.racyAllRead_ll);

		racyGrideViewChage_rl = (RelativeLayout) findViewById(R.id.racyGrideViewChage_rl);

		racyAllImageDialog_ll = (RelativeLayout) findViewById(R.id.racyAllImageDialog_ll);

		racyAllDialoggon_ll = (LinearLayout) findViewById(R.id.racyAllDialoggon_ll);
		racyAllDoit_ll = (LinearLayout) findViewById(R.id.racyAllDoit_ll);
		racyAllDontAgain_ll = (LinearLayout) findViewById(R.id.racyAllDontAgain_ll);
		racyAllTakeMe_ll = (LinearLayout) findViewById(R.id.racyAllTakeMe_ll);

		racyAllMemeGrid = (PullToRefreshGridView) findViewById(R.id.racyAllMemeGrid);
		racyAllUnread_gv = racyAllMemeGrid.getRefreshableView();
		// racyAllUnread_gv = (GridView) findViewById(R.id.racyAllUnread_gv);
		racyAllunread_tv = (TextView) findViewById(R.id.racyAllunread_tv);

		racyallUrHome_im = (ImageView) findViewById(R.id.racyallUrHome_im);
		racyAllPreference_im = (ImageView) findViewById(R.id.racyAllPreference_im);
		racyAllFavorite_im = (ImageView) findViewById(R.id.racyAllFavorite_im);
		racyparentrelative = (RelativeLayout) findViewById(R.id.racyparentrelative);

		racyAllBack_ll.setOnClickListener(this);
		racyAllMemo_ll.setOnClickListener(this);
		racyAllRead_ll.setOnClickListener(this);

		racyAllDialoggon_ll.setOnClickListener(this);
		racyAllDoit_ll.setOnClickListener(this);
		racyAllDontAgain_ll.setOnClickListener(this);
		racyAllTakeMe_ll.setOnClickListener(this);

		racyallUrHome_im.setOnClickListener(this);
		racyAllPreference_im.setOnClickListener(this);
		racyAllFavorite_im.setOnClickListener(this);
		racyparentrelative.setOnClickListener(this);
		racyAllImageDialog_ll.setOnClickListener(this);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset12);
		menu.setFadeDegree(0.1f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.slidememeaction);
		menuActions = new SlidingMenuActions(con, RacyAllUnreadActivity.this,
				menu, appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
				silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
				fullscreenslide, getnewapps, singledadRelativelayout,
				rateappRelativelayout, fivestarlinear, rmaindmelinear,
				noidontwannalinear, rateheadinglinear, disableaddheadinglinear,
				disablefor99, disableforever99, nahichangemind,
				singledateheadinglinear, yahtakeme2sateit, remindmenext,
				bahidontcare);

		racyAllunread_tv.setText(Html.fromHtml(getString(R.string.markall)));

		final String label = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

		// String abs=R.color.

		// mPullRefreshListView.setMode(Mode.BOTH);
		racyAllMemeGrid.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		racyAllMemeGrid.setMode(Mode.BOTH);

		racyAllMemeGrid.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				// TODO Auto-generated method stub

				final String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// String abs=R.color.

				// mPullRefreshListView.setMode(Mode.BOTH);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				if (racyAllMemeGrid.getCurrentMode() == Mode.PULL_FROM_START) {
					currentPage = 1;
					loadRacyList();
				} else if (racyAllMemeGrid.getCurrentMode() == Mode.PULL_FROM_END) {
					currentPage++;
					loadRacyList();
				}
			}
		});

		currentPage = 1;
		loadRacyList();

		racyAllUnread_gv.setHorizontalSpacing(DisplayUtils.dpToPx(
				getResources(), 4));
		racyAllUnread_gv.setVerticalSpacing(DisplayUtils.dpToPx(getResources(),
				4));
		racyAllUnread_gv.setBackgroundColor(getResources().getColor(
				R.color.gray_border));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// Data for pagination
		final int totalCount = AllRacy.getAllRacy().size();
		final float cnt = (float) totalCount
				/ AppConstant.PAGINATION_ITEM_COUNT;
		currentPage = (int) Math.ceil(cnt);

		if (currentPage == 0) {
			currentPage = 1;

		}

		if (AppConstant.readFlagRacy) {
			customerAdapter = new MyLibraryIconicAdapter(con);
			racyAllUnread_gv.setAdapter(customerAdapter);
			customerAdapter.notifyDataSetChanged();

			AppConstant.readFlagRacy = false;

			setLastEntrySelection();
		}

		if (AllRacy.getAllRacy().size() > 0) {
			customerAdapter = new MyLibraryIconicAdapter(con);
			racyAllUnread_gv.setAdapter(customerAdapter);
			customerAdapter.notifyDataSetChanged();

			setLastEntrySelection();
		}

	}

	public void exitCurrentActivity() {

		RacyAllUnreadActivity.this.finish();

	}

	public void toastMessage(String mes) {

		Toast.makeText(con, mes, 3000).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.racyAllBack_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			exitCurrentActivity();

		} else if (v.getId() == R.id.racyAllMemo_ll) {

			// final Intent ii = new Intent(con, SlideActivity.class);
			// startActivity(ii);

			menu.toggle();

		} else if (v.getId() == R.id.appRate_rl) {
			menu.toggle();
		} else if (v.getId() == R.id.disableAdd_rl) {
			menu.toggle();
		} else if (v.getId() == R.id.getSdl_rl) {
			menu.toggle();
		} else if (v.getId() == R.id.singleFB_rl) {
			menu.toggle();
		} else if (v.getId() == R.id.silgeInstagram_rl) {
			menu.toggle();
		} else if (v.getId() == R.id.racyAllRead_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_all_mark_option);
			if (!AppConstant.flageAgainRacy) {
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_close_mark_all_option);
				racyAllImageDialog_ll.setVisibility(View.VISIBLE);
			}

		} else if (v.getId() == R.id.racyallUrHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			exitCurrentActivity();

		} else if (v.getId() == R.id.racyAllPreference_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_preference);
			final Intent ii = new Intent(con, PreferencesActivity.class);
			startActivity(ii);
			overridePendingTransition(R.anim.slide_in_up, R.anim.noslide);

		} else if (v.getId() == R.id.racyAllFavorite_im) {

			// if (!AppConstant.favflage) {
			//
			// final Intent ii = new Intent(con, FavoritesActivity.class);
			// startActivity(ii);
			// racyAllFavorite_im.setImageResource(R.drawable.memestarlike);
			// AppConstant.favflage = false;
			// } else {
			// racyAllFavorite_im.setImageResource(R.drawable.favcon);
			// AppConstant.favflage = true;
			// }

		} else if (v.getId() == R.id.racyAllDialoggon_ll) {

		} else if (v.getId() == R.id.racyAllDoit_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_mark_all_as_read);
			// racyAllImageDialog_ll.setVisibility(View.GONE);
			// racyGrideViewChage_rl.setBackgroundResource(R.drawable.bglistview);

			final BusyDialog busyDialog = new BusyDialog(con, true);
			busyDialog.show();

			final Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					for (final ImageInfo imgracy : AllRacy.getAllRacy()) {
						// ImageInfo
						// imgmoti:AllMotivational.getAllMotivational()
						db.addRead(imgracy);
						imgracy.setIsRead("1");
					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if (busyDialog != null) {
								busyDialog.dismis();
							}

							customerAdapter = new MyLibraryIconicAdapter(con);
							racyAllUnread_gv.setAdapter(customerAdapter);
							customerAdapter.notifyDataSetChanged();

							racyAllImageDialog_ll.setVisibility(View.GONE);

							setLastEntrySelection();

						}
					});

				}

			});

			t.start();

		} else if (v.getId() == R.id.racyAllDontAgain_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_mark_all_option);
			racyAllImageDialog_ll.setVisibility(View.GONE);
			AppConstant.flageAgainRacy = true;

		} else if (v.getId() == R.id.racyAllTakeMe_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_mark_all_option);
			racyAllImageDialog_ll.setVisibility(View.GONE);
			// exitCurrentActivity();

		} else if (v.getId() == R.id.racyparentrelative) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_mark_all_option);
			racyAllImageDialog_ll.setVisibility(View.GONE);
		} else if (v.getId() == R.id.racyAllImageDialog_ll) {
			racyAllImageDialog_ll.setVisibility(View.GONE);
		}

	}

	class MyLibraryIconicAdapter extends ArrayAdapter<ImageInfo> {
		Context context;

		Vector<ImageInfo> temp;

		MyLibraryIconicAdapter(Context context) {
			super(context, R.layout.racy_rows, AllRacy.getAllRacy());

			this.context = context;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.racy_rows, null);

			}

			if (position < AllRacy.getAllRacy().size()) {

				final ImageInfo query = AllRacy.getAllRacy()
						.elementAt(position);

				final ImageView favImage_im = (ImageView) v
						.findViewById(R.id.racyImageRow_im);
				final LayoutParams params = favImage_im.getLayoutParams();
				// params.height = params.width;
				params.height = racyAllUnread_gv.getWidth() / 4 - 8;
				favImage_im.setLayoutParams(params);

				if (width > 480) {
					Picasso.with(con)
							.load(AppConstant.thumbsMedium
									+ query.getImageUrl()).noFade().fit()
							.centerInside()
							.placeholder(R.drawable.place_holder)
							.into(favImage_im);
				} else {
					Picasso.with(con)
							.load(AppConstant.thumbSmall + query.getImageUrl())
							.noFade().fit().centerInside()
							.placeholder(R.drawable.place_holder)
							.into(favImage_im);
				}

				if (query.getIsRead().equalsIgnoreCase("1")) {

					// favImage_im.setBackgroundResource(R.drawable.photo_framea);
					favImage_im.setBackgroundColor(getResources().getColor(
							R.color.gray_pic_bg));

				} else {
					// favImage_im.setBackgroundResource(R.drawable.photo_frame2);
					favImage_im.setBackgroundColor(getResources().getColor(
							R.color.white));

				}

				v.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AnalyticsTracker
								.sendTrackData(
										RacyAllUnreadActivity.this,
										R.string.analytics_open_racy_meme_swip_activity);
						AppConstant.position = position;
						SharedPreferencesHelper.setRacyViewpagerPosition(con,
								AppConstant.position);
						final ImageInfo query = AllRacy.getAllRacy().elementAt(
								position);
						AppConstant.image = query.getImageUrl();

						AllRacy.getAllRacy().elementAt(position).setIsRead("1");

						final Intent ii = new Intent(con,
								RacySwipeActivity.class);
						startActivity(ii);

					}
				});

			}
			return v;

		}
	}

	protected void loadRacyList() {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, "Internet Connection Problem",
					"Please check the Internet Connection");
			return;
		}
		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		// final String url = AllURL.getRacyMemeUrl(AppConstant.userID);
		final String url = AllURL.getRecyMemeByPage(AppConstant.userID,
				currentPage, AppConstant.PAGINATION_ITEM_COUNT);
		final AsyncHttpClient client = new AsyncHttpClient();
		client.put(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started

				Log.e("Racy URL is", url + "");
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				try {

					if (currentPage == 1) {
						RacyParser.connect(con, new String(response));
					} else {
						RacyParser.parseNext(new String(response));
					}

					for (final ImageInfo im : db.getAllReadImage("3")) {
						for (final ImageInfo imgobj : AllRacy.getAllRacy()) {
							if (imgobj.getImageID().equalsIgnoreCase(
									im.getImageID())) {

								imgobj.setIsRead("1");
								break;
							}

						}

					}

				} catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (AppConstant.status.equalsIgnoreCase("1")) {

					customerAdapter = new MyLibraryIconicAdapter(con);
					racyAllUnread_gv.setAdapter(customerAdapter);
					customerAdapter.notifyDataSetChanged();

					setLastEntrySelection();

				} else {

					toastMessage("You've reached the end of the memes in this section.");

				}

				/*
				 * dismiss the dialog
				 */
				busyNow.dismis();

				racyAllMemeGrid.onRefreshComplete();

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

				racyAllMemeGrid.onRefreshComplete();

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}

		});

	}

	protected void setLastEntrySelection() {
		// TODO Auto-generated method stub

		try {
			if (currentPage != 1) {
				int pos = 0;

				if ((currentPage - 1) * AppConstant.PAGINATION_ITEM_COUNT < AppConstant.totalRacyMeme) {
					pos = (currentPage - 1) * AppConstant.PAGINATION_ITEM_COUNT;
				}

				racyAllUnread_gv.setSelection(pos);

			}
		} catch (final Exception e) {

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
