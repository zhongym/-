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
 * @project_name ��Ϣ��ѯ
 * @file_name StartPageActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��9��18�� ����5:36:12
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript ����ҳ��
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
		versionTV.setText("�汾��" + ActivityTool.getVersionInfo(this));

	}

	@Override
	protected void onStart() {
		super.onStart();
		handler.sendMessageDelayed(Message.obtain(), 500);
	}

	// ������һ����
	private void enterNextActivity() {

		// ����ϴα���İ汾��
		String version = PreferencesTool.getString(getApplicationContext(), PreferencesTool.LAST_OPEN_VERSON);
		
		Intent intent = null;
		if (version.equals(ActivityTool.getVersionInfo(this))) { // �Ѿ�����������ҳ��
			// �Ѿ���¼��ֱ�ӽ���������
			if (Account.getInstaceFromSerializable(getApplicationContext()) != null) {
				intent = new Intent(getApplicationContext(), MainActivity.class);
			} else { // û��¼�������¼����
				intent = new Intent(getApplicationContext(), LoginActivity.class);
			}
		} else { // ��һ�δ򿪣����߸�����
			intent = new Intent(getApplicationContext(), NewFeaturesActivity.class);
		}

		startActivity(intent);
		finish();
		// ���ж���
		overridePendingTransition(R.anim.translation_next_in, R.anim.translation_next_out);
		return;
	}

}
