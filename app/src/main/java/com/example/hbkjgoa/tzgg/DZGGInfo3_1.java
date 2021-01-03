package com.example.hbkjgoa.tzgg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.LanGongGao;
import com.example.hbkjgoa.model.QS_Bean;
import com.example.hbkjgoa.util.MyTableTextView;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DZGGInfo3_1 extends Activity {
	private LanGongGao email;
	private WebView info_text01; // 帐号编辑框
	final Handler myHandler = new Handler();
	private String nr,spname,json,json1,aaa;
	private File file01;
	private ProgressDialog progressDialog;
	Button fj;
	private LinearLayout relativelayout,mainLinerLayout;
	private List<LanGongGao> list = new ArrayList<LanGongGao>();
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what==1) {
				progressDialog.dismiss();
				if (file01!=null) {

					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(Intent.ACTION_VIEW);
					String type = getMIMEType(file01);
					// 判断版本大于等于7.0
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
						StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
						StrictMode.setVmPolicy(builder.build());
					}
					Uri data = Uri.fromFile(file01);
					intent.setDataAndType(data, type);
					//	Toast.makeText(context, "uri:" + data.toString(), Toast.LENGTH_SHORT).show();
					startActivity(intent);
				}
			}else if(msg.what==2){
            //    Toast.makeText(getApplicationContext(), json1, Toast.LENGTH_SHORT).show();

			}
		}
	};



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dzgginfo3);
		info_text01 = (WebView)findViewById(R.id.text01);
		fj=findViewById(R.id.bj);
		Intent intent = this.getIntent();
		email=(LanGongGao)intent.getSerializableExtra("info");
		spname=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");
		info_text01.getSettings().setDefaultTextEncodingName("utf-8");
		info_text01.getSettings().setBuiltInZoomControls(true);
		info_text01.getSettings().setUseWideViewPort(true);
		info_text01.getSettings().setLoadWithOverviewMode(true);
		info_text01.getSettings().setJavaScriptEnabled(true);
		info_text01.loadUrl("file:///android_asset/ZlglInfo_pic.htm");
		info_text01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		info_text01.setWebViewClient(new MyWebViewClient());
		info_text01.setInitialScale(45);
		info_text01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		info_text01.setWebChromeClient(new WebChromeClient());
		initStatusBar();
		initData();
        setZT();
		nr="<p style=\"font-size:21px;font-weight: bold\" ;  align=\"center\">"+email.getTitleString()+"</p>"


			//	+"<p  style=\" margin:10px 0px 10px 0px;\">发布单位："+email.getNoticeType()+"</p>"
				+"<p  style=\"margin:5px 0px 10px 0px;\">发布人员："+email.getUsernameString()+"</p>"
				+"<p  style=\" margin:10px 0px 10px 0px;\">发布时间："+email.getTimeString()+"</p>"
				+"<p  style=\" margin:10px 0px 10px 0px;\">正文内容："+email.getContentString()+"</p>";




		if (email.getFujianString().equals("")){

		//	nr=nr+email.getContentString();
			info_text01.loadDataWithBaseURL("","<meta name='viewport' content='width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no'>"+nr,"text/html","utf-8",null);

		}else {
			nr=nr+"<style type='text/css'>.mui-preview-image.mui-fullscreen {position: fixed;z-index: 20;background-color: #000;}.mui-preview-header,.mui-preview-footer {position: absolute;width: 100%;left: 0;z-index: 10;}.mui-preview-header {height: 44px;top: 0;}.mui-preview-footer {height: 50px;bottom: 0px;}.mui-preview-header .mui-preview-indicator {display: block;line-height: 25px;color: #fff;text-align: center;margin: 15px auto 4;width: 70px;background-color: rgba(0, 0, 0, 0.4);border-radius: 12px;font-size: 16px;}.mui-preview-image {display: none;-webkit-animation-duration: 0.5s;animation-duration: 0.5s;-webkit-animation-fill-mode: both;animation-fill-mode: both;}.mui-preview-image.mui-preview-in {-webkit-animation-name: fadeIn;animation-name: fadeIn;}.mui-preview-image.mui-preview-out {background: none;-webkit-animation-name: fadeOut;animation-name: fadeOut;}.mui-preview-image.mui-preview-out .mui-preview-header,.mui-preview-image.mui-preview-out .mui-preview-footer {display: none;}.mui-zoom-scroller {position: absolute;display: -webkit-box;display: -webkit-flex;display: flex;-webkit-box-align: center;-webkit-align-items: center;align-items: center;-webkit-box-pack: center;-webkit-justify-content: center;justify-content: center;left: 0;right: 0;bottom: 0;top: 0;width: 100%;height: 100%;margin: 0;-webkit-backface-visibility: hidden;}.mui-zoom {-webkit-transform-style: preserve-3d;transform-style: preserve-3d;}.mui-slider .mui-slider-group .mui-slider-item img {width: auto;height: auto;max-width: 100%;max-height: 100%;}.mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {width: 100%;}.mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {display: inline-table;}.mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {display: table-cell;vertical-align: middle;}.mui-preview-loading {position: absolute;width: 100%;height: 100%;top: 0;left: 0;display: none;}.mui-preview-loading.mui-active {display: block;}.mui-preview-loading .mui-spinner-white {position: absolute;top: 50%;left: 50%;margin-left: -25px;margin-top: -25px;height: 50px;width: 50px;}.mui-preview-image img.mui-transitioning {-webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;transition: transform 0.5s ease, opacity 0.5s ease;}@-webkit-keyframes fadeIn {0% {opacity: 0;}100% {opacity: 1;}}@keyframes fadeIn {0% {opacity: 0;}100% {opacity: 1;}}@-webkit-keyframes fadeOut {0% {opacity: 1;}100% {opacity: 0;}}@keyframes fadeOut {0% {opacity: 1;}100% {opacity: 0;}}p img {max-width: 100%;height: auto;}</style>";
			String cc[] = email.getFujianString().split("\\|");

			for(int i=0;i<cc.length;i++){
				String dd=cc[i];
				String ee[]=dd.split("\\*");

				if(dd.indexOf(".jpg")>=0 || dd.indexOf(".JPG")>=0 || dd.indexOf(".png")>=0 || dd.indexOf(".PNG")>=0|| dd.indexOf(".gif")>=0 || email.getFujianString().indexOf("bmp")>=0)
				{
					nr =nr+ "<table><tr><td rowspan='2'><img onclick=window.local_obj.download('"+ee[0]+"'); style='width:50px;'  src='"+"http://116.62.238.239:61645/uploadfile/"+ee[0]+"'  /></td>" +"<td ><div colspan='2' onclick=window.local_obj.download('"+ee[0]+"'); class=\"mui-media-body mui-ellipsis-3\" > " + ee[1] + "</div></td> </tr></table>";
				}
				else if(dd.indexOf(".doc")>=0 || dd.indexOf(".DOC")>=0 || dd.indexOf(".docx")>=0 ||dd.indexOf(".DOCX")>=0)
				{
					nr =nr+ "<table><tr><td rowspan='2'><img onclick=window.local_obj.download('"+ee[0]+"'); style='width:50px;'  src='file:///android_asset/word.png'  /></td>" +"<td ><div colspan='2' onclick=window.local_obj.download('"+ee[0]+"'); class=\"mui-media-body mui-ellipsis-3\" > " + ee[1] + "</div></td> </tr><tr><td colspan='1'>点击查看</td></tr></table>";
				}
				else if(dd.indexOf(".xls")>=0 || dd.indexOf(".XLS")>=0 || dd.indexOf(".xlsx")>=0 || dd.indexOf(".XLSX")>=0)
				{
					nr =nr+ "<table><tr><td rowspan='2'><img onclick=window.local_obj.download('"+ee[0]+"'); style='width:50px;'  src='file:///android_asset/excel.png'  /></td>" +"<td ><div colspan='2' onclick=window.local_obj.download('"+ee[0]+"'); class=\"mui-media-body mui-ellipsis-3\" > " + ee[1] + "</div></td> </tr><tr><td colspan='1'>点击查看</td></tr></table>";
				}
				else if(dd.indexOf(".pdf")>=0 || dd.indexOf(".PDF")>=0 )
				{
					nr =nr+ "<table><tr><td rowspan='2'><img onclick=window.local_obj.download('"+ee[0]+"'); style='width:50px;'  src='file:///android_asset/pdf.png'  /></td>"   +"<td ><div colspan='2' onclick=window.local_obj.download('"+ee[0]+"'); class=\"mui-media-body mui-ellipsis-3\" > " + ee[1] + "</div></td> </tr><tr><td colspan='1'>点击查看</td></tr></table>";
				}
			}




			info_text01.loadDataWithBaseURL("","<meta name='viewport' content='width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no'>"
							+nr,"text/html","utf-8",null);
		}


