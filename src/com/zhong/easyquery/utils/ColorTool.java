package com.zhong.easyquery.utils;

import java.util.Random;

import android.graphics.Color;

public class ColorTool {

	/**
	 * ����һ�������ɫ
	 * 
	 */
	public static int getRandomColor() {
		Random random = new Random();
		return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}
}
