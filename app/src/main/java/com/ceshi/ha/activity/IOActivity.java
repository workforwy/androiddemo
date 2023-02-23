package com.ceshi.ha.activity;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.ceshi.ha.R;

public class IOActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File sdcard =
                new File("/mnt/sdcard/Pictures");
        File pic =
                new File(sdcard, "ql.JPG");
        ByteArrayOutputStream bos =
                new ByteArrayOutputStream();
        InputStream in = null;
        byte buf[] = new byte[1024];
        int len = -1;
        Bitmap bm = null;
        try {
            in = new FileInputStream(pic);
            while ((len = in.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            byte bits[] = bos.toByteArray();
            bm = BitmapFactory
                    .decodeByteArray(bits, 0, bits.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //in.close
        }
        ImageView iv =
                new ImageView(this);
        iv.setImageBitmap(bm);
        setContentView(iv);
    }
}
