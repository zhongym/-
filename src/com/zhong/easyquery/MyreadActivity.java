package com.zhong.easyquery;

import java.util.ArrayList;
import java.util.List;

import com.zhong.easyquery.R;
import com.zhong.easyquery.Fragment.BaseFragment;
import com.zhong.easyquery.Fragment.BorrowListFragment;
import com.zhong.easyquery.Fragment.ReturnListFragment;
import com.zhong.easyquery.fragmentadapter.BaseFragmentAdapter;
import com.zhong.easyquery.fragmentadapter.MyreadFragmentAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * ============================================================
 * 
 * @project_name �ײ�ѯ
 * @file_name MyreadActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��10��24�� ����1:41:39
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript �ҵĽ���
 * 
 *           ============================================================
 *
 */
public class MyreadActivity extends BaseActivity {

	private ViewPager viewPager;

	private List<BaseFragment> fragments;

	private TextView historyTextView;
	private TextView currenTextView;
	private ImageView selectorImageView;

	// ��¼ViewPger��һ�������position��Ϣ
	private int lastPosition = 0;

	/** ��Ļ�Ŀ�� **/
	private int sWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myreading);

		initView();
		initData();
		setListener();
		selectorPosition();
		handleBreakButton();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.myread_viewPage);
		historyTextView = (TextView) findViewById(R.id.tv_history);
		currenTextView = (TextView) findViewById(R.id.tv_current);
		selectorImageView = (ImageView) findViewById(R.id.iv_selector);
	}

	private void initData() {
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new BorrowListFragment());
		fragments.add(new ReturnListFragment());

		viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragments));

	}

	/**
	 * ����ѡ����ͼƬ�ĳ�ʼλ��
	 */
	private void selectorPosition() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		sWidth = outMetrics.widthPixels;// ��Ļ�Ŀ��

		// ������ĸ�ѡ����ͼƬ����ʵ��ȣ�����ImageView�Ŀ��
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.id_category_selector);
		int bWidth = bitmap.getWidth();
		
		// �����ʼλ��
		int offset = ((sWidth / 2) - bWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		selectorImageView.setImageMatrix(matrix);// ����ͼƬ�ĳ�ʼλ��
	}

	/**
	 * ���ü���
	 */
	private void setListener() {
		// �������������õ���¼������ʱ����page
		currenTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
			}
		});
		historyTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
			}
		});

		/** ���page���ļ���������page����ʱ��ͼƬѡ����Ҳ���� **/
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			/** view���ĺ�ص����� **/
			public void onPageSelected(int position) {

				// fromXDelta toXDelta:�����ͼƬ��ʼλ����Ҫ���ӵ���
				TranslateAnimation animation = new TranslateAnimation(lastPosition * sWidth / 2, position * sWidth / 2,
						0, 0);
				animation.setDuration(300);
				animation.setFillAfter(true);// �ƶ����ͣ�����յ�
				selectorImageView.startAnimation(animation);

				lastPosition = position;// ��¼����ǰ��ʾ��page

				// page�л��󣬸��ı������ֵ�ɫ�� 1����ԭ���У�2�����ݵ�ǰ����
				currenTextView.setTextColor(Color.BLACK);
				historyTextView.setTextColor(Color.BLACK);

				switch (position) {
				case 0:
					currenTextView.setTextColor(Color.RED);
					break;
				case 1:
					historyTextView.setTextColor(Color.RED);
					break;
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

}
