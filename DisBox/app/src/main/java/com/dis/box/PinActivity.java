package com.dis.box;

import android.os.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.andrognito.pinlockview.*;
import com.blankj.utilcode.util.*;
import com.dis.box.Util.*;
import com.wevey.selector.dialog.*;

public class PinActivity extends AppCompatActivity
{
	private PinLockView mPinLockView;
	private EditText mEdt;
	public static boolean isExists;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//Toast.makeText(this, LocalUtils.getPinLockPassword(this), Toast.LENGTH_SHORT).show();
		if (isExists || !LocalUtils.isLock(this))
		{
			finish();
		}
		isExists = true;
		setContentView(R.layout.pin_layout);
		
		mPinLockView = (PinLockView)findViewById(R.id.pinLock);
		mEdt = (EditText)findViewById(R.id.pinEditText);
		
		mPinLockView.setPinLength(10);
		mPinLockView.setTextSize(40);
		mPinLockView.setButtonSize(120);
		mPinLockView.setPinLockListener(new PinLockListener() {
			public void onComplete(java.lang.String p1)
			{
				mPinLockView.resetPinLockView();
			}
			public void onEmpty()
			{
				mEdt.setText("");
				if ("".equals(LocalUtils.getPinLockPassword(PinActivity.this)))
				{
					finish();
				}
			}
			public void onPinChange(int p1, java.lang.String p2)
			{
				if (p2.equals(LocalUtils.getPinLockPassword(PinActivity.this)))
				{
					finish();
				}
				mEdt.setText(p2);
			}
		});
		findViewById(R.id.pinEncrypted).setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{new MDEditDialog.Builder(PinActivity.this)
					.setTitleText("重置密码")
					.setInputTpye(InputType.TYPE_CLASS_TEXT)
					.setHintText(LocalUtils.getPinLockEncryptedProblem(PinActivity.this))
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
							if (dialog.getEditTextContent().equals(LocalUtils.getPinLockEncryptedAnswer(PinActivity.this)))
							{
								Toast.makeText(PinActivity.this, "密码已重置:123456", Toast.LENGTH_SHORT).show();
								LocalUtils.setPinLockPassword(PinActivity.this, "123456");
								finish();
								dialog.dismiss();
							}
							else
							{
								Toast.makeText(PinActivity.this, "答案错误", Toast.LENGTH_SHORT).show();
							}
						}
					})
					.build().show();
			}
		});
		
		BarUtils.setStatusBarAlpha(this, 160);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		isExists = false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		return false;
	}
	
}
