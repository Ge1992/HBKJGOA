package com.example.hbkjgoa.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

public class PicUtils {
	
	
	public static Bitmap createBitmap( Bitmap src, Bitmap watermark )

    {

    if( src == null )

    {

    return null;

    }

    int w = src.getWidth();

    int h = src.getHeight();

    int ww = watermark.getWidth();

    int wh = watermark.getHeight();

    //create the new blank bitmap

    Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
    //创建�?个新的和SRC长度宽度�?样的位图

    Canvas cv = new Canvas( newb );

    //draw src into

    cv.drawBitmap( src, 0, 0, null );//�? 0�?0坐标�?始画入src

    //draw watermark into

    //cv.drawBitmap( watermark, w - ww + 5, h - wh + 5, null );//在src的右下角画入水印
    
    cv.drawBitmap( watermark, w / 2 - ww / 2,h / 2 - wh / 2, null );//在src的中间画入水�?
    
    //save all clip

        cv.save();
    //store

    cv.restore();//存储

    return newb;

    }

}
