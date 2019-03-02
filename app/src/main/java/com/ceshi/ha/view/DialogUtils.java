package com.ceshi.ha.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ceshi.ha.R;

/**
 * Created by WY on 2017/10/11 0011.
 */

public class DialogUtils extends android.app.Dialog {

    Context context;
    DialogInterface exitDialog;

    public DialogUtils(@NonNull Context context) {
        super(context);
    }

    public DialogUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUtils(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void showMydialog() {
        //        View view = context.getLayoutInflater().inflate(R.layout.dialog_password, null);
        //        final MyDialog builder = new MyDialog(context, 0, 0, view, R.style.DialogTheme);
        //        builder.show();
    }

    void alert1() {
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

    void alert2() {
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


    public void onClick(DialogInterface dialog, int which) {
        // 判断是哪个对话框
        if (dialog == exitDialog) {
            // 点击了“退出”时弹出的对话框，则判断点的是哪个按钮
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    // 在本例中，NEGATIVE对应的是：确定，即退出程序

                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    // 在本例中，POSITIVE对应的是：取消，即隐藏对话框即可，不必退出程序
                    exitDialog.dismiss();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    // 在本例中，NEUTRAL对应的是：后台播放，实现方案可以使用“回到桌面”
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
