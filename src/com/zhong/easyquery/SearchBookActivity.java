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
 * @project_name �ײ�ѯ
 * @file_name SearchBookActivity.java
 * @autho ZYM
 * @version 1.0
 * @create_date 2015��10��25�� ����9:41:00
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript �������
 * 
 *           ============================================================
 *
 */
public class SearchBookActivity extends BaseActivity {

	private EditText searchNameView;
	
	private List<SearchBookItem> bookList = new ArrayList<SearchBookItem>();
	
	private ListView bookListView;

	/** �����ɨ������ҳ�� */
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
			showToast("�������ѯ���ݣ���");
			return;
		}
		
		 InputMethodManager in = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
			//���μ���
		 in.hideSoftInputFromWindow(searchNameView.getWindowToken(), 0);
		
		AjaxParams params = new AjaxParams();
		params.put("sortCoName", "hadLendedNum"); //�����ֶ�
		params.put("sortOrder", "desc"); //����
		params.put("pageSize", "50"); //��ҳ��С
		params.put("pageNum", "1"); //ҳ��
		params.put("name", text); //��ѯ����
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
		
		//����ұ�ͼƬ����¼�
		searchNameView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				/**
				 * 0 1 2 3�ֱ�Ծ�������������ͼƬ
				 */
				Drawable drawable = searchNameView.getCompoundDrawables()[2]; // ����ұ�û��ͼƬ�����ٴ���
				
				if (drawable == null)
					return false; // ������ǰ����¼������ٴ���
				
				if (event.getAction() != MotionEvent.ACTION_UP)
					return false;
				
				if (event.getX() > searchNameView.getWidth() - searchNameView.getPaddingRight()	- drawable.getIntrinsicWidth()) {
//					searchNameView.setText("");
					getPageData();
				}

				return false;
			}
		});
		
		//�س�����������
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
		 * �����������ɨ�����
		 */
		searchCodeView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
				SearchBookActivity.this.startActivity(intent);
			}
		});
		
		/**
		 * ����鱾�б�򿪵�ǰ�鱾����
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
			holder.tv_book_author.setText("���ߣ�"+sbBuilder.toString());
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
