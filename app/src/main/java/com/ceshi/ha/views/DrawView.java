package com.ceshi.ha.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

public class DrawView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //构建一个Bitmap对象
        Bitmap bitMap = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888);
        //构建一个画布对象(关联BitMap对象)
        Canvas canvas = new Canvas(bitMap);
        //绘制一个矩形
        //构建矩形
        RectF rect = new RectF(10, 10, 100, 100);
        //画笔对象
        Paint paint = new Paint();
        //Random r=new Random();
        /*paint.setColor( Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));*/
        paint.setColor(Color.BLACK);
        //绘制一个长方形
        canvas.drawRect(rect, paint);
        //绘制一个文本
        paint.setColor(Color.RED);
        canvas.drawText("AAA", 50, 50, paint);
        //构建ImageView对象
        ImageView iv = new ImageView(this);//Context
        //设置要显示的位图对象
        iv.setImageBitmap(bitMap);
        //将imageView放到 activity对应的window窗口上
        setContentView(iv);
    }
}
