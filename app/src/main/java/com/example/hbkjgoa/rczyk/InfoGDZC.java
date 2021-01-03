package com.example.hbkjgoa.rczyk;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.sqsp.InfoWorkFlow_xj;
import com.example.hbkjgoa.util.StatusBarUtils;


public class InfoGDZC extends Activity {
    private GDZC_Bean ap;
     TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14;
     Button serch,b1,b2,b3;
    public static InfoGDZC listact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_gdzc);
        listact = this;
        Intent intent = this.getIntent();
        ap=(GDZC_Bean)intent.getSerializableExtra("GDZC");
        initStatusBar();
        findview();
    }
    private  void findview(){
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



        b1.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InfoGDZC.this, WorkFlowShebei.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("GDZC", ap);
                bundle.putInt("ID", 0);
                bundle.putString("show", "no");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "78");
                bundle.putString("wid", "71");
                bundle.putString("next", "yes");
                Intent intent = new Intent();
                intent.setClass(InfoGDZC.this, InfoWorkFlow_xq.class);
                intent.putExtras(bundle);
                intent.putExtra("tile", "设备维修");
                bundle.putSerializable("GDZC", ap);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                intent.setClass(InfoGDZC.this, AddEmail_XXJC.class);
                intent.putExtras(bundle);
                bundle.putSerializable("GDZC", ap);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id","71");
                bundle.putString("wid","62");
                bundle.putString("next","yes");
                //   bundle.putString("value",jj.get(m).getValues());
                Intent intent = new Intent();
                intent.setClass(InfoGDZC.this, InfoWorkFlow_xj.class);
                intent.putExtra("tile", "耗材领取");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
            StatusBarUtils.setStatusBarColor(InfoGDZC.this, R.color.btground);
        }
    }
}
