package com.memecabin.parser;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.aacom.memecabin.utils.AppConstant;
import com.memecabin.holder.AllFavoriteMeme;
import com.memecabin.holder.AllImageInfo;
import com.memecabin.model.ImageInfo;

public class MemeParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		if (result.length() < 1) {
			return false;

		}
		AllImageInfo.removeAllImageInfo();

		final JSONObject json_ob = new JSONObject(result);

		AppConstant.totalEveryoneMeme = json_ob.optInt("total");
		AppConstant.status = json_ob.optString("status");
		AppConstant.imageBaseUrl = json_ob.optString("base_url");
		AppConstant.thumbsMedium = json_ob.optString("thumb250BaseUrl");
		AppConstant.thumbSmall = json_ob.optString("thumb100BaseUrl");

		ImageInfo meme;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.optJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				meme = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.optJSONObject(i);

				meme.setImageID(jsonHolder.optString("id"));
				meme.setImageCategory(jsonHolder.optString("category"));
				meme.setImageUrl(jsonHolder.optString("url"));
				meme.setImageUploadBy(jsonHolder.optString("upload_by"));
				meme.setImageUploadDate(jsonHolder.optString("upload_date"));
				meme.setLikeCount(jsonHolder.optString("like"));
				meme.setViewCount(jsonHolder.optString("view"));
				meme.setStatus(jsonHolder.optString("status"));
				meme.setFavorite(jsonHolder.optString("isMyFavorite"));
				meme.setIsLike(jsonHolder.optString("isMyLike"));
				// meme.setFavorite("1");
				AllImageInfo.setImageInfo(meme);
				meme = null;

			}

		}

		Log.e("Total everyone object ", AllImageInfo.getAllImageInfo().size()
				+ "");

		return true;
	}

	public static boolean parseFavoriteMeme(Context con, String result)
			throws JSONException, IOException {
		if (result.length() < 1) {
			return false;

		}
		AllFavoriteMeme.removeAllImageInfo();

		final JSONObject json_ob = new JSONObject(result);

		AppConstant.status = json_ob.optString("status");
		AppConstant.imageBaseUrl = json_ob.optString("base_url");
		AppConstant.thumbsMedium = json_ob.optString("thumb250BaseUrl");
		AppConstant.thumbSmall = json_ob.optString("thumb100BaseUrl");

		ImageInfo meme;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.optJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				meme = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.optJSONObject(i);

				meme.setImageID(jsonHolder.optString("id"));
				meme.setImageCategory(jsonHolder.optString("category"));
				meme.setImageUrl(jsonHolder.optString("url"));
				meme.setImageUploadBy(jsonHolder.optString("upload_by"));
				meme.setImageUploadDate(jsonHolder.optString("upload_date"));
				meme.setLikeCount(jsonHolder.optString("like"));
				meme.setViewCount(jsonHolder.optString("view"));
				meme.setStatus(jsonHolder.optString("status"));
				meme.setIsLike(jsonHolder.optString("isMyLike"));
				meme.setFavorite("1");
				AllFavoriteMeme.setImageInfo(meme);
				meme = null;

			}

		}

		return true;
	}

	public static boolean parseNext(String response) throws JSONException,
			IOException {
		final boolean isParse = true;
		if (response.length() < 1) {
			return false;

		}
		final Vector<ImageInfo> meme = new Vector<ImageInfo>();

		final JSONObject json_ob = new JSONObject(response);
		AppConstant.totalEveryoneMeme = json_ob.optInt("total");

		AppConstant.status = json_ob.optString("status");
		AppConstant.imageBaseUrl = json_ob.optString("base_url");
		AppConstant.thumbsMedium = json_ob.optString("thumb250BaseUrl");
		AppConstant.thumbSmall = json_ob.optString("thumb100BaseUrl");

		ImageInfo memeMo;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.optJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				memeMo = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.optJSONObject(i);

				memeMo.setImageID(jsonHolder.optString("id"));
				memeMo.setImageCategory(jsonHolder.optString("category"));
				memeMo.setImageUrl(jsonHolder.optString("url"));
				memeMo.setImageUploadBy(jsonHolder.optString("upload_by"));
				memeMo.setImageUploadDate(jsonHolder.optString("upload_date"));
				memeMo.setLikeCount(jsonHolder.optString("like"));
				memeMo.setViewCount(jsonHolder.optString("view"));
				memeMo.setStatus(jsonHolder.optString("status"));
				memeMo.setFavorite(jsonHolder.optString("isMyFavorite"));
				memeMo.setIsLike(jsonHolder.optString("isMyLike"));
				meme.add(memeMo);

				memeMo = null;

			}

			Log.e("new everyone object ", meme.size() + "");

			AllImageInfo.appendAll(meme);
			Log.e("Total everyone object ", AllImageInfo.getAllImageInfo()
					.size() + "");

		}

		return isParse;
	}

}
