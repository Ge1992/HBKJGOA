package com.example.hbkjgoa.Email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.UsersListViewAdapter;
import com.example.hbkjgoa.model.Users;
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


public class ChooseUserActivity_dx extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private boolean webbing = false;
	private EditText editText1;
	private UsersListViewAdapter listViewAdapter;
	private TextView Text1;
	private List<Users> list = new ArrayList<Users>();
	private List<Users> listItems = new ArrayList<Users>();

	private String json;
	private XListView mListView;
	private XListViewFooter footer;
	private Dialog progressDialog;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private boolean havesou=false;
	List<Integer> listItemID = new ArrayList<Integer>();
	Button show;
	LoadingDialog dialog1;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				setData(list);
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<Users>) msg.obj;
				listViewAdapter.setmes((List<Users>) msg.obj);
				mListView.stopRefresh();
				listViewAdapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 3) {
				listViewAdapter.setmes(list);
				mListView.stopLoadMore();
				listViewAdapter.notifyDataSetChanged();
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			
			}

		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.chooseusers);
		String spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(listViewAdapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getListItems();
		initStatusBar();
		setTextWatch();

	}



	private void findview(){
		show = (Button) findViewById(R.id.sub);
		editText1=(EditText)findViewById(R.id.editText1);
		show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				listItemID.clear();
				for (int i = 0; i < listViewAdapter.mChecked.size(); i++) {
					if (listViewAdapter.mChecked.get(i)) {
						listItemID.add(i);
					}
				}

				if (listItemID.size() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							ChooseUserActivity_dx.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setMessage("没有选中任何记录");
					builder1.show();

				} else {
					String sb = "";


					if(havesou){
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i))
									.getUsername()+",";
						}
					}else{
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i))
									.getUsername()+",";
						}
					}


					Intent localIntent = new Intent();
					if (sb.endsWith(",")) {
						localIntent.putExtra("dclrl",sb.substring(0, sb.length() - 1));
					} else {
						localIntent.putExtra("dclrl", sb);
					}
					setResult(102, localIntent);
					finish();
				}
			}
		});
	}
	private void setData(List<Users> listitem) {
		listViewAdapter = new UsersListViewAdapter(ChooseUserActivity_dx.this, listitem);
		mListView.setAdapter(listViewAdapter);
	}
	private void setTextWatch() {
		 TextWatcher watcher=new TextWatcher(){
	        	
				@Override
				public void afterTextChanged(Editable arg0) {
					havesou=true;
					Log.d("yin","编辑"+editText1.getText().toString());
					String sou=editText1.getText().toString();
					listItems.clear();
					if (list!=null&&list.size()>0) {
						for(int i = 0; i < list.size() ; i++){
							if(list.get(i).getUsername().toString().contains(sou)){
								listItems.add(list.get(i));
							}
						}
						Message msg = new Message();
						msg.what = 2;
						msg.obj=listItems;
						handler.sendMessage(msg);
					}
					
					
				}
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
				}
	        	
	        };
	        
	        editText1.addTextChangedListener(watcher);		
}

	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Users> list = new ArrayList<Users>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("", "", "", "","", "iPageNo",
						"", "", "", "", "",pageno,"GetUsers");
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Users>>() {
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


	

	private void getListItems() {
		boolean havenet = NetHelper.IsHaveInternet(ChooseUserActivity_dx.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(ChooseUserActivity_dx.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("", "", "", "","", "iPageNo", 
							"", "", "", "", "",pageno,"GetUsers");
					Log.d("gsp", "GetXMList" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<Users>>() {
						}.getType();
						list = gson.fromJson(json, type);
					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {
			ToastUtils.showToast(ChooseUserActivity_dx.this, "网络连接失败！");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	
	
	public void bt_back(View v){
		finish();
	}

	@Override
	public void onLoadMore() {
		Log.d("yuan", "加载更多已经被执行到…………");
		pageno++;
		new Thread(new Runnable() {
			@Override
			public void run() {
				webbing = true;
				json = WebServiceUtil.everycanforStr2("", "", "", "","", "iPageNo", 
						"", "", "", "", "",pageno,"GetUsers");
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					List<Users> list2 = new ArrayList<Users>();
					Type type = new TypeToken<ArrayList<Users>>() {
					}.getType();
					list2 = gson.fromJson(json, type);
					list.addAll(list2);

					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}

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
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(ChooseUserActivity_dx.this, R.color.btground);
		}
	}
}
