package com.ceshi.ha.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.ceshi.ha.R;
import com.ceshi.ha.bean.Contact;
import com.ceshi.ha.bean.Container;

public class InnerActivity extends Activity {
    /** 实例变量 */
    private EditText nameEt;
    private EditText phoneEt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        // 创建name编辑框
        nameEt = createNameEditText();
        // 创建phone编辑框
        phoneEt = createPhoneEditText();
        // 构建一个Button对象
        btn = createAddButton();// alt+shift+m
        // 将view对象添加到容器中
        setContentView(nameEt, phoneEt, btn);
    }

    private void setContentView(EditText nameEt, EditText phoneEt, Button btn) {
        // 构建一个相对布局对象
        RelativeLayout layout = new RelativeLayout(this);// Context
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, // width
                LayoutParams.MATCH_PARENT);// height
        // 设置布局规则
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 100);
        layout.setLayoutParams(layoutParams);
        layout.addView(nameEt);// View
        layout.addView(phoneEt);
        layout.addView(btn);
        // 将layout添加到activity对应的window窗口
        setContentView(layout);
    }

    private Button createAddButton() {
        Button btn = new Button(this);
        btn.setId(200);
        btn.setText("添加联系人");
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundResource(R.mipmap.green_btn);
        LayoutParams btnParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 设置外边距
        btnParams.topMargin = 30;
        btnParams.leftMargin = 30;
        btnParams.rightMargin = 30;
        // 添加规则(让button在101的下面)
        btnParams.addRule(RelativeLayout.BELOW, 101);
        // 设置Button的布局
        btn.setLayoutParams(btnParams);
        // 设置Button的监听器
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContactToArray();
            }
        });
        return btn;
    }

    private void saveContactToArray() {
        // 1.获得输入的名字，手机号
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        // 2.对数据进行非空验证
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("名字不能为空!");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneEt.setError("手机号不能为空!");
            return;
        }
        // 3.将数据存起来？
        Toast.makeText(this, name + "/" + phone, 1).show();
        // 获得一个容器对象
        Container c = Container.getInstance();
        // 构建联系人对象，封装页面数据
        Contact contact = new Contact(name, phone);
        // 将联系人对象添加到容器
        c.addContact(contact);
        // 清空编辑框内容
        nameEt.setText("");
        phoneEt.setText("");
        // 构建一个意图对象，通过此对象封装数据
        Intent intent = new Intent(this, ContactsActivity.class);
        // 启动activity
        startActivity(intent);
    }

    private EditText createNameEditText() {
        // 构建一个EditText对象
        EditText nameEt = new EditText(this);
        nameEt.setId(100);
        // 设置提示信息
        nameEt.setHint("please input name!");
        LayoutParams nameEtParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        nameEtParams.topMargin = 30;
        nameEt.setLayoutParams(nameEtParams);
        return nameEt;
    }

    private EditText createPhoneEditText() {
        // 构建一个EditText对象
        EditText et = new EditText(this);
        et.setId(101);
        // 设置提示信息
        et.setHint("please input phone!");
        LayoutParams etParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        etParams.topMargin = 20;
        etParams.addRule(RelativeLayout.BELOW, 100);
        et.setLayoutParams(etParams);
        return et;
    }
}
