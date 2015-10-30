package com.zhong.easyquery;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhong.easyquery.domain.SearchBookItem;
import com.zhong.easyquery.utils.ConstantValues;

public class BookDetailsActivity extends BaseActivity {

	private TextView tv_book_name;
	private TextView tv_book_author;
	private TextView tv_book_publisher;
	private TextView tv_book_publidate;
	/**��ǰ�ɽ�*/
	private TextView tv_currentlendnum;
	/**�������*/
	private TextView tv_hadlendednum;
	/**�ɽ�ͼ��*/
	private TextView tv_canlendnum;
	
	private LinearLayout ll_content;
	private ImageView iv_no_data;
	private ProgressBar pb_loading;
	
	private ImageView iv_share;
	
	
	
	private String isbn;
	private String xlh;
	private SearchBookItem bookItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		
		isbn = getIntent().getStringExtra("isbn");
		xlh = getIntent().getStringExtra("xlh");
		if (TextUtils.isEmpty(isbn)) {
			isbn="";
		}
		if (TextUtils.isEmpty(xlh)) {
			xlh="";
		}
		
		initView();
		initData();
	}

	private void initView() {
		handleBreakButton();
		tv_book_name = (TextView) findViewById(R.id.tv_book_name);
		tv_book_author = (TextView) findViewById(R.id.tv_book_author);
		tv_book_publisher = (TextView) findViewById(R.id.tv_book_publisher);
		tv_book_publidate = (TextView) findViewById(R.id.tv_book_publidate);
		tv_currentlendnum = (TextView) findViewById(R.id.tv_currentlendnum);
		tv_hadlendednum = (TextView) findViewById(R.id.tv_hadlendednum);
		tv_canlendnum = (TextView) findViewById(R.id.tv_canlendnum);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		iv_no_data = (ImageView) findViewById(R.id.iv_no_data);
		pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
		
		iv_share= (ImageView) findViewById(R.id.iv_share);
		
		/**
		 * �����鱾��Ϣ
		 */
		iv_share.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.SEND");
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "�Ƽ����Ķ�:\""+bookItem.author[0]+"д��"+bookItem.title+"\"");//����
				startActivity(intent);
			}
		});
	}
	
	private void initData() {
		AjaxParams params = new AjaxParams();
		params.put("isbn", isbn);
		params.put("xlh", xlh);
		requestData(ConstantValues.BOOKDETAIL_URL, params);
	}
	@Override
	public void onRequestDataSuccess(JSONObject object) {
		try {
			String jsonStr = object.get("result").toString();
			bookItem = new Gson().fromJson(jsonStr, new TypeToken<SearchBookItem>() {}.getType());
			
			pb_loading.setVisibility(View.GONE);
			
			if (bookItem==null) {
				showToast("û���ҵ��Ȿ�飡����");
				iv_no_data.setVisibility(View.VISIBLE);
				return;
			}
			
			ll_content.setVisibility(View.VISIBLE);
			tv_book_name.setText(bookItem.title);
			
			StringBuilder sbBuilder = new StringBuilder();
			for (String author : bookItem.author) {
				sbBuilder.append(author);
				sbBuilder.append(",");
			}
			sbBuilder.delete(sbBuilder.length()-1, sbBuilder.length());
			tv_book_author.setText("���ߣ�"+sbBuilder.toString());
			
			tv_book_publisher.setText("�����磺"+bookItem.publisher);
			tv_book_publidate.setText("�������ڣ�"+bookItem.pubdate);
			tv_currentlendnum.setText(bookItem.currentLendNum);
			tv_hadlendednum.setText(bookItem.hadLendedNum);
			tv_canlendnum.setText(bookItem.canLendNum);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
