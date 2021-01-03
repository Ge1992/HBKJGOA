package com.example.hbkjgoa.tzgg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.BMAdapter2;
import com.example.hbkjgoa.model.TXL_New;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class bm_list extends Activity implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    private String wz;
    private String str;
    private String spname = "", pageindex = "", wantto = "", proName = "", prodq = "", yearnd = "", proProfession = "",  proProfessionchild = "";
    private String strseq = "";
    private String strseq2 = "";
    private String spword, ulx;
    private SharedPreferences sharedPreferences;
    private AlertDialog dlg;
    private String json,json2, searchstr = "";
    private BMAdapter2 adapter;
    private int fid = 0;
    private int step = 1;
    private String pid3 = "0";
    private boolean webbing = false;
    private List<TXL_New> list = new ArrayList<TXL_New>();
    private ListView XKGListView;
    private Button login_reback_btn, serch,save,all;
    private ImageView addsb;
    private Boolean iswebbing = false;
    private int page = 1;
    public static bm_list listact;
    private XListView mListView;
    private XListViewFooter footer;
    private String methodString = "";
    private TextView title, zs;
    private Animation loadAnimation;
    RelativeLayout R1,bootn;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
    LoadingDialog dialog1;
    private boolean havesou=false;
    List<Integer> listItemID = new ArrayList<Integer>();
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
                list = (List<TXL_New>) msg.obj;
                adapter.setmes((List<TXL_New>) msg.obj);
                mListView.stopRefresh();
                adapter.notifyDataSetChanged();
                mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
                TextView tx = (TextView)  findViewById(R.id.xlistview_footer_hint_textview);
                tx.setVisibility(View.VISIBLE);
            } else if (msg.what == 3) {
                if (strseq2 != "")
                    strseq = strseq2;
                adapter.setmes(list);
                adapter.notifyDataSetChanged();
                mListView.stopLoadMore();
                TextView tx = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
                //	zs.setText("总数:" + record + "个");
                tx.setVisibility(View.VISIBLE);
            }
        }
    };

    private void setData(List<TXL_New> listitem) {
        adapter = new BMAdapter2(bm_list.this, listitem);
        mListView.setAdapter(adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlx);
        sharedPreferences = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        spword = sharedPreferences.getString("spword", "");
        listact = this;
        mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setOnItemClickListener(this);
        finview();
        getInfo();
        initStatusBar();
    }

    private void finview(){
        ulx = sharedPreferences.getString("ulx", "");
        methodString = getIntent().getStringExtra("method");
        zs = (TextView) findViewById(R.id.zs);
        save = (Button) findViewById(R.id.save);
        all= (Button) findViewById(R.id.all);
        title= (TextView) findViewById(R.id.t1);
        R1= (RelativeLayout) findViewById(R.id.R1);
        bootn= (RelativeLayout) findViewById(R.id.bootn);
        title.setText("选择部门");
        R1.setVisibility(View.VISIBLE);
        bootn.setVisibility(View.VISIBLE);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<Integer, Boolean> isCheck = adapter.getMap();
                if (all.getText().equals("全选")) {
                    adapter.initCheck(true);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                    all.setText("取消全选");
                } else if (all.getText().equals("取消全选")) {
                    adapter.initCheck(false);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                    all.setText("全选");
                }

              }
        });


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listItemID.clear();
                for (int i = 0; i < adapter.isCheck.size(); i++) {
                    if (adapter.isCheck.get(i)) {
                        listItemID.add(i);
                    }
                }

                if (listItemID.size() == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(bm_list.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder1.setMessage("没有选中任何记录");
                    builder1.show();

                } else {
                    String sb = "";
                    if(havesou){
                        for (int i = 0; i < listItemID.size(); i++) {
                            sb += list.get(listItemID.get(i)).getMC()+",";
                        }
                    }else{
                        for (int i = 0; i < listItemID.size(); i++) {
                            sb += list.get(listItemID.get(i)) .getMC()+",";
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
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }


    private void getInfo() {


        boolean havenet = NetHelper.IsHaveInternet(bm_list.this);
        if (havenet) {
            LoadingDialog.Builder builder1=new LoadingDialog.Builder(bm_list.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1=builder1.create();
            dialog1.show();
            new Thread() {
                @Override
                public void run() {
                    JSONObject jsonObj2;
                    JSONArray jsonObjs2 = null;
                    json = WebServiceUtil.everycanforStr2("userid", "", "", "", "", "pid",
                            spname, "", "", "", "",0, "GetFenZu");

                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        try {

                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                json2 = jsonObj.getString("childlist");
                            }
                            if (json2 != null) {
                                JSONTokener jsonTokener2 = new JSONTokener(json2);
                                try {
                                    jsonObjs = (JSONArray) jsonTokener2.nextValue();
                                    list = new ArrayList<TXL_New>();
                                    for (int j = 0; j < jsonObjs.length(); j++) {
                                        TXL_New ap = new TXL_New();
                                        jsonObj = (JSONObject) jsonObjs.opt(j);

                                        ap.setMC(jsonObj.getString("MC"));
                                        ap.setId(jsonObj.getString("id"));
                                        list.add(ap);
                                        Log.d("hahahahh", ap.getMC());
                                    }
                                    Message msg = new Message();
                                    msg.what = 1;
                                    handler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                        }

                    }

                }
            }.start();

        } else {
            Toast.makeText(bm_list.this, "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

        }



    }




    @Override
    public void onRefresh() {

        Log.d("yuan", "刷新已经被执行到…………");
        page = 1;
        strseq = "";
        new Thread(new Runnable() {
		/*	@Override
			public void run() {
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
			}*/


            @Override
            public void run() {
                List<TXL_New> list = new ArrayList<TXL_New>();
                JSONObject jsonObj2;
                JSONArray jsonObjs2 = null;
                json = WebServiceUtil.everycanforStr2("userid", "", "", "", "", "pid",
                        spname, "", "", "", "",0, "GetFenZu");

                if (json != null && !json.equals("0")) {
                    JSONArray jsonObjs;
                    JSONObject jsonObj;
                    JSONTokener jsonTokener = new JSONTokener(json);
                    try {

                        jsonObjs = (JSONArray) jsonTokener.nextValue();
                        for (int i = 0; i < jsonObjs.length(); i++) {
                            jsonObj = (JSONObject) jsonObjs.opt(i);
                            json2 = jsonObj.getString("childlist");
                        }

                        if (json2 != null) {
                            JSONTokener jsonTokener2 = new JSONTokener(json2);
                            try {
                                jsonObjs = (JSONArray) jsonTokener2.nextValue();
                                list = new ArrayList<TXL_New>();
                                for (int j = 0; j < jsonObjs.length(); j++) {
                                    TXL_New ap = new TXL_New();
                                    jsonObj = (JSONObject) jsonObjs.opt(j);

                                    ap.setMC(jsonObj.getString("MC"));
                                    ap.setId(jsonObj.getString("id"));
                                    list.add(ap);
                                    Log.d("hahahahh", ap.getMC());
                                }
                                Message msg = new Message();
                                msg.what = 2;
                                msg.obj=list;
                                handler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                    }

                }

            }

        }).start();


    }


    public void bt_back(View v) {
      finish();
    }
    @Override
    public void onLoadMore() {

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
            StatusBarUtils.setStatusBarColor(bm_list.this, R.color.btground);
        }
    }
}