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
 * @project_name �ײ�ѯ
 * @file_name BaseActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��10��23�� ����11:36:51
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript ����activity
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
	 * ��д�˷�������Handler�յ�����Ϣ
	 * 
	 * @param msg
	 */
	public void myHandleMessage(Message msg) {

	}

	/**
	 * ���ҳ������з��ذ�ť������Щ��������ť�󶨵���¼�
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
	 * ���ô˷�����������������ݣ�������ɺ�ص�onRequestDataComplete����
	 * 
	 * @param url
	 * @param params
	 */
	public void requestData(String url, AjaxParams params) {

		if (!NetUtil.checkNetConnetion(this)) {
			NoticetManager.showNoNetWork(this);
			return;
		}

		// ��������url����Ҫ�Ĳ���
		params.put("sblx", "1");
		params.put("xtbb", "1.3.0");
		params.put("sbxh", Build.DEVICE); // �豸�ͺ�
		params.put("sbbb", Build.VERSION.RELEASE); // �豸�汾

		// �ǵ�¼url��Ҫ�Ĳ���
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
					onRequestDataFailure("���ݽ�����������");
				}

			}
		});
	}

	/**
	 * ������requestData��������ʧ�ܺ�ص� ���������ui�߳�ִ��
	 * @param strMsg
	 *            ʧ����Ϣ
	 */
	public void onRequestDataFailure(String strMsg) {
		showToast(strMsg);
	}

	/**
	 * ������requestData�������ݳɹ���ص� ���������ui�߳�ִ��
	 * 
	 * @param object
	 *            �ӷ��������ص�����
	 */
	public void onRequestDataSuccess(JSONObject object) {

	}
	
	public void showToast(String msg){
		NoticetManager.showToast(getApplicationContext(), msg);
	}

}
