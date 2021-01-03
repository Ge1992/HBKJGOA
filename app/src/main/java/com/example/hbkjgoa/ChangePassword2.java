package com.example.hbkjgoa;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "HandlerLeak", "WorldReadableFiles", "WorldWriteableFiles" })
public class ChangePassword2 extends Activity {

	private RelativeLayout R2;
	private EditText E1, E2, E3;
	private String spname, spword, json;
	private boolean iswebbing = false;

	private Handler handler = new Handler() {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 1) {
				iswebbing = false;
				if (json.equals("0") || json == null) {
					new AlertDialog.Builder(ChangePassword2.this).setTitle("密码修改失败！").setPositiveButton("确定", null)
							.create().show();
				} else {
					SharedPreferences sharedPreferences = getSharedPreferences("sdlxLogin",
							Context.MODE_PRIVATE + Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("spword", spword);
					editor.commit();

					new AlertDialog.Builder(ChangePassword2.this).setTitle("密码修改成功！")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									finish();
									Intent intent = new Intent(ChangePassword2.this, Login.class);
									Toast.makeText(ChangePassword2.this, "退出成功，请重新登录。 ", Toast.LENGTH_SHORT).show();
									startActivity(intent);
									ChangePassword2.this.finish();
								}
							}).create().show();
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword2);
		findView();
		setClick();
		setInfo();
		initStatusBar();
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
			StatusBarUtils.setStatusBarColor(ChangePassword2.this, R.color.btground);
		}
	}
	private void setInfo() {

	}

	private void setClick() {

		R2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!E1.getText().toString().equals("") && !E2.getText().toString().equals("")
						&& !E3.getText().toString().equals("")) {
					if (E1.getText().toString().equals(E2.getText().toString())) {

						if (E3.getText().toString().equals(spword)) {
							if (!iswebbing) {
								iswebbing = true;

								new Thread() {
									@Override
									public void run() {
										json = WebServiceUtil.everycanforStr4("uname", "oldpass", "password", "", "",
												"", spname, E3.getText().toString(), E2.getText().toString(), 0, 0, 0, "ChangePassword");
										Log.d("yin", "ChangePassword：" + json);
										Message message = new Message();
										message.what = 1;
										handler.sendMessage(message);

									}
								}.start();
						 	}
						} else {
							new AlertDialog.Builder(ChangePassword2.this, AlertDialog.THEME_HOLO_LIGHT)
									.setTitle("输入的原密码不正确！").setPositiveButton("确定", null).create().show();
						}

					} else {
						new AlertDialog.Builder(ChangePassword2.this, AlertDialog.THEME_HOLO_LIGHT)
								.setTitle("两次输入的密码不一样！").setPositiveButton("确定", null).create().show();
					}

				} else {
					new AlertDialog.Builder(ChangePassword2.this, AlertDialog.THEME_HOLO_LIGHT).setTitle("原密码或新密码不能为空！")
							.setPositiveButton("确定", null).create().show();
				}

			}
		});
	}

	@SuppressWarnings("deprecation")
	private void findView() {
		E1 = (EditText) findViewById(R.id.E1);
		E2 = (EditText) findViewById(R.id.E2);
		E3 = (EditText) findViewById(R.id.E3);
		R2 = (RelativeLayout) findViewById(R.id.R2);
		TextView tile = findViewById(R.id.title);
		tile.setText("修改密码");
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE)
				.getString("spname", "");
		spword = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE)
				.getString("spword", "");
	}

	public void bt_back(View v) {
		this.finish();
	}

}
