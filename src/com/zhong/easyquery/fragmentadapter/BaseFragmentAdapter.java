package com.zhong.easyquery.fragmentadapter;

import java.util.List;

import com.zhong.easyquery.Fragment.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/*
 * ��ViewPagerӦ�ú�Fragmentһ��ʹ��ʱ����ʱViewPager����������FragmentPagerAdapter��

 getCount()

 getItem()
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

	// �����б�
	private List<BaseFragment> fragments;

	public BaseFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		if (fragments != null)
			return fragments.size();
		return 0;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

}
