package com.ceshi.ha.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import androidx.collection.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageUtils {

    private static LruCache<String, Bitmap> mCaches;

    /**
     * 定义上下文对象
     */
    private Context mContext;

    private static Handler mHandler;

    //声明线程池,全局只有一个线程池,所有访问网络图片，只有这个池子去访问。
    private static ExecutorService mPool;

    //解决错位问题，定义一个存标记的集合
    private Map<ImageView, String> mTags = new LinkedHashMap<ImageView, String>();

    public ImageUtils(Context context) {
        this.mContext = context;
        if (mCaches == null) {
            //申请内存空间
            int maxSize = (int) (Runtime.getRuntime().freeMemory() / 4);
            //实例化LruCache
            mCaches = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    //判断添加进入的value的占用内存的大小
                    //这里默认sizeOf是返回1，不占用，内存会不够用，所以要给它一个具体占用内存的大小
                    //                    return super.sizeOf(key, value);
                    //获取Bitmap的大小
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }
        if (mHandler == null) {
            //实例化Handler
            mHandler = new Handler();
        }

        if (mPool == null) {
            //创建固定大小的线程池
            mPool = Executors.newFixedThreadPool(3);
            //创建一个缓存的线程池,生产者和消费者，一个线程生产，必须得消费完成后再生产
            /*Executors.newCachedThreadPool();
            Executors.newSingleThreadExecutor();//创建一个单线程池
            Executors.newScheduledThreadPool();//创建一个计划的任务池*/
        }
    }

    /**
     * 给imageView加载url对应的图片
     *
     * @param iv
     * @param url
     */
    public void display(ImageView iv, String url) {
        //1.从内存中获取
        Bitmap bitmap = mCaches.get(url);
        if (bitmap != null) {
            //内存中有，显示图片
            iv.setImageBitmap(bitmap);
            return;
        }

        //2.内存中没有，从本地获取
        bitmap = loadFromLocal(url);
        if (bitmap != null) {
            //本地有,显示
            iv.setImageBitmap(bitmap);
            return;
        }

        //从网络中获取
        loadFromNet(iv, url);
    }

    private void loadFromNet(ImageView iv, String url) {

        mTags.put(iv, url);//url是ImageView最新的地址

        //耗时操作
        //        new Thread(new LoadImageTask(iv, url)).start();
        //用线程池去管理
        mPool.execute(new LoadImageTask(iv, url));
        //        Future<?> submit = mPool.submit(new LoadImageTask(iv, url));
        //取消的操作（有机率取消）,而使用execute没有办法取消
        //        submit.cancel(true);
    }

    private class LoadImageTask implements Runnable {
        private ImageView iv;
        private String url;

        public LoadImageTask(ImageView iv, String url) {
            this.iv = iv;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

                //连接服务器超时时间
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                //连接服务器(可写可不写)
                conn.connect();

                //获取流
                InputStream is = conn.getInputStream();

                //将流变成bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                //存储到本地
                save2Local(bitmap, url);

                //存储到内存
                mCaches.put(url, bitmap);

                //在显示UI之前，拿到最新的url地址
                String recentlyUrl = mTags.get(iv);

                //把这个url和最新的url地址做一个比对，如果相同，就显示ui
                if (url.equals(recentlyUrl)) {
                    //显示到UI,当前是子线程,需要使用Handler。其中post方法是执行在主线程的
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            display(iv, url);
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 存储到本地
     *
     * @param bitmap
     * @param url
     */
    public void save2Local(Bitmap bitmap, String url) throws FileNotFoundException {
        File file = getCacheFile(url);
        FileOutputStream fos = new FileOutputStream(file);
        /**
         * 用来压缩图片大小
         * Bitmap.CompressFormat format 图像的压缩格式；
         * int quality 图像压缩率，0-100。 0 压缩100%，100意味着不压缩；
         * OutputStream stream 写入压缩数据的输出流；
         * 返回值：如果成功地把压缩数据写入输出流，则返回true。
         */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
    }

    /**
     * 从本地获取图片
     *
     * @param url
     * @return bitmap
     */
    private Bitmap loadFromLocal(String url) {
        //本地需要存储路径
        File file = getCacheFile(url);

        if (file.exists()) {
            //本地有
            //把文件解析成Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //存储到内存
            mCaches.put(url, bitmap);

            return bitmap;
        }

        return null;
    }

    /**
     * 获取缓存文件路径(缓存目录)
     *
     * @return 缓存的文件
     */
    private File getCacheFile(String url) {
        //把url进行md5加密
//        String name = MD5Utils.encode(url);
        String name = url;
        //获取当前的状态,Environment是环境变量
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //挂载状态，sd卡存在
            File dir = new File(Environment.getExternalStorageDirectory(),
                    "/Android/data/" + mContext.getPackageName() + "/icon");
            if (!dir.exists()) {
                //文件不存在,就创建
                dir.mkdirs();
            }
            //此处的url可能会很长，一般会使用md5加密
            return new File(dir, name);
        } else {
            File dir = new File(mContext.getCacheDir(), "/icon");
            if (!dir.exists()) {
                //文件不存在,就创建
                dir.mkdirs();
            }
            return new File(dir, name);
        }
    }
}