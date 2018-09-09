package com.dis.box.Other;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.dis.box.*;
import java.util.*;
import android.support.v4.widget.*;
import com.bumptech.glide.*;
import com.dis.box.Util.*;
import android.view.View.*;


public class PluginListViewAdapter extends ArrayAdapter<PluginInfo>
{
	private int mResId;
	private List<PluginInfo> mList;
	public PluginListViewAdapter(Context context, int resId, List<PluginInfo> list)
	{
		super(context, resId);
		mResId = resId;
		mList = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		View view = LayoutInflater.from(getContext()).inflate(mResId, null, false);
		final PluginInfo item = mList.get(position);
		ImageView icon = (ImageView)view.findViewById(R.id.pluginItemIcon);
		TextView name = (TextView)view.findViewById(R.id.pluginItemName);
		TextView size = (TextView)view.findViewById(R.id.pluginItemSize);
		Button btn = (Button)view.findViewById(R.id.pluginItemBtn);
		
		icon.setImageDrawable(item.getIcon());
		name.setText(item.getName());
		size.setText(MyConvertUtils.BToMB(item.getSize()) + "MB");
		btn.setOnClickListener(new OnClickListener() {public void onClick(View v){}});
		if (item.isDownload())
		{
			btn.setText("运行");
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{
					Toast.makeText(getContext(), "当前未开放启动", Toast.LENGTH_SHORT).show();
				}
			});
		}

		return view;
	}

	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return mList.size();
	}

}
