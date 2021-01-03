package com.example.hbkjgoa.adapter;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.XMJD_Bean;
import com.example.hbkjgoa.util.FileUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class JCFWFJAdapter2 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<XMJD_Bean.FileList> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private ProgressDialog progressDialog;
	private File file01;
	Uri	uri;
	public final class ListItemView { // 自定义控件集合
		public TextView text1, text2,text3,text3_1, text4, text5, text6;
		ImageView im1;
	}
	@SuppressLint("HandlerLeak")
	private Handler handler =new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==6){
				progressDialog.dismiss();
				if (file01!=null) {

/*					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(Intent.ACTION_VIEW);
					String type = getMIMEType(file01);

				//	Uri	uri = Uri.fromFile(file01);

					if (Build.VERSION.SDK_INT >= 24) {
						uri = FileProvider.getUriForFile(context, "com.example.txflnew.fileprovider", file01);
					//	intent.setData(Uri.parse("http://www.xxx.com"));
					} else {
						uri = Uri.fromFile(file01);
					}
					intent.setDataAndType(uri, type);
					context. startActivity(intent);

				*/

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
					context.startActivity(intent);

				}
			}

		}

	};
	public JCFWFJAdapter2(Context context, List<XMJD_Bean.FileList> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		if (listItems == null) {
			return 0;
		} else {
			return listItems.size();
		}
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	public void setmes(List<XMJD_Bean.FileList> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int x=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.fj_item, null);
			listItemView.im1 = (ImageView) convertView.findViewById(R.id.im1);
			// 获取控件对象
			listItemView.text1 = (TextView) convertView.findViewById(R.id.text1);


			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}


	//	listItemView.text02.setText(Html.fromHtml("拜访时间: <font color='#3592D1'>"+listItems.get(position).getQDXX_QDSJ()+"</font> "));
		listItemView.text1.setText(listItems.get(position).getFileName());

		if(listItems.get(position).getFilePath().contains(".JPG")||listItems.get(position).getFilePath().contains(".jpg")||listItems.get(position).getFilePath().contains(".png")||listItems.get(position).getFilePath().contains(".bmp")){
			String url = "uploadfile/" + listItems.get(position).getFilePath().replace("_", "");
			ImageLoader.getInstance().displayImage(WebServiceUtil.getURL2() + url, listItemView.im1, options);
		}else if(listItems.get(position).getFilePath().contains(".PDF")||listItems.get(position).getFilePath().contains(".pdf")){
			listItemView.im1.setImageResource(R.mipmap.pdf);
		}else if(listItems.get(position).getFilePath().contains(".DOC")||listItems.get(position).getFilePath().contains(".doc")){
			listItemView.im1.setImageResource(R.mipmap.word);
		}else if(listItems.get(position).getFilePath().contains(".xls")||listItems.get(position).getFilePath().contains(".xlsx")){
			listItemView.im1.setImageResource(R.mipmap.excel);
		}else if(listItems.get(position).getFilePath().contains(".")){
			listItemView.im1.setImageResource(R.mipmap.unknow);
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				XMJD_Bean.FileList bp = new XMJD_Bean.FileList();

				String url="";
				url=url+listItems.get(x).getFilePath()+",";
				final String[] urls=url.split(",");
				for (int i = 0; i < urls.length; i++)
					urls[i] = WebServiceUtil.SERVICE_URL2 + "FJUploadFiles/" + urls[i];
				if(listItems.get(x).getFilePath().contains(".JPG")||listItems.get(x).getFilePath().contains(".jpg")||listItems.get(x).getFilePath().contains(".png")||listItems.get(x).getFilePath().contains(".PNG")||listItems.get(x).getFilePath().contains(".bmp")){
					imageBrower(x, urls);
				}else if(listItems.get(x).getFilePath().contains(".PDF")||listItems.get(x).getFilePath().contains(".pdf")
						||listItems.get(x).getFilePath().contains(".DOC")||listItems.get(x).getFilePath().contains(".doc")
						||listItems.get(x).getFilePath().contains(".xls")||listItems.get(x).getFilePath().contains(".xlsx")){


					final String filename=listItems.get(x).getFilePath();
					new AlertDialog.Builder(context).setMessage("是否下载查看？")
							.setNegativeButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									progressDialog= ProgressDialog.show(context,"正在获取数据中","请稍等...");
									new Thread(){
										@Override
										public void run() {

											try {
												String url= URLEncoder.encode(filename,"utf-8");
												file01=getFileFromServer(context.getResources().getString(R.string.http_url)+"/FJUploadFiles/"+url,filename);
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
		return convertView;
	}




	// 服务器获取图片圆形
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
			// .displayer(new CircleBitmapDisplayer())
			.build();

	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
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

}