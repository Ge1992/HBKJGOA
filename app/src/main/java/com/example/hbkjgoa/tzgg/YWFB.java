package com.example.hbkjgoa.tzgg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.DCWJ_Bean;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.StatusBarUtils;

public class YWFB extends Activity {
    private WebView info_text01;
    private String spname;
    private DCWJ_Bean ap;
    private TextView Text2;
    LoadingDialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ywfb);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");

        findview();

        initStatusBar();


    }

    private void findview(){
        Intent intent = this.getIntent();
        ap=(DCWJ_Bean)intent.getSerializableExtra("HD");
        Text2 = (TextView) findViewById(R.id.Text2);
        info_text01 = (WebView) findViewById(R.id.text01);
        info_text01.getSettings().setDefaultTextEncodingName("utf-8");
        info_text01.getSettings().setBuiltInZoomControls(true);
        info_text01.getSettings().setUseWideViewPort(true);
        info_text01.getSettings().setLoadWithOverviewMode(true);
        info_text01.getSettings().setJavaScriptEnabled(true);
        info_text01.setWebChromeClient(new MyWebChromeClient());
        info_text01.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        info_text01.loadUrl("http://116.62.238.239:61645/Diaocha/DC.aspx?ID="+ap.getId()+"&userName="+spname);
        LoadingDialog.Builder builder1=new LoadingDialog.Builder(YWFB.this)
                .setMessage("加载中...")
                .setCancelable(false);
        dialog1=builder1.create();
        dialog1.show();
        info_text01.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog1.dismiss();
            }

        });
        Text2.setText("网上调查");
    }
    /**
     * 继承WebChromeClient类
     * 对js弹出框时间进行处理
     *
     */
    final class MyWebChromeClient extends WebChromeClient {

        /**
         * 处理alert弹出框
         */
        @Override
        public boolean onJsAlert(WebView view, String url,
                                 String message, JsResult result) {

            //对alert的简单封装
            AlertDialog.Builder builder = new AlertDialog.Builder(YWFB.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setMessage(message).setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                    /*        finish();
                            GOGAO .listact.finish();
                            Intent intent = new Intent(YWFB.this, GOGAO.class);
                            intent.putExtra("LX", "通知公告");
                            startActivity(intent);*/
                        }
                    }).create().show();
            result.confirm();
            return true;
        }

        /**
         * 处理confirm弹出框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            result.confirm();
            return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 处理prompt弹出框
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            result.confirm();
            return super.onJsPrompt(view, url, message, message, result);
        }
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
            StatusBarUtils.setStatusBarColor(YWFB.this, R.color.btground);
        }
    }

}