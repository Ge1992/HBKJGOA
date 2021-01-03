package com.example.hbkjgoa.dbsx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hbkjgoa.FirstFragment;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.SY_New;
import com.example.hbkjgoa.adapter.WFListViewAdapter;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.news.SY_News;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xlistview.XListView;
import com.example.hbkjgoa.xlistview.XListViewFooter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WorkFlowActivity extends Activity implements XListView.IXListViewListener, OnItemClickListener {
    private EditText editText1;
    private TextView Text1, Text2;
    private WFListViewAdapter listViewAdapter;
    private List<NWorkToDo> list = new ArrayList<NWorkToDo>();
    private String json, nowtitle;
    private int EmailPageNo = 1;
    private String spname, show,type="";
    private int ID = 0;
    private boolean havesou = false;
    public static WorkFlowActivity mm;
    LoadingDialog dialog1;
    private XListView mListView;
    private XListViewFooter footer;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");

    public void closehere() {
        finish();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == 1) {
                dialog1.dismiss();
                setData(list);
            } else if (msg.what == 2) {
                list.clear();
                list = (List<NWorkToDo>) msg.obj;
                listViewAdapter.setmes((List<NWorkToDo>) msg.obj);
                mListView.stopRefresh();
                listViewAdapter.notifyDataSetChanged();
                mListView.setRefreshTime("更新于：" + dateFormat.format(new Date(System.currentTimeMillis())));
            } else if (msg.what == 3) {
                listViewAdapter.setmes(list);
                listViewAdapter.notifyDataSetChanged();
                mListView.stopLoadMore();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab_workflow_db);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mm = this;
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        nowtitle = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("nowtitle", "");
        mListView = (XListView) findViewById(R.id.XKListView);// 你这个listview是在这个layout里面
        mListView.setAdapter(listViewAdapter);
        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mListView.setOnItemClickListener(this);
        mListView.setCacheColorHint(0x00000000);
        mListView.setDivider(getResources().getDrawable(R.color.divider_color));
        mListView.setDividerHeight(1);
        findview();
        getListItems();


        setTextWatch();
        initStatusBar();
    }

    private void findview(){
        type = this.getIntent().getStringExtra("type");
        Text1 = (TextView) findViewById(R.id.Text1);
        Text2 = (TextView) findViewById(R.id.Text2);
        ID = getIntent().getIntExtra("ID", 0);
        show = getIntent().getStringExtra("show");
        editText1 = (EditText) findViewById(R.id.editText1);

        Text1.setText("欢迎你，" + spname);
        Text2.setText(nowtitle);

    }

    private void setData(List<NWorkToDo> listitem) {
        listViewAdapter = new WFListViewAdapter(this, listitem);
        mListView.setAdapter(listViewAdapter);
    }

    private void setTextWatch() {
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                havesou = true;
                Log.d("yin", "编辑" + editText1.getText().toString());
                String sou = editText1.getText().toString();
                list.clear();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getWorkname().toString().contains(sou)) {
                            list.add(list.get(i));
                        }
                    }
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = list;
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
        EmailPageNo = 1;
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<NWorkToDo> list = new ArrayList<NWorkToDo>();
                json = WebServiceUtil.everycanforStr2(
                        "iPageNo",
                        "uname",
                        "type",
                        "",
                        "",
                        "depid",
                        "" + EmailPageNo + "",
                        "" + spname + "",
                        type,
                        "",
                        "",
                        ID,
                        "GetWork");

                if (json != null && !json.equals("0")) {
                    JSONArray jsonObjs;
                    JSONObject jsonObj;
                    JSONTokener jsonTokener = new JSONTokener(json);
                    try {

                        jsonObjs = (JSONArray) jsonTokener.nextValue();
                        for (int i = 0; i < jsonObjs.length(); i++) {
                            jsonObj = (JSONObject) jsonObjs.opt(i);
                            NWorkToDo email = new NWorkToDo();
                            email.setID(jsonObj.getInt("ID"));
                            email.setWorkname(jsonObj.getString("WorkName"));
                            // 图片资源 //物品标题
                            email.setFromContentString(jsonObj.getString("FormContent"));
                            email.setTimestr(jsonObj.getString("TimeStr"));
                            email.setFujianString(jsonObj.getString("FuJianList"));
                            email.setUsernameString(jsonObj.getString("UserName"));
                            email.setFormid(jsonObj.getInt("FormID"));
                            email.setFlowid(jsonObj.getInt("WorkFlowID"));
                            email.setJiedianid(jsonObj.getInt("JieDianID"));
                            email.setJiedianNameString(jsonObj.getString("JieDianName"));
                            email.setShenpiyjString(jsonObj.getString("ShenPiYiJian"));
                            email.setShenpiUserString(jsonObj.getString("ShenPiUserList"));
                            email.setOkuserlist(jsonObj.getString("OKUserList"));
                            email.setStateNowString(jsonObj.getString("StateNow"));
                            email.setWenhaoString(jsonObj.getString("WenHao"));
                            email.setLatetimeString(jsonObj.getString("LateTime"));
                            email.setBeiyong1(jsonObj.getString("BeiYong1"));
                            email.setBeiyong2(jsonObj.getString("BeiYong2"));
                            email.setSSGS(jsonObj.getString("SSGS"));
                            email.setPSMS(jsonObj.getString("PSMS"));
                            email.setCS(jsonObj.getString("CS"));
                            email.setBeiYong1(jsonObj.getString("BeiYong1"));
                            JSONArray nodArray = jsonObj.getJSONArray("NodeItem");
                            List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
                            for (int j = 0; j < nodArray.length(); j++) {
                                JSONObject nodejsonObj = (JSONObject) nodArray
                                        .opt(j);
                                WorkFlowItem wf = new WorkFlowItem();
                                wf.setValueString(nodejsonObj
                                        .getString("Values"));
                                wf.setTextString(nodejsonObj
                                        .getString("Texts"));
                                wf.setTijaoString(nodejsonObj
                                        .getString("Tiaojian"));
                                wf.setSpmsString(nodejsonObj
                                        .getString("Spms"));
                                wf.setPsmsString(nodejsonObj
                                        .getString("Psms"));
                                wf.setMrsprString(nodejsonObj
                                        .getString("Mrspr"));
                                nodeItems.add(wf);

                            }
                            email.setNodeList(nodeItems);
                            list.add(email);
                        }
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = list;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }


    private void getListItems() {

        boolean havenet = NetHelper.IsHaveInternet(WorkFlowActivity.this);
        if (havenet) {
            LoadingDialog.Builder builder1 = new LoadingDialog.Builder(WorkFlowActivity.this)
                    .setMessage("加载中...")
                    .setCancelable(false);
            dialog1 = builder1.create();
            dialog1.show();
            new Thread() {
                @Override
                public void run() {

                    json = WebServiceUtil.everycanforStr2(
                            "iPageNo",
                            "uname",
                            "type",
                            "",
                            "",
                            "depid",
                            "" + EmailPageNo + "",
                            "" + spname + "",
                            type,
                            "",
                            "",
                            ID,
                            "GetWork");
                    if (json != null && !json.equals("0")) {
                        JSONArray jsonObjs;
                        JSONObject jsonObj;
                        JSONTokener jsonTokener = new JSONTokener(json);
                        try {

                            jsonObjs = (JSONArray) jsonTokener.nextValue();
                            for (int i = 0; i < jsonObjs.length(); i++) {
                                jsonObj = (JSONObject) jsonObjs.opt(i);
                                NWorkToDo email = new NWorkToDo();
                                email.setID(jsonObj.getInt("ID"));
                                email.setWorkname(jsonObj.getString("WorkName"));
                                email.setFromContentString(jsonObj.getString("FormContent"));
                                email.setTimestr(jsonObj.getString("TimeStr"));
                                email.setFujianString(jsonObj.getString("FuJianList"));
                                email.setUsernameString(jsonObj.getString("UserName"));
                                email.setFormid(jsonObj.getInt("FormID"));
                                email.setFlowid(jsonObj.getInt("WorkFlowID"));
                                email.setJiedianid(jsonObj.getInt("JieDianID"));
                                email.setJiedianNameString(jsonObj.getString("JieDianName"));
                                email.setShenpiyjString(jsonObj.getString("ShenPiYiJian"));
                                email.setShenpiUserString(jsonObj.getString("ShenPiUserList"));
                                email.setOkuserlist(jsonObj.getString("OKUserList"));
                                email.setStateNowString(jsonObj.getString("StateNow"));
                                email.setWenhaoString(jsonObj.getString("WenHao"));
                                email.setLatetimeString(jsonObj.getString("LateTime"));
                                email.setBeiyong1(jsonObj.getString("BeiYong1"));
                                email.setBeiyong2(jsonObj.getString("BeiYong2"));
                                email.setSSGS(jsonObj.getString("SSGS"));
                                email.setPSMS(jsonObj.getString("PSMS"));
                                email.setCS(jsonObj.getString("CS"));
                                email.setBeiYong1(jsonObj.getString("BeiYong1"));
                                JSONArray nodArray = jsonObj.getJSONArray("NodeItem");
                                List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
                                for (int j = 0; j < nodArray.length(); j++) {
                                    JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
                                    WorkFlowItem wf = new WorkFlowItem();
                                    wf.setValueString(nodejsonObj.getString("Values"));
                                    wf.setTextString(nodejsonObj.getString("Texts"));
                                    wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
                                    wf.setSpmsString(nodejsonObj.getString("Spms"));
                                    wf.setPsmsString(nodejsonObj.getString("Psms"));
                                    wf.setMrsprString(nodejsonObj.getString("Mrspr"));
                                    nodeItems.add(wf);

                                }
                                email.setNodeList(nodeItems);
                                list.add(email);
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                        }

                    }

                }
            }.start();

        } else {
            Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onLoadMore() {

        ++EmailPageNo;
        Log.d("yin", "EmailPageNo：" + EmailPageNo);
        new Thread(new Runnable() {

            @Override
            public void run() {
                json = WebServiceUtil.everycanforStr2(
                        "iPageNo",
                        "uname",
                        "type",
                        "",
                        "",
                        "depid",
                        "" + EmailPageNo + "",
                        "" + spname + "",
                        type,
                        "",
                        "",
                        ID,
                        "GetWork");

                if (json != null && !json.equals("0")) {
                    JSONArray jsonObjs;
                    JSONObject jsonObj;
                    JSONTokener jsonTokener = new JSONTokener(json);
                    try {

                        jsonObjs = (JSONArray) jsonTokener.nextValue();
                        for (int i = 0; i < jsonObjs.length(); i++) {
                            jsonObj = (JSONObject) jsonObjs.opt(i);
                            NWorkToDo email = new NWorkToDo();
                            email.setID(jsonObj.getInt("ID"));
                            email.setWorkname(jsonObj.getString("WorkName"));
                            // 图片资源 //物品标题
                            email.setFromContentString(jsonObj
                                    .getString("FormContent"));
                            email.setTimestr(jsonObj.getString("TimeStr"));


                            email.setFujianString(jsonObj
                                    .getString("FuJianList"));
                            email.setUsernameString(jsonObj
                                    .getString("UserName"));
                            email.setFormid(jsonObj.getInt("FormID"));
                            email.setFlowid(jsonObj.getInt("WorkFlowID"));
                            email.setJiedianid(jsonObj.getInt("JieDianID"));
                            email.setJiedianNameString(jsonObj
                                    .getString("JieDianName"));
                            email.setShenpiyjString(jsonObj
                                    .getString("ShenPiYiJian"));
                            email.setShenpiUserString(jsonObj
                                    .getString("ShenPiUserList"));
                            email.setOkuserlist(jsonObj.getString("OKUserList"));
                            email.setStateNowString(jsonObj
                                    .getString("StateNow"));
                            email.setWenhaoString(jsonObj.getString("WenHao"));
                            email.setLatetimeString(jsonObj
                                    .getString("LateTime"));
                            email.setBeiyong1(jsonObj.getString("BeiYong1"));
                            email.setBeiyong2(jsonObj.getString("BeiYong2"));
                            email.setSSGS(jsonObj.getString("SSGS"));
                            email.setPSMS(jsonObj.getString("PSMS"));
                            email.setCS(jsonObj.getString("CS"));
                            email.setBeiYong1(jsonObj.getString("BeiYong1"));
                            JSONArray nodArray = jsonObj
                                    .getJSONArray("NodeItem");
                            List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
                            for (int j = 0; j < nodArray.length(); j++) {
                                {
                                    JSONObject nodejsonObj = (JSONObject) nodArray
                                            .opt(j);
                                    WorkFlowItem wf = new WorkFlowItem();
                                    wf.setValueString(nodejsonObj
                                            .getString("Values"));
                                    wf.setTextString(nodejsonObj
                                            .getString("Texts"));
                                    wf.setTijaoString(nodejsonObj
                                            .getString("Tiaojian"));
                                    wf.setSpmsString(nodejsonObj
                                            .getString("Spms"));
                                    wf.setPsmsString(nodejsonObj
                                            .getString("Psms"));
                                    wf.setMrsprString(nodejsonObj
                                            .getString("Mrspr"));
                                    nodeItems.add(wf);

                                }
                            }
                            email.setNodeList(nodeItems);
                            list.add(email);

                        }
                        Message msg = new Message();
                        msg.what = 3;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();


    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        NWorkToDo email = new NWorkToDo();
        email = list.get(arg2 - 1);
        Intent intent = new Intent();
        intent.setClass(WorkFlowActivity.this, GOGAO_DB.class);
        intent.putExtra("bt", "待办事项");
        intent.putExtra("nowtitle", nowtitle);
        Bundle bundle = new Bundle();
        bundle.putSerializable("NWorkToDo", email);
        bundle.putString("show", show);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    public void btn_ref(View v) {

        onRefresh();
    }



    public void bt_back(View v){
        if(SY_News.instance!=null){
            SY_News.instance.setInfo2();
        }
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
            if(SY_News.instance!=null){
                SY_News.instance.setInfo2();
            }
            finish();
        }
        return false;
    }
    public void bt_back2(View v) {
        if(SY_News.instance!=null){
            SY_News.instance.setInfo2();
        }
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
            StatusBarUtils.setStatusBarColor(WorkFlowActivity.this, R.color.btground);
        }
    }

}
