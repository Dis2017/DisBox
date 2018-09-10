package com.dis.box;

import android.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.CustomView.*;
import com.dis.box.Manager.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import com.wevey.selector.dialog.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import com.wevey.selector.dialog.DialogInterface;

public class SetupActivity extends BasePageActivity
{
	private Toolbar mToolbar;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_layout);

		mToolbar = (Toolbar)findViewById(R.id.setupToolbar);
		mListView = (ListView)findViewById(R.id.setupListView);


		mListView.setAdapter(getAdapter());
		mToolbar.setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		BarUtils.setStatusBarAlpha(this, 160);
	}

	private ListViewAdapter getAdapter()
	{
		ListViewAdapter adapter = new ListViewAdapter(this);

		adapter.add(new Item("安全", null, ItemType.SPAN, false, null, null));
		adapter.add(new Item("密码锁", "", ItemType.SWITCH, LocalUtils.isLock(this), null, new SwitchButton.OnValueChangeListener() {
							public void onChange(boolean value)
							{
								LocalUtils.setLock(SetupActivity.this, value);
							}
						}));
		adapter.add(new Item("设置密码", null, ItemType.TEXT, false, new OnClickListener() {
							public void onClick(View v)
							{
								new MDEditDialog.Builder(SetupActivity.this)
									.setTitleText("设置密码")
									.setInputTpye(InputType.TYPE_CLASS_NUMBER)
									.setHintText("在这里输入密码")
									.setMaxLength(9)
									.setMaxLines(1)
									.setLeftButtonText("取消")
									.setRightButtonText("确定")
									.setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
										@Override
										public void clickLeftButton(MDEditDialog dialog, View view)
										{
											dialog.dismiss();
										}

										@Override
										public void clickRightButton(MDEditDialog dialog, View view)
										{
											String password = dialog.getEditTextContent();
											LocalUtils.setPinLockPassword(SetupActivity.this, password);
											Toast.makeText(SetupActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
											dialog.dismiss();
										}
									})
									.build().show();
							}
						}, null));
		adapter.add(new Item("设置密保", null, ItemType.TEXT, false, new OnClickListener() {
							public void onClick(View v)
							{
								new MDEditDialog.Builder(SetupActivity.this)
									.setTitleText("设置密保问题")
									.setInputTpye(InputType.TYPE_CLASS_TEXT)
									.setHintText("在这里输入问题")
									.setMaxLength(9)
									.setMaxLines(1)
									.setLeftButtonText("取消")
									.setRightButtonText("确定")
									.setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
										@Override
										public void clickLeftButton(MDEditDialog dialog, View view)
										{
											dialog.dismiss();
										}

										@Override
										public void clickRightButton(final MDEditDialog problemDialog, View view)
										{
											problemDialog.dismiss();
											new MDEditDialog.Builder(SetupActivity.this)
												.setTitleText("设置密保答案")
												.setInputTpye(InputType.TYPE_CLASS_TEXT)
												.setHintText("在这里输入答案")
												.setMaxLength(9)
												.setMaxLines(1)
												.setRightButtonText("确定")
												.setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
													@Override
													public void clickLeftButton(MDEditDialog dialog, View view)
													{
														dialog.dismiss();
													}

													@Override
													public void clickRightButton(MDEditDialog dialog, View view)
													{
														Toast.makeText(SetupActivity.this, "密保设置成功", Toast.LENGTH_SHORT).show();
														LocalUtils.setPinLockEncryptedProblem(SetupActivity.this, problemDialog.getEditTextContent());
														LocalUtils.setPinLockEncryptedAnswer(SetupActivity.this, dialog.getEditTextContent());
														dialog.dismiss();
													}
												})
												.build().show();
										}
									})
									.build().show();
							}
						}, null));
		adapter.add(new Item("定制", "", ItemType.SPAN, false, null, null));
		adapter.add(new Item("背景图片", "", ItemType.TEXT, false, new OnClickListener() {
							public void onClick(View v)
							{
								if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))
								{
									PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(new PermissionUtils.FullCallback() {
											public void onGranted(List<String> p1)
											{
												requestPicture(0);
											}
											public void onDenied(List<String> p1, List<String> p2)
											{
												return;
											}
										}).request();
									return;
								}
								else
								{
									requestPicture(0);
								}
							}
						}, null));
		return adapter;
	}

	@Override
	protected void callBackOnRequestPicture(String path, int requestCode, int result)
	{
		// TODO: Implement this method
		super.callBackOnRequestPicture(path, requestCode, result);
		if (requestCode == 0)
		{
			FileUtils.copyFile(path, LocalUtils.getBackgroundFile().getAbsolutePath());
			BackgroundManager.getInstance().resetAll();
			//AppUtils.relaunchApp();
		}
		if (result == RESULT_OK)
		{
			Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
		}
	}

	private class ListViewAdapter extends ArrayAdapter<Item>
	{

		public ListViewAdapter(Context context)
		{
			super(context, -1);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO: Implement this method
			View view = new TextView(getContext());
			final Item item = getItem(position);
			TextView tv = (TextView)view;

			tv.setTextSize(14);
			tv.setTextColor(Color.WHITE);
			tv.getPaint().setFakeBoldText(true);
			tv.setPadding(20, 20, 20, 20);

			if (item.getType() == ItemType.TEXT)
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.setup_text_layout, null);
				TextView text = (TextView)view.findViewById(R.id.setupText);
				tv = (TextView)view.findViewById(R.id.setupTitle);
				ImageView arrow = (ImageView)view.findViewById(R.id.setuptextlayoutImageView1);
				Drawable d = getResources().getDrawable(R.drawable.icon_right_arrow);
				d.setTint(Color.WHITE);
				arrow.setImageDrawable(d);
				text.setText(item.getText());
				view.setOnClickListener(new OnClickListener() {
						public void onClick(View v)
						{
							item.getClickListener().onClick(v);
						}
					});
			}
			else if (item.getType() == ItemType.SWITCH)
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.setup_switch_layout, null);
				SwitchButton switchButton = (SwitchButton)view.findViewById(R.id.setupSwitch);
				tv = (TextView)view.findViewById(R.id.setupTitle);

				switchButton.setValue(item.isValue());
				switchButton.setOnValueChangeListener(item.getValueChanged());
			}

			tv.setText(item.getTitle());

			return view;
		}
	}

	private class Item
	{
		private String title, text;
		private ItemType type;
		private boolean value;
		private OnClickListener clickListener;
		private SwitchButton.OnValueChangeListener valueChanged;

		public Item(String title, String text, ItemType type, boolean value, OnClickListener clickListener, SwitchButton.OnValueChangeListener valueChanged)
		{
			this.title = title;
			this.text = text;
			this.type = type;
			this.value = value;
			this.clickListener = clickListener;
			this.valueChanged = valueChanged;
		}

		public void setClickListener(OnClickListener clickListener)
		{
			this.clickListener = clickListener;
		}

		public OnClickListener getClickListener()
		{
			return clickListener;
		}

		public void setValueChanged(SwitchButton.OnValueChangeListener valueChanged)
		{
			this.valueChanged = valueChanged;
		}

		public SwitchButton.OnValueChangeListener getValueChanged()
		{
			return valueChanged;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getTitle()
		{
			return title;
		}

		public void setText(String text)
		{
			this.text = text;
		}

		public String getText()
		{
			return text;
		}

		public void setType(ItemType type)
		{
			this.type = type;
		}

		public ItemType getType()
		{
			return type;
		}

		public void setValue(boolean value)
		{
			this.value = value;
		}

		public boolean isValue()
		{
			return value;
		}}

	private enum ItemType
	{
		SPAN,
		TEXT,
		SWITCH
		}
}
