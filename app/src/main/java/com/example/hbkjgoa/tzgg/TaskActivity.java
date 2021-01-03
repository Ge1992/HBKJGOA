package com.example.hbkjgoa.tzgg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.GGListViewAdapter;
import com.example.hbkjgoa.model.LanGongGao;
import com.example.hbkjgoa.model.QS_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskActivity extends Fragment implements XListView.IXListViewListener, OnItemClickListener {
	private ListView listview;
	private GGListViewAdapter listViewAdapter;
	private TextView Text1;
	private List<LanGongGao> list = new ArrayList<LanGongGao>();
	private String json;
	private int EmailPageNo = 1;
	private String spname,TrueName;
	 private String LX = "";
	public static TaskActivity mm;
	private int newid;
	private Dialog progressDialog;
	public static TaskActivity listen;
	private XListView mListView;
	private XListViewFooter footer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private String a,zt="";
	LoadingDialog dialog1;

	@SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				dialog1.dismiss();
				setData(list);

			} else if (msg.what == 2) {
				list.clear();
				list = (List<LanGongGao>) msg.obj;
				listViewAdapter.setmes((List<LanGongGao>) msg.obj);
				mListView.stopRefresh();
				listViewAdapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
			} else if (msg.what == 3) {
				listViewAdapter.setmes(list);
				listViewAdapter.notifyDataSetChanged();
				mListView.stopLoadMore();
			}

		}

	};
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取

			getActivity().finish();
		}
		return false;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.main_tab_address, container, false);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mm = this;
		spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		Bundle bundle = getArguments();// 从activity传过来的Bundle


		LX = bundle.getString("LX");
		zt = bundle.getString("zt");
		spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		TrueName = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("TrueName", "");
		a = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("a", "");

		mListView = (XListView) getView().findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(listViewAdapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		mListView.setCacheColorHint(0x00000000);
		mListView.setDivider(getResources().getDrawable(R.color.divider_color));
		mListView.setDividerHeight(1);
		getListItems();
	}






	private void setData(List<LanGongGao> listitem) {
		listViewAdapter = new GGListViewAdapter(getActivity(), listitem);
		mListView.setAdapter(listViewAdapter);
	}
	
	private void getListItems() {
		
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(getActivity())
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread() {
				@Override
				public void run() {

					json = WebServiceUtil.everycanforStr2("trueuname", "uname", "LX", "zt", "", "iPageNo",
							TrueName,  spname, LX, zt,"", EmailPageNo, "GetDZGG");
					if (json != null && !json.equals("0")) {
						JSONArray jsonObjs;
						JSONObject jsonObj;
						JSONTokener jsonTokener = new JSONTokener(json);
						try {
							jsonObjs = (JSONArray) jsonTokener.nextValue();
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								LanGongGao gonggao = new LanGongGao();
								gonggao.setID(jsonObj.getInt("ID"));
								gonggao.setTitleString(jsonObj.getString("TitleStr"));
								gonggao.setContentString(jsonObj.getString("ContentStr"));
								gonggao.setTimeString(jsonObj.getString("TimeStr"));
								gonggao.setNoticeType(jsonObj.getString("NoticeType"));
								gonggao.setFujianString(jsonObj.getString("FuJian"));
								gonggao.setTypeString(jsonObj.getString("TypeStr"));
								gonggao.setUserBumenString(jsonObj.getString("UserBuMen"));
								gonggao.setUsernameString(jsonObj.getString("UserName"));
								gonggao.setSFYY(jsonObj	.getString("SFYY"));

								JSONArray dqArray = jsonObj .getJSONArray("qslist");
								List<QS_Bean> dqItems = new ArrayList<QS_Bean>();
								for (int j = 0; j < dqArray.length(); j++) {
									JSONObject nodejsonObj = (JSONObject) dqArray.opt(j);
									QS_Bean wf = new QS_Bean();
									wf.setUserid(nodejsonObj.getString("userid"));
									wf.setBm(nodejsonObj.getString("bm"));
									wf.setTruename(nodejsonObj.getString("truename"));
									wf.setReadtime(nodejsonObj.getString("readtime"));
									wf.setSFYD(nodejsonObj.getString("SFYD"));

									dqItems.add(wf);

								}
								gonggao.setQd_list(dqItems);

								list.add(gonggao);

							}

							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}catch (Exception e) {

						}

					}

				}
			}.start();

		} else {
			Toast.makeText(getActivity(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

		}
	}
	@Override
	public void onRefresh() {
		EmailPageNo=1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<LanGongGao> list = new ArrayList<LanGongGao>();
				json = WebServiceUtil.everycanforStr2("trueuname", "uname", "LX", "zt", "", "iPageNo",
						TrueName,  spname, LX, zt,"", EmailPageNo, "GetDZGG");
				if (json != null && !json.equals("0")) {
					JSONArray jsonObjs;
					JSONObject jsonObj;
					JSONTokener jsonTokener = new JSONTokener(json);
					try {
						jsonObjs = (JSONArray) jsonTokener.nextValue();
						for (int i = 0; i < jsonObjs.length(); i++) {
							jsonObj = (JSONObject) jsonObjs.opt(i);
							LanGongGao gonggao = new LanGongGao();
							gonggao.setID(jsonObj.getInt("ID"));
							gonggao.setTitleString(jsonObj.getString("TitleStr"));
							gonggao.setContentString(jsonObj.getString("ContentStr"));
							gonggao.setTimeString(jsonObj.getString("TimeStr"));
							gonggao.setNoticeType(jsonObj.getString("NoticeType"));
							gonggao.setFujianString(jsonObj.getString("FuJian"));
							gonggao.setTypeString(jsonObj.getString("TypeStr"));
							gonggao.setUserBumenString(jsonObj.getString("UserBuMen"));
							gonggao.setUsernameString(jsonObj.getString("UserName"));
							gonggao.setSFYY(jsonObj	.getString("SFYY"));

							JSONArray dqArray = jsonObj .getJSONArray("qslist");
							List<QS_Bean> dqItems = new ArrayList<QS_Bean>();
							for (int j = 0; j < dqArray.length(); j++) {
								JSONObject nodejsonObj = (JSONObject) dqArray.opt(j);
								QS_Bean wf = new QS_Bean();
								wf.setUserid(nodejsonObj.getString("userid"));
								wf.setBm(nodejsonObj.getString("bm"));
								wf.setTruename(nodejsonObj.getString("truename"));
								wf.setReadtime(nodejsonObj.getString("readtime"));
								wf.setSFYD(nodejsonObj.getString("SFYD"));

								dqItems.add(wf);

							}
							gonggao.setQd_list(dqItems);

							list.add(gonggao);

						}
						Message msg = new Message();
						msg.what = 2;
						msg.obj = list;
						handler.sendMessage(msg);
					}catch (Exception e) {

					}
				}
			}
		}).start();
	}


	
	@Override
	public void onLoadMore() {
		EmailPageNo++;
		Log.d("yin", "EmailPageNo：" + EmailPageNo);
		new Thread(new Runnable() {

			@Override
			public void run() {
				json = WebServiceUtil.everycanforStr2("trueuname", "uname", "LX", "zt", "", "iPageNo",
						TrueName,  spname, LX, zt,"", EmailPageNo, "GetDZGG");

				if (json != null && !json.equals("0")) {
					JSONArray jsonObjs;
					JSONObject jsonObj;
					JSONTokener jsonTokener = new JSONTokener(json);
					try {
						jsonObjs = (JSONArray) jsonTokener.nextValue();
						for (int i = 0; i < jsonObjs.length(); i++) {
							jsonObj = (JSONObject) jsonObjs.opt(i);
							LanGongGao gonggao = new LanGongGao();
							gonggao.setID(jsonObj.getInt("ID"));
							gonggao.setTitleString(jsonObj.getString("TitleStr"));
							gonggao.setContentString(jsonObj.getString("ContentStr"));
							gonggao.setTimeString(jsonObj.getString("TimeStr"));
							gonggao.setNoticeType(jsonObj.getString("NoticeType"));
							gonggao.setFujianString(jsonObj.getString("FuJian"));
							gonggao.setTypeString(jsonObj.getString("TypeStr"));
							gonggao.setUserBumenString(jsonObj.getString("UserBuMen"));
							gonggao.setUsernameString(jsonObj.getString("UserName"));
							gonggao.setSFYY(jsonObj	.getString("SFYY"));

							JSONArray dqArray = jsonObj .getJSONArray("qslist");
							List<QS_Bean> dqItems = new ArrayList<QS_Bean>();
							for (int j = 0; j < dqArray.length(); j++) {
								JSONObject nodejsonObj = (JSONObject) dqArray.opt(j);
								QS_Bean wf = new QS_Bean();
								wf.setUserid(nodejsonObj.getString("userid"));
								wf.setBm(nodejsonObj.getString("bm"));
								wf.setTruename(nodejsonObj.getString("truename"));
								wf.setReadtime(nodejsonObj.getString("readtime"));
								wf.setSFYD(nodejsonObj.getString("SFYD"));

								dqItems.add(wf);

							}
							gonggao.setQd_list(dqItems);

							list.add(gonggao);

						}

						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);
					}catch (Exception e) {

					}
				}
			}
		}).start();
			
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	/*	ListView listView = (ListView) arg0;
		Log.d("yin", ":" + arg0);
		LanGongGao email = new LanGongGao();
		email = listItems.get(arg2);
		TaskActivity.listen.setNewid(email.getID());
		

		Intent intent = new Intent();
		intent.setClass(TaskActivity.this, InfoGongGao.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("LanGongGao", email);
		intent.putExtras(bundle);
		startActivity(intent);*/
	
	}



	public void bt_back(View v) {

		getActivity().finish();
	}

	public void bt_back2(View v) {

		getActivity().finish();
	}


}
