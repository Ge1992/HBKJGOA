package com.example.hbkjgoa.zcfg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.hbkjgoa.adapter.ZCFGAdatper;
import com.example.hbkjgoa.model.DZGG;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
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

public class ZCFG_TWO extends Activity implements OnItemClickListener, XListView.IXListViewListener {
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
	private ZCFGAdatper adapter;
	private int fid = 0;
	private int step = 1;
	private String pid3 = "0";
	private boolean webbing = false;
	private List<DZGG> list = new ArrayList<DZGG>();
	private ListView XKGListView;
	private Button login_reback_btn, serch;
	private ImageView addsb;
	private Boolean iswebbing = false;
	private int page = 1;
	public static ZCFG_TWO listact;
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
	  private String LX = "";
		private Dialog progressDialog;
	LoadingDialog dialog1;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				dialog1.dismiss();
			 if (json.equals("[]")) {
				 ToastUtils.showToast(ZCFG_TWO.this, "暂无数据！");
			}
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			//	zs.setText("总数:" + record + "个");
				setData(list);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<DZGG>) msg.obj;
				adapter.setmes((List<DZGG>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 3) {
				if (strseq2 != "")
					strseq = strseq2;
				adapter.setmes(list);
				adapter.notifyDataSetChanged();
				mListView.stopLoadMore();
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
			//	zs.setText("总数:" + record + "个");
				tx.setVisibility(View.VISIBLE);
			}

		}

	};

	private void setData(List<DZGG> listitem) {
		adapter = new ZCFGAdatper(ZCFG_TWO.this, listitem);
		mListView.setAdapter(adapter);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zcfg2);
		sharedPreferences = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		spword = sharedPreferences.getString("spword", "");
		ulx = sharedPreferences.getString("ulx", "");
		 LX = this.getIntent().getStringExtra("LX");
		methodString = this.getIntent().getStringExtra("method");
		listact = this;
		findview();
		getInfo();
		initStatusBar();

	}


	private void findview(){
		title = (TextView) findViewById(R.id.title);
		title.setText(LX);
		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		zs = (TextView) findViewById(R.id.zs);
		serch = (Button) findViewById(R.id.serch);
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	private void getInfo() {
		strseq = "";
		boolean havenet = NetHelper.IsHaveInternet(ZCFG_TWO.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(ZCFG_TWO.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("", "uname", "LX", "zt", "", "iPageNo", "",  spname, LX, "2","", page, "GetDZGG");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						Type type=new TypeToken<ArrayList<DZGG>>(){}.getType();
						list=gson.fromJson(json, type);	
					}
					Message msg = new Message();
					msg.what = 1;							
					handler.sendMessage(msg);
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCFG_TWO.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
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
			public void run() {
				List<DZGG> list = new ArrayList<DZGG>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("", "uname", "LX", "zt", "", "iPageNo", "",  spname, LX, "2","", page, "GetDZGG");
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<DZGG>>(){}.getType();
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
		// progressDialog=ProgressDialog.show(getActivity(),"正在加载","请稍等...");
		strseq = "";
		page++;
		// showProgress();
		boolean havenet = NetHelper.IsHaveInternet(ZCFG_TWO.this);
		if (havenet) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("", "uname", "LX", "zt", "", "iPageNo", "",  spname, LX, "2","", page, "GetDZGG");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						Type type=new TypeToken<ArrayList<DZGG>>(){}.getType();
						list=gson.fromJson(json, type);	
					}
					Message msg = new Message();
					msg.what = 3;							
					handler.sendMessage(msg);
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCFG_TWO.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
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
			StatusBarUtils.setStatusBarColor(ZCFG_TWO.this, R.color.btground);
		}
	}
}
