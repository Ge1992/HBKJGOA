package com.example.hbkjgoa.news;

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
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.MeetAdapter;
import com.example.hbkjgoa.model.Meet_Bean;
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

public class Meet_list extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json,searchstr="";
	private boolean webbing = false;
	private List<Meet_Bean> list = new ArrayList<Meet_Bean>();
	private XListView mListView;
	private MeetAdapter adapter;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	private String[] mdq;
	private String type="1",name="",spname;
	public static Meet_list listact;
	TabLayout tabLayout;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				setData(list);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<Meet_Bean>) msg.obj;
				adapter.setmes((List<Meet_Bean>) msg.obj);
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
		setContentView(R.layout.activity_meet_list);
		listact = this;
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView)findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getInfo();
		initview();
		initStatusBar();
	}

	private void initview() {

		mdq = getResources().getStringArray(R.array.meetlx);
		for (int i = 0; i < mdq.length; i++) {
			TabLayout.Tab tab = tabLayout.newTab();//创建tab
			tab.setText(mdq[i]);//设置文字
			tabLayout.addTab(tab);//添加到tabLayout中
		}
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {

				if (tab.getText().equals("未开始")){
					type="1";
					onRefresh();
				}else if (tab.getText().equals("进行中")){
					type="2";
					onRefresh();
				}else if (tab.getText().equals("已结束")){
					type="3";
					onRefresh();
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				if (tab.getText().equals("未开始")){
					type="1";
					onRefresh();
				}else if (tab.getText().equals("进行中")){
					type="2";
					onRefresh();
				}else if (tab.getText().equals("已结束")){
					type="3";
					onRefresh();
				}
			}
		});
	}

	private void setData(List<Meet_Bean> listitem) {
		adapter = new MeetAdapter(Meet_list.this, listitem);
		mListView.setAdapter(adapter);
	}

	private void findview(){
		tabLayout = (TabLayout) findViewById(R.id.tab2);
		zs=(TextView) findViewById(R.id.zs);
		chat= (ImageView) findViewById(R.id.chat);
		title=(TextView) findViewById(R.id.title);
		serch=(Button) findViewById(R.id.serch);
		title.setText("会议通知");
		tabLayout.setVisibility(View.VISIBLE);
	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(Meet_list.this);
		if (havenet) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",
							searchstr, spname, type, pageno, "GetMeetList");

					Log.d("gsp", "GetMeetList" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<Meet_Bean>>() {
						}.getType();
						list = gson.fromJson(json, type);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {
			ToastUtils.showToast(Meet_list.this, "网络连接失败！");
		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Meet_Bean> list = new ArrayList<Meet_Bean>();
				webbing = true;

				json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",
						searchstr, spname, type, pageno, "GetMeetList");

				Log.d("gsp", "GetCompanyList" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Meet_Bean>>() {
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
		boolean havenet = NetHelper.IsHaveInternet(Meet_list.this);
		if (havenet) {
			pageno++;
			new Thread() {
				@Override
				public void run() {

					json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",
							searchstr, spname, type, pageno, "GetMeetList");
					if (json != null && !json.equals("0")) {
						try {
							Gson gson=new Gson();
							List<Meet_Bean> list2 = new ArrayList<Meet_Bean>();
							Type type=new TypeToken<ArrayList<Meet_Bean>>(){}.getType();
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
		if(SY_News.instance!=null){
			SY_News.instance.setInfo2();
		}
		finish();

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取

			if(SY_News.instance!=null){
				SY_News.instance.setInfo2();
			}

			finish();

		}
		return false;
	}


	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(Meet_list.this, R.color.btground);
		}
	}
}
