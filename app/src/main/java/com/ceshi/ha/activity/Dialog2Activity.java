package com.ceshi.ha.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ceshi.ha.R;

public class Dialog2Activity extends Activity implements
        android.view.View.OnClickListener,
        android.content.DialogInterface.OnClickListener {
    Button btn01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        btn01 = new Button(this);
        btn01.setText("请选择城市");
        btn01.setOnClickListener(this);
        setContentView(btn01);
    }

    String items[] = {"北京", "上海", "深圳"};
    private AlertDialog dialog;

    @Override
    public void onClick(View v) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("选择出发城市")
                //.setItems(items, this)
                .setSingleChoiceItems(items, -1, this)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        btn01.setText(items[which]);
        //销毁对话框
        dialog.cancel();
        //dialog.dismiss();
    }

    private void showImageDialog() {
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.png_01);
		/*new AlertDialog.Builder(this)
		.setView(iv)//(View v)
		.create().show();*/

        //Toast.makeText(context, text, duration)

        Toast t = new Toast(this);
        t.setView(iv);
        t.setDuration(Toast.LENGTH_LONG);
        //设置位置
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }
}
