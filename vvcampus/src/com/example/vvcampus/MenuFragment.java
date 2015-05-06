package com.example.vvcampus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.example.vvcampus.activity.LoginActivity;
import com.example.vvcampus.utils.MyLoger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MenuFragment extends Fragment implements OnItemClickListener {

	@ViewInject(R.id.lv)
	private ListView lv;

	private String[] names = new String[] { "更换学校", "更改班级", "logout" };
	private MyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this, view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new MyAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getActivity(),
						R.layout.fragment_menu_lv_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) view.findViewById(R.id.iv);
				holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
				view.setTag(holder);
			}

			holder.tv_title.setText(names[position]);

			if (position == selectedPosition) {
				holder.iv.setEnabled(false);
				holder.tv_title.setEnabled(false);
			} else {
				holder.iv.setEnabled(true);
				holder.tv_title.setEnabled(true);
			}
			return view;
		}

	}

	static class ViewHolder {
		ImageView iv;
		TextView tv_title;
	}

	private int selectedPosition = 0;// 选中条目的下标

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		selectedPosition = position;
		adapter.notifyDataSetChanged();
		// 原来选中的条目就是现在点击的条目
		((MainActivity) getActivity()).getSlidingMenu().toggle();

		// 注销
		if (position == 2) {
			new Thread() {
				public void run() {
					// 此方法为异步方法
					EMChatManager.getInstance().logout(new EMCallBack() {

						@Override
						public void onSuccess() {
							MyLoger.i("gong", "注销成功");
							startActivity(new Intent(getActivity(),
									LoginActivity.class));

						}

						@Override
						public void onProgress(int progress, String status) {

						}

						@Override
						public void onError(int code, String message) {
							MyLoger.i("gong", "注销失败" + code + message);
							((MainActivity) getActivity()).finish();

						}
					});

				};
			}.start();
		}
		// 修改主界面标题的显示
		// switchTitle(position);
	}

	// private void switchTitle(int position) {
	// // 控制MainFragment里面的显示
	// // 获取MainFragment
	// ((MainActivity) getActivity()).mainFragment.operatorTitle(position);
	// }

}
