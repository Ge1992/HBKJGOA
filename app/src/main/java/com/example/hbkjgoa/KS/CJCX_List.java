package com.example.hbkjgoa.KS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.CJCX_List_ListAdapter;
import com.example.hbkjgoa.model.CJCX;
import com.example.hbkjgoa.model.CJCXs;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CJCX_List extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	public static CJCX_List listact;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	LoadingDialog dialog1;
	private Dialog progressDialog;
	private String json, spname = "";
	private boolean webbing = false;
	private List<CJCX> list = new ArrayList<CJCX>();
	private XListView mListView;
	private CJCX_List_ListAdapter adapter;
	private int pageno = 1,PageSize=10;
	private TextView zs;
    private RelativeLayout title;
	private ImageView chat;
	private Button zxks;
	private	TextView tit;
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
				list = (List<CJCX>) msg.obj;
				adapter.setmes((List<CJCX>) msg.obj);
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
		setContentView(R.layout.listview);
		listact = this;
		spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");
		mListView = (XListView)findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		initStatusBar();

		getInfo();
	}

	private void setData(List<CJCX> listitem) {
		adapter = new CJCX_List_ListAdapter(CJCX_List.this,listitem);
		mListView.setAdapter(adapter);
	}

	private void findview() {
		title=findViewById(R.id.title);
		tit=findViewById(R.id.tit);
		zxks=findViewById(R.id.zxks);
		zxks.setVisibility(View.VISIBLE);
		title.setVisibility(View.GONE);
		tit.setText("成绩查询");
		zxks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(CJCX_List.this).setMessage("本次考试20题，每题5分，考试时间120分钟，预祝取得优异成绩").setTitle("提示")
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {}})
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent=new Intent();
								intent.setClass(CJCX_List.this, SJ_List.class);//RY_list
								startActivity(intent);
							}
						}).show();
			}
		});

	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(CJCX_List.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(CJCX_List.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json= WebServiceUtil.GetKSJieGuoList(pageno, PageSize, spname, "");

					if(json!=null&&!json.equals("")&&!json.equals("null")){

						CJCXs jj= JSONObject.parseObject(json,CJCXs.class);
						list=jj.getRows();

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {

		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<CJCX> list = new ArrayList<CJCX>();
				json=WebServiceUtil.GetKSJieGuoList(pageno, PageSize, spname, "");
				if(json!=null&&!json.equals("")&&!json.equals("null")){
					CJCXs jj= JSONObject.parseObject(json,CJCXs.class);
					list=jj.getRows();

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
		boolean havenet = NetHelper.IsHaveInternet(CJCX_List.this);
		if (havenet) {
			pageno++;
			new Thread() {
				@Override
				public void run() {
					json=WebServiceUtil.GetKSJieGuoList(pageno, PageSize, spname, "");
					if (json != null && !json.equals("0")) {

						try {

							List<CJCX> list2 = new ArrayList<CJCX>();
							CJCXs jj= JSONObject.parseObject(json,CJCXs.class);
							list2=jj.getRows();
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
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(CJCX_List.this, R.color.btground);
		}
	}
}