package com.dis.box.Other;
import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.dis.box.*;
import java.io.*;
import java.lang.reflect.*;

import android.support.v7.widget.Toolbar;

public class BasePageActivity extends BaseActivity implements SlidingPaneLayout.PanelSlideListener
{
    private final static String WINDOWBITMAP = "screenshots.jpg";
    private File mFileTemp;
    private SlidingPaneLayout slidingPaneLayout;
    private FrameLayout frameLayout;
    private ImageView behindImageView;
    private ImageView shadowImageView;
    private int defaultTranslationX = 100;
    private int shadowWidth = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        //通过反射来改变SlidingPanelayout的值
        try
		{
            slidingPaneLayout = new SlidingPaneLayout(this);
            Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            f_overHang.setAccessible(true);
            f_overHang.set(slidingPaneLayout, 0);
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        mFileTemp = new File(getCacheDir(), WINDOWBITMAP);
        defaultTranslationX = dip2px(defaultTranslationX);
        shadowWidth = dip2px(shadowWidth);
        //behindframeLayout
        FrameLayout behindframeLayout = new FrameLayout(this);
        behindImageView = new ImageView(this);
        behindImageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        behindframeLayout.addView(behindImageView, 0);

        //containerLayout
        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);
        containerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        containerLayout.setLayoutParams(new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth() + shadowWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        //you view container
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //add shadow
        shadowImageView = new ImageView(this);
        shadowImageView.setBackgroundResource(R.drawable.shadow);
        shadowImageView.setLayoutParams(new LinearLayout.LayoutParams(shadowWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        containerLayout.addView(shadowImageView);
        containerLayout.addView(frameLayout);
        containerLayout.setTranslationX(-shadowWidth);
        //添加两个view
        slidingPaneLayout.addView(behindframeLayout, 0);
        slidingPaneLayout.addView(containerLayout, 1);
		
		//关闭双击退出
		setExit(true);
	}
    @Override
    public void setContentView(int id)
	{
        setContentView(getLayoutInflater().inflate(id, null));
    }

    @Override
    public void setContentView(View v)
	{
        setContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        try
		{
            behindImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            behindImageView.setImageBitmap(getBitmap());
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(View v, ViewGroup.LayoutParams params)
	{
        super.setContentView(slidingPaneLayout, params);
        frameLayout.removeAllViews();
        frameLayout.addView(v, params);
    }


    @Override
    public void onPanelClosed(View view)
	{

    }

    @Override
    public void onPanelOpened(View view)
	{
        finish();
        this.overridePendingTransition(0, 0);
    }

    @Override
    public void onPanelSlide(View view, float v)
	{
        //duang duang duang 你可以在这里加入很多特效
        behindImageView.setTranslationX(v * defaultTranslationX - defaultTranslationX);
        shadowImageView.setAlpha(v < 0.8 ?1: (1.5f - v));
    }

    /**
     * 取得视觉差背景图
     *
     * @return
     */
    public Bitmap getBitmap()
	{
        return BitmapFactory.decodeFile(mFileTemp.getAbsolutePath());
    }

    /**
     * 启动视觉差返回Activity
     *
     * @param activity
     * @param intent
     */
    public void startParallaxSwipeBackActivty(Activity activity, Intent intent)
	{
        startParallaxSwipeBackActivty(activity, intent, false);
    }

    /**
     * startParallaxSwipeBackActivty
     *
     * @param activity
     * @param intent
     * @param isFullScreen
     */
    public void startParallaxSwipeBackActivty(Activity activity, Intent intent, boolean isFullScreen)
	{
        screenshots(activity, isFullScreen);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * this screeshots form
     *
     * @param activity
     * @param isFullScreen
     */
    public void screenshots(Activity activity, boolean isFullScreen)
	{
        try
		{
            //View是你需要截图的View
            View decorView = activity.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap b1 = decorView.getDrawingCache();
            // 获取状态栏高度 /
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            // 获取屏幕长和高 Get screen width and height
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            int height = activity.getWindowManager().getDefaultDisplay().getHeight();
            // 去掉标题栏 Remove the statusBar Height
            Bitmap bitmap;
            if (isFullScreen)
			{
                bitmap = Bitmap.createBitmap(b1, 0, 0, width, height);
            }
			else
			{
                bitmap = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
            }
            decorView.destroyDrawingCache();
            FileOutputStream out = new FileOutputStream(mFileTemp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue)
	{
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		//设置ActionBar
		ViewGroup group = ((ViewGroup)findViewById(android.R.id.content));
		Toolbar bar = getToolbar(group);
		if (bar != null)
		{
			bar.setTitle("");
			setSupportActionBar(bar);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		//设置状态栏及
		BarUtils.setStatusBarAlpha(this, 160);
	}
	//获取Toolbar
	private Toolbar getToolbar(ViewGroup group)
	{
		for (int i = 0;i < group.getChildCount();i++)
		{
			View view = group.getChildAt(i);
			//是Toolbar
			if (view instanceof Toolbar)
			{
				return (Toolbar)view;
			}
			//是一个Group
			else if (view instanceof ViewGroup)
			{
				Toolbar bar = getToolbar((ViewGroup)group.getChildAt(i));
				if (bar != null)
				{
					return bar;
				}
			}
		}
		return null;
	}
	
	//请求图片
	protected void requestPicture(int requestCode)
	{
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.setType("image/*");
		startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			if (Build.VERSION.SDK_INT >= 19)
			{
				handleImageOnKitKat(data, requestCode, resultCode);
			}
			else
			{
				handleImageBeforeKitKat(data, requestCode, resultCode);
			}
		}
	}

	//图片请求回调
	protected void callBackOnRequestPicture(String path, int id, int result)
	{
	}

	private void handleImageOnKitKat(Intent data, int requestCode, int result)
	{
		String imagePath = null;
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(this, uri))
		{
			String docId = DocumentsContract.getDocumentId(uri);
			if ("com.android.providers.media.documents".equals(uri.getAuthority()))
			{
				String id = docId.split(":")[1];
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			}
			else if ("com.android.providers.downloads.documents".equals(uri.getAuthority()))
			{
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}
		}
		else if ("content".equalsIgnoreCase(uri.getScheme()))
		{
			imagePath = getImagePath(uri, null);
		}
		else if ("file".equalsIgnoreCase(uri.getScheme()))
		{
			imagePath = uri.getPath();
		}
		callBackOnRequestPicture(imagePath, requestCode, result);
	}
	private void handleImageBeforeKitKat(Intent data, int requestCode, int result)
	{
		Uri uri = data.getData();
		String imagePath = getImagePath(uri, null);
		callBackOnRequestPicture(imagePath, requestCode, result);
	}

	private String getImagePath(Uri uri, String selection)
	{
		String path = null;
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null)
		{
			if (cursor.moveToFirst())
			{
				path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:////主键id 必须这样写
				onBackPressed();//按返回图标直接回退上个界面
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
