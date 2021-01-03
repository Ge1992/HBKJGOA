package com.example.hbkjgoa.txl;

import android.annotation.SuppressLint;
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
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMLXAdapter3;
import com.example.hbkjgoa.model.TXL_New;
import com.example.hbkjgoa.model.TXL_New2;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZCSL3 extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private String wz;
	private String str;
	private String spname = "", pageindex = "", wantto = "", proName = "", prodq = "", yearnd = "", proProfession = "",
			proProfessionchild = "";
	private String strseq = "";
	private String strseq2 = "";
	private String spword, ulx;
	private SharedPreferences sharedPreferences;
	private AlertDialog dlg;
	private String json, searchstr = "";
	private XMLXAdapter3 adapter;
	private int fid = 0;
	private int step = 1;
	private String pid3 = "0";
	private boolean webbing = false;
	private List<TXL_New2> list = new ArrayList<TXL_New2>();
	private ListView XKGListView;
	private Button login_reback_btn, serch;
	private ImageView addsb;
	private Boolean iswebbing = false;
	private int page = 1;
	public static ZCSL3 listact;
	private XListView mListView;
	private XListViewFooter footer;
	private String methodString = "";
	private TextView title, zs;
	private Animation loadAnimation;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private Double geoLat;
	private Double geoLng;
	private String lat, record;
	private String lng;
	private Handler mHandler;
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, bqb;
	private TXL_New email;
    LoadingDialog dialog1;
	@SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
                dialog1.dismiss();
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			//	zs.setText("总数:" + record + "个");
				setData(list);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<TXL_New2>) msg.obj;
				adapter.setmes((List<TXL_New2>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 3) {
				adapter.setmes(list);
				mListView.stopLoadMore();
				adapter.notifyDataSetChanged();
			}

		}

	};

	private void setData(List<TXL_New2> listitem) {
		adapter = new XMLXAdapter3(ZCSL3.this, listitem);
		mListView.setAdapter(adapter);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.txl);
		sharedPreferences = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		spword = sharedPreferences.getString("spword", "");
		ulx = sharedPreferences.getString("ulx", "");
		listact = this;
		Intent intent = this.getIntent();
		email=(TXL_New)intent.getSerializableExtra("TXL_New");
/*		for (Childlist item : email.getChildlist()) {
			Toast.makeText(getApplicationContext(), item.getIdString(),
				     Toast.LENGTH_SHORT).show();
		}*/
		

		//methodString = this.getIntent().getStringExtra("method");
		title = (TextView) findViewById(R.id.title);
		title.setText("通讯录");
		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		zs = (TextView) findViewById(R.id.zs);
		serch = (Button) findViewById(R.id.serch);
		
		getInfo();
		initStatusBar();
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProProfessionchild() {
		return proProfessionchild;
	}

	public void setProProfessionchild(String proProfessionchild) {
		this.proProfessionchild = proProfessionchild;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	

	}

	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(ZCSL3.this);
		if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(ZCSL3.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo",
														   spname, email.getMC(), "", "", "",1, "GetUsersListFZ");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
						list=gson.fromJson(json, type);	
					}
					
					
					
					Message msg = new Message();
					msg.what = 1;							
					handler.sendMessage(msg);
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCSL3.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}
}

	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		page = 1;
		strseq = "";
		new Thread(new Runnable() {
			@Override
			public void run() {/*
				List<TXL_New> list = new ArrayList<TXL_New>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("username", "", "", "", "", "pid", 
						   spname, "", "", "", "",0, "GetDept");
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<TXL_New>>(){}.getType();
					list=gson.fromJson(json, type);	
				}
				Message msg = new Message();
				msg.what = 2;	
				msg.obj=list;
				handler.sendMessage(msg);
			*/
				List<TXL_New> list = new ArrayList<TXL_New>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo", 
													   spname, email.getMC(), "", "", "",page, "GetUsersListFZ");
				Log.d("gsp", "GetDept" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
					list=gson.fromJson(json, type);	
				}
				
				
				
				Message msg = new Message();
				msg.what = 2;	
				msg.obj=list;
				handler.sendMessage(msg);
			
			}
		}).start();

	}

	public void onLoadMore() {
		page++;
		boolean havenet = NetHelper.IsHaveInternet(ZCSL3.this);
		if (havenet) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo", 
														   spname, email.getMC(), "", "", "",page, "GetUsersListFZ");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						List<TXL_New2> list2 = new ArrayList<TXL_New2>();
						Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
						list2=gson.fromJson(json, type);		
						list.addAll(list2);
						
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);
					}
					
					
					
				
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCSL3.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}
}

	/*
	 * public void showProgress() { if (progressDialog != null) {
	 * progressDialog.cancel(); } progressDialog1 = new
	 * CustomProgressDialog(XMGLCX.this, ""); progressDialog1.show(); }
	 */

	public String getWantto() {
		return wantto;
	}

	public void setWantto(String wantto) {
		this.wantto = wantto;
	}

	public String getProProfession() {
		return proProfession;
	}

	public void setProProfession(String proProfession) {
		this.proProfession = proProfession;
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
			 getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(ZCSL3.this, R.color.btground);
		}
	}
}
