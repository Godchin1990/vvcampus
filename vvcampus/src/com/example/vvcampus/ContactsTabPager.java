package com.example.vvcampus;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.example.vvcampus.activity.DialogActivity;
import com.example.vvcampus.activity.Search2AddContactsActivity;
import com.example.vvcampus.base.BaseMainTabPager;

public class ContactsTabPager extends BaseMainTabPager {

	protected static final int LOAD_SUCCESSS = 0;
	private View inflate;
	private ExpandableListView lv;
	private ListAdapter myadapter;
	public String[] otherfriends;
	private MyExpandableListAdapter adapter;

	public ContactsTabPager(Context context) {
		super(context);
	}

	private Handler mhHandler = new Handler() {

		private List list;

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_SUCCESSS:
				list = (List) msg.obj;
				otherfriends = (String[]) list.toArray(new String[list.size()]);

				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}

		};

	};

	@Override
	public void initView() {
		ib_toggle.setVisibility(View.VISIBLE);
		ib_switch.setVisibility(View.VISIBLE);
		tv_title.setText("联系人");
		inflate = View.inflate(context, R.layout.contacts_group, null);
		frame_content.addView(inflate);
		loadData();
		findView();

	}

	private void findView() {
		lv = (ExpandableListView) inflate.findViewById(R.id.lv_chat);

		lv.setGroupIndicator(null);// 去默认箭头
		adapter = new MyExpandableListAdapter();
		lv.setAdapter(adapter);

	}

	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// Sample data set. children[i] contains the children (String[]) for
		// groups[i].
		public String[] groups = { "校方&辅导员", "我的班级", "我的年级", "同校好友" };

		public String[][] children = { { "胡算林", "张俊峰", "王志军", "二人" },
				{ "李秀婷", "蔡乔", "别高", "余音" }, { "摊派新", "张爱明" }, { "马超", "司道光" }, };

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {

			return childPosition;

		}

		@Override
		public int getChildrenCount(int groupPosition) {

			return children[groupPosition].length;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = null;
			ItemHolder holder = null;
			if (convertView != null) {
				view = convertView;
				holder = (ItemHolder) view.getTag();
			} else {
				view = View.inflate(context,
						R.layout.contacts_group_child_item, null);
				holder = new ItemHolder();
				holder.img = (ImageView) view.findViewById(R.id.iv);
				holder.txt = (TextView) view.findViewById(R.id.tv_childname);
				view.setTag(holder);
			}

			holder.txt.setText(children[groupPosition][childPosition]);

			return view;

		}

		@Override
		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return groups.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View
						.inflate(context, R.layout.contacts_group_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) view.findViewById(R.id.iv);
				holder.tv_groupname = (TextView) view
						.findViewById(R.id.tv_groupname);
				holder.tv_groupcount = (TextView) view
						.findViewById(R.id.tv_group_count);
				view.setTag(holder);
			}

			holder.tv_groupname.setText(groups[groupPosition]);
			holder.tv_groupcount.setText("12/32");

			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	static class ViewHolder {
		ImageView iv;
		TextView tv_groupname;
		TextView tv_groupcount;
	}

	static class ItemHolder {
		ImageView img;
		TextView txt;
	}

	@Override
	public void setListener() {
		lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Intent intent = new Intent(context, DialogActivity.class);
				context.startActivity(intent);

				return false;
			}
		});

		ib_switch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// 搜索添加联系人列表

				context.startActivity(new Intent(context,
						Search2AddContactsActivity.class));

			}
		});

	}

	@Override
	public void loadData() {
		new Thread() {
			public void run() {
				// 好友列表

				try {
					List<String> usernames = EMContactManager.getInstance()
							.getContactUserNames();
					Message msg = mhHandler.obtainMessage();
					msg.what = LOAD_SUCCESSS;
					msg.obj = usernames;
					mhHandler.sendMessage(msg);

				} catch (EaseMobException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 需异步执行

			};
		}.start();

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

	@Override
	public void switch_list_grid(View v) {
		super.switch_list_grid(v);
		// TODO

	}

}
