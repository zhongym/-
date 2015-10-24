package com.zhong.easyquery.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected View contentView;

	protected Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		super.onActivityCreated(savedInstanceState);
		System.out.println(this.getClass().getName() + ".onActivityCreated()");

		initData(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		System.out.println(this.getClass().getName() + ".onDestroy()");
		super.onDestroy();
	}

	public abstract void initData(Bundle savedInstanceState);

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

}
