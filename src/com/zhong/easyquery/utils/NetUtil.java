package com.zhong.easyquery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	/**
	 * 检查用户网络连接情况
	 * @param context
	 * @return
	 */
	public static boolean checkNetConnetion(Context context) {
		boolean wifi = isWIFIConnetion(context);
		boolean mobile = isMOBILEConnetion(context);

		if (mobile || wifi) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否连接移动网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMOBILEConnetion(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

	/**
	 * 检查wifi是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnetion(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

}
