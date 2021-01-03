package com.example.hbkjgoa.tzgg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.GZJHAdapter;
import com.example.hbkjgoa.model.GZJH_Bean;
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

public class gzjh_list extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json,searchstr="";
	private boolean webbing = false;
	private List<GZJH_Bean> list = new ArrayList<GZJH_Bean>();
	private XListView mListView;
	private GZJHAdapter adapter;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	private String lx="",name="",spname;
	public static gzjh_list listact;
	LoadingDialog dialog1;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				setData(list);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<GZJH_Bean>) msg.obj;
				adapter.setmes((List<GZJH_Bean>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
			} else if (msg.what == 3) {
				adapter.setmes(list);
				mListView.stopLoadMore();
				adapter.notifyDataSetChanged();
			}

		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmjl_sjx);
		listact = this;
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView)findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getInfo();
		initStatusBar();
	}

	private void setData(List<GZJH_Bean> listitem) {
		adapter = new GZJHAdapter(gzjh_list.this, listitem);
		mListView.setAdapter(adapter);
	}

	private void findview(){
		zs=(TextView) findViewById(R.id.zs);
		chat= (ImageView) findViewById(R.id.chat);
		title=(TextView) findViewById(R.id.title);
		serch=(Button) findViewById(R.id.serch);
		title.setText("工作计划");
        chat.setVisibility(View.GONE);



	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(gzjh_list.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(gzjh_list.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json = WebServiceUtil.everycanforStr("", "userid", "", "pageNo","", spname, "", 1, "GetWorkPlanList");

					Log.d("gsp", "GetCompanyList" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<GZJH_Bean>>() {
						}.getType();
						list = gson.fromJson(json, type);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {
			ToastUtils.showToast(gzjh_list.this, "网络连接失败！");
		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<GZJH_Bean> list = new ArrayList<GZJH_Bean>();
				webbing = true;
				json = WebServiceUtil.everycanforStr("", "userid", "", "pageNo","", spname, "", pageno, "GetWorkPlanList");

				Log.d("gsp", "GetCompanyList" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<GZJH_Bean>>() {
					}.getType();
					list = gson.fromJson(json, type);
				}
				Message msg = new Message();
				msg.what = 2;
				msg.obj = list;
				handler.sendMessage(msg);
			}
		}).start();

	}

	@Override
	public void onLoadMore() {
		boolean havenet = NetHelper.IsHaveInternet(gzjh_list.this);
		if (havenet) {
			pageno++;
			new Thread() {
				@Override
				public void run() {
					json = WebServiceUtil.everycanforStr("", "userid", "", "pageNo","", spname, "", pageno, "GetWorkPlanList");
					if (json != null && !json.equals("0")) {

						try {

							Gson gson=new Gson();
							List<GZJH_Bean> list2 = new ArrayList<GZJH_Bean>();
							Type type=new TypeToken<ArrayList<GZJH_Bean>>(){}.getType();
							list2=gson.fromJson(json, type);
							list.addAll(list2);
							Message msg = new Message();
							msg.what = 3;
							handler.sendMessage(msg);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}



				}
			}.start();

		}else {

			Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

		}


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


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
			StatusBarUtils.setStatusBarColor(gzjh_list.this, R.color.btground);
		}
	}
}
