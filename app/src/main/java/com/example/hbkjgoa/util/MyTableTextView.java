package com.example.hbkjgoa.util;
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MyTableTextView extends TextView {

	Paint paint = new Paint();

	public MyTableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int color = Color.parseColor("#ACACAC");
		//为边框设置颜色
		paint.setColor(color);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画四个边框
		canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
		canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
	}
}