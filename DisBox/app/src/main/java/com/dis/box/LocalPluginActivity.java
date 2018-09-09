package com.dis.box;

import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.widget.AdapterView.*;
import android.view.*;
import android.content.*;

public class LocalPluginActivity extends BasePageActivity
{
	private Toolbar mToolbar;
	private TextView mTip;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_plugin_layout);

		Drawable imgBack = getResources().getDrawable(R.drawable.icon_back);
		SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
			public void onRefresh()
			{
				new Thread(new Runnable() {
						public void run()
						{
							final List<PluginInfo> list = LocalUtils.getPluginList(LocalPluginActivity.this);
							runOnUiThread(new Runnable(){
									public void run()
									{
										mSwipeRefreshLayout.setRefreshing(true);
										mListView.setAdapter(new PluginListViewAdapter(LocalPluginActivity.this, R.layout.plugin_item_layout, list));
										if (list.size() == 0)
										{
											mTip.setVisibility(mTip.VISIBLE);
										}
										else
										{
											mTip.setVisibility(mTip.GONE);
										}
										mSwipeRefreshLayout.setRefreshing(false);
									}
								});
						}
					}).start();
			}
		};

		mToolbar = (Toolbar)findViewById(R.id.localPluginToolbar);
		mTip = (TextView)findViewById(R.id.localPluginTip);
		mListView = (ListView)findViewById(R.id.localPlugunListView);
		mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.localPluginSwipeRefreshLayout);

		imgBack.setTint(Color.WHITE);

		mSwipeRefreshLayout.setOnRefreshListener(refreshListener);

		BarUtils.setStatusBarAlpha(this, 160);
		refreshListener.onRefresh();
	}
}
