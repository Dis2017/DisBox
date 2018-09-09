package com.dis.box;

import android.os.*;
import android.support.v7.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;

public class AboutActivity extends BasePageActivity
{
	private Toolbar mToolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		
		mToolbar = (Toolbar)findViewById(R.id.aboutToolbar);
		
		mToolbar.setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		BarUtils.setStatusBarAlpha(this, 160);
	}
}
