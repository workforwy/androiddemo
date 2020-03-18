package com.ceshi.ha.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ceshi.ha.R;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 进度条的写法
 */
public class ThreadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        progressBar = findViewById(R.id.progressBar);
//        Thread3 thread3 = new Thread3();
//        FutureTask<Integer> ft = new FutureTask<>(thread3);
//        new Thread(ft, "返回值").start();
//        try {
//            System.out.println("子线程的返回值：" + ft.get());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ThreadPool threadPool =  new ThreadPool();
        threadPool.main(null);
    }

    // 继承
    class Thread1 extends Thread {
        @Override
        public void run() {
            super.run();
            // 1.进度设置为10,线程每睡眠一秒发送一次进度
            for (int i = 0; i <= 10; i++) {
                // 获得消息对象
                Message ms = Message.obtain();
                ms.arg1 = i;
                handler.sendMessage(ms);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 接口
    class Thread2 implements Runnable {

        @Override
        public void run() {
            int progressBarMax = progressBar.getMax();
            try {
                while (progressBarMax != progressBar.getProgress()) {
                    int stepProgress = progressBarMax / 10;
                    int currentprogress = progressBar.getProgress();
                    progressBar.setProgress(currentprogress + stepProgress);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // FutureTask
    class Thread3 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return getData();
        }
    }

    private Integer getData() {
        int i = 0;
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }

    class ThreadPool {
        /* POOL_NUM */
        private int POOL_NUM = 10;

        /**
         * Main function
         */
        public void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (int i = 0; i < POOL_NUM; i++) {
                RunnableThread thread = new RunnableThread();
                executorService.execute(thread);
            }
        }
    }

    class RunnableThread implements Runnable {
        private int THREAD_NUM = 10;

        public void run() {
            for (int i = 0; i < THREAD_NUM; i++) {
                System.out.println("线程" + Thread.currentThread() + " " + i);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 取出进度
            int i = 0;
            i = msg.arg1;
            // 进行进度更新
            progressBar.setProgress(i);
            if (i == 10) {
                Toast.makeText(ThreadActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
