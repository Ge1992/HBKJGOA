package com.example.hbkjgoa.rczyk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.LogAdapter;
import com.example.hbkjgoa.adapter.WFListViewAdapter_SBJL;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WorkFlowShebei extends Activity implements XListView.IXListViewListener, OnItemClickListener {
	private ListView listview;
	private EditText editText1;
	private TextView Text1,Text2;
	private WFListViewAdapter_SBJL listViewAdapter;
	private List<NWorkToDo> list = new ArrayList<NWorkToDo>();
	private List<NWorkToDo> list2 = new ArrayList<NWorkToDo>();
	private String json,nowtitle;
	private int EmailPageNo = 1;
	private String spname,show;
	private int ID=0;
	private boolean havesou=false;
	public static WorkFlowShebei mm;
	private Button serch;
	private Boolean iswebbing=false;
	private String searchstr="",LX="耗材";
	private Dialog progressDialog;
	private XListView mListView;
	private XListViewFooter footer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	LoadingDialog dialog1;
	private GDZC_Bean ap;
	private LogAdapter LogAdapterAdapter;
	public void closehere(){
		finish();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				dialog1.dismiss();
				if (json.equals("[]")){
					Toast.makeText(getApplicationContext(), "暂无记录",Toast.LENGTH_SHORT).show();
				}

				setData(list);


			} else if (msg.what == 2) {
				if (json.equals("[]")){
					Toast.makeText(getApplicationContext(), "暂无记录",Toast.LENGTH_SHORT).show();
				}

				list.clear();
				list = (List<NWorkToDo>) msg.obj;
				listViewAdapter.setmes((List<NWorkToDo>) msg.obj);
				mListView.stopRefresh();
				listViewAdapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
			} else if (msg.what == 3) {

				listViewAdapter.setmes(list);
				listViewAdapter.notifyDataSetChanged();
				mListView.stopLoadMore();
			}else if (msg.what == 4) {
				list.clear();
				setData2(list2);
			}

		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sbjl_list);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mm=this;
		spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("spname","");
		nowtitle=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("nowtitle","");
		Intent intent = this.getIntent();
		ap = (GDZC_Bean) intent.getSerializableExtra("GDZC");
		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(listViewAdapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		mListView.setCacheColorHint(0x00000000);
		mListView.setDivider(getResources().getDrawable(R.color.divider_color));
		mListView.setDividerHeight(1);
		findview();
		getListItems(LX);
		initStatusBar();
		

	}

	private void findview(){
		show=getIntent().getStringExtra("show");
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);
		tabLayout.setVisibility(View.VISIBLE);
		if (spname.contains("管理员")){
			String[] array={"领用","保修","借用","日志"};
			for (int i = 0; i < array.length; i++) {
				TabLayout.Tab tab = tabLayout.newTab();//创建tab
				tab.setText(array[i]);//设置文字
				tabLayout.addTab(tab);//添加到tabLayout中
			}
		}else {
			String[] array={"领用","保修","借用"};
			for (int i = 0; i < array.length; i++) {
				TabLayout.Tab tab = tabLayout.newTab();//创建tab
				tab.setText(array[i]);//设置文字
				tabLayout.addTab(tab);//添加到tabLayout中
			}
		}


		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {

					if(tab.getText().equals("日志")){

						mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
						mListView.setPullRefreshEnable(false);
						geylog(ap.getID());

					}else 	if (tab.getText().equals("领用")) {
						list2.clear();
						list.clear();
						LX="耗材";
						getListItems(LX);
					} else {
						list2.clear();
						list.clear();
						LX = String.valueOf(tab.getText());
						getListItems(LX);
				}

			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}


	private void setData(List<NWorkToDo> listitem) {
		listViewAdapter = new WFListViewAdapter_SBJL(this, listitem);
		mListView.setAdapter(listViewAdapter);
	}

	private void setData2(List<NWorkToDo> listitem) {
		LogAdapterAdapter = new LogAdapter(this, listitem);
		mListView.setAdapter(LogAdapterAdapter);
	}

	private void getListItems(final String LX) {

		boolean havenet = NetHelper.IsHaveInternet(WorkFlowShebei.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(WorkFlowShebei.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread() {
				@Override
				public void run() {

					json = WebServiceUtil.everycanforStr2("", "uname", "SBID","LX", "", "iPageNo",
							""  ,spname, ap.getID(), LX, "",EmailPageNo, "GetWorkFlowBySheBie");

					if (json != null && !json.equals("0")) {
						JSONArray jsonObjs;
						JSONObject jsonObj;
						JSONTokener jsonTokener = new JSONTokener(json);
						try {

							jsonObjs = (JSONArray) jsonTokener.nextValue();
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								NWorkToDo email = new NWorkToDo();
								email.setID(jsonObj.getInt("ID"));
								email.setWorkname(jsonObj.getString("WorkName"));
								email.setFromContentString(jsonObj.getString("FormContent"));
								email.setTimestr(jsonObj.getString("TimeStr"));
								email.setFujianString(jsonObj.getString("FuJianList"));
								email.setUsernameString(jsonObj.getString("UserName"));
								email.setFormid(jsonObj.getInt("FormID"));
								email.setFlowid(jsonObj.getInt("WorkFlowID"));
								email.setJiedianid(jsonObj.getInt("JieDianID"));
								email.setJiedianNameString(jsonObj.getString("JieDianName"));
								email.setShenpiyjString(jsonObj.getString("ShenPiYiJian"));
								email.setShenpiUserString(jsonObj.getString("ShenPiUserList"));
								email.setOkuserlist(jsonObj.getString("OKUserList"));
								email.setStateNowString(jsonObj.getString("StateNow"));
								email.setWenhaoString(jsonObj.getString("WenHao"));
								email.setLatetimeString(jsonObj.getString("LateTime"));
								email.setBeiyong1(jsonObj.getString("BeiYong1"));
								email.setBeiyong2(jsonObj.getString("BeiYong2"));
								JSONArray nodArray = jsonObj.getJSONArray("NodeItem");
								List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
								for (int j = 0; j < nodArray.length(); j++) {

									JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
									WorkFlowItem wf = new WorkFlowItem();
									wf.setValueString(nodejsonObj.getString("Values"));
									wf.setTextString(nodejsonObj.getString("Texts"));
									wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
									wf.setSpmsString(nodejsonObj.getString("Spms"));
									wf.setPsmsString(nodejsonObj.getString("Psms"));
									wf.setMrsprString(nodejsonObj.getString("Mrspr"));
									nodeItems.add(wf);
								}
								email.setNodeList(nodeItems);
								list.add(email);
							}
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						} catch (Exception e) {
						}

					}

				}
			}.start();

		} else {
			Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

		}
	}
	@Override
	public void onLoadMore() {

		++EmailPageNo;
		Log.d("yin", "EmailPageNo：" + EmailPageNo);
		new Thread(new Runnable() {

			@Override
			public void run() {
				json = WebServiceUtil.everycanforStr2("", "uname", "SBID","LX", "", "iPageNo",
						""  ,spname,  ap.getID(), LX, "",EmailPageNo, "GetWorkFlowBySheBie");

				if (json != null && !json.equals("0")) {
					JSONArray jsonObjs;
					JSONObject jsonObj;
					JSONTokener jsonTokener = new JSONTokener(json);
					try {
						jsonObjs = (JSONArray) jsonTokener.nextValue();
						for (int i = 0; i < jsonObjs.length(); i++) {
							jsonObj = (JSONObject) jsonObjs.opt(i);
							NWorkToDo email = new NWorkToDo();
							email.setID(jsonObj.getInt("ID"));
							email.setWorkname(jsonObj.getString("WorkName"));
							email.setFromContentString(jsonObj.getString("FormContent"));
							email.setTimestr(jsonObj.getString("TimeStr"));
							email.setFujianString(jsonObj.getString("FuJianList"));
							email.setUsernameString(jsonObj.getString("UserName"));
							email.setFormid(jsonObj.getInt("FormID"));
							email.setFlowid(jsonObj.getInt("WorkFlowID"));
							email.setJiedianid(jsonObj.getInt("JieDianID"));
							email.setJiedianNameString(jsonObj.getString("JieDianName"));
							email.setShenpiyjString(jsonObj.getString("ShenPiYiJian"));
							email.setShenpiUserString(jsonObj.getString("ShenPiUserList"));
							email.setOkuserlist(jsonObj.getString("OKUserList"));
							email.setStateNowString(jsonObj.getString("StateNow"));
							email.setWenhaoString(jsonObj.getString("WenHao"));
							email.setLatetimeString(jsonObj.getString("LateTime"));
							email.setBeiyong1(jsonObj.getString("BeiYong1"));
							email.setBeiyong2(jsonObj.getString("BeiYong2"));
							JSONArray nodArray = jsonObj.getJSONArray("NodeItem");
							List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
							for (int j = 0; j  < nodArray.length(); j++) {
									JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
									WorkFlowItem wf = new WorkFlowItem();
									wf.setValueString(nodejsonObj.getString("Values"));
									wf.setTextString(nodejsonObj.getString("Texts"));
									wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
									wf.setSpmsString(nodejsonObj.getString("Spms"));
									wf.setPsmsString(nodejsonObj.getString("Psms"));
									wf.setMrsprString(nodejsonObj.getString("Mrspr"));
									nodeItems.add(wf);

								}
							email.setNodeList(nodeItems);
							list.add(email);
						}
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
	
	@Override
	public void onRefresh() {
		EmailPageNo=1;
		new Thread(new Runnable() {

			@Override
			public void run() {
				List<NWorkToDo> list = new ArrayList<NWorkToDo>();
				json = WebServiceUtil.everycanforStr2("", "uname", "SBID","LX", "", "iPageNo",
						""  ,spname,  ap.getID(), LX, "",EmailPageNo, "GetWorkFlowBySheBie");
				if (json != null && !json.equals("0")) {
					JSONArray jsonObjs;
					JSONObject jsonObj;
					JSONTokener jsonTokener = new JSONTokener(json);
					try {
						jsonObjs = (JSONArray) jsonTokener.nextValue();
						for (int i = 0; i < jsonObjs.length(); i++) {
							jsonObj = (JSONObject) jsonObjs.opt(i);
							NWorkToDo email = new NWorkToDo();
							email.setID(jsonObj.getInt("ID"));
							email.setWorkname(jsonObj.getString("WorkName"));
							email.setFromContentString(jsonObj.getString("FormContent"));
							email.setTimestr(jsonObj.getString("TimeStr"));
							email.setFujianString(jsonObj.getString("FuJianList"));
							email.setUsernameString(jsonObj.getString("UserName"));
							email.setFormid(jsonObj.getInt("FormID"));
							email.setFlowid(jsonObj.getInt("WorkFlowID"));
							email.setJiedianid(jsonObj.getInt("JieDianID"));
							email.setJiedianNameString(jsonObj.getString("JieDianName"));
							email.setShenpiyjString(jsonObj.getString("ShenPiYiJian"));
							email.setShenpiUserString(jsonObj.getString("ShenPiUserList"));
							email.setOkuserlist(jsonObj.getString("OKUserList"));
							email.setStateNowString(jsonObj.getString("StateNow"));
							email.setWenhaoString(jsonObj.getString("WenHao"));
							email.setLatetimeString(jsonObj.getString("LateTime"));
							email.setBeiyong1(jsonObj.getString("BeiYong1"));
							email.setBeiyong2(jsonObj.getString("BeiYong2"));
							JSONArray nodArray = jsonObj.getJSONArray("NodeItem");
							List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
							for (int j = 0; j < nodArray.length(); j++) {
									JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
									WorkFlowItem wf = new WorkFlowItem();
									wf.setValueString(nodejsonObj.getString("Values"));
									wf.setTextString(nodejsonObj.getString("Texts"));
									wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
									wf.setSpmsString(nodejsonObj.getString("Spms"));
									wf.setPsmsString(nodejsonObj.getString("Psms"));
									wf.setMrsprString(nodejsonObj.getString("Mrspr"));
									nodeItems.add(wf);
							}
							email.setNodeList(nodeItems);
							list.add(email);
						}
						Message msg = new Message();
						msg.what = 2;
						msg.obj = list;
						handler.sendMessage(msg);
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}
	private void  geylog(final String id){
		new Thread(new Runnable() {

			@Override
			public void run() {
				json = WebServiceUtil.everycanforStr2("sbid", "", "","", "", "",
						id  ,"",  "", "", "",0, "GetSheBeiLog");
				if ( !json.equals("暂无记录")) {
					Gson gson = new Gson();
					NWorkToDo ap = new NWorkToDo();
					ap = gson.fromJson(json, NWorkToDo.class);
					list2.add(ap);
				}
				Message msg = new Message();
				msg.what = 4;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {



	}



	public void btn_ref(View v) {

		onRefresh();
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			 
			/* Intent intent = new Intent(WorkFlowDBActivity.this, MainWexin.class);
				startActivity(intent);*/
				finish();
			 
		 }
		return super.onKeyDown(keyCode, event);
	}
	
	public void bt_back(View v){

		finish();
	}
	
	public void bt_back2(View v){

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
			StatusBarUtils.setStatusBarColor(WorkFlowShebei.this, R.color.btground);
		}
	}
	
	
}
