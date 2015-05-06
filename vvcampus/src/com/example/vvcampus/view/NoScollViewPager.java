package com.example.vvcampus.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *@author laoqin 
 *限制ViewPager不能够执行滑动操作
 * 1 拦截事件
 * 
 */
public class NoScollViewPager extends LazyViewPager {

	public NoScollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScollViewPager(Context context) {
		super(context);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;//不要拦截事件   事件要分发给后面的控件
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;//不自己去处理事件
	}

}
