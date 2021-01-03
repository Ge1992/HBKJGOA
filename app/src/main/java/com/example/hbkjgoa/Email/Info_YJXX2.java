package com.example.hbkjgoa.Email;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Xmjl_sjxBean;
import com.example.hbkjgoa.util.StatusBarUtils;


public class Info_YJXX2 extends Activity {
	private TextView t1,t2,t3,t4,t5,t1_1;
	private Button fj;
	private Xmjl_sjxBean ap;
	private LinearLayout L1;
	private String name = "";
	private RelativeLayout info_tab;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_xmjl2);
		initStatusBar();

		FindView();
		SetClicI();
	}
	private void FindView(){
		t1=(TextView) findViewById(R.id.t1);
		t2=(TextView) findViewById(R.id.t2);
		t3=(TextView) findViewById(R.id.t3);
		t4=(TextView) findViewById(R.id.t4);
		t5=(TextView) findViewById(R.id.t5);
		fj=(Button) findViewById(R.id.fj);
		L1=(LinearLayout) findViewById(R.id.L1);
		info_tab=(RelativeLayout) findViewById(R.id.info_tab);
	
	}
	
	private void SetClicI(){
		Intent intent = this.getIntent();
	    ap=(Xmjl_sjxBean) intent.getSerializableExtra("Xmjl_sjxBean");
	    t1.setText(ap.getEmailTitle());
	    t2.setText(ap.getFromUser());
	    t3.setText(ap.getToUser());
	    t4.setText(ap.getTimeStr());
	    t5.setText(ap.getEmailContent());

	    fj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent();
				intent.setClass(Info_YJXX2.this, ZlglInfo4.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Xmjl_sjxBean", ap);
				Log.d("附件", ap.getFuJian());
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
	    
	
	    
	    info_tab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Info_YJXX2.this, AddEmail2.class);
				intent.putExtra("name", "2");
				intent.putExtra("name1", ap.getEmailTitle());
				intent.putExtra("name2", ap.getFromUser());
				intent.putExtra("xmmc", ap.getXMMC());
				intent.putExtra("id", ap.getID());
				startActivity(intent);
				
			}
		});
	    
	}
	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */
	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(Info_YJXX2.this, R.color.btground);
		}
	}
	
	
	public void bt_back(View v) {
		XMJL_SJX.listact.onRefresh();
		this.finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			XMJL_SJX.listact.onRefresh();

			this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
