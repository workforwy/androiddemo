package com.ceshi.ha.viewutils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.ceshi.ha.R;

/**
 * Created by WY on 2017/11/8 0008.
 */

public class ProgressBarUtils extends ProgressBar {
    Context mContext;

    public ProgressBarUtils(Context context) {
        super(context);
    }

    public ProgressBarUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBarUtils(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProgressBarUtils(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void Progress() {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("数据正在更新");
        dialog.setMessage("请稍等。。。。。");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆形
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//直线
        dialog.setButton("暂停", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
