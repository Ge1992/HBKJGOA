package com.example.hbkjgoa.rcgl;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.HD;
import com.example.hbkjgoa.util.StatusBarUtils;


public class InfoLDHD extends Activity {
    private HD ap;
     TextView t1,t2,t3,t4,t5;
     Button bj;
    public static InfoLDHD listact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ldhd);
        listact = this;
        Intent intent = this.getIntent();
        ap=(HD)intent.getSerializableExtra("HD");
        initStatusBar();
        findview();
    }
    private  void findview(){
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        t5=findViewById(R.id.t5);
        bj=findViewById(R.id.bj);
        t1.setText(ap.getUserName());
        t2.setText(ap.getTitleStr());
        t3.setText(ap.getTimeStart());
        t4.setText(ap.getTimeEnd());
        t5.setText(ap.getContentStr());
        bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                Intent intent = new Intent();
                intent.setClass(InfoLDHD.this,LDHD_BJ.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HD", ap);
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
            StatusBarUtils.setStatusBarColor(InfoLDHD.this, R.color.btground);
        }
    }
}
