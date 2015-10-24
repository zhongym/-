package com.zhong.easyquery.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.view.WindowManager;

public class ActivityTool {
	// ��ǰ�����ð汾��Ϣ
	public static String getVersionInfo(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �����Ļ�Ŀ�� �͸߶�
	 * @param context
	 * @return
	 */
	public static Point getDisplaySize(Context context) {
		WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Point outSize = new Point();
		systemService.getDefaultDisplay().getSize(outSize);
		return outSize;
	}
}
