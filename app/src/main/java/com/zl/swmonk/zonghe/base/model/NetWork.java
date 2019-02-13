package com.zl.swmonk.zonghe.base.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zl.swmonk.zonghe.base.util.Factory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {
    private static NetWork instance;
    private Retrofit retrofit;
    private OkHttpClient client;

    static {
        instance = new NetWork();
    }

    public static OkHttpClient getClient() {
        if (instance.client != null)
            return instance.client;

        // 存储起来
        instance.client = new OkHttpClient.Builder()
                // 给所有的请求添加一个拦截器
                .addInterceptor(chain -> {
                    // 拿到我们的请求
                    Request original = chain.request();
                    // 重新进行build
                    Request.Builder builder = original.newBuilder();
                    builder.addHeader("Authorization", "APPCODE 79ad731890c244d48d7453282561bb80");
                    Request newRequest = builder.build();

                    // 返回
                    return chain.proceed(newRequest);
                })
                .build();
        return instance.client;
    }

    // 构建一个Retrofit
    public static Retrofit getRetrofit() {
        if (instance.retrofit != null)
            return instance.retrofit;

        // 得到一个OK Client
        OkHttpClient client = getClient();

        // Retrofit
        Retrofit.Builder builder = new Retrofit.Builder();

        // 设置电脑链接
        instance.retrofit = builder.baseUrl("https://ali-joke.showapi.com/")
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return instance.retrofit;

    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remote() {
        return NetWork.getRetrofit().create(RemoteService.class);
    }
}
