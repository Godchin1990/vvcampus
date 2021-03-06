package com.example.vvcampus.base;

import android.view.View;

/**
 * 
 * 抽取了一个基本的操作
 */
public interface BaseOperator {

	public void init();// 初始化

	public void initView();// 初始化控件

	public void setListener();// 设置监听

	public void loadData();// 加载数据

	public void processData(String result);// 处理数据

	public void bindDataToView();// 绑定数据给控件

	public void setTitle(int position);// 设置标题

	public void addView(View view);// 添加布局

	public void addView(int position);// 在侧滑菜单切换的时候去添加原来已经加载的view

}
