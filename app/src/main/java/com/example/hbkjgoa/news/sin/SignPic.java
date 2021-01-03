package com.example.hbkjgoa.news.sin;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.dbsx.InfoWorkFlow;
import com.example.hbkjgoa.sqsp.InfoWorkFlow2;
import com.example.hbkjgoa.util.WebServiceUtil;

public class SignPic extends Activity {

	/** Called when the activity is first created. */
	private MyView handWrite = null;
	private Button clear = null;
	private Button save = null;

	public static int displayWidth;
	public static int displayHeight;
	private String json="";
	//private ImageView saveImage = null;

	private String zlid,name,step,uxm;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (json!=null&&!json.equals("")) {
				if (InfoWorkFlow.mm!=null) {
					InfoWorkFlow.mm.showsign(json, step);
				}
			/*	if (InfoWorkFlow2.mm!=null) {
					InfoWorkFlow2.mm.showsign(json, step);
				}*/
				finish();
			}else {
				new AlertDialog.Builder(SignPic.this)
						.setMessage("签名上传失败！")
						.setPositiveButton("确定",null)
						.show();
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signpic);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		displayWidth = dm.widthPixels;
		displayHeight = dm.heightPixels;

		uxm=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("uxm","");
		name=getSharedPreferences("sdlxLogin",Context.MODE_PRIVATE+Context.MODE_PRIVATE).getString("spname","");
		zlid=this.getIntent().getExtras().getInt("KEY_iZLID")+"";
		step=this.getIntent().getExtras().getString("step");

		handWrite = (MyView) findViewById(R.id.handwriteview);
		save = (Button) findViewById(R.id.save);
		//saveImage = (ImageView) findViewById(R.id.saveimage);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(new clearListener());
		save.setOnClickListener(new saveListener());
		handerl();
	}

	private class clearListener implements OnClickListener {

		public void onClick(View v) {
			handWrite.clear();
		}
	}

	static Handler hh;
	@SuppressLint("HandlerLeak")
	public void handerl() {
		hh = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int what = msg.what;
				if (what == 3) {
					//saveImage.setVisibility(View.VISIBLE);
					//saveImage.setImageBitmap(handWrite.saveImage());
					uploadImage();
				}
				super.handleMessage(msg);
			}

		};
	}

	private void uploadImage(){
		new Thread(){
			@Override
			public void run() {

				ByteArrayOutputStream bStream = new ByteArrayOutputStream();

				//Bitmap.createScaledBitmap(handWrite.saveImage(),80, 40,true).compress(CompressFormat.PNG,100, bStream);
				handWrite.saveImage().compress(CompressFormat.PNG, 100, bStream);

				byte[] bytes = bStream.toByteArray();

				String imagebuffer = Base64.encodeToString(bytes, Base64.DEFAULT);

				/*File file = new File(Environment.getExternalStorageDirectory().toString()+"/woyeapp/Picsign.jpg");
				OutputStream output;
				try {
					output = new FileOutputStream(file);
					BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
					bufferedOutput.write(bytes);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}


				final Map<String, String> params1 = new HashMap<String, String>();

				params1.put("userid",name);
				params1.put("fileName",zlid+"_"+uxm+"_"+step+"_"+new Random().nextInt()+".jpg");

				Map<String, File> files = new HashMap<String, File>();
				files.put("uploadfile",new File(Environment.getExternalStorageDirectory().toString()+"/woyeapp/Picsign.jpg"));

				try {
					json = UploadUtil.post(WebServiceUtil.getURL()+"/IOSZLQMUpload", params1, files);
				} catch (IOException e) {
					e.printStackTrace();
				}*/


				json= WebServiceUtil.everycanforStr2(
						"fs",
						"fileName",
						"userid",
						"",
						"",
						"",
						imagebuffer,
						zlid+"_"+uxm+"_"+step+"_"+new Random().nextInt()+".jpg",
						name,
						"",
						"",
						0,
						"ZLQMUpload");

				Log.d("yin","传照片"+json);

				handWrite.setImge();

				Message message=new Message();
				message.what=1;
				handler.sendMessage(message);
			}
		}.start();

	}

	private class saveListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			Message msg = new Message();
			msg = Message.obtain(handWrite.handler1, 2);
			handWrite.handler1.sendMessage(msg);

			if (handWrite.saveImage() != null) {
				Log.i("RG", "1");

			} else {
			}
		}

	}

}