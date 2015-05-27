package com.memecabin.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.aacom.memecabin.utils.AppConstant;
import com.memecabin.holder.AllReload;
import com.memecabin.model.ImageInfo;

public class ReloadlParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		if (result.length() < 1) {
			return false;

		}
		AllReload.removeAllReload();

		final JSONObject json_ob = new JSONObject(result);

		AppConstant.status = json_ob.optString("status");
		AppConstant.imageBaseUrl = json_ob.optString("base_url");

		ImageInfo reload;

		if (AppConstant.status.toString().trim().equalsIgnoreCase("1")) {

			final JSONArray jsonarray = json_ob.optJSONArray("result");

			for (int i = 0; i < jsonarray.length(); i++) {
				reload = new ImageInfo();

				final JSONObject jsonHolder = jsonarray.optJSONObject(i);

				reload.setImageID(jsonHolder.optString("id"));
				reload.setImageCategory(jsonHolder.optString("category"));
				reload.setImageUrl(jsonHolder.optString("url"));
				reload.setImageUploadBy(jsonHolder.optString("upload_by"));
				reload.setImageUploadDate(jsonHolder.optString("upload_date"));
				reload.setLikeCount(jsonHolder.optString("allLike"));
				reload.setDayslikecount(jsonHolder.optString("daytotalLike"));
				reload.setS7daytotalLike(jsonHolder.optString("7daytotalLike"));
				reload.setT30daytotalLike(jsonHolder
						.optString("30daytotalLike"));
				reload.setN90daytotalLike(jsonHolder
						.optString("90daytotalLike"));

				reload.setViewCount(jsonHolder.optString("view"));
				reload.setStatus(jsonHolder.optString("status"));
				AllReload.setReload(reload);

				reload = null;

			}

		}

		return true;
	}

}
