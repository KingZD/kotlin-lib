package com.zed.http;

import com.zed.common.constant.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zed.http.header.HeaderInterceptor;
import com.zed.http.log.HttpLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Desc  : 网络请求客户端
 */
public class HttpClient {

    private volatile static HttpClient INSTANCE = null;

    private Retrofit retrofit;

    private HttpClient() {
        this(AppConstant.BaseUrl);
    }

    public HttpClient(String baseUrl) {
        //打印请求Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //打印请求log
                .addInterceptor(logInterceptor)
                //stetho, 可以在chrome中查看请求
                .addNetworkInterceptor(new StethoInterceptor())
                //添加 header Params
                .addInterceptor(new HeaderInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                //time out
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .connectTimeout(25, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(okHttpClient)
                //baseUrl
                .baseUrl(baseUrl)
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create(gson))
                //自适配
                .addCallAdapterFactory(GAObservableFactory.create())
                //Rx
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

    }

    public static HttpClient getSingleton() {
        if (INSTANCE == null) {
            synchronized (HttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpClient();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T getService(Class<T> t) {
        return retrofit.create(t);
    }
}
