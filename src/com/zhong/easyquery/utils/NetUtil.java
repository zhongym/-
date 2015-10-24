package com.zhong.easyquery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	/**
	 * ����û������������
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
	 * ����Ƿ������ƶ�����
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
	 * ���wifi�Ƿ�����
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
