package com.example.hbkjgoa.rcgl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.util.wheelview.WheelMain;
import com.example.hbkjgoa.util.widge.CustomDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LDHD_XJ extends Activity {
		private EditText e1,e4,e5;
		private TextView e2,e3;
		private Button save;
	private LoadingDialog dialog1;
		WheelMain wheelMain;
		private RelativeLayout selectDate, selectTime;
	private Dialog progressDialog;
		private String time;
		private String date,ID="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		private String json,spname;
		   private CustomDatePicker datePicker,timePicker,timePicker1;
		@SuppressLint("HandlerLeak")
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				if (msg.what == 1) {
					dialog1.dismiss();
					if(json.equals("ok")){
                        LDHD_XJ.this.finish();
					if (New_LDActivity.listact != null) {
						New_LDActivity.listact.onRefresh();
						Toast.makeText(LDHD_XJ.this, "提交成功",
							     Toast.LENGTH_SHORT).show();


						}
					}else {
						
					}
				}
			}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rcap_info_add);
		//启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		   spname = getSharedPreferences("sdlxLogin",
					Context.MODE_PRIVATE + Context.MODE_PRIVATE)
					.getString("spname", "");
		 ID=this.getIntent().getStringExtra("id");
		findview();
		 initPicker();
		Save();
		initStatusBar();
	}
	
		private void  findview(){
			e1=(EditText) findViewById(R.id.Et1);
			e2=(TextView) findViewById(R.id.Et2);
			e3=(TextView) findViewById(R.id.Et3);
			e4=(EditText) findViewById(R.id.Et4);
			e5=(EditText) findViewById(R.id.Et5);
			save=(Button) findViewById(R.id.add);
		    selectTime = (RelativeLayout) findViewById(R.id.selectTime);
		    selectDate = (RelativeLayout) findViewById(R.id.selectDate);
			
		    e2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					timePicker.show(time);
				}
			});
		    
		    e3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						  timePicker1.show(time);
					}
				});
			e5.setText(spname);
		}
		
		
		private void Save(){
			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					LoadingDialog.Builder builder1=new LoadingDialog.Builder(LDHD_XJ.this)
							.setMessage("加载中...")
							.setCancelable(false);
					dialog1=builder1.create();
					dialog1.show();
						new Thread(new Runnable() {
							@Override
							public void run() {

								json = WebServiceUtil.HD_Add("0",e1.getText().toString(),e4.getText().toString(),e2.getText().toString(),e3.getText().toString(),e5.getText().toString());
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
						        
							}
						}).start();
				}
			});
		}
		
		
		
		
		 private void initPicker() {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		        time = sdf.format(new Date());
		        date = time.split(" ")[0];
		        //设置当前显示的日期
		  //      e2.setText(date);
		        //设置当前显示的时间
		   //     e3.setText(time);

		        /**
		         * 设置年月日
		         */
		        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
		            @Override
		            public void handle(String time) {
		                e2.setText(time.split(" ")[0]);
		            }
		        }, "2007-01-01 00:00", time);
		        datePicker.showSpecificTime(false); //显示时和分
		        datePicker.setIsLoop(false);
		        datePicker.setDayIsLoop(true);
		        datePicker.setMonIsLoop(true);

		        timePicker = new CustomDatePicker(this, "请选择开始时间", new CustomDatePicker.ResultHandler() {
		            @Override
		            public void handle(String time) {
		                e2.setText(time);
		            }
		        }, "2007-01-01 00:00", "2027-12-31 23:59");//"2027-12-31 23:59"
		        timePicker.showSpecificTime(true);
		        timePicker.setIsLoop(true);
		        
		        
		        timePicker1 = new CustomDatePicker(this, "请选择结束时间", new CustomDatePicker.ResultHandler() {
		            @Override
		            public void handle(String time) {
		                e3.setText(time);
		            }
		        }, "2007-01-01 00:00", "2027-12-31 23:59");//"2027-12-31 23:59"
		        timePicker1.showSpecificTime(true);
		        timePicker1.setIsLoop(true);
		        
		        
		    }
		
		
		
		public void bt_back(View v){

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
			StatusBarUtils.setStatusBarColor(LDHD_XJ.this, R.color.btground);
		}
	}
}
