package com.example.hbkjgoa;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.Email.ThirdFragment;
import com.example.hbkjgoa.txl.SecondFragment;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.zxing.activity.CaptureActivity;

public class SY_New extends FragmentActivity {
	Fragment fragment1;
	Fragment fragment2;
	Fragment fragment3;
	Fragment fragment4;
	Fragment fragment5;
	public static SY_New instance = null;
	LinearLayout L1, L2, L3, L4;
	TextView t1, t2, t3, t4,title;
	boolean isExit;

	private ImageView mTab1, mTab2, mTab3, mTab4, mTab5;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a);
		instance = this;
		initView();
		setOnclick();
		initFragment(0);
		initStatusBar();
	}
	private void setOnclick() {
		L1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(0);
				mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.nav1_pas));
				mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.nav3));
				mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.nav2));
				mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.nav4));
				
				t1.setTextColor(Color.rgb(58, 181, 241));
				t2.setTextColor(Color.rgb(154, 154, 154));
				t3.setTextColor(Color.rgb(154, 154, 154));
				t4.setTextColor(Color.rgb(154, 154, 154));
				title.setText("工作台");
			}
		});
		L2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(1);
				mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.nav3_pas));
				mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.nav1));
				mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.nav2));
				mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.nav4));
				
				t2.setTextColor(Color.rgb(58, 181, 241));
				t1.setTextColor(Color.rgb(154, 154, 154));
				t3.setTextColor(Color.rgb(154, 154, 154));
				t4.setTextColor(Color.rgb(154, 154, 154));
				title.setText("通讯录");
			}
		});
		L3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(2);
				mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.nav2_pas));
				mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.nav1));
				mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.nav3));
				mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.nav4));
				
				t3.setTextColor(Color.rgb(58, 181, 241));
				t1.setTextColor(Color.rgb(154, 154, 154));
				t2.setTextColor(Color.rgb(154, 154, 154));
				t4.setTextColor(Color.rgb(154, 154, 154));
				title.setText("我的邮件");
			}
		});
		L4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(3);
				mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.nav4_pas));
				mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.nav1));
				mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.nav3));
				mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.nav2));
				
				t4.setTextColor(Color.rgb(58, 181, 241));
				t1.setTextColor(Color.rgb(154, 154, 154));
				t2.setTextColor(Color.rgb(154, 154, 154));
				t3.setTextColor(Color.rgb(154, 154, 154));
				title.setText("我的");
			}
		});

	}

	private void initView() {
		L1 = (LinearLayout) findViewById(R.id.L1);
		L2 = (LinearLayout) findViewById(R.id.L2);
		L3 = (LinearLayout) findViewById(R.id.L3);
		L4 = (LinearLayout) findViewById(R.id.L4);

		mTab1 = (ImageView) findViewById(R.id.img_address);
		mTab2 = (ImageView) findViewById(R.id.img_friends);
		mTab3 = (ImageView) findViewById(R.id.img_settings);
		mTab4 = (ImageView) findViewById(R.id.img_weixin);
		
		t1=(TextView) findViewById(R.id.t1);
		t2=(TextView) findViewById(R.id.t2);
		t3=(TextView) findViewById(R.id.t3);
		t4=(TextView) findViewById(R.id.t4);
		title=findViewById(R.id.title_text_tv);

		//点击触发扫码功能
		ImageView button = findViewById(R.id.core);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//判断Android版本是否为6.0以上，如果是则进行权限允许
				if (Build.VERSION.SDK_INT >= 23){
					int checkCallPhonePermission = ContextCompat.checkSelfPermission(SY_New.this, android.Manifest.permission.CAMERA);
					if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
						ActivityCompat.requestPermissions(SY_New.this,new String[]{Manifest.permission.CAMERA},222);
						return;
					}
				}
		//		startActivity(new Intent(SY_New.this, CaptureActivity.class));
				Intent intent = new Intent();
				intent.putExtra("tile", "");
				intent.setClass(SY_New.this,CaptureActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initFragment(int index) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (index) {
		case 0:
			if (fragment1 == null) {
				fragment1 = new FirstFragment();

				transaction.add(R.id.ZTQK_content, fragment1);

			} else {
				transaction.show(fragment1);
			}
			break;
		case 1:
			if (fragment2 == null) {
				fragment2 = new SecondFragment();

				transaction.add(R.id.ZTQK_content, fragment2);

			} else {
				transaction.show(fragment2);
			}

			break;
		case 2:
			if (fragment3 == null) {
				fragment3 = new ThirdFragment();

				transaction.add(R.id.ZTQK_content, fragment3);

			} else {
				transaction.show(fragment3);
			}

			break;
		case 3:
			if (fragment4 == null) {
				fragment4 = new FiveFragment();

				transaction.add(R.id.ZTQK_content, fragment4);

			} else {
				transaction.show(fragment4);
			}

			break;

		default:
			break;
		}
		transaction.commit();
	}
	
	private void hideFragment(FragmentTransaction transaction) {
		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
		if (fragment3 != null) {
			transaction.hide(fragment3);
		}
		if (fragment4 != null) {
			transaction.hide(fragment4);
		}

	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
	}
	
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		 
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
 
    };
public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */

	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(SY_New.this, R.color.btground);
		}
	}
}
