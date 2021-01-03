package com.example.hbkjgoa.dbsx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.Email.ChooseUserActivity2;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.MyWork;
import com.example.hbkjgoa.model.NWorkToDo;
import com.example.hbkjgoa.model.WorkFlowItem;
import com.example.hbkjgoa.news.sin.SignPic;
import com.example.hbkjgoa.rczyk.gdzc_list3;
import com.example.hbkjgoa.sqsp.InfoWorkFlow_xj;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.UtilStr;
import com.example.hbkjgoa.util.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InfoWorkFlow extends Activity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KEY_pd == 1) {

        } else if (KEY_pd == 2) {
        }
        return super.onKeyDown(keyCode, event);
    }


    private int GCXX_ID;
    private int GCXX_FWQID, GCXX_WID;
    private String name;
    private String xm;
    private String lsdw, spname, nowtitle = "";
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
    private String json,jsonYY;
    private String json2;
    private ProgressDialog progressDialog;
    private Message message;
    private TextView Text1;
    private String sdcardDir = Environment.getExternalStorageDirectory().toString();
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
    private NWorkToDo email;
    private LocationListener ll;
    private LocationManager locationManager;
    final Handler myHandler = new Handler();

    @SuppressWarnings("unused")
    private String jwd;
    private String a;
    private String b, next;
    public static InfoWorkFlow mm;
    private MyWork ap;
    private String aaa;
    private Button ziliao02_zj, ziliao02_js;
    private String psms;
    private TextView zlcl01_T311, zlcl01_T3;
    private RelativeLayout bt;
    LoadingDialog dialog1;

    public void closehere() {
        finish();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("MissingPermission")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                progressDialog.dismiss();
                if (json != null && !json.equals("0")) {
//					json=json.replaceAll("src=\"/Content/","src=\""+WebServiceUtil.getURL()+"Content/");
//					FileUtils.write2SDFromInput2("woyeapp/fileforhtml/",iZLID+".htm",json);


                } else {
                    new AlertDialog.Builder(InfoWorkFlow.this).setMessage("获取数据失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
            } else if (msg.what == 4) {
                if (json != null && !json.equals("")) {
                    JSONArray nodArray;
                    try {
                        nodArray = new JSONArray(json);
                        List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
                        for (int j = 0; j < nodArray.length(); j++) {
                            {
                                JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
                                WorkFlowItem wf = new WorkFlowItem();
                                wf.setValueString(nodejsonObj.getString("Values"));
                                wf.setTextString(nodejsonObj.getString("Texts"));
                                wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
                                wf.setSpmsString(nodejsonObj.getString("Spms"));
                                wf.setPsmsString(nodejsonObj.getString("Psms"));
                                wf.setMrsprString(nodejsonObj.getString("Mrspr"));
                                nodeItems.add(wf);

                            }
                        }
                        email.setNodeList(nodeItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 5) {
                if (json != null && !json.equals("")) {
                    JSONArray nodArray;
                    try {
                        nodArray = new JSONArray(json);
                        List<WorkFlowItem> nodeItems = new ArrayList<WorkFlowItem>();
                        for (int j = 0; j < nodArray.length(); j++) {
                            {
                                JSONObject nodejsonObj = (JSONObject) nodArray.opt(j);
                                WorkFlowItem wf = new WorkFlowItem();
                                wf.setValueString(nodejsonObj.getString("Values"));
                                wf.setTextString(nodejsonObj.getString("Texts"));
                                wf.setTijaoString(nodejsonObj.getString("Tiaojian"));
                                wf.setSpmsString(nodejsonObj.getString("Spms"));
                                wf.setPsmsString(nodejsonObj.getString("Psms"));
                                wf.setMrsprString(nodejsonObj.getString("Mrspr"));
                                nodeItems.add(wf);

                            }
                        }
                        email.setNodeList(nodeItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ziliaoWebView01.loadUrl("javascript:window.local_obj.THshowNR('<head>'+" +
                            "document.getElementById('ZLNR').innerHTML+'</head>');");
                }
            } else if (msg.what == 6) {
                if (json.equals("ok")) {
                    Toast.makeText(getApplicationContext(), "结束成功",
                            Toast.LENGTH_SHORT).show();
                    WorkFlowActivity.mm.onRefresh();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "结束失败",
                            Toast.LENGTH_SHORT).show();
                }

            }else if (msg.what == 7) {
                Toast.makeText(getApplicationContext(), "提交成功",
                        Toast.LENGTH_SHORT).show();
                WorkFlowActivity.mm.onRefresh();
                finish();

            }

        }
    };

    @Override
    protected void onResume() {
        getpic();
        super.onResume();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                getpic();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint({"JavascriptInterface", "ClickableViewAccessibility"})
    private void toshowhtml() {

        ziliaoWebView01.getSettings().setDefaultTextEncodingName("utf-8");
        ziliaoWebView01.getSettings().setBuiltInZoomControls(true);
        //下面一行在低版本的SDK上不支持
        ziliaoWebView01.getSettings().setUseWideViewPort(true);
        ziliaoWebView01.getSettings().setLoadWithOverviewMode(true);
        ziliaoWebView01.getSettings().setJavaScriptEnabled(true);


        Log.d("yin", "内容：" + sNR);

        ziliaoWebView01.setWebViewClient(new MyWebViewClient());
        ziliaoWebView01.loadUrl("file:///android_asset/ZLXX.htm");
        ziliaoWebView01.setInitialScale(45);
        ziliaoWebView01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        ziliaoWebView01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        ziliaoWebView01.setWebChromeClient(new WebChromeClient());
        ziliaoWebView01.getSettings().setDisplayZoomControls(false);

        ziliaoWebView01.setWebChromeClient(new InfoWorkFlow.MyWebChromeClient());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_workflow);
        mm = this;
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        findView();
        getBundle();
        toshowhtml();
        getpic();
        setClick();
        initStatusBar();

    }

    private void findView() {
        ziliaoWebView01 = (WebView) findViewById(R.id.ziliaowebView01);
        ziliao02_sZLZT = (TextView) findViewById(R.id.ziliao02_sZLZT);
        ziliao02_up = (Button) findViewById(R.id.ziliao02_up);
        ziliao02_down = (Button) findViewById(R.id.ziliao02_down);
        ziliao_upPic = (Button) findViewById(R.id.ziliao_upPic);
        ziliao02_zj = (Button) findViewById(R.id.ziliao02_zj);
        ziliao02_js = (Button) findViewById(R.id.ziliao02_js);
        zlcl01_T3 = (TextView) findViewById(R.id.zlcl01_T3);
        String show = getIntent().getStringExtra("show");
        nowtitle = this.getIntent().getStringExtra("nowtitle");
        Text1 = (TextView) findViewById(R.id.Text1);
        Text1.setText("欢迎你，" + spname);
        ziliao02_sZLZT.setText("资料状态：" + sZLZT);
        bt = findViewById(R.id.bt);
        bt.setVisibility(View.GONE);
        ziliao02_gallery1 = (Gallery) findViewById(R.id.ziliao02_gallery1);
        if (show != null && show.equals("no")) {
            ziliao02_up.setText("审批记录");
            ziliao02_up.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(InfoWorkFlow.this, SPJL_Info.class);
                    bundle = new Bundle();
                    bundle.putSerializable("NWorkToDo", email);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            ziliao02_sZLZT.setText(sZLZT + "-" + email.getShenpiUserString());
        }


        if (nowtitle.equals("我的待阅")) {
            ziliao02_down.setVisibility(View.GONE);
            ziliao02_up.setText("已阅");
            ziliao02_up.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread() {
                        @Override
                        public void run() {
                            jsonYY = WebServiceUtil.everycanforStr2(
                                    "wid", "", "", "", "username", "",
                                    email.getID().toString(), "", "", "", spname, 0,
                                    "YY");
                            Log.d("yin", "YY:" + jsonYY);
                            message = new Message();
                            message.what = 7;
                            handler.sendMessage(message);
                        }
                    }.start();
                }
            });
        } else {
            ziliao02_up.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    ziliaoWebView01.loadUrl("javascript:removefocus();");
                    ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR(''+" + "getZLNR()+'');");
//				ziliaoWebView01.loadUrl("javascript:window.local_obj.CheckBiTians(''+" + "CheckBiTian()+'');");
//				ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR('<head>'+" + "document.getElementById('ZLNR').innerHTML+'</head>');");

                }
            });
        }
    }


    final class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

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
                    // This gets executed on the UI thread so it can safely modify Views
                    String a = UtilStr.url;
                    String b = UtilStr.url;
                    if (next == null || next.equals("")) {
                        ziliaoWebView01.loadUrl("javascript:myFunction(" + GCXX_FWQID + ",'" + spname + "','" + b + "')");
                        Log.d("yin", "ID:" + GCXX_FWQID);
                    } else {//
                        ziliaoWebView01.loadUrl("javascript:myFunction2(" + GCXX_FWQID + ",'" + spname + "','" + a + "'," + GCXX_WID + ")");
                        Log.d("yin", "2ID" + GCXX_FWQID);
                    }
                }
            });

        }

        @JavascriptInterface
        public void showNR(String html) {
            Log.d("HTML", html);
            if (html.equals("1")) {

            } else {
                email.setFromContentString(html);
                sNR = html;
                Intent intent = new Intent(InfoWorkFlow.this, Info_DoFlow.class);
                putbundles();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

        //zj
        @JavascriptInterface
        public void showNRzj(final String html) {
            Log.d("HTML", html);
            // 增加判断逻辑
            new Thread() {
                @Override
                public void run() {

                    json = WebServiceUtil.everycanforStr3("wid", "Content", "mrspr", "yijian", "NodeStr", "NodeID", "uname",
                            email.getID().toString(), html, "", "", "", 0, spname, "ZJBanLi");
                    Log.d("gsp", "转交:" + "1:" + html);
                    message = new Message();
                    message.what = 4;
                    handler.sendMessage(message);
                }
            }.start();

            if (!html.trim().isEmpty()) {
                Intent intent = new Intent(InfoWorkFlow.this, ChooseUserActivity2.class);
                bundle = new Bundle();
                bundle.putSerializable("NWorkToDo", email);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                InfoWorkFlow.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "内容获取失败",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void showNRjs(final String html) {
            Log.d("HTML", html);
            // 增加判断逻辑
            new Thread() {
                @Override
                public void run() {

                    json = WebServiceUtil.everycanforStr2("wid", "Content", "mrspr", "yijian", "uname", "",
                            email.getID().toString(), html, "", "", spname, 0, "JS1");

                    message = new Message();
                    message.what = 6;
                    handler.sendMessage(message);
                }
            }.start();

        }
  /*  	@JavascriptInterface
        public void CheckBiTians(String html) {
    		if(!html.equals("false")){
    			
    			ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR(''+" +"getZLNR()+'');");
    			//ziliaoWebView01.loadUrl("javascript:window.local_obj.showNR('<head>'+" + "document.getElementById('ZLNR').innerHTML+'</head>');");
    		}
    		
     }*/

        @JavascriptInterface
        public void THshowNR(String html) {
            Log.d("HTML", html);
            email.setFromContentString(html);
            sNR = html;
            Intent intent = new Intent(InfoWorkFlow.this, Info_DoFlowTH.class);
            putbundles();
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    protected void putbundles() {
        bundle = new Bundle();
        bundle.putSerializable("NWorkToDo", email);
        //bundle.putString("SNR",sNR);
        bundle.putString("KEY_NR", sNR);
    }

    private void setClick() {

        ziliao02_zj.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ziliaoWebView01.loadUrl("javascript:removefocus();");
                ziliaoWebView01.loadUrl("javascript:window.local_obj.showNRzj(''+" + "getZLNR()+'');");
            }
        });
        ziliao02_js.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ziliaoWebView01.loadUrl("javascript:removefocus();");

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        InfoWorkFlow.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setMessage("是否要结束！");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {

                                ziliaoWebView01.loadUrl("javascript:window.local_obj.showNRjs(''+" + "getZLNR()+'');");

                            }
                        }).show();

            }
        });

        ziliao02_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ziliaoWebView01.loadUrl("javascript:removefocus();");
                new Thread() {
                    @Override
                    public void run() {

                        int WID = email.getID();

                        json = WebServiceUtil.everycanforStr2("username", "", "", "", "", "wid", spname, "", "", "", "", WID, "NGetAllNode");
                        Log.d("yin", "GetNode:" + json);
                        System.out.println("GetNode:" + json);
                        Message message = new Message();
                        message.what = 5;
                        handler.sendMessage(message);

                    }

                }.start();


            }
        });

        ziliao_upPic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String aaString = "";
                /**
                 *
                 for(int i=0;i<email.urls.length;i++){
                 aaString=aaString+email.urls[i]+",";
                 }
                 */

                final String fileurl2 = aaString;

                Intent intent = new Intent();
                intent.setClass(InfoWorkFlow.this, ZlglInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("NWorkToDo", email);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    private void getBundle() {
        Intent intent = this.getIntent();
        email = (NWorkToDo) intent.getSerializableExtra("NWorkToDo");

        next = intent.getStringExtra("next");
        zlcl01_T3.setText("流程：" + email.getBeiYong1());
        if (next == null || next.equals("")) {
            name = email.getUsernameString();
            xm = email.getWorkname();
            lsdw = email.getJiedianNameString();
            currentPosition = email.getJiedianid();
            currentPosition2 = email.getFormid();
            KEY_pd = email.getFormid();
            sNR = email.getFromContentString();
            GCXX_ID = email.getFormid();
            sGCMC = email.getWorkname();
            iZLID = email.getID();
            ZL_ID = email.getID();
            sZLZT = email.getJiedianNameString();
            psms = email.getPSMS();
            GCXX_FWQID = email.getID();
            GCXX_WID = email.getFlowid();
//		 Toast.makeText(InfoWorkFlow.this, psms, 90000).show();
        } else {
            sZLZT = email.getJiedianNameString();
        }
        /**
         * 附件
         */

        //判断有几个文件
        if (email.getFujianString().equals("")) {
            ziliao_upPic.setVisibility(View.GONE);
        } else {
            aaa = email.getFujianString() + "|";
            Log.d("附件", aaa);
            int x = 0;
            //遍历数组的每个元素
            for (int i = 0; i <= aaa.length() - 1; i++) {
                String getstr = aaa.substring(i, i + 1);
                //     Toast.makeText(InfoWorkFlow.this, getstr, 90000).show();
                if (getstr.equals("|")) {
                    x++;
                }
            }


            ziliao_upPic.setText(Html.fromHtml("附件(<Span style='color:red;' >" + x + "</Span>)"));


            if (x != 0) {
                ziliao_upPic.setVisibility(View.VISIBLE);
            } else {
                ziliao_upPic.setVisibility(View.GONE);
            }
        }


        if (psms.equals("全部通过可向下流转")) {

        } else {
            ziliao02_zj.setVisibility(View.GONE);
            ziliao02_js.setVisibility(View.GONE);
            //	ziliao02_down.setVisibility(View.GONE);
        }


    }


    public void btn_back(View v) {     //标题栏 返回按钮
        this.finish();
    }

    public void btn_back_send(View v) {     //标题栏 返回按钮
        this.finish();
    }

    private void getpic() {
        listcc = getSD();
        if (listcc == null) {
            ziliao02_gallery1.setVisibility(View.GONE);
        } else {
            ziliao02_gallery1.setVisibility(View.VISIBLE);
            ziliao02_gallery1.setAdapter(new ImageAdapter(this, listcc));
            ziliao02_gallery1.setPadding(-230, 0, 0, 0);
            ziliao02_gallery1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    final int args = arg2;
                    if (picdel == 2) {
                        new AlertDialog.Builder(InfoWorkFlow.this).setTitle("请选择")
                                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//								Intent intent = new Intent(InfoWorkFlow.this,MultiTouch.class);
//								intent.putExtra("id",listcc.get(args).toString());
//								startActivity(intent);
                                    }
                                })
                                .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        File file01 = new File(listcc.get(args).toString());
                                        if (file01.exists()) {
                                            file01.delete();
                                        }
                                        getpic();
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(InfoWorkFlow.this).setTitle("请选择")
                                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//								Intent intent = new Intent(InfoWorkFlow.this,MultiTouch.class);
//								intent.putExtra("id",listcc.get(args).toString());
//								startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
            });
        }
    }

    public class ImageAdapter extends BaseAdapter {

        int mGalleryItemBackground;
        private Context mContext;
        private List<String> lis;

        public ImageAdapter(Context c, List<String> li) {
            mContext = c;
            lis = li;
            //Log.d("yin","看一下设置图片传来的list"+lis.toString());
        }

        @Override
        public int getCount() {
            return lis.size();
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            i = new ImageView(mContext);
            if (lis == null) {

            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(lis.get(position).toString());
                //得到缩略图
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 120);
                i.setImageBitmap(bitmap);
                i.setAdjustViewBounds(true);
                i.setLayoutParams(new Gallery.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

            }
            return i;
        }
    }

    private List<String> getSD() {
        List<String> it = new ArrayList<String>();
        File f = new File(sdcardDir + "/woyeapp/fileforhtml/" + iZLID + "/");
        if (!f.exists()) {
            //Log.d("yin","1无图片");
            return null;
        } else {
            File[] files = f.listFiles();
            if (files.length == 0) {
                //Log.d("yin","2无图片");
                return null;
            } else {
                for (int i = 0; i < files.length; i++) {
                    //Log.d("yin","有图片");
                    File file = files[i];
                    if (getImageFile(file.getPath()))
                        it.add(file.getPath());
                }
                return it;
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private boolean getImageFile(String fName) {
        boolean re;
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            re = true;
        } else {
            re = false;
        }
        return re;
    }

    public void bt_back(View v) {
        finish();
    }

    public void bt_back2(View v) {

        if (WorkFlowActivity.mm != null) {
            WorkFlowActivity.mm.closehere();
        }
        finish();

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
          /*  new AlertDialog.Builder(InfoWorkFlow.this).
                    setTitle("Alert").setMessage(message).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    }).create().show();*/

            if (message.equals("img1404918026")){
                Intent intent=new Intent(InfoWorkFlow.this, SignPic.class);
                putbundles();
                bundle.putString("step",step);
                bundle.putString("sWPID",sWPID);
                intent.putExtras(bundle);
                startActivity(intent);
            }

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
    public void showsign(String zp,String step){
        ziliaoWebView01.loadUrl("javascript:SetQM('"+zp+"','"+step+"');");
    }
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
            StatusBarUtils.setStatusBarColor(InfoWorkFlow.this, R.color.btground);
        }
    }
}
