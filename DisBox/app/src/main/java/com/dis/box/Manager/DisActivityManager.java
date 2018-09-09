package com.dis.box.Manager;

import android.view.*;
import com.dis.box.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import java.util.*;

public class DisActivityManager
{
	private static DisActivityManager mInstance = new DisActivityManager();
	private DisActivityManager(){}
	public static DisActivityManager getInstance(){ return mInstance; }

	private List<BaseActivity> mList = new ArrayList<BaseActivity>();

	public void addActivity(BaseActivity activity)
	{
		mList.add(activity);
	}
	public void removeActivity(BaseActivity activity)
	{
		mList.remove(activity);
	}
	public void removeAll()
	{
		mList.clear();
	}
}