//判断有几个文件
	/*	if (email.getFujianString().equals("")) {
			fj.setVisibility(View.GONE);
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
			fj.setText(Html.fromHtml("附件(<Span style='color:red;' >" + x + "</Span>)"));
			if (x != 0) {
				fj.setVisibility(View.VISIBLE);
			} else {
				fj.setVisibility(View.GONE);
			}

		}*/
/*	fj.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String aaString = "";
			final String fileurl2 = aaString;
			Intent intent = new Intent();
			intent.setClass(DZGGInfo3_1.this, ZlglInfo_tzgg.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("LanGongGao", email);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	});*/

	}


	private void setZT(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                json1= WebServiceUtil.everycanforStr2("uname","","","","","id",
                        spname,"","", "", "",email.getID(), "SetGGZT");
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
            }
        }).start();
    }

	@SuppressLint("CutPasteId")
	private void initData() {

		relativelayout = (LinearLayout) LayoutInflater.from(DZGGInfo3_1.this).inflate(R.layout.table_khdw2, null);
		MyTableTextView title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_1);
		title.setVisibility(View.VISIBLE);
		TextPaint tp = title.getPaint();//加粗
		tp.setFakeBoldText(true);
		mainLinerLayout = (LinearLayout) findViewById(R.id.MyTable);
		title.setText("签收单位");
		title.setTextSize(18);
		title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_2);
		TextPaint tp2 = title.getPaint();
		tp2.setFakeBoldText(true);
		title.setText("签收人");
		title.setTextSize(18);
		title = (MyTableTextView) relativelayout.findViewById(R.id.list_1_3);
		TextPaint tp3 = title.getPaint();
		tp3.setFakeBoldText(true);
		title.setText("签收时间");
		title.setTextSize(18);
		mainLinerLayout.addView(relativelayout);
		mainLinerLayout = (LinearLayout) findViewById(R.id.MyTable);
		List<QS_Bean> Comll= email.getQd_list();
		if(Comll!=null&&Comll.size()>0){
			for (int i = 0; i < Comll.size(); i++) {
				relativelayout = (LinearLayout) LayoutInflater.from(DZGGInfo3_1.this).inflate(R.layout.table_khdw2, null);
				MyTableTextView txt = (MyTableTextView) relativelayout.findViewById(R.id.list_1_1);
				txt.setVisibility(View.VISIBLE);
				txt.setText(Comll.get(i).getBm());
				if (Comll.get(i).getSFYD().equals("0")){
					txt.setTextColor(Color.rgb(255, 107, 107));
					txt.setTextSize(16);
				}else {

				}
				MyTableTextView txt2 = (MyTableTextView) relativelayout.findViewById(R.id.list_1_2);
				txt2.setText(Comll.get(i).getTruename());
				if (Comll.get(i).getSFYD().equals("0")){
					txt2.setTextColor(Color.rgb(255, 107, 107));
					txt2.setTextSize(16);
				}else {

				}
				MyTableTextView txt3 = (MyTableTextView) relativelayout.findViewById(R.id.list_1_3);
				if (Comll.get(i).getSFYD().equals("1")){
					txt3.setText(Comll.get(i).getReadtime().replace(" ","\n").substring(0, Comll.get(i).getReadtime().length()-3));
					txt3.setTextSize(16);
				}else {
					txt3.setText("");
				}

				//		txt3.setTextColor(Color.rgb(255, 107, 107));


				mainLinerLayout.addView(relativelayout);
			}
		}

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

	final class InJavaScriptLocalObj {
		@JavascriptInterface
		public void showSource(String html) {
			myHandler.post(new Runnable() {
				@Override
				public void run() {



				}
			});

		}
		public void photo(final String html) {
			AlertDialog.Builder builder = new AlertDialog.Builder(DZGGInfo3_1.this,AlertDialog.THEME_HOLO_LIGHT);
			//builder.setTitle("选择");
			builder.setItems(new String[] {"在线预览","第三方应用查看"}, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						try {
							final String url = URLEncoder.encode(html, "utf-8");
							file01=getFileFromServer(getResources().getString(R.string.http_url)+"/uploadfile/"+url,html);

						} catch (Exception e) {
							e.printStackTrace();
						}
						Message message=new Message();
						message.what=1;
						handler.sendMessage(message);
					} else {


					}

				}
			});
			builder.create().show();
		}


		@JavascriptInterface
		public void download(final String html) {
			progressDialog=ProgressDialog.show(DZGGInfo3_1.this,"正在加载文件中","请稍等...");
			new Thread(){
				@Override
				public void run() {

					try {
						final String url = URLEncoder.encode(html, "utf-8");
						file01=getFileFromServer(getResources().getString(R.string.http_url)+"/uploadfile/"+url,html);

					} catch (Exception e) {
						e.printStackTrace();
					}
					Message message=new Message();
					message.what=1;
					handler.sendMessage(message);
				/*	Intent intent = new Intent(DZGGInfo3_1.this, DZGGInfo3_2.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("LanGongGao", email);
					intent.putExtras(bundle);
					startActivity(intent);*/

				}
			}.start();


		}

	}


	public File getFileFromServer(String path,String filename) throws Exception{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
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
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	final class MyWebViewClient extends WebViewClient{

		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);

			return true;

		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);

		}

		public void onPageFinished(WebView view, String url) {

			view.loadUrl("javascript:window.local_obj.showSource('');");

			super.onPageFinished(view, url);

		}

	}



    public void bt_back(View v){
      /*  Intent intent = new Intent(DZGGInfo3_1.this, GOGAO.class);
        startActivity(intent);

		GOGAO.mm.finish();
        finish();*/
		if(TaskActivity.mm!=null){
			finish();
			TaskActivity.mm.onRefresh();
		}

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

        /*    Intent intent = new Intent(DZGGInfo3_1.this, GOGAO.class);
            startActivity(intent);
			GOGAO.mm.finish();
            finish();*/
			if(TaskActivity.mm!=null){
				finish();
				TaskActivity.mm.onRefresh();
			}

        }
        return super.onKeyDown(keyCode, event);
    }
	public void btn_back(View v) {     //标题栏 返回按钮
		this.finish();
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
			StatusBarUtils.setStatusBarColor(DZGGInfo3_1.this, R.color.btground);
		}
	}
}

