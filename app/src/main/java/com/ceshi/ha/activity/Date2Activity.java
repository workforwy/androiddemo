package com.ceshi.ha.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date2Activity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //构建一个线性布局对象
        LinearLayout layout = new LinearLayout(this);
        //设置线性布局的布局方式
        layout.setOrientation(LinearLayout.VERTICAL);
        //构建一个Button对象
        btn = new Button(this);
        btn.setText("显示时间对话框");
        btn.setOnClickListener(this);
        //将button对象添加到线性布局对象中
        layout.addView(btn, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //将布局对象添加到activity对应的window窗口中
        setContentView(layout);

    }

    /**
     * 点击Button对象时会执行此方法
     */
    @Override
    public void onClick(View v) {
        //获得当前时间的年月日
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        //构建一个日历对话框对象
        DatePickerDialog dialog = new DatePickerDialog(this,//context
                new OnDateSetListener() {
                    //当点击日历对话框的完成按钮时执行此方法
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //重新设置日历对象的时间
                        c.set(year, monthOfYear, dayOfMonth);
			/*
			//获得星期几
			String week=DateUtils.formatDateTime(
			MainActivity.this,//context
			c.getTimeInMillis(),
			DateUtils.FORMAT_SHOW_WEEKDAY);
			//将设置的内容显示在button上
     		btn.setText(year+
			"-"+$(monthOfYear+1)+
			"-"+$(dayOfMonth)+":"+week);*/

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE");
                        String time = sdf.format(c.getTime());//date
                        btn.setText(time);
                    }
                }, //callBack???
                year, monthOfYear, dayOfMonth);

        //显示对话框
        dialog.show();
    }

    private String $(int n) {
        return n < 10 ? "0" + n : "" + n;
    }
}
