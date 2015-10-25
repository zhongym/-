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
	 * ����ÿҳ�����Ӧ�ı��⣬���viewpagerû�����ñ��⣬������д�������
	 */
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	};
	
}
