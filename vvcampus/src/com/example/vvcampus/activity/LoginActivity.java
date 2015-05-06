package com.example.vvcampus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.applib.utils.DemoHXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.vvcampus.MainActivity;
import com.example.vvcampus.R;
import com.example.vvcampus.UILApplication;
import com.example.vvcampus.utils.MyLoger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LoginActivity extends Activity {
	protected static final String TAG = "LoginActivity";
	// 停留时间
	int sleepTime = 2000;
	@ViewInject(R.id.et_username)
	private EditText et_username;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.bt_login)
	private Button bt_login;
	@ViewInject(R.id.bt_register)
	private Button bt_register;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		islogined();
	}

	private void islogined() {
		// 判断是否有登录 已经登录直接进入主界面 否则进入登录界面
		new Thread(new Runnable() {
			public void run() {
				if (DemoHXSDKHelper.getInstance().isLogined()) {
					// ** 免登陆情况 加载所有本地群和会话
					// 不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
					// 加上的话保证进了主页面会话和群组都已经load完毕
					long start = System.currentTimeMillis();
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					long costTime = System.currentTimeMillis() - start;
					// 等待sleeptime时长
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 进入主页面
					startActivity(new Intent(LoginActivity.this,
							MainActivity.class));
					finish();

				}
			}
		}).start();

	}

	@Override
	protected void onResume() {
		super.onResume();
		String name = UILApplication.getInstance().getUserName();
		et_username.setText((name != null) ? name : "");
	}

	@OnClick(R.id.bt_login)
	public void login(View v) {

		username = et_username.getText().toString().trim();
		password = et_password.getText().toString().trim();

		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, R.string.User_name_cannot_be_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, R.string.Password_cannot_be_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}

		EMChatManager.getInstance().login(username, password, new EMCallBack() {// 回调
					@Override
					public void onSuccess() {
						runOnUiThread(new Runnable() {

							public void run() {
								EMGroupManager.getInstance().loadAllGroups();
								EMChatManager.getInstance()
										.loadAllConversations();
								// 登陆成功，保存用户名密码
								UILApplication.getInstance().setUserName(
										username);
								UILApplication.getInstance().setPassword(
										password);

								MyLoger.i(TAG, "登陆聊天服务器成功");
								// 跳转注册页
								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();

							}
						});
					}

					@Override
					public void onProgress(int progress, String status) {

					}

					@Override
					public void onError(int code, String message) {

						MyLoger.i(TAG, "登陆聊天服务器失败！" + code + message);

					}
				});

	}

	@OnClick(R.id.bt_register)
	public void register(View v) {
		// 跳转注册页
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);

	}
}
