package com.example.hbkjgoa.xmjd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.example.hbkjgoa.adapter.XMJDAdapter;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class xmjd_list extends Activity implements OnItemClickListener, XListView.IXListViewListener {
	private Dialog progressDialog;
	private String json,searchstr="";
	private boolean webbing = false;
	private XListView mListView;
	private XMJDAdapter adapter;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private int pageno = 1;
	private TextView zs,title;
	private ImageView chat;
	private Button serch;
	private String lx="",name="",spname;
	public static xmjd_list listact;
	private List<XMJD_Bean> list=new ArrayList<XMJD_Bean>();
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				setData(list);


			} else if (msg.what == 2) {
				list.clear();
				list = (List<XMJD_Bean>) msg.obj;
				adapter.setmes((List<XMJD_Bean>) msg.obj);
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
		setContentView(R.layout.activity_xmjl_sjx);
		listact = this;
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		mListView = (XListView)findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
		mListView.setOnItemClickListener(this);
		findview();
		getInfo();
		initStatusBar();
	}

	private void setData(List<XMJD_Bean> listitem) {
		adapter = new XMJDAdapter(xmjd_list.this, listitem);
		mListView.setAdapter(adapter);
	}

	private void findview(){
		zs=(TextView) findViewById(R.id.zs);
		chat= (ImageView) findViewById(R.id.chat);
		title=(TextView) findViewById(R.id.title);
		serch=(Button) findViewById(R.id.serch);
		title.setText("项目进度");
        chat.setVisibility(View.GONE);



	}


	private void getInfo() {
		boolean havenet = NetHelper.IsHaveInternet(xmjd_list.this);
		if (havenet) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					webbing = true;

			try {
					json = WebServiceUtil.everycanforStr("", "userName", "", "pageNo","", spname, "", 1, "Project_List");
				if (json != null && !json.equals("0")) {
                    JSONTokener jsonTokener = new JSONTokener(json);
                    JSONArray  jsonObjs = (JSONArray) jsonTokener.nextValue();
                    for (int i = 0; i < jsonObjs.length(); i++) {
                        XMJD_Bean ap = new XMJD_Bean();
                        JSONObject  jsonObj = (JSONObject) jsonObjs.opt(i);
                        JSONObject jObj1 = jsonObj.getJSONObject("project");

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

						JSONArray nodArray = jsonObj.getJSONArray("projProgress");
						List<XMJD_Bean.projProgress> nodeItems = new ArrayList<XMJD_Bean.projProgress>();
						for (int j = 0; j < nodArray.length(); j++) {
							JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
							XMJD_Bean.projProgress wf = new XMJD_Bean.projProgress();
							wf.setTypeID(nodejsonObj.getString("TypeID"));
							wf.setTypeName(nodejsonObj.getString("TypeName"));
							wf.setFileCount(nodejsonObj.getString("FileCount"));

							nodeItems.add(wf);

						}
						ap.setProjList(nodeItems);
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
			ToastUtils.showToast(xmjd_list.this, "网络连接失败！");
		}
	}




	@Override
	public void onRefresh() {
		Log.d("yuan", "刷新已经被执行到…………");
		pageno = 1;
		new Thread(new Runnable() {
			@Override
			public void run() {
                try {
                    List<XMJD_Bean> list = new ArrayList<XMJD_Bean>();
                    json = WebServiceUtil.everycanforStr("", "userName", "", "pageNo","", spname, "", 1, "Project_List");
                    if (json != null && !json.equals("0")) {
                        JSONTokener jsonTokener = new JSONTokener(json);
                        JSONArray  jsonObjs = (JSONArray) jsonTokener.nextValue();
                        for (int i = 0; i < jsonObjs.length(); i++) {
                            XMJD_Bean ap = new XMJD_Bean();
                            JSONObject  jsonObj = (JSONObject) jsonObjs.opt(i);
                            JSONObject jObj1 = jsonObj.getJSONObject("project");

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
                        msg.what = 2;
                        msg.obj = list;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
			}
		}).start();

	}

	@Override
	public void onLoadMore() {

        pageno++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = WebServiceUtil.everycanforStr("", "userName", "", "pageNo","", spname, "", pageno, "Project_List");
                    if (json != null && !json.equals("0")) {
                        JSONTokener jsonTokener = new JSONTokener(json);
                        JSONArray  jsonObjs = (JSONArray) jsonTokener.nextValue();
                        for (int i = 0; i < jsonObjs.length(); i++) {
                            XMJD_Bean ap = new XMJD_Bean();
                            JSONObject  jsonObj = (JSONObject) jsonObjs.opt(i);
                            JSONObject jObj1 = jsonObj.getJSONObject("project");

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
                        msg.what = 3;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }).start();

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


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
			StatusBarUtils.setStatusBarColor(xmjd_list.this, R.color.btground);
		}
	}
}
