package com.memecabin.pro;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aacom.memecabin.utils.AllURL;
import com.aacom.memecabin.utils.AppConstant;
import com.aapbd.utils.network.NetInfo;
import com.aapbd.utils.notification.AlertMessage;
import com.aapbd.utils.notification.BusyDialog;
import com.aapbd.utils.validation.ValidateEmail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.memecabin.pro.R;

public class ReportActivity extends Activity implements OnClickListener {
	private Context con;

	public static ReportActivity instance;

	public static ReportActivity getInstance() {
		return instance;
	}

	TextView reportTitle, topTxt;
	EditText reportName, reportEmail, report;
	String email, name, reportTxt;
	TouchImageView reportImage;
	CheckBox reportCheckBox;
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report_new);
		con = this;

		instance = this;

		initUI();

	}

	private void initUI() {
		reportCheckBox = (CheckBox) findViewById(R.id.reportCheckBox);
		reportImage = (TouchImageView) findViewById(R.id.reportImage);
		report = (EditText) findViewById(R.id.report);
		reportEmail = (EditText) findViewById(R.id.reportEmail);
		reportName = (EditText) findViewById(R.id.reportName);
		topTxt = (TextView) findViewById(R.id.topTxt);
		reportTitle = (TextView) findViewById(R.id.reportTitle);
		webView = (WebView) findViewById(R.id.gifwebview);

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

		webView.setInitialScale(30);
		webView.getSettings().setUseWideViewPort(true);

		final String imagewebURL = AppConstant.imageBaseUrl + AppConstant.image;

		final Typeface tf = Typeface.createFromAsset(getAssets(),
				"fond/ofront.otf");
		reportTitle.setTypeface(tf);
		topTxt.setTypeface(tf);

		if (AppConstant.spiffy.equalsIgnoreCase("spiffy")) {
			// set web view
			reportImage.setVisibility(View.GONE);

			final String data = "<body><center><img  src=\"" + imagewebURL
					+ "\"  width=\"100%\" /></center></body></html>";

			Log.e("Image URL ", ": " + imagewebURL);

			webView.loadData(data, "text/html", null);

		} else {
			webView.setVisibility(View.GONE);
			Picasso.with(con)
					.load(AppConstant.imageBaseUrl + AppConstant.image)
					.skipMemoryCache().into(reportImage);
		}

	}

	@Override
	public void onClick(View v) {

	}

	public void backBtn(View v) {
		finish();
	}

	public void reportSubmit(View v) {

		checkData();

	}

	private void checkData() {

		if (TextUtils.isEmpty(reportName.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterName));
			return;
		} else if (TextUtils.isEmpty(reportEmail.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterEmail));
			return;
		} else if (!ValidateEmail.validateEmail(reportEmail.getText()
				.toString())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterValidEmail));
			return;
		} else if (TextUtils.isEmpty(report.getText())) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.PleaseEnterReport));
			return;

		} else if (reportCheckBox.isChecked() == false) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.reportcheckerror));

			return;
		} else {
			email = reportEmail.getText().toString();
			name = reportName.getText().toString();
			reportTxt = report.getText().toString();

			// String gifurl =
			// reportTxt+AppConstant.imageBaseUrl+AppConstant.image;
			final String url = AllURL.getReportUrl(email, name,
					AppConstant.imageid, reportTxt);

			// String gifurl =
			// reportTxt+AppConstant.imageBaseUrl+AppConstant.image;
			// String gifurl = reportTxt;
			// final String url = AllURL.getReportUrl(email,
			// name,AppConstant.imageid, gifurl);

			loadReport(url);
		}
	}

	protected void loadReport(final String url) {
		// TODO Auto-generated method stub

		if (!NetInfo.isOnline(con)) {
			AlertMessage.showMessage(con, getString(R.string.Status),
					getString(R.string.checkInternet));
			return;
		}

		final BusyDialog busyNow = new BusyDialog(con, true);
		busyNow.show();

		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				if (busyNow != null) {
					busyNow.dismis();
				}

				try {
					final JSONObject json = new JSONObject(new String(response));
					Log.e("Report ", ">>" + new String(response));
					final String status = json.optString("status");

					if (status.equalsIgnoreCase("1")) {
						reportName.setText("");
						reportEmail.setText("");
						report.setText("");
						webView.loadUrl("");

						AlertMessage.showMessage(con,
								getString(R.string.Status),
								getString(R.string.removeMeme));

					} else {

						AlertMessage.showMessage(con,
								getString(R.string.Status),
								"Report Submit Failed");

					}

				} catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)

				if (busyNow != null) {
					busyNow.dismis();
				}
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried

			}
		});

	}

}
