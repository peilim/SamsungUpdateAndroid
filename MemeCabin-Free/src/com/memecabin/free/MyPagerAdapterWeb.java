package com.memecabin.free;

import java.util.Vector;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AppConstant;
import com.aacom.memecabin.utils.SharedPreferencesHelper;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.memecabin.model.ImageInfo;

public class MyPagerAdapterWeb extends PagerAdapter {

	private final Context context;
	private final Vector<ImageInfo> spicy;
	private LayoutInflater inflater;

	private final View memeswip;
	Animation animationFadeIn;
	Animation animationFadeOut;
	View bookmark;

	public MyPagerAdapterWeb(Context con, Vector<ImageInfo> spicy,
			View swipeHead_ll, View bookmark) {

		this.context = con;
		this.bookmark = bookmark;

		final ImageInfo lastItem = new ImageInfo();
		lastItem.imageUrl = "";
		spicy.add(lastItem);
		this.spicy = spicy;
		this.memeswip = swipeHead_ll;

	}

	@Override
	public int getCount() {
		return spicy.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View viewLayout = inflater.inflate(
				R.layout.layout_fullscreen_imageweb, container, false);

		/*
		 * image view part
		 */

		final TouchImageView imgDisplay = (TouchImageView) viewLayout
				.findViewById(R.id.gifimageview);
		final ImageView imgDisplayLoader = (ImageView) viewLayout
				.findViewById(R.id.gifimageviewloader);

		// imgDisplay.setZoom(.75f);
		// imgDisplay.TouchIn(vi);
		Log.e("adapter position", ": " + position);

		// imgDisplay.set

		final String imagewebURL = AppConstant.imageBaseUrl
				+ spicy.elementAt(position).getImageUrl();

		Log.e("Image URL ", ": " + imagewebURL);

		animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fadein);
		animationFadeOut = AnimationUtils
				.loadAnimation(context, R.anim.fadeout);

		/*
		 * place holder image
		 */
		Ion.with(imgDisplayLoader)
				// .placeholder(R.drawable.loadingsmall)

				.animateLoad(animationFadeIn).animateIn(animationFadeOut)
				.load("file:///android_asset/loadingsmall.gif");

		//
		// final BusyDialog busy=new BusyDialog(context, true);
		// busy.show();

		/*
		 * actual image
		 */
		//
		Ion.with(imgDisplay)
				// .placeholder(R.drawable.loadingsmall)

				.animateLoad(animationFadeIn).animateIn(animationFadeOut)
				.load(imagewebURL).setCallback(new FutureCallback<ImageView>() {

					@Override
					public void onCompleted(Exception arg0, ImageView arg1) {
						// TODO Auto-generated method stub

						imgDisplayLoader.setVisibility(View.GONE);
						// if(busy!=null)
						// {
						// busy.dismis();
						// }

					}
				});

		imgDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (SharedPreferencesHelper.getBookMarkMessate(context)) {
					if (memeswip.getVisibility() == View.VISIBLE) {

						memeswip.setVisibility(View.GONE);
						memeswip.startAnimation(animationFadeOut);

					} else {
						memeswip.setVisibility(View.VISIBLE);
						memeswip.startAnimation(animationFadeIn);
					}
				} else {
					bookmark.setVisibility(View.GONE);
					SharedPreferencesHelper.setBookMarkMessate(context, true);

				}

			}
		});

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((RelativeLayout) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (RelativeLayout) object;
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}