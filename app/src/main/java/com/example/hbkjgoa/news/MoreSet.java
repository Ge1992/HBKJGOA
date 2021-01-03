package com.example.hbkjgoa.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.ChangePassword;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.yxtx.CircleBitmapDisplayer;
import com.example.hbkjgoa.yxtx.CircularImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class MoreSet extends Activity {
    private RelativeLayout R1,R2;
    private TextView text1,text2,text3,text4,text5,text6;
    private String json,phototime;
    private String uxm,spword,spname;
    private String udw;
    private String uxb;
    private String usr;
    private String udh;
    private String uzw;
    private String udwdz,ubmi,ZhiWei,Department,JiaoSe,TrueName,utx;
    private boolean iswebbing=false;
    private TextView t1;
    private AlertDialog dialog;
    public static MoreSet instance = null;
    RelativeLayout tile1;
    private static String path="/sdcard/myYCSSJ/";//sd路径
    private CircularImage cover_user_photo;
    Bitmap bitmap;
    private ImageView tx;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1) {
                iswebbing=false;
                if (json.equals("0")||json==null) {
                    new AlertDialog.Builder(MoreSet.this)
                            .setTitle("密码修改失败！").setPositiveButton("确定", null)
                            .create().show();
                }else {
                    SharedPreferences sharedPreferences=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("spword", spword);
                    editor.commit();

                    new AlertDialog.Builder(MoreSet.this)
                            .setTitle("密码修改成功！").setPositiveButton("确定", null)
                            .create().show();
                }
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreactivity2);
        instance = this;
        instance = this;
        json = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("json", "");
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        spword = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spword", "");
        Department = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("Department", "");
        ZhiWei = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("ZhiWei", "");
        TrueName = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("TrueName", "");
        utx = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("GroupName", "");
        findView();
        setClick();
        ImageLoader.getInstance().displayImage(WebServiceUtil.getURL2() + "uploadfile/" + utx, tx, options);
        Bitmap bt = BitmapFactory.decodeFile(path + "ycsj.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            cover_user_photo.setImageDrawable(drawable);
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD�?
             *
             */
        }
        initStatusBar();


    }
    //服务器获取图片圆形
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .displayer(new CircleBitmapDisplayer())
            .build();

    public void onRefresh(){
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().clearMemoryCache();
        //ImageLoader.getInstance().displayImage(getResources().getString(R.string.http_url)+uszp,more_image1);
    }

    private void setClick() {

        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MoreSet.this, ChangePassword.class);
                startActivity(intent);
            }
        });

    }
    private void findView (){

        //more_b1=(Button)findViewById(R.id.more_b1);
        tile1=(RelativeLayout)  findViewById(R.id.title);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);

        text5=(TextView)findViewById(R.id.text5);
        text6=(TextView)findViewById(R.id.text6);
        R1=(RelativeLayout)findViewById(R.id.R1);
        t1=(TextView) findViewById(R.id.t1);
        //more_image1=(ImageView)findViewById(R.id.more_image1);
        tx=(ImageView) findViewById(R.id.tx);
        cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        text1.setText(spname);
        text2.setText(Department);
        text3.setText(ZhiWei);
        text4.setText(udh);
        text5.setText(udwdz);
        t1.setText(TrueName);



    }
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(MoreSet.this, R.color.btground);
        }
    }

    public void bt_back(View v){

        finish();

    }

}