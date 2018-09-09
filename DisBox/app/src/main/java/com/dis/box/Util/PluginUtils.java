package com.dis.box.Util;

import android.content.res.*;
import java.lang.reflect.*;
import android.content.*;
import dalvik.system.*;
import java.io.*;
import android.app.*;
import android.content.pm.*;

public class PluginUtils
{
	private Context context;
	
	private static PluginUtils mInstance = new PluginUtils();
	private PluginUtils(){}
	public static PluginUtils getInstance(){ return mInstance; }
	public void setContext(Context context){ this.context = context; }
	
	public void Load(String path) throws Exception
	{
		if (context == null)
		{
			throw new Exception("Context are null. Please invoke setContext(Context) Method");
		}
		setResources(path);
		setDexClassLoder(path);
		setActivities(path);
	}
	
	private String[] mActivitiesName;
	private void setActivities(String path)
	{
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(path, pm.GET_ACTIVITIES);
		ActivityInfo[] activitiesInfo = info.activities;
		mActivitiesName = new String[activitiesInfo.length];
		for (int i = 0;i < activitiesInfo.length;i++)
		{
			mActivitiesName[i] = activitiesInfo[i].name;
		}
	}
	public String[] getActivitiesName()
	{
		return mActivitiesName;
	}
	public String getEntryActivityName()
	{
		return mActivitiesName[0];
	}
	
	private DexClassLoader mDexClassLoder;
	private void setDexClassLoder(String path)
	{
		File dexOutPut = context.getDir("dex", context.MODE_PRIVATE);
		mDexClassLoder = new DexClassLoader(path, dexOutPut.getAbsolutePath(), null, context.getClassLoader());
	}
	public DexClassLoader getDexClassLoder()
	{
		return mDexClassLoder;
	}
	
	private Resources mResources;
	private void setResources(String path)
	{
		Resources resources = null;
		try {
			//反射获得AssetManager
			AssetManager assetManager = AssetManager.class.newInstance();
			//反射获得"addAssetPath(String path)"方法
			Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
			addAssetPath.invoke(assetManager, path);
			//new Resources
			resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mResources = resources;
	}
	public Resources getResources()
	{
		return mResources;
	}
}
