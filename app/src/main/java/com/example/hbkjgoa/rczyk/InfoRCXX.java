package com.example.hbkjgoa.rczyk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.SJ_Bean;
import com.example.hbkjgoa.util.StatusBarUtils;


public class InfoRCXX extends Activity {
    private SJ_Bean ap;
     TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
     Button bj;
    public static InfoRCXX listact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_rcxx);
        listact = this;
        Intent intent = this.getIntent();
        ap=(SJ_Bean)intent.getSerializableExtra("HD");
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


        t1.setText(ap.getNameStr());
        t2.setText(ap.getRuHuiDate());
        t3.setText(ap.getSexStr());
        t4.setText(ap.getJiGuan());
        t5.setText(ap.getChuShengDate());
        t6.setText(ap.getXueLi());
        t7.setText(ap.getZiLi());
        t8.setText(ap.getGeXing());
        t9.setText(ap.getAiHao());
        t10.setText(ap.getMobTel());
        t11.setText(ap.getUserName());
        t12.setText(ap.getTimeStr());
        t13.setText(ap.getBackInfo());



        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" +ap.getMobTel());
                intent.setData(data);
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
            StatusBarUtils.setStatusBarColor(InfoRCXX.this, R.color.btground);
        }
    }
}
