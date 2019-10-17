package com.cn.ql.frame.net;

import android.text.TextUtils;

import android.util.Log;
import com.cn.ql.frame.net.cookie.CookieJarImpl;
import com.cn.ql.frame.net.cookie.store.PersistentCookieStore;
import com.cn.ql.frame.utils.Utils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitFactory
 * Created by jaycee on 2017/6/23.
 */
public class RetrofitFactory {

    private static final long TIMEOUT = 30;

    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient okHttpClient;

    public static Map<String, String> header = new HashMap<>();

    public static void initOkHttpClick(){
        okHttpClient = new OkHttpClient.Builder()
                // 添加通用的Header
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        // 替换为自己的token
                        if (header != null){
                            Set<String> keySets = header.keySet();
                            for (String key: keySets){
                                String value = header.get(key);
                                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
                                    builder.addHeader(key, value);
                                }
                            }
                        }
                        return chain.proceed(builder.build());
                    }
                })
                /*
                这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
                出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
                 */
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        System.out.println("log:"+message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(Utils.getContext())))

//                .addInterceptor(new AddCookiesInterceptor()) //这部分
//                .addInterceptor(new ReceivedCookiesInterceptor()) //这部分

                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public static <T>T getInstance(String baseUrl, Class<T> service) {
        if (okHttpClient == null){
            initOkHttpClick();
        }
        T retrofitService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(service);
        return retrofitService;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(JsonElement.class, new RetrofitAdapter())
                .create();
    }

}
