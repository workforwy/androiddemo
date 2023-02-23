package com.ceshi.ha.activity;

import android.os.Bundle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.Log;
import android.widget.Toast;

import com.ceshi.ha.R;

public class DialogActivity extends AppCompatActivity implements OnClickListener, OnMultiChoiceClickListener {

    private List<CharSequence> list = new LinkedList<CharSequence>();
    CharSequence items[] = {"清除浏览记录", "不再提示"};
    boolean checkedItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 当点击手机上的回退键时会自动执行此方法
     */
    @Override
    public void onBackPressed() {
        checkedItems = new boolean[items.length];
        //构建对话框
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("退出")
                //.setMessage("您确定退出吗?")
                .setMultiChoiceItems(items, checkedItems, this)
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", null)
                .create();
        //显示对话框
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (list.size() != 0) {
                //执行操作
                Toast.makeText(this, list.toString(), 1).show();
                list.clear();
                //在LogCat窗口输入一段信息
                Log.i("TAG", Arrays.toString(checkedItems));
            }
            //finish();//销毁当前activity
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
            list.add(items[which]);
        } else {
            list.remove(items[which]);
        }
    }



}
