package com.example.hbkjgoa.ryxz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMLXAdapter3_RY;
import com.example.hbkjgoa.dbsx.Info_DoFlow;
import com.example.hbkjgoa.dbsx.Info_DoFlowTH;
import com.example.hbkjgoa.model.TXL_New;
import com.example.hbkjgoa.model.TXL_New2;
import com.example.hbkjgoa.sqsp.Info_DoFlow_xj;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
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

public class ZCSL_BM extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private String wz;
	private String str;
	private String spname = "", pageindex = "", wantto = "", proName = "", prodq = "", yearnd = "", proProfession = "",
			proProfessionchild = "";
	private String strseq = "";
	private String strseq2 = "";
	private String spword, ulx;
	private SharedPreferences sharedPreferences;
	private AlertDialog dlg;
	private String json, searchstr = "";
	private XMLXAdapter3_RY adapter;
	private int fid = 0;
	private int step = 1;
	private String pid3 = "0";
	private boolean webbing = false;
	private List<TXL_New2> list = new ArrayList<TXL_New2>();
	private ListView XKGListView;
	private Button login_reback_btn, serch;
	private ImageView addsb;
	private Boolean iswebbing = false;
	private int page = 1;
	public static ZCSL_BM listact;
	private XListView mListView;
	private XListViewFooter footer;
	private String methodString = "";
	private TextView title, zs;
	private Animation loadAnimation;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private Double geoLat;
	private Double geoLng;
	private String lat, record;
	private String lng;
	private Handler mHandler;
	RelativeLayout title1;
	private boolean havesou=false;
	List<Integer> listItemID = new ArrayList<Integer>();
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, bqb;
	private TXL_New email;
    LoadingDialog dialog1;
	RelativeLayout ti1;
	@SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
                dialog1.dismiss();
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			//	zs.setText("总数:" + record + "个");
				setData(list);
			} else if (msg.what == 2) {
				list.clear();
				list = (List<TXL_New2>) msg.obj;
				adapter.setmes((List<TXL_New2>) msg.obj);
				mListView.stopRefresh();
				adapter.notifyDataSetChanged();
				mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
				TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
				tx.setVisibility(View.VISIBLE);
			} else if (msg.what == 3) {
				adapter.setmes(list);
				mListView.stopLoadMore();
				adapter.notifyDataSetChanged();
			}

		}

	};

	private void setData(List<TXL_New2> listitem) {
		adapter = new XMLXAdapter3_RY(ZCSL_BM.this, listitem);
		mListView.setAdapter(adapter);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.txl);
		sharedPreferences = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		spword = sharedPreferences.getString("spword", "");
		ulx = sharedPreferences.getString("ulx", "");
		listact = this;
		Intent intent = this.getIntent();
		email=(TXL_New)intent.getSerializableExtra("TXL_New");
