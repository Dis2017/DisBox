package com.dis.box.Other;

import android.graphics.drawable.*;
import android.content.pm.*;
import android.content.*;
import java.io.*;
import com.blankj.utilcode.util.*;

public class PluginInfo
{
	private Drawable icon;
	private String name, path, packageName, versionName;
	private int versionCode;
	private long size;
	private boolean isDownload;

	public static PluginInfo load(Context context, File apkFile)
	{
		PluginInfo info = null;
		String path = apkFile.getAbsolutePath();
		if (path.endsWith(".apk"))
		{
			info = new PluginInfo();
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			ApplicationInfo ai = pi.applicationInfo;

			ai.sourceDir = path;  
			ai.publicSourceDir = path;  
			info.setPath(path);
			info.setName((String)ai.loadLabel(pm));
			info.setIcon(ai.loadIcon(pm));
			info.setSize(apkFile.length());
			info.setPackageName(ai.packageName);
			info.setVersionCode(pi.versionCode);
			info.setVersionName(pi.versionName);
			info.setIsDownload(true);
		}
		return info;
	}
	
	public void setIsDownload(boolean isDownload)
	{
		this.isDownload = isDownload;
	}

	public boolean isDownload()
	{
		return isDownload;
	}

	public void setIcon(Drawable icon)
	{
		this.icon = icon;
	}

	public Drawable getIcon()
	{
		return icon;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getPath()
	{
		return path;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setVersionName(String versionName)
	{
		this.versionName = versionName;
	}

	public String getVersionName()
	{
		return versionName;
	}

	public void setVersionCode(int versionCode)
	{
		this.versionCode = versionCode;
	}

	public int getVersionCode()
	{
		return versionCode;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public long getSize()
	{
		return size;
	}}
