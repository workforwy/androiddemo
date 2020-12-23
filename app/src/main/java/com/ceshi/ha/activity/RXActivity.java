package com.ceshi.ha.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityRxBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static java.lang.Thread.sleep;

public class RXActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaTag";

    private int[] drawableRes = new int[]{
            R.mipmap.amap_bus, R.mipmap.amap_car, R.mipmap.amap_man, R.mipmap.amap_ride,
            R.mipmap.app_guide_broadcast_nor, R.mipmap.app_guide_map_nor, R.mipmap.app_guide_beauty_nor,
            R.mipmap.app_guide_music_nor, R.mipmap.app_guide_news_nor, R.mipmap.app_guide_note_nor};

    private Disposable mDisposable, mDisposable1;
    ActivityRxBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    //RxJava基本使用
    public void rxJavaBaseUse(View view) {
        //被观察者
        Observable obs = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
//                emitter.onComplete();
                emitter.onError(new Throwable("报错了"));
            }
        });

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("连载2".equals(value)) {
                    mDisposable.dispose();
                    return;
                }
                Log.e(TAG, "observer:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError=" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete()");
            }
        };
        //观察者
        Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable1 = d;
                Log.e(TAG, "onSubscribe1");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)) {
                    mDisposable1.dispose();
                    return;
                }
                Log.e(TAG, "observer1:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError1" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete1");
            }
        };

        //订阅
        obs.subscribe(observer);
        obs.subscribe(observer1);
    }

    //RxJava链式使用
    public void rxJavaChainUse(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    //定时操作
    @SuppressLint("CheckResult")
    public void timeDoSomething(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(123);
//                sleep(3000);
                emitter.onNext(456);
//                emitter.onComplete();
                emitter.onError(new Throwable("我想报个错"));
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "Consumer 接收的数据 = " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Consumer 接收的  throwable  =  " + throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "Consumer 下一步继续操作 Action");

                    }
                });

    }

    @SuppressLint("CheckResult")
    public void complicatedDoSomething(View view) {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> emitter) throws Exception {
                for (int i = 0; i < drawableRes.length; i++) {
                    Drawable drawable = getResources().getDrawable(drawableRes[i]);
                    //第6个图片延时3秒后架子
                    if (i == 5) {
                        sleep(10000);
                    }
                    //复制第7张图片到sd卡
                    if (i == 6) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        saveBitmap(bitmap);
                    }
                    //上传到网络
                    if (i == 7) {
                        updateIcon(drawable);
                    }
                    emitter.onNext(drawable);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        //回调后在UI界面上展示出来
                        binding.ivIcon.setImageDrawable(drawable);
                    }
                });
    }

    private void updateIcon(Drawable drawable) {
        Log.e(TAG, "上传成功");
    }

    public void saveBitmap(Bitmap bitmap) {
        Log.e(TAG, "保存成功");
    }

    @SuppressLint("CheckResult")
    public void testArray(View view) {
        binding.textView2.setText("");
        String[] names = {"Hello", "Hi", "Aloha"};
        Observable
                .fromArray(names)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        binding.textView2.setText(s);
                    }
                });
    }
}
