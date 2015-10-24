package com.zhong.easyquery.view;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhong.easyquery.R;
import com.zhong.easyquery.domain.AdPager;

/**
 * 
 * ============================================================
 * 
 * @project_name �ײ�ѯ
 * @file_name AdViewPager.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��9��26�� ����4:58:10
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript �Զ������������б���㡢���⡢�Զ���ҳ��ѭ����ҳ<br/>
 * 
 *           ʹ�ã�<br/>
 *           AdViewPager viewPager = (AdViewPager)
 *           view.findViewById(R.id.adViewPager); <br/>
 *           viewPager.setData(views);//�������� <br/>
 *           viewPager.setAutomatic(true);//�����Ƿ��Զ���ҳ <br/>
 *           viewPager.setCycle(flase); // �����Ƿ�ҳѭ��Ч��<br/>
 * 
 *           ============================================================
 *
 */
public class AdViewPager extends LinearLayout implements OnPageChangeListener {

	/** ��ʾ������� **/
	private ViewPager viewPager;

	/** ��ʾ������ **/
	private TextView title;

	/** ��ʾ���ָʾ�� **/
	private LinearLayout points;

	/** ��ʾ������ **/
	private List<AdPager> beans;

	/** ��ǰѡ��ҳ **/
	private int currenIndex;

	/** �Ƿ��Զ���ҳ **/
	private boolean isAutomatic;

	/** �Ƿ�һֱѭ�� **/
	private boolean isCycle = true;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (isCycle) {
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				} else {
					int index = currenIndex + 1;
					if (index >= beans.size()) {
						index = 0;
					}
					viewPager.setCurrentItem(index);
				}
				sendMessageDelayed(Message.obtain(this, 1), 3000);
			}
		};
	};

	public AdViewPager(Context context) {
		super(context);
		initView(context);
	}

	public AdViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public AdViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// �Ѳ����ļ����س�Ϊ��ǰ���ֶ���
		View.inflate(context, R.layout.page_adviwpager, this);
		viewPager = (ViewPager) findViewById(R.id.viewPage_ad);
		title = (TextView) findViewById(R.id.title);
		points = (LinearLayout) findViewById(R.id.point);

	}

	/**
	 * ����Ҫ��ʾ������ Ȼ�����initData()�������ݴ���
	 * 
	 * @param beans
	 */
	public void setData(List<AdPager> beans) {
		this.beans = beans;
		initData();
	}

	/**
	 * �����Ƿ��Զ���ҳ Ĭ�ϲ��Զ���ҳ
	 *
	 **/
	public void setAutomatic(boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
		if (isAutomatic) {
			handler.sendMessageDelayed(Message.obtain(handler, 1), 3000);
		}
	}

	/** �����Ƿ�ѭ����ҳЧ�� Ĭ����true **/
	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	private void initData() {
		for (int i = 0; i < beans.size(); i++) {
			// ������֮��Ӧ����ʾ��
			ImageView point = new ImageView(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
			params.rightMargin = 20;
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.nifice_point_bg);
			if (i == 0) { // ����Ĭ����ʾ��ı���
				point.setEnabled(false);
				title.setText(beans.get(0).title);
			} else {
				point.setEnabled(true);
			}
			points.addView(point);
		}

		viewPager.setAdapter(new AdPagerAdapter(beans, isCycle));
		viewPager.setOnPageChangeListener(this);

	}

	@Override
	public void onPageSelected(int position) {
		if (isCycle) {
			position = position % beans.size();
		}

		((ImageView) points.getChildAt(position)).setEnabled(false);
		((ImageView) points.getChildAt(currenIndex)).setEnabled(true);
		title.setText(beans.get(position).title);
		currenIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	class AdPagerAdapter extends PagerAdapter {

		List<AdPager> pagers;
		boolean isCycle;

		public AdPagerAdapter(List<AdPager> views, boolean isCycle) {
			super();
			this.pagers = views;
			this.isCycle = isCycle;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if (isCycle) {
				container.removeView(pagers.get(position % pagers.size()).view);
				return;
			}
			container.removeView(pagers.get(position).view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view;
			if (isCycle) {
				view = pagers.get(position % pagers.size()).view;
			} else {
				view = pagers.get(position).view;
			}
			container.addView(view);
			return view;
		}

		@Override
		public int getCount() {
			if (isCycle) {
				return Integer.MAX_VALUE;
			} else {
				return pagers.size();
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

}
