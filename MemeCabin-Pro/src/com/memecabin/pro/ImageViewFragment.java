package com.memecabin.pro;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.memecabin.model.ImageInfo;
import com.memecabin.pro.R;

public class ImageViewFragment extends Fragment {
	private Context context;
	int mNum = 0;

	String testUrl = "http://izuera.com/admin/resposive-content/showImage.php?content_id=461";
	private WebView webView;

	// ...........

	public static ImageViewFragment newInstance(int num) {
		final ImageViewFragment imageViewDetailFragment = new ImageViewFragment();
		final Bundle bundle = new Bundle();
		bundle.putInt("num", num);

		imageViewDetailFragment.setArguments(bundle);
		return imageViewDetailFragment;
	}

	ImageInfo query;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		// query = AllImageInfo.getAllImageInfo().elementAt(mNum);
		// print.message("URL : ", query.getImageUrl());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.swaping_img, container,
				false);
		context = getActivity();
		webView = (WebView) view.findViewById(R.id.webview_imagedetails);

		webView.setBackgroundColor(Color.parseColor("#00000000"));
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		// webView.getSettings().setPluginsEnabled(false);
		webView.getSettings().setSupportMultipleWindows(false);
		webView.getSettings().setAppCacheEnabled(true);
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.getSettings()
				.setUserAgentString("silly_that_i_have_to_do_this");

		webView.loadUrl(testUrl);

		return view;
	}
}
