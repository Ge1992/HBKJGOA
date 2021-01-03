package com.example.hbkjgoa.zcfg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.DZGG;
import com.example.hbkjgoa.util.StatusBarUtils;


public class YWFB4 extends Activity {
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
        Text2.setText("政策法规");
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
        info_text01.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });


        //设置下载监听
        info_text01.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //进行下载处理，跳转浏览器或者调用系统下载方法

                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.wps.moffice_eng");//WPS个人版的包名
                if (intent == null) {
                    Toast.makeText(getApplicationContext(), "手机未安装 WPS Office 请先从应用市场下载", Toast.LENGTH_LONG).show();
                    // 从市场上下载
                    Uri uri = Uri.parse("market://details?id=cn.wps.moffice_eng");
                    // 直接从指定网址下载
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "正在打开请稍等", Toast.LENGTH_SHORT).show();
                    //    Intent intent = YWFB2.this.getPackageManager().getLaunchIntentForPackage( "cn.wps.moffice_eng");
                    Bundle bundle = new Bundle();
                    intent.setData(Uri.parse(url));//这里采用传入文档的在线地址进行打开，免除下载的步骤，也不需要判断安卓版本号
                    intent.putExtras(bundle);
                    YWFB4.this.startActivity(intent);
                }

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
            StatusBarUtils.setStatusBarColor(YWFB4.this, R.color.btground);
        }
    }

}