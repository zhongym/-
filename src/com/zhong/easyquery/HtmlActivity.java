package com.zhong.easyquery;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhong.easyquery.utils.ActivityTool;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name 易查询
 * @file_name StudyReportActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月24日 上午12:52:15
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 用于显示Html数据
 * 
 *           ============================================================
 *
 */
public class HtmlActivity extends BaseActivity implements OnClickListener {

	private WebView webView;

	/** 加载条 **/
	private ProgressBar pb_loading;

	/** 标题文本 **/
	private TextView titleTextView;
	
	/**排行表*/
	private ImageView iv_pk;

	private static String TAG = "HtmlActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		initView();

		String url = getIntent().getStringExtra("url");
		String title = getIntent().getStringExtra("title");
		titleTextView.setText(title);
		
		if (title.equals("阅读报告")) {
			iv_pk.setVisibility(View.VISIBLE);
			iv_pk.setOnClickListener(this);
		}
		
		initData(url);

	}

	private void initView() {
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		// webView.getSettings().setCacheMode(2);
		// webView.addJavascriptInterface(this, "ybgConsumeReport");
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pb_loading.setVisibility(View.GONE);
			}

		});
		 webView.setWebChromeClient(new WebChromeClient());

		pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
		titleTextView = (TextView) findViewById(R.id.tv_title);
		iv_pk = (ImageView) findViewById(R.id.iv_pk);
		handleBreakButton();

	}

	private void initData(String url) {
		AjaxParams params = new AjaxParams();
		params.put("widgetHeight", ActivityTool.getDisplaySize(this).x + "");
		requestData(url, params);

	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String content = (String) object.getJSONObject("result").get("html");

			StringBuffer sb = new StringBuffer(content);
			if (sb.indexOf("阅读报告") == -1) {
				
				String script = "<script src=\"http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js\"></script> "
						+ "<script src=\"http://cdn.hcharts.cn/highcharts/4.0.3/highcharts.js\"></script>"
						+ "<script src=\"http://code.highcharts.com/highcharts-more.js\"></script>";

				sb.insert(sb.indexOf("</title>") + "</title>".length(), script);

			}

			if (sb.indexOf("<!DOCTYPE html>") == -1) {
				sb.insert(0, "<!DOCTYPE html>");
			}

			// String title =
			// sb.substring(sb.indexOf("<title>")+"<title>".length(),
			// sb.indexOf("</title>"));
			// titleTextView.setText(title);

			Log.i(TAG, sb.toString());

			// webView.loadData(sb.toString(), "text/html; charset=UTF-8",
			// null);
			webView.loadDataWithBaseURL("about:blank", sb.toString(), "text/html; charset=UTF-8", "utf-8", null);

		} catch (JSONException e) {
			e.printStackTrace();
			showToast("数据解析出错！！！");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_pk://阅读排行
			Intent intent= new Intent(this, HtmlActivity.class);
			intent.putExtra("title", "阅读排行");
			intent.putExtra("url", ConstantValues.READRANK_URL);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
