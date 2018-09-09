package com.dis.box.Other;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import com.dis.box.*;
import com.dis.box.Manager.*;
import java.util.*;
import android.app.*;
import android.os.*;

public class BaseActivity extends AppCompatActivity
{
	private Boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		DisActivityManager.getInstance().addActivity(this);
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		BackgroundManager.getInstance().addBackground(this);
		BackgroundManager.getInstance().resetActivity(this);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		DisActivityManager.getInstance().removeActivity(this);
		BackgroundManager.getInstance().removeBackground(this);
	}

	protected void setExit(Boolean isExit)
	{
		this.isExit = isExit;
	}
	

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{ 
			exitBy2Click(); //调用双击退出函数
		}
		return false;
	}
	/**
	 * 双击退出函数
	 */
	private void exitBy2Click()
	{
		Timer tExit = null;
		if (isExit == false)
		{
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
					@Override
					public void run()
					{
						isExit = false; // 取消退出
					}
				}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		}
		else
		{
			finish();
		}
	}

	@Override
	public void startActivity(Intent intent)
	{
		// TODO: Implement this method
		super.startActivity(intent);
        this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
}
