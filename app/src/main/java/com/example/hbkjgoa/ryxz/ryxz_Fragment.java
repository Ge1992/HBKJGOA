package com.example.hbkjgoa.ryxz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.StatusBarUtils;

public class ryxz_Fragment extends FragmentActivity {
    TextView L1,L2;
    private SharedPreferences sharedPreferences;
    Fragment fragment1;
    Fragment fragment2;
    private int pageno = 1;
    String json,spname,searchstr="";
    Button serch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ryxz);
        sharedPreferences = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        initStatusBar();
        findview();
        setOnclick();
        initFragment(0);
    }

    private  void  findview(){
        L1=findViewById(R.id.text1);
        L2=findViewById(R.id.text2);
        serch=findViewById(R.id.serch);
        //serch.setVisibility(View.VISIBLE);
        serch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View arg0) {

                final EditText inputServer = new EditText(ryxz_Fragment.this);

                new AlertDialog.Builder(ryxz_Fragment.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("输入人员姓名")//使用默认设备 浅色主题
                        .setView(inputServer)
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface arg0) {
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchstr=inputServer.getText().toString();

                        if (ryxz_f1.listact != null) {
                            ryxz_f1.listact.getListItems(searchstr);
                        }




                        dialog.dismiss();
                    }

                }).show();
            }

        });
    }



    private void setOnclick() {

        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L1.setTextColor(Color.rgb(255, 0, 0));
                L2.setTextColor(Color.rgb(0, 0, 0));
                initFragment(0);
                serch.setVisibility(View.VISIBLE);
            }
        });
        L2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                L2.setTextColor(Color.rgb(255, 0, 0));
                L1.setTextColor(Color.rgb(0, 0, 0));

                initFragment(1);
                serch.setVisibility(View.VISIBLE);
            }
        });


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
            StatusBarUtils.setStatusBarColor(ryxz_Fragment.this, R.color.btground);
        }
    }




    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new BM();
                    Bundle bundle = new Bundle();
                    fragment1.setArguments(bundle);
                    transaction.add(R.id.ZTQK_content, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = new ryxz_f1();
                    Bundle bundle = new Bundle();
                    fragment2.setArguments(bundle);
                    transaction.add(R.id.ZTQK_content, fragment2);
                } else {
                    transaction.show(fragment2);
                }

                break;

            default:
                break;
        }
        // 提交事务
        transaction.commit();
    } // 隐藏Fragment

    private void hideFragment(FragmentTransaction transaction) {
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }


    }

    public void bt_back(View v){
        finish();
    }


}
