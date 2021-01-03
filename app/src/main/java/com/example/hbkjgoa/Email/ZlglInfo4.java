package com.example.hbkjgoa.Email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.Xmjl_sjxBean;
import com.example.hbkjgoa.util.FileUtils;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@SuppressLint({ "SetJavaScriptEnabled", "HandlerLeak", "WorldReadableFiles", "WorldWriteableFiles", "SimpleDateFormat" })
public class ZlglInfo4 extends Activity {

	private String fileurl;
	private WebView info_text01;
	private String nr="";
	private File file01;
	private TextView Text1;
	private String spname;
	private Xmjl_sjxBean email;
	private String mc;
	private Dialog progressDialog;
	final Handler myHandler = new Handler();
	LoadingDialog dialog1;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what==1) {
				if (file01!=null) {

					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(Intent.ACTION_VIEW);
					String type = getMIMEType(file01);
					intent.setDataAndType(Uri.fromFile(file01), type);
					startActivity(intent);
				}
				dialog1.dismiss();
			}else if (msg.what==2) {

			}
		}
	};
	/**
	 * 说明：
	 * 1. SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖。
	 * 2. SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏图标为黑色或者白色
	 * 3. StatusBarUtil 工具类是修改状态栏的颜色为白色。
	 */
	private void initStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			//    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
			StatusBarUtils.setStatusBarColor(ZlglInfo4.this, R.color.btground);
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zlglinfo);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
		initStatusBar();
		info_text01 = (WebView)findViewById(R.id.text01);
		Intent intent = this.getIntent();


		email=(Xmjl_sjxBean)intent.getSerializableExtra("Xmjl_sjxBean");

		mc=email.getFuJian().replace("..", "").replace("UploadFile/", "");
		if (mc.equals("")) {
			ToastUtils.showToast(ZlglInfo4.this, "暂无数据！");
		}
		fileurl=mc;




		info_text01.getSettings().setDefaultTextEncodingName("utf-8");
		info_text01.getSettings().setBuiltInZoomControls(true);
		info_text01.getSettings().setSupportZoom(true);
		info_text01.getSettings().setDefaultZoom(ZoomDensity.CLOSE);
		info_text01.getSettings().setUseWideViewPort(true);
		info_text01.getSettings().setLoadWithOverviewMode(true);
		info_text01.getSettings().setJavaScriptEnabled(true);
		info_text01.loadUrl("file:///android_asset/ZlglInfo_pic.htm");
		info_text01.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		info_text01.setWebViewClient(new MyWebViewClient());
		info_text01.setInitialScale(45);
		info_text01.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		info_text01.setWebChromeClient(new WebChromeClient());
	}

	final class InJavaScriptLocalObj {
		@JavascriptInterface
		public void showSource(String html) {
			myHandler.post(new Runnable() {
				@Override
				public void run() {
					info_text01.loadUrl("javascript:SetQM('"+fileurl+"','');");
				}
			});

		}

		@JavascriptInterface
		public void download(final String html) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(ZlglInfo4.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(){
				@Override
				public void run() {
					FileUtils.creatSDDir("/woyeapp/");

					try {
						if(html.indexOf(".jpg")>=0 || html.indexOf(".JPG")>=0 || html.indexOf(".png")>=0 || html.indexOf(".PNG")>=0|| html.indexOf(".gif")>=0 || html.indexOf("bmp")>=0){
							try {
								String url= URLEncoder.encode(html,"utf-8");
								file01=getFileFromServer(getResources().getString(R.string.http_url)+"/uploadfile/"+url,html);
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}else if (html.indexOf(".mp4")>=0 || html.indexOf(".MP4")>=0 ) {

							/**
							 * 		调用自带播放器播放
							 */
								/*String url=URLEncoder.encode(html,"utf-8");
								Uri a =Uri.parse(getResources().getString(R.string.http_url)+"/fjuploadfiles/"+url);
								Intent intent = new Intent(Intent.ACTION_VIEW);
							    intent.setDataAndType(a, "video/mp4");
							    startActivity(intent);  */

						/*	 Intent intent = new Intent(Intent.ACTION_VIEW);
							 String url=URLEncoder.encode(html,"utf-8");
							  Uri uri = Uri.parse(getResources().getString(R.string.http_url)+"/fjuploadfiles/"+url);
							       intent.setData (uri);
							       startActivity(intent);*/


							if (android.os.Build.MODEL.contains("mi")||android.os.Build.MODEL.contains("MI")||android.os.Build.MODEL.contains("Mi")) {
								System.out.println("asdasdasdas"+android.os.Build.MODEL);
								//小米
								Intent intent = new Intent(Intent.ACTION_VIEW);
								String url= URLEncoder.encode(html,"utf-8");
								Uri uri = Uri.parse(getResources().getString(R.string.http_url)+"/uploadfile/"+url);
								intent.setData (uri);
								startActivity(intent);
							}else {
								//其他
								String url= URLEncoder.encode(html,"utf-8");
								Uri a = Uri.parse(getResources().getString(R.string.http_url)+"/uploadfile/"+url);
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setDataAndType(a, "video/mp4");
								startActivity(intent);
							}

				/*			String url1=URLEncoder.encode(html,"utf-8");
							Uri uri = Uri.parse(getResources().getString(R.string.http_url)+"/fjuploadfiles/"+url1);
							String url = uri.toString();
							Intent intent = new Intent(ZlglInfo4.this, VideoActivity.class);
							intent.putExtra("url", url);
							startActivity(intent);
							*/

						}else{
							try {
								String url= URLEncoder.encode(html,"utf-8");
								file01=getFileFromServer(getResources().getString(R.string.http_url)+"/uploadfile/"+url,html);
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}





					} catch (Exception e) {
						e.printStackTrace();
					}
					Message message=new Message();
					message.what=1;
					handler.sendMessage(message);
				}
			}.start();


		}

	}
	public void btn_back(View v) {
		this.finish();
	}
	public File getFileFromServer(String path, String filename) throws Exception {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 200) {
				//创建文件夹
				FileUtils.creatSDDir("/woyeapp/");
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


	final class MyWebViewClient extends WebViewClient {

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
		finish();
	}
	public void btn_back_send(View v) {     //标题栏 返回按钮
		this.finish();
	}


}

