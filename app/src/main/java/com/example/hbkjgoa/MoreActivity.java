package com.example.hbkjgoa;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.display.CircleBitmapDisplayer;
import com.example.hbkjgoa.display.CircularImage;
import com.example.hbkjgoa.model.MyBean;
import com.example.hbkjgoa.util.Image.HeaderImageView;
import com.example.hbkjgoa.util.Image.HeaderInfo;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MoreActivity extends Activity {
	private RelativeLayout R1;
	private TextView text1, text2, text3, text4, text5, text6;
	private String json, phototime;
	private String uxm, spword, spname, Department, JiaoSe, ZhiWei;
	private HeaderImageView mHiHeaderImageView;
	private List<HeaderInfo> list = new ArrayList<>();
	private HeaderInfo i1;
	private ImageView tx;
	private String Camerapath, Camerapath2;
	private MyBean email = new MyBean();
	public static int displayWidth;
	public static int displayHeight;
	Bitmap bitmap;
	public static MoreActivity instance = null;
	private ProgressDialog progressDialog;
	private String fileName;
	// private ImageView saveImage = null;
	private static String path = "/sdcard/myYCJFQ/";// sd路径
	private String zlid, name, step;
	// private DisplayImageOptions options;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (json != null && !json.equals("")) {
				progressDialog.dismiss();

			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreactivity);
		instance = this;
		SetTile();
		json=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("json","");
		spname=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");
		spword=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spword","");
		spname=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");

		Department=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("Department","");
		ZhiWei=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("ZhiWei","");
		initView();
		initStatusBar();
		setOnClick();
	}

	private void SetTile() {
		TextView tile = findViewById(R.id.title);
		tile.setText("个人设置");
	}

	public void bt_back(View v) {
		this.finish();
	}

	// 服务器获取图片圆形
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
			.displayer(new CircleBitmapDisplayer()).build();

	private void initView() {
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);
		text6 = (TextView) findViewById(R.id.text6);
		R1 = (RelativeLayout) findViewById(R.id.R1);
		tx = (ImageView) findViewById(R.id.tx);
		CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);

		mHiHeaderImageView = (HeaderImageView) findViewById(R.id.hi);
		//https需另做支持
		i1 = new HeaderInfo(spname, "", 1115);
		list.clear();
		list.add(i1);
		mHiHeaderImageView.setTextSize1(23f).setTextSizeOther(13f).setList(list);


		text1.setText(spname);
		text2.setText(Department);
		text3.setText(ZhiWei);
		//text4.setText(udh);
		//text5.setText(udwdz);
		Bitmap bt = BitmapFactory.decodeFile(path + "ycjfq.jpg");// 从Sd中找头像，转换成Bitmap

		if(bt!=null){

			@SuppressWarnings("deprecation")
			Drawable drawable = new
					BitmapDrawable(bt);//转换成drawable
			cover_user_photo.setImageDrawable(drawable);
		}else{

			// 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中


		}

	}

	private void setOnClick() {
		R1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this, ChangePassword.class);
				startActivity(intent);

			}
		});

		tx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			//	photo();
			}
		});
	}

	private void photo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("选择");
		builder.setItems(new String[] { "拍照", "从相册中选择" }, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
					String phototime = d1.format(now);
					// Camerapath =
					// Environment.getExternalStorageDirectory().toString() +
					// "/woyeapp/" + phototime+ ".jpg";
					// fileName=phototime+ ".jpg";
					Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent2.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "ycjfq.jpg")));
					startActivityForResult(intent2, 2);// 采用ForResult打开
				} else {
					Date now = new Date();
					SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
					String phototime = d1.format(now);
					fileName = phototime + ".jpg";
					Intent intent1 = new Intent(Intent.ACTION_PICK, null);
					intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(intent1, 1);

				}

			}
		});
		builder.create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 0:
				if (resultCode == RESULT_OK) {
					if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
						return;
					}
					// startActivity(new Intent(MoreActivity.this,
					// PhotosEdit.class).putExtra("path", Camerapath));
					Log.d("路径为", Camerapath);
					overridePendingTransition(R.anim.roll_up, R.anim.roll);
				} else {
					Toast.makeText(this, "取消拍照", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:
				/**
				 * 拍照
				 */
				if (resultCode == RESULT_OK) {
					cropPhoto(data.getData());// 裁剪图片
				}
				break;
			case 2:
				/**
				 * 相册选择
				 */
				if (resultCode == RESULT_OK) {
					File temp = new File(Environment.getExternalStorageDirectory() + "/lygzs.jpg");
					cropPhoto(Uri.fromFile(temp));// 裁剪图片
				}
				break;
			case 3:
				if (resultCode == RESULT_OK) {
					Bundle extras = data.getExtras();
					bitmap = extras.getParcelable("data");
					if (bitmap != null) {
						/**
						 * 上传服务器代码
						 */
						progressDialog = ProgressDialog.show(MoreActivity.this, "正在更换头像", "请稍等...");
						new Thread() {
							public void run() {
								ByteArrayOutputStream bStream = new ByteArrayOutputStream();
								bitmap.compress(CompressFormat.PNG, 100, bStream);
								byte[] bytes = bStream.toByteArray();
								String imagebuffer = Base64.encodeToString(bytes, Base64.DEFAULT);

								json = WebServiceUtil.everycanforStr2("fs", "fileName", "userid", "", "", "", imagebuffer,
										fileName, spname, "", "", 0, "ZLQMUpload");
								Log.d("yin", "传照片: " + imagebuffer);
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}.start();
						// Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
						setPicToView(bitmap);// 保存在SD卡中
						CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
						cover_user_photo.setImageBitmap(bitmap);// 用ImageView显示出来
					}
				}
				break;
		}
	}

	/**
	 * 调用系统的裁剪
	 *
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}

	private void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		FileOutputStream b = null;
		File file = new File(path);
		file.mkdirs();// 创建文件夹
		String fileName = path + "ycjfq.jpg";// 图片名字
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(CompressFormat.JPEG, 100, b);// 把数据写入文件

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 上传服务器
	 */
	private void uploadImage() {
		progressDialog = ProgressDialog.show(MoreActivity.this, "正在更换头像", "请稍等...");
		new Thread() {
			public void run() {
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				bitmap = BitmapFactory.decodeFile(Camerapath);
				bitmap.compress(CompressFormat.PNG, 100, bStream);
				byte[] bytes = bStream.toByteArray();
				String imagebuffer = Base64.encodeToString(bytes, Base64.DEFAULT);

				json = WebServiceUtil.everycanforStr2("fs", "fileName", "userid", "", "", "", imagebuffer, fileName,
						spname, "", "", 0, "ZLQMUpload");
				Log.d("yin", "传照片: " + json);
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		}.start();

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
			StatusBarUtils.setStatusBarColor(MoreActivity.this, R.color.btground);
		}
	}
}
