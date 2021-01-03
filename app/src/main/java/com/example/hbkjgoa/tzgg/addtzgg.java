package com.example.hbkjgoa.tzgg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.MyBean;
import com.example.hbkjgoa.util.AlbumActivity;
import com.example.hbkjgoa.util.LoadingDialog;
import com.example.hbkjgoa.util.MyGridAdapter;
import com.example.hbkjgoa.util.NetHelper;
import com.example.hbkjgoa.util.NoScrollGridView;
import com.example.hbkjgoa.util.StatusBarUtils;
import com.example.hbkjgoa.util.ToastUtils;
import com.example.hbkjgoa.util.UploadUtil;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;

import org.kobjects.base64.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class addtzgg extends Activity {
	private RelativeLayout R1;
	private Button add;
	private String phototime;
	private String path = "", imagebuffer;
	private String sdcardDir = Environment.getExternalStorageDirectory().toString();
	private String fileName;
	Bitmap bitmap;
	private String spname;
	private String json,json2;
	private EditText e1, e2,Ed_mc;
	private TextView t1;
	private FileInputStream fis;
	private Dialog progressDialog;
	private String photoPath;// 照片保存路径
	//
	private NoScrollGridView detail;
	private String Camerapath,Camerapath2;
	private MyBean email = new MyBean();
	private String request="";
	String json1="";
	 private LinearLayout L1;
	LoadingDialog dialog1;
	private static String[] PERMISSIONS_CAMERA_AND_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.CAMERA};
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// progressDialog.dismiss();
			if (msg.what == 1) {
				if (request != null) {
					dialog1.dismiss();
					Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
					finish();

					if(TaskActivity.mm!=null){
						TaskActivity.mm.onRefresh();
					}
				}

			}

		}
	};

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE)
				.getString("spname", "");
		setContentView(R.layout.activity_addgg);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
		FindView();
		setfirstphoto();

		SetClic();
		initStatusBar();
	}

	private void FindView() {
		Intent intent = this.getIntent();
		R1 = (RelativeLayout) findViewById(R.id.R1);
		t1 = (TextView) findViewById(R.id.t1);
		e1 = (EditText) findViewById(R.id.Ed1);
		e2 = (EditText) findViewById(R.id.Ed2);
		add = (Button) findViewById(R.id.save);
		detail = (NoScrollGridView) findViewById(R.id.gridView);

		//
		Ed_mc=(EditText) findViewById(R.id.Ed_mc);
		L1=(LinearLayout) findViewById(R.id.L1);

	}

	private void SetClic() {
		R1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	Intent intent = new Intent(addtzgg.this, ChooseUserActivity2.class);
			//	startActivityForResult(intent, 1);

				Intent intent = new Intent(addtzgg.this, bm_list.class);
				startActivityForResult(intent, 1);



			}
		});

		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 uppic();

			}
		});



	}





	protected void uppic() {



		boolean havenet=NetHelper.IsHaveInternet(addtzgg.this);
		if (havenet) {
			LoadingDialog.Builder builder1=new LoadingDialog.Builder(addtzgg.this)
					.setMessage("加载中...")
					.setCancelable(false);
			dialog1=builder1.create();
			dialog1.show();
			new Thread(){
				@Override
				public void run() {


					final Map<String, String> params1 = new HashMap<String, String>();

					params1.put("ID","0");
					params1.put("TitleStr", e1.getText().toString());
					params1.put("UserName",spname);
					params1.put("UserBuMen", t1.getText().toString());
					params1.put("ContentStr",e2.getText().toString());





					Map<String, File> files = new HashMap<String, File>();
					if (email.getUrls() != null && email.getUrls().length >0) {
						for(int i=email.getUrls().length-1;i>=0;i--){
							if (new File(email.getUrls()[i].substring(6, email.getUrls()[i].length())).getName().contains(".mp4")) {
								files.put("uploadfile"+i,new File(email.getUrls()[i].substring(6, email.getUrls()[i].length())));
							}else {
								files.put("uploadfile"+i, yaosuo(email.getUrls()[i].substring(6,email.getUrls()[i].length())));
							}
						}
						try {
							request = UploadUtil.post(WebServiceUtil.getURL()+"/TZGG_Edit", params1, files);
							Log.d("yin","上传图片："+request);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}else{

						try {
							request = UploadUtil.post(WebServiceUtil.getURL()+"/TZGG_Edit", params1, null);
							Log.d("yin","上传图片："+request);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}.start();
		}
		else {
			ToastUtils.showToast(addtzgg.this,"网络连接失败！");
		}

	}


	private void photo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(addtzgg.this, AlertDialog.THEME_HOLO_LIGHT);

		builder.setTitle("选择");
		builder.setItems(new String[] { "拍照", "从相册中选择"}, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {


                        Date now = new Date();
                        SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
                        String phototime = d1.format(now);
                        Camerapath = Environment.getExternalStorageDirectory().toString() + "/woyeapp/" + phototime+ ".jpg";
                        File file = new File(Camerapath);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent, 2);


				} else   {
					Intent intent = new Intent(addtzgg.this, AlbumActivity.class);
					Bundle bundle = new Bundle();
					ArrayList<String> dataList = new ArrayList<String>();
					bundle.putStringArrayList("dataList", dataList);
					intent.putExtras(bundle);
					startActivityForResult(intent, 3);
				}

			}
		});
		builder.create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent paramIntent) {
		switch (requestCode) {
		case 1:
			if ((requestCode == 1) && (resultCode == 102)) {
				String dclrl = paramIntent.getStringExtra("dclrl");
				t1.setText(dclrl);
			}
			break;
		//
		case 2:
			if (resultCode == RESULT_OK) {
				if (resultCode == RESULT_OK) {
					if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
						return;
					}
					//startActivity(new Intent(ZSDTRZ.this, PhotosEdit.class).putExtra("path", Camerapath));
					///overridePendingTransition(R.anim.roll_up, R.anim.roll);
					getRefresh();
				} else {
					Toast.makeText(this, "取消拍照", Toast.LENGTH_SHORT).show();
				}

			}
			break;
		case 3:
			if (resultCode == RESULT_OK) {

				Bundle bundle = paramIntent.getExtras();
				ArrayList<String> tDataList = (ArrayList<String>) bundle.getSerializable("dataList");
				if (tDataList != null) {

					int le = 0;
					String urls2[] = null;
					if (email.getUrls() != null && email.getUrls().length > 0) {
						le = email.getUrls().length;
					}

					String urls1[] = new String[le + tDataList.size()];
					urls2 = new String[le + tDataList.size() + 1];
					if (le != 0) {
						for (int j = 0; j < le; j++) {
							urls1[j] = email.getUrls()[j];
							urls2[j] = email.getUrls()[j];
						}
					}
					for (int i = 0; i < tDataList.size(); i++) {
						urls1[le + i] = "file://" + tDataList.get(i);
						urls2[le + i] = "file://" + tDataList.get(i);
					}
					urls2[le + tDataList.size()] = "assets://photoadd.jpg";
					email.setUrls(urls1);
					final int p = urls2.length - 1;
					detail.setVisibility(View.VISIBLE);
					detail.setAdapter(new MyGridAdapter(urls2, addtzgg.this));
					detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
							if (position1 == p) {
								photo();
							} else {
								imageBrower(position1, email.getUrls());
							}

						}
					});
					detail.setOnItemLongClickListener(new OnItemLongClickListener() {
						public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
							if (arg2 != p) {
								new AlertDialog.Builder(addtzgg.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("删除此照片！").setTitle("注意")
										.setNegativeButton("取消", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface arg0, int arg1) {
											}
										}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												deletepic(arg2, email.getUrls());
											}
										}).show();

							}
							return true;
						}
					});

				}


			}
			break;
		default:
		}

	}
	private void imageBrower(int position, String[] urls) {

		if (urls[position].contains(".mp4")) {

			final File a = new File(urls[position].replace("file://", ""));
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(a), "video/*");
			startActivity(intent);
		} else {
			Intent intent = new Intent(addtzgg.this, ImagePagerActivity.class);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
			startActivity(intent);
		}

	}
	public void getRefresh2() {
		final String aaa = Camerapath2;
		String urls2[] = null;
		if (email.getUrls() != null && email.getUrls().length > 0) {
			String urls1[] = new String[email.getUrls().length + 1];
			urls2 = new String[email.getUrls().length + 2];
			for (int i = 0; i < email.getUrls().length; i++) {
				urls1[i] = email.getUrls()[i];
				urls2[i] = email.getUrls()[i];
			}
			urls1[email.getUrls().length] = "file://" + aaa;
			urls2[email.getUrls().length] = "file://" + aaa;
			urls2[email.getUrls().length + 1] = "assets://photoadd.jpg";
			email.setUrls(urls1);
		} else {
			urls2 = new String[2];
			String pp = "file://" + aaa;
			urls2[0] = "file://" + aaa;
			urls2[1] = "assets://photoadd.jpg";
			email.setUrls(new String[] { pp });
		}
		final int p = urls2.length - 1;
		detail.setVisibility(View.VISIBLE);
		detail.setAdapter(new MyGridAdapter(urls2, addtzgg.this));
		detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
				if (position1 == p) {
					photo();
					
				} else {
					imageBrower(position1, email.getUrls());
				}

			}
		});

		detail.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				if (arg2 != p) {
					new AlertDialog.Builder(addtzgg.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("删除此照片！").setTitle("注意")
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
								}
							}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									deletepic(arg2, email.getUrls());
								}

							}).show();

				}
				return true;
			}
		});

	}
	
	public void getRefresh() {
		final String aaa = Camerapath;
		String urls2[] = null;
		if (email.getUrls() != null && email.getUrls().length > 0) {
			String urls1[] = new String[email.getUrls().length + 1];
			urls2 = new String[email.getUrls().length + 2];
			for (int i = 0; i < email.getUrls().length; i++) {
				urls1[i] = email.getUrls()[i];
				urls2[i] = email.getUrls()[i];
			}
			urls1[email.getUrls().length] = "file://" + aaa;
			urls2[email.getUrls().length] = "file://" + aaa;
			urls2[email.getUrls().length + 1] = "assets://photoadd.jpg";
			email.setUrls(urls1);
		} else {
			urls2 = new String[2];
			String pp = "file://" + aaa;
			urls2[0] = "file://" + aaa;
			urls2[1] = "assets://photoadd.jpg";
			email.setUrls(new String[] { pp });
		}
		final int p = urls2.length - 1;
		detail.setVisibility(View.VISIBLE);
		detail.setAdapter(new MyGridAdapter(urls2, addtzgg.this));
		detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
				if (position1 == p) {
					photo();
					
				} else {
					imageBrower(position1, email.getUrls());
				}

			}
		});

		detail.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				if (arg2 != p) {
					new AlertDialog.Builder(addtzgg.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("删除此照片！").setTitle("注意")
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
								}
							}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									deletepic(arg2, email.getUrls());
								}

							}).show();

				}
				return true;
			}
		});

	}
	private void deletepic(int arg2, String[] urls) {
		if (urls.length == 1) {
			email.setUrls(null);
			detail.setVisibility(View.VISIBLE);
			detail.setAdapter(new MyGridAdapter(new String[] { "assets://photoadd.jpg" }, addtzgg.this));
			detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
					photo();
					
				}
			});

		} else {
			String a[] = new String[urls.length - 1];
			for (int i = 0; i < arg2; i++) {
				a[i] = urls[i];
			}
			for (int i = arg2; i < a.length; i++) {
				a[i] = urls[i + 1];
			}
			email.setUrls(a);
			String urls2[] = new String[a.length + 1];
			for (int i = 0; i < a.length; i++) {
				urls2[i] = a[i];
			}
			urls2[a.length] = "assets://photoadd.jpg";
			final int p = urls2.length - 1;
			detail.setVisibility(View.VISIBLE);
			detail.setAdapter(new MyGridAdapter(urls2, addtzgg.this));
			detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
					if (position1 == p) {
						photo();
						
					} else {
						imageBrower(position1, email.getUrls());
					}

				}
			});
			detail.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
					if (arg2 != p) {
						new AlertDialog.Builder(addtzgg.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("删除此照片！").setTitle("注意")
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
									}
								}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										deletepic(arg2, email.getUrls());
									}

								}).show();

					}
					return true;
				}
			});
		}

	}
	private void setfirstphoto() {
		String urls[] = new String[1];
		urls[0] = "assets://photoadd.jpg";

		detail.setVisibility(View.VISIBLE);
		detail.setAdapter(new MyGridAdapter(urls, addtzgg.this));
		detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
				photo();
				
			}
		});
	}
	
	@SuppressWarnings("unused")
	private File yaosuo(String path) {
		File file = new File(path);
		FileInputStream fis = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				if (fis.available() / 1024 > 1024) {

					Options options = new Options();
					options.inSampleSize = 4;
					Bitmap mBitmap_High = BitmapFactory.decodeFile(path, options);
					try {
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
						mBitmap_High.compress(Bitmap.CompressFormat.JPEG, 100, bos);
						bos.flush();
						bos.close();
					} catch (IOException e) {
						return file;
					}

					return file;
				} else {
					return file;
				}
			} catch (Exception e) {
				return file;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unused", "resource" })
	private String fileString(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count = 0;
		try {
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(Base64.encode(baos.toByteArray()));
	}
	
	

	public void bt_back(View v) {
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
			StatusBarUtils.setStatusBarColor(addtzgg.this, R.color.btground);
		}
	}
}
