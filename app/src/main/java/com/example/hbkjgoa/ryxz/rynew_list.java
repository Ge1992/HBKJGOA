package com.example.hbkjgoa.ryxz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.RYXZ_NAdapter2;
import com.example.hbkjgoa.model.RYXZ_NBean;
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

public class rynew_list extends Fragment implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json,searchstr="";
	private boolean webbing = false;
	private List<RYXZ_NBean> list = new ArrayList<RYXZ_NBean>();
	private XListView mListView;
	private RYXZ_NAdapter2 adapter;
	private EditText editText1;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	RelativeLayout R1;
	private Button 	show ;
	private String lx="",name="",spname;
	List<Integer> listItemID = new ArrayList<Integer>();
	private boolean havesou=false;
	public static rynew_list listact;
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
				list = (List<RYXZ_NBean>) msg.obj;
				adapter.setmes((List<RYXZ_NBean>) msg.obj);
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



	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.chooseusers_new, container, false);

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//启动activity时不自动弹出软键盘
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		 spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		listact = this;
		spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView) getView().findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		mListView.setDividerHeight(0);
		findview();
		getInfo();
		initStatusBar();
	}






	private void setData(List<RYXZ_NBean> listitem) {
		adapter = new RYXZ_NAdapter2(getActivity(), listitem);
		mListView.setAdapter(adapter);
	}

	private void findview(){
		title=(TextView) getView().findViewById(R.id.title);
		editText1=(EditText)getView().findViewById(R.id.editText1);
		R1=getView().findViewById(R.id.R1);
		R1.setVisibility(View.GONE);
		editText1.setVisibility(View.GONE);
		show = (Button)getView(). findViewById(R.id.sub);
		show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				listItemID.clear();
				for (int i = 0; i < adapter.mChecked.size(); i++) {
					if (adapter.mChecked.get(i)) {
						listItemID.add(i);
					}
				}

				if (listItemID.size() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setMessage("没有选中任何记录");
					builder1.show();

				} else {
					String sb = "";
					if(havesou){
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i)).getUsername()+",";
						}
					}else{
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i)) .getUsername()+",";
						}
					}
					Intent localIntent = new Intent();
					if (sb.endsWith(",")) {
						localIntent.putExtra("dclrl",sb.substring(0, sb.length() - 1));
					} else {
						localIntent.putExtra("dclrl", sb);
					}
					getActivity().setResult(102, localIntent);
					getActivity().finish();
				}
			}
		});
}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(getActivity())
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json = WebServiceUtil.everycanforStr("search", "userid", "", "iPageNo",searchstr, spname, "", 1, "GetUsers");

					Log.d("gsp", "GetUsers" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<RYXZ_NBean>>() {
						}.getType();
						list = gson.fromJson(json, type);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {
			ToastUtils.showToast(getActivity(), "网络连接失败！");
		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<RYXZ_NBean> list = new ArrayList<RYXZ_NBean>();
				webbing = true;
				json = WebServiceUtil.everycanforStr("search", "userid", "", "iPageNo",searchstr, spname, "", 1, "GetUsers");

				Log.d("gsp", "GetCompanyList" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<RYXZ_NBean>>() {
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
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			pageno++;
			new Thread() {
				@Override
				public void run() {
					json = WebServiceUtil.everycanforStr("search", "userid", "", "iPageNo",searchstr, spname, "", pageno, "GetUsers");
					if (json != null && !json.equals("0")) {

						try {

							Gson gson=new Gson();
							List<RYXZ_NBean> list2 = new ArrayList<RYXZ_NBean>();
							Type type=new TypeToken<ArrayList<RYXZ_NBean>>(){}.getType();
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

			Toast.makeText(getActivity(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

		}


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


	}
	public void bt_back(View v){

		getActivity().finish();

	}


	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */

	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(getActivity(), R.color.btground);
		}
	}
}
