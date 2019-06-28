package com.ceshi.ha.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.ceshi.ha.R;
import com.ceshi.ha.views.PassWordDialog;

/**
 * Created by WY on 2017/10/11 0011.
 */

public class DialogUtils {

    private void passWordDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_password, null);
        final PassWordDialog builder = new PassWordDialog(context, 0, 0, view, R.style.DialogTheme);
        builder.show();
    }

    private void twoButtonDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("您确定要退出吗？")
                .setPositiveButton("取消", null)
                .setNegativeButton("确定", null)
                .create()
                .show();

    }

    private void threeButtonDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setMessage("您确定要退出吗？");
        builder.setPositiveButton("取消", null);
        builder.setNegativeButton("确定", null);
        builder.setNeutralButton("中立", null);//中立选择
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void progressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("数据正在更新");
        dialog.setMessage("请稍等。。。。。");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆形
        //        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//直线
        dialog.setButton("暂停", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
