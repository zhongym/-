package com.zhong.easyquery;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.NetUtil;
import com.zhong.easyquery.utils.NoticetManager;

/**
 * 
 * ============================================================
 * 
 * @project_name 易查询
 * @file_name BaseActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月23日 下午11:36:51
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 基础activity
 * 
 *           ============================================================
 *
 */
public class BaseActivity extends FragmentActivity {

	private FinalHttp finalHttp;

	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			myHandleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
	};

	/**
	 * 重写此方法处理Handler收到的信息
	 * 
	 * @param msg
	 */
	public void myHandleMessage(Message msg) {

	}

	/**
	 * 如果页面标题有返回按钮，调用些方法给按钮绑定点击事件
	 */
	public void handleBreakButton() {
		ImageView breakBut = (ImageView) findViewById(R.id.iv_break);
		breakBut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});

	}

	/**
	 * 调用此方法向服务器请求数据，请求完成后回调onRequestDataComplete方法
	 * 
	 * @param url
	 * @param params
	 */
	public void requestData(String url, AjaxParams params) {

		if (!NetUtil.checkNetConnetion(this)) {
			NoticetManager.showNoNetWork(this);
			return;
		}

		// 设置所有url都需要的参数
		params.put("sblx", "1");
		params.put("xtbb", "1.3.0");
		params.put("sbxh", Build.DEVICE); // 设备型号
		params.put("sbbb", Build.VERSION.RELEASE); // 设备版本

		// 非登录url需要的参数
		Account account = Account.getInstaceFromSerializable(this);
		if (account != null) {
			params.put("userID", account.userID);
			params.put("userType", account.userType);
			params.put("c_session_key", account.c_session_key);
		}

		finalHttp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, final String strMsg) {
				BaseActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						onRequestDataFailure(strMsg);
					}
				});
			}

			@Override
			public void onSuccess(final String data) {
				try {
					final JSONObject object = new JSONObject(data);
					BaseActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							onRequestDataSuccess(object);
						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
					onRequestDataFailure("数据解析出错！！！");
				}

			}
		});
	}

	/**
	 * 当调用requestData请求数据失败后回调 这个方法在ui线程执行
	 * @param strMsg
	 *            失误信息
	 */
	public void onRequestDataFailure(String strMsg) {
		showToast(strMsg);
	}

	/**
	 * 当调用requestData请求数据成功后回调 这个方法在ui线程执行
	 * 
	 * @param object
	 *            从服务器返回的数据
	 */
	public void onRequestDataSuccess(JSONObject object) {

	}
	
	public void showToast(String msg){
		NoticetManager.showToast(getApplicationContext(), msg);
	}

}
