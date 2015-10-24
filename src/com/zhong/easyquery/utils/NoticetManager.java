package com.zhong.easyquery.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.zhong.easyquery.R;

/**
 * ��ʾ��Ϣ�Ĺ����Ի�����˿�����ȿ�
 */
public class NoticetManager {
	private static ProgressDialog dialog;

	/**
	 * ���ȿ�
	 * 
	 * @param context
	 */
	public static void showProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.logo);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("��Ⱥ����ݼ����С���");
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * ���жϵ�ǰ�ֻ�û������ʱʹ��
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.logo)//
				.setTitle(R.string.app_name)//
				.setMessage("��ǰ������").setPositiveButton("����", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ��ת��ϵͳ���������ý���
						Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						context.startActivity(intent);
					}
				}).setNegativeButton("֪����", null).show();
	}

	/**
	 * �˳�ϵͳ�Ի���
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.logo)//
				.setTitle(R.string.app_name)//
				.setMessage("�Ƿ��˳�Ӧ��").setPositiveButton("ȷ��", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
						// ���Activity�����������飺û�г����˳�Ӧ��
						// �������õ���Activity������������ȡȫ�����ɵ�
						// BaseActivity����onCreated�����ŵ�������
					}
				})//
				.setNegativeButton("ȡ��", null)//
				.show();

	}

	/**
	 * ��ʾ������ʾ��
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)//
				.setIcon(R.drawable.logo)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton("ȷ��", null)//
				.show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}

	// �����Խ׶�ʱtrue
	private static final boolean isShow = true;

	/**
	 * ������ ����ʽͶ���г���ɾ
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}
}
