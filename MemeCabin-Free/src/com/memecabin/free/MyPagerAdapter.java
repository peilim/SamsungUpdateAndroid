package com.memecabin.free;

import java.util.Vector;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
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
import android.widget.RelativeLayout;

import com.memecabin.free.R;
import com.aacom.memecabin.utils.AppConstant;
import com.aapbd.interfaceclass.ViewInvisible;
import com.memecabin.model.ImageInfo;
import com.squareup.picasso.Picasso;

public class MyPagerAdapter extends PagerAdapter {

	private final Context context;
	private final Vector<ImageInfo> imageInfos;
	private LayoutInflater inflater;
	ViewInvisible vi;
	// ...............

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF startPoint = new PointF();
	PointF midPoint = new PointF();
	float oldDist = 1f;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	private final View memeswip;
	Animation animationFadeIn;
	Animation animationFadeOut;

	int newScreenWidth = 0;
	int newScreenHeight = 0;

	// ...........

	public MyPagerAdapter(Context con, Vector<ImageInfo> imageInfos,
			View swipeHead_ll) {

		// newScreenWidth= AppConstant.minScreenWidth;
		// newScreenHeight= AppConstant.minScreenHeight;
		//
		//
		this.context = con;
		this.vi = vi;
		// Test
		// imageInfos.clone()
		// final ImageInfo lastItem = new ImageInfo();
		// lastItem.imageUrl = "";
		// imageInfos.add(lastItem);
		this.imageInfos = imageInfos;
		this.memeswip = swipeHead_ll;
		// Test
		// this.imageInfos.addElement(imageInfos.get(imageInfos.size() - 1));
	}

	@Override
	public int getCount() {
		return imageInfos.size();
	}

	/**
	 * Create the page for the given position. The adapter is responsible for
	 * adding the view to the container given here, although it only must ensure
	 * this is done by the time it returns from {@link #finishUpdate()}.
	 * 
	 * @param container
	 *            The containing View in which the page will be shown.
	 * @param position
	 *            The page position to be instantiated.
	 * @return Returns an Object representing the new page. This does not need
	 *         to be a View, but can be some other container of the page.
	 */

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		TouchImageView imgDisplay;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View viewLayout = inflater.inflate(
				R.layout.layout_fullscreen_image, container, false);

		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		imgDisplay.setZoom(.75f);
		// imgDisplay.TouchIn(vi);
		Log.e("adapter position", ": " + position);

		// imgDisplay.set

		final String imageURL = AppConstant.imageBaseUrl
				+ imageInfos.elementAt(position).getImageUrl();

		Log.e("Image URL ", ": " + imageURL);

		Picasso.with(context).load(imageURL).skipMemoryCache().into(imgDisplay);

		// print.message("image width and height ",
		// newScreenWidth+" "+newScreenHeight);
		//
		//
		// Picasso.with(context)
		// .load(imageURL).resize(newScreenWidth, newScreenHeight)
		// .skipMemoryCache()
		// .into(imgDisplay);

		animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fadein);
		animationFadeOut = AnimationUtils
				.loadAnimation(context, R.anim.fadeout);

		/*
		 * RequestCreator rc =
		 * Picasso.with(context).load(AppConstant.imageBaseUrl +
		 * imageInfos.elementAt(position)); rc.placeholder(R.drawable.refreshg);
		 * rc.error(R.drawable.cross); rc.skipMemoryCache();
		 * rc.into(imgDisplay);
		 */

		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// Bitmap bitmap =
		// BitmapFactory.decodeFile(imageInfos.get(position).getImageUrl(),
		// options);

		((ViewPager) container).addView(viewLayout);

		imgDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (memeswip.getVisibility() == View.VISIBLE) {

					memeswip.setVisibility(View.GONE);
					memeswip.startAnimation(animationFadeOut);

				} else {
					memeswip.setVisibility(View.VISIBLE);
					memeswip.startAnimation(animationFadeIn);
				}

			}
		});

		return viewLayout;
	}

	/*
	 * @Override public Object instantiateItem(View collection, int position) {
	 * 
	 * TouchImageView imgDisplay;
	 * 
	 * inflater = (LayoutInflater)
	 * context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); View
	 * viewLayout = inflater.inflate(R.layout.layout_fullscreen_image,
	 * collection, false);
	 * 
	 * imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
	 * 
	 * final ImageView imageView = new ImageView(context);
	 * imageView.setScaleType(ScaleType.MATRIX); Picasso.with(context)
	 * .load(AppConstant.imageBaseUrl +
	 * imageInfos.get(position).getImageUrl()).fit()
	 * .skipMemoryCache().into(imageView);
	 * 
	 * ((ViewPager) collection).addView(imageView, 0);
	 * imageView.setOnTouchListener(new View.OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) {
	 * 
	 * // Toast.makeText(context, "Touch Method:", 3000).show();
	 * 
	 * final ImageView view = (ImageView) v; System.out.println("matrix=" +
	 * savedMatrix.toString()); switch (event.getAction() &
	 * MotionEvent.ACTION_MASK) { case MotionEvent.ACTION_DOWN:
	 * savedMatrix.set(matrix); startPoint.set(event.getX(), event.getY()); mode
	 * = DRAG; break; case MotionEvent.ACTION_POINTER_DOWN: oldDist =
	 * spacing(event); if (oldDist > 10f) { savedMatrix.set(matrix);
	 * midPoint(midPoint, event); mode = ZOOM; } break; case
	 * MotionEvent.ACTION_UP: case MotionEvent.ACTION_POINTER_UP: mode = NONE;
	 * break; case MotionEvent.ACTION_MOVE: if (mode == DRAG) {
	 * matrix.set(savedMatrix); matrix.postTranslate(event.getX() -
	 * startPoint.x, event.getY() - startPoint.y); } else if (mode == ZOOM) {
	 * final float newDist = spacing(event); if (newDist > 10f) {
	 * matrix.set(savedMatrix); final float scale = newDist / oldDist;
	 * matrix.postScale(scale, scale, midPoint.x, midPoint.y); } } break; }
	 * view.setImageMatrix(matrix); return true; }
	 * 
	 * private float spacing(MotionEvent event) { final float x = event.getX(0)
	 * - event.getX(1); final float y = event.getY(0) - event.getY(1); return
	 * FloatMath.sqrt(x * x + y * y); }
	 * 
	 * private void midPoint(PointF point, MotionEvent event) { final float x =
	 * event.getX(0) + event.getX(1); final float y = event.getY(0) +
	 * event.getY(1); point.set(x / 2, y / 2); } });
	 * 
	 * return imageView; }
	 */

	/**
	 * Remove a page for the given position. The adapter is responsible for
	 * removing the view from its container, although it only must ensure this
	 * is done by the time it returns from {@link #finishUpdate()}.
	 * 
	 * @param container
	 *            The containing View from which the page will be removed.
	 * @param position
	 *            The page position to be removed.
	 * @param object
	 *            The same object that was returned by
	 *            {@link #instantiateItem(View, int)}.
	 */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((RelativeLayout) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (RelativeLayout) object;
	}

	/**
	 * Called when the a change in the shown pages has been completed. At this
	 * point you must ensure that all of the pages have actually been added or
	 * removed from the container as appropriate.
	 * 
	 * @param container
	 *            The containing View which is displaying this adapter's page
	 *            views.
	 */
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