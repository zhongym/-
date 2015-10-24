package com.zhong.easyquery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesTool {

	public static final String LAST_OPEN_VERSON = "lastOpenVerson";

	/**
	 * 
	 * @param context
	 * @param name
	 * @return 没有数据时 默认返回false
	 */
	public static boolean getBoolean(Context context, String name) {
		SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return preferences.getBoolean(name, false);
	}

	/**
	 * 
	 * @param context
	 * @param name
	 * @return 没有数据时返回 ""
	 */
	public static String getString(Context context, String name) {
		SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return preferences.getString(name, "");
	}

	public static void sava(Context context, String name, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(name, value);
		edit.commit();
	}

	public static void sava(Context context, String name, String value) {
		SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(name, value);
		edit.commit();
	}
}
