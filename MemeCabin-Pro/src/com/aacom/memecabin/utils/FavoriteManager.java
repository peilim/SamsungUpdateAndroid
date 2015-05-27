package com.aacom.memecabin.utils;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.memecabin.db.DatabaseHandler;
import com.memecabin.model.ImageInfo;
import com.memecabin.pro.R;

public class FavoriteManager {

	DatabaseHandler db;
	Context con;

	public FavoriteManager(Context con) {
		this.con = con;
		db = new DatabaseHandler(this.con);
	}

	public void addToFavoriteOnline(final ImageInfo img) {
		// Check for net

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, con.getString(R.string.Status),
					con.getString(R.string.checkInternet));
			return;
		}

		// Call api
		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();
		final String url = AllURL.makeFavorites(AppConstant.userID,
				img.getImageID());
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				if (busyNow != null) {
					busyNow.dismis();
				}

				try {
					final JSONObject json = new JSONObject(new String(response));
					final String status = json.optString("status");
					final String msg = json.optString("message");
					if (status.equals("1")) {
						System.out.println(msg
								+ ": Meme add to favorite success");
						db.setOnlineFav(img);
					} else {
						System.out.println(msg
								+ ": Add meme to favorite failed.");
					}
				} catch (final Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if (busyNow != null) {
					busyNow.dismis();
				}
			}
		});
	}

	public void removeFromFavoriteOnline(final ImageInfo img) {
		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, con.getString(R.string.Status),
					con.getString(R.string.checkInternet));
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
				if (busyNow != null) {
					busyNow.dismis();
				}

				try {
					final JSONObject json = new JSONObject(new String(response));
					final String status = json.optString("status");
					final String msg = json.optString("message");
					if (status.equals("1")) {

						System.out.println(msg + ": Meme unfavorite success");
						// AllFavoriteMeme.removeFromID(img);
						// db.setOnlineUnFav(img);
					} else {
						System.out.println(msg + ": unfavorite failed.");
					}
				} catch (final Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if (busyNow != null) {
					busyNow.dismis();
				}
			}
		});
	}

}
