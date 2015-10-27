package com.zhong.easyquery.domain;

/**
 * 
 * ============================================================
 * @descript 
 *      封装搜索书返回的json
 * 
 * ============================================================
 *
 */
public class SearchBookItem {
	
	/** 作者s **/
	public String author[];
	/** 可借本数 **/
	public String canLendNum;//canLendNum
	/** 当前多少人借 **/
	public String currentLendNum;
	/** 多少人借过 **/
	public String hadLendedNum;//hadLendedNum
	
	/** 出版时间 **/
	public String pubdate;
	/** 搜索号码 **/
	public String isbn;
	/** 页数 **/
	public String pages;
	/** 图片 **/
//	public String images;
	/** 价格 **/
	public String price;
	/** 出版社 **/
	public String publisher;
	/** 书名 **/
	public String title;
	/** 书本总数 **/
	public String total;

}
