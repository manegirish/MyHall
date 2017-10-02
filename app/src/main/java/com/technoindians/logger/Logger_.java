package com.technoindians.logger;

import android.content.Context;
import android.util.Log;

import com.technoindians.preferences.Preferences;

/**
 * Created by Trojan on 10/1/2017.
 */

public class Logger_ {

    public static void error(String TAG, String logCat, Context context) {
        Preferences.initialize(context);
        if (Preferences.contains("error") && Preferences.get("error").equalsIgnoreCase("1")) {
            Log.e(TAG, logCat);
        }
    }

    public static void debug(String TAG, String logCat, Context context) {
        Preferences.initialize(context);
        if (Preferences.contains("debug") && Preferences.get("debug").equalsIgnoreCase("1")) {
            Log.d(TAG, logCat);
        }
    }

    public static void info(String TAG, String logCat, Context context) {
        Preferences.initialize(context);
        if (Preferences.contains("info") && Preferences.get("info").equalsIgnoreCase("1")) {
            Log.i(TAG, logCat);
        }
    }
}
