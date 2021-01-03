package com.example.hbkjgoa.rczyk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.sqsp.Info_DoFlow_xj;
import com.example.hbkjgoa.util.StatusBarUtils;

import java.util.List;


public class InfoWorkFlow_xq extends Activity {
    private String sdcardDir = Environment.getExternalStorageDirectory().toString();
   // private NWork_XJ ap;
    public static InfoWorkFlow_xq mm;
    final Handler myHandler = new Handler();
    private int GCXX_ID;
    private int GCXX_FWQID, GCXX_WID;
    private String name;
    private String xm;
    private String lsdw, spname, Sbname="",Sbid="",uname="",xh="",wxmc="",tile="";
    private TextView Text1,Text2;
    private String YH_LSDQ;
    private Gallery ziliao02_gallery1;
    private ImageView i;
    private WebView ziliaoWebView01;
    private TextView ziliao02_sZLZT;
    private Button ziliao02_up;
    private Button ziliao02_down;
    private Button ziliao_upPic;
    private int iZLID;
    private int ZL_ID;
    private String sNR;
    private String sGCMC;
    private String sZLZT;
    private String json;
    private String json2;
    private ProgressDialog progressDialog;
    private Message message;
    private int KEY_pd;
    private int currentPosition;
    private int currentPosition2;
    private Bundle bundle;
    private boolean xianshi = false;
    private List<String> listcc;
    private String phototime = null;
    private String json3;
    private String step = "";
    private String sWPID;
    private int picdel = 1;
    private LocationListener ll;
    private LocationManager locationManager;
    private String jwd;
    private String a;
    private String b, next;
    private String id = "", wid = "";
    private String[] bb;
    private GDZC_Bean ap;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("MissingPermission")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                progressDialog.dismiss();
                if (json != null && !json.equals("0")) {
                } else {
                    new AlertDialog.Builder(InfoWorkFlow_xq.this)
                            .setMessage("获取数据失败！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog.dismiss();
                                }
                            }).show();
                }
            } else if (msg.what == 2) {
                if (json2 == null) {
                } else if (json2.equals("success")) {
                    ziliao02_sZLZT.setText("资料状态：" + sZLZT);
                    if (xianshi) {
                        ziliao02_down.setVisibility(View.VISIBLE);
                        ziliao02_up.setVisibility(View.VISIBLE);
                        picdel = 2;
                    }
                }
            } else if (msg.what == 3) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, ll);
            }else if (msg.what == 4) {
                if (json2 != null && !json2.equals("") && json2.contains("|")) {
                    bb= json2.split("\\|");
                    final String[] mItems = bb;
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoWorkFlow_xq.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder.setItems(mItems, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ziliaoWebView01.loadUrl("javascript:setsbname('" + mItems[which] + "','" + Sbid + "')");

                        }
                    });
                    builder.create().show();

                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_workflow);
        mm = this;
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        id = this.getIntent().getStringExtra("id");
        wid = this.getIntent().getStringExtra("wid");
        tile = this.getIntent().getStringExtra("tile");
    //    value = this.getIntent().getStringExtra("value");
        Intent intent = this.getIntent();
  //      ap = (NWork_XJ) intent.getSerializableExtra("NWork_XJ");
        ap = (GDZC_Bean) intent.getSerializableExtra("GDZC");
        findView();
        getBundle();
        toshowhtml();
        setClick();
        initStatusBar();

    }

    private void findView() {
        Intent intent = this.getIntent();
        ap = (GDZC_Bean) intent.getSerializableExtra("GDZC");

        Text1 = (TextView) findViewById(R.id.Text1);
        Text2= (TextView) findViewById(R.id.Text2);
        Text1.setText("欢迎你，" + spname);
        Text2.setText(tile);
        ziliaoWebView01 = (WebView) findViewById(R.id.ziliaowebView01);
        ziliao02_sZLZT = (TextView) findViewById(R.id.ziliao02_sZLZT);
        ziliao02_sZLZT.setText("资料状态：" + sZLZT);
        ziliao02_up = (Button) findViewById(R.id.ziliao02_up);
        ziliao02_down = (Button) findViewById(R.id.ziliao02_down);
        ziliao02_gallery1 = (Gallery) findViewById(R.id.ziliao02_gallery1);
        String show = getIntent().getStringExtra("show");
        if (show != null && show.equals("no")) {
            ziliao02_up.setVisibility(View.GONE);
        }
        ziliao02_down.setVisibility(View.GONE);
        wxmc=ap.getGuanLiUser()+"-"+ap.getXingHao()+"-"+ap.getSheBeiLeiBie();

    }

    @SuppressLint({"JavascriptInterface", "ClickableViewAccessibility"})
    private void toshowhtml() {
        ziliaoWebView01.getSettings().setDefaultTextEncodingName("utf-8");
        ziliaoWebView01.getSettings().setBuiltInZoomControls(true);
        // ziliaoWebView01.getSettings().setDisplayZoomControls(false);
        ziliaoWebView01.getSettings().setUseWideViewPort(true);
        ziliaoWebView01.getSettings().setLoadWithOverviewMode(true);
        ziliaoWebView01.getSettings().setJavaScriptEnabled(true);
        ziliaoWebView01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        ziliaoWebView01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        ziliaoWebView01.setWebChromeClient(new WebChromeClient());
        ziliaoWebView01.addJavascriptInterface(this, "nativeMethod");
        ziliaoWebView01.setWebViewClient(new MyWebViewClient());
        ziliaoWebView01.loadUrl("file:///android_asset/ZLXX.htm");
        ziliaoWebView01.getSettings().setDisplayZoomControls(false);
        ziliaoWebView01.setInitialScale(100);
        ziliaoWebView01.setHorizontalScrollBarEnabled(false);//水平不显示
        ziliaoWebView01.setVerticalScrollBarEnabled(false); //垂直不显示
        ziliaoWebView01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        ziliaoWebView01.setWebChromeClient(new InfoWorkFlow_xq.MyWebChromeClient());
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == 0) {
            } else if (requestCode == 1024) {
                Sbname = data.getStringExtra("data");// 这边获取值
                Sbid = data.getStringExtra("dataid");
                uname = data.getStringExtra("uname");
                xh = data.getStringExtra("xh");
                wxmc=uname+"-"+xh+"-"+Sbname;
                ziliaoWebView01.loadUrl("javascript:setsbname('" + wxmc + "','" + Sbid + "')");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setClick() {
        ziliao02_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ziliaoWebView01.loadUrl("javascript:removefocus();");
                ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR(''+" + "getZLNR()+'');");
            }
        });

        ziliao02_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void getBundle() {
        Intent intent = this.getIntent();
        next = intent.getStringExtra("next");
    }
    protected void putbundles() {
        bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("wid", wid);
        bundle.putString("KEY_NR", sNR);
 //       bundle.putSerializable("NWorkToDo", ap);

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
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //return super.onJsAlert(view, url, message, result);
            //对alert的简单封装
          /*  new AlertDialog.Builder(InfoWorkFlow_xj.this).
                    setTitle("Alert").setMessage(message).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    }).create().show();*/

       /*     if (message.contains("申购")){
                new Thread() {
                    @Override
                    public void run() {
                        json2 = WebServiceUtil.everycanforStr("", "", "", "","", "", "", 0, "GetSheBeiType");
                        Message message = new Message();
                        message.what = 4;
                        handler.sendMessage(message);



                    }
                }.start();
            }else  if (message.contains("申购"){
                startActivityForResult(new Intent(InfoWorkFlow_xq.this, gdzc_list3.class), 1024);
            }*/
            if (tile.equals("耗材领取")) {
                ziliaoWebView01.loadUrl("javascript:sethcname('" + wxmc + "','" + ap.getID()+ "')");
            }else if (tile.equals("设备维修")) {
                ziliaoWebView01.loadUrl("javascript:setsbname('" + wxmc + "','" + ap.getID()+ "')");
                ziliaoWebView01.loadUrl("javascript:setwx('" + "保修" + "')");
            }


        //    ziliaoWebView01.loadUrl("javascript:setsbname('" + wxmc + "','" + ap.getID()+ "')");
      //
      //      ziliaoWebView01.loadUrl("javascript:setwx('" + "保修" + "')");
            result.confirm();
            return true;
        }

        /**
         * 处理confirm弹出框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            //return super.onJsConfirm(view, url, message, result);
            result.confirm();
            return super.onJsConfirm(view, url, message, result);

        }

        /**
         * 处理prompt弹出框
         */

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            result.confirm();
            return super.onJsPrompt(view, url, message, message, result);
        }
    }



    final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.d("WebView", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView", "onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource();");

            super.onPageFinished(view, url);
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource() {
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    String a = "http://116.62.238.239:61645/";
                    ziliaoWebView01.loadUrl("javascript:myFunction2(" + id + ",'" + spname + "','" + a + "'," + wid + ")");


                }
            });
        }

        @JavascriptInterface
        public void showNR(String html) {
            Log.d("HTML1", html);
            sNR = html;
            Intent intent = new Intent(InfoWorkFlow_xq.this, Info_DoFlow_xj.class);
            putbundles();
            intent.putExtras(bundle);
            startActivity(intent);
        }

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
            StatusBarUtils.setStatusBarColor(InfoWorkFlow_xq.this, R.color.btground);
        }
    }
    public void bt_back(View v) {
        finish();
    }
    public void bt_back2(View v) {
        finish();
    }
    public void btn_back(View v) {     //标题栏 返回按钮
        this.finish();
    }

}



