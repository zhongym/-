package com.zhong.easyquery.fragmentadapter;

import java.util.List;

import android.support.v4.app.FragmentManager;

import com.zhong.easyquery.Fragment.BaseFragment;

public class MyreadFragmentAdapter extends BaseFragmentAdapter {

	private List<String> titleList;
	
	public MyreadFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments,List<String> titleList) {
		super(fm, fragments);
		this.titleList=titleList;
	}

	/**
	 * 设置每页上面对应的标题，如果viewpager没有设置标题，不用重写这个方法
	 */
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	};
	
}
