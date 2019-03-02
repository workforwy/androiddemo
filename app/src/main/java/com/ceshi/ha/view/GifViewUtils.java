package com.ceshi.ha.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.ceshi.ha.R;

/**
 * Created by WY on 2017/10/12 0012.
 */

public class GifViewUtils extends View {

    private long movieStart;
    private Movie movie;

    //此处必须重写该构造方法
    @SuppressLint("ResourceType")
    public GifViewUtils(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //以文件流（InputStream）读取进gif图片资源
        movie = Movie.decodeStream(getResources().openRawResource(R.mipmap.ic_launcher));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long curTime = android.os.SystemClock.uptimeMillis();
        //第一次播放
        if (movieStart == 0) {
            movieStart = curTime;
        }
        if (movie != null) {
            int duraction = movie.duration();
            int relTime = (int) ((curTime - movieStart) % duraction);
            movie.setTime(relTime);
            movie.draw(canvas, 0, 0);
            //强制重绘
            invalidate();
        }
        super.onDraw(canvas);
    }
}
