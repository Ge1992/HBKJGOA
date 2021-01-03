package com.example.hbkjgoa.jydj;

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
import com.example.hbkjgoa.adapter.SBJY_Adapter2;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
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


public class JY_Activity2 extends Fragment implements XListView.IXListViewListener, OnItemClickListener {
	private ListView listview;
	private SBJY_Adapter2 listViewAdapter;
	private TextView Text1;
	private List<SBYJ_Bean> list = new ArrayList<SBYJ_Bean>();
	private String json;
	private int EmailPageNo = 1;
	private String spname,TrueName,json3,Department;
	 private String type = "";
	public static JY_Activity2 mm;
	private int newid;
	private Dialog progressDialog;
	public static JY_Activity2 listen;
	private XListView mListView;
	private XListViewFooter footer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private String a;
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
				list = (List<SBYJ_Bean>) msg.obj;
				listViewAdapter.setmes((List<SBYJ_Bean>) msg.obj);
				mListView.stopRefresh();
				listViewAdapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
			} else if (msg.what == 3) {
				listViewAdapter.setmes(list);
				listViewAdapter.notifyDataSetChanged();
				mListView.stopLoadMore();
			}else if (msg.what == 4) {
				if (json3.contains("1")){
					Toast.makeText(getActivity(), "归还成功",
							Toast.LENGTH_SHORT).show();
					onRefresh();

				}else {
					Toast.makeText(getActivity(), "归还失败",
							Toast.LENGTH_SHORT).show();
				}

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


		type = bundle.getString("type");
		spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		TrueName = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("TrueName", "");
		a = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("a", "");
		Department = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("Department", "");
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






	private void setData(List<SBYJ_Bean> listitem) {
		listViewAdapter = new SBJY_Adapter2(getActivity(), listitem);
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

					json = WebServiceUtil.everycanforStr2("userid", "bmxx", "search", "type", "", "pageNo",
							spname,  Department, "", type,"", EmailPageNo, "GetSBJYList");

					if (json != null && !json.equals("0")) {

						try {
							Gson gson = new Gson();
							Type type = new TypeToken<ArrayList<SBYJ_Bean>>() {
							}.getType();
							list = gson.fromJson(json, type);

							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
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
				List<SBYJ_Bean> list = new ArrayList<SBYJ_Bean>();

				json = WebServiceUtil.everycanforStr2("userid", "bmxx", "search", "type", "", "pageNo",
						spname,  Department, "", type,"", EmailPageNo, "GetSBJYList");
				Log.d("gsp", "GetCompanyList" + ":" + json);
				if (json != null && !json.equals("0")) {
					try {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<SBYJ_Bean>>() {
						}.getType();
						list = gson.fromJson(json, type);

						Message msg = new Message();
						msg.what = 2;
						msg.obj = list;
						handler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
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

				json = WebServiceUtil.everycanforStr2("userid", "bmxx", "search", "type", "", "pageNo",
						spname,  Department, "", type,"", EmailPageNo, "GetSBJYList");
				try {

					Gson gson=new Gson();
					List<SBYJ_Bean> list2 = new ArrayList<SBYJ_Bean>();
					Type type=new TypeToken<ArrayList<SBYJ_Bean>>(){}.getType();
					list2=gson.fromJson(json, type);
					list.addAll(list2);
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);

				} catch (Exception e) {
					e.printStackTrace();
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
	public void SBGH(final String id){
		new Thread(new Runnable() {

			@Override
			public void run() {
				json3=WebServiceUtil.everycanforStr2("ID", "userid", "", "", "", "", id, spname, "", "", "",0, "SBGH");
				Log.d("归还", json3);
				Message msg = new Message();
				msg.what = 4;
				handler.sendMessage(msg);
			}
		}).start();

	}

}
