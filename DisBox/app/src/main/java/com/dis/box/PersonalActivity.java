package com.dis.box;

import com.dis.box.Other.*;
import android.os.*;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import com.dis.box.Util.*;
import com.blankj.utilcode.util.*;
import com.bumptech.glide.*;
import android.support.design.widget.*;
import android.view.*;
import de.hdodenhof.circleimageview.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.View.*;
import com.wevey.selector.dialog.*;
import java.util.*;
import android.text.*;

public class PersonalActivity extends BasePageActivity
{
	private Toolbar mToolbar;
	private ImageView mToolbarBackground;
	private CollapsingToolbarLayout mCollapsingToolbarLayout;
	private AppBarLayout mAppBarLayout;
	private View mToolbarBackgroundShade;
	private CircleImageView mCircleImageView;
	private ImageView mMore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_layout);
		
		Drawable more = getResources().getDrawable(R.drawable.icon_more);
		
		mToolbar = (Toolbar)findViewById(R.id.personalToolbar);
		mToolbarBackground = (ImageView)findViewById(R.id.personalToolbarBackground);
		mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.personalCollapsingToolbar);
		mAppBarLayout = (AppBarLayout)findViewById(R.id.personalAppBar);
		mToolbarBackgroundShade = findViewById(R.id.personalToolbarBackgroundShade);
		mCircleImageView = (CircleImageView)findViewById(R.id.personalCircleImageView1);
		mMore = (ImageView)findViewById(R.id.personalMore);
		
		if (LocalUtils.getHeadPortraitFile().exists())
		{
			mCircleImageView.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getHeadPortraitFile()));
		}
		else
		{
			mCircleImageView.setImageDrawable(getResources().getDrawable(R.drawable.test_head));
		}
		if (LocalUtils.getPersonalToolbarBackgroundFile().exists())
		{
			mToolbarBackground.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getPersonalToolbarBackgroundFile()));
		}
		else
		{
			mToolbarBackground.setImageDrawable(getResources().getDrawable(R.drawable.def_bg));
		}
		mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
		{
				@Override
				public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
				{
					int height = appBarLayout.getHeight() - 100;
					float SlipPercentage = -((float)verticalOffset) / ((float)height);
					mToolbarBackground.setAlpha(1f-SlipPercentage);
					mToolbarBackgroundShade.setAlpha(1f-SlipPercentage);
					mCircleImageView.setTranslationX((mCollapsingToolbarLayout.getWidth()-mCircleImageView.getWidth())/2);
					mCircleImageView.setTranslationY((mCollapsingToolbarLayout.getHeight()-mCircleImageView.getHeight())/2);
					mCircleImageView.setScaleX(1f-SlipPercentage);
					mCircleImageView.setScaleY(1f-SlipPercentage);
				}
		});

		more.setTint(Color.WHITE);
		mMore.setImageDrawable(more);
		mMore.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				NormalSelectionDialog dialog = new NormalSelectionDialog.Builder(PersonalActivity.this)
				.setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>(){
						public void onItemClick(NormalSelectionDialog p1, View p2, int p3)
						{
							if (p3 == 0)
							{
								requestPicture(0);
							}
							else if (p3 == 1)
							{
								requestPicture(1);
							}
							else if (p3 == 2)
							{
								new MDEditDialog.Builder(PersonalActivity.this)
									.setTitleText("设置名称")
									.setInputTpye(InputType.TYPE_CLASS_TEXT)
									.setHintText("名称")
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
											dialog.dismiss();
											LocalUtils.setUserName(PersonalActivity.this, dialog.getEditTextContent());
											mCollapsingToolbarLayout.setTitle(LocalUtils.getUserName(PersonalActivity.this));
											Toast.makeText(PersonalActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
										}
									}).build().show();
							}
							p1.dismiss();
						}
				})
				.build();
				List<String> list = new ArrayList<String>();
				list.add("更换头像");
				list.add("更换背景");
				list.add("更改名称");
				dialog.setDatas(list);
				dialog.show();
			}
		});
		mCollapsingToolbarLayout.setTitle(LocalUtils.getUserName(this));
		mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
		mCollapsingToolbarLayout.setExpandedTitleMarginBottom(30);
		mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutTitleTextAppearance);
		mToolbar.setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		BarUtils.setStatusBarAlpha(this, 160);
	}

	@Override
	protected void callBackOnRequestPicture(String path, int id, int result)
	{
		// TODO: Implement this method
		super.callBackOnRequestPicture(path, id, result);
		if (id == 0)
		{
			FileUtils.copyFile(path, LocalUtils.getHeadPortraitFile().getAbsolutePath());
			mCircleImageView.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getHeadPortraitFile()));
		}
		else if (id == 1)
		{
			FileUtils.copyFile(path, LocalUtils.getPersonalToolbarBackgroundFile().getAbsolutePath());
			mToolbarBackground.setImageBitmap(ImageUtils.getBitmap(LocalUtils.getPersonalToolbarBackgroundFile()));
		}
		if (result == RESULT_OK)
		{
			Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
		}
	}
	
}
