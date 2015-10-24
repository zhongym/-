package com.zhong.easyquery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.ActivityTool;
import com.zhong.easyquery.utils.PreferencesTool;

/**
 * 
 * ============================================================
 * 
 * @project_name 信息查询
 * @file_name StartPageActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年9月18日 下午5:36:12
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 启动页面
 * 
 * ============================================================
 *
 */
public class StartPageActivity extends Activity {

	private TextView versionTV;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			enterNextActivity();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startpage);

		versionTV = (TextView) findViewById(R.id.tv_version);
		versionTV.setText("版本：" + ActivityTool.getVersionInfo(this));

	}

	@Override
	protected void onStart() {
		super.onStart();
		handler.sendMessageDelayed(Message.obtain(), 500);
	}

	// 进入下一界面
	private void enterNextActivity() {

		// 获得上次保存的版本号
		String version = PreferencesTool.getString(getApplicationContext(), PreferencesTool.LAST_OPEN_VERSON);
		
		Intent intent = null;
		if (version.equals(ActivityTool.getVersionInfo(this))) { // 已经看过新特性页面
			// 已经登录，直接进入主界面
			if (Account.getInstaceFromSerializable(getApplicationContext()) != null) {
				intent = new Intent(getApplicationContext(), MainActivity.class);
			} else { // 没登录，进入登录界面
				intent = new Intent(getApplicationContext(), LoginActivity.class);
			}
		} else { // 第一次打开，或者更新了
			intent = new Intent(getApplicationContext(), NewFeaturesActivity.class);
		}

		startActivity(intent);
		finish();
		// 进行动画
		overridePendingTransition(R.anim.translation_next_in, R.anim.translation_next_out);
		return;
	}

}
