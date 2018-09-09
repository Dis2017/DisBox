package com.dis.box;

import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.CustomView.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import java.util.*;
import android.content.*;

public class MainActivity extends BaseActivity
{
	private CustomBottomNavigationView mButtomNavigation;
	private CustomViewPager mViewPager;
	private DrawerLayout mDrawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		List<Item> itemList = new ArrayList<Item>();
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		
		mButtomNavigation = (CustomBottomNavigationView)findViewById(R.id.mainCustomBottomNavigationView);
		mViewPager = (CustomViewPager)findViewById(R.id.mainViewPager);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.disBackground);

		mDrawerLayout.setStatusBarBackgroundColor(Color.TRANSPARENT);
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);
		mDrawerLayout.setFitsSystemWindows(true);
		mDrawerLayout.setClipToPadding(true);
		fragmentList.add(new CommunicationFragment());
		fragmentList.add(new PluginsFragment());
		fragmentList.add(new MineFragment());
		mViewPager.setAdapter(new mViewPagerAdapter(getSupportFragmentManager(), fragmentList));
		mViewPager.setOffscreenPageLimit(2);
		
		itemList.add(new Item(Color.GRAY, Color.WHITE, "通讯", getResources().getDrawable(R.drawable.icon_communication)));
		itemList.add(new Item(Color.GRAY, Color.WHITE,"插件", getResources().getDrawable(R.drawable.icon_plugins)));
		itemList.add(new Item(Color.GRAY, Color.WHITE,"我的", getResources().getDrawable(R.drawable.icon_mine)));
		mButtomNavigation.setList(itemList);
		mButtomNavigation.setViewPager(mViewPager);
		((RadioButton)mButtomNavigation.getChildAt(1)).setChecked(true);

		BarUtils.setStatusBarAlpha(this, 160);
		startActivity(new Intent(this, PinActivity.class));
	}
	
	private class mViewPagerAdapter extends FragmentPagerAdapter
	{
		private List<Fragment> mList;
		
		public mViewPagerAdapter(FragmentManager fm, List<Fragment> list)
		{
			super(fm);
			mList = list;
		}

		@Override
		public Fragment getItem(int p1)
		{
			// TODO: Implement this method
			return mList.get(p1);
		}

		@Override
		public int getCount()
		{
			// TODO: Implement this method
			return mList.size();
		}
	}
}
