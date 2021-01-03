package com.example.hbkjgoa.rczyk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.SJ_Bean;
import com.example.hbkjgoa.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;


public class InfoRCXX2 extends Activity {
    private SJ_Bean ap;
     TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
     Button bj;
    private LinearLayout mainLinerLayout,relativelayout;
    public static InfoRCXX2 listact;
    private List<String> listbiao = new ArrayList<String>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == 1) {
                initData();
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bydjxx);
        listact = this;
        Intent intent = this.getIntent();
        ap=(SJ_Bean)intent.getSerializableExtra("HD");
        initStatusBar();
        getinfo();
    }

    public void bt_back(View v){


        finish();
    }




    private void getinfo(){
        new Thread() {
            @Override
            public void run() {

                if(ap.getZiXin().equals("1")||ap.getZiXin().equals("2")){
                    listbiao.add(ap.getNameStr());
                    listbiao.add(ap.getRuHuiDate());
                    listbiao.add(ap.getSexStr());
                    listbiao.add(ap.getJiGuan());
                    listbiao.add(ap.getChuShengDate());
                    listbiao.add(ap.getXueLi());
                    listbiao.add(ap.getZiLi());
                    listbiao.add(ap.getGeXing());
                    listbiao.add(ap.getAiHao());
                    listbiao.add(ap.getMobTel());
                    listbiao.add(ap.getBackInfo());
                    listbiao.add(ap.getUserName());
                    listbiao.add(ap.getTimeStr());

                }else if (ap.getZiXin().equals("3")){
                    listbiao.add(ap.getNameStr());
                    listbiao.add(ap.getAiHao());
                    listbiao.add(ap.getChuShengDate());
                    listbiao.add(ap.getJiGuan());
                    listbiao.add(ap.getGeXing());
                    listbiao.add(ap.getRuHuiDate());
                    listbiao.add(ap.getXueLi());
                    listbiao.add(ap.getZiLi());
                    listbiao.add(ap.getChangYong());
                    listbiao.add(ap.getUserName());
                    listbiao.add(ap.getTimeStr());

                }else if (ap.getZiXin().equals("4")){
                    listbiao.add(ap.getNameStr());
                    listbiao.add(ap.getAiHao());
                    listbiao.add(ap.getGeXing());
                    listbiao.add(ap.getSexStr());
                    listbiao.add(ap.getChuShengDate());
                    listbiao.add(ap.getZiLi());
                    listbiao.add(ap.getMobTel());
                    listbiao.add(ap.getTel());
                    listbiao.add(ap.getChangYong());
                    listbiao.add(ap.getJieLun());
                    listbiao.add(ap.getBackInfo());
                    listbiao.add(ap.getUserName());
                    listbiao.add(ap.getTimeStr());
                }else {
                    listbiao.add(ap.getNameStr());
                    listbiao.add(ap.getRuHuiDate());
                    listbiao.add(ap.getSexStr());
                    listbiao.add(ap.getJiGuan());
                    listbiao.add(ap.getGeXing());
                    listbiao.add(ap.getZiLi());
                    listbiao.add(ap.getChuShengDate());
                  //  listbiao.add(ap.getAiHao());
                 //   listbiao.add(ap.getAddress());
                    listbiao.add(ap.getTel());
                 //   listbiao.add(ap.getMobTel());
                 //   listbiao.add(ap.getZuiJiaTime());
                    listbiao.add(ap.getBackInfo());
                  //  listbiao.add(ap.getUserName());
                 //   listbiao.add(ap.getTimeStr());

                }


                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }


        }.start();
    }

    private void initData() {
        String[] areas;
        if(ap.getZiXin().equals("1")||ap.getZiXin().equals("2"))
            areas = getResources().getStringArray(R.array.tysj);
        else if(ap.getZiXin().equals("3"))
            areas = getResources().getStringArray(R.array.sjggtd);
        else if(ap.getZiXin().equals("4"))
            areas = getResources().getStringArray(R.array.sjs);
        else
            areas = getResources().getStringArray(R.array.zjks);

        mainLinerLayout=(LinearLayout)findViewById(R.id.MyTable);
        for(int i=0;i<areas.length;i++){
            relativelayout=(LinearLayout) LayoutInflater.from(InfoRCXX2.this).inflate(R.layout.tablex, null);
            TextView txt=(TextView) relativelayout.findViewById(R.id.list_1);
            txt.setText(areas[i]);
            TextView txt2=(TextView) relativelayout.findViewById(R.id.list_2);
            txt2.setText(listbiao.get(i));

            final int x=i;
            if (txt.getText().toString().equals("手机")||txt.getText().toString().equals("联系电话")){
                //  txt2.setTextColor(Color.parseColor("#419DE4"));
                txt2 .getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );//下划线
                txt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + listbiao.get(x));
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
            }
            mainLinerLayout.addView(relativelayout);
        }
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
            StatusBarUtils.setStatusBarColor(InfoRCXX2.this, R.color.btground);
        }
    }
}
