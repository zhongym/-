package com.zhong.easyquery.domain;

/**
 * 
 * @descript 历史借书条目
 * 
 */
public class BorrowBookItem {
	public String sqh;// 索书号
	public String sjmc;// 书名
	public String zz;// 作者
	public String cbs;// 出版社
	public String cbrq;// 出版日期
	public String cgd;// 存放地
	public String jyrq;// 借书日期
	public String jyrid;// 借书人id
	public String ghrq;// 归还日期
	public String yhrq;// 应还日期
	public String tmh;//

	@Override
	public String toString() {
		return "BorrowBookItem [sqh=" + sqh + ", sjmc=" + sjmc + ", zz=" + zz + ", cbs=" + cbs + ", cbrq=" + cbrq
				+ ", cgd=" + cgd + ", jyrq=" + jyrq + ", jyrid=" + jyrid + ", ghrq=" + ghrq + ", tmh=" + tmh + "]";
	}

}
