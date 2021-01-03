package com.example.hbkjgoa.tzgg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.LanGongGao;
import com.example.hbkjgoa.util.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class YWFB2 extends Activity {
    private WebView info_text01;
    private String spname,ID;
    private LanGongGao email;
    TextView Text2;
    private File file01;
    private ProgressDialog progressDialog;
    private final String APP_PACKAGE = "cn.wps.moffice_eng";
    private final String APP_NAME = "WPS Office 个人版";
    private Uri downLoadUri;
    //下载完成标识
    private boolean dFlag = false;
    //是否安装标识
    private boolean iFlag = false;


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1) {
               progressDialog.dismiss();
                if (file01!=null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy( builder.build() );
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_VIEW);
                        String type = getMIMEType(file01);
                        intent.setDataAndType(Uri.fromFile(file01), type);
                        startActivity(intent);
                    }

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ywfb);
        Intent intent = this.getIntent();
        email=(LanGongGao)intent.getSerializableExtra("LanGongGao");
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        info_text01 = (WebView) findViewById(R.id.text01);
        Text2= (TextView) findViewById(R.id.Text2);
        Text2.setText("通知公告");
        info_text01.getSettings().setDefaultTextEncodingName("utf-8");
        info_text01.getSettings().setBuiltInZoomControls(true);
        info_text01.getSettings().setUseWideViewPort(true);
        info_text01.getSettings().setLoadWithOverviewMode(true);
        info_text01.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = info_text01.getSettings();
        webSettings.setJavaScriptEnabled(true);

        info_text01.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        info_text01.loadUrl("http://116.62.238.239:61645/WX/GongGaoInfo.aspx?userid=" + spname + "&ID="+email.getID());
        FileUtils.creatSDDir("/woyeapp/");

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
                    YWFB2.this.startActivity(intent);
                }


          //      Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();


                 // download(url.substring(url.length()-10));
                // 指定下载地址
/*                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
                request.allowScanningByMediaScanner();
                // 设置通知的显示类型，下载进行时和完成后显示通知
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                // 设置通知栏的标题，如果不设置，默认使用文件名
                //  request.setTitle("This is title");
                // 设置通知栏的描述
                // request.setDescription("This is description");
                // 允许在计费流量下下载
                request.setAllowedOverMetered(false);
                // 允许该记录在下载管理界面可见
                request.setVisibleInDownloadsUi(false);
                // 允许漫游时下载
                request.setAllowedOverRoaming(true);
                // 允许下载的网路类型
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                // 设置下载文件保存的路径和文件名
                String fileName  = URLUtil.guessFileName(url, contentDisposition, mimetype);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                // 另外可选一下方法，自定义下载路径
                // request.setDestinationUri()
                // request.setDestinationInExternalFilesDir()
                final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                // 添加一个下载任务
                long downloadId = downloadManager.enqueue(request);


                // 获取此次下载的ID
                final long refernece = downloadManager.enqueue(request);
                // 注册广播接收器，当下载完成时自动安装
                IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (refernece == myDwonloadID) {
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            Uri downloadFileUri = downloadManager.getUriForDownloadedFile(refernece);
                            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(install);
                        }
                    }
                };
                registerReceiver(receiver, filter);*/

            }
        });



    }















    public void bt_back(View v){


        finish();
        GOGAO .listact.finish();
        Intent intent = new Intent();
        intent.setClass(YWFB2.this,GOGAO.class);
        intent.putExtra("LX", "通知公告");
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            GOGAO .listact.finish();
            Intent intent = new Intent();
            intent.setClass(YWFB2.this,GOGAO.class);
            intent.putExtra("LX", "通知公告");
            startActivity(intent);


        }
        return false;
    }



    private final String[][] MIME_MapTable={
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    public void download(final String html) {
      progressDialog= ProgressDialog.show(YWFB2.this,"正在加载文件中","请稍等...");
        new Thread(){
            @Override
            public void run() {

                try {
                    final String url = URLEncoder.encode(html, "utf-8");
                    file01=getFileFromServer(getResources().getString(R.string.http_url)+url.replace("http://116.62.238.239:61645/",""),url.substring(url.length()-10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();

    }
    private String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        /* 获取文件的后缀名*/
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public File getFileFromServer(String path, String filename) throws Exception {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getErrorStream();
                File file = new File(Environment.getExternalStorageDirectory(),"/woyeapp/"+filename);
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len;
                while((len =bis.read(buffer))!=-1){
                    fos.write(buffer, 0, len);
                }
                fos.close();
                bis.close();
                is.close();
                return file;

        }
        else{
            return null;
        }
    }
}