/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hbkjgoa.zxing.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.login_sm;
import com.example.hbkjgoa.model.GDZC_Bean;
import com.example.hbkjgoa.rczyk.InfoGDZC_SM;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.zxing.camera.CameraManager;
import com.example.hbkjgoa.zxing.decode.DecodeThread;
import com.example.hbkjgoa.zxing.utils.BeepManager;
import com.example.hbkjgoa.zxing.utils.CaptureActivityHandler;
import com.example.hbkjgoa.zxing.utils.InactivityTimer;
import com.google.zxing.Result;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements
        SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private ImageView mFlash;
    private  String json,spname,spword,tile="",date;
    private Message message;
    private Rect mCropRect = null;
    public Handler getHandler() {
        return handler;
    }
    public CameraManager getCameraManager() {
        return cameraManager;
    }
    private boolean isHasSurface = false;
    private GDZC_Bean ap;
    private final static Pattern URL = Pattern .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler1=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1) {
                if (json==null||json.equals("[]")) {
                    new android.app.AlertDialog.Builder(CaptureActivity.this)
                            .setTitle("登录失败")
                            .setMessage("帐号或者密码不正确，\n请检查后重新输入！")
                            .create().show();
                }else {
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                }
            }else if (msg.what==2){
                     ap= JSONObject.parseObject(json, GDZC_Bean.class);
                    if (ap.getSheBeiLeiBie().contains("打印机")){
                        Intent intent = new Intent(CaptureActivity.this, InfoGDZC_SM.class);
                        intent.putExtra("date", ap.getYuanBianHao());
                        intent.putExtra("tile", tile);
                        intent.putExtra("lx", tile);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "仅支持打印机类别", Toast.LENGTH_SHORT).show();
                    }


            }



        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        spname = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");
        spword = getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spword", "");
        tile = this.getIntent().getStringExtra("tile");
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        mFlash = (ImageView) findViewById(R.id.capture_flash);
        mFlash.setOnClickListener(new View.OnClickListener() {
            private boolean flag;
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.capture_flash) {
                    if (flag == true) {
                        flag = false;
                        // 开闪光灯
                        cameraManager.openLight();
                        mFlash.setBackgroundResource(R.drawable.activity_capture_flash_open);
                    } else {
                        flag = true;
                        // 关闪光灯
                        cameraManager.offLight();
                        mFlash.setBackgroundResource(R.drawable.activity_capture_flash_default);
                    }
                }
            }
        });

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        // 通过这种方式可以获取到扫描的图片
//	bundle.putInt("width", mCropRect.width());
//	bundle.putInt("height", mCropRect.height());
//	bundle.putString("result", rawResult.getText());
//
//	startActivity(new Intent(CaptureActivity.this, ResultActivity.class)
//		.putExtras(bundle));

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                handleText(rawResult.getText());
            }
        }, 800);
    }

    private void handleText(String text) {

        if (isUrl(text)) {
            showUrlOption(text);
        } else {
            handleOtherText(text);
        }
    }
    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }
    private void showUrlOption(final String url) {
        if (url.contains("scan_login")) {
            //showConfirmLogin(url);
            return;
        }else{
            try {
                // 浏览器
                Intent intent = new Intent(CaptureActivity.this, BrowserActivity.class);
                intent.putExtra(BrowserActivity.BUNDLE_KEY_URL, url);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }
    private void handleOtherText(final String text) {
        // 判断是否符合基本的json格式
            showCopyTextOption(text);
    }

    private void showCopyTextOption(final String text) {
        if (text.contains("login")){
            Intent intent = new Intent(CaptureActivity.this, login_sm.class);
            intent.putExtra("date", text);
            startActivity(intent);
            finish();
        }else {
            if (tile.equals("耗材领取")){
                new Thread() {
                    @Override
                    public void run() {
                        json = WebServiceUtil.everycanforStr("bm", "", "", "", text, "", "", 0, "GetSheBeiDetail");
                        Message message = new Message();
                        message.what =2;
                        handler1.sendMessage(message);
                    }
                }.start();
            }else {
                Intent intent = new Intent(CaptureActivity.this, InfoGDZC_SM.class);
                intent.putExtra("date", text);
                intent.putExtra("tile", tile);
                intent.putExtra("lx", tile);
                startActivity(intent);
                finish();
            }


        }


    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}