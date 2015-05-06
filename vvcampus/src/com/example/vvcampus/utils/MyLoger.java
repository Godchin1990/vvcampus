package com.example.vvcampus.utils;

import android.util.Log;

/**
 *@author laoqin 
 *日志工具类
 */
public final class MyLoger {
	
	private final static boolean flag = true;//测试  
	
	public static void v(String tag,String msg){
		if(flag){
			Log.v(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if(flag){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg){
		if(flag){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg){
		if(flag){
			Log.w(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if(flag){
			Log.e(tag, msg);
		}
	}

}
