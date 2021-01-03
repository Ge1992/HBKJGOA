package com.example.hbkjgoa.rczyk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;


public class InfoGDZC_SM extends Activity {
     TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14;
    Button serch,b1,b2,b3;
    private String date,json,spname,tile="";
    public static InfoGDZC_SM listact;
    private GDZC_Bean ap;
    LinearLayout L1;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                if(json.contains("暂无记录")){
                    Toast.makeText(getApplicationContext(), "暂无记录",
                            Toast.LENGTH_SHORT).show();
                    L1.setVisibility(View.GONE);


                }else {
                        ap=JSONObject.parseObject(json,GDZC_Bean.class);
                        t1.setText(ap.getYuanBianHao());
                        t2.setText(ap.getSheBeiLeiBie());
                        t3.setText(ap.getShengChanChangJia());
                        t4.setText(ap.getQiYongDate());
                        t5.setText(ap.getXingHao());
                        t6.setText(ap.getChuChangBianHao());
                        t7.setText(ap.getDanWei());
                        t8.setText(ap.getSuYuanFangShi());
                        t9.setText(ap.getSuYaunDanWei());
                        t10.setText(ap.getZhengShuBianHao());
                        t11.setText(ap.getShiYongBuMen());
                        t12.setText(ap.getGuanLiUser());
                        t13.setText(ap.getZhuangTai());
                        t14.setText(ap.getBeiZhu());

                    if (tile.equals("耗材领取")&& t2.getText().toString().contains("打印机")){
                        b1.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.GONE);
                        b1.setText("申请耗材");
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id","71");
                                bundle.putString("wid","62");
                                bundle.putString("next","yes");
                                //   bundle.putString("value",jj.get(m).getValues());
                                Intent intent = new Intent();
                                intent.setClass(InfoGDZC_SM.this, InfoWorkFlow_xq.class);
                                intent.putExtra("tile", "耗材领取");
                                bundle.putSerializable("GDZC", ap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        b3.setVisibility(View.VISIBLE);
                        b3.setText("信息纠错");
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent();
                                intent.setClass(InfoGDZC_SM.this, AddEmail_XXJC.class);
                                intent.putExtras(bundle);
                                bundle.putSerializable("GDZC", ap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                    }else {
                        b3.setVisibility(View.VISIBLE);
                        b3.setText("信息纠错");
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent();
                                intent.setClass(InfoGDZC_SM.this, AddEmail_XXJC.class);
                                intent.putExtras(bundle);
                                bundle.putSerializable("GDZC", ap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_gdzc);
        listact = this;
        date = this.getIntent().getStringExtra("date");
        tile = this.getIntent().getStringExtra("tile");
     //   Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
        initStatusBar();
        findview();
        setInfo2();
    }
    private  void findview(){
        L1=findViewById(R.id.L1);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        t5=findViewById(R.id.t5);
        t6=findViewById(R.id.t6);
        t7=findViewById(R.id.t7);
        t8=findViewById(R.id.t8);
        t9=findViewById(R.id.t9);
        t10=findViewById(R.id.t10);
        t11=findViewById(R.id.t11);
        t12=findViewById(R.id.t12);
        t13=findViewById(R.id.t13);
        t14=findViewById(R.id.t14);
        serch=findViewById(R.id.serch);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);


   if (tile.equals("设备维修")||tile.equals("")){
            b2.setVisibility(View.GONE);
            b1.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);
            b3.setText("信息纠错");
            b1.setText("设备维修");
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", "78");
                    bundle.putString("wid", "71");
                    bundle.putString("next", "yes");
                    Intent intent = new Intent();
                    intent.setClass(InfoGDZC_SM.this, InfoWorkFlow_xq.class);
                    intent.putExtras(bundle);
                    intent.putExtra("tile", "设备维修");
                    bundle.putSerializable("GDZC", ap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            //信息纠错
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    intent.setClass(InfoGDZC_SM.this, AddEmail_XXJC.class);
                    intent.putExtras(bundle);
                    bundle.putSerializable("GDZC", ap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        //记录
        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InfoGDZC_SM.this, WorkFlowShebei.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("GDZC", ap);
                bundle.putInt("ID", 0);
                bundle.putString("show", "no");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });





    }


    public void setInfo2() {
        new Thread() {
            @Override
            public void run() {
                json = WebServiceUtil.everycanforStr("bm", "", "", "", date, "", "", 0, "GetSheBeiDetail");
                Message message = new Message();
                message.what =1;
                handler.sendMessage(message);
            }
        }.start();
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
            StatusBarUtils.setStatusBarColor(InfoGDZC_SM.this, R.color.btground);
        }
    }
}
