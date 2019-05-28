package com.ceshi.ha.utils.toast;

import android.content.Context;
import android.widget.Toast;

/*
 * Author: lihanyang
 * Create: 2017/12/12 
 * Description: $desc$
 */
public class ToastUtils {
    private static ToastUtils sInstance;
    private Toast mToast;

    private ToastUtils() {
    }

    public static synchronized ToastUtils getInstance() {
        if (sInstance == null) {
            sInstance = new ToastUtils();
        }
        return sInstance;
    }

    public Toast createToast(Context context, String text, int duration) {
        if (context != null) {
            context = context.getApplicationContext();
            cancelLastToast();
            text = getTextToShow(text);
            duration = getCorrectDuration(duration);
            mToast = Toast.makeText(context, text, duration);
        }
        return mToast;
    }

    private int getCorrectDuration(int duration) {
        if (duration < 0) {
            duration = Toast.LENGTH_SHORT;
        }
        return duration;
    }

    private String getTextToShow(String text) {
        if (text == null) {
            text = "";
        }
        return text;
    }

    public Toast createToast(Context context, int resId, int duration) {
        if (context != null) {
            context = context.getApplicationContext();
            cancelLastToast();
            String text = getTextToShow(context, resId);
            duration = getCorrectDuration(duration);
            mToast = Toast.makeText(context, text, duration);
        }

        return mToast;
    }

    public Toast createToast(Context context, String message, int duration, int flag) {
        if (context != null) {
            context = context.getApplicationContext();
            cancelLastToast();
            duration = getCorrectDuration(duration);
            // mToast = SabineApi.makeText(context,
            // message, duration, flag);
            mToast = Toast.makeText(context, message, duration);
        }

        return mToast;
    }

    public void showToast(Context context, String text) {
        createToast(context, text, Toast.LENGTH_SHORT).show();
    }

    private String getTextToShow(Context context, int resId) {
        String text;
        if (resId < 0) {
            text = "";
        } else {
            text = context.getResources().getString(resId);
        }
        return text;
    }

    private void cancelLastToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
