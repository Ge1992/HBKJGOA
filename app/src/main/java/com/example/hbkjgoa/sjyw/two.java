package com.example.hbkjgoa.sjyw;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.DZGGAdatper;
import com.example.hbkjgoa.model.DZGG;
import com.example.hbkjgoa.util.CustomListView;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class two extends Fragment {
	public static two listact;
	private DZGGAdatper adapter;
	private String json,json2;
	private List<DZGG> list = new ArrayList<DZGG>();
	private CustomListView GroupList;// 自定义ListView
	private int page = 1;
	private Button serch;
	private String spname;
	private Dialog progressDialog;
	private ImageView im1;
	private String mc;
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
				adapter.setmes((List<DZGG>) msg.obj);
				adapter.notifyDataSetChanged();
				GroupList.onRefreshComplete();
			} else if (msg.what == 3) {
				adapter.setmes(list);
				adapter.notifyDataSetChanged();
				GroupList.onLoadMoreComplete();
			}else if (msg.what == 4) {
				onRe();
				Toast.makeText(getActivity(), "删除成功",
					     Toast.LENGTH_SHORT).show();
			}

		}

	};

	protected void setData(List<DZGG> list2) {
		adapter = new DZGGAdatper(getActivity(), list2);
		GroupList.setAdapter(adapter);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		return inflater.inflate(R.layout.two, container, false);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listact = this;
		Bundle bundle = getArguments();// 从activity传过来的Bundle
		mc=bundle.getString("mc");
		spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		findview();
		getInfo();

	}
	private  void findview(){
		GroupList = (CustomListView) this.getView().findViewById(R.id.GroupList);
		GroupList.setAdapter(adapter);
		GroupList.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				onRe();
			}

		});
		GroupList.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				onLo();
			}

		});
		GroupList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DZGG ap = new DZGG();
				ap = list.get(position - 1);
				Intent intent = new Intent();
				intent.setClass(getActivity(), YWFB3.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("DZGG", ap);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		im1=(ImageView) this.getView().findViewById(R.id.imahe);
		im1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();

			}
		});
	}

	protected void onLo() {
		page++;
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			new Thread() {
				public void run() {
					json = WebServiceUtil.everycanforStr2("search", "uname", "LX", "zt", "", "iPageNo",
							"",  spname, mc, "2","", page, "GetDZGG");
					Log.d("yuan", json);
					if (json != null && !json.equals("0")) {
						JSONObject jsonObj;
						JSONArray jsonObjs = null;
						try {
							jsonObjs = new JSONArray(json);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						try {
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								DZGG rz = new DZGG();
								if (jsonObj.getString("ID") != null)
									rz.setID(jsonObj.getString("ID"));
								else
									rz.setID("");
								if (jsonObj.getString("TimeStr") != null)
									rz.setTimeStr(jsonObj.getString("TimeStr"));
								else
									rz.setTimeStr("");
								if (jsonObj.getString("TitleStr") != null)
									rz.setTitleStr(jsonObj.getString("TitleStr"));
								else
									rz.setTitleStr("");
								if (jsonObj.getString("UserName") != null)
									rz.setUserName(jsonObj.getString("UserName"));
								else
									rz.setUserName("");
								if (jsonObj.getString("UserBuMen") != null)
									rz.setUserBuMen(jsonObj.getString("UserBuMen"));
								else
									rz.setUserBuMen("");
								if (jsonObj.getString("ContentStr") != null)
									rz.setContentStr(jsonObj.getString("ContentStr"));
								else
									rz.setContentStr("");
								
								
								list.add(rz);
							}
						} catch (Exception e) {
						}
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);

					}

				}
			}.start();
		} else {
			new AlertDialog.Builder(getActivity()).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}

		
		
	}

	public void onRe() {
		page=1;
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			new Thread() {
				public void run() {
					List<DZGG> list = new ArrayList<DZGG>();
					json = WebServiceUtil.everycanforStr2("search", "uname", "LX", "zt", "", "iPageNo",
							"",  spname, mc, "2","", page, "GetDZGG");
					Log.d("yuan", json);
					if (json != null && !json.equals("0")) {
						JSONObject jsonObj;
						JSONArray jsonObjs = null;
						try {
							jsonObjs = new JSONArray(json);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						try {
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								DZGG rz = new DZGG();
								if (jsonObj.getString("ID") != null)
									rz.setID(jsonObj.getString("ID"));
								else
									rz.setID("");
								if (jsonObj.getString("TimeStr") != null)
									rz.setTimeStr(jsonObj.getString("TimeStr"));
								else
									rz.setTimeStr("");
								if (jsonObj.getString("TitleStr") != null)
									rz.setTitleStr(jsonObj.getString("TitleStr"));
								else
									rz.setTitleStr("");
								if (jsonObj.getString("UserName") != null)
									rz.setUserName(jsonObj.getString("UserName"));
								else
									rz.setUserName("");
								if (jsonObj.getString("UserBuMen") != null)
									rz.setUserBuMen(jsonObj.getString("UserBuMen"));
								else
									rz.setUserBuMen("");
								if (jsonObj.getString("ContentStr") != null)
									rz.setContentStr(jsonObj.getString("ContentStr"));
								else
									rz.setContentStr("");
								
								
								list.add(rz);
							}
						} catch (Exception e) {
						}
						Message msg = new Message();
						msg.what = 2;
						msg.obj = list;
						handler.sendMessage(msg);

					}

				}
			}.start();
		} else {
			new AlertDialog.Builder(getActivity()).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}


	}
	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(getActivity());
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(getActivity())
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread() {
				public void run() {
					json = WebServiceUtil.everycanforStr2("search", "uname", "LX", "zt", "", "iPageNo",
							"",  spname, mc, "2","", page, "GetDZGG");
					Log.d("yuan", json);
					if (json != null && !json.equals("0")) {
						JSONObject jsonObj;
						JSONArray jsonObjs = null;
						try {
							jsonObjs = new JSONArray(json);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						try {
							for (int i = 0; i < jsonObjs.length(); i++) {
								jsonObj = (JSONObject) jsonObjs.opt(i);
								DZGG rz = new DZGG();
								if (jsonObj.getString("ID") != null)
									rz.setID(jsonObj.getString("ID"));
								else
									rz.setID("");
								if (jsonObj.getString("TimeStr") != null)
									rz.setTimeStr(jsonObj.getString("TimeStr"));
								else
									rz.setTimeStr("");
								if (jsonObj.getString("TitleStr") != null)
									rz.setTitleStr(jsonObj.getString("TitleStr"));
								else
									rz.setTitleStr("");
								if (jsonObj.getString("UserName") != null)
									rz.setUserName(jsonObj.getString("UserName"));
								else
									rz.setUserName("");
								if (jsonObj.getString("UserBuMen") != null)
									rz.setUserBuMen(jsonObj.getString("UserBuMen"));
								else
									rz.setUserBuMen("");
								if (jsonObj.getString("ContentStr") != null)
									rz.setContentStr(jsonObj.getString("ContentStr"));
								else
									rz.setContentStr("");
								list.add(rz);
							}
						} catch (Exception e) {
						}
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);

					}

				}
			}.start();
		} else {
			new AlertDialog.Builder(getActivity()).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}

	}
	
	
	 public void XWGLDel(final String id){
			new Thread(new Runnable() {
				@Override
				public void run() {
					json2=WebServiceUtil.everycanforStr2("id", "", "", "", "", "", id, "", "", "", "",0, "XWGLDel");
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);	
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
