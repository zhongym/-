package com.zhong.easyquery.Fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import net.tsz.afinal.http.AjaxParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.R;
import com.zhong.easyquery.domain.BorrowBookItem;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name �ײ�ѯ
 * @file_name BorrowListFragment.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��10��24�� ����3:32:39
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript ��ǰ����
 * 
 *           ============================================================
 *
 */
public class BorrowListFragment extends BaseFragment {

	private ListView bookListView;

	// ��ǰû�н�������
	private RelativeLayout noBookHint;

	private ProgressBar loadingView;

	private List<BorrowBookItem> bookList = new ArrayList<BorrowBookItem>();

	private BookListAdapter adapter;

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_curren_borrow, null);
		noBookHint = (RelativeLayout) view.findViewById(R.id.no_book_hint);
		loadingView = (ProgressBar) view.findViewById(R.id.pb_book_load);
		bookListView = (ListView) view.findViewById(R.id.book_list);

		adapter = new BookListAdapter();
		bookListView.setAdapter(adapter);

		return view;
	}

	@Override
	public void initData() {

		AjaxParams params = new AjaxParams();
		params.put("pageNum", "1");
		params.put("pageSize", "10");
		requestData(ConstantValues.BORROWLIST_URL, params);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String str = object.get("result").toString();
			List<BorrowBookItem> list = new Gson().fromJson(str, new TypeToken<List<BorrowBookItem>>() {}.getType());

			if (list.size() == 0) { //��ǰû�н���
				noBookHint.setVisibility(View.VISIBLE);
			} else {
				bookList = list;
				adapter.notifyDataSetChanged();
			}

		} catch (Exception e) {
			e.printStackTrace();
			showToast("���ݼ��س�������");
		}
		loadingView.setVisibility(View.GONE);
	}

	class BookListAdapter extends BaseAdapter {

		public int getCount() {
			return bookList.size();
		}

		public Object getItem(int position) {
			return bookList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.item_history_read_item, null);
				holder = new ViewHolder();
				holder.bookName = (TextView) convertView.findViewById(R.id.tv_bookname);
				holder.jxrq = (TextView) convertView.findViewById(R.id.tv_jxrq);
				holder.yhrq = (TextView) convertView.findViewById(R.id.tv_gfrq);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			BorrowBookItem bookItem = bookList.get(position);
			holder.bookName.setText(bookItem.sjmc);
			holder.jxrq.setText("�������ڣ�" + bookItem.jyrq);
			holder.yhrq.setText("Ӧ�����ڣ�" + bookItem.yhrq);

			return convertView;
		}

	}

	static class ViewHolder {
		TextView bookName;
		TextView jxrq;
		TextView yhrq;
	}
}
