package com.zhong.easyquery.domain;

import java.util.List;

/**
 * ============================================================
 * @project_name  易查询
 * @file_name  XqScore.java
 * @autho  ZYM
 * @version  1.0
 * @create_date 2015年10月25日 下午2:56:53
 * @Copyright 2015 www.zhongym.com Inc. All rights reserved
 *
 * @descript 
 *      每学期成绩
 * 
 * ============================================================
 *
 */
public class XqScore {

	/**学年**/
	public String xn;
	
	/**学期**/
	public String xq;
	
	/**所有课程成绩列表**/
	public List<Score> cjlb;
	
}
