package com.ceshi.ha.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ceshi.ha.bean.Contact;
import com.ceshi.ha.bean.Container;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_contacts);

        // 从容器中获得数据
        Container c = Container.getInstance();
        Contact[] contacts = c.getContacts();
        // 将数据显示TextView中
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < c.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(contacts[i].getName() + ":" + contacts[i].getPhone());
            tv.setTextSize(20);
            tv.setTextColor(Color.RED);
            layout.addView(tv);
        }
        setContentView(layout);

    }

}

