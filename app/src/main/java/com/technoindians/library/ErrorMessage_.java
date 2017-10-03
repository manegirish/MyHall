package com.technoindians.library;

import android.content.Context;

import com.technoindians.myhall.R;

/**
 * Created by GirishM on 03-10-2017.
 */

public class ErrorMessage_ {

    public static String get(int status, Context context) {
        if (status == 12) {
            return context.getResources().getString(R.string.network_error_occurred);
        }
        if (status == 2) {
            return context.getResources().getString(R.string.no_record_found);
        }
        return context.getResources().getString(R.string.internal_error_occurred);
    }
}
