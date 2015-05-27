/**
 *
 */
package com.aacom.memecabin.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Ashraful
 * 
 */
public class CustomImageViewLargeHeight extends ImageView {

	public CustomImageViewLargeHeight(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomImageViewLargeHeight(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomImageViewLargeHeight(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// System.gc();

		final int height = MeasureSpec.getSize(heightMeasureSpec);
		final int width = height * getDrawable().getIntrinsicWidth()
				/ getDrawable().getIntrinsicHeight();
		setMeasuredDimension(width, height);

	}
}
