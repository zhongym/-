package com.zhong.easyquery;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtr.zxing.activity.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.domain.SearchBookItem;
import com.zhong.easyquery.utils.ConstantValues;

/**
 * 
 * ============================================================
 * 
 * @project_name 易查询
 * @file_name SearchBookActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015年10月25日 下午9:41:00
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 查书服务
 * 
 *           ============================================================
 *
 */
public class SearchBookActivity extends BaseActivity {

	private EditText searchNameView;
	
	private List<SearchBookItem> bookList = new ArrayList<SearchBookItem>();
	
	private ListView bookListView;

	/** 点击打开扫条行码页面 */
	private ImageView searchCodeView;

	private BookListAdapter adapter;
	
	private ProgressBar lodaingView;
	
	private ImageView noDataHintView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchbook);

		initView();
	}

	private void getPageData(){
		String text = searchNameView.getText().toString();
		if (TextUtils.isEmpty(text)) {
			showToast("请输入查询内容！！");
			return;
		}
		
		 InputMethodManager in = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
			//隐蔽键盘
		 in.hideSoftInputFromWindow(searchNameView.getWindowToken(), 0);
		
		AjaxParams params = new AjaxParams();
		params.put("sortCoName", "hadLendedNum"); //排序字段
		params.put("sortOrder", "desc"); //降序
		params.put("pageSize", "50"); //分页大小
		params.put("pageNum", "1"); //页码
		params.put("name", text); //查询内容
		requestData(ConstantValues.SEARCHBOOKS_URL, params);
		
		lodaingView.setVisibility(View.VISIBLE);
		noDataHintView.setVisibility(View.GONE);
	}

	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String result = object.get("result").toString();
			List<SearchBookItem> bookItems = new Gson().fromJson(result, new TypeToken<List<SearchBookItem>>() {}.getType());

			System.out.println(bookItems);
			
			if (bookItems.size()==0) {
				noDataHintView.setVisibility(View.VISIBLE);
			}
			
//			bookList.clear();
//			bookList.addAll(bookItems);
			bookList=bookItems;
			adapter.notifyDataSetChanged();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lodaingView.setVisibility(View.GONE);
	}
	
	private void initView() {
		handleBreakButton();
		searchNameView = (EditText) findViewById(R.id.et_search_name);
		searchCodeView = (ImageView) findViewById(R.id.iv_search_code);
		bookListView = (ListView) findViewById(R.id.lv_book_list);
		lodaingView = (ProgressBar) findViewById(R.id.pb_load);
		noDataHintView = (ImageView) findViewById(R.id.iv_no_data);
		
		adapter= new BookListAdapter();
		bookListView.setAdapter(adapter);
		
		//添加右边图片点击事件
		searchNameView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				/**
				 * 0 1 2 3分别对就左上右下四张图片
				 */
				Drawable drawable = searchNameView.getCompoundDrawables()[2]; // 如果右边没有图片，不再处理
				
				if (drawable == null)
					return false; // 如果不是按下事件，不再处理
				
				if (event.getAction() != MotionEvent.ACTION_UP)
					return false;
				
				if (event.getX() > searchNameView.getWidth() - searchNameView.getPaddingRight()	- drawable.getIntrinsicWidth()) {
//					searchNameView.setText("");
					getPageData();
				}

				return false;
			}
		});
		
		//回车键进行搜索
		searchNameView.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if(keyCode == KeyEvent.KEYCODE_ENTER){  
					getPageData(); 
					 return true;
				 }
				return false;
			}
		});
		
		/**
		 * 点击打开条形码扫描界面
		 */
		searchCodeView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
				SearchBookActivity.this.startActivity(intent);
			}
		});
		
		/**
		 * 点击书本列表打开当前书本详情
		 */
		bookListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SearchBookItem bookItem = bookList.get(position);
				Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
				intent.putExtra("isbn", bookItem.isbn);
				intent.putExtra("xlh", bookItem.xlh);
				SearchBookActivity.this.startActivity(intent);
			}
		});

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
				convertView = View.inflate(getApplicationContext(), R.layout.item_search_book_item, null);
				holder = new ViewHolder();
				holder.tv_book_name = (TextView) convertView.findViewById(R.id.tv_book_name);
				holder.tv_book_author = (TextView) convertView.findViewById(R.id.tv_book_author);
				holder.tv_canlendNum = (TextView) convertView.findViewById(R.id.tv_canlendNum);
				holder.tv_hadLendedNum = (TextView) convertView.findViewById(R.id.tv_hadLendedNum);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			SearchBookItem bookItem = bookList.get(position);
			holder.tv_book_name.setText(bookItem.title);
			
			StringBuilder sbBuilder = new StringBuilder();
			for (String author : bookItem.author) {
				sbBuilder.append(author);
				sbBuilder.append(",");
			}
			sbBuilder.delete(sbBuilder.length()-1, sbBuilder.length());
			holder.tv_book_author.setText("作者："+sbBuilder.toString());
			holder.tv_canlendNum.setText(bookItem.canLendNum);
			holder.tv_hadLendedNum.setText(bookItem.hadLendedNum);

			return convertView;
		}

	}

	static class ViewHolder {
		TextView tv_book_name;
		TextView tv_book_author;
		TextView tv_canlendNum;
		TextView tv_hadLendedNum;
	}

}
