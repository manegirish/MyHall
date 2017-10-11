package com.technoindians.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

/**
 * Created by GirishM on 11-10-2017.
 */

public class RandomColor {

    public static int get(Context context) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("random_color", "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
}
