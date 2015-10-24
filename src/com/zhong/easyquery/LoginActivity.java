package com.zhong.easyquery;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name 信息查询
 * @file_name LoginActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年9月18日 下午5:35:25
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 登录页面
 * 
 *           ============================================================
 *
 */
public class LoginActivity extends BaseActivity {

	private EditText userEditText;

	private EditText pwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userEditText = (EditText) findViewById(R.id.user);
		pwdEditText = (EditText) findViewById(R.id.pwd);

	}

	public void cancel(View view) {
		userEditText.setText("");
		pwdEditText.setText("");
	}

	/**
	 * 点击登录
	 */
	public void login(View view) {
		String userID = userEditText.getText().toString().trim();
		String passwd = pwdEditText.getText().toString().trim();

		if (TextUtils.isEmpty(userID)) {
			Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(passwd)) {
			Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}
		passwd = Base64.encodeToString(passwd.getBytes(), Base64.DEFAULT);
		passwd = passwd + "e0xZX3liZ18xMjN9";

		AjaxParams params = new AjaxParams();
		params.put("passw", passwd);
		params.put("userID", userID);
		requestData(ConstantValues.LONGI_URL, params);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String statusMsg = object.getString("statusMsg");
			Toast.makeText(getApplicationContext(), statusMsg, 1).show();
			if ("请求成功".equals(statusMsg)) {
				Account account = new Account(object.getJSONObject("result"));
				account.instanceSerializable(getApplicationContext());

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				finish();
				startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showToast("数据解析出错！！！");
		}
	}


}
