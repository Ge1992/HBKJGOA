package com.example.hbkjgoa.util;

import android.content.SharedPreferences;
import android.content.Context;

public class PreferenceUtils {

	    private static SharedPreferences sp;
	  
	    public static void getSharedPreference(Context context) {
	        if (sp == null) {
	            sp = context.getApplicationContext().getSharedPreferences("config", context.getApplicationContext().MODE_PRIVATE);
	        }
	    }

	    public static void putString(Context context, String key, String value) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        sp.edit().putString(key, value).commit();
	    }

	    public static String getString(Context context, String key, String defValue) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        return sp.getString(key, defValue);
	    }

	    public static void putInt(Context context, String key, int value) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        sp.edit().putInt(key, value).commit();
	    }

	    public static int getInt(Context context, String key, int defValue) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        return sp.getInt(key, defValue);
	    }

	    public static void putBoolean(Context context, String key, boolean value) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        sp.edit().putBoolean(key, value).commit();
	    }

	    public static boolean getBoolean(Context context, String key,
	                                     boolean defValue) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        return sp.getBoolean(key, defValue);
	    }

	    /**
	     * �Ƴ�
	     */
	    public static void remove(Context context, String key) {
	        if (context.getApplicationContext() != null) {
	            getSharedPreference(context.getApplicationContext());
	        } else {
	            getSharedPreference(context);
	        }
	        sp.edit().remove(key).commit();
	    }
}
