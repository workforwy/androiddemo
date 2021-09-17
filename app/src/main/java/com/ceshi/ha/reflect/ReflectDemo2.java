package com.ceshi.ha.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ceshi.ha.R;

public class ReflectDemo2 extends Activity {

    //int imgs[]={R.drawable.png_01,R.drawable.png_02};
    List<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflectdemo2);
        //获得drawable的类对象
        Class<?> c = R.drawable.class;
        //获得类对象中的所有属性
        Field[] fs = c.getDeclaredFields();
        //提取属性名以png开头的属性值。
        try {//检查异常
            for (Field f : fs) {
                if (f.getName().startsWith("png")) {
                    images.add(f.getInt(c));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //==================
        //将list集合中的图片显示在gridview中
        //获得GridView
        GridView gv = (GridView) findViewById(R.id.gridView);
        //构建适配器
        ImageAdapter adapter = new ImageAdapter();
        //关联适配器
        gv.setAdapter(adapter);
    }

    class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = (ImageView) View.inflate(ReflectDemo2.this, R.layout.grid_item_1, null);
            iv.setImageResource(images.get(position));
            return iv;
        }
    }
}
