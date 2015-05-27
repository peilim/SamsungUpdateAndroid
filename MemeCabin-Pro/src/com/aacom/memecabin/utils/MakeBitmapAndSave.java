package com.aacom.memecabin.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.widget.Toast;

import com.aapbd.utils.image.SaveAndShareImage;
import com.memecabin.pro.R;

public class MakeBitmapAndSave {

	public static Bitmap createMyBitmap(Context con, Bitmap bmp) {
		/*
		 * v.setDrawingCacheEnabled(true); final Bitmap bitmap =
		 * Bitmap.createBitmap(v.getDrawingCache());
		 * v.setDrawingCacheEnabled(false); saveBitmapFile(bitmap);
		 */

		// Save custome image with canvas
		int topBorder = 20;
		// Left border
		int leftBorder = 20;
		// Right border
		int rightBorder = 20;
		// Side border height
		int sideBorderHeight;
		// Offset for bitmap calculation
		float bitmapOffset = 1.0f;
		// Maximum image height or width
		final int maxLimit = 1000;
		// Minimum image height or width
		final int minLimit = 400;
		// Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.bottom_frame);
		// Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.meme_bottom_frame_b);

		// Actual width of Bitmap
		final int bitmapWidth = bmp.getWidth();
		// Actual height of Bitmap
		final int bitmapHeight = bmp.getHeight();

		Bitmap logoBitmap;

		if (bitmapWidth > 600) {

			logoBitmap = BitmapFactory.decodeResource(con.getResources(),
					R.drawable.meme_bottom_frame_b);
		} else if (bitmapWidth <= 600 && bitmapWidth >= 300) {
			logoBitmap = BitmapFactory.decodeResource(con.getResources(),
					R.drawable.meme_bottom_frame_c);
		} else {
			logoBitmap = BitmapFactory.decodeResource(con.getResources(),
					R.drawable.meme_bottom_frame_d);
		}
		// Calculated width of Bitmap
		int bitmapNewWidth;
		// Calculated height of Bitmap
		int bitmapNewHeight;
		// Calculated logo width
		int logoNewWidth;
		// Calculated logo height
		int logoNewHeight;
		// Actual logo width
		final int logoWidth = logoBitmap.getWidth();
		// Actual logo height
		final int logoHeight = logoBitmap.getHeight();
		// Start top position of canvas to draw bitmap
		int mainBmpLeftPos;
		// Start left position of canvas to draw bitmap
		int mainBmpTopPos;
		// Start left position of canvas to draw logo
		int logoLeftPos;
		// Start top position of canvas to draw logo
		int logoTopPos;
		// Total canvas Width
		int canvasWidth;
		// Total canvas height
		int canvasHeight;

		// Do the calculation
		if (bitmapWidth > bitmapHeight) { // check if the image is in landscape
											// mode
			if (bitmapWidth > maxLimit) { // Check if needed to scale down. No
											// need to scale up
				bitmapOffset = (float) bitmapWidth / maxLimit;
			}
		} else { // If image is in portrait mode or a square image
			if (bitmapHeight > maxLimit) { // check if needed to scale down. No
											// need to scale up
				bitmapOffset = (float) bitmapHeight / maxLimit;
			}
		}
		// Scaling down (or not) the actual image
		bitmapNewHeight = (int) (bitmapHeight / bitmapOffset);
		bitmapNewWidth = (int) (bitmapWidth / bitmapOffset);

		// if (bitmapNewHeight <= minLimit) {
		// topBorder = 10;
		// }
		// if (bitmapNewWidth <= minLimit) {
		// leftBorder = 10;
		// rightBorder = 10;
		// }

		if (bitmapNewHeight <= minLimit && bitmapNewWidth <= minLimit) {
			topBorder = 10;
			leftBorder = 10;
			rightBorder = 10;
		}

		// Scale down the bottom logo based on image new width
		bitmapOffset = (float) logoWidth / bitmapNewWidth;
		logoNewWidth = (int) (logoWidth / bitmapOffset);
		logoNewHeight = (int) (logoHeight / bitmapOffset);
		logoNewWidth += leftBorder + rightBorder + 2;

		// Calculating canvas size
		canvasWidth = leftBorder + bitmapNewWidth + rightBorder;
		canvasHeight = topBorder + bitmapNewHeight + logoNewHeight;

		sideBorderHeight = topBorder + bitmapNewHeight;

		// Positioning Bitmap and Bottom Logo
		mainBmpLeftPos = leftBorder;
		mainBmpTopPos = topBorder;
		logoLeftPos = 0;
		logoTopPos = topBorder + bitmapNewHeight;
		// bmp = Bitmap.createBitmap(bmp, 0, 0, bitmapNewWidth,
		// bitmapNewHeight);
		// Creating the bitmap and bottom logo with new scaled value
		bmp = Bitmap.createScaledBitmap(bmp, bitmapNewWidth, bitmapNewHeight,
				false);
		// logoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoNewWidth,
		// logoNewHeight);
		logoBitmap = Bitmap.createScaledBitmap(logoBitmap, logoNewWidth,
				logoNewHeight, false);

		// Start drawing on canvas
		final Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT); // Set background to transparent
		final Bitmap bg = Bitmap.createBitmap(canvasWidth, canvasHeight,
				Bitmap.Config.ARGB_8888); // Creating mutable bitmap to save
											// with proper size
		final Canvas canvas = new Canvas(bg); // Canvas to be used to hold the
												// mutable bitmap
		canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint); // Draw
																	// background
		paint.setColor(Color.BLACK); // Set background for border black
		canvas.drawRect(0, 0, leftBorder, sideBorderHeight, paint); // Draw left
																	// border
		canvas.drawRect(0, 0, canvasWidth, topBorder, paint); // Draw top border
		canvas.drawRect(leftBorder + bitmapNewWidth, 0, canvasWidth,
				sideBorderHeight, paint); // Draw right border
		canvas.drawBitmap(bmp, mainBmpLeftPos, mainBmpTopPos, null); // Draw
																		// actual
																		// bitmap
		canvas.drawBitmap(logoBitmap, logoLeftPos, logoTopPos, null); // Draw
																		// bottom
																		// logo

		// Save the mutable bitmap to SDCard
		return bg;

		// bmp.recycle();
		// logoBitmap.recycle();

	}

	public static void saveBitmapToDevice(Context con, Bitmap bitmap) {
		File saveFile;
		final long timestr = System.currentTimeMillis();
		final File MEMECABIN = new File(Environment
				.getExternalStorageDirectory().toString()
				+ File.separator
				+ "MEMECABIN");
		if (!MEMECABIN.exists()) {
			MEMECABIN.mkdir();
		}
		try {
			// String dirPath = seeThatDir.toString();
			saveFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/MEMECABIN" + "/" + timestr + "meme.jpg");

			FileOutputStream ostream = null;
			try {
				ostream = new FileOutputStream(saveFile);

				bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
				ostream.flush();
				ostream.close();
				SaveAndShareImage.galleryAddPic(saveFile, con);
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		// bitmap.recycle();
		Toast.makeText(con, "Image is saved successfully", 500).show(); // screenimageframe_rl.setVisibility(View.GONE);
	}

	public static String saveGIFToDevice(Context con, byte[] data) {
		File saveFile;
		final long timestr = System.currentTimeMillis();
		final File MEMECABIN = new File(Environment
				.getExternalStorageDirectory().toString()
				+ File.separator
				+ "MEMECABIN");
		if (!MEMECABIN.exists()) {
			MEMECABIN.mkdir();
		}
		try {
			// String dirPath = seeThatDir.toString();
			saveFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/MEMECABIN" + "/" + timestr + "meme.gif");

			FileOutputStream ostream = null;
			try {
				ostream = new FileOutputStream(saveFile);

				ostream.write(data);

				ostream.flush();
				ostream.close();

				SaveAndShareImage.galleryAddPic(saveFile, con);

				return saveFile.getAbsolutePath();

			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		return "";
		// bitmap.recycle();
		// screenimageframe_rl.setVisibility(View.GONE);
	}

	public static String saveBitmapPathToShare(Bitmap bitmap,
			final String sharetype) {
		File saveFile = null;
		final File MEMECABIN = new File(Environment
				.getExternalStorageDirectory().toString()
				+ File.separator
				+ "MEMECABIN");
		if (!MEMECABIN.exists()) {
			MEMECABIN.mkdir();
		}
		try {
			// String dirPath = seeThatDir.toString();

			if (sharetype.equalsIgnoreCase("mms")) {
				final long timein = System.currentTimeMillis();

				saveFile = new File(Environment.getExternalStorageDirectory()
						.getPath() + "/MEMECABIN" + "/" + timein + "meme.jpg");
			} else {
				saveFile = new File(Environment.getExternalStorageDirectory()
						.getPath() + "/MEMECABIN" + "/" + "meme.jpg");
			}

			FileOutputStream ostream = null;
			try {
				ostream = new FileOutputStream(saveFile);

				bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
				ostream.flush();
				ostream.close();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		return saveFile.getAbsolutePath();

	}

}
