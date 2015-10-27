package com.zhong.easyquery;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.domain.BorrowBookGroup;
import com.zhong.easyquery.domain.BorrowBookItem;
import com.zhong.easyquery.domain.Score;
import com.zhong.easyquery.domain.XqScore;
import com.zhong.easyquery.utils.ActivityTool;
import com.zhong.easyquery.utils.ConstantValues;

public class MyScoreActivity extends BaseActivity {

	private ExpandableListView scoreListView;

	private ProgressBar loadingView;

	private List<XqScore> xqScoreList;

	private ScroeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myscore);

		initView();
		initData();
	}

	private void initView() {
		handleBreakButton();
		loadingView = (ProgressBar) findViewById(R.id.pb_load);
		scoreListView = (ExpandableListView) findViewById(R.id.elv_score);

		Point displaySize = ActivityTool.getDisplaySize(this);
		scoreListView.setIndicatorBounds(displaySize.x - 50, displaySize.x);

	}

	private void initData() {
		AjaxParams params = new AjaxParams();
		requestData(ConstantValues.GRADELIST_URL, params);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String jsonResult = object.get("result").toString();
			xqScoreList = new Gson().fromJson(jsonResult, new TypeToken<List<XqScore>>() {
			}.getType());

			adapter = new ScroeAdapter();
			scoreListView.setAdapter(adapter);

			// 展开全部分组
			for (int i = 0; i < xqScoreList.size(); i++) {
				scoreListView.expandGroup(i);
			}

		} catch (Exception e) {
			e.printStackTrace();
			showToast("数据加载失败！！！");
		}
		loadingView.setVisibility(View.GONE);
	}

	class ScroeAdapter extends BaseExpandableListAdapter {

		// 获得组的总数
		public int getGroupCount() {
			return xqScoreList.size();
		}

		// 获得当前组的孩子部级
		public int getChildrenCount(int groupPosition) {
			return xqScoreList.get(groupPosition).cjlb.size();
		}

		// 返回组显示的数据
		public Object getGroup(int groupPosition) {
			return xqScoreList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return xqScoreList.get(groupPosition).cjlb.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				TextView textView = (TextView) View.inflate(getApplicationContext(), R.layout.item_history_read_group,
						null);
				convertView = textView;
			}

			XqScore xqScore = xqScoreList.get(groupPosition);
			((TextView) convertView).setText(xqScore.xn + "年第" + xqScore.xq + "学期");

			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item_scroe_item, null);
				holder = new ViewHolder();
				holder.course_name = (TextView) convertView.findViewById(R.id.tv_course_name);
				holder.course_xf = (TextView) convertView.findViewById(R.id.tv_course_xf);
				holder.course_score = (TextView) convertView.findViewById(R.id.tv_course_score);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Score score = xqScoreList.get(groupPosition).cjlb.get(childPosition);
			holder.course_name.setText(score.kcmc);
			holder.course_xf.setText(score.xf + "学分");
			holder.course_score.setText(score.zscj);
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	static class ViewHolder {
		TextView course_name;
		TextView course_xf;
		TextView course_score;
	}
}
