package com.zhong.easyquery.domain;

import android.view.View;

/**
 * 
 * ============================================================
 *	滚动条显示的每条内容：view+title
 * 
 * ============================================================
 *
 */
public class AdPager {
	
	public View view;
	public String title;

	public AdPager(View view, String title) {
		super();
		this.view = view;
		this.title = title;
	}

}
