package com.example.hbkjgoa.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	 
    private static Context context = null;
    private static Toast toast = null;
 
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}