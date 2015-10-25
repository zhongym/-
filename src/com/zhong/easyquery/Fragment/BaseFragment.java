package com.zhong.easyquery.Fragment;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhong.easyquery.BaseActivity;
import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.NetUtil;
import com.zhong.easyquery.utils.NoticetManager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected View contentView;

	protected Context context;

	private FinalHttp finalHttp;

	private boolean show = true;
	
	/**
	 * 当些Fragment显示时才调用 initData()方法加载数据，防止预加载浪费性能
	 */
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && isVisible() && contentView.getVisibility() == View.VISIBLE&&show) {
			System.out.println("initData()");
			initData(); // 加载数据的方法
			show = false;
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
		context = getActivity();
		System.out.println(this.getClass().getName() + ".onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 防止多次初始化view
		if (contentView != null) {
			ViewGroup parent = (ViewGroup) contentView.getParent();
			if (parent != null) {
				parent.removeView(contentView);
			}
		} else {
			contentView = initView(inflater);// 控件初始化
			setListener();
			System.out.println(this.getClass().getName() + ".onCreateView()");
		}
		return contentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		if (getUserVisibleHint() && contentView.getVisibility() == View.VISIBLE&&show) {
			System.out.println("onActivityCreated-initData()");
			initData();
			show=false;
		}
		super.onActivityCreated(savedInstanceState);
		
		System.out.println(this.getClass().getName() + ".onActivityCreated()");
	}

	@Override
	public void onDestroy() {
		System.out.println(this.getClass().getName() + ".onDestroy()");
		super.onDestroy();
	}

	public abstract void initData();

	public abstract View initView(LayoutInflater inflater);

	/**
	 * 添加监听事件
	 */
	public void setListener() {

	}

	public View getContentView() {
		return contentView;
	}

	public Context getContext() {
		return context;
	}

	/**
	 * 调用此方法向服务器请求数据，请求完成后回调onRequestDataComplete方法
	 * 
	 * @param url
	 * @param params
	 */
	public void requestData(String url, AjaxParams params) {

		if (!NetUtil.checkNetConnetion(getActivity())) {
			NoticetManager.showNoNetWork(getActivity());
			return;
		}

		// 设置所有url都需要的参数
		params.put("sblx", "1");
		params.put("xtbb", "1.3.0");
		params.put("sbxh", Build.DEVICE); // 设备型号
		params.put("sbbb", Build.VERSION.RELEASE); // 设备版本

		// 非登录url需要的参数
		Account account = Account.getInstaceFromSerializable(getActivity());
		if (account != null) {
			params.put("userID", account.userID);
			params.put("userType", account.userType);
			params.put("c_session_key", account.c_session_key);
		}

		finalHttp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, final String strMsg) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						onRequestDataFailure(strMsg);
					}
				});
			}

			@Override
			public void onSuccess(final String data) {
				try {
					final JSONObject object = new JSONObject(data);
					getActivity().runOnUiThread(new Runnable() {
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
	 * 
	 * @param strMsg
	 *            失误信息
	 */
	public void onRequestDataFailure(String strMsg) {
		if (strMsg==null||strMsg.length()==0) {
			showToast("网络请求失败！！！");
		}
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

	public void showToast(String msg) {
		NoticetManager.showToast(getActivity(), msg);
	}

}
