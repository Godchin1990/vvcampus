package com.example.vvcampus;

import com.example.vvcampus.R;
import com.example.vvcampus.R.id;
import com.example.vvcampus.R.layout;
import com.example.vvcampus.base.BaseMainTabPager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DynamicTabPager extends BaseMainTabPager {

	private View inflater;
	private ListView lv;
	private String[] names = new String[] { "校方新闻", "感恩回报", "反馈" };
	private MyAdapter adapter;

	public DynamicTabPager(Context context) {
		super(context);
	}

	@Override
	public void initView() {
		ib_toggle.setVisibility(View.INVISIBLE);
		ib_switch.setVisibility(View.INVISIBLE);
		tv_title.setText("动态");
		inflater = View.inflate(context, R.layout.dynamic, null);
		frame_content.addView(inflater);
		findView();

	}

	private void findView() {
		lv = (ListView) inflater.findViewById(R.id.lv_dynamic);
		MyAdapter adapter = new MyAdapter();
		lv.setAdapter(adapter);

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return 3;
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
				view = View.inflate(context, R.layout.dynamic_listitem, null);
				holder = new ViewHolder();
				holder.tv_dynamic_listitem = (TextView) view
						.findViewById(R.id.tv_dynamic_listitem);
				view.setTag(holder);
			}

			holder.tv_dynamic_listitem.setText(names[position]);
			return view;
		}

	}

	static class ViewHolder {
		TextView tv_dynamic_listitem;
	}

	@Override
	public void setListener() {

	}

	@Override
	public void loadData() {

	}

	@Override
	public void processData(String result) {

	}

	@Override
	public void bindDataToView() {

	}

	@Override
	public void setTitle(int position) {

	}

	@Override
	public void addView(View view) {

	}

	@Override
	public void addView(int position) {
		// TODO Auto-generated method stub

	}

}
