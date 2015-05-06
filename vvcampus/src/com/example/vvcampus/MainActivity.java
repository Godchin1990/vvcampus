package com.example.vvcampus;

import android.os.Bundle;
import android.view.Window;

import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.example.vvcampus.utils.MyLoger;
import com.example.vvcampus.utils.NetUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	public MainFragment mainFragment;
	public MenuFragment menuFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 指定主界面
		setContentView(R.layout.activity_main);

		// 设置菜单界面
		setBehindContentView(R.layout.activity_main_menu);

		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		slidingMenu.setBehindOffset(180);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setFadeDegree(0.35f);
		initFragment();
		// 注册一个监听连接状态的listener
		EMChatManager.getInstance().addConnectionListener(
				new MyConnectionListener());

	}

	// 实现ConnectionListener接口
	private class MyConnectionListener implements EMConnectionListener {

		protected static final String TAG = "MyConnectionListener";

		@Override
		public void onConnected() {
			// 已连接到服务器
			MyLoger.i(TAG, "已连接到服务器");
		}

		@Override
		public void onDisconnected(final int error) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (error == EMError.USER_REMOVED) {
						// 显示帐号已经被移除
						MyLoger.i(TAG, "显示帐号已经被移除");

					} else if (error == EMError.CONNECTION_CONFLICT) {
						// 显示帐号在其他设备登陆
						MyLoger.i(TAG, " 显示帐号在其他设备登陆");
					} else {
						if (NetUtils.hasNetwork(MainActivity.this)) {

							// 连接不到聊天服务器
							MyLoger.i(TAG, " 连接不到聊天服务器");
						} else {
							// 当前网络不可用，请检查网络设置
							MyLoger.i(TAG, "当前网络不可用，请检查网络设置");
						}
					}
				}
			});
		}
	}

	private void initFragment() {
		// 替换主界面
		mainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_frame, mainFragment).commit();

		// 替换菜单栏
		menuFragment = new MenuFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_menu_frame, menuFragment).commit();
	}

}
