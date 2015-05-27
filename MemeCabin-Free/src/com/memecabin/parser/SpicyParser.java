package com.memecabin.parser;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.aacom.memecabin.utils.AppConstant;
import com.memecabin.holder.AllSpicyInfo;
import com.memecabin.model.ImageInfo;

public class SpicyParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		if (result.length() < 1) {
			return false;

		}
		AllSpicyInfo.removeAllSpicyInfo();

		final JSONObject json_ob = new JSONObject(result);
		AppConstant.totalSpiffyMeme = json_ob.optInt("total");

		AppConstant.status = json_ob.getString("status");
		AppConstant.imageBaseUrl = json_ob.getString("base_url");
		AppConstant.thumbsMedium = json_ob.getString("thumb250BaseUrl");
		AppConstant.thumbSmall = json_ob.getString("thumb100BaseUrl");

		ImageInfo spicy;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.getJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				spicy = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.getJSONObject(i);

				spicy.setImageID(jsonHolder.optString("id"));
				spicy.setImageUrl(jsonHolder.optString("url"));
				spicy.setLikeCount(jsonHolder.optString("like"));
				spicy.setTitle(jsonHolder.optString("title"));
				spicy.setIsLike(jsonHolder.optString("isMyLike"));
				spicy.setImageUploadDate(jsonHolder.optString("upload_date"));
				spicy.setStatus(jsonHolder.optString("status"));
				AllSpicyInfo.setImageInfo(spicy);
				spicy = null;

			}

			Log.e("Total spicy object ", AllSpicyInfo.getAllSpicyInfo().size()
					+ "");

		}

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
		AppConstant.totalSpiffyMeme = json_ob.optInt("total");

		AppConstant.status = json_ob.getString("status");
		AppConstant.imageBaseUrl = json_ob.getString("base_url");
		AppConstant.thumbsMedium = json_ob.getString("thumb250BaseUrl");
		AppConstant.thumbSmall = json_ob.getString("thumb100BaseUrl");

		ImageInfo memeMo;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.getJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				memeMo = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.getJSONObject(i);

				memeMo.setImageID(jsonHolder.getString("id"));
				memeMo.setImageCategory(jsonHolder.getString("category"));
				memeMo.setImageUrl(jsonHolder.getString("url"));
				memeMo.setImageUploadBy(jsonHolder.getString("upload_by"));
				memeMo.setImageUploadDate(jsonHolder.getString("upload_date"));
				memeMo.setLikeCount(jsonHolder.getString("like"));
				memeMo.setViewCount(jsonHolder.getString("view"));
				memeMo.setStatus(jsonHolder.getString("status"));
				memeMo.setFavorite(jsonHolder.optString("isMyFavorite"));
				memeMo.setIsLike(jsonHolder.optString("isMyLike"));
				allMotivational.add(memeMo);

				memeMo = null;

			}
			Log.e("new racy object ", allMotivational.size() + "");

			AllSpicyInfo.appendAll(allMotivational);
			Log.e("Total spicy object ", AllSpicyInfo.getAllSpicyInfo().size()
					+ "");

		}

		return isParse;
	}

}
