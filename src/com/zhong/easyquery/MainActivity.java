package com.zhong.easyquery;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zhong.easyquery.Fragment.BaseFragment;
import com.zhong.easyquery.Fragment.MessageFragment;
import com.zhong.easyquery.Fragment.MyFragment;
import com.zhong.easyquery.Fragment.SchoolFragment;
import com.zhong.easyquery.fragmentadapter.BaseFragmentAdapter;
/**
 * ʵ�ַ�ʽ������TabWidget��ͨ��RadioGroup��RadioButtonʵ�ֵײ��˵���
 * ��Ϊ����Radion,����visibility="gone"������
 * 
 * ���ַ�ʽ��Ư����Ҳ�����󲿷ֵ�Ӧ�ó����������ʹ�����ַ�ʽ��ͨ��setCurrentTabByTag()�������л���ͬ��ѡ���
 * 
 * ������
 * 
 */
public class MainActivity extends FragmentActivity implements OnPageChangeListener {

	// tab��ҳҪ��ʾ��Activity
	private List<BaseFragment> fragments;

	private RadioGroup radioGroup;

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView(); 
		
		//�ر�Ԥ���أ�Ĭ��һ��ֻ����һ��Fragment
		viewPager.setOffscreenPageLimit(2); //ʹ��������ò���ʵ��ֻ�ܼ���һ��page
	}

	private void initData() {
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new MessageFragment());
		fragments.add(new SchoolFragment());
		fragments.add(new MyFragment());
	}

	/**
	 * ��ʼ�����
	 */
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPage);
		viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragments));

		viewPager.setOnPageChangeListener(this);

		// ��ʼ���ײ���ť
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioBu_message:
					viewPager.setCurrentItem(0);
					break;
				case R.id.radioBu_school:
					viewPager.setCurrentItem(1);
					break;
				case R.id.radioBu_my:
					viewPager.setCurrentItem(2);
					break;
				}
			}
		});
	}

	// viewPager�������ĺ�Ļص�����

	@Override
	public void onPageSelected(int arg0) {
		((RadioButton) radioGroup.getChildAt(arg0)).setChecked(true);
		;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

}
