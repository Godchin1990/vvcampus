package com.example.vvcampus.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.example.vvcampus.R;
import com.example.vvcampus.UILApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RegisterActivity extends Activity {
	@ViewInject(R.id.editText1)
	private EditText userNameEditText;
	@ViewInject(R.id.editText2)
	private EditText passwordEditText;
	@ViewInject(R.id.editText3)
	private EditText confirmPwdEditText;
	@ViewInject(R.id.button1)
	private Button bt_register;
	private String username;
	private String pwd;
	private String confirm_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ViewUtils.inject(this);

	}

	/**
	 * 注册
	 * 
	 * @param view
	 */
	@OnClick(R.id.button1)
	public void register(View v) {
		String st1 = getResources().getString(
				R.string.User_name_cannot_be_empty);
		String st2 = getResources()
				.getString(R.string.Password_cannot_be_empty);
		String st3 = getResources().getString(
				R.string.Confirm_password_cannot_be_empty);
		String st4 = getResources().getString(R.string.Two_input_password);
		String st5 = getResources().getString(R.string.Is_the_registered);
		final String st6 = getResources().getString(
				R.string.Registered_successfully);
		username = userNameEditText.getText().toString().trim();
		pwd = passwordEditText.getText().toString().trim();
		confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, st1, Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, st2, Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, st3, Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, st4, Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage(st5);
			pd.show();
			final String st7 = getResources().getString(
					R.string.network_anomalies);
			final String st8 = getResources().getString(
					R.string.User_already_exists);
			final String st9 = getResources().getString(
					R.string.registration_failed_without_permission);
			final String st10 = getResources().getString(
					R.string.Registration_failed);
			new Thread(new Runnable() {
				public void run() {
					try {
						// 调用sdk注册方法
						EMChatManager.getInstance().createAccountOnServer(
								username, pwd);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								// 保存用户名
								UILApplication.getInstance().setUserName(
										username);
								Toast.makeText(getApplicationContext(), st6, 0)
										.show();
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode = e.getErrorCode();
								if (errorCode == EMError.NONETWORK_ERROR) {
									Toast.makeText(getApplicationContext(),
											st7, Toast.LENGTH_SHORT).show();
								} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
									Toast.makeText(getApplicationContext(),
											st8, Toast.LENGTH_SHORT).show();
								} else if (errorCode == EMError.UNAUTHORIZED) {
									Toast.makeText(getApplicationContext(),
											st9, Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getApplicationContext(),
											st10 + e.getMessage(),
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();

		}
	}

	public void back(View view) {
		finish();
	}
}
