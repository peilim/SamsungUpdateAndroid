/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.learnncode.demotwitterimagepost;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.aapbd.utils.notification.BusyDialog;
import com.memecabin.pro.R;

public class LoginActivity extends Activity {

	public static final int TWITTER_LOGIN_RESULT_CODE_SUCCESS = 1111;
	public static final int TWITTER_LOGIN_RESULT_CODE_FAILURE = 2222;

	private static final String TAG = "LoginActivity";

	private WebView twitterLoginWebView;
	private BusyDialog busyDialog;
	private static String twitterConsumerKey;
	private static String twitterConsumerSecret;

	private static Twitter twitter;
	private static RequestToken requestToken;
	private Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_login);
		con = this;
		twitterConsumerKey = getResources().getString(
				R.string.twitter_consumer_key);
		twitterConsumerSecret = getResources().getString(
				R.string.twitter_consumer_secret);

		if (twitterConsumerKey == null || twitterConsumerSecret == null) {
			Log.e(TAG, "ERROR: Consumer Key and Consumer Secret required!");
			LoginActivity.this.setResult(TWITTER_LOGIN_RESULT_CODE_FAILURE);
			LoginActivity.this.finish();
		}

		final View view = getLayoutInflater().inflate(R.layout.view_loading,
				null);
		((TextView) view.findViewById(R.id.messageTextViewFromLoading))
				.setText(getString(R.string.authenticating_your_app_message));
		busyDialog = new BusyDialog(con, true);
		busyDialog.show();

		twitterLoginWebView = (WebView) findViewById(R.id.twitterLoginWebView);
		twitterLoginWebView.setBackgroundColor(Color.TRANSPARENT);
		twitterLoginWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.contains(AppConstantTwitter.TWITTER_CALLBACK_URL)) {
					final Uri uri = Uri.parse(url);
					LoginActivity.this.saveAccessTokenAndFinish(uri);
					return true;
				}
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if (busyDialog != null) {
					busyDialog.dismis();
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				if (busyDialog != null) {
					busyDialog.dismis();
				}
			}
		});

		Log.d(TAG, "Authorize....");
		askOAuth();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (busyDialog != null) {
			busyDialog.dismis();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void saveAccessTokenAndFinish(final Uri uri) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String verifier = uri
						.getQueryParameter(AppConstantTwitter.IEXTRA_OAUTH_VERIFIER);
				try {
					final SharedPreferences sharedPrefs = getSharedPreferences(
							AppConstantTwitter.SHARED_PREF_NAME,
							Context.MODE_PRIVATE);
					final AccessToken accessToken = twitter
							.getOAuthAccessToken(requestToken, verifier);
					final Editor e = sharedPrefs.edit();
					e.putString(AppConstantTwitter.SHARED_PREF_KEY_TOKEN,
							accessToken.getToken());
					e.putString(AppConstantTwitter.SHARED_PREF_KEY_SECRET,
							accessToken.getTokenSecret());
					e.commit();

					Log.d(TAG,
							"TWITTER LOGIN SUCCESS ----TOKEN "
									+ accessToken.getToken());
					Log.d(TAG, "TWITTER LOGIN SUCCESS ----TOKEN SECRET "
							+ accessToken.getTokenSecret());
					LoginActivity.this
							.setResult(TWITTER_LOGIN_RESULT_CODE_SUCCESS);
				} catch (final Exception e) {
					e.printStackTrace();
					if (e.getMessage() != null) {
						Log.e(TAG, e.getMessage());

					} else {
						Log.e(TAG, "ERROR: Twitter callback failed");
					}
					LoginActivity.this
							.setResult(TWITTER_LOGIN_RESULT_CODE_FAILURE);
				}
				LoginActivity.this.finish();
			}
		}).start();
	}

	public static boolean isActive(Context ctx) {
		final SharedPreferences sharedPrefs = ctx.getSharedPreferences(
				AppConstantTwitter.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		return sharedPrefs.getString(AppConstantTwitter.SHARED_PREF_KEY_TOKEN,
				null) != null;
	}

	public static void logOutOfTwitter(Context ctx) {
		final SharedPreferences sharedPrefs = ctx.getSharedPreferences(
				AppConstantTwitter.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		final Editor e = sharedPrefs.edit();
		e.putString(AppConstantTwitter.SHARED_PREF_KEY_TOKEN, null);
		e.putString(AppConstantTwitter.SHARED_PREF_KEY_SECRET, null);
		e.commit();
	}

	public static String getAccessToken(Context ctx) {
		final SharedPreferences sharedPrefs = ctx.getSharedPreferences(
				AppConstantTwitter.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		return sharedPrefs.getString(AppConstantTwitter.SHARED_PREF_KEY_TOKEN,
				null);
	}

	public static String getAccessTokenSecret(Context ctx) {
		final SharedPreferences sharedPrefs = ctx.getSharedPreferences(
				AppConstantTwitter.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		return sharedPrefs.getString(AppConstantTwitter.SHARED_PREF_KEY_SECRET,
				null);
	}

	private void askOAuth() {
		final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(twitterConsumerKey);
		configurationBuilder.setOAuthConsumerSecret(twitterConsumerSecret);
		final Configuration configuration = configurationBuilder.build();
		twitter = new TwitterFactory(configuration).getInstance();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					requestToken = twitter
							.getOAuthRequestToken(AppConstantTwitter.TWITTER_CALLBACK_URL);
				} catch (final Exception e) {
					final String errorString = e.toString();
					LoginActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (busyDialog != null) {
								busyDialog.dismis();
							}
							Toast.makeText(LoginActivity.this,
									errorString.toString(), Toast.LENGTH_SHORT)
									.show();
							finish();
						}
					});
					return;
				}

				LoginActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						twitterLoginWebView.loadUrl(requestToken
								.getAuthenticationURL());
					}
				});
			}
		}).start();
	}

}
