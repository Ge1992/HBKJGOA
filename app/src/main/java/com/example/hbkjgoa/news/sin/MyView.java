package com.example.hbkjgoa.news.sin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.hbkjgoa.R;

public class MyView extends View {
	private Paint paint = null;
	private Bitmap originalBitmap = null;
	private Bitmap new1Bitmap = null;
	private Bitmap new2Bitmap = null;
	private float clickX = 0, clickY = 0;
	private float startX = 0, startY = 0;
	private boolean isMove = true;
	private boolean isClear = false;
	private int color = Color.BLACK;
	private float strokeWidth = 10.0f;
	private Context context;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zjtbg1).copy(Bitmap.Config.ARGB_8888, true);
		new1Bitmap = Bitmap.createBitmap(originalBitmap);
		setDrawingCacheEnabled(true);
		Log.i("RG", "new1Bitmap--->>>" + new1Bitmap);
	}

	public void clear() {
		isClear = true;
		new2Bitmap = Bitmap.createBitmap(originalBitmap);
		invalidate();
	}

	Bitmap saveImage = null;

	public Bitmap saveImage() {
		if (saveImage == null) {
			return null;
		}
		return saveImage;
	}

	public void setImge() {
		saveImage = null;
	}

	public void setstyle(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	Handler handler1;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(HandWriting(new1Bitmap), (SignPic.displayWidth-new1Bitmap.getWidth())/2  , 0, null);
		handler1 = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				Log.i("RG", "--------------------");
				int what = msg.what;
				if (what == 2) {
					
					Matrix matrix = new Matrix(); 
					matrix.postScale(0.072f,0.072f);
					Bitmap resizeBmp= HandWriting(new1Bitmap);
					saveImage = Bitmap.createBitmap(resizeBmp,0,0,resizeBmp.getWidth(),resizeBmp.getHeight(),matrix,true);
					Message msg1 = new Message();
					msg1 = Message.obtain(SignPic.hh, 3);
					SignPic.hh.sendMessage(msg1);
				}
				super.handleMessage(msg);
			}

		};

	}
	Handler handler;
	@SuppressLint("HandlerLeak")
	public Bitmap HandWriting(Bitmap originalBitmap) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int what = msg.what;
				if (what == 1) {
					startX = clickX;
					startY = clickY;
				}
				super.handleMessage(msg);
			}

		};
		Canvas canvas = null;

		if (isClear) {
			canvas = new Canvas(new2Bitmap);
			Log.i("RG", "canvas ");
		} else {
			canvas = new Canvas(originalBitmap);
		}
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
		Log.i("RG", "startX-->>>>" + startX);
		Log.i("RG", "startY-->>>>" + startY);
		if (isMove) {
			canvas.drawLine(startX-(SignPic.displayWidth-new1Bitmap.getWidth())/2, startY, clickX-(SignPic.displayWidth-new1Bitmap.getWidth())/2, clickY, paint);
		}

		if (isClear) {
			return new2Bitmap;
		}

		return originalBitmap;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Message msg = new Message();
		msg = Message.obtain(handler, 1);
		handler.sendMessage(msg);
		clickX = event.getX();
		clickY = event.getY();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			isMove = false;
			invalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {

			isMove = true;
			invalidate();
			return true;
		}

		return super.onTouchEvent(event);
	}

}