package com.dis.box.CustomView;

import android.support.v4.view.*;
import android.content.*;
import android.util.*;
import android.view.*;

public class CustomViewPager extends ViewPager
{
	private boolean isPagingEnabled;

	public CustomViewPager(Context context)
	{
		this(context, null);
	}
	public CustomViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		isPagingEnabled = false;
	}

	public void setIsPagingEnabled(boolean isPagingEnabled)
	{
		this.isPagingEnabled = isPagingEnabled;
	}

	public boolean isPagingEnabled()
	{
		return isPagingEnabled;
	}

	@Override
	public void setCurrentItem(int item)
	{
		// TODO: Implement this method
		super.setCurrentItem(item, false);
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }
}
