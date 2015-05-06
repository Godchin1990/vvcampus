package com.example.vvcampus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.vvcampus.activity.LoginActivity;
import com.example.vvcampus.utils.Constants;
import com.example.vvcampus.utils.MyLoger;
import com.example.vvcampus.utils.SpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class GuideActivity extends Activity implements OnPageChangeListener {

	private static final String TAG = "GuideActivity";

	@ViewInject(R.id.vp)
	private ViewPager vp;

	private int[] imgs = new int[] { R.drawable.guide_1, R.drawable.guide_2,
			R.drawable.guide_3 };
	private List<ImageView> list;

	@ViewInject(R.id.bt_enter_main)
	private Button bt_enter_main;

	@ViewInject(R.id.ll_point)
	private LinearLayout ll_point;

	@ViewInject(R.id.red_point)
	private View red_point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		ViewUtils.inject(this);

		list = new ArrayList<ImageView>();
		for (int resId : imgs) {
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(resId);
			list.add(iv);
		}

		MyPagerAdapter adapter = new MyPagerAdapter();
		vp.setAdapter(adapter);

		// 给ViewPager设置滑动监听
		vp.setOnPageChangeListener(this);

		// 初始化灰色的点
		for (int resId : imgs) {
			View view = new View(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);
			params.rightMargin = 20;// 指定右边距
			view.setBackgroundResource(R.drawable.point_gray);
			ll_point.addView(view, params);
		}
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = list.get(position);
			vp.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = list.get(position);
			vp.removeView(view);
		}

	}

	@OnClick(R.id.bt_enter_main)
	public void enter(View v) {

		// 进入主界面
		startActivity(new Intent(this, LoginActivity.class));
		finish();
		SpUtils.putBoolean(this, Constants.KEY_HAS_FINISH_GUIDE, true);
	}

	// 两个灰色点之间的间距
	int width = -1;

	// int position, 当前选中的页面下标
	// float positionOffset, 页面滑动偏移量的比率 0 - 1
	// int positionOffsetPixels 页面偏移的像素
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		MyLoger.i(TAG, "position:" + position + ",positionOffset:"
				+ positionOffset + ",positionOffsetPixels:"
				+ positionOffsetPixels);
		if (width == -1) {
			// 获取间距 第二个灰色点击的左边距 - 第一个点击的左边距
			width = ll_point.getChildAt(1).getLeft()
					- ll_point.getChildAt(0).getLeft();
		}
		// 执行红色点击的移动
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				10, 10);
		// position *width 红色点本身的距离 positionOffset * width 偏移量
		params.leftMargin = (int) (position * width + positionOffset * width);
		red_point.setLayoutParams(params);
	}

	// 页面被选中
	@Override
	public void onPageSelected(int position) {
		int visibility = View.GONE;
		if (position == imgs.length - 1) {
			// 选择的是最后一页
			visibility = View.VISIBLE;
		} else {
			visibility = View.GONE;
		}
		bt_enter_main.setVisibility(visibility);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
