package com.zhong.easyquery.utils;

public interface ConstantValues {

	/**
	 * 登录url
	 */
	String LONGI_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/user/login";

	
	/**
	 * 学习报告url
	 */
	String STUDYREPORT_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/gradeReport/get-GradeReportInfo";

	/**
	 * 消息报告url
	 */
	String CONSUMEREPORT_URL ="http://ybg.gzccc.edu.cn/mobileapi/service/oneCardReport/get-ConsumeReport";
	
	/**
	 * 阅读报告url
	 */
	String READREPORT_URL ="http://ybg.gzccc.edu.cn/mobileapi/service/libraryReport/get-ReadInfo";
	
	/**
	 * 阅读排行url
	 */
	String READRANK_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/libraryReport/get-ReadRank";
	
	/**
	 * 历史借书列表url
	 */
	String RETURNLIST_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/library/get-ReturnList";
	
	/**
	 * 当前借阅书籍列表url
	 */
	String BORROWLIST_URL="http://ybg.gzccc.edu.cn/mobileapi/service/library/get-BorrowList";
	
	/**
	 * 我的成绩url
	 */
	String GRADELIST_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/grade/get-GradeList";
	
	/**
	 * 我的消费url
	 */
	String CONSUMELIST_URL = "http://ybg.gzccc.edu.cn/mobileapi/service/oneCard/get-ConsumeList";
	
	/**
	 *  上周消费
	 */
	String CONSUMERANK_URL="http://ybg.gzccc.edu.cn/mobileapi/service/oneCardReport/get-ConsumeRank";
	
	/**
	 *  总消费
	 */
	String CONSUMESTATISTICS_URL="http://ybg.gzccc.edu.cn/mobileapi/service/oneCardReport/get-ConsumeStatistics";
	
	/**
	 *  绩点排行
	 */
	String GRADEREPORTRANK_URL="http://ybg.gzccc.edu.cn/mobileapi/service/gradeReport/get-GradeReportRank";
	
	/**
	 *  查找书本url
	 */
	String SEARCHBOOKS_URL="http://ybg.gzccc.edu.cn/mobileapi/service/bookService/search-books";
	
}
