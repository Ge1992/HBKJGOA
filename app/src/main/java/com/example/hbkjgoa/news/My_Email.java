package com.example.hbkjgoa.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.Email.AddEmail2;
import com.example.hbkjgoa.Email.XMJL_Email;
import com.example.hbkjgoa.Email.XMJL_SJX;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class My_Email extends Activity {
    private RelativeLayout R1, R2, R3, R4, R5;
    private TextView t2;
    private String yjs,json,spname,Department;
    RelativeLayout tile1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(json!=null&&!json.equals("")){
                    String[] a=json.split("\\|");
                   	t2.setText(a[4]);
                }
            }
        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmjl);
        spname =getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        Department =getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("Department", "");
        FindView();
        SetClic();
        setInfo();
        initStatusBar();
    }





    private void FindView() {
        R1 = (RelativeLayout)  findViewById(R.id.Rx1);
        R2 = (RelativeLayout)  findViewById(R.id.Rx2);
        R3 = (RelativeLayout)  findViewById(R.id.Rx3);
        R4 = (RelativeLayout)  findViewById(R.id.Rx4);
        R5 = (RelativeLayout)  findViewById(R.id.Rx5);
        t2=(TextView)  findViewById(R.id.t2);
        tile1=(RelativeLayout)   findViewById(R.id.title1);
    }

    private void SetClic() {
        //写邮件
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass( My_Email.this, AddEmail2.class);
                intent.putExtra("name", "1");
                intent.putExtra("name1", "");
                intent.putExtra("name2","");
                startActivity(intent);
            }
        });
        //收件箱
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass( My_Email.this, XMJL_SJX.class);
                //	intent.putExtra("name", "收件箱");
                startActivity(intent);
            }
        });
        //草稿箱
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass( My_Email.this, XMJL_Email.class);
                intent.putExtra("name", "草稿箱");
                startActivity(intent);
            }
        });
        //已删除
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass( My_Email.this, XMJL_Email.class);
                intent.putExtra("name", "已删除");
                startActivity(intent);
            }
        });
        //已发送
        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass( My_Email.this, XMJL_Email.class);
                intent.putExtra("name", "已发送");
                startActivity(intent);
            }
        });
    }



    public void setInfo() {
        boolean havenet = NetHelper.IsHaveInternet( My_Email.this);
        if (havenet) {
            new Thread() {
                @Override
                public void run() {
                    json= WebServiceUtil.everycanforStr("username","","","", spname,"", "", 0,"GetDBCount");
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            }.start();
        }else {
            Toast.makeText( My_Email.this.getApplicationContext(), "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
        }
    }


    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(My_Email.this, R.color.btground);
        }
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
}