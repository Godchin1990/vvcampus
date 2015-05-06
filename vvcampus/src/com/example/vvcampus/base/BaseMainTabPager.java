package com.example.vvcampus.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vvcampus.MainActivity;
import com.example.vvcampus.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 主界面的标签页 上下文：Context 是一个很重要的参数 那么一般抽取的时候都会抽取出来
 */
public abstract class BaseMainTabPager implements BaseOperator {

	public Context context;
	// 工具类的初始化
	public HttpUtils httpUtils;
	public BitmapUtils bitmapUtils;
	public Gson gson;
	public View root;// 整个标签页布局

	@ViewInject(R.id.ib_toggle)
	public ImageButton ib_toggle;

	@ViewInject(R.id.tv_title)
	public TextView tv_title;

	@ViewInject(R.id.ib_switch)
	public ImageButton ib_switch;

	@ViewInject(R.id.frame_content)
	public FrameLayout frame_content;// 占位 标签页的内容 后面进行处理

	public BaseMainTabPager(Context context) {
		super();
		this.context = context;
		httpUtils = new HttpUtils();
		bitmapUtils = new BitmapUtils(context);
		gson = new Gson();
		init();
	}

	public void init() {
		// 完成整体布局的加载
		root = View.inflate(context, R.layout.base_main_tab_pager, null);
		// 注入
		ViewUtils.inject(this, root, true);// 调用的是自己扩展的方法
		initView();// 准备让实现类使用
		setListener();
	}

	@OnClick(R.id.ib_toggle)
	public void toggle(View v) {
		// 获取到SlidingMenu
		((MainActivity) context).getSlidingMenu().toggle();
	}

	@OnClick(R.id.ib_switch)
	public void switch_list_grid(View v) {

	}

}
