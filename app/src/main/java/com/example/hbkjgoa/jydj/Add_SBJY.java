package com.example.hbkjgoa.jydj;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

public class Add_SBJY extends Activity {
    public static Add_SBJY listact;
    private GDZC_Bean ap;
    private EditText e1;
    private Button add;
    private  String  json,spname,sbmc;
    private TextView t2,t4,t6,t8,t10,t12;
    LoadingDialog dialog1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (json.contains("1")){
                    dialog1.dismiss();
                    Toast.makeText(getApplicationContext(), "提交成功",
                            Toast.LENGTH_SHORT).show();

                    finish();

                    if (gdzc_list_add.listact!=null){
                        gdzc_list_add.listact.finish();
                    }

                    if (JY_Activity.mm!=null){
                        JY_Activity.mm.onRefresh();
                    }
                }

            }

        }

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qd_xj);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");

        listact = this;
        findview();
        initStatusBar();
    }

    private void findview() {
        Intent intent = this.getIntent();
        ap=(GDZC_Bean)intent.getSerializableExtra("GDZC");

        t2=findViewById(R.id.t2);
        t4=findViewById(R.id.t4);
        t6=findViewById(R.id.t6);
        t8=findViewById(R.id.t8);
        t10=findViewById(R.id.t10);
        t12=findViewById(R.id.t12);
        e1=findViewById(R.id.e2);
        add=findViewById(R.id.save);
        t2.setText(ap.getSheBeiName());
        t4.setText(ap.getYuanBianHao());
        t6.setText(ap.getShengChanChangJia());
        t8.setText(ap.getXingHao());
        t10.setText(ap.getShiYongBuMen());
        t12.setText(ap.getUserName());
        sbmc=ap.getSheBeiName()+"-"+ap.getYuanBianHao();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


    }


    private void save(){
                LoadingDialog.Builder builder1=new LoadingDialog.Builder(Add_SBJY.this)
                        .setMessage("加载中...")
                        .setCancelable(false);
                dialog1=builder1.create();
                dialog1.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        json = WebServiceUtil.everycanforStr2("SBMC", "SBID", "BZ", "userid","","",
                                sbmc,ap.getID(),e1.getText().toString(),spname,"",0,"SBJY");

                        Log.d("设备",json);
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }).start();

    }


    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(Add_SBJY.this, R.color.btground);
        }
    }
    public void bt_back(View v){


        finish();
    }
}