/*		for (Childlist item : email.getChildlist()) {
			Toast.makeText(getApplicationContext(), item.getIdString(),
				     Toast.LENGTH_SHORT).show();
		}*/
		

		//methodString = this.getIntent().getStringExtra("method");

		mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getInfo("");
		initStatusBar();



	}



	private  void findview(){
		title = (TextView) findViewById(R.id.title);
		ti1 = (RelativeLayout) findViewById(R.id.ti);
		title.setText("选择人员");
		zs = (TextView) findViewById(R.id.zs);
		serch = (Button) findViewById(R.id.serch);
		serch.setVisibility(View.VISIBLE);
		ti1.setVisibility(View.VISIBLE);
		title1=findViewById(R.id.ti);
		title1.setVisibility(View.GONE);
		serch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				listItemID.clear();
				for (int i = 0; i < adapter.mChecked.size(); i++) {
					if (adapter.mChecked.get(i)) {
						listItemID.add(i);
					}
				}

				if (listItemID.size() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(ZCSL_BM.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setMessage("没有选中任何记录");
					builder1.show();

				} else {
					String sb = "";
					if(havesou){
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i)).getUserName()+",";
						}
					}else{
						for (int i = 0; i < listItemID.size(); i++) {
							sb += list.get(listItemID.get(i)) .getUserName()+",";
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
					if (BM.listact != null) {
						BM.listact.getActivity().finish();
					}


					if (Info_DoFlow.listact != null) {
						if (sb.endsWith(",")) {
							Info_DoFlow.listact.setry(sb.substring(0, sb.length() - 1));
						} else {
							localIntent.putExtra("dclrl", sb);
							Info_DoFlow.listact.setry(sb);
						}

					}

					if (Info_DoFlow_xj.listact != null) {
						if (sb.endsWith(",")) {
							Info_DoFlow_xj.listact.setry(sb.substring(0, sb.length() - 1));
						} else {
							localIntent.putExtra("dclrl", sb);
							Info_DoFlow_xj.listact.setry(sb);
						}

					}
					if (Info_DoFlowTH.listact != null) {
						if (sb.endsWith(",")) {
							Info_DoFlowTH.listact.setry(sb.substring(0, sb.length() - 1));
						} else {
							localIntent.putExtra("dclrl", sb);
							Info_DoFlowTH.listact.setry(sb);
						}

					}
				}
			}
		});
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProProfessionchild() {
		return proProfessionchild;
	}

	public void setProProfessionchild(String proProfessionchild) {
		this.proProfessionchild = proProfessionchild;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TXL_New2 localGZRZ =list.get(arg2-1);
		Intent localIntent = new Intent();
		if (localGZRZ.getUserName().endsWith(",")) {
			localIntent.putExtra("dclrl",localGZRZ.getUserName().substring(0, localGZRZ.getUserName().length() - 1));
		} else {
			localIntent.putExtra("dclrl", localGZRZ.getUserName());
		}
		setResult(102, localIntent);
		finish();
		if (BM.listact != null) {
			BM.listact.getActivity().finish();
		}


		if (Info_DoFlow.listact != null) {
			if (localGZRZ.getUserName().endsWith(",")) {
				Info_DoFlow.listact.setry(localGZRZ.getUserName().substring(0, localGZRZ.getUserName().length() - 1));
			} else {
				localIntent.putExtra("dclrl", localGZRZ.getUserName());
				Info_DoFlow.listact.setry(localGZRZ.getUserName());
			}

		}

		if (Info_DoFlow_xj.listact != null) {
			if (localGZRZ.getUserName().endsWith(",")) {
				Info_DoFlow_xj.listact.setry(localGZRZ.getUserName().substring(0, localGZRZ.getUserName().length() - 1));
			} else {
				localIntent.putExtra("dclrl", localGZRZ.getUserName());
				Info_DoFlow_xj.listact.setry(localGZRZ.getUserName());
			}

		}
		if (Info_DoFlowTH.listact != null) {
			if (localGZRZ.getUserName().endsWith(",")) {
				Info_DoFlowTH.listact.setry(localGZRZ.getUserName().substring(0, localGZRZ.getUserName().length() - 1));
			} else {
				localIntent.putExtra("dclrl", localGZRZ.getUserName());
				Info_DoFlowTH.listact.setry(localGZRZ.getUserName());
			}

		}
	}


	private void getInfo(String name) {
		boolean havenet = NetHelper.IsHaveInternet(ZCSL_BM.this);
		if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(ZCSL_BM.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo",
														   spname, email.getMC(), "", "", "",1, "GetUsersListFZ");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
						list=gson.fromJson(json, type);	
					}
					
					
					
					Message msg = new Message();
					msg.what = 1;							
					handler.sendMessage(msg);
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCSL_BM.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}
}

	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		page = 1;
		strseq = "";
		new Thread(new Runnable() {
			@Override
			public void run() {/*
				List<TXL_New> list = new ArrayList<TXL_New>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("username", "", "", "", "", "pid", 
						   spname, "", "", "", "",0, "GetDept");
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<TXL_New>>(){}.getType();
					list=gson.fromJson(json, type);	
				}
				Message msg = new Message();
				msg.what = 2;	
				msg.obj=list;
				handler.sendMessage(msg);
			*/
				List<TXL_New> list = new ArrayList<TXL_New>();
				webbing = true;
				json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo", 
													   spname, email.getMC(), "", "", "",page, "GetUsersListFZ");
				Log.d("gsp", "GetDept" + ":" + json);
				if (json != null && !json.equals("0")) {
					Gson gson=new Gson();
					Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
					list=gson.fromJson(json, type);	
				}
				
				
				
				Message msg = new Message();
				msg.what = 2;	
				msg.obj=list;
				handler.sendMessage(msg);
			
			}
		}).start();

	}

	public void onLoadMore() {
		page++;
		boolean havenet = NetHelper.IsHaveInternet(ZCSL_BM.this);
		if (havenet) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;
					json = WebServiceUtil.everycanforStr2("userid", "fenzu", "", "", "", "pageNo", 
														   spname, email.getMC(), "", "", "",page, "GetUsersListFZ");
					Log.d("gsp", "GetDept" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson=new Gson();
						List<TXL_New2> list2 = new ArrayList<TXL_New2>();
						Type type=new TypeToken<ArrayList<TXL_New2>>(){}.getType();
						list2=gson.fromJson(json, type);		
						list.addAll(list2);
						
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);
					}
					
					
					
				
				}
			}).start();
		} else {
			new AlertDialog.Builder(ZCSL_BM.this).setMessage("请检查网络连接设置！").setTitle("无网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();

		}
}

	/*
	 * public void showProgress() { if (progressDialog != null) {
	 * progressDialog.cancel(); } progressDialog1 = new
	 * CustomProgressDialog(XMGLCX.this, ""); progressDialog1.show(); }
	 */

	public String getWantto() {
		return wantto;
	}

	public void setWantto(String wantto) {
		this.wantto = wantto;
	}

	public String getProProfession() {
		return proProfession;
	}

	public void setProProfession(String proProfession) {
		this.proProfession = proProfession;
	}

	public void bt_back(View v) {
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
			StatusBarUtils.setStatusBarColor(ZCSL_BM.this, R.color.btground);
		}
	}
}
