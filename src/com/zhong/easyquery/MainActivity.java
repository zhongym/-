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
 * 实现方式：隐藏TabWidget，通过RadioGroup和RadioButton实现底部菜单栏
 * 因为用了Radion,所以visibility="gone"隐藏它
 * 
 * 这种方式更漂亮，也更灵活，大部分的应用程序基本都是使用这种方式，通过setCurrentTabByTag()方法来切换不同的选项卡。
 * 
 * 主界面
 * 
 */
public class MainActivity extends FragmentActivity implements OnPageChangeListener {

	// tab第页要显示的Activity
	private List<BaseFragment> fragments;

	private RadioGroup radioGroup;

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView(); 
		
		//关闭预加载，默认一次只加载一个Fragment
		viewPager.setOffscreenPageLimit(2); //使用这个设置不能实现只能加载一个page
	}

	private void initData() {
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new MessageFragment());
		fragments.add(new SchoolFragment());
		fragments.add(new MyFragment());
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPage);
		viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragments));

		viewPager.setOnPageChangeListener(this);

		// 初始化底部按钮
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

	// viewPager滑动更改后的回调方法

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
