package com.technoindians.library;

/**
 * @author Girish D M(girishmane8692@gmail.com)
 *         Created on 28/10/16.
 *         Last Modified on 28/10/16.
 */
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

import com.technoindians.myhall.R;


public class ActivityTransition {

    public static Bundle moveToNextAnimation(Context _context) {
        return ActivityOptions.makeCustomAnimation
                (_context, R.anim.animation_one, R.anim.animation_two).toBundle();
    }
}
