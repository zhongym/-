package com.zhong.easyquery;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhong.easyquery.domain.Account;
import com.zhong.easyquery.utils.ActivityTool;
import com.zhong.easyquery.utils.PreferencesTool;
import com.zhong.easyquery.view.adapter.NewFeaturesPageAdapter;

/**
 * 
 * ============================================================
 * 
 * @project_name 11-view
 * @file_name NewFeaturesActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��9��18�� ����11:37:51
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript չʾ���������
 * 
 *           ============================================================
 *
 */
public class NewFeaturesActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ViewPager viewPager;

	private List<View> views;

	/** ��ʾ������ */
	private LinearLayout promptbar;

	// ��¼��ǰѡ��λ��
	private int currentIndex;

	/** ���һ������İ�ť */
	private Button startBut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newfeatures);

		viewPager = (ViewPager) findViewById(R.id.viewpage);
		promptbar = (LinearLayout) findViewById(R.id.promptbar);

		initData();
		viewPager.setAdapter(new NewFeaturesPageAdapter(views));
		viewPager.setOnPageChangeListener(this);
		startBut.setOnClickListener(this);
	}

	private void initData() {
		views = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			if (i == 3) {
				RelativeLayout view = (RelativeLayout) View.inflate(getApplicationContext(),
						R.layout.page_newfeatures_4, null);
				startBut = (Button) view.findViewById(R.id.bt_start);
				views.add(view);
			} else {
				// ����viewPage��ʾ��view
				ImageView imageView = new ImageView(getApplicationContext());
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
				imageView.setLayoutParams(params);
				imageView.setScaleType(ScaleType.FIT_XY);
				int id = getResources().getIdentifier("newfeatures_" + i, "drawable", getPackageName());
				imageView.setImageResource(id);
				views.add(imageView);
			}

			// ������֮��Ӧ����ʾ��
			ImageView point = new ImageView(getApplicationContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
			params.rightMargin = 20;
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.nifice_point_bg);
			if (i == 0) {
				point.setEnabled(false);
			} else {
				point.setEnabled(true);
			}

			// ���õ� ���
			point.setTag(i);
			point.setOnClickListener(this);
			promptbar.addView(point);
		}
	}

	// ������ṩ��ʱ�л�viewPager
	public void onClick(View v) {
		if (v.getId() == startBut.getId()) { // �����ʼ��ť������ҳ
			enterNextActivity();
			return;
		}

		Integer integer = (Integer) v.getTag();
		if (integer >= 0 && integer < views.size()) {
			viewPager.setCurrentItem(integer);
			selectPonit(integer);
		}
	}

	/**
	 * ������һ����
	 */
	private void enterNextActivity() {
		// ���浱ǰ�򿪰汾
		PreferencesTool.sava(getApplicationContext(), PreferencesTool.LAST_OPEN_VERSON,
				ActivityTool.getVersionInfo(getApplicationContext()));

		Intent intent = null;
		if (Account.getInstaceFromSerializable(getApplicationContext()) != null) {
			intent = new Intent(getApplicationContext(), MainActivity.class);
		} else { // û��¼�������¼����
			intent = new Intent(getApplicationContext(), LoginActivity.class);
		}
		startActivity(intent);
		finish();
		// ���ж���
		overridePendingTransition(R.anim.translation_next_in, R.anim.translation_next_out);
	}

	// ----------------------ViewPager��������ķ���

	private void selectPonit(int arg0) {
		promptbar.getChildAt(currentIndex).setEnabled(true);
		promptbar.getChildAt(arg0).setEnabled(false);
		currentIndex = arg0;
	}

	/**
	 * ���µ�ҳ�汻ѡ��ʱ����
	 */
	public void onPageSelected(int arg0) {
		selectPonit(arg0);
	}

	/**
	 * ������״̬�ı�ʱ����
	 */
	public void onPageScrollStateChanged(int arg0) {
	}

	/**
	 * ����ǰҳ�汻����ʱ����
	 */
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

}
