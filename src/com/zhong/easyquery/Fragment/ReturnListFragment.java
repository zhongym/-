package com.zhong.easyquery.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.R;
import com.zhong.easyquery.domain.BorrowBookGroup;
import com.zhong.easyquery.domain.BorrowBookItem;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name 易查询
 * @file_name ReturnListFragment.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月24日 下午3:32:24
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 借阅历史
 * 
 *           ============================================================
 *
 */
public class ReturnListFragment extends BaseFragment {

	private ProgressBar pb;
	private RelativeLayout loadMoreview;
	private ExpandableListView listView;
	private TextView floatTitle;

	private List<BorrowBookGroup> bookGroups = new ArrayList<BorrowBookGroup>();
	private HistoryAdapter adapter;

	// 当前显示页
	private int currenPage = 0;

	// 加载到最后了
	private boolean loading_end = false;

	@Override
	public void initData() {
		getPage();
	}

	/**
	 * 请求网络获取分页数据
	 */
	private void getPage() {
		AjaxParams params = new AjaxParams();
		params.put("pageNum", ++currenPage + "");
		params.put("pageSize", "10");
		requestData(ConstantValues.RETURNLIST_URL, params);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		String str;
		try {
			str = object.get("result").toString();
			List<BorrowBookItem> list = new Gson().fromJson(str, new TypeToken<List<BorrowBookItem>>() {
			}.getType());

			if (list.size() < 10) {
				loading_end = true;
			}

			SimpleDateFormat formFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat toFormat = new SimpleDateFormat("yyyy年MM月");
			Map<String, List<BorrowBookItem>> map = new LinkedHashMap<String, List<BorrowBookItem>>();

			for (BorrowBookItem borrowBookItem : list) {
				Date date = formFormat.parse(borrowBookItem.jyrq);
				String dateStr = toFormat.format(date);
				if (map.get(dateStr) == null) {
					map.put(dateStr, new ArrayList<BorrowBookItem>());
				}
				map.get(dateStr).add(borrowBookItem);
			}

			for (String groupDate : map.keySet()) {
				BorrowBookGroup group = new BorrowBookGroup();
				group.date = groupDate;
				group.items = map.get(groupDate);
				bookGroups.add(group);
			}

//			floatTitle.setVisibility(View.VISIBLE);
//			floatTitle.setText(bookGroups.get(0).date);

			pb.setVisibility(View.GONE);
			loadMoreview.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();

			// 展开全部分组
			for (int i = 0; i < listView.getCount(); i++) {
				listView.expandGroup(i);
			}

		} catch (Exception e) {
			e.printStackTrace();
			pb.setVisibility(View.GONE);
		}
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_history, null);
		pb = (ProgressBar) view.findViewById(R.id.pb_load);
		loadMoreview = (RelativeLayout) view.findViewById(R.id.rl_loadmore);
		floatTitle = (TextView) view.findViewById(R.id.float_title);
		listView = (ExpandableListView) view.findViewById(R.id.elv_readhistory);

		adapter = new HistoryAdapter(bookGroups);
		listView.setAdapter(adapter);
		listView.setGroupIndicator(null); // 取消分组图标
		listView.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});

		listView.setOnChildClickListener(new OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				return false;
			}
		});

		listView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // 已经停止滑动
					// 获取最后一个可见条目在集合里面的位置
					int lastpostion = listView.getLastVisiblePosition();

					int count = bookGroups.size();
					for (BorrowBookGroup borrowBookGroup : bookGroups) {
						count += borrowBookGroup.items.size();
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

		return view;
	}

	class HistoryAdapter extends BaseExpandableListAdapter {

		private List<BorrowBookGroup> bookGroups;

		HistoryAdapter(List<BorrowBookGroup> bookGroups) {
			this.bookGroups = bookGroups;
		}

		// 获得组的总数
		public int getGroupCount() {
			return bookGroups.size();
		}

		// 获得当前组的孩子部级
		public int getChildrenCount(int groupPosition) {
			return bookGroups.get(groupPosition).items.size();
		}

		// 返回组显示的数据
		public Object getGroup(int groupPosition) {
			return bookGroups.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return bookGroups.get(groupPosition).items.get(childPosition);
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
				TextView textView = (TextView) View.inflate(getContext(), R.layout.item_history_read_group, null);
				convertView = textView;
			}
			
			((TextView) convertView).setText(bookGroups.get(groupPosition).date);

			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.item_history_read_item, null);
				holder = new ViewHolder();
				holder.bookName = (TextView) convertView.findViewById(R.id.tv_bookname);
				holder.jxrq = (TextView) convertView.findViewById(R.id.tv_jxrq);
				holder.gfrq = (TextView) convertView.findViewById(R.id.tv_gfrq);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			BorrowBookItem bookItem = bookGroups.get(groupPosition).items.get(childPosition);
			holder.bookName.setText(bookItem.sjmc);
			holder.jxrq.setText("借阅日期：" + bookItem.jyrq);
			holder.gfrq.setText("归还日期：" + bookItem.ghrq);

			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}

	static class ViewHolder {
		TextView bookName;
		TextView jxrq;
		TextView gfrq;
	}
}
