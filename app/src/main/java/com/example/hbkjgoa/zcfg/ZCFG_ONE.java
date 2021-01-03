package com.example.hbkjgoa.zcfg;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;


public class ZCFG_ONE extends Activity {
	private RelativeLayout R1, R2, R3, R4;
	private TextView t2;
	private String yjs,json,spname,Department;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				if(json!=null&&!json.equals("")){
					String[] a=json.split(",");
				//	t2.setText(a[1]); 接口报错
			    	
				}
			}
			
			
			
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zcsl);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		Department = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("Department", "");
		FindView();
		SetClic();
		setInfo();
		initStatusBar();
	}

	private void FindView() {
		R1 = (RelativeLayout) findViewById(R.id.Rx1);
		R2 = (RelativeLayout) findViewById(R.id.Rx2);
		R3 = (RelativeLayout) findViewById(R.id.Rx3);
		R4 = (RelativeLayout) findViewById(R.id.Rx4);
		
		
		t2=(TextView) findViewById(R.id.t2);
		Intent intent = getIntent();//获取传来的intent对象
	//	yjs = intent.getStringExtra("yjs");//获取键值对的键名
	//	t2.setText(yjs);
	}

	private void SetClic() {
		//写邮件
		R1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ZCFG_ONE.this, ZCFG_TWO.class);
				intent.putExtra("LX", "国家政策");
				startActivity(intent);
			}
		});
		//收件箱
		R2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ZCFG_ONE.this, ZCFG_TWO.class);
				intent.putExtra("LX", "省政策");
				startActivity(intent);
			}
		});
		//草稿箱
		R3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ZCFG_ONE.this, ZCFG_TWO.class);
				intent.putExtra("LX", "市政策");
				startActivity(intent);
			}
		});
		//已删除
		R4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ZCFG_ONE.this, ZCFG_TWO.class);
				intent.putExtra("LX", "区政策");
				startActivity(intent);
			}
		});
		
	
	}

	
	
	public void setInfo() {
		boolean havenet = NetHelper.IsHaveInternet(ZCFG_ONE.this);
		if (havenet) {
		new Thread() {
			@Override
			public void run() {
				json= WebServiceUtil.everycanforStr("username","dw","","", spname,Department, "", 0,"GetDBCount");
				Message message=new Message();
				message.what=1;
				handler.sendMessage(message);
			}
		}.start();
		}else {
			Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			 
		/*	 Intent intent = new Intent(ZCFG_ONE.this, MainWexin.class);
				startActivity(intent);*/
				finish();
			 
		 }
		return super.onKeyDown(keyCode, event);
	}
	public void bt_back(View v){
	/*	Intent intent = new Intent(ZCFG_ONE.this, MainWexin.class);
		startActivity(intent);*/
		finish();
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
			StatusBarUtils.setStatusBarColor(ZCFG_ONE.this, R.color.btground);
		}
	}
}
