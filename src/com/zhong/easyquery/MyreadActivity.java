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
 * @project_name 易查询
 * @file_name MyreadActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月24日 下午1:41:39
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 我的借阅
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

	// 记录ViewPger上一个界面的position信息
	private int lastPosition = 0;

	/** 屏幕的宽度 **/
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
	 * 设置选择器图片的初始位置
	 */
	private void selectorPosition() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		sWidth = outMetrics.widthPixels;// 屏幕的宽度

		// 计算出哪个选择器图片的真实宽度，不是ImageView的宽度
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.id_category_selector);
		int bWidth = bitmap.getWidth();
		
		// 求出初始位置
		int offset = ((sWidth / 2) - bWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		selectorImageView.setImageMatrix(matrix);// 设置图片的初始位置
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		// 给标题文字设置点击事件，点击时更改page
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

		/** 添加page更改监听器，当page更改时，图片选择器也更改 **/
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			/** view更改后回调方法 **/
			public void onPageSelected(int position) {

				// fromXDelta toXDelta:相对于图片初始位置需要增加的量
				TranslateAnimation animation = new TranslateAnimation(lastPosition * sWidth / 2, position * sWidth / 2,
						0, 0);
				animation.setDuration(300);
				animation.setFillAfter(true);// 移动完后停留到终点
				selectorImageView.startAnimation(animation);

				lastPosition = position;// 记录出当前显示的page

				// page切换后，更改标题文字的色彩 1：还原所有，2：根据当前设置
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
