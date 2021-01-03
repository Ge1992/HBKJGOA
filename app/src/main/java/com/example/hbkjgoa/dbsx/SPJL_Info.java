package com.example.hbkjgoa.dbsx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.util.StatusBarUtils;


public class SPJL_Info extends Activity {
	String spname;
	private RelativeLayout bt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spjl_info);
		 spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		findview();
		initStatusBar();

	}


	private void findview(){
		Intent intent = this.getIntent();
		NWorkToDo nWorkToDo = (NWorkToDo) intent.getSerializableExtra("NWorkToDo");
		TextView Text1=(TextView)findViewById(R.id.Text1);
		WebView text3=(WebView)findViewById(R.id.text3);
		bt=findViewById(R.id.bt);
		bt.setVisibility(View.GONE);
		Text1.setText("欢迎你，"+spname);
		text3.loadDataWithBaseURL(null, nWorkToDo.getShenpiyjString(), "text/html", "utf-8", null);
	}

	
	
	public void bt_back(View view){
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
			StatusBarUtils.setStatusBarColor(SPJL_Info.this, R.color.btground);
		}
	}
}
