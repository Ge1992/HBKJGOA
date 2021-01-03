package com.example.hbkjgoa;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hbkjgoa.util.StatusBarUtils;

public class ChangePassword extends Activity {

	private RelativeLayout R1, R2, R3;
	private TextView text4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.changepassword);
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
			StatusBarUtils.setStatusBarColor(ChangePassword.this, R.color.btground);
		}
	}
	private void setInfo() {
		try {
			String app_versionName = this.getPackageManager()
					.getPackageInfo(getResources().getString(R.string.package_name), 0).versionName;
			text4.setText(app_versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setClick() {
		R1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ChangePassword.this, ChangePassword2.class);
				startActivity(intent);

			}
		});

		R2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangePassword.this, Login.class);
				Toast.makeText(ChangePassword.this, "退出成功，请重新登录。 ", Toast.LENGTH_SHORT).show();
				startActivity(intent);
				ChangePassword.this.finish();


				if (MoreActivity.instance != null) {
					MoreActivity.instance.finish();
				}
			}
		});

		R3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ChangePassword.this, ChangePassword3.class);
				startActivity(intent);
			}
		});
	}

	private void findView() {
		R1 = (RelativeLayout) findViewById(R.id.R1);
		R2 = (RelativeLayout) findViewById(R.id.R2);
		R3 = (RelativeLayout) findViewById(R.id.R3);
		text4 = (TextView) findViewById(R.id.text4);
		TextView tile = findViewById(R.id.title);
		tile.setText("设置");
	}

	public void bt_back(View v) {
		this.finish();
	}

}
