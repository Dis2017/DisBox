package com.dis.box.Util;

import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import com.dis.box.Other.*;
import java.io.*;
import java.util.*;
import com.blankj.utilcode.util.*;
import android.graphics.*;

public class LocalUtils
{
	public static File getProgrammingFile()
	{
		File file = new File(Environment.getExternalStorageDirectory(), "/.DisBox");
		if (!file.exists())
		{
			file.mkdirs();
		}
		return file;
	}
	public static File getPluginsFile()
	{
		File file = new File(getProgrammingFile(), "/Plugins"+"/");
		if (!file.exists())
		{
			file.mkdirs();
		}
		return file;
	}
	public static File getConfigurationFile()
	{
		File file = new File(getProgrammingFile(), "/Configuration/");
		if (!file.exists())
		{
			file.mkdirs();
		}
		return file;
	}
	public static File getUserFile()
	{
		File file = new File(getProgrammingFile(), "/User/");
		if (!file.exists())
		{
			file.mkdirs();
		}
		return file;
	}
	public static File getStartPictureFile()
	{
		return new File(getConfigurationFile(), "startPic.jpg");
	}
	public static File getBackgroundFile()
	{
		return new File(getConfigurationFile(), "Background.jpg");
	}
	public static File getHeadPortraitFile()
	{
		return new File(getUserFile(), "headPortrait.jpg");
	}
	public static File getPersonalToolbarBackgroundFile()
	{
		return new File(getUserFile(), "personalToolbarBackground.jpg");
	}
	

	public static void setBackground(Context context, View bg)
	{
		File file = getBackgroundFile();
		if (file.exists())
		{
			bg.setBackground(Drawable.createFromPath(file.getAbsolutePath()));
		}
	}
	public static List<PluginInfo> getPluginList(Context context)
	{
		File file = getPluginsFile();
		File[] files = file.listFiles();
		List<PluginInfo> list = new ArrayList<PluginInfo>();

		for (int i = 0;i < files.length;i++)
		{
			PluginInfo info = PluginInfo.load(context, files[i]);
			for (int j = 0;info != null && j < list.size();j++)
			{
				if (info.getPackageName().equals(list.get(j).getPackageName()))
				{
					if (info.getVersionCode() <= list.get(j).getVersionCode())
					{
						info = null;
					}
				}
			}
			if (info != null)
			{
				list.add(info);
			}
		}

		return list;
	}
	public static void setLock(Context context, boolean p1)
	{
		context.getSharedPreferences("setup", context.MODE_PRIVATE).edit().putBoolean("isLock", p1).commit();
	}
	public static boolean isLock(Context context)
	{
		return context.getSharedPreferences("setup", context.MODE_PRIVATE).getBoolean("isLock", false);
	}
	public static void setPinLockPassword(Context context, String p1)
	{
		context.getSharedPreferences("setup", context.MODE_PRIVATE).edit().putString("pinLoclPassword", p1).commit();
	}
	public static String getPinLockPassword(Context context)
	{
		return context.getSharedPreferences("setup", context.MODE_PRIVATE).getString("pinLoclPassword", "");
	}
	public static void setPinLockEncryptedProblem(Context context, String p1)
	{
		context.getSharedPreferences("setup", context.MODE_PRIVATE).edit().putString("pinLockEncryptedProblem", p1).commit();
	}
	public static String getPinLockEncryptedProblem(Context context)
	{
		return context.getSharedPreferences("setup", context.MODE_PRIVATE).getString("pinLockEncryptedProblem", "");
	}
	public static void setPinLockEncryptedAnswer(Context context, String p1)
	{
		context.getSharedPreferences("setup", context.MODE_PRIVATE).edit().putString("pinLockEncryptedAnswer", p1).commit();
	}
	public static String getPinLockEncryptedAnswer(Context context)
	{
		return context.getSharedPreferences("setup", context.MODE_PRIVATE).getString("pinLockEncryptedAnswer", "");
	}
	
	public static void setUserName(Context context, String p1)
	{
		context.getSharedPreferences("user", context.MODE_PRIVATE).edit().putString("name", p1).commit();
	}
	public static String getUserName(Context context)
	{
		return context.getSharedPreferences("user", context.MODE_PRIVATE).getString("name", "");
	}
	public static void setUserHeadPortrait(Context context, Drawable p1)
	{
		Bitmap bitmap = ConvertUtils.drawable2Bitmap(p1);
		ImageUtils.save(bitmap, getHeadPortraitFile(), Bitmap.CompressFormat.JPEG);
	}
	public static void setUserPersonalToolbarBackground(Context context, Drawable p1)
	{
		Bitmap bitmap = ConvertUtils.drawable2Bitmap(p1);
		ImageUtils.save(bitmap, getPersonalToolbarBackgroundFile(), Bitmap.CompressFormat.JPEG);
	}
}
