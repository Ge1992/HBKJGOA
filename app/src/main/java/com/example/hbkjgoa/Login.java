package com.example.hbkjgoa;

import java.io.File;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.hbkjgoa.news.SY_News;
import com.example.hbkjgoa.util.GetUUI;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.UtilStr;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.pm.PackageManager.NameNotFoundException;


@SuppressLint({ "WorldWriteableFiles", "WorldReadableFiles", "HandlerLeak" })
public class Login extends Activity {
    private EditText mUser,mPassword,login_passwd_edit2; // 帐号编辑框
    private CheckBox login_check,login_check2;
    private SharedPreferences sharedPreferences;
    private RelativeLayout Login_R;
    private String spname,spword,tologin,serverip,repassword,json,remark,GroupName,UserName;
    private int app_versionCode=-1;
    private String app_versionName = "",Department,JiaoSe,ZhiWei,userkey="",TrueName,UserPwd,json3;
    private File file01;
    private TextView main_verInfo;
    private Message message;
    private ProgressDialog progressDialog;
    private String name;
    private static String[] PERMISSIONS_CAMERA_AND_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1) {
                progressDialog.dismiss();



                if (json==null||json.equals("[]")) {
                    new android.app.AlertDialog.Builder(Login.this)
                            .setTitle("登录失败")
                            .setMessage("帐号或者密码不正确，\n请检查后重新输入！")
                            .create().show();
                }else {
                    JSONArray array;
                    try {
                        array = new JSONArray(json);
                        JSONObject singleBussiness = (JSONObject) array.opt(0);
                        Department=singleBussiness.getString("Department");
                        JiaoSe=singleBussiness.getString("JiaoSe");
                        ZhiWei=singleBussiness.getString("ZhiWei");
                        spname=singleBussiness.getString("UserName");
                        TrueName=singleBussiness.getString("TrueName");
                        UserPwd=singleBussiness.getString("UserPwd");
                        GroupName=singleBussiness.getString("GroupName");
                        UserName=singleBussiness.getString("UserName");
                    } catch (JSONException e) {

                    }

                    saveSharedPreferences();
                    Intent intent = new Intent();
                    intent.setClass(Login.this, SY_News.class);
                    JPushInterface.setAliasAndTags(getApplicationContext(),UserName,null,null);
                    startActivity(intent);
                    finish();

                }
            }else if (msg.what==3) {
                progressDialog.dismiss();
                if (file01!=null) {
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(file01);
                    intent.setDataAndType(uri,"application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }else if (msg.what==4) {
                progressDialog.dismiss();

            }
            else if (msg.what==5) {

                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);

            }else if (msg.what == 9) {

                if (json3 != null && !json3.equals("") && json3.contains("|")) {
                    String[] a = json3.split("\\|");

                    int allcount=Integer.parseInt(a[0]);

                    SetBadge(allcount);

                }
            }



        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        loadSharedPreferences();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login3);
        findview();
        getVerInfo();
        checkUpdate();
        PgyCrashManager.register();
        initStatusBar();
        if (Build.VERSION.SDK_INT >= 23) {
            int storagePermission = ActivityCompat.checkSelfPermission(Login.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.CAMERA);
            if (storagePermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Login.this, PERMISSIONS_CAMERA_AND_STORAGE,
                        2);
                return;
            }
        }

    }


    private void getVerInfo(){
        try {
            app_versionCode=this.getPackageManager().getPackageInfo(getResources().getString(R.string.package_name), 0).versionCode;
            app_versionName=this.getPackageManager().getPackageInfo(getResources().getString(R.string.package_name), 0).versionName;
            main_verInfo.setText("V:"+app_versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void findview(){
        mUser = (EditText)findViewById(R.id.login_user_edit);
        mPassword = (EditText)findViewById(R.id.login_passwd_edit);
        main_verInfo=(TextView)findViewById(R.id.main_verInfo);
        GetUUI getUUI=new GetUUI(Login.this);
        userkey=""+getUUI.getDeviceUuid();
    }

    public void login_mainweixin(View v) {
        name=mUser.getText().toString().trim();
        spword=mPassword.getText().toString();
        if (!spname.equals("")) {
            setInfo2();
        } else {

        }

        serverip="http://116.62.238.239:61645";
        if("".equals(name) || "".equals(spword)|| "".equals(serverip))  {
            new AlertDialog.Builder(Login.this)
                    .setTitle("登录错误")
                    .setMessage("用户名或密码不能为空")
                    .create().show();
        }else {
            UtilStr.url=serverip;
            progressDialog = ProgressDialog.show(Login.this, "正在登录中", "请稍等...");
            new Thread(){
                @Override
                public void run() {
                    json=WebServiceUtil.everycanforStr("uname","pass","clientid","",name,spword,userkey,0,"Login");
                    Log.d("yin","IsUser："+json);
                    message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }

            }.start();
        }
    }





    private void checkUpdate() {
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新
                .setUserCanRetry(true)         //失败后是否提示重新下载
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                .register();

    }

    @SuppressWarnings("deprecation")
    protected void saveSharedPreferences() {
        sharedPreferences=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("spword", spword);
        editor.putString("serverip", serverip);
        editor.putString("repassword", repassword);
        editor.putString("tologin", tologin);
        editor.putString("spname", spname);
        editor.putString("Department", Department);
        editor.putString("JiaoSe", JiaoSe);
        editor.putString("ZhiWei", ZhiWei);
        editor.putString("TrueName", TrueName);
        editor.putString("UserPwd", UserPwd);
        editor.putString("json", json);
        editor.putString("GroupName", GroupName);
        editor.putString("UserName", UserName);

        editor.commit();
        handler.sendMessage(handler.obtainMessage(5, spname));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";

                    handler.sendMessageDelayed(handler.obtainMessage(5, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };

    @SuppressWarnings("deprecation")
    private void loadSharedPreferences() {
        sharedPreferences=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        name=sharedPreferences.getString("name","");
        spword=sharedPreferences.getString("spword", "");
        serverip=sharedPreferences.getString("serverip","");
        repassword=sharedPreferences.getString("repassword", "");
        tologin=sharedPreferences.getString("tologin","");
        spname=sharedPreferences.getString("spname","");
        GroupName=sharedPreferences.getString("GroupName","");
        UserName=sharedPreferences.getString("UserName","");
        if(repassword.equals("0")){
            mUser.setText("");
            mPassword.setText("");
            //	login_passwd_edit2.setText("");
            //	login_check.setChecked(false);
        }else{
            mUser.setText(name);
            mPassword.setText(spword);
            //  		login_passwd_edit2.setText(serverip);
            // 		login_check.setChecked(true);
        }
        if(tologin.equals("1")){
            mUser.setText(name);
            mPassword.setText(spword);
            //	login_passwd_edit2.setText(serverip);
            // 		login_check.setChecked(true);
            //		login_check2.setChecked(false);
            login_mainweixin(new View(Login.this));
        }else if(tologin.equals("0")){
            //		login_check2.setChecked(false);
        }else{
            //	login_check.setChecked(true);
            //		login_check2.setChecked(false);
        }

        JPushInterface.setAliasAndTags(getApplicationContext(),UserName,null,null);
    }



    //JPush 极光
    protected void onResume(){
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        JPushInterface.onPause(this);
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
            StatusBarUtils.transparencyBar(Login.this);
        }
    }
    public void setInfo2() {

        new Thread() {
            public void run() {

                json3 = WebServiceUtil.everycanforStr2("username", "","", "", "", "",
                        spname,"", "","", "", 0,"GetDBCount");

                Message msg = new Message();
                msg.what = 9;
                handler.sendMessage(msg);

            }
        }.start();
    }
    public void SetBadge(int num){
        try{
            Bundle bunlde =new Bundle();
            bunlde.putString("package", "com.example.hbkjgoa"); // 包名
            bunlde.putString("class", "com.example.hbkjgoa.Login"); //类名
            bunlde.putInt("badgenumber",num);
            Bundle res= this.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        }catch(Exception e){
       //     Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
