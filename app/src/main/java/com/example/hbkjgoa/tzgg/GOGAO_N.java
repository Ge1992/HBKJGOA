package com.example.hbkjgoa.tzgg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.SY_New;
import com.example.hbkjgoa.news.SY_News;
import com.example.hbkjgoa.util.StatusBarUtils;

public class GOGAO_N extends FragmentActivity {
	public static GOGAO_N list_Act;
	Fragment fragment1;
	Fragment fragment2;
	Fragment fragment3;
	TextView L1, L2, L3,Tile;
	boolean isExit;
	private Button add;
	private String LX = "", name = "",MB="",which="0",MBID="";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ggao_new);
		list_Act=this;
		LX = this.getIntent().getStringExtra("LX");

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

				L1.setTextColor(Color.rgb(255, 0, 0));
				L2.setTextColor(Color.rgb(0, 0, 0));
				L3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		L2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(1);

				L2.setTextColor(Color.rgb(255, 0, 0));
				L1.setTextColor(Color.rgb(0, 0, 0));
				L3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		L3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(2);
				L3.setTextColor(Color.rgb(255, 0, 0));
				L1.setTextColor(Color.rgb(0, 0, 0));
				L2.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		add=(Button) findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GOGAO_N.this, addtzgg.class);
				startActivity(intent);
			}
		});

	}

	private void initView() {

		add = (Button) findViewById(R.id.add);
		L1=(TextView) findViewById(R.id.text1);
		L2=(TextView) findViewById(R.id.text2);
		L3=(TextView) findViewById(R.id.text3);
		Tile=(TextView) findViewById(R.id.Tile);
	//	Tile.setText(LX);


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
					fragment1 = new TaskActivity();
					Bundle bundle = new Bundle();
					bundle.putString("LX",LX);
					bundle.putString("zt","2");
					fragment1.setArguments(bundle);
					transaction.add(R.id.ZTQK_content, fragment1);
				} else {
					transaction.show(fragment1);
				}
				break;
			case 1:
				if (fragment2 == null) {

					fragment2 = new TaskActivity();
					Bundle bundle = new Bundle();
					bundle.putString("LX",LX);
					bundle.putString("zt","0");
					fragment2.setArguments(bundle);
					transaction.add(R.id.ZTQK_content, fragment2);

				} else {
					transaction.show(fragment2);
				}

				break;
			case 2:
				if (fragment3 == null) {

					fragment3 = new TaskActivity2();
					Bundle bundle = new Bundle();
					bundle.putString("LX",LX);
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

	public void bt_back(View v){
		/*if(FirstFragment.instance!=null){
			FirstFragment.instance.setInfo2();
		}*/
		if(SY_News.instance!=null){
			SY_News.instance.setInfo2();
		}
		finish();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取

			if(SY_News.instance!=null){
				SY_News.instance.setInfo2();
			}

			finish();

		}
		return false;
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
			StatusBarUtils.setStatusBarColor(GOGAO_N.this, R.color.btground);
		}
	}
}
