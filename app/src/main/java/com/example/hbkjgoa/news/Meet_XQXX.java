package com.example.hbkjgoa.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Meet_Bean;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.NoScrollGridView;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.xmjd.ZQYZT_NEW;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class Meet_XQXX extends Activity {
    public static Meet_XQXX listact;
    private List<String> listbiao = new ArrayList<String>();
    private LinearLayout mainLinerLayout;
    private LinearLayout relativelayout;
    private String json1,uxm,spname;
    private Meet_Bean JDXX;
    private Button tj;
    private LinearLayout L1;
    public NoScrollGridView gridView;
    private String Pic;
    private Button fj;
    LoadingDialog dialog2;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dialog2.dismiss();
                initData();
            }
        }

    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meet_jbxx);
        listact = this;
        uxm= getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("uxm", "");
        spname= getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");


        findview();
        getinfo();
        initStatusBar();

    }

    private void findview() {

        Intent intent = this.getIntent();
        JDXX = (Meet_Bean) intent.getSerializableExtra("HD");
        fj=findViewById(R.id.fj);
        if (!JDXX.getZD1().equals("")){
            fj.setVisibility(View.VISIBLE);
        }else {
            fj.setVisibility(View.GONE);
        }
        fj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Meet_XQXX.this, Meet_Fj.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fj", JDXX);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getinfo() {
        LoadingDialog.Builder builder1=new LoadingDialog.Builder(Meet_XQXX.this)
                .setMessage("加载中...")
                .setCancelable(false);
        dialog2=builder1.create();
        dialog2.show();
        new Thread() {
            @Override
            public void run() {

                try {

                    listbiao.add(JDXX.getMeetingTitle());
                    listbiao.add(JDXX.getMeetingZhuTi());
                    listbiao.add(JDXX.getMiaoShu());
                    listbiao.add(JDXX.getChuXiRen());
                    listbiao.add(JDXX.getWangLuoHuiYiShiIP());
                    listbiao.add(JDXX.getHuiYiZhuChi());
                    listbiao.add(JDXX.getKaiShiTime());
                    listbiao.add(JDXX.getJieShuTime());
                    listbiao.add(JDXX.getHuiYiJiYao());
                    listbiao.add(JDXX.getZD2());
                    listbiao.add(JDXX.getSHR());
                    listbiao.add(JDXX.getSPSJ());
                    listbiao.add(JDXX.getSHZT());

                } catch (Exception e) {

                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);



            }


        }.start();

    }

    private void initData() {
        String[] areas;
            areas = getResources().getStringArray(R.array.meet_xq);
            mainLinerLayout = (LinearLayout) findViewById(R.id.MyTable);

        for (int i = 0; i < areas.length; i++) {
            relativelayout = (LinearLayout) LayoutInflater.from(Meet_XQXX.this).inflate(R.layout.tablex, null);
            TextView txt = (TextView) relativelayout.findViewById(R.id.list_1);
            txt.setText(areas[i]);
            TextView txt2 = (TextView) relativelayout.findViewById(R.id.list_2);
            txt2.setText(listbiao.get(i));
            mainLinerLayout.addView(relativelayout);
        }

    }


    public void bt_back(View v) {

        finish();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(Meet_XQXX.this, R.color.btground);
        }
    }


}
