package com.example.hbkjgoa.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetHelper {
	  public static boolean IsHaveInternet(final Context context) {
			
	        try {
	
	            ConnectivityManager manger =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            if (manger==null) {
					return false;
				}
	
	            NetworkInfo info = manger.getActiveNetworkInfo();
	            if (info==null||!info.isAvailable()) {
					return false;
				}
	            return true;
	
	        } catch (Exception e) {
	            return false;
	        }
	
	    }
}
