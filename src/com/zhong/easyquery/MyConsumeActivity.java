package com.zhong.easyquery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import net.tsz.afinal.http.AjaxParams;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.domain.BorrowBookGroup;
import com.zhong.easyquery.domain.BorrowBookItem;
import com.zhong.easyquery.domain.ConsumeGroup;
import com.zhong.easyquery.domain.ConsumeItem;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name 易查询
 * @file_name MyConsumeActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月25日 下午4:57:29
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 我的消费
 * 
 *           ============================================================
 *
 */
public class MyConsumeActivity extends BaseActivity {

	private ExpandableListView consListView;

	/** 显示正在加载 */
	private ProgressBar loadingView;

	/** 显示加载更多 */
	private RelativeLayout loadMoreview;

	private List<ConsumeGroup> groupList = new ArrayList<ConsumeGroup>();

	private ConsumeAdapter adapter;

	private int currenPage = 0;

	private boolean loading_end = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myconsume);

		initView();
		getPage();
	}

	private void initView() {
		handleBreakButton();
		loadingView = (ProgressBar) findViewById(R.id.pb_load);
		consListView = (ExpandableListView) findViewById(R.id.elv_myconsume);
		consListView.setGroupIndicator(null);
		loadMoreview = (RelativeLayout) findViewById(R.id.rl_loadmore);

		adapter = new ConsumeAdapter();
		consListView.setAdapter(adapter);

		consListView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // 已经停止滑动
					// 获取最后一个可见条目在集合里面的位置
					int lastpostion = consListView.getLastVisiblePosition();

					int count = groupList.size();
					for (ConsumeGroup group : groupList) {
						count += group.items.size();
					}

					if (lastpostion == (count - 1) && !loading_end) {
						loadMoreview.setVisibility(View.VISIBLE);
						getPage();
					}
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});

	}

	/**
	 * 请求网络获取分页数据
	 */
	private void getPage() {
		AjaxParams params = new AjaxParams();
		params.put("pageNum", ++currenPage + "");
		params.put("pageSize", "20");
		requestData(ConstantValues.CONSUMELIST_URL, params);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {

		String str;
		try {
			str = object.get("result").toString();
			List<ConsumeItem> list = new Gson().fromJson(str, new TypeToken<List<ConsumeItem>>() {
			}.getType());

			 if (list.size() < 20) {
				 loading_end = true;
			 }

			SimpleDateFormat formFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
			SimpleDateFormat toFormat = new SimpleDateFormat("yyyy年MM月");
			Map<String, List<ConsumeItem>> map = new LinkedHashMap<String, List<ConsumeItem>>();

			for (ConsumeItem item : list) {
				Date date = formFormat.parse(item.xfsj);
				String dateStr = toFormat.format(date);

				boolean contnues = false;
				for (ConsumeGroup group : groupList) {
					if (dateStr.equals(group.month)) {
						group.items.add(item);
						contnues=true;
						continue;
					}
				}
				
				if (contnues) {
					continue;
				}

				if (map.get(dateStr) == null) {
					map.put(dateStr, new ArrayList<ConsumeItem>());
				}
				map.get(dateStr).add(item);
			}

			for (String month : map.keySet()) {
				ConsumeGroup group = new ConsumeGroup();
				group.month = month;
				group.items = map.get(month);
				groupList.add(group);
			}

			adapter.notifyDataSetChanged();

			// 展开全部分组
			for (int i = 0; i < groupList.size(); i++) {
				consListView.expandGroup(i);
			}

		} catch (Exception e) {
			e.printStackTrace();
			showToast("加载数据出错！！！");
		}
		loadingView.setVisibility(View.GONE);
		loadMoreview.setVisibility(View.GONE);
	}

	class ConsumeAdapter extends BaseExpandableListAdapter {

		// 获得组的总数
		public int getGroupCount() {
			return groupList.size();
		}

		// 获得当前组的孩子部级
		public int getChildrenCount(int groupPosition) {
			return groupList.get(groupPosition).items.size();
		}

		// 返回组显示的数据
		public Object getGroup(int groupPosition) {
			return groupList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return groupList.get(groupPosition).items.get(childPosition);
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
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				TextView textView = (TextView) View.inflate(getApplicationContext(), R.layout.item_history_read_group,
						null);
				convertView = textView;
			}
			ConsumeGroup group = groupList.get(groupPosition);
			((TextView) convertView).setText(group.month);

			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item_consume_item, null);
				holder = new ViewHolder();
				holder.tv_cons_locale = (TextView) convertView.findViewById(R.id.tv_cons_locale);
				holder.tv_xfje = (TextView) convertView.findViewById(R.id.tv_xfje);
				holder.tv_cons_time = (TextView) convertView.findViewById(R.id.tv_cons_time);
				holder.tv_syje = (TextView) convertView.findViewById(R.id.tv_syje);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ConsumeItem item = groupList.get(groupPosition).items.get(childPosition);
			holder.tv_cons_locale.setText(item.shmc);
			if ("0".equals(item.szlx)) {
				holder.tv_xfje.setTextColor(Color.RED);
				holder.tv_xfje.setText("-" + item.xfje);
			} else {
				holder.tv_xfje.setTextColor(Color.GREEN);
				holder.tv_xfje.setText("+" + item.xfje);
			}
			holder.tv_cons_time.setText(item.xfsj);
			holder.tv_syje.setText("余额：" + item.syje);
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}

	static class ViewHolder {
		TextView tv_cons_locale;
		TextView tv_xfje;
		TextView tv_cons_time;
		TextView tv_syje;
	}
}
