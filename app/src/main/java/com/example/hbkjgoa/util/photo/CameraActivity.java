package com.example.hbkjgoa.util.photo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.hbkjgoa.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CameraActivity extends Activity implements SurfaceHolder.Callback{
	private SurfaceView surfaceView1;
	private SurfaceHolder surfaceHolder;
	private Button button1;
	private Button button2;
	private Button button3;
	private Camera camera;
	private byte[] datecc=null;
	private String path="";
	private Intent intent;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_OK,intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("yin","跳转后显示1");
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.d("yin","跳转后显示2");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d("yin","跳转后显示3");
		setContentView(R.layout.camera_take);
		Log.d("yin","跳转后显示4");
		intent=this.getIntent();
		Log.d("yin","跳转后显示5");
		Bundle bundle=intent.getExtras();
		Log.d("yin","跳转后显示6");
		path=bundle.getString("path");
		Log.d("yin","跳转后显示7");
		initView();
		Log.d("yin","跳转后显示8");
		setClick();
		Log.d("yin","跳转后显示");
	}

	private void setClick() {
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				camera.autoFocus(mAutoFocusCallback);
				button1.setVisibility(View.GONE);
			}
		});

		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				datecc=null;
				if (camera!=null) {
					camera.startPreview();
					button2.setVisibility(View.GONE);
					button3.setVisibility(View.GONE);
					button1.setVisibility(View.VISIBLE);
				}
			}
		});

		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FileOutputStream outSteam=null;
				FileOutputStream outSteam2=null;
				try{
					outSteam=new FileOutputStream(path);
					outSteam2=new FileOutputStream(path);
					outSteam.write(datecc);
					outSteam2.write(datecc);
					outSteam.close();
					outSteam2.close();
					datecc=null;


				}catch(FileNotFoundException e)
				{
					e.printStackTrace();
				} 	catch (IOException e) {
					e.printStackTrace();
				}
				//添加保存后事件
				setResult(RESULT_OK,intent);
				finish();
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		surfaceView1=(SurfaceView)findViewById(R.id.camera_take_surfaceView1);
		button1=(Button)findViewById(R.id.camera_take_button1);
		button2=(Button)findViewById(R.id.camera_take_button2);
		button3=(Button)findViewById(R.id.camera_take_button3);
		button2.setVisibility(View.GONE);
		button3.setVisibility(View.GONE);

		surfaceHolder=surfaceView1.getHolder();
		//surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.setKeepScreenOn(true);// 屏幕常亮
		surfaceHolder.setFixedSize(1920,1088); //设置Surface分辨率
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		//Log.d("yin","surfaceCreated");
		camera=Camera.open();
		try {
			Camera.Parameters parameters=camera.getParameters();
			parameters.getPictureSize();
			camera.setDisplayOrientation(90);
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.setRotation(90);
			//parameters.setPictureSize(1920,1088);
			String a= parameters.get("picture-size-values");
			Log.d("yin", "分辨率："+a);
			String []aa=a.split(",");
			int width=0;
			int height=0;
			for(int i=0;i<aa.length;i++){
				String []bb= aa[i].split("x");
				if (Integer.parseInt(bb[0])>1000&&Integer.parseInt(bb[0])<2000) {
					if (Integer.parseInt(bb[0])>width) {
						width=Integer.parseInt(bb[0]);
						height=Integer.parseInt(bb[1]);
					}
				}
			}
			Log.d("yin", "分辨率："+width+"****"+height);

			parameters.setPictureSize(width, height);
			camera.setParameters(parameters);
			//设置参数
			camera.setPreviewDisplay(surfaceHolder);
			//摄像头画面显示在Surface上
			camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
			camera.release();
			camera=null;
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (camera!=null) {
			Log.d("yin","surfaceDestroyed1");
			camera.stopPreview();
		}
		Log.d("yin","surfaceDestroyed2");
		camera.release();
		camera=null;
	}


	AutoFocusCallback mAutoFocusCallback= new AutoFocusCallback(){

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			Log.d("yin","setOnClickListener");
			if(success){
				//对焦成功才拍照
				camera.takePicture(shutterCallback, null, jpegCallback);
			}else{
				Log.d("yin","setOnClickListener22");
			}
		}
	};

	private ShutterCallback shutterCallback = new ShutterCallback()
	{
		public void onShutter()
		{
		}
	};

	private PictureCallback jpegCallback = new PictureCallback()
	{
		public void onPictureTaken(byte[] _data, Camera _camera)
		{
			datecc=_data;
			button2.setVisibility(View.VISIBLE);
			button3.setVisibility(View.VISIBLE);

		}
	};

}
