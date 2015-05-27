package com.memecabin.pro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aacom.memecabin.utils.AppConstant;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.learnncode.demotwitterimagepost.HelperMethods;
import com.learnncode.demotwitterimagepost.HelperMethods.TwitterCallback;
import com.learnncode.demotwitterimagepost.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.memecabin.pro.R;

public class MemeShareActivity extends Activity implements OnClickListener {
	private Context con;
	private LinearLayout memeShareSlide_ll;
	private ImageView memeShareSave_im, memeShareFaceBook_im,
			memeShareTwitter_im, memeShareEmail_im, memeShareWhatup,
			memeShareHome_im, memeShareRel_im, memeShared_im, memeFavrite_im;

	// final File myDir = new File(Environment.getExternalStorageDirectory()
	// .getAbsolutePath() + "/Pictures/Images/");
	boolean success = false;
	static File dir = null;
	private File file;
	// private AlertDialog mAlertBuilder;

	// private static final String twitter_consumer_key =
	// "0Tcd9w5IVkbFpO0lb05x3cDEv";
	// private static final String twitter_secret_key =
	// "4VxqUXTGTAoFPZ1rS00U1miuX1ed8C0JFbISYppaxnbPrOTCcB";

	private static final String twitter_consumer_key = "Bjm2ThbJgANH8rWB1v5hiFOTe";
	private static final String twitter_secret_key = "jEdpNeoAJwY8PX3cDNQ097Fhx64DDjtIxISwJH9CuZL8XjqqNl";

	// private static File dir = null;

	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";

	String path = "";
	public SlidingMenu menu;
	private RelativeLayout appRate_rl, disableAdd_rl, getSdl_rl, singleFB_rl,
			silgeInstagram_rl, logoutRelativeLayout, dialogRelativelayout,
			fullscreenslide, getnewapps, singledadRelativelayout,
			rateappRelativelayout;
	private LinearLayout fivestarlinear, rmaindmelinear, noidontwannalinear,
			rateheadinglinear, disableaddheadinglinear, disablefor99,
			disableforever99, nahichangemind, singledateheadinglinear,
			yahtakeme2sateit, remindmenext, bahidontcare;

