//package com.memecabin.facebook;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//
//import com.facebook.android.AsyncFacebookRunner;
//import com.facebook.android.DialogError;
//import com.facebook.android.Facebook;
//import com.facebook.android.Facebook.DialogListener;
//import com.facebook.android.FacebookError;
//import com.facebook.android.Util;
//import com.memecabin.facebook.SessionEvents.AuthListener;
//import com.memecabin.facebook.SessionEvents.LogoutListener;
//
//public class FacebookConnector {
//
//	private Facebook facebook = null;
//	private final Context context;
//	private final String[] permissions;
//	private final Handler mHandler;
//	private final Activity activity;
//	private final SessionListener mSessionListener = new SessionListener();
//
//	// public TestSuccess tsuc;
//
//	public FacebookConnector(String appId, Activity activity, Context context,
//			String[] permissions) {
//		this.facebook = new Facebook(appId);
//
//		SessionStore.restore(facebook, context);
//		SessionEvents.addAuthListener(mSessionListener);
//		SessionEvents.addLogoutListener(mSessionListener);
//
//		this.context = context;
//		this.permissions = permissions;
//		this.mHandler = new Handler();
//		this.activity = activity;
//	}
//
//	public void login() {
//		if (!facebook.isSessionValid()) {
//			facebook.authorize(activity, permissions, new LoginDialogListener());
//		}
//	}
//
//	public void logout() {
//		SessionEvents.onLogoutBegin();
//		final AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(
//				this.facebook);
//		asyncRunner.logout(this.context, new LogoutRequestListener());
//	}
//
//	// public void postMessageOnWall(String msg) {
//	// if (facebook.isSessionValid()) {
//	//
//	// final Bundle parameters = new Bundle();
//	// parameters.putString("message", msg);
//	// try {
//	// final String response = facebook.request("me/feed", parameters,
//	// "POST");
//	// System.out.println(response);
//	// } catch (final IOException e) {
//	// e.printStackTrace();
//	// }
//	// }
//	// }
//
//	public boolean facebookImagePostWithoutImage(String msg) {
//
//		Log.e("call ", ">>>>" + facebook.isSessionValid());
//		Log.e("permission ", ">>>>"
//				+ facebook.getSession().getPermissions().toString());
//
//		boolean flag = false;
//		if (facebook.isSessionValid()) {
//
//			final Bundle params = new Bundle();
//			params.putString("message", msg);
//			// params.putByteArray("picture", data);
//
//			String strRet = "";
//			try {
//				strRet = facebook.request("me/feed", params, "POST");
//
//				flag = true;
//
//			} catch (final FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (final MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (final IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Log.e("Facebook res", ">>." + strRet);
//
//		}
//
//		return flag;
//	}
//
//	public boolean facebookImagePost(byte[] biteVal, String msg,
//			String caption, String description) {
//
//		Log.e("call ", ">>>>" + facebook.isSessionValid());
//		Log.e("permission ", ">>>>"
//				+ facebook.getSession().getPermissions().toString());
//
//		boolean flag = false;
//		if (facebook.isSessionValid()) {
//
//			byte[] data = null;
//			try {
//
//				data = biteVal;
//			} catch (final Exception e) {
//				e.printStackTrace();
//			}
//			final Bundle params = new Bundle();
//			params.putString("message", msg);
//			params.putByteArray("picture", data);
//
//			String strRet = "";
//			try {
//				strRet = facebook.request("me/photos", params, "POST");
//
//				flag = true;
//
//			} catch (final FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (final MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (final IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Log.e("Facebook res", ">>." + strRet);
//
//		}
//
//		return flag;
//	}
//
//	/*
//	 * public String getEmail() {
//	 * 
//	 * String email="";
//	 * 
//	 * if (facebook.isSessionValid()) { final Bundle parameters = new Bundle();
//	 * parameters.putString("message", msg); try { final String response =
//	 * facebook.request("me/feed", parameters, "POST");
//	 * System.out.println(response); } catch (final IOException e) {
//	 * e.printStackTrace(); } } else { login(); }
//	 * 
//	 * return email; }
//	 */
//
//	public String getMyfbId() {
//
//		String email = null;
//
//		String myfbID = null;
//
//		Log.e("login permission ", ">>>>"
//				+ facebook.getSession().getPermissions());
//
//		try {
//			final JSONObject json = Util.parseJson(facebook.request("me"));
//			myfbID = json.getString("id");
//
//			final String firstName = json.getString("first_name");
//			final String lastName = json.getString("last_name");
//			email = json.getString("email");
//
//			Log.e("facebookID : firstName : lastName : email", myfbID
//
//			+ " : " + firstName + " : " + lastName + " : " + email);
//
//		} catch (final MalformedURLException e) {
//			e.printStackTrace();
//		} catch (final JSONException e) {
//			e.printStackTrace();
//		} catch (final IOException e) {
//			e.printStackTrace();
//		} catch (final FacebookError e) {
//			e.printStackTrace();
//		}
//
//		return myfbID;
//
//	}
//
//	private final class LoginDialogListener implements DialogListener {
//		@Override
//		public void onComplete(Bundle values) {
//			SessionEvents.onLoginSuccess();
//		}
//
//		@Override
//		public void onFacebookError(FacebookError error) {
//			SessionEvents.onLoginError(error.getMessage());
//		}
//
//		@Override
//		public void onError(DialogError error) {
//			SessionEvents.onLoginError(error.getMessage());
//		}
//
//		@Override
//		public void onCancel() {
//			SessionEvents.onLoginError("Action Canceled");
//		}
//	}
//
//	public class LogoutRequestListener extends BaseRequestListener {
//		@Override
//		public void onComplete(String response, final Object state) {
//			// callback should be run in the original thread,
//			// not the background thread
//			mHandler.post(new Runnable() {
//				@Override
//				public void run() {
//					SessionEvents.onLogoutFinish();
//				}
//			});
//		}
//	}
//
//	private class SessionListener implements AuthListener, LogoutListener {
//
//		@Override
//		public void onAuthSucceed() {
//			SessionStore.save(facebook, context);
//		}
//
//		@Override
//		public void onAuthFail(String error) {
//		}
//
//		@Override
//		public void onLogoutBegin() {
//		}
//
//		@Override
//		public void onLogoutFinish() {
//			SessionStore.clear(context);
//		}
//	}
//
//	public Facebook getFacebook() {
//		return this.facebook;
//	}
// }
