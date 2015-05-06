package com.example.vvcampus.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.vvcampus.R;

public class DialogActivity extends Activity {
	public static final String COPY_IMAGE = null;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

	}

}
