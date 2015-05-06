package com.example.vvcampus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.example.vvcampus.activity.LoginActivity;
import com.example.vvcampus.utils.Constants;
import com.example.vvcampus.utils.MyLoger;
import com.example.vvcampus.utils.SpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WelcomeActivity extends Activity implements AnimationListener {

	private static final String TAG = "WelcomeActivity";
	@ViewInject(R.id.ll)
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_welcome);
		ViewUtils.inject(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MyLoger.i(TAG, "onResume");

	}

	// 该方法是在onResume()方法调用完成后调用
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		MyLoger.i(TAG, "onWindowFocusChanged");

		// 效果：1 旋转 2 缩放 3 透明度改变
		AnimationSet set = new AnimationSet(false);// 动画集合
		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(2000);
		rotate.setFillAfter(true);

		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(2000);
		scale.setFillAfter(true);

		// 透明度改变动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(2000);
		alpha.setFillAfter(true);

		// 添加动画
		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		ll.startAnimation(set);

		// 当动画执行完成后 过3s再进入其他的界面
		set.setAnimationListener(this);
	}

	private Handler mHandler = new Handler();

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// 过了3s进入主界面 或者向导界面
		mHandler.postDelayed(new EnterTask(), 3000);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	private class EnterTask implements Runnable {

		@Override
		public void run() {
			// 进入主界面 或者向导界面:通过首选项来判断
			boolean has_finish_guide = SpUtils.getBoolean(
					getApplicationContext(), Constants.KEY_HAS_FINISH_GUIDE,
					false);
			Intent intent = new Intent();
			if (has_finish_guide) {
				// 进入主界面
				intent.setClass(getApplicationContext(), LoginActivity.class);
			} else {
				// 进入向导界面
				intent.setClass(getApplicationContext(), GuideActivity.class);
			}
			startActivity(intent);
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 如果用户按下后退键 就把mHandler里面的消息清空
		mHandler.removeCallbacksAndMessages(null);
	}

}
