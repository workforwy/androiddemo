package com.ceshi.ha.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ceshi.ha.base.BaseActivity;
import com.ceshi.ha.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public class RetrofitActivity extends BaseActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.getBlog(2);
// 用法和OkHttp的call如出一辙,
// 不同的是如果是Android系统回调方法执行在主线程
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    interface BlogService {
        @GET("blog/{id}")
        Call<ResponseBody> getBlog(@Path("id") int id);
    }

    interface BlogService1 {
        /**
         * method 表示请求的方法，区分大小写
         * path表示路径
         * hasBody表示是否有请求体
         */
        @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
        Call<ResponseBody> getBlog(@Path("id") int id);
    }
}
