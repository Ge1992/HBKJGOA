package com.example.hbkjgoa.KS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.XXQK_Bean;
import com.example.hbkjgoa.model.XXQK_Bean2;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.MyTableTextView;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XXQK_List2 extends Activity  {
	private Dialog progressDialog;
	private String json, spname = "";
	private boolean webbing = false;
	private List<XXQK_Bean2> list = new ArrayList<XXQK_Bean2>();
	LoadingDialog dialog1;
	public static XXQK_List2 listact;
	private XXQK_Bean email;
	private LinearLayout relativelayout,mainLinerLayout;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				Log.d("gsp", "GetKSTJDetails" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<XXQK_Bean2>>() {
					}.getType();
					list = gson.fromJson(json, type);
					initData();

					for (int i=0;i<list.size();i++){
						relativelayout = (LinearLayout) LayoutInflater.from(XXQK_List2.this).inflate(R.layout.table_khdw2, null);
						MyTableTextView txt = (MyTableTextView) relativelayout.findViewById(R.id.list_1_1);
						txt.setVisibility(View.VISIBLE);
						txt.setText(list.get(i).getDepartment());
					//	txt.setTextColor(Color.rgb(255, 107, 107));
							txt.setTextSize(16);

						MyTableTextView txt2 = (MyTableTextView) relativelayout.findViewById(R.id.list_1_2);
						txt2.setText(list.get(i).getUserName());
						//	txt2.setTextColor(Color.rgb(255, 107, 107));
							txt2.setTextSize(16);

						MyTableTextView txt3 = (MyTableTextView) relativelayout.findViewById(R.id.list_1_3);
							txt3.setText(list.get(i).getScore());
							txt3.setTextSize(16);


						//		txt3.setTextColor(Color.rgb(255, 107, 107));


						mainLinerLayout.addView(relativelayout);





					}

				}


			}

		}

	};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xxqk_info2);
		listact = this;
		spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");


		initStatusBar();
		findview();
		getInfo();
	}


	private void findview() {
		email=(XXQK_Bean)getIntent().getSerializableExtra("YZM");

	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(XXQK_List2.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(XXQK_List2.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json = WebServiceUtil.everycanforStr2("", "", "", "", "ShiJuanID", "", "", "", "", "", email.getShiJuanID(), 0, "GetKSTJDetails");


					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {

		}
	}
	@SuppressLint("CutPasteId")
	private void initData() {

		relativelayout = (LinearLayout) LayoutInflater.from(XXQK_List2.this).inflate(R.layout.table_khdw2, null);
		MyTableTextView title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_1);
		title.setVisibility(View.VISIBLE);
		TextPaint tp = title.getPaint();//加粗
		tp.setFakeBoldText(true);
		mainLinerLayout = (LinearLayout) findViewById(R.id.MyTable);
		title.setText("部门");
		title.setTextSize(18);
		title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_2);
		TextPaint tp2 = title.getPaint();
		tp2.setFakeBoldText(true);
		title.setText("姓名");
		title.setTextSize(18);
		title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_3);
		TextPaint tp3 = title.getPaint();
		tp3.setFakeBoldText(true);
		title.setText("得分");
		title.setTextSize(18);
		mainLinerLayout.addView(relativelayout);
		mainLinerLayout = (LinearLayout) findViewById(R.id.MyTable);

	}


	public void bt_back(View v) {

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
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(XXQK_List2.this, R.color.btground);
		}
	}

}