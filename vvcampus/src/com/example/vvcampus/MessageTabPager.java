package com.example.vvcampus;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vvcampus.activity.DialogActivity;
import com.example.vvcampus.base.BaseMainTabPager;

/**
 * @author laoqin
 */
public class MessageTabPager extends BaseMainTabPager {

	private ListView lv;
	private View inflater;

	public MessageTabPager(Context context) {
		super(context);
	}

	@Override
	public void initView() {
		ib_toggle.setVisibility(View.INVISIBLE);
		ib_switch.setVisibility(View.INVISIBLE);
		tv_title.setText("会话");
		inflater = View.inflate(context, R.layout.message_chat, null);
		frame_content.addView(inflater);
		findView();

	}

	private void findView() {
		lv = (ListView) inflater.findViewById(R.id.lv_message_chat);
		MyAdapter adapter = new MyAdapter();
		lv.setAdapter(adapter);

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return 4;
		}

		@Override
		public Object getItem(int position) {
			return 0;
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
				view = View.inflate(context, R.layout.message_chat_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) view.findViewById(R.id.iv_head);
				holder.tv_msg_from = (TextView) view
						.findViewById(R.id.tv_msg_from);
				holder.tv_msg_content = (TextView) view
						.findViewById(R.id.tv_msg_content);
				holder.tv_msg_time = (TextView) view
						.findViewById(R.id.tv_msg_time);
				holder.tv_msg_count = (TextView) view
						.findViewById(R.id.tv_msg_count);
				view.setTag(holder);
			}

			holder.tv_msg_from.setText("校方消息");
			holder.tv_msg_content.setText("你好");
			holder.tv_msg_time.setText("昨天");
			holder.tv_msg_count.setText("6");
			return view;
		}

	}

	static class ViewHolder {
		ImageView iv;
		TextView tv_msg_from;
		TextView tv_msg_content;
		TextView tv_msg_time;
		TextView tv_msg_count;
	}

	@Override
	public void setListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, DialogActivity.class);
				context.startActivity(intent);

			}

		});

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

	}

}
