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
import android.view.WindowManager;
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
import com.memecabin.holder.AllImageInfo;
import com.memecabin.model.ImageInfo;
import com.memecabin.parser.MemeParser;
/* Jin Remove Mopub ADS
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;
*/
import com.squareup.picasso.Picasso;
import com.memecabin.pro.R;

public class MemesEverUnreadActivity extends Activity implements
		OnClickListener {
	private Context con;
	private GridView gridmeme_gv;
	private MyLibraryIconicAdapter customerAdapter;
	private TextView markAllRead_tv;
	private LinearLayout readMemeAction_ll, memBack_ll, meAllUnreadSlide_ll;
	RelativeLayout memeAllImageDialog_ll;
	private ImageView preBottmBackunRead_iv, preBottmBackPreUread_iv,
			memeUnreadFav_im;
	private LinearLayout meDoit_ll, meDontAgain_ll, noTakeMe_ll, yesorNo_ll;

	private RelativeLayout memeAfterRead_rl, memeallviewParent;

	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;

	PullToRefreshGridView memeAllMemeGrid;

	DatabaseHandler db;

	int width;

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

	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	// The helper object
	IabHelper mHelper;
	private BusyDialog busy = null;

	/*
	 * ************ In app END
	 */
	int memeLenght;
	private final String urlDan = "";

	public static MemesEverUnreadActivity instance;

	public static MemesEverUnreadActivity getInstance() {
		return instance;
	}

	private SlidingMenuActions menuActions;

	private int currentPage = 1;
	/* Jin Remove Mopub ADS
	MoPubView moPubView;
	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.memesallunread);
		con = this;
		db = new DatabaseHandler(con);
		instance = this;
		AllImageInfo.removeAllImageInfo();

		// add call

		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
			*/
		}

		// Den Message call(API)
		initUI();

		initBIllng();
		final Display display = getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		width = size.x;
		Log.e("Screen width Device:", ">>" + width);

	}

	private void initUI() {

		// AppConstant.twitterFlag = false;
		// gridmeme_gv = (GridView) findViewById(R.id.gridmeme_gv);
		memeAllMemeGrid = (PullToRefreshGridView) findViewById(R.id.memeAllMemeGrid);
		gridmeme_gv = memeAllMemeGrid.getRefreshableView();

		markAllRead_tv = (TextView) findViewById(R.id.markAllRead_tv);
		readMemeAction_ll = (LinearLayout) findViewById(R.id.readMemeAction_ll);
		memBack_ll = (LinearLayout) findViewById(R.id.memBack_ll);
		meAllUnreadSlide_ll = (LinearLayout) findViewById(R.id.meAllUnreadSlide_ll);
		memeAllImageDialog_ll = (RelativeLayout) findViewById(R.id.memeAllImageDialog_ll);

		memeAfterRead_rl = (RelativeLayout) findViewById(R.id.memeAfterRead_rl);

		meDoit_ll = (LinearLayout) findViewById(R.id.meDoit_ll);
		meDontAgain_ll = (LinearLayout) findViewById(R.id.meDontAgain_ll);
		noTakeMe_ll = (LinearLayout) findViewById(R.id.noTakeMe_ll);
		yesorNo_ll = (LinearLayout) findViewById(R.id.yesorNo_ll);

		preBottmBackunRead_iv = (ImageView) findViewById(R.id.preBottmBackunRead_iv);
		preBottmBackPreUread_iv = (ImageView) findViewById(R.id.preBottmBackPreUread_iv);
		memeUnreadFav_im = (ImageView) findViewById(R.id.memeUnreadFav_im);
		memeallviewParent = (RelativeLayout) findViewById(R.id.memeallviewParent);

		readMemeAction_ll.setOnClickListener(this);
		preBottmBackunRead_iv.setOnClickListener(this);
		preBottmBackPreUread_iv.setOnClickListener(this);
		memBack_ll.setOnClickListener(this);

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

		meAllUnreadSlide_ll.setOnClickListener(this);
		memeUnreadFav_im.setOnClickListener(this);

		meDoit_ll.setOnClickListener(this);
		meDontAgain_ll.setOnClickListener(this);
		noTakeMe_ll.setOnClickListener(this);
		yesorNo_ll.setOnClickListener(this);
		memeallviewParent.setOnClickListener(this);
		memeAllImageDialog_ll.setOnClickListener(this);

		markAllRead_tv.setText(Html.fromHtml(getString(R.string.markall)));
		final String label = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

		// String abs=R.color.

		// mPullRefreshListView.setMode(Mode.BOTH);
		memeAllMemeGrid.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		memeAllMemeGrid.setMode(Mode.BOTH);
		memeAllMemeGrid.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {

				final String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// String abs=R.color.

				// mPullRefreshListView.setMode(Mode.BOTH);
				memeAllMemeGrid.getLoadingLayoutProxy().setLastUpdatedLabel(
						label);

				// TODO Auto-generated method stub
				if (memeAllMemeGrid.getCurrentMode() == Mode.PULL_FROM_START) {
					currentPage = 1;
					loadMeme();
				} else if (memeAllMemeGrid.getCurrentMode() == Mode.PULL_FROM_END) {

					currentPage++;

					Log.e("This is pull from end", "current page is "
							+ currentPage);

					loadMeme();
				}
			}
		});

		currentPage = 1;
		loadMeme();

		// Create the interstitial.
		// interstitial.setAdUnitId(AppConstant.MY_FullPage_AD_UNIT_ID);

		// Create ad request.
		// AdRequest adRequest = new AdRequest.Builder().build();

		// Begin loading your interstitial.
		// interstitial.loadAd(adRequest);

		gridmeme_gv
				.setHorizontalSpacing(DisplayUtils.dpToPx(getResources(), 4));
		gridmeme_gv.setVerticalSpacing(DisplayUtils.dpToPx(getResources(), 4));
		gridmeme_gv.setBackgroundColor(getResources().getColor(
				R.color.gray_border));

	}

	// Invoke displayInterstitial() when you are ready to display an
	// interstitial.
	// public void displayInterstitial() {
	// if (interstitial.isLoaded()) {
	// interstitial.show();
	// }
	// }

	protected void setLastEntrySelection() {
		// TODO Auto-generated method stub

		try {
			if (currentPage != 1) {

				int pos = 0;

				if ((currentPage - 1) * AppConstant.PAGINATION_ITEM_COUNT < AppConstant.totalEveryoneMeme) {
					pos = (currentPage - 1) * AppConstant.PAGINATION_ITEM_COUNT;
				}

				gridmeme_gv.setSelection(pos);

			}
		} catch (final Exception e) {

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*
		 * set current page from swipe screen
		 */

		final int totalCount = AllImageInfo.getAllImageInfo().size();
		final float cnt = (float) totalCount
				/ AppConstant.PAGINATION_ITEM_COUNT;
		currentPage = (int) Math.ceil(cnt);

		if (currentPage == 0) {
			currentPage = 1;

		}

		if (AppConstant.readFlagmeme) {
			customerAdapter = new MyLibraryIconicAdapter(con);
			gridmeme_gv.setAdapter(customerAdapter);
			customerAdapter.notifyDataSetChanged();
			AppConstant.readFlagmeme = false;
			setLastEntrySelection();
		}

		memeLenght = AllImageInfo.getAllImageInfo().size();
		if (memeLenght > 0) {
			customerAdapter = new MyLibraryIconicAdapter(con);
			gridmeme_gv.setAdapter(customerAdapter);
			customerAdapter.notifyDataSetChanged();
			setLastEntrySelection();
		}

	}

	public void exitCurrentActivity() {

		MemesEverUnreadActivity.this.finish();

	}

	public void toastMessage(String mes) {

		Toast.makeText(con, mes, 3000).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.readMemeAction_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_all_mark_option);
			if (!AppConstant.flageAgainMeme) {
				memeAllImageDialog_ll.setVisibility(View.VISIBLE);
			}

		} else if (v.getId() == R.id.preBottmBackunRead_iv) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			// final Intent ii = new Intent(con, HomeActivity.class);
			// startActivity(ii);
			exitCurrentActivity();

		} else if (v.getId() == R.id.preBottmBackPreUread_iv) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_preference);
			final Intent ii = new Intent(con, PreferencesActivity.class);
			startActivity(ii);
			overridePendingTransition(R.anim.slide_in_up, R.anim.noslide);

		} else if (v.getId() == R.id.memBack_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			exitCurrentActivity();
		} else if (v.getId() == R.id.meAllUnreadSlide_ll) {

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
		} else if (v.getId() == R.id.memeUnreadFav_im) {
			// if (!AppConstant.favflage) {
			//
			// final Intent ii = new Intent(con, FavoritesActivity.class);
			// startActivity(ii);
			// memeUnreadFav_im.setImageResource(R.drawable.memestarlike);
			// AppConstant.favflage = false;
			// } else {
			// memeUnreadFav_im.setImageResource(R.drawable.favcon);
			// AppConstant.favflage = true;
			// }

		} else if (v.getId() == R.id.meDoit_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_mark_all_as_read);
			final BusyDialog busyDialog = new BusyDialog(con, true);
			busyDialog.show();

			final Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					for (final ImageInfo img : AllImageInfo.getAllImageInfo()) {
						db.addRead(img);
						img.setIsRead("1");
					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if (busyDialog != null) {
								busyDialog.dismis();
							}

							customerAdapter = new MyLibraryIconicAdapter(con);
							gridmeme_gv.setAdapter(customerAdapter);
							customerAdapter.notifyDataSetChanged();

							memeAllImageDialog_ll.setVisibility(View.GONE);
							setLastEntrySelection();
						}
					});

				}

			});

			t.start();

		} else if (v.getId() == R.id.meDontAgain_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_mark_all_and_dont_show);
			memeAllImageDialog_ll.setVisibility(View.GONE);
			AppConstant.flageAgainMeme = true;

		} else if (v.getId() == R.id.noTakeMe_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_no_mark_take_back);
			memeAllImageDialog_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.yesorNo_ll) {

		} else if (v.getId() == R.id.memeallviewParent) {
			memeAllImageDialog_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.memeAllImageDialog_ll) {

			memeAllImageDialog_ll.setVisibility(View.GONE);

		}

		// else if (v.getId() == R.id.danmessageshowLinear) {
		// // Den message show at this Link
		// //SharedPreferencesHelper.setMsgShowNot(con, true);
		// denmessagefromapiRelative.setVisibility(View.GONE);
		//
		// //Log.e("URL",":"+urlDan);
		//
		// if(SharedPreferencesHelper.getMsgUrl(con).length()!=0)
		// {
		// Intent browserIntent = new Intent(Intent.ACTION_VIEW,
		// Uri.parse(SharedPreferencesHelper.getMsgUrl(con)));
		// startActivity(browserIntent);
		// }
		//
		//
		//
		//
		// } else if (v.getId() == R.id.danmeassageshownexttimeLinear) {
		// // Message show after perticular time
		// SharedPreferencesHelper.setMsgShowLeter(con, true);
		// //SharedPreferencesHelper.setMessageLeter(con, true);
		// denmessagefromapiRelative.setVisibility(View.GONE);
		//
		// } else if (v.getId() == R.id.danmessagenotshowLinear) {
		// // Message not show next time
		//
		// SharedPreferencesHelper.setMsgShowNot(con, true);
		// denmessagefromapiRelative.setVisibility(View.GONE);
		//
		// } else if (v.getId() == R.id.danallracynoactionLinear) {
		// // no action
		//
		// }

	}

	class MyLibraryIconicAdapter extends ArrayAdapter<ImageInfo> {
		Context context;

		Vector<ImageInfo> temp;

		MyLibraryIconicAdapter(Context context) {
			super(context, R.layout.favo_rows, AllImageInfo.getAllImageInfo());

			this.context = context;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.favo_rows, null);

			}

			if (position < AllImageInfo.getAllImageInfo().size()) {

				final ImageInfo query = AllImageInfo.getAllImageInfo()
						.elementAt(position);

				final ImageView favImage_im = (ImageView) v
						.findViewById(R.id.favImage_im);

				final LayoutParams params = favImage_im.getLayoutParams();
				params.height = gridmeme_gv.getWidth() / 4;
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
						AnalyticsTracker.sendTrackData(
								MemesEverUnreadActivity.this,
								R.string.analytics_open_all_meme_swipe);
						AppConstant.position = position;
						SharedPreferencesHelper.setMemeViewpagerPosition(con,
								AppConstant.position);
						final ImageInfo query = AllImageInfo.getAllImageInfo()
								.elementAt(position);
						AppConstant.image = query.getImageUrl();
						AllImageInfo.getAllImageInfo().elementAt(position)
								.setIsRead("1");
						final Intent ii = new Intent(con,
								MemeSwipeActivity.class);
						startActivity(ii);

					}
				});

			}
			return v;

		}
	}

	String respones_meme = "";

	protected void loadMeme() {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, "Internet Connection Problem",
					"Please check the Internet Connection");
			return;
		}
		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final String url = AllURL.geteveryoneMemesByPage(AppConstant.userID,
				currentPage, AppConstant.PAGINATION_ITEM_COUNT);

		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
				Log.e("MEME every URL", url + "");
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				Log.e("Meme unread response ; ", ">> " + new String(response));

				try {

					if (currentPage == 1) {
						MemeParser.connect(con, new String(response));

					} else {
						/*
						 * append to current
						 */
						MemeParser.parseNext(new String(response));
					}

					for (final ImageInfo im : db.getAllReadImage("1")) {
						for (final ImageInfo imgobj : AllImageInfo
								.getAllImageInfo()) {
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
					gridmeme_gv.setAdapter(customerAdapter);
					customerAdapter.notifyDataSetChanged();
					setLastEntrySelection();

				} else {

					toastMessage("You've reached the end of the memes in this section.");

				}

				memeAllMemeGrid.onRefreshComplete();

				memeLenght = AllImageInfo.getAllImageInfo().size();
				// System.out.println(memeLenght);
				/*
				 * dismiss the dialog
				 */
				busyNow.dismis();
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
				memeAllMemeGrid.onRefreshComplete();

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
