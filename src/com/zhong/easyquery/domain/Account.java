package com.zhong.easyquery.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import org.json.JSONObject;

import android.content.Context;

/**
 * 封闭登录成功后返回的信息 <br/>
 * 
 * 返回json数据如下：<br/>
 * {"passw":"NjY2NjY2e0xZX3liZ18xMjN9",
 * "c_session_key":"60fa5f01275c3943e2ad28d8430d301d2ff1ed5f", "pageSize":10,
 * "pageNum":1, "ip":"14.146.217.29, 192.168.200.248", "userType":"1",
 * "userID":"1304090156", "dlfs":"session_key", "userName":"钟源茂",
 * "dlsj":"2015-09-18 14:14:18", "login":true, "sblx":"1",
 * "key":"60fa5f01275c3943e2ad28d8430d301d2ff1ed5f", "xtbb":"1.3.0"}
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 1966022457916364075L;
	
	public String userID;
	public String passw;
	public String userType;
	public String userName;
	public String key;
	public String c_session_key;
	public String dlsj;
	public String dlfs;
	public String xtbb;
	public boolean login;
	public String sblx;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 通过json对象给Account属性赋值 注意：json对象的key一定要和字段名称一至
	 * 
	 * @param jObject
	 * @return 赋值好的对象
	 */
	public Account(JSONObject jObject) {
		Field[] fields = this.getClass().getFields();
		for (Field field : fields) {
			try {
				field.set(this, jObject.get(field.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从硬盘中将序列化对象反序列化出来
	 * 
	 * @return account 如果没有数据返回 null
	 */
	public static Account getInstaceFromSerializable(Context context) {
		File cacheDir = context.getCacheDir();
		File saveFile = new File(cacheDir, "accout.data");
		if (saveFile.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));
				Account account = (Account) ois.readObject();
				ois.close();
				return account;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将对象序列化到硬盘
	 * 
	 * @param context
	 */
	public void instanceSerializable(Context context) {
		File cacheDir = context.getCacheDir();
		File saveFile = new File(cacheDir, "accout.data");
		try {
			ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(saveFile));
			ops.writeObject(this);
			ops.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Account [userID=" + userID + ", passw=" + passw + ", userType=" + userType + ", userName=" + userName
				+ ", key=" + key + ", c_session_key=" + c_session_key + ", dlsj=" + dlsj + ", dlfs=" + dlfs + ", xtbb="
				+ xtbb + ", login=" + login + ", sblx=" + sblx + "]";
	}

}
