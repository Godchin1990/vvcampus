package com.example.vvcampus.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vvcampus.R;

public class DialogUtils {
	private static final int DIALOG_EXIT = 0;
	private Context context;

	// 展示土司提示--联网失败的提示
	public static void showHttpError(final Activity activity) {
		showToast(activity, "连接服务器失败,请稍后重试");
	}

	// 展示提示信息
	public static void showToast(final Activity activity, final String text) {
		try {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// Toast.makeText(activity, text,
					// Toast.LENGTH_SHORT).show();
					toast(activity, text);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void toast(Activity activity, String msg) {
		if (activity == null)
			return;
		Toast toast = Toast.makeText(activity, null, Toast.LENGTH_SHORT);
		LinearLayout layout = (LinearLayout) toast.getView();
		layout.setBackgroundResource(R.drawable.dlg_exit_nomal);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.CENTER_VERTICAL);
		TextView tv = new TextView(activity);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setTextColor(Color.parseColor("#ffffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setPadding(0, 0, 0, 0);
		tv.setText(msg);
		layout.addView(tv);
		toast.show();
	}

	public static Dialog CreateDialog(int id, final Activity activity) {
		Dialog dialog = null;

		switch (id) {
		case DIALOG_EXIT:
			MyDialog.Builder myBuilder = new MyDialog.Builder(activity);
			myBuilder.setTitle("温馨提示");
			myBuilder.setMessage("您确定退出吗?");
			myBuilder.setBackButton("返 回",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							activity.finish();

						}
					});
			myBuilder.setConfirmButton("确 定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {

							activity.finish();

						}
					});
			dialog = myBuilder.create();
			break;
		}

		return dialog;
	}
}
