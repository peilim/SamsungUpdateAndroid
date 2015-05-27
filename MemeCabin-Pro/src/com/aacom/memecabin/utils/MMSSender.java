/**
 *
 */
package com.aacom.memecabin.utils;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @author Ashraful
 * 
 */
public class MMSSender {

	public static void mmsImageToAll(Context con, String text, File f) {

		if (TextUtils.isEmpty(text)) {
			text = "Just wanted to share this meme from the MemeCabin app!";
		}
		try {
			final Uri imgUri = Uri.fromFile(f);
			final Intent sendIntent = new Intent(Intent.ACTION_SEND);
			// sendIntent.setClassName("com.android.mms",
			// "com.android.mms.ui.ComposeMessageActivity");
			sendIntent.putExtra("sms_body", text);
			sendIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
			sendIntent.setType("image/*");
			con.startActivity(Intent.createChooser(sendIntent, "Send"));

		} catch (final ActivityNotFoundException e) {
			// TODO: handle exception

			final Intent intent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("mmsto:"));
			intent.putExtra("sms_body", text);
			intent.putExtra(Intent.EXTRA_STREAM,
					Uri.parse("file://" + f.getAbsolutePath()));
			intent.setType("image/*");

			con.startActivity(intent);

		} catch (final Exception e) {
			// TODO: handle exception

			Toast.makeText(con, "No client is installed", 500).show();
		}
	}

	public static void mmsImageToAllWithoutImage(Context con, String text) {

		if (TextUtils.isEmpty(text)) {
			text = "Just wanted to share this meme from the MemeCabin app!";
		}
		try {

			final Intent sendIntent = new Intent(Intent.ACTION_SEND);
			// sendIntent.setClassName("com.android.mms",
			// "com.android.mms.ui.ComposeMessageActivity");
			sendIntent.putExtra("sms_body", text);

			sendIntent.setType("text/*");
			con.startActivity(Intent.createChooser(sendIntent, "Send"));

		} catch (final ActivityNotFoundException e) {
			// TODO: handle exception

			final Intent intent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("mmsto:"));
			intent.putExtra("sms_body", text);
			// intent.putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("file://" + f.getAbsolutePath()));
			intent.setType("text/*");

			con.startActivity(intent);

		} catch (final Exception e) {
			// TODO: handle exception

			Toast.makeText(con, "No client is installed", 500).show();
		}
	}

}
