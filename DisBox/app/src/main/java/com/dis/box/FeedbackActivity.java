package com.dis.box;

import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.Util.*;
import android.support.v7.widget.Toolbar;
import com.dis.box.Other.*;

public class FeedbackActivity extends BasePageActivity
{
	private Toolbar mToolbar;
	private Button mBtn;
	private EditText mEdt;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_layout);
	
		mToolbar = (Toolbar)findViewById(R.id.feedbackToolbar);
		mBtn = (Button)findViewById(R.id.feedbackBtn);
		mEdt = (EditText)findViewById(R.id.feedbackEdt);
		
		mBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
			}
		});
		mToolbar.setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		BarUtils.setStatusBarAlpha(this, 160);
	}
}
