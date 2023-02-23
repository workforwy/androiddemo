package com.ceshi.ha.activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class IO2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File sdcard = new File("/mnt/sdcard/Pictures");
        File pic = new File(sdcard, "ql.JPG");

        Bitmap bitMap = decodeOptionFile(pic);
        //将bitMap对象显示在imageView上
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(bitMap);
        setContentView(iv);

        //将此对象缓存起来(缓存一个目录)

        writeFileToSdcardCache(bitMap);


    }

    private void writeFileToSdcardCache(Bitmap bitMap) {
        File cache =//
                getExternalCacheDir();//context
        File newPic = new File(cache, "cacheql.JPG");
        OutputStream out = null;
        try {
            out = new FileOutputStream(newPic);
            bitMap.compress(CompressFormat.JPEG, 100, out);
            out.close();
            Log.i("TAG", "file cache ok!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap decodeOptionFile(File pic) {
        //对此File对应的图片进行压缩
        //1.借助option对象封装选项信息
        Options opts = new Options();
        //2.设置Options对象的inJustDecodeBounds值为true
        //当此属性的值为true时，表示只读取图片的边界信息
        opts.inJustDecodeBounds = true;
        //3.读取图片边界信息，并将其信息封装到options对象
        BitmapFactory.decodeFile(pic.getPath(), opts);
        //4.获得图片边界信息
        int oHeight = opts.outHeight;
        int oWidth = opts.outWidth;
        Log.i("TAG", "oHeight=" + oHeight);
        Log.i("TAG", "oWidth=" + oWidth);
        //5.计算并设置压缩比例
        //获得window窗口显示信息，
        //并将其封装到DisplayMetrics对象中
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int hPix = dm.heightPixels;
        int wPix = dm.widthPixels;
        Log.i("TAG", "hPix=" + hPix);
        Log.i("TAG", "wPix=" + wPix);

        int x = oWidth / wPix;
        int y = oHeight / hPix;
        Log.i("TAG", "x=" + x);
        Log.i("TAG", "y=" + y);
        //压缩比例的计算由实际业务决定
        if (x >= y && x > 1) {
            opts.inSampleSize = x;
        } else if (y > x && y > 1) {
            opts.inSampleSize = y;
        }
        //6.设置Options对象的inJustDecodeBounds值为false
        opts.inJustDecodeBounds = false;
        //7.读取文件内容
        Bitmap bitMap = BitmapFactory.decodeFile(pic.getPath(), opts);
        return bitMap;
    }
}
