package com.dis.box.CustomView;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import com.dis.box.*;

public class SwitchButton extends View
{
	public SwitchButton(Context context){this(context, null);}
	public SwitchButton(Context context, AttributeSet attrs){super(context, attrs);init(attrs);}

	//默认宽高
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 50;

	//开关值
	private boolean mValue;
	//关闭背景色活，开启背景色
	private int mOffBackgroundColor, mOnBackgroundColor;
	//关闭滑块色，开启滑块色
	private int mOffSliderColor, mOnSliderColor;
	//分割线宽度
	private int mDividerWidth;
	//关闭分割线颜色，开启分割线颜色
	private int mOffDividerColor, mOnDividerColor;
	//滑块偏移量
	//关闭时为0
	//开启时为Width - Height
	private int mOffset;
	//滑块移动持续时间(毫秒为单位)
	private int mDuration;
	//滑块移动速度(移动距离)
	//滑块运动时间隔时长为 Duration / (Width / Offset)
	private int mSpeed;
	//是否开启动画
	//做为默认值在 setValue(boolean Value) 到 setValue(boolean Value, boolean animate) 传递
	private boolean mAnimatable;
	//在值(点击货setting)切换时调用
	private OnValueChangeListener mOnValueChangeListener;
	//Handler 控制动画
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			int offset = getSpeed();
			//0 = 向右偏移
			//1 = 向左偏移
			switch (msg.what)
			{
				//动画开启
				case 0:
					//判断是否偏移后会越界
					if (offset + getOffset() > getWidth() - getHeight())
					{
						offset = getWidth() - getHeight() - getOffset();
					}
					setOffset(getOffset() + offset);
					break;
				//直接开启
				case 1:
					setOffset(getWidth() - getHeight());
					break;
				//动画关闭
				case 2:
					//判断是否偏移后会越界
					if (getOffset() - offset < 0)
					{
						offset = getOffset();
					}
					setOffset(getOffset() - offset);
					break;
				//直接关闭
				case 3:
					setOffset(0);
					break;
			}
		}
	};

	//初始化
	private void init(AttributeSet attrs)
	{
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButtom);
		
		setSpeed(typedArray.getInteger(R.styleable.SwitchButtom_speed, 1));
		setOffBackgroundColor(typedArray.getColor(R.styleable.SwitchButtom_off_background_color, Color.GRAY));
		setOnBackgroundColor(typedArray.getColor(R.styleable.SwitchButtom_on_background_color, Color.GREEN));
		setOffSliderColor(typedArray.getColor(R.styleable.SwitchButtom_off_slider_color, Color.WHITE));
		setOnSliderColor(typedArray.getColor(R.styleable.SwitchButtom_on_slider_color, Color.WHITE));
		setOffDividerColor(typedArray.getColor(R.styleable.SwitchButtom_off_divider_color, Color.CYAN));
		setOnDividerColor(typedArray.getColor(R.styleable.SwitchButtom_on_divider_color, Color.GREEN));
		setDividerWidth(typedArray.getInteger(R.styleable.SwitchButtom_divider_width, 2));
		setAnimatable(typedArray.getBoolean(R.styleable.SwitchButtom_animatable, true));
		setDuration(typedArray.getInteger(R.styleable.SwitchButtom_duration, 250));
		setOffset(0);
		setOnValueChangeListener(null);
		setOnClickListener(null);
		
		typedArray.recycle();
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
					setValue(!isValue());
					if (mOnValueChangeListener != null)
					{
						mOnValueChangeListener.onChange(isValue());
					}
				}
			});
	}
	
	public void setValue(final boolean Value)
	{
		setValue(Value, isAnimatable());
	}
	
	public void setValue(final boolean Value, final boolean animation)
	{
		this.mValue = Value;
		post(new Runnable() {
			public void run()
			{
				mSetValue(Value, animation);
			}
		});
	}
	
	private static Thread mAnimateThread = null;
	private void mSetValue(boolean Value, final boolean animation)
	{
		//结束掉是一个存活的动画线程
		if (mAnimateThread != null && mAnimateThread.isAlive())
		{
			mAnimateThread.interrupt();
		}
		//设置动画线程
		if (Value)
		{
			mAnimateThread = new Thread(){
				public void run()
				{
					//直接到开启状态
					if (!isAnimatable())
					{
						mHandler.sendEmptyMessage(1);
					}
					//循环到满足Duratin时间并动画设置到开启状态
					for(;getOffset() < getWidth()-getHeight();)
					{
						try {
							//睡眠线程
							Thread.sleep(getDuration()/(getWidth()/getSpeed()));
							mHandler.sendEmptyMessage(0);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
						if (isInterrupted())
						{
							return;
						}
					}
				}
			};
		}
		else
		{
			mAnimateThread = new Thread(){
				public void run()
				{
					//直接到关闭状态
					if (!isAnimatable())
					{
						mHandler.sendEmptyMessage(3);
					}
					//循环到满足Duratin时间并动画设置到关闭状态
					for(;getOffset() > 0;)
					{
						try {
							//睡眠线程
							Thread.sleep(getDuration()/(getWidth()/getSpeed()));
							mHandler.sendEmptyMessage(2);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
						if (isInterrupted())
						{
							return;
						}
					}
				}
			};
		}
		//启动动画线程
		mAnimateThread.start();
	}

	public void setSpeed(int mSpeed)
	{
		this.mSpeed = mSpeed;
	}

	public int getSpeed()
	{
		return mSpeed;
	}

	public void setDuration(int Duration)
	{
		this.mDuration = Duration;
	}

	public int getDuration()
	{
		return mDuration;
	}

	public void setAnimatable(boolean Animatable)
	{
		this.mAnimatable = Animatable;
	}

	public boolean isAnimatable()
	{
		return mAnimatable;
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
