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
import android.content.Context;
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
import android.widget.FrameLayout;
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
import com.memecabin.holder.AllFavoriteMeme;
import com.memecabin.holder.AllReload;
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
import com.memecabin.pro.R;

public class FavoritesSwipeActivity extends FragmentActivity implements
		OnClickListener {
	private Context con;
	private LinearLayout favoSwipeBack_ll, favoHere_ll, favoFirst_ll,
			favofavri_ll;
	private ImageView favoSwipeHome_im, favoSwipeRel_im, favoSwipeShare_im,
			favoSwipeFav_im, favoLike_im;
	private RelativeLayout favoAfterright_ll, favoAfterLeft_rl,
			favosharememe_rl;
	private LinearLayout favofirstVp_ll, favomessageNotShow_ll,
			favoumCancle_ll;
	private TextView favoliketotal_tv;
	private ViewPager favoimageView1;
	private ImageView favesaveimaelocal_im, faveshareimagefb_im,
			favsharetwitter_im, faveShareemail_im, favesendimageMMS_im,
			favoriteReportImage;

	private MyPagerAdapter myPagerAdapter;
	public int currentIndex = 0;
	public boolean iniLoad = true;
	// static DatabaseHandler db;
	ImageInfo query;
	String like_count = "";
	Vector<ImageInfo> imageInfos;
	private Target loadTarget;

	FrameLayout fulladdfavolayout, addBottomfavoriteSwipe;
	String adSpaceName, adSpaceName2;

	DatabaseHandler db;
	FavoriteManager favoriteManager;
	TextView addtofavTv;

	Animation animationFadeIn;
	Animation animationFadeOut;

	int fadeInDuration = 500;
	int timeBetween = 3000;
	int fadeOutDuration = 1000;
	Handler handler = new Handler();

	// private FacebookConnector facebookConnector;
	// private static final String FACEBOOK_APPID = "558359717642213";
	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";
	String path = "";
	static File dir = null;
	private File file;

	int courter = 0;

	LinearLayout llLikePanel;

	EditText commentstextforshare;
	RelativeLayout sharecommntsviewfavo;
	TextView buttontexshare;
	LinearLayout sharebtfbortwitter;
	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	Handler adHandler;
	/* Jin Remove Mopub ADS
	// private InterstitialAd mInterstitialAd;
	private MoPubInterstitial mInterstitial;
	MoPubView moPubView;
	*/
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.favwipe);
		con = this;
		activity = this;

		fb_session = Session.openActiveSessionFromCache(con);
		db = new DatabaseHandler(con);
		favoriteManager = new FavoriteManager(con);

		animationFadeIn = AnimationUtils.loadAnimation(con, R.anim.fadein);
		animationFadeOut = AnimationUtils.loadAnimation(con, R.anim.fadeout);

		if (!SharedPreferencesHelper.isAddDisable(con)) {

			/* Jin Remove Mopub ADS
			addShow();
			initAd();
			*/
		}

		initUI();

	}

	private void initUI() {

		favoriteReportImage = (ImageView) findViewById(R.id.favoriteReportImage);

		favoSwipeBack_ll = (LinearLayout) findViewById(R.id.favoSwipeBack_ll);

		favoHere_ll = (LinearLayout) findViewById(R.id.favoHere_ll);
		favoFirst_ll = (LinearLayout) findViewById(R.id.favoFirst_ll);
		favofavri_ll = (LinearLayout) findViewById(R.id.favofavri_ll);

		favofirstVp_ll = (LinearLayout) findViewById(R.id.favofirstVp_ll);
		favomessageNotShow_ll = (LinearLayout) findViewById(R.id.favomessageNotShow_ll);
		favoumCancle_ll = (LinearLayout) findViewById(R.id.favoumCancle_ll);
		llLikePanel = (LinearLayout) findViewById(R.id.llLikePanel);

		favoAfterright_ll = (RelativeLayout) findViewById(R.id.favoAfterright_ll);
		favoAfterLeft_rl = (RelativeLayout) findViewById(R.id.favoAfterLeft_rl);
		favosharememe_rl = (RelativeLayout) findViewById(R.id.favosharememe_rl);

		favoSwipeHome_im = (ImageView) findViewById(R.id.favoSwipeHome_im);
		favoSwipeShare_im = (ImageView) findViewById(R.id.favoSwipeShare_im);
		favoSwipeRel_im = (ImageView) findViewById(R.id.favoSwipeRel_im);
		favoSwipeFav_im = (ImageView) findViewById(R.id.favoSwipeFav_im);
		favoLike_im = (ImageView) findViewById(R.id.favoLike_im);
		favesaveimaelocal_im = (ImageView) findViewById(R.id.favesaveimaelocal_im);
		faveshareimagefb_im = (ImageView) findViewById(R.id.faveshareimagefb_im);
		favsharetwitter_im = (ImageView) findViewById(R.id.favsharetwitter_im);
		faveShareemail_im = (ImageView) findViewById(R.id.faveShareemail_im);
		favesendimageMMS_im = (ImageView) findViewById(R.id.favesendimageMMS_im);

		favoimageView1 = (ViewPager) findViewById(R.id.favoimageView1);

		favoliketotal_tv = (TextView) findViewById(R.id.favoliketotal_tv);
		addtofavTv = (TextView) findViewById(R.id.addtofavTv);

		sharecommntsviewfavo = (RelativeLayout) findViewById(R.id.sharecommntsviewfavo);
		sharebtfbortwitter = (LinearLayout) findViewById(R.id.sharebtfbortwitter);
		commentstextforshare = (EditText) findViewById(R.id.commentstextforshare);
		buttontexshare = (TextView) findViewById(R.id.buttontexshare);

		favoSwipeBack_ll.setOnClickListener(this);
		favoSwipeHome_im.setOnClickListener(this);
		favoSwipeShare_im.setOnClickListener(this);
		favoSwipeRel_im.setOnClickListener(this);
		favoSwipeFav_im.setOnClickListener(this);
		favoLike_im.setOnClickListener(this);
		favesaveimaelocal_im.setOnClickListener(this);
		faveshareimagefb_im.setOnClickListener(this);
		favsharetwitter_im.setOnClickListener(this);
		faveShareemail_im.setOnClickListener(this);
		favesendimageMMS_im.setOnClickListener(this);

		favoHere_ll.setOnClickListener(this);
		favoFirst_ll.setOnClickListener(this);
		favofavri_ll.setOnClickListener(this);
		favosharememe_rl.setOnClickListener(this);

		favofirstVp_ll.setOnClickListener(this);
		favomessageNotShow_ll.setOnClickListener(this);
		favoumCancle_ll.setOnClickListener(this);
		favoAfterright_ll.setOnClickListener(this);
		favoAfterLeft_rl.setOnClickListener(this);
		sharebtfbortwitter.setOnClickListener(this);
		favoriteReportImage.setOnClickListener(this);

		imageInfos = AllFavoriteMeme.getAllImageInfo();
		final int vectorsize = imageInfos.size();
		// Log.e("Vector size of favorites:....", ""+vectorsize);

		myPagerAdapter = new MyPagerAdapter(con, imageInfos, llLikePanel);
		favoimageView1.setAdapter(myPagerAdapter);

		// ......................View Pager.................
		favoimageView1.setCurrentItem(SharedPreferencesHelper
				.getFevoriteViewpagerPosition(con));

		// query = imageInfos.get(AppConstant.position);
		query = imageInfos.elementAt(SharedPreferencesHelper
				.getFevoriteViewpagerPosition(con));

		Log.e("Query show:",
				"" + SharedPreferencesHelper.getFevoriteViewpagerPosition(con));

		AppConstant.imageid = query.getImageID();
		AppConstant.imagecategory = query.getImageCategory();

		favoliketotal_tv.setText(getResources().getString(R.string.totallikes)
				+ "(" + query.getLikeCount() + ")");

		// Log.e(" favorites Position ", ""+query.getFavorite());

		if (query.getFavorite().equalsIgnoreCase("1")) {
			favoSwipeFav_im.setImageResource(R.drawable.afterfavoimages);
		} else {
			favoSwipeFav_im.setImageResource(R.drawable.c111);
		}

		// if (db.isLikeImage(query.getImageID(), query.getImageCategory()))
		if (query.getIsLike().equalsIgnoreCase("1")) {

			favoLike_im.setImageResource(R.drawable.afterlikefavo);
		} else {
			favoLike_im.setImageResource(R.drawable.beforelike);
		}

		favoimageView1.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				/* By Jin

				if (positionOffset > 0) {
					favoSwipeFav_im.setImageResource(R.drawable.cc111);
				} else if (positionOffset == 0.0f) {

					if (query.getFavorite().equalsIgnoreCase("1")) {
						favoSwipeFav_im
								.setImageResource(R.drawable.afterfavoimages);
					} else {
						favoSwipeFav_im.setImageResource(R.drawable.c111);
					}
				}
				*/

			}

			@Override
			public void onPageSelected(int arg0) {

				llLikePanel.setVisibility(View.VISIBLE);
				final int index = favoimageView1.getCurrentItem();
				currentIndex = index;
				SharedPreferencesHelper
						.setFevoriteViewpagerPosition(con, index);

				query = imageInfos.elementAt(index);

				// Log.e("AFter scroll......>>>>>>", ""+query);

				AppConstant.image = query.getImageUrl();
				AppConstant.totalLike = query.getLikeCount();
				AppConstant.imageid = query.getImageID();
				AppConstant.imagecategory = query.getImageCategory();
				favoliketotal_tv.setText(getResources().getString(
						R.string.totallikes)
						+ "(" + query.getLikeCount() + ")");

				if (query.getFavorite().equalsIgnoreCase("1")) {
					favoSwipeFav_im
							.setImageResource(R.drawable.afterfavoimages);
				} else {
					favoSwipeFav_im.setImageResource(R.drawable.c111);
				}

				if (query.getIsLike().equalsIgnoreCase("1")) {

					favoLike_im.setImageResource(R.drawable.afterlikefavo);
				} else {
					favoLike_im.setImageResource(R.drawable.beforelike);
				}

				// final int vectorSize = db.getAllFavouriteImage().size();
				final int vectorSize = imageInfos.size();

				if (vectorSize > 0) {
					if (currentIndex + 1 == vectorSize) {

						favoAfterright_ll.setVisibility(View.VISIBLE);
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
							favoliketotal_tv.setText(getResources().getString(
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

	public void removeImageAfter1seconds()

	{

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				addtofavTv.setVisibility(View.GONE);

				addtofavTv.startAnimation(animationFadeOut);

			}
		}, 1000);
	}

	public void exitCurrentActivity() {

		FavoritesSwipeActivity.this.finish();

	}

	public void toastMessage(String mes) {

		final Toast toast = Toast.makeText(getApplicationContext(), mes, 3000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.favoSwipeBack_ll) {
			AppConstant.refreshFavourite = true;
			if (AppConstant.favoriteshareview) {
				AppConstant.favoriteshareview = false;
				AnalyticsTracker.sendTrackData(this,
						R.string.analytics_go_back_favorite);

				exitCurrentActivity();

			} else {

				if (AppConstant.readfavoriteshare) {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_close_share_option);
					favosharememe_rl.setVisibility(View.GONE);
					AppConstant.readfavoriteshare = false;
				} else {
					AnalyticsTracker.sendTrackData(this,
							R.string.analytics_go_back_favorite);
					AppConstant.refreshFavourite = true;
					exitCurrentActivity();
				}
			}

		} else if (v.getId() == R.id.favoSwipeHome_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_home);
			final Intent ii = new Intent(con, HomeActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			exitCurrentActivity();
			if (FavoritesActivity.getInstance() != null) {
				FavoritesActivity.getInstance().finish();
			}

		} else if (v.getId() == R.id.favoSwipeRel_im) {
			// Do Refresh
			// favoimageView1.setCurrentItem(0);
			if (SharedPreferencesHelper.getFavoSwipeDialog(con)) {
				favoimageView1.setCurrentItem(0);
			} else {
				favoAfterLeft_rl.setVisibility(View.VISIBLE);
			}

			// final String reUrl = AllURL.getReloadinfo(AppConstant.imageid);
			// reload(reUrl);

		} else if (v.getId() == R.id.favoSwipeShare_im) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_open_share_option);
			favosharememe_rl.setVisibility(View.VISIBLE);
			AppConstant.readfavoriteshare = true;

			// final Intent ii = new Intent(con, MemeShareActivity.class);
			// startActivity(ii);

		}

		else if (v.getId() == R.id.favoLike_im) {
			if (SharedPreferencesHelper.getUsingskip(con)) {

				toastMessage(getString(R.string.text_decide_title));

			} else {
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

		} else if (v.getId() == R.id.favoHere_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_keep_me_here);
			favoimageView1.setCurrentItem(favoimageView1.getCurrentItem() - 1);
			favoAfterright_ll.setVisibility(View.GONE);

		} else if (v.getId() == R.id.favoFirst_ll) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_take_me_to_first);
			// if (!SharedPreferencesHelper.getFavoSwipeDialog(con)) {
			// favoAfterright_ll.setVisibility(View.GONE);
			// favoAfterLeft_rl.setVisibility(View.VISIBLE);
			// } else {
			favoAfterright_ll.setVisibility(View.GONE);
			favoimageView1.setCurrentItem(0);
			// }

		}

		else if (v.getId() == R.id.favofirstVp_ll) {
			favoimageView1.setCurrentItem(0);
			favoAfterLeft_rl.setVisibility(View.GONE);
		} else if (v.getId() == R.id.favomessageNotShow_ll) {
			favoimageView1.setCurrentItem(favoimageView1.getCurrentItem() - 1);
			favoAfterright_ll.setVisibility(View.GONE);
			SharedPreferencesHelper.setFavoSwipeDialog(con, true);

		} else if (v.getId() == R.id.favoumCancle_ll) {

			favoAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.favoSwipeFav_im) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_add_remove_favorite);

			if (query != null) {

				// Log.e(" favorites Position ", ""+query.getFavorite());

				if (!query.getFavorite().equalsIgnoreCase("1")) {
					query.setFavorite("1");
					db.addFavoritesImg(query);
					favoriteManager.addToFavoriteOnline(query);

					favoSwipeFav_im
							.setImageResource(R.drawable.afterfavoimages);

					addtofavTv.setText(""
							+ getResources().getString(R.string.addfavorites));

					addtofavTv.startAnimation(animationFadeIn);
					addtofavTv.setVisibility(View.VISIBLE);

					removeImageAfter1seconds();

				} else {

					query.setFavorite("0");
					db.removeFavoritesImg(query);
					favoriteManager.removeFromFavoriteOnline(query);

					favoSwipeFav_im.setImageResource(R.drawable.c111);

					addtofavTv.setText(""
							+ getResources()
									.getString(R.string.removefavorites));

					addtofavTv.startAnimation(animationFadeIn);
					addtofavTv.setVisibility(View.VISIBLE);

					removeImageAfter1seconds();

				}
			}

		} else if (v.getId() == R.id.favofavri_ll) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_go_back_favorite);
			exitCurrentActivity();
			/*
			 * final Intent ii = new Intent(con, FavoritesActivity.class);
			 * exitCurrentActivity(); if(FavoritesActivity.getInstance() !=
			 * null){ FavoritesActivity.getInstance().finish(); }
			 * startActivity(ii);
			 */
		} else if (v.getId() == R.id.favesaveimaelocal_im) {
			// Image save own device
			// final String imageurl = "" + AppConstant.imageBaseUrl
			// + AppConstant.image;
			// Picasso.with(this).load(imageurl).into(target);

			getbitmapFromServer();

		} else if (v.getId() == R.id.faveshareimagefb_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_facebook);

			SharedPreferencesHelper.setFbmeme(con, true);

			favesaveimaelocal_im.setClickable(false);
			faveshareimagefb_im.setClickable(false);
			favsharetwitter_im.setClickable(false);
			faveShareemail_im.setClickable(false);
			favesendimageMMS_im.setClickable(false);

			commentstextforshare.setText("");
			// Remove : Sharing this from the SDL app! Get it on Android and
			// iOS!
			commentstextforshare.append("");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Facebook");
			sharecommntsviewfavo.setVisibility(View.VISIBLE);
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

		} else if (v.getId() == R.id.favsharetwitter_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_twitter);
			// Image share twitter

			SharedPreferencesHelper.setTwittermeme(con, true);

			favesaveimaelocal_im.setClickable(false);
			faveshareimagefb_im.setClickable(false);
			favsharetwitter_im.setClickable(false);
			faveShareemail_im.setClickable(false);
			favesendimageMMS_im.setClickable(false);
			;

			commentstextforshare.setText("");
			commentstextforshare
					.append("Sharing this from the MemeCabin app! Get it on Android and iOS!");
			commentstextforshare.requestFocus();
			buttontexshare.setText("Share to Twitter");
			sharecommntsviewfavo.setVisibility(View.VISIBLE);
			openKeyBoard(v);

			// getbitmapFromServerForShare("twitter");

		} else if (v.getId() == R.id.faveShareemail_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_email);
			// Image send via Email
			getbitmapFromServerForShare("email");

		} else if (v.getId() == R.id.favesendimageMMS_im) {
			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_share_on_mms);
			// Image Send via MMS
			// getbitmapFromServerForShare("mms");
			getbitmapFromServerForShare("mms");

		} else if (v.getId() == R.id.favosharememe_rl) {

			AnalyticsTracker.sendTrackData(this,
					R.string.analytics_close_share_option);

			if (sharecommntsviewfavo.getVisibility() == View.VISIBLE) {

				SharedPreferencesHelper.setFbmeme(con, false);
				SharedPreferencesHelper.setTwittermeme(con, false);

				favesaveimaelocal_im.setClickable(true);
				faveshareimagefb_im.setClickable(true);
				favsharetwitter_im.setClickable(true);
				faveShareemail_im.setClickable(true);
				favesendimageMMS_im.setClickable(true);

				closeKeyBoard(v);
				sharecommntsviewfavo.setVisibility(View.GONE);

			} else if (favosharememe_rl.getVisibility() == View.VISIBLE
					&& sharecommntsviewfavo.getVisibility() == View.GONE) {

				AppConstant.favoriteshareview = true;
				favosharememe_rl.setVisibility(View.GONE);
			}

			// AppConstant.favoriteshareview = true;
			//
			// favosharememe_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.favoAfterright_ll) {
			favoimageView1.setCurrentItem(favoimageView1.getCurrentItem() - 1);
			favoAfterright_ll.setVisibility(View.GONE);
		} else if (v.getId() == R.id.favoAfterLeft_rl) {
			favoAfterLeft_rl.setVisibility(View.GONE);

		} else if (v.getId() == R.id.sharebtfbortwitter) {

			closeKeyBoard(v);

			if (SharedPreferencesHelper.getFbmeme(con)) {

				favesaveimaelocal_im.setClickable(true);
				faveshareimagefb_im.setClickable(true);
				favsharetwitter_im.setClickable(true);
				faveShareemail_im.setClickable(true);
				favesendimageMMS_im.setClickable(true);

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

				favesaveimaelocal_im.setClickable(true);
				faveshareimagefb_im.setClickable(true);
				favsharetwitter_im.setClickable(true);
				faveShareemail_im.setClickable(true);
				favesendimageMMS_im.setClickable(true);

				SharedPreferencesHelper.setTwittermeme(con, false);
				getbitmapFromServerForShare("twitter");

			}

		} else if (v.getId() == R.id.favoriteReportImage) {
			final Intent i = new Intent(con, ReportActivity.class);
			startActivity(i);
		}
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
							favoLike_im
									.setImageResource(R.drawable.afterlikefavo);

							// db.addLike(query);
							favoliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// favoliketotal_tv.setText("Total Likes ("+
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
							favoLike_im.setImageResource(R.drawable.beforelike);
							// db.removeLike(query);
							favoliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");
							// favoliketotal_tv.setText("Total Likes ("+
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
	// + "/MEMECABIN" + "/" + timestr + "fave.jpg");
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
	// favosharememe_rl.setVisibility(View.GONE);
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

							favoliketotal_tv.setText(getResources().getString(
									R.string.totallikes)
									+ "(" + query.getLikeCount() + ")");

							// favoliketotal_tv.setText("Total Likes ("+
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

								// final String imagePath = MakeBitmapAndSave
								// .saveBitmapPathToShare(myImage, "");

								final String value = commentstextforshare
										.getText().toString();

								// final Bitmap b = BitmapFactory
								// .decodeFile(imagePath);
								//
								//
								//
								// // final Bitmap b = bmp;
								// final ByteArrayOutputStream stream = new
								// ByteArrayOutputStream();
								// b.compress(Bitmap.CompressFormat.PNG, 100,
								// stream);
								// final byte[] byteArray =
								// stream.toByteArray();
								//
								// final boolean sab = facebookConnector
								// .facebookImagePost(byteArray, value,
								// "", "");

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
											sharecommntsviewfavo
													.setVisibility(View.GONE);
										} else {

											if (busyDialog != null) {
												busyDialog.dismis();
											}
											toastMessage("Facebook post failed!");
											commentstextforshare.setText("");
											sharecommntsviewfavo
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
											sharecommntsviewfavo
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

					Log.d(TAG, "----------------response----------------"
							+ response);
					Toast.makeText(con,
							getString(R.string.image_posted_on_twitter),
							Toast.LENGTH_SHORT).show();
					commentstextforshare.setText("");
					sharecommntsviewfavo.setVisibility(View.GONE);
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {

		final int memeLenght = imageInfos.size();
		System.out.println(memeLenght);
		if (memeLenght > 0) {
			imageInfos.remove(memeLenght - 1);

		}

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
				}

			}

			@Override
			public void onInterstitialFailed(MoPubInterstitial arg0,
					MoPubErrorCode arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onInterstitialDismissed(MoPubInterstitial arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onInterstitialClicked(MoPubInterstitial arg0) {
				// TODO Auto-generated method stub

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
	*/

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
