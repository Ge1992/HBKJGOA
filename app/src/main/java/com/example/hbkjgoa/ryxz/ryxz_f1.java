package com.example.hbkjgoa.ryxz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


public class ryxz_f1 extends Fragment implements OnItemClickListener, XListView.IXListViewListener {
	private boolean webbing = false;
	private EditText editText1;
	private UsersListViewAdapter listViewAdapter;
	private TextView Text1;
	private List<Users> list = new ArrayList<Users>();
	private List<Users> listItems = new ArrayList<Users>();
	RelativeLayout r1;
    LinearLayout L1;
	ImageView chat;
	public static ryxz_f1 listact;
	private String json,searchstr="",spname;
	private XListView mListView;
	private XListViewFooter footer;
	private Dialog progressDialog;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private boolean havesou=false;
	RelativeLayout title1;
	List<Integer> listItemID = new ArrayList<Integer>();
	Button show,serch;
	LoadingDialog dialog1;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				setData(list);
				TextView tx = (TextView) getView().findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<Users>) msg.obj;
				listViewAdapter.setmes((List<Users>) msg.obj);
				mListView.stopRefresh();
				listViewAdapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				TextView tx = (TextView) getView().findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 3) {
				listViewAdapter.setmes(list);
				mListView.stopLoadMore();
				listViewAdapter.notifyDataSetChanged();
				TextView tx = (TextView)  getView().findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			
			}

		}

	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.chooseusers, container, false);

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//启动activity时不自动弹出软键盘
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		 spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		listact = this;
		 mListView = (XListView)  getView().findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(listViewAdapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getListItems("");
		initStatusBar();
		setTextWatch();
	}





	private void findview(){
		show = (Button)getView(). findViewById(R.id.sub);
		editText1=(EditText)getView().findViewById(R.id.editText1);
		chat=getView(). findViewById(R.id.chat);
		r1=getView().findViewById(R.id.R1);
        L1=getView().findViewById(R.id.L1);
		title1=getView().findViewById(R.id.title1);
		r1.setVisibility(View.GONE);
        L1.setVisibility(View.GONE);
		show.setVisibility(View.VISIBLE);
		//chat.setVisibility(View.VISIBLE);
		title1.setVisibility(View.GONE);

		chat.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {

				final EditText inputServer = new EditText(getActivity());

				new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("输入姓名")//使用默认设备 浅色主题
						.setView(inputServer)
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						}).setOnCancelListener(new DialogInterface.OnCancelListener() {
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
							getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
							sb += list.get(listItemID.get(i)).getUsername()+",";
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
	private void setData(List<Users> listitem) {
		listViewAdapter = new UsersListViewAdapter(getActivity(), listitem);
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
				json = WebServiceUtil.everycanforStr2("userid", "search", "", "","", "iPageNo",
						spname, searchstr, "", "", "",pageno,"GetUsers");
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


	

	public void getListItems(final String searchstr) {
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
					json = WebServiceUtil.everycanforStr2("userid", "search", "", "","", "iPageNo",
							spname, searchstr, "", "", "",pageno,"GetUsers");
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
			ToastUtils.showToast(getActivity(), "网络连接失败！");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Users localGZRZ =list.get(arg2-1);
		Intent localIntent = new Intent();
		if (localGZRZ.getUsername().endsWith(",")) {
			localIntent.putExtra("dclrl",localGZRZ.getUsername().substring(0, localGZRZ.getUsername().length() - 1));
		} else {
			localIntent.putExtra("dclrl", localGZRZ.getUsername());
		}
		getActivity().setResult(102, localIntent);
		getActivity().finish();
	}

	
	
	public void bt_back(View v){
		getActivity().finish();
	}

	@Override
	public void onLoadMore() {
		Log.d("yuan", "加载更多已经被执行到…………");
		pageno++;
		new Thread(new Runnable() {
			@Override
			public void run() {
				webbing = true;
				json = WebServiceUtil.everycanforStr2("userid", "search", "", "","", "iPageNo",
						spname, searchstr, "", "", "",pageno,"GetUsers");
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
			getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(getActivity(), R.color.btground);
		}
	}
}
