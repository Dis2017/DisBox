package com.dis.box.Manager;


import android.view.*;
import android.widget.*;
import com.dis.box.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import java.util.*;

public class BackgroundManager
{
	private static BackgroundManager mInstance = new BackgroundManager();
	private BackgroundManager()
	{}
	public static BackgroundManager getInstance()
	{ return mInstance; }

	private List<View> mList = new ArrayList<View>();

	public void addBackground(BaseActivity activity)
	{
		try
		{
			View view = activity.findViewById(R.id.disBackground);
			if (view != null)
			{
				//Toast.makeText(activity, "activity has been added.", Toast.LENGTH_SHORT).show();
				mList.add(view);
			}
		}
		catch (Exception e)
		{

		}
	}
	public void removeBackground(BaseActivity activity)
	{
		try
		{
			View view = activity.findViewById(R.id.disBackground);
			mList.remove(view);
		}
		catch (Exception e)
		{

		}
	}
	public void resetAll()
	{
		for (int i = 0;i < mList.size();i++)
		{
			View view = mList.get(i);
			LocalUtils.setBackground(view.getContext(), view);
		}
	}
	public void resetActivity(BaseActivity activity)
	{
		try
		{
			View view = activity.findViewById(R.id.disBackground);
			LocalUtils.setBackground(activity, view);
		}
		catch (Exception e)
		{

		}
	}
}
