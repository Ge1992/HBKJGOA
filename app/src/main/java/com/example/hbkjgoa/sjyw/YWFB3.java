package com.example.hbkjgoa.sjyw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.DZGG;
import com.example.hbkjgoa.util.StatusBarUtils;


public class YWFB3 extends Activity {
    private WebView info_text01;
    private String spname,ID;
    private DZGG email;
    TextView Text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ywfb);
        Intent intent = this.getIntent();
        email=(DZGG)intent.getSerializableExtra("DZGG");
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        findview();
        initStatusBar();
    }

    private void findview(){
        info_text01 = (WebView) findViewById(R.id.text01);
        Text2= (TextView) findViewById(R.id.Text2);
        Text2.setText("审计要闻");
        info_text01.getSettings().setDefaultTextEncodingName("utf-8");
        info_text01.getSettings().setBuiltInZoomControls(true);
        info_text01.getSettings().setUseWideViewPort(true);
        info_text01.getSettings().setLoadWithOverviewMode(true);
        info_text01.getSettings().setJavaScriptEnabled(true);


        info_text01.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        info_text01.loadUrl("http://116.62.238.239:61645/WX/GongGaoInfo.aspx?userid=" + spname + "&ID="+email.getID());


        info_text01.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //注意这边必须返回false
                return false;
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
            StatusBarUtils.setStatusBarColor(YWFB3.this, R.color.btground);
        }
    }
}