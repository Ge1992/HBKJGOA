package com.example.hbkjgoa.txl;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.adapter.XMLXAdapter2;
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


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class SecondFragment extends Fragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    private String wz;
    private String str;
    private String spname = "", pageindex = "", wantto = "", proName = "", prodq = "", yearnd = "", proProfession = "",  proProfessionchild = "";
    private String strseq = "";
    private String strseq2 = "";
    private String spword, ulx;
    private SharedPreferences sharedPreferences;
    private AlertDialog dlg;
    private String json,json2, searchstr = "";
    private XMLXAdapter2 adapter;
    private int fid = 0;
    private int step = 1;
    private String pid3 = "0";
    private boolean webbing = false;
    private List<TXL_New> list = new ArrayList<TXL_New>();
    private ListView XKGListView;
    private Button login_reback_btn, serch;
    private ImageView addsb;
    private Boolean iswebbing = false;
    private int page = 1;
    public static SecondFragment listact;
    private XListView mListView;
    private XListViewFooter footer;
    private String methodString = "";
    private TextView title, zs;
    private Animation loadAnimation;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
    LoadingDialog dialog1;
    RelativeLayout R1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                dialog1.dismiss();
                TextView tx = (TextView)  getView().findViewById(R.id.xlistview_footer_hint_textview);
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
                TextView tx = (TextView)  getView().findViewById(R.id.xlistview_footer_hint_textview);
                tx.setVisibility(View.VISIBLE);
            } else if (msg.what == 3) {
                if (strseq2 != "")
                    strseq = strseq2;
                adapter.setmes(list);
                adapter.notifyDataSetChanged();
                mListView.stopLoadMore();
                TextView tx = (TextView)  getView().findViewById(R.id.xlistview_footer_hint_textview);
                //	zs.setText("总数:" + record + "个");
                tx.setVisibility(View.VISIBLE);
            }
        }
    };

    private void setData(List<TXL_New> listitem) {
        adapter = new XMLXAdapter2(getActivity(), listitem);
        mListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_xmlx, container, false);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        spname = getActivity().getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        spword = sharedPreferences.getString("spword", "");
        ulx = sharedPreferences.getString("ulx", "");
        listact = this;
        methodString =  getActivity().getIntent().getStringExtra("method");

        mListView = (XListView) getView().findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(false);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setOnItemClickListener(this);
        zs = (TextView) getView().findViewById(R.id.zs);
        serch = (Button)getView(). findViewById(R.id.serch);

        getInfo();
        initStatusBar();
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        TXL_New email = new TXL_New();
        email = list.get(arg2 - 1);
        Intent intent = new Intent();
        intent.setClass(getActivity(), ZCSL3.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("TXL_New", email);
        intent.putExtras(bundle);
        startActivity(intent);
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
            Toast.makeText(getActivity(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

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
        getActivity().finish();
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
            getActivity().  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(getActivity(), R.color.btground);
        }
    }
}