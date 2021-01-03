package com.example.hbkjgoa.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class GetUUI {
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static UUID uuid;


    public GetUUI(Context context) {

        if( uuid ==null ) {
            synchronized (GetUUI.class) {
                if( uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences( PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null );

                    if (id != null) {
                        uuid = UUID.fromString(id);

                    } else {

                        final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                @SuppressLint("MissingPermission") final String deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                                uuid = deviceId!=null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }

                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString() ).commit();

                    }

                }
            }
        }

    }


    public UUID getDeviceUuid() {
        return uuid;
    }
}
