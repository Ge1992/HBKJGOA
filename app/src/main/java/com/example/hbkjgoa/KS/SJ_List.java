package com.example.hbkjgoa.KS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.SJ_List_ListAdapter;
import com.example.hbkjgoa.model.SJ_LB_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SJ_List extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json, spname = "";
	private boolean webbing = false;
	private List<SJ_LB_Bean> list = new ArrayList<SJ_LB_Bean>();
	private XListView mListView;
	private SJ_List_ListAdapter adapter;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs, title;
	private ImageView chat;
	private Button serch;
	private	TextView tit;
	LoadingDialog dialog1;
	public static SJ_List listact;
	public SJ_LB_Bean sjaaa;
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
				list = (List<SJ_LB_Bean>) msg.obj;
				adapter.setmes((List<SJ_LB_Bean>) msg.obj);
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
		mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		mListView.setPullRefreshEnable(false);
		initStatusBar();
		findview();
		getInfo();
	}

	private void setData(List<SJ_LB_Bean> listitem) {
		adapter = new SJ_List_ListAdapter(SJ_List.this,listitem);
		mListView.setAdapter(adapter);
	}

	private void findview() {
		tit=findViewById(R.id.tit);
		tit.setText("试卷类别");
	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(SJ_List.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(SJ_List.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

					json= WebServiceUtil.GetShiJuanLB("",spname);

					Log.d("gsp", "GetShiJuanLB" + ":" + json);
					if (json != null && !json.equals("0")) {
						Gson gson = new Gson();
						Type type = new TypeToken<ArrayList<SJ_LB_Bean>>() {
						}.getType();
						list = gson.fromJson(json, type);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}).start();
		} else {

		}
	}

	public void takephoto(SJ_LB_Bean aa){
		sjaaa=aa;
		Intent intent=new Intent(SJ_List.this,SJ_Check.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("YZM",sjaaa);
		intent.putExtras(bundle);
		startActivity(intent);
	}



	@Override

	public void onLoadMore() {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	/*	SJ_LB_Bean app = new SJ_LB_Bean();
		app = list.get(arg2 - 1);
		Intent resultIntent = new Intent();
		resultIntent.putExtra("respond", app.getName());
		setResult(RESULT_OK, resultIntent);
		finish();
*/
	}

	public void bt_back(View v) {

		finish();

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

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
			StatusBarUtils.setStatusBarColor(SJ_List.this, R.color.btground);
		}
	}
}