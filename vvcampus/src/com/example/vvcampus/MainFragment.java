package com.example.vvcampus;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.vvcampus.base.BaseMainTabPager;
import com.example.vvcampus.utils.MyLoger;
import com.example.vvcampus.view.BadgeView;
import com.example.vvcampus.view.LazyViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainFragment extends Fragment implements OnCheckedChangeListener {
	private static final String TAG = "MainFragment";

	@ViewInject(R.id.vp)
	private LazyViewPager vp;

	@ViewInject(R.id.rg)
	private RadioGroup rg;
	@ViewInject(R.id.rb_message)
	private RadioButton rb_message;
	@ViewInject(R.id.tv_msgcount)
	public TextView tv_msgcount;

	@ViewInject(R.id.tv_hasnewdynamic)
	private TextView tv_hasnewdynamic;

	private List<BaseMainTabPager> list;// 标签页集合

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this, view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 设置单选按钮的切换监听
		rg.setOnCheckedChangeListener(this);

		// 初始化标签页
		list = new ArrayList<BaseMainTabPager>();
		list.add(new MessageTabPager(getActivity()));
		list.add(new ContactsTabPager(getActivity()));
		list.add(new DynamicTabPager(getActivity()));
		MyPagerAdapter adapter = new MyPagerAdapter();
		vp.setAdapter(adapter);
		addcount(23);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// 获取到侧滑菜单
		SlidingMenu slidingMenu = ((MainActivity) getActivity())
				.getSlidingMenu();
		int item = 0;
		switch (checkedId) {
		case R.id.rb_message:
			item = 0;
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		case R.id.rb_contacts:
			item = 1;
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;
		case R.id.rb_dynamic:
			item = 2;
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;
		default:
			break;
		}
		vp.setCurrentItem(item);// 点击底部tab进行内容切换
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

		// 加载页面的操作
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			MyLoger.i(TAG, "instantiateItem:" + position);
			View view = list.get(position).root;
			vp.addView(view);
			// 当页面加载就去加载数据
			list.get(position).loadData();
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = list.get(position).root;
			vp.removeView(view);
		}

	}

	// 操作标题
	public void operatorTitle(int position) {
		// 获取当前ViewPager的选择页面
		int currentItem = vp.getCurrentItem();
		// 获取当前的标签页
		BaseMainTabPager baseMainTabPager = list.get(currentItem);
		baseMainTabPager.setTitle(position);
		// 关闭侧滑菜单
		((MainActivity) getActivity()).getSlidingMenu().toggle();
		// 操作：
		// 移除显示的布局
		// baseMainTabPager.frame_content.removeAllViews();
		// 内容添加
		// baseMainTabPager.addView(position);

	}

	public void addcount(int count) {
		BadgeView badgeView = new BadgeView(getActivity());
		badgeView.setTargetView(tv_msgcount);
		badgeView.setBadgeCount(count);
	}

}
