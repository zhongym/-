package com.zhong.easyquery.view.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class NewFeaturesPageAdapter extends PagerAdapter {

	// �����б�
	private List<View> views;

	public NewFeaturesPageAdapter(List<View> views) {
		this.views = views;
	}

	/**
	 * ��õ�ǰ������
	 */
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	/**
	 * ��ʼ��positionλ�õĽ���
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = views.get(position);
		container.addView(view);
		System.out.println(view);
		return view;
	}

	/**
	 * �ж��Ƿ��ɶ������ɽ���
	 */
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	/**
	 * ����positionλ�õĽ���
	 */
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeView(views.get(position));
	}

}
