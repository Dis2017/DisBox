package com.dis.box;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import com.bumptech.glide.*;
import android.graphics.drawable.*;
import android.graphics.*;
import com.blankj.utilcode.util.*;
import android.view.View.*;
import de.hdodenhof.circleimageview.*;
import com.dis.box.Util.*;

public class MineFragment extends Fragment
{
	private ListView mListView;
	private CircleImageView mHeadPortrait;
	private TextView mName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View view = inflater.inflate(R.layout.mine_layout, null, false);
		ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.mine_item_layout);

		mListView = (ListView)view.findViewById(R.id.mineRecyclerView);
		mHeadPortrait = (CircleImageView)view.findViewById(R.id.mineHeadPortrait);
		mName = (TextView)view.findViewById(R.id.mineName);
		
		adapter.add(new Item(R.drawable.icon_local, "本地插件"));
		adapter.add(new Item(R.drawable.icon_feedback, "意见反馈"));
		adapter.add(new Item(R.drawable.icon_tool_addition_application, "插件添加申请"));
		adapter.add(new Item(R.drawable.icon_about, "关于软件"));
		adapter.add(new Item(R.drawable.icon_updata, "检查更新"));
		adapter.add(new Item(R.drawable.icon_help, "帮助"));
		adapter.add(new Item(R.drawable.icon_setup, "设置"));
		
		mHeadPortrait.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getHeadPortraitFile()));
		mName.setText(LocalUtils.getUserName(getContext()));
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnClickItemListener());
		view.findViewById(R.id.mineBusinessCard).setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(), PersonalActivity.class));
			}
		});

		return view;
	}

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		mHeadPortrait.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getHeadPortraitFile()));
		mName.setText(LocalUtils.getUserName(getContext()));
	}
	
	private class OnClickItemListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		{
			// TODO: Implement this method
			Item item = (Item)p1.getItemAtPosition(p3);
			Intent intent;
			switch (item.getTitle())
			{
				case "本地插件":
					intent = new Intent(getContext(), LocalPluginActivity.class);
					startActivity(intent);
					break;
				case "关于软件":
					intent = new Intent(getContext(), AboutActivity.class);
					startActivity(intent);
					break;
				case "意见反馈":
					intent = new Intent(getContext(), FeedbackActivity.class);
					startActivity(intent);
					break;
				case "设置":
					intent = new Intent(getContext(), SetupActivity.class);
					startActivity(intent);
					break;
				case "帮助":
					intent = new Intent(getContext(), HelpActivity.class);
					startActivity(intent);
					break;
				case "插件添加申请":
					Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_SHORT).show();
					break;
				case "检查更新":
					Toast.makeText(getContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
	
	private class ListViewAdapter extends ArrayAdapter<Item>
	{
		private int mResId;
		public ListViewAdapter(Context context, int resId)
		{
			super(context, resId);
			mResId = resId;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO: Implement this method
			View view = LayoutInflater.from(getContext()).inflate(mResId, null, false);
			Item item = getItem(position);
			ImageView icon = (ImageView)view.findViewById(R.id.mineItemIcon);
			ImageView rightArrow = (ImageView)view.findViewById(R.id.mineItemRightArrow);
			TextView title = (TextView)view.findViewById(R.id.mineItemTitle);
			Drawable img =getResources().getDrawable(item.getIconResId());
			Drawable arrow = getResources().getDrawable(R.drawable.icon_right_arrow);
			
			img.setTint(Color.WHITE);
			icon.setImageDrawable(img);
			arrow.setTint(Color.WHITE);
			rightArrow.setImageDrawable(arrow);
			title.setText(item.getTitle());
			
			return view;
		}
	}
	
	private class Item
	{
		private int iconResId;
		private String title;

		public Item(int iconResId, String title)
		{
			this.iconResId = iconResId;
			this.title = title;
		}

		public void setIconResId(int iconResId)
		{
			this.iconResId = iconResId;
		}

		public int getIconResId()
		{
			return iconResId;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getTitle()
		{
			return title;
		}}
}
