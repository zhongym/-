package com.zhong.easyquery.domain;

/**
 * 
 * @descript ��ʷ������Ŀ
 * 
 */
public class BorrowBookItem {
	public String sqh;// �����
	public String sjmc;// ����
	public String zz;// ����
	public String cbs;// ������
	public String cbrq;// ��������
	public String cgd;// ��ŵ�
	public String jyrq;// ��������
	public String jyrid;// ������id
	public String ghrq;// �黹����
	public String yhrq;// Ӧ������
	public String tmh;//

	@Override
	public String toString() {
		return "BorrowBookItem [sqh=" + sqh + ", sjmc=" + sjmc + ", zz=" + zz + ", cbs=" + cbs + ", cbrq=" + cbrq
				+ ", cgd=" + cgd + ", jyrq=" + jyrq + ", jyrid=" + jyrid + ", ghrq=" + ghrq + ", tmh=" + tmh + "]";
	}

}
