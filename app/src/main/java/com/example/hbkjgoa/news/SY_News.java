package com.example.hbkjgoa.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.SY_New;
import com.example.hbkjgoa.dbsx.WorkFlowActivity;
import com.example.hbkjgoa.display.ImageCycleView;
import com.example.hbkjgoa.tzgg.GOGAO_N;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.ybsx.WorkFlowDBActivity;
import com.lidroid.xutils.BitmapUtils;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class SY_News extends Activity {
    private boolean canbo = false;
    private LinearLayout L1, L2, L3, L4, L5, L6, L7, L8, L9;
    public static SY_News instance = null;
    private ImageCycleView mImageCycleView;
    boolean isExit;
    private  String json2,spname;
    private BadgeView badge, badge2, badge3, badge4, badge5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityn_sy);
        spname=getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE+ Context.MODE_PRIVATE).getString("spname","");

        instance = this;
        canbo = true;
        findview();
        setInfo2();
        initialize();
        initStatusBar();

    }

    private void findview() {

        L1 = findViewById(R.id.L1);
        L2 = findViewById(R.id.L2);
        L3 = findViewById(R.id.L3);
        L4 = findViewById(R.id.L4);
        L5 = findViewById(R.id.L5);
        L6 = findViewById(R.id.L6);
        L7 = findViewById(R.id.L7);
        L8 = findViewById(R.id.L8);
        L9 = findViewById(R.id.L9);


        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "我的待办");
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(SY_News.this, WorkFlowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "我的已办");
                editor.commit();
                Intent intent = new Intent(SY_News.this, WorkFlowDBActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", 0);
                bundle.putString("show", "no");
                bundle.putString("type", "1");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        L3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "我的待阅");
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(SY_News.this, WorkFlowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "2");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        L4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("sdlxLogin",  Context.MODE_PRIVATE + Context.MODE_PRIVATE).edit();
                editor.putString("nowtitle", "我的已办");
                editor.commit();
                Intent intent = new Intent(SY_News.this, WorkFlowDBActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", 0);
                bundle.putString("show", "no");
                bundle.putString("type", "2");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        L5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SY_News.this, MoreSet.class);
                startActivity(intent);

            }
        });


        L6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SY_News.this, Meet_list.class);
                startActivity(intent);

            }
        });
        L7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SY_News.this, GOGAO_N.class);
                intent.putExtra("LX", "通知公告");
                startActivity(intent);

            }
        });

        L8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SY_News.this, TXLActivity_n.class);
                startActivity(intent);

            }
        });




        L9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SY_News.this, My_Email.class);
                Bundle bundle = new Bundle();
                startActivity(intent);

            }
        });



    }


    /**
     * 轮播
     */
    private void initialize() {
        mImageCycleView = (ImageCycleView) findViewById(R.id.icv_topView);
        mImageCycleView.setAutoCycle(false); //关闭自动播放
//		mImageCycleView.setCycleDelayed(2000);//设置自动轮播循环时间
        mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.COLOR, Color.TRANSPARENT, Color.TRANSPARENT, 1f);
//		mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.IMAGE,R.drawable.dian_unfocus, R.drawable.dian_focus, 1f);
        List<ImageCycleView.ImageInfo> list = new ArrayList<ImageCycleView.ImageInfo>();

        //res图片资源
/*		list.add(new ImageCycleView.ImageInfo(R.drawable.lnbn,"1",""+1));
		list.add(new ImageCycleView.ImageInfo(R.drawable.lbn4,"1",""+2));
    	list.add(new ImageCycleView.ImageInfo(R.drawable.lbn3,"2",""+3));
    	list.add(new ImageCycleView.ImageInfo(R.drawable.lbn5,"3",""+4));*/

        //使用网络加载图片
        list.add(new ImageCycleView.ImageInfo("http://112.24.96.17:8019/banner.jpg", "1", "1"));


        mImageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
                if (imageInfo.value.equals("1")) {

                }
            }
        });


        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {

		/*
			//本地图片
				ImageView imageView=new ImageView(MainActivity_New.this);
				imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
				return imageView;
		*/


                //使用BitmapUtils,只能使用网络图片
                BitmapUtils bitmapUtils = new BitmapUtils(SY_News.this);
                ImageView imageView = new ImageView(SY_News.this);
                bitmapUtils.display(imageView, imageInfo.image.toString());
                return imageView;


            }
        });

    }
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(SY_News.this, R.color.btground);
        }
    }

    public void onDestroy() {
        canbo = false;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        canbo = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        canbo = true;
        super.onResume();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
    // 角标样式
    private void setbadgeView(BadgeView badge){
        badge.setTextSize(15);
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge.setBadgeMargin(70, 50);
        badge.setBadgeBackgroundColor(Color.parseColor("#FF0000"));
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(1000);
        badge.toggle(anim, null);
    }

    // 角标接口数据
    public void setInfo2() {
        new Thread() {
            @Override
            public void run() {
                json2 = WebServiceUtil.everycanforStr("username", "", "", "", spname, "", "", 0, "GetDBCount");
                Message message = new Message();
                message.what =7;
                handler.sendMessage(message);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 7) {
                if (json2 != null && !json2.equals("") && json2.contains("|")) {
                    String aa[]=json2.split("\\|");
                    if(!aa[0].equals("0")){
                        if(badge!=null){
                            badge.setText(aa[0]+"");
                            setbadgeView(badge);
                            badge.setVisibility(View.VISIBLE);
                        }else{
                            badge=new BadgeView(SY_News.this,L1);
                            badge.setText(aa[0]+"");
                            setbadgeView(badge);
                            badge.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(badge!=null){
                            badge.setVisibility(View.GONE);
                        }
                    }

                    if(!aa[1].equals("0")){
                        if(badge2!=null){
                            badge2.setText(aa[1]+"");
                            setbadgeView(badge2);
                            badge2.setVisibility(View.VISIBLE);
                        }else{
                            badge2=new BadgeView(SY_News.this,L3);
                            badge2.setText(aa[1]+"");
                            setbadgeView(badge2);
                            badge2.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(badge2!=null){
                            badge2.setVisibility(View.GONE);
                        }
                    }

                    if(!aa[2].equals("0")){
                        if(badge3!=null){
                            badge3.setText(aa[2]+"");
                            setbadgeView(badge3);
                            badge3.setVisibility(View.VISIBLE);
                        }else{
                            badge3=new BadgeView(SY_News.this,L6);
                            badge3.setText(aa[2]+"");
                            setbadgeView(badge3);
                            badge3.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(badge3!=null){
                            badge3.setVisibility(View.GONE);
                        }
                    }


                    if(!aa[3].equals("0")){
                        if(badge4!=null){
                            badge4.setText(aa[3]+"");
                            setbadgeView(badge4);
                            badge4.setVisibility(View.VISIBLE);
                        }else{
                            badge4=new BadgeView(SY_News.this,L7);
                            badge4.setText(aa[3]+"");
                            setbadgeView(badge4);
                            badge4.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(badge4!=null){
                            badge4.setVisibility(View.GONE);
                        }
                    }

                    if(!aa[4].equals("0")){
                        if(badge5!=null){
                            badge5.setText(aa[4]+"");
                            setbadgeView(badge5);
                            badge5.setVisibility(View.VISIBLE);
                        }else{
                            badge5=new BadgeView(SY_News.this,L9);
                            badge5.setText(aa[4]+"");
                            setbadgeView(badge5);
                            badge5.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if(badge5!=null){
                            badge5.setVisibility(View.GONE);
                        }
                    }

                }
            }


        }
    };
}
