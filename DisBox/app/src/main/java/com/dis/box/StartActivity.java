package com.dis.box;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.blankj.utilcode.util.*;
import com.bumptech.glide.*;
import com.dis.box.Other.*;
import com.dis.box.Util.*;
import java.io.*;
import java.util.*;
import okhttp3.*;


public class StartActivity extends BaseActivity
{
	private ImageView mImage;
	private TextView mCountDownTv;
	private LinearLayout mCountDown;
	private Intent intent;
	private CountDownTimer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_layout);

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String bingPic = prefs.getString("bingPic", null);
		Date saveDate = new Date(prefs.getLong("saveDate", 0)), nowDate = new Date();
		
		mImage = (ImageView)findViewById(R.id.startImage);
		mCountDownTv = (TextView)findViewById(R.id.startCountDownTime);
		mCountDown = (LinearLayout)findViewById(R.id.startCountDown);
		
		intent = new Intent(StartActivity.this, MainActivity.class);
		
		//加载必应每日一图
//		if (bingPic != null && saveDate.getDay() == nowDate.getDay())
//		{
//			Glide
//				.with(StartActivity.this)
//				.load(bingPic)
//				//.bitmapTransform(new BlurTransformation(this))
//				.into(mImage);
//		}
//		else
//		{
//			loadBingPic();
//		}
			
		BarUtils.setStatusBarAlpha(this, 160);
		BarUtils.setNavBarImmersive(this);
	}
	
	/**
	*加载必应每日一图
	*/
	private void loadBingPic()
	{
		String requestBingPic = "http://guolin.tech/api/bing_pic";
		HttpUtil.sendOkHttpRequest(requestBingPic, new okhttp3.Callback() {
			public void onResponse(okhttp3.Call call, Response response) throws IOException
			{
				final String bingPic = response.body().string();
				StartActivity.this.getPreferences(StartActivity.this.MODE_PRIVATE).edit()
					.putLong("saveDate", new Date().getTime())
					.putString("bingPic", bingPic)
					.commit();
				runOnUiThread(new Runnable() {
					public void run()
					{
						Glide
							.with(StartActivity.this)
							.load(bingPic)
							//.bitmapTransform(new BlurTransformation(StartActivity.this))
							.into(mImage);
					}
				});
			}
			public void onFailure(Call call, IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
//
		timer = new CountDownTimer(3000, 1000){
			public void onTick(long p1)
			{
				mCountDownTv.setText(p1/1000+"s");
			}
			public void onFinish()
			{
				startActivity(intent);
				finish();
			}
		}.start();

		mCountDown.setOnClickListener(new OnClickListener() {
				public void onClick(View v)
				{
					timer.cancel();
					startActivity(intent);
					finish();
				}
			});
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
	}
	
}
