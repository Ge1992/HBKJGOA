package com.example.hbkjgoa.Email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMJL_SJXAdapter_sjx;
import com.example.hbkjgoa.model.Xmjl_sjxBean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * 收件箱
 *
 */
public class XMJL_SJX extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
    boolean webbing = false;
    private int pageno = 1;
	private String json,json2,json3,spname;
	private XListView mListView;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private TextView title;
	private List<Xmjl_sjxBean> list = new ArrayList<Xmjl_sjxBean>();
	private XMJL_SJXAdapter_sjx adapter;
	private ImageView sosu;
	public static XMJL_SJX listact;
	private Boolean iswebbing = false;
	private String searchstr = "";
	private String id;
	LoadingDialog dialog1;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				if (json.equals("[]")) {
					 ToastUtils.showToast(XMJL_SJX.this,"暂无数据");
				}
				setData();

			}else if (msg.what == 2) {
				list.clear();
				list = (List<Xmjl_sjxBean>) msg.obj;
				adapter.setmes((List<Xmjl_sjxBean>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				
			}else if (msg.what == 3) {
				adapter.setmes(list);
				mListView.stopLoadMore();
				adapter.notifyDataSetChanged();
			} else if (msg.what == 4) {
				if(json2.equals("1")){
					Toast.makeText(XMJL_SJX.this, "删除成功", Toast.LENGTH_SHORT).show();
				onRefresh();
				}
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmjl_sjx);
		listact = this;
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		title=(TextView) findViewById(R.id.title);
		title.setText("收件箱");
		 getInfo();
		 FindView();
		initStatusBar();
	}
	
	private void setData() {
		adapter = new XMJL_SJXAdapter_sjx(XMJL_SJX.this, list);
		mListView.setAdapter(adapter);
	}
	
	private void FindView(){
		sosu=(ImageView) findViewById(R.id.chat);
		sosu.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				if (!iswebbing) {
					iswebbing=true;
					final EditText inputServer = new EditText(XMJL_SJX.this);
					new AlertDialog.Builder(XMJL_SJX.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("请输入邮件名")//使用默认设备 浅色主题
					.setView(inputServer)
					.setNegativeButton("取消",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							iswebbing=false;
						}
					}).setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							iswebbing=false;
						}
					}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							searchstr=inputServer.getText().toString();
							onRefresh();
							iswebbing=false;
							dialog.dismiss();
						}

					}).show();
				}
				
			}
		});
	}
	
	private void getInfo(){
		boolean havenet = NetHelper.IsHaveInternet(XMJL_SJX.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(XMJL_SJX.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("uname", "search", "", "", "", "iPageNo",
														   spname, searchstr, "", "", "",pageno, "GetEmail");
					Log.d("gsp", "GetEmail" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						Type type=new TypeToken<ArrayList<Xmjl_sjxBean>>(){}.getType();
						list=gson.fromJson(json, type);	
					
						
					}
					Message msg = new Message();
					msg.what = 1;							
					handler.sendMessage(msg);
				}
				
			}).start();
		} else {
			 ToastUtils.showToast(XMJL_SJX.this,"网络连接失败！");		
		}
	}
	
	
	
	public void bt_back(View v) {
		this.finish();
	}





	@Override
	public void onRefresh() {
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Xmjl_sjxBean> list = new ArrayList<Xmjl_sjxBean>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("uname", "search", "", "", "", "iPageNo", 
						   spname, searchstr, "", "", "",pageno, "GetEmail");
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<Xmjl_sjxBean>>(){}.getType();
					list=gson.fromJson(json, type);	
				}
				Message msg = new Message();
				msg.what = 2;	
				msg.obj=list;
				handler.sendMessage(msg);
			}
		}).start();

	}





	@Override
	public void onLoadMore() {
		Log.d("yuan", "加载更多已经被执行到…………");
		pageno++;
		new Thread(new Runnable() {
			@Override
			public void run() {
				webbing = true;
				json = WebServiceUtil.everycanforStr2("uname", "search", "", "", "", "iPageNo", 
						   spname, searchstr, "", "", "",pageno, "GetEmail");
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					List<Xmjl_sjxBean> list2 = new ArrayList<Xmjl_sjxBean>();
					Type type=new TypeToken<ArrayList<Xmjl_sjxBean>>(){}.getType();
					list2=gson.fromJson(json, type);		
					list.addAll(list2);
					
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}
				
			}
		}).start();

	}





	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
		
	/*	Xmjl_sjxBean email = new Xmjl_sjxBean();
		email = list.get(arg2 - 1);
		Intent intent = new Intent();
		intent.setClass(XMJL_SJX.this, Info_YJXX_pass.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Xmjl_sjxBean", email);
		intent.putExtras(bundle);
		startActivity(intent);*/
		
	}
	
	
	 public void DelEmail(final String id){
			new Thread(new Runnable() {
				@Override
				public void run() {
					json2=WebServiceUtil.everycanforStr2("id", "uname", "", "", "", "", id, spname, "", "", "",0, "DelEmail");
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);	
				}
			}).start();
			
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
			StatusBarUtils.setStatusBarColor(XMJL_SJX.this, R.color.btground);
		}
	}
	
	
	
	
	
}
