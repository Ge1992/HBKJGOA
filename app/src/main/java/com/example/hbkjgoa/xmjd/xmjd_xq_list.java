package com.example.hbkjgoa.xmjd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMJDAdapter;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class xmjd_xq_list extends FragmentActivity {
	private String json,searchstr="";
	private XMJDAdapter adapter;
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	private String lx="",name="",spname;
	public static xmjd_xq_list listact;
	private String[] mdq;
	private List<XMJD_Bean> list=new ArrayList<XMJD_Bean>();
	TextView t1,t2,t3,t4,t5,t6,L1,L2,L3;
	LoadingDialog dialog1;
	Fragment fragment1;
	Fragment fragment2;
	Fragment fragment3;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				dialog1.dismiss();
				for(int i=0;i<list.size();i++){
					t1.setText(list.get(i).getProjectName());
					t2.setText(list.get(i).getProjectSerils());
					t3.setText(list.get(i).getSuoShuKeHu());
					t4.setText(list.get(i).getXiangMuJinE());
					t5.setText(list.get(i).getXiangMuDaXiao());
					t6.setText(list.get(i).getBackInfo());
				}

			}

		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_xmjd);
		listact = this;
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");

		findview();
		getInfo();
		initFragment(0);
		setOnclick();
		initStatusBar();
	}



	private void findview(){

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);

		t1=findViewById(R.id.t1);
		t2=findViewById(R.id.t2);
		t3=findViewById(R.id.t3);
		t4=findViewById(R.id.t4);
		t5=findViewById(R.id.t5);
		t6=findViewById(R.id.t6);

		L1=findViewById(R.id.text1);
		L2=findViewById(R.id.text2);
		L3=findViewById(R.id.text3);
	}
	private void setOnclick() {
		L1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(0);

				L1.setTextColor(Color.rgb(255, 0, 0));
				L2.setTextColor(Color.rgb(0, 0, 0));
				L3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		L2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(1);

				L2.setTextColor(Color.rgb(255, 0, 0));
				L1.setTextColor(Color.rgb(0, 0, 0));
				L3.setTextColor(Color.rgb(0, 0, 0));

			}
		});
		L3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initFragment(2);
				L3.setTextColor(Color.rgb(255, 0, 0));
				L1.setTextColor(Color.rgb(0, 0, 0));
				L2.setTextColor(Color.rgb(0, 0, 0));

			}
		});

	}

	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(xmjd_xq_list.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(xmjd_xq_list.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(new Runnable() {
				@Override
				public void run() {

			try {
					json = WebServiceUtil.everycanforStr("projID", "userName", "", "pageNo","5", spname, "", 1, "Project_Details");

					if(json != null && !json.equals("0")) {
						JSONObject jObj = new JSONObject(json);
						JSONObject jObj1 = jObj.getJSONObject("project");
						for (int i = 0; i < jObj1.length(); i++) {
							XMJD_Bean ap = new XMJD_Bean();
							ap.setID(jObj1.getString("ID"));
							ap.setProjectName(jObj1.getString("ProjectName"));
							ap.setProjectSerils(jObj1.getString("ProjectSerils"));
							ap.setSuoShuKeHu(jObj1.getString("SuoShuKeHu"));
							ap.setYuJiChengJiaoRiQi(jObj1.getString("YuJiChengJiaoRiQi"));
							ap.setTiXingDate(jObj1.getString("TiXingDate"));
							ap.setFuZeRen(jObj1.getString("FuZeRen"));
							ap.setXiangMuJinE(jObj1.getString("XiangMuJinE"));

							ap.setXiangMuYuSuan(jObj1.getString("XiangMuYuSuan"));
							ap.setXiangMuDaXiao(jObj1.getString("XiangMuDaXiao"));
							ap.setPeiHeRenList(jObj1.getString("PeiHeRenList"));
							ap.setUserName(jObj1.getString("UserName"));
							ap.setTimeStr(jObj1.getString("TimeStr"));
							ap.setHeTongAndFuJian(jObj1.getString("HeTongAndFuJian"));
							ap.setBackInfo(jObj1.getString("BackInfo"));


							list.add(ap);
					}

					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

					} catch (Exception e) {
						// TODO: handle exception
					}

				}

			}).start();
		} else {
			ToastUtils.showToast(xmjd_xq_list.this, "网络连接失败！");
		}
	}



	private  void sate() {
		for (int i = 0; i < list.size(); i++) {

		}

	}
	public void bt_back(View v){
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
			StatusBarUtils.setStatusBarColor(xmjd_xq_list.this, R.color.btground);
		}
	}

	private void initFragment(int index) {
		// 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 开启事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 隐藏所有Fragment
		hideFragment(transaction);
		switch (index) {
			case 0:
				if (fragment1 == null) {
					fragment1 = new xmjd_Fragment1();
					Bundle bundle = new Bundle();
					bundle.putString("method", " ");
					fragment1.setArguments(bundle);

					transaction.add(R.id.ZTQK_content, fragment1);
				} else {
					transaction.show(fragment1);
				}
				break;
			case 1:
				if (fragment2 == null) {
					fragment2 = new xmjd_Fragment1();
					Bundle bundle = new Bundle();
					bundle.putString("method", " ");
					fragment2.setArguments(bundle);

					transaction.add(R.id.ZTQK_content, fragment2);
				} else {
					transaction.show(fragment2);
				}

				break;
			case 2:
				if (fragment3 == null) {
					fragment3 = new xmjd_Fragment1();
					Bundle bundle = new Bundle();
					bundle.putString("method", " ");
					fragment3.setArguments(bundle);

					transaction.add(R.id.ZTQK_content, fragment3);
				} else {
					transaction.show(fragment3);
				}

				break;
			default:
				break;
		}
		// 提交事务
		transaction.commit();
	} // 隐藏Fragment

	private void hideFragment(FragmentTransaction transaction) {
		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
		if (fragment3 != null) {
			transaction.hide(fragment3);
		}

	}
}
