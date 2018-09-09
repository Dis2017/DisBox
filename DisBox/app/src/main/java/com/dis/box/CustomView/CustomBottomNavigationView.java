package com.dis.box.CustomView;

import android.widget.*;
import android.content.*;
import android.util.*;
import java.util.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.view.*;
import android.widget.RadioGroup.*;
import android.content.res.*;
import android.support.v4.view.*;

public class CustomBottomNavigationView extends RadioGroup
{
	private List<Item> mList;
	private ViewPager mViewPager;
	
	public CustomBottomNavigationView(Context context)
	{
		this(context, null);
	}
	public CustomBottomNavigationView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setOrientation(RadioGroup.HORIZONTAL);
		setOnCheckedChangeListener(null);
	}

	public void setViewPager(ViewPager viewPager)
	{
		this.mViewPager = viewPager;
	}

	public ViewPager getViewPager()
	{
		return mViewPager;
	}
	
	public void onResetList()
	{
		removeAllViews();
		
		for (int i = 0;i < mList.size();i++)
		{
			Item item = mList.get(i);
			RadioButton button = new RadioButton(getContext());
			Drawable icon = item.getIcon();
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			
			button.setText(item.getText());
			button.setTextColor(item.getUnSelectedColor());
			button.setTextSize(13);
			button.setButtonDrawable(0);
			button.setCompoundDrawablePadding(3);
			button.setPadding(0, 10, 0, 10);
			button.setGravity(Gravity.CENTER);
			icon.setBounds(0, 0, 50, 50);
			icon.setTint(item.getUnSelectedColor());
			button.setCompoundDrawables(null, icon, null, null);
			lp.weight = 1.0f;
			
			addView(button, lp);
		}
	}

	@Override
	public void setOnCheckedChangeListener(final RadioGroup.OnCheckedChangeListener listener)
	{
		// TODO: Implement this method
		OnCheckedChangeListener mListener = new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup p1, int p2)
			{
				//查询选中RadioButton在RadioGroup的位置
				for (int i = 0;i < p1.getChildCount();i++)
				{
					RadioButton button = (RadioButton)p1.getChildAt(i);
					Item item = mList.get(i);
					
					if (p2 == button.getId())
					{
						button.setTextColor(item.getSelectedColor());
						button.getCompoundDrawables()[1].setTint(item.getSelectedColor());
						if (mViewPager != null)
						{
							mViewPager.setCurrentItem(i);
						}
					}
					else
					{
						button.setTextColor(item.getUnSelectedColor());
						button.getCompoundDrawables()[1].setTint(item.getUnSelectedColor());
					}
				}
				if (listener != null)
				{
					listener.onCheckedChanged(p1, p2);
				}
			}
		};
		super.setOnCheckedChangeListener(mListener);
	}

	public void setList(List<Item> List)
	{
		this.mList = List;
		onResetList();
	}

	public List<Item> getList()
	{
		return mList;
	}
}
