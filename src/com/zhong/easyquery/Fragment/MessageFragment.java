package com.zhong.easyquery.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhong.easyquery.CourseListActivity;
import com.zhong.easyquery.HtmlActivity;
import com.zhong.easyquery.MyConsumeActivity;
import com.zhong.easyquery.MyScoreActivity;
import com.zhong.easyquery.MyreadActivity;
import com.zhong.easyquery.R;
import com.zhong.easyquery.SearchBookActivity;
import com.zhong.easyquery.domain.AdPager;
import com.zhong.easyquery.utils.ColorTool;
import com.zhong.easyquery.utils.ConstantValues;
import com.zhong.easyquery.view.AdViewPager;

public class MessageFragment extends BaseFragment implements OnClickListener {

	private AdViewPager viewPager;

	/** 学习报告 **/
	private LinearLayout studyReport;

	/** 消息报告 **/
	private LinearLayout consumptionReport;

	/** 阅读报告 **/
	private LinearLayout readReport;

	/** 我的借阅 **/
	private LinearLayout myReading;

	/** 我的成绩 **/
	private LinearLayout myScore;

	/** 我的消费 **/
	private LinearLayout myConsume;

	/** 查书服务 **/
	private LinearLayout searchBook;
	
	/** 我的课程表 **/
	private LinearLayout courseList;

	@Override
	public void initData() {
		List<AdPager> views = new ArrayList<AdPager>();
		for (int i = 0; i < 4; i++) {
			TextView tView = new TextView(getContext());
			tView.setBackgroundColor(ColorTool.getRandomColor());
			ViewPager.LayoutParams params = new ViewPager.LayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
			tView.setLayoutParams(params);
			tView.setGravity(Gravity.CENTER);
			
			views.add(new AdPager(tView, "第" + i + "个"));
		}

		viewPager.setData(views);
		viewPager.setAutomatic(true);

	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_message, null);
		studyReport = (LinearLayout) view.findViewById(R.id.ll_study_report);
		consumptionReport = (LinearLayout) view.findViewById(R.id.ll_report_consumption);
		readReport = (LinearLayout) view.findViewById(R.id.ll_read_report);
		myReading = (LinearLayout) view.findViewById(R.id.ll_myreading);
		myScore = (LinearLayout) view.findViewById(R.id.ll_myscore);
		myConsume = (LinearLayout) view.findViewById(R.id.ll_myconsume);
		searchBook= (LinearLayout) view.findViewById(R.id.ll_search_book);
		courseList=(LinearLayout) view.findViewById(R.id.ll_my_course_list);

		viewPager = (AdViewPager) view.findViewById(R.id.adViewPager);
		return view;
	}

	public void setListener() {
		studyReport.setOnClickListener(this);
		consumptionReport.setOnClickListener(this);
		readReport.setOnClickListener(this);
		myReading.setOnClickListener(this);
		myScore.setOnClickListener(this);
		myConsume.setOnClickListener(this);
		searchBook.setOnClickListener(this);
		courseList.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_study_report: //
			intent = new Intent(getActivity(), HtmlActivity.class);
			intent.putExtra("title", "学习报告");
			intent.putExtra("url", ConstantValues.STUDYREPORT_URL);
			break;

		case R.id.ll_report_consumption: // 消费报告
			intent = new Intent(getActivity(), HtmlActivity.class);
			intent.putExtra("title", "消费报告");
			intent.putExtra("url", ConstantValues.CONSUMEREPORT_URL);
			break;

		case R.id.ll_read_report: // 阅读报告
			intent = new Intent(getActivity(), HtmlActivity.class);
			intent.putExtra("title", "阅读报告");
			intent.putExtra("url", ConstantValues.READREPORT_URL);
			break;

		case R.id.ll_myreading: // 我的借阅
			intent = new Intent(getActivity(), MyreadActivity.class);
			break;

		case R.id.ll_myscore: // 我的成绩
			intent = new Intent(getActivity(), MyScoreActivity.class);
			break;

		case R.id.ll_myconsume: // 我的消费
			intent = new Intent(getActivity(), MyConsumeActivity.class);
			break;
			
		case R.id.ll_search_book: // 查书服务
			intent = new Intent(getActivity(), SearchBookActivity.class);
			break;
			
		case R.id.ll_my_course_list: // 我的课表
			intent = new Intent(getActivity(), CourseListActivity.class);
			break;

		}

		startActivity(intent);
	}
	
}
