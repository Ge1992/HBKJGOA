package com.example.hbkjgoa.sjyw;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.StatusBarUtils;


public class Xxglactivity extends FragmentActivity {

	Fragment fragment1;
	Fragment fragment2;
	Fragment fragment3;
	TextView t1, t2, t3;
	ImageView imahe;
	LinearLayout L1, L2, L3;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xxglxml);
		initView();
		setOnclick();
		initStatusBar();
		initFragment(0);
	}

	private void initView() {

		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		imahe=(ImageView) findViewById(R.id.imahe);
	}
	public void bt_back(View v){
		finish();
	}
	private void setOnclick() {

		

		t1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(0);
				t1.setTextColor(Color.rgb(255, 0, 0));
				t2.setTextColor(Color.rgb(0, 0, 0));
				t3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		t2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(1);
				t2.setTextColor(Color.rgb(255, 0, 0));
				t1.setTextColor(Color.rgb(0, 0, 0));
				t3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		t3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(2);
				t3.setTextColor(Color.rgb(255, 0, 0));
				t1.setTextColor(Color.rgb(0, 0, 0));
				t2.setTextColor(Color.rgb(0, 0, 0));

			}
		});
	}

	private void initFragment(int index) {
		// 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 开启事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 隐藏所有Fragment
		hideFragment(transaction);
		switch (index) {
			case 0:
				if (fragment1 == null) {
					fragment1 = new two();
					Bundle bundle = new Bundle();
					bundle.putString("mc", "国家");
					fragment1.setArguments(bundle);
					transaction.add(R.id.ZTQK_content, fragment1);
				} else {
					transaction.show(fragment1);
				}
				break;
			case 1:
				if (fragment2 == null) {
					fragment2 = new two();
					Bundle bundle = new Bundle();
					bundle.putString("mc", "省");
					fragment2.setArguments(bundle);
					transaction.add(R.id.ZTQK_content, fragment2);
				} else {
					transaction.show(fragment2);
				}

				break;
			case 2:
				if (fragment3 == null) {
					fragment3 = new two();
					Bundle bundle = new Bundle();
					bundle.putString("mc", "市");
					fragment3.setArguments(bundle);
					transaction.add(R.id.ZTQK_content, fragment3);
				} else {
					transaction.show(fragment3);
				}

				break;

			default:
				break;
		}
		// 提交事务
		transaction.commit();
	} // 隐藏Fragment

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
			StatusBarUtils.setStatusBarColor(Xxglactivity.this, R.color.btground);
		}
	}
}
