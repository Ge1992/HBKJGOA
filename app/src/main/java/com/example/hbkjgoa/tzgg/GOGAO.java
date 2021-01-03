package com.example.hbkjgoa.tzgg;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.example.hbkjgoa.FirstFragment;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class GOGAO extends Activity {

	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TabHost tabHost = null;
	TextView t1,t2,t3,tile;
	 Button serch;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor;// 动画图片
	   private String LX = "";
	public static GOGAO listact;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ggao);
		listact = this;
		context = GOGAO.this;
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);
		initTextView();
		 LX = this.getIntent().getStringExtra("LX");
		 
		 tile.setText(LX);
		InitImageView();
		
		initPagerViewer();
		initStatusBar();
	}
	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		tile=(TextView) findViewById(R.id.Text2);
	
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));

		serch=(Button) findViewById(R.id.serch);
		serch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GOGAO.this, addtzgg.class);
				startActivity(intent);
			}
		});



	}
	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, TaskActivity.class);
		intent.putExtra("LX",LX);
        intent.putExtra("zt","2");
		list.add(getView("A", intent));

		Intent intent2 = new Intent(context, TaskActivity.class);
		intent2.putExtra("LX",LX);
        intent2.putExtra("zt","0");

		list.add(getView("B", intent2));

		Intent intent3 = new Intent(context, TaskActivity2.class);
		intent3.putExtra("LX",LX);
		list.add(getView("C", intent3));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.roller).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.ggao, menu);
		return true;
	}
	/**
	 * 通过activity获取视图
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	/**
	 * Pager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		List<View> list =  new ArrayList<View>();
		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position,
                                Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					t1.setTextColor(Color.rgb(255, 0, 0));
					t2.setTextColor(Color.rgb(0, 0, 0));
					t3.setTextColor(Color.rgb(0, 0, 0));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					t1.setTextColor(Color.rgb(255, 0, 0));
					t2.setTextColor(Color.rgb(0, 0, 0));
					t3.setTextColor(Color.rgb(0, 0, 0));
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
					t2.setTextColor(Color.rgb(255, 0, 0));
					t1.setTextColor(Color.rgb(0, 0, 0));
					t3.setTextColor(Color.rgb(0, 0, 0));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					t2.setTextColor(Color.rgb(255, 0, 0));
					t1.setTextColor(Color.rgb(0, 0, 0));
					t3.setTextColor(Color.rgb(0, 0, 0));
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
					t3.setTextColor(Color.rgb(255, 0, 0));
					t1.setTextColor(Color.rgb(0, 0, 0));
					t2.setTextColor(Color.rgb(0, 0, 0));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					t3.setTextColor(Color.rgb(255, 0, 0));
					t1.setTextColor(Color.rgb(0, 0, 0));
					t2.setTextColor(Color.rgb(0, 0, 0));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}
	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	};
	


	public void bt_back(View v){
		if(FirstFragment.instance!=null){
			FirstFragment.instance.setInfo2();
		}
		finish();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取

			if(FirstFragment.instance!=null){
				FirstFragment.instance.setInfo2();
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
			StatusBarUtils.setStatusBarColor(GOGAO.this, R.color.btground);
		}
	}
}
