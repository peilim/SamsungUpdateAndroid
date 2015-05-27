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
public class CustomImageViewLargeWidth extends ImageView {

	public CustomImageViewLargeWidth(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomImageViewLargeWidth(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomImageViewLargeWidth(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// System.gc();
		//
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int height = width * getDrawable().getIntrinsicHeight()
				/ getDrawable().getIntrinsicWidth();
		setMeasuredDimension(width, height);
	}
}
