package com.dis.box.CustomView;

import android.content.*;
import android.view.*;
import android.util.*;
import android.graphics.*;
import android.view.View.*;
import java.util.*;
import android.os.*;

public class SwitchButton extends View
{
	public SwitchButton(Context context){this(context, null);}
	public SwitchButton(Context context, AttributeSet attrs){super(context, attrs);init();}

	//默认宽高
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 50;

	private boolean mValue;
	private int mOffBackgroundColor, mOnBackgroundColor;
	private int mOffSliderColor, mOnSliderColor;
	private int mDividerWidth;
	private int mOffDividerColor, mOnDividerColor;
	private int mOffset;
	private OnValueChangeListener mOnValueChangeListener;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			int offset = 10;
			//0 = 向右偏移
			//1 = 向左偏移
			switch (msg.what)
			{
				case 0:
					//判断是否偏移后会越界
					if (offset + getOffset() > getWidth() - getHeight())
					{
						offset = getWidth() - getHeight() - getOffset();
					}
					setOffset(getOffset() + offset);
					break;
				case 1:
					setOffset(getWidth() - getHeight());
					break;
				case 2:
					//判断是否偏移后会越界
					if (getOffset() - offset < 0)
					{
						offset = getOffset();
					}
					setOffset(getOffset() - offset);
					break;
				case 3:
					setOffset(0);
					break;
			}
		}
	};

	//初始化
	private void init()
	{
		setOffBackgroundColor(Color.GRAY);
		setOnBackgroundColor(Color.GREEN);
		setOffSliderColor(Color.WHITE);
		setOnSliderColor(Color.WHITE);
		setOffDividerColor(Color.CYAN);
		setOnDividerColor(Color.GREEN);
		setDividerWidth(2);
		setOffset(0);
		setOnValueChangeListener(null);
		setOnClickListener(null);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		int mDividerColor;
		
		paint.setAntiAlias(true);
		paint.setStrokeWidth(mDividerWidth);
		if (mValue)
		{
			mDividerColor = mOnDividerColor;
		}
		else
		{
			mDividerColor = mOffDividerColor;
		}
		//绘制背景
		paint.setColor(mDividerColor);
		canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), getHeight()/2, getHeight()/2, paint);
		if (mValue)
		{
			paint.setColor(mOnBackgroundColor);
		}
		else
		{
			paint.setColor(mOffBackgroundColor);
		}
		canvas.drawRoundRect(new RectF(mDividerWidth, mDividerWidth, getWidth() - mDividerWidth, getHeight() - mDividerWidth), getHeight()/2 - mDividerWidth, getHeight()/2 - mDividerWidth, paint);
		//绘制滑块
		paint.setColor(mDividerColor);
		canvas.drawCircle(getHeight()/2+mOffset, getHeight()/2, getHeight()/2, paint);
		if (mValue)
		{
			paint.setColor(mOnSliderColor);
		}
		else
		{
			paint.setColor(mOffSliderColor);
		}
		canvas.drawCircle(getHeight()/2+mOffset, getHeight()/2, getHeight()/2 - mDividerWidth, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		float ratio = DEFAULT_WIDTH / DEFAULT_HEIGHT;
		
		//设置成默认宽高的情况
		if (widthMode == MeasureSpec.AT_MOST || width < DEFAULT_WIDTH)
		{
			width = DEFAULT_WIDTH;
		}
		if (heightMode == MeasureSpec.AT_MOST || height < DEFAULT_HEIGHT)
		{
			height = DEFAULT_HEIGHT;
		}
		
		//设置默认比例
		if (widthMode != MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST)
		{
			//以宽为准
			height = (int)(width / ratio);
		}
		else if (widthMode == MeasureSpec.AT_MOST && heightMode != MeasureSpec.AT_MOST)
		{
			//以高位准
			width = (int)(height * ratio);
		}
		else if (widthMode != MeasureSpec.AT_MOST && heightMode != MeasureSpec.AT_MOST)
		{
			//以最大者为准
			if (width > height)
			{
				height = (int)(width / ratio);
			}
			else
			{
				width = (int)(height * ratio);
			}
		}
		//提交
		setMeasuredDimension(width, height);
	}

	//屏蔽setOnclickListener
	@Override
	public void setOnClickListener(View.OnClickListener l)
	{
		// TODO: Implement this method
		super.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{
					mSetValue(!isValue(), true);
					if (mOnValueChangeListener != null)
					{
						mOnValueChangeListener.onChange(isValue());
					}
				}
			});
	}
	
	public void setValue(final boolean Value, final boolean animation)
	{
		post(new Runnable() {
			public void run()
			{
				mSetValue(Value, animation);
			}
		});
	}
	
	private void mSetValue(boolean Value, final boolean animation)
	{
		this.mValue = Value;
		if (Value)
		{
			//播放打开动画
			new Thread() {
				public void run()
				{
					if (!animation)
					{
						mHandler.sendEmptyMessage(1);
					}
					for(;getOffset() < getWidth()-getHeight();)
					{
						try {
							Thread.sleep(10);
							mHandler.sendEmptyMessage(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		else
		{
			//播放关闭动画
			new Thread() {
				public void run()
				{
					if (!animation)
					{
						mHandler.sendEmptyMessage(3);
					}
					for(;getOffset() > 0;)
					{
						try {
							Thread.sleep(10);
							mHandler.sendEmptyMessage(2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}

	public boolean isValue()
	{
		return mValue;
	}

	public void setOffBackgroundColor(int OffBackgroundColor)
	{
		this.mOffBackgroundColor = OffBackgroundColor;
	}

	public int getOffBackgroundColor()
	{
		return mOffBackgroundColor;
	}

	public void setOnBackgroundColor(int OnBackgroundColor)
	{
		this.mOnBackgroundColor = OnBackgroundColor;
	}

	public int getOnBackgroundColor()
	{
		return mOnBackgroundColor;
	}

	public void setOffSliderColor(int OffSliderColor)
	{
		this.mOffSliderColor = OffSliderColor;
	}

	public int getOffSliderColor()
	{
		return mOffSliderColor;
	}

	public void setOnSliderColor(int OnSliderColor)
	{
		this.mOnSliderColor = OnSliderColor;
	}

	public int getOnSliderColor()
	{
		return mOnSliderColor;
	}

	public void setDividerWidth(int DividerWidth)
	{
		this.mDividerWidth = DividerWidth;
	}

	public int getDividerWidth()
	{
		return mDividerWidth;
	}

	public void setOffDividerColor(int OffDividerColor)
	{
		this.mOffDividerColor = OffDividerColor;
	}

	public int getOffDividerColor()
	{
		return mOffDividerColor;
	}

	public void setOnDividerColor(int OnDividerColor)
	{
		this.mOnDividerColor = OnDividerColor;
	}

	public int getOnDividerColor()
	{
		return mOnDividerColor;
	}
	
	public void setOnValueChangeListener(OnValueChangeListener OnValueChangeListener)
	{
		this.mOnValueChangeListener = OnValueChangeListener;
	}

	public OnValueChangeListener getOnValueChangeListener()
	{
		return mOnValueChangeListener;
	}
	
	public void setOffset(int Offset)
	{
		this.mOffset = Offset;
		invalidate();
	}

	public int getOffset()
	{
		return mOffset;
	}
	
	public interface OnValueChangeListener
	{
		public void onChange(boolean value)
	}
}
