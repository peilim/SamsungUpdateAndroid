package com.memecabin.parser;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.aacom.memecabin.utils.AppConstant;
import com.memecabin.holder.AllMotivational;
import com.memecabin.model.ImageInfo;

public class MotivationalParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		if (result.length() < 1) {
			return false;

		}
		AllMotivational.removeAllMotivational();

		final JSONObject json_ob = new JSONObject(result);
		AppConstant.totalMotivationalMeme = json_ob.optInt("total");

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
				AllMotivational.setMotivational(memeMo);

				memeMo = null;

			}

		}

		Log.e("Total moti object ", AllMotivational.getAllMotivational().size()
				+ "");

		return true;
	}

	public static boolean parseNext(String response) throws JSONException,
			IOException {
		final boolean isParse = true;
		if (response.length() < 1) {
			return false;

		}
		final Vector<ImageInfo> allMotivational = new Vector<ImageInfo>();

		final JSONObject json_ob = new JSONObject(response);
		AppConstant.totalMotivationalMeme = json_ob.optInt("total");

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
				allMotivational.add(memeMo);

				memeMo = null;

			}
			Log.e("new moti object ", allMotivational.size() + "");

			AllMotivational.appendAll(allMotivational);
			Log.e("Total moti object ", AllMotivational.getAllMotivational()
					.size() + "");

		}

		return isParse;
	}

}