	Session fb_session;
	Activity activity;
	boolean facebookFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.memeswipeshare);
		con = this;
		activity = this;

		fb_session = Session.openActiveSessionFromCache(con);

		initUI();

	}

	private void initUI() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.1f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.slidememeaction);
		new SlidingMenuActions(con, menu, appRate_rl, disableAdd_rl, getSdl_rl,
				singleFB_rl, silgeInstagram_rl, logoutRelativeLayout,
				dialogRelativelayout, fullscreenslide, getnewapps,
				singledadRelativelayout, rateappRelativelayout, fivestarlinear,
				rmaindmelinear, noidontwannalinear, rateheadinglinear,
				disableaddheadinglinear, disablefor99, disableforever99,
				nahichangemind, singledateheadinglinear, yahtakeme2sateit,
				remindmenext, bahidontcare);

		// memeShareSlide_ll = (LinearLayout)
		// findViewById(R.id.memeShareSlide_ll);

		memeShareSave_im = (ImageView) findViewById(R.id.memeShareSave_im);
		memeShareFaceBook_im = (ImageView) findViewById(R.id.memeShareFaceBook_im);
		memeShareTwitter_im = (ImageView) findViewById(R.id.memeShareTwitter_im);
		memeShareEmail_im = (ImageView) findViewById(R.id.memeShareEmail_im);
		// memeShareWhatup = (ImageView) findViewById(R.id.memeShareWhatup);
		// memeShareHome_im = (ImageView) findViewById(R.id.memeShareHome_im);
		// memeShareRel_im = (ImageView) findViewById(R.id.memeShareRel_im);
		// memeShared_im = (ImageView) findViewById(R.id.memeShared_im);
		// memeFavrite_im = (ImageView) findViewById(R.id.memeFavrite_im);

		memeShareSlide_ll.setOnClickListener(this);
		memeShareSave_im.setOnClickListener(this);
		memeShareFaceBook_im.setOnClickListener(this);
		memeShareTwitter_im.setOnClickListener(this);
		memeShareEmail_im.setOnClickListener(this);
		memeShareWhatup.setOnClickListener(this);
		memeShareHome_im.setOnClickListener(this);
		memeShareRel_im.setOnClickListener(this);
		memeShared_im.setOnClickListener(this);
		memeFavrite_im.setOnClickListener(this);

	}

	public void exitCurrentActivity() {

		MemeShareActivity.this.finish();

	}

	public void toastMessage(String mes) {

		Toast.makeText(con, mes, 3000).show();
	}

	@Override
	public void onClick(View v) {
		// if (v.getId() == R.id.memeShareHome_im) {
		//
		// final Intent ii = new Intent(con, HomeActivity.class);
		// startActivity(ii);
		// MemeShareActivity.this.finish();
		// } else if (v.getId() == R.id.memeShareSlide_ll) {
		//
		// menu.toggle();
		//
		// }
		// else if (v.getId() == R.id.memeFavrite_im) {
		//
		// final Intent ii = new Intent(con, FavoritesActivity.class);
		// startActivity(ii);
		// }
		if (v.getId() == R.id.memeShareSave_im) {

			final String imageurl = "" + AppConstant.imageBaseUrl
					+ AppConstant.image;

			Picasso.with(this).load(imageurl).into(target);

		} else if (v.getId() == R.id.memeShareFaceBook_im) {
			// Share fb

			final String imageurl = "" + AppConstant.imageBaseUrl
					+ AppConstant.image;

			// if (facebookConnector.getFacebook().isSessionValid()) {
			// loadData(imageurl, "facebook");
			// } else {
			// final SessionEvents.AuthListener listener = new
			// SessionEvents.AuthListener() {
			//
			// @Override
			// public void onAuthSucceed() {
			//
			// loadData(imageurl, "facebook");
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

			fb_session = Session.openActiveSessionFromCache(con);

			if (fb_session != null && fb_session.isOpened()) {

				loadData(imageurl, "facebook");
				// getbitmapFromServerForShare("facebook");
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

		} else if (v.getId() == R.id.memeShareTwitter_im) {

			final String imageurl = "" + AppConstant.imageBaseUrl
					+ AppConstant.image;
			//
			loadData(imageurl, "twitter");
			// share twitter

		} else if (v.getId() == R.id.memeShareEmail_im) {

			// share Email

			final String imageurl = "" + AppConstant.imageBaseUrl
					+ AppConstant.image;

			loadData(imageurl, "email");

		}
		// else if (v.getId() == R.id.memeShareWhatup) {
		// // Share MMS/SMS
		//
		// final String imageurl = "" + AppConstant.imageBaseUrl
		// + AppConstant.image;
		//
		// loadData(imageurl, "mms");
		//
		// }

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

			final String imgURL = AppConstant.imageBaseUrl + AppConstant.image;

			loadData(imgURL, "facebook");
			// getbitmapFromServerForShare("facebook");
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

	private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
			throws IOException {
		/*
		 * 
		 * check the path and create if needed
		 */
		createBaseDirctory();

		try {

			new Date();

			OutputStream out = null;
			file = new File(MemeShareActivity.dir, "/" + filename);

			if (file.exists()) {
				file.delete();
			}

			out = new FileOutputStream(file);

			bitmap22.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.flush();
			out.close();
			// Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
			return file.getAbsolutePath();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private String saveBitmapByFile(File f1, String filename)
			throws IOException {
		/*
		 * 
		 * check the path and create if needed
		 */
		createBaseDirctory();

		Bitmap bitmap = null;
		// File f= new File(_path);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(f1), null,
					options);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		try {

			new Date();

			OutputStream out = null;
			file = new File(MemeShareActivity.dir, "/" + filename);

			if (file.exists()) {
				file.delete();
			}

			out = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.flush();
			out.close();
			// Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
			return file.getAbsolutePath();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void loadData(final String url, final String type) {

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final AsyncHttpClient client = new AsyncHttpClient();

		client.get(url, new FileAsyncHttpResponseHandler(con) {

			@Override
			public void onSuccess(int arg0, Header[] arg1, final File arg2) {
				// TODO Auto-generated method stub

				if (busyNow != null) {
					busyNow.dismis();
				}

				final String TimeStr = "tempimage.png";

				try {
					path = saveBitmapByFile(arg2, TimeStr);
					AppConstant.twitterImage = path;
				} catch (final IOException e) {

					e.printStackTrace();
				}

				if (type.equalsIgnoreCase("email")) {
					final Intent exportMessageIntent = new Intent(
							android.content.Intent.ACTION_SEND_MULTIPLE);
					exportMessageIntent.setType("text/plain");
					exportMessageIntent.setType("application/octet-stream");
					final ArrayList<Uri> uris = new ArrayList<Uri>();

					final String[] filePaths = new String[] { path };
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
							"Meme CABIN Image");

					exportMessageIntent.putExtra(Intent.EXTRA_TEXT,
							"Dear\n This is meme cabin image.");

					startActivity(exportMessageIntent);
				} else if (type.equalsIgnoreCase("mms")) {

					//
					// final Intent sendIntent = new
					// Intent(Intent.ACTION_SENDTO);
					//
					// sendIntent.putExtra("sms_body", "some text");
					// sendIntent.putExtra(Intent.EXTRA_STREAM,
					// Uri.parse(path));
					// sendIntent.setType("image/png");
					// startActivity(sendIntent);

				} else if (type.equalsIgnoreCase("facebook")) {
					final Thread t = new Thread() {
						@Override
						public void run() {

							try {
								// facebookConnector.getMe();

								final Bitmap b = BitmapFactory.decodeFile(path);

								AppConstant.fbImage = b;

								// final ByteArrayOutputStream stream = new
								// ByteArrayOutputStream();
								// b.compress(Bitmap.CompressFormat.PNG, 100,
								// stream);
								// final byte[] byteArray =
								// stream.toByteArray();
								//
								// final boolean sab = facebookConnector
								// .facebookImagePost(byteArray,
								// "Meme CABIN", "Image",
								// "Descrip sdes");

								runOnUiThread(new Runnable() {
									@Override
									public void run() {

										if (makeMeRequest(b, "Meme CABIN",
												"Image", con)) {
											// toastMessage("Facebook submittte");
										} else {
											toastMessage("Facebook not submittte");
										}

									}
								});

							} catch (final Exception ex) {
								Log.e(TAG, "Error sending msg", ex);
							}
						}
					};
					t.start();
				} else if (type.equalsIgnoreCase("twitter")) {
					if (LoginActivity.isActive(con)) {

						// AppConstant.twitterFlag = false;

						try {

							final BusyDialog busyDialog1 = new BusyDialog(con,
									true);
							busyDialog1.show();

							// InputStream inputStream =
							// v.getContext().getAssets().open("1.png");
							// Bitmap bmp =
							// BitmapFactory.decodeStream(inputStream);
							// String filename =
							// Environment.getExternalStorageDirectory().toString()
							// + File.separator + "1.png";
							// Log.d("BITMAP", filename);
							// FileOutputStream out = new
							// FileOutputStream(filename);
							// bmp.compress(Bitmap.CompressFormat.PNG, 90, out);

							HelperMethods.postToTwitterWithImage(con,
									(Activity) con, path,
									getString(R.string.tweet_with_image_text),
									new TwitterCallback() {

										@Override
										public void onFinsihed(Boolean response) {
											if (busyDialog1 != null) {
												busyDialog1.dismis();
											}

											Log.d(TAG,
													"----------------response----------------"
															+ response);
											Toast.makeText(
													con,
													getString(R.string.image_posted_on_twitter),
													Toast.LENGTH_SHORT).show();
										}
									});

						} catch (final Exception ex) {
							Toast.makeText(con, "ERROR", Toast.LENGTH_SHORT)
									.show();
						}
					} else {

						// AppConstant.twitterFlag = true;

						startActivity(new Intent(con, LoginActivity.class));
					}
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					File arg3) {

				if (busyNow != null) {
					busyNow.dismis();
				}

			}
		});

	}

	public static byte[] readFile(File file) throws IOException {
		// Open file
		final RandomAccessFile f = new RandomAccessFile(file, "r");
		try {
			// Get and check length
			final long longlength = f.length();
			final int length = (int) longlength;
			if (length != longlength) {
				throw new IOException("File size >= 2 GB");
			}
			// Read file and return data
			final byte[] data = new byte[length];
			f.readFully(data);
			return data;
		} finally {
			f.close();
		}
	}

	private final Target target = new Target() {
		@Override
		public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
			new Thread(new Runnable() {
				@Override
				public void run() {

					final long timestr = System.currentTimeMillis();

					final File file = new File(Environment
							.getExternalStorageDirectory().getPath()

					+ "/MEMECABIN" + "/" + timestr + "meme.jpg");
					try {
						file.createNewFile();
						final FileOutputStream ostream = new FileOutputStream(
								file);
						bitmap.compress(CompressFormat.JPEG, 75, ostream);
						ostream.close();
					} catch (final Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
			toastMessage("Image saved successed");
			exitCurrentActivity();
		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
		}

		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
			if (placeHolderDrawable != null) {
			}
		}
	};

	public void createBaseDirctory() {

		final String extStorageDirectory = Environment
				.getExternalStorageDirectory().toString();
		dir = new File(extStorageDirectory + "/MEMECABIN");

		if (MemeShareActivity.dir.mkdir()) {
			System.out.println("Directory created");
		} else {
			System.out.println("Directory is not created or exists");
		}
	}

	public void onShareClick() {
		getResources();

		final Intent emailIntent = new Intent();
		emailIntent.setAction(Intent.ACTION_SEND);
		// Native email client doesn't currently support HTML, but it doesn't
		// hurt to try in case they fix it
		emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("thi is te"));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "sub");
		emailIntent.setType("message/rfc822");

		final PackageManager pm = getPackageManager();
		final Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("text/plain");

		final Intent openInChooser = Intent
				.createChooser(emailIntent, "choose");

		final List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent,
				0);
		final List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
		for (int i = 0; i < resInfo.size(); i++) {
			// Extract the label, append it, and repackage it in a LabeledIntent
			final ResolveInfo ri = resInfo.get(i);
			final String packageName = ri.activityInfo.packageName;
			if (packageName.contains("android.email")) {
				emailIntent.setPackage(packageName);
			} else if (packageName.contains("twitter")
					|| packageName.contains("facebook")
					|| packageName.contains("mms")
					|| packageName.contains("android.gm")) {
				final Intent intent = new Intent();
				intent.setComponent(new ComponentName(packageName,
						ri.activityInfo.name));
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				if (packageName.contains("twitter")) {

					intent.putExtra(Intent.EXTRA_TEXT, "twiter");

				} else if (packageName.contains("facebook")) {
					// Warning: Facebook IGNORES our text. They say
					// "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
					// One workaround is to use the Facebook SDK to post, but
					// that doesn't allow the user to choose how they want to
					// share. We can also make a custom landing page, and the
					// link
					// will show the <meta content ="..."> text from that page
					// with our link in Facebook.
					intent.putExtra(Intent.EXTRA_TEXT, "facebbo");

				} else if (packageName.contains("mms")) {

					intent.putExtra(Intent.EXTRA_TEXT, "SMS");

				} else if (packageName.contains("android.gm")) {
					intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("email"));
					intent.putExtra(Intent.EXTRA_SUBJECT, "sub");
					intent.setType("message/rfc822");
				}

				intentList.add(new LabeledIntent(intent, packageName, ri
						.loadLabel(pm), ri.icon));
			}
		}

		// convert intentList to array
		final LabeledIntent[] extraIntents = intentList
				.toArray(new LabeledIntent[intentList.size()]);

		openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
		startActivity(openInChooser);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (false) {

			final BusyDialog busyDialog1 = new BusyDialog(con, true);
			busyDialog1.show();

			HelperMethods.postToTwitterWithImage(con, (Activity) con,
					AppConstant.twitterImage,
					getString(R.string.tweet_with_image_text),
					new TwitterCallback() {

						@Override
						public void onFinsihed(Boolean response) {
							if (busyDialog1 != null) {
								busyDialog1.dismis();
							}

							Log.d(TAG,
									"----------------response----------------"
											+ response);
							Toast.makeText(
									con,
									getString(R.string.image_posted_on_twitter),
									Toast.LENGTH_SHORT).show();
						}
					});
		}
	}
	//
	// private class myView extends View{
	//
	// public myView(Context context) {
	// super(context);
	// }
	//
	// @Override
	// protected void onDraw(Canvas canvas) {
	//
	// Paint myPaint = new Paint();
	// myPaint.setColor(Color.BLACK);
	// myPaint.setStyle(Paint.Style.STROKE);
	// myPaint.setStrokeWidth(5);
	// canvas.drawRect(10, 10, 100, 100, myPaint);
	// }
	// }

}
