package com.example.hbkjgoa.xmjd;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.FileUtils;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ZQYZT_NEW extends Activity {
	private ListView listView;
	TextView Text2;
	private XMJD_Bean.projProgress ap;
	String[] names = null;
	String[] FilePath = null;
	String psid2 = "",psid3 = "";
	private ProgressDialog progressDialog;
	private File file01;

	@SuppressLint("HandlerLeak")
	private Handler handler =new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==6){
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
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zqyzt);

		findview();
		initStatusBar();
		if (ap.getFileLists().size()>0 && ap.getFileLists()!=null ){
			for (int i=0;i<ap.getFileLists().size();i++){
				psid2 += "," + ap.getFileLists().get(i).getFileName();
				names = psid2.substring(1, psid2.length()).split(",");

				psid3 += "," + ap.getFileLists().get(i).getFilePath();
				FilePath = psid3.substring(1, psid3.length()).split(",");

			}
			listView.setAdapter(new MyAdapter());
		}else {
			ToastUtils.showToast(ZQYZT_NEW.this, "暂无附件");
		}


	}

	private void findview(){
		listView = (ListView) findViewById(R.id.listView1);
		Text2=findViewById(R.id.Text2);
		Intent intent = this.getIntent();
		ap = (XMJD_Bean.projProgress) intent.getSerializableExtra("fj");
		Text2.setText("附件");
	}
    public void bt_back(View v){
        finish();
    }
	// 自定义适配器
	class MyAdapter extends BaseAdapter {
		private Context context; // 运行上下文

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int w = position;
			ViewHolder vh = new ViewHolder();
			// 通过下面的条件判断语句，来循环利用。如果convertView = null ，表示屏幕上没有可以被重复利用的对象。
			if (convertView == null) {
				// 创建View
				convertView = getLayoutInflater().inflate(R.layout.zqyzt_item, null);
				vh.im1 = (ImageView) convertView.findViewById(R.id.imageView1);
				vh.tv = (TextView) convertView.findViewById(R.id.textView1);
				vh.R1 = (RelativeLayout) convertView.findViewById(R.id.R1);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			// vh.iv.setImageResource(images[position]);
			vh.tv.setText(names[position]);

				if( FilePath[position].contains(".JPG")|| FilePath[position].contains(".jpg")|| FilePath[position].contains(".png")|| FilePath[position].contains(".bmp")){
					String url = "uploadfile/" +  FilePath[position].replace("_", "");
					ImageLoader.getInstance().displayImage(WebServiceUtil.getURL2() + url, vh.im1, options);
				}else if( FilePath[position].contains(".PDF")|| FilePath[position].contains(".pdf")){
					vh.im1.setImageResource(R.mipmap.pdf);
				}else if( FilePath[position].contains(".DOC")|| FilePath[position].contains(".doc")){
					vh.im1.setImageResource(R.mipmap.word);
				}else if( FilePath[position].contains(".xls")|| FilePath[position].contains(".xlsx")){
					vh.im1.setImageResource(R.mipmap.excel);
				}else if( FilePath[position].contains(".")){
					vh.im1.setImageResource(R.mipmap.unknow);
				}

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					XMJD_Bean.FileList bp = new XMJD_Bean.FileList();

					String url="";
					url=url+FilePath[w]+",";
					final String[] urls=url.split(",");
					for (int i = 0; i < urls.length; i++)
						urls[i] = WebServiceUtil.SERVICE_URL2 + "uploadfile/" + urls[i];
					if(FilePath[w].contains(".JPG")||FilePath[w].contains(".jpg")||FilePath[w].contains(".png")||FilePath[w].contains(".PNG")||FilePath[w].contains(".bmp")){
						imageBrower(w, urls);
					}else if(FilePath[w].contains(".PDF")||FilePath[w].contains(".pdf")
							||FilePath[w].contains(".DOC")||FilePath[w].contains(".doc")
							||FilePath[w].contains(".xls")||FilePath[w].contains(".xlsx")){


						final String filename=FilePath[w];
						new AlertDialog.Builder(ZQYZT_NEW.this).setMessage("是否下载查看？")
								.setNegativeButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										progressDialog= ProgressDialog.show(ZQYZT_NEW.this,"正在获取数据中","请稍等...");
										new Thread(){
											@Override
											public void run() {

												try {
													String url= URLEncoder.encode(filename,"utf-8");
													file01=getFileFromServer(ZQYZT_NEW.this.getResources().getString(R.string.http_url)+"/uploadfile/"+url,filename);
												} catch (Exception e) {
													e.printStackTrace();
												}
												Message message=new Message();
												message.what=6;
												handler.sendMessage(message);
											}
										}.start();

									}})
								.setPositiveButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}}).show();
					}

				}

			});

		/*	String url = "FJuploadfiles/" + names[position];
			ImageLoader.getInstance().displayImage(WebServiceUtil.getURL2() + url, vh.im1, options);
			final String TP_urls[] = FilePath[position].split(",");

			for (int i = 0; i < TP_urls.length; i++)
				TP_urls[i] = WebServiceUtil.SERVICE_URL2 + "uploadfile/" + TP_urls[i];
			// ImageLoader.getInstance().displayImage(TP_urls[i],vh.iv,options);

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					imageBrower(w, TP_urls);
				}
			});*/
			return convertView;
		}

	}
	public File getFileFromServer(String path, String filename) throws Exception{
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
	// 服务器获取图片圆形
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
			// .displayer(new CircleBitmapDisplayer())
			.build();

	static class ViewHolder {
		ImageView im1;
		TextView tv;
		RelativeLayout R1;
	}


	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(ZQYZT_NEW.this, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		ZQYZT_NEW.this.startActivity(intent);
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
			StatusBarUtils.setStatusBarColor(ZQYZT_NEW.this, R.color.btground);
		}
	}
}