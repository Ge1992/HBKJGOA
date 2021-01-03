package com.example.hbkjgoa.rczyk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.util.Log;
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
import com.example.hbkjgoa.adapter.GDZCAdapter;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.model.SJ_Bean;
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

public class gdzc_list extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json,searchstr="",type="",json2="";
	private boolean webbing = false;
	private List<GDZC_Bean> list = new ArrayList<GDZC_Bean>();
	private XListView mListView;
	private GDZCAdapter adapter;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	private String lx="",name="",spname;
	public static gdzc_list listact;
	LoadingDialog dialog1;
	private String[] bb;
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
				list = (List<GDZC_Bean>) msg.obj;
				adapter.setmes((List<GDZC_Bean>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
			} else if (msg.what == 3) {
				adapter.setmes(list);
				mListView.stopLoadMore();
				adapter.notifyDataSetChanged();
			}else if (msg.what == 4) {
				if (json2 != null && !json2.equals("") && json2.contains("|")) {
					bb= json2.split("\\|");
				/*	int size = mdq.length;  //获取数组长度
					String [] newArray= new String[mdq.length+1];
					for (int i=0;i<size;i++){
						newArray[i] = mdq[i];
					}
					newArray[size] = "全部";  //在最后添加上需要追加的数据*/

					String [] aa={"全部"};
					String array[] = new String[aa.length + bb.length];
					// 循环添加数组内容
					for (int i = 0; i < array.length; i++) {

						if (i < aa.length) {
							array[i] = aa[i];
						} else {
							array[i] = bb[i - aa.length];
						}
					}



					TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);
					tabLayout.setVisibility(View.VISIBLE);
					for (int i = 0; i < array.length; i++) {
						TabLayout.Tab tab = tabLayout.newTab();//创建tab
						tab.setText(array[i]);//设置文字
						tabLayout.addTab(tab);//添加到tabLayout中
					}
					tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
						@Override
						public void onTabSelected(TabLayout.Tab tab) {

							if (tab.getText().equals("全部")){
								type="";
							}else {
								type=String.valueOf(tab.getText());
							}
							onRefresh();
						}

						@Override
						public void onTabUnselected(TabLayout.Tab tab) {

						}

						@Override
						public void onTabReselected(TabLayout.Tab tab) {

						}
					});

				}
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
		gettype();
		initStatusBar();
	}

	private void setData(List<GDZC_Bean> listitem) {
		adapter = new GDZCAdapter(gdzc_list.this, listitem);
		mListView.setAdapter(adapter);
	}

	private void findview(){
		zs=(TextView) findViewById(R.id.zs);
		chat= (ImageView) findViewById(R.id.chat);
		title=(TextView) findViewById(R.id.title);
		serch=(Button) findViewById(R.id.serch);
		serch.setVisibility(View.VISIBLE);
		title.setText("固定资产");








		serch.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {

				final EditText inputServer = new EditText(gdzc_list.this);

				new AlertDialog.Builder(gdzc_list.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("输入责任人姓名")//使用默认设备 浅色主题
						.setView(inputServer)
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						}).setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface arg0) {
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						searchstr=inputServer.getText().toString();
						onRefresh();
						dialog.dismiss();
					}

				}).show();
			}

		});

	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(gdzc_list.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(gdzc_list.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",searchstr, spname, type, 1, "GetSheBeiList");

					Log.d("gsp", "GetSheBeiList" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<GDZC_Bean>>() {
						}.getType();
						list = gson.fromJson(json, type);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {
			ToastUtils.showToast(gdzc_list.this, "网络连接失败！");
		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<GDZC_Bean> list = new ArrayList<GDZC_Bean>();
				webbing = true;
				json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",searchstr, spname, type, pageno, "GetSheBeiList");

				Log.d("gsp", "GetCompanyList" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<GDZC_Bean>>() {
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
		boolean havenet = NetHelper.IsHaveInternet(gdzc_list.this);
		if (havenet) {
			pageno++;
			new Thread() {
				@Override
				public void run() {
					json = WebServiceUtil.everycanforStr("search", "userid", "type", "pageNo",searchstr, spname, type, pageno, "GetSheBeiList");
					if (json != null && !json.equals("0")) {

						try {

							Gson gson=new Gson();
							List<GDZC_Bean> list2 = new ArrayList<GDZC_Bean>();
							Type type=new TypeToken<ArrayList<GDZC_Bean>>(){}.getType();
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
	/*	GDZC_Bean email = new GDZC_Bean();
		email = list.get(arg2-1);
		Intent intent = new Intent();
		intent.setClass(gdzc_list.this, InfoWorkFlow.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("NWorkToDo", email);
		bundle.putString("show",show);
		intent.putExtras(bundle);
		startActivity(intent);*/
		new SJ_Bean();
		GDZC_Bean localGZRZ =list.get(arg2-1);
		Intent intent = new Intent();
		intent.setClass(gdzc_list.this, InfoGDZC.class);
		Bundle localBundle = new Bundle();
		localBundle.putSerializable("GDZC", localGZRZ);
		intent.putExtras(localBundle);
		startActivity(intent);
	}
	public void bt_back(View v){

		finish();

	}


	private  void gettype(){
		new Thread() {
			@Override
			public void run() {
				json2 = WebServiceUtil.everycanforStr("", "", "", "","", "", "", 0, "GetSheBeiType");
				Message message = new Message();
				message.what = 4;
				handler.sendMessage(message);



			}
		}.start();
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
			StatusBarUtils.setStatusBarColor(gdzc_list.this, R.color.btground);
		}
	}
}
